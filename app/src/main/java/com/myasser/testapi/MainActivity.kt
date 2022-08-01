package com.myasser.testapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
/*
* 1. Create model class that will be populated with response
* 2. User has 3 options to get a quote -> by character name, by anime name, randomly
* 3. Quotes will be displayed in a recycler view if its by character or anime name
* 4. Create button for Random quote and another for search that will utilize anime/character name.
* */