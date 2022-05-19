package com.tesoem.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tesoem.movieapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private  lateinit var binding: ActivityMainBinding
    private  lateinit var adapter: ViewAdapter

    private  val poster = mutableListOf<String >()
    private var nombres = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svMovies.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        adapter = ViewAdapter(poster,nombres)
        binding.rvMovies.layoutManager = GridLayoutManager(this,2)
        binding.rvMovies.adapter = adapter

    }
    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://online-movie-database.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }

    private fun getClient():OkHttpClient =
       OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()

    private fun searchBy(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIservice::class.java).getData("auto-complete?q=$query")
            val movies = call.body()
            runOnUiThread{
                //Comprueba si la conexion fue exitosa
                if (call.isSuccessful){
                    val images = movies?.d ?: emptyList()
                    poster.clear()
                    nombres.clear()
                    for(i in 0 until  images.count()){
                        val getMovie = images[i].i
                        val getName = images[i].l
                        val getYear = images[i].y
                        Log.d("nombre",getName)
                        var image: String

                        try {
                            image = getMovie.imageUrl
                            println(image)
                            nombres.add(getName.plus(" (" + getYear + ")"))
                            poster.add(image)
                        }
                        catch (e: Exception){ 
                            image = "https://plantillasdememes.com/img/plantillas/imagen-no-disponible01601774755.jpg"
                            println(image)
                            nombres.add(getName.plus(" (" + getYear + ")"))
                            poster.add(image)
                        }
                       
                        adapter.notifyDataSetChanged()
                    }

                    llamadaExitosa()
                }else{
                    showError()
                }
                hideKB()
            }

        }

    }

    private fun hideKB() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError(){
        Toast.makeText(this,"Ocurrio un error",Toast.LENGTH_SHORT).show()
    }
    private fun llamadaExitosa(){
        Toast.makeText(this,"todo salio bien",Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       if (!query.isNullOrEmpty()){
           searchBy(query)
       }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }
}