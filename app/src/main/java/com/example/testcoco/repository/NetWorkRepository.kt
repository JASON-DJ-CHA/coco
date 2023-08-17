package com.example.testcoco.repository

import com.example.testcoco.network.Api
import com.example.testcoco.network.RetrofitInstance

class NetWorkRepository {

    private val client = RetrofitInstance.getInstance().create(Api::class.java)

    suspend fun getCurrentCoinList() = client.getCurrentCoinList()

    suspend fun getInterestCoinPriceData(coin : String)  = client.getRecentCoinPrice(coin)

}