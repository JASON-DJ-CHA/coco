package com.example.testcoco.repository

import com.example.testcoco.App
import com.example.testcoco.db.CoinPriceDatabase
import com.example.testcoco.db.entity.InterestCoinEntity
import com.example.testcoco.db.entity.SelectedCoinPriceEntity

class DBRepository {

    val context = App.context()
    val db = CoinPriceDatabase.getDatabase(context)

    //InterestCoin

    //전체 코인데이터 가져오기
    fun getAllInterestCoinData() = db.interestCoinDAO().getAlldata()

    //코인 데이터 넣기

    fun insertInterestcoinData(interestCoinEntity: InterestCoinEntity) = db.interestCoinDAO().insert(interestCoinEntity)

    // 코인데이터 업데이트
    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = db.interestCoinDAO().update(interestCoinEntity)

    // 사용자가 관심있어한 코인만 가져오기
    fun getAllInterestSelectedCoinData() = db.interestCoinDAO().getSelectedData()

    // coinPrice
    fun getAllCoinPriceData() = db.selectedCoinDAO().getAllDate()

    fun insertCoinPriceData(selectedCoinPriceEntity: SelectedCoinPriceEntity) = db.selectedCoinDAO().insert(selectedCoinPriceEntity)

    fun getOneSelectedCoinData(coinName : String) = db.selectedCoinDAO().getOneCoinDate(coinName)

}