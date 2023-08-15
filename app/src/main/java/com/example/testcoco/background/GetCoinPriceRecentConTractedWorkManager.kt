package com.example.testcoco.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import timber.log.Timber


// 최근 거래된 코인 가격 내역을 가져오는 WorkManager

// 1. 사용자가 관심있어하는 코인 리스트를 가져와서
// 2. 관심있는 코인 각각의 가격병동 정보를 가져와서 (New API)
// 3. 관심있는 코인 각각의 가격병동 정보를 DB에 저장

class GetCoinPriceRecentConTractedWorkManager(val context : Context, workParameters : WorkerParameters)
    : CoroutineWorker(context, workParameters)
{
    override suspend fun doWork(): Result {

        Timber.d("dowork")

        return Result.success()
    }


}