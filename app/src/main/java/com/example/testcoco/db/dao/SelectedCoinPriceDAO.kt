package com.example.testcoco.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testcoco.db.entity.SelectedCoinPriceEntity

@Dao
interface SelectedCoinPriceDAO {

    //getAllData
    @Query("SELECT * FROM selected_coin_price_table")
    fun getAllDate() : List<SelectedCoinPriceEntity>

    //insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun  insert(selectedCoinPriceEntity: SelectedCoinPriceEntity)

    // 하나의 코인에 대해서 저장된 정보를 가져오는 친구
    // BTC 15 30 45 -> List<BTC> -> 현재가격 15 30 45 어떻게 변화하였는디 DB에저장된 값과 비교하는 용도
    @Query("SELECT * FROM selected_coin_price_table WHERE coinName = :coinName")
    fun getOneCoinDate(coinName : String) : List<SelectedCoinPriceEntity>

}