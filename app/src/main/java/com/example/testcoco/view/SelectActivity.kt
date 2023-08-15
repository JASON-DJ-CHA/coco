package com.example.testcoco.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.testcoco.background.GetCoinPriceRecentConTractedWorkManager
import com.example.testcoco.view.main.MainActivity
import com.example.testcoco.databinding.ActivitySelectBinding
import com.example.testcoco.view.adapter.SelectRVAdapter
import timber.log.Timber
import java.util.concurrent.TimeUnit

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

                // 가장 처음으로 우리가 저장한 코인의 정보가 저장되는 시점


            }

        })
    }

    private fun saveInterestCoinDataPeriodic() {
        val myWork = PeriodicWorkRequest.Builder(
            GetCoinPriceRecentConTractedWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "GetCoinPriceRecentConTractedWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )
    }

}