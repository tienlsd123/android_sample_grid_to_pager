package com.example.movepagersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.movepagersample.fragment.GridFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_POSITION, 0);
            return
        }
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, GridFragment()).commit()

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt(CURRENT_POSITION, currentPosition)
    }

    companion object {
        var currentPosition: Int = 0
        const val CURRENT_POSITION = "current_position"
    }
}