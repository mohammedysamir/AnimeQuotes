package com.myasser.testapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //todo: define retrofit builder, fetch quote and send them to other activities
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.searchButton ->{
                //todo: implement search function either by anime name or character name or both
            }
            R.id.searchRandomButton ->{
                //todo: implement fetch random quote function
            }
        }
    }
}
/*
* 1. Create model class that will be populated with response
* 2. User has 3 options to get a quote -> by character name, by anime name, randomly
* 3. Quotes will be displayed in a recycler view if its by character or anime name
* 4. Create button for Random quote and another for search that will utilize anime/character name.
* */