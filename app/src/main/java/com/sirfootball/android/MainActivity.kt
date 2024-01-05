package com.sirfootball.android

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sirfootball.android.client.ApiClient
import com.sirfootball.android.databinding.ActivityMainBinding
import com.sirfootball.android.model.LoadUserTeamsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var responseText: TextView

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        responseText = findViewById(R.id.myResponseText)
        Log.i("MikeTesting","WTF is going on")
        val call = ApiClient.apiService.getTeams()
        call.enqueue(object : Callback<LoadUserTeamsResponse> {
            override fun onResponse(call: Call<LoadUserTeamsResponse>, response: Response<LoadUserTeamsResponse>) {
                Log.i("MikeTesting","WTF22222 is going on")
                if (response.isSuccessful) {
                    val post = response.body()
                    responseText.text = post!!.detail
                    // Handle the retrieved post data
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<LoadUserTeamsResponse>, t: Throwable) {

                Log.i("MikeTesting","We failed!!! $t.message")
                // Handle failure
            }
        })

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_draft, R.id.navigation_add,
            R.id.navigation_questions, R.id.navigation_settings))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}