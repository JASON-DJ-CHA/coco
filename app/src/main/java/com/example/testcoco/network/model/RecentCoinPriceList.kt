package com.example.testcoco.network.model

import com.example.testcoco.dataModel.RecentPriceData

data class RecentCoinPriceList (
    val status : String,
    val data : List<RecentPriceData>

)


