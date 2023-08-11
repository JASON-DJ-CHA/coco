package com.example.testcoco.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testcoco.MainActivity
import com.example.testcoco.databinding.ActivitySelectBinding
import com.example.testcoco.view.adapter.SelectRVAdapter
import timber.log.Timber

class SelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBinding

    private val viewModel : SelectViewModel by viewModels()

    private lateinit var selectRVAdapter: SelectRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getCurrentCoinList()
        viewModel.currentPriceResult.observe(this, Observer {

            selectRVAdapter = SelectRVAdapter(this, it)

            binding.coinListRV.adapter = selectRVAdapter
            binding.coinListRV.layoutManager = LinearLayoutManager(this)

            Timber.d(it.toString())
        })


        binding.laterTextArea.setOnClickListener{
            viewModel.setUpFirstFlag()
            viewModel.savaSelectedCoinList(selectRVAdapter.selectedCoinList)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        viewModel.save.observe(this, Observer {
            if(it.equals("done")){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        })

    }
}