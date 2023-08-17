package com.example.testcoco.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.testcoco.db.entity.SelectedCoinPriceEntity
import com.example.testcoco.network.model.RecentCoinPriceList
import com.example.testcoco.repository.DBRepository
import com.example.testcoco.repository.NetWorkRepository
import timber.log.Timber
import java.util.Calendar
import java.util.Date


// 최근 거래된 코인 가격 내역을 가져오는 WorkManager

// 3. 관심있는 코인 각각의 가격병동 정보를 DB에 저장

class GetCoinPriceRecentConTractedWorkManager(val context : Context, workParameters : WorkerParameters)
    : CoroutineWorker(context, workParameters)
{

    private val dbRepository = DBRepository()
    private val netWorkRepository = NetWorkRepository()

    override suspend fun doWork(): Result {

        Timber.d("dowork")

        return Result.success()
    }
    // 1. 사용자가 관심있어하는 코인 리스트를 가져오기
    suspend fun getAllInterestSelectedCoinData()
    {
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        val timeStamp = Calendar.getInstance().time

        for (coinData in selectedCoinList){

            Timber.d(coinData.toString())
    // 2. 관심있는 코인 각각의 가격병동 정보를 가져와서 (New API)

            val recentCoinPriceList = netWorkRepository.getInterestCoinPriceData(coinData.coin_name)

            Timber.d(recentCoinPriceList.toString())

            saveSelectedCoinPrice(
                coinData.coin_name,
                recentCoinPriceList,
                timeStamp
            )
        }
    }

    fun saveSelectedCoinPrice(
        coinName : String,
        recentCoinPriceList: RecentCoinPriceList,
        timeStamp : Date
    ){

        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timeStamp
            )

        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)

    }


}