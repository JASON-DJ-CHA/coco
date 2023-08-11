package com.example.testcoco.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testcoco.db.entity.InterestCoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestCoinDAO {

    // getAllData
    @Query("SELECT * FROM interset_coin_table")
    fun getAlldata() : Flow<List<InterestCoinEntity>>

    // Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(interestCoinEntity: InterestCoinEntity)
    //update
    // 사용자가 코인데이터를 선택했다가 다시 취소할 수도 있고, 반대로 선택 안된것을 선택할 수도 있게함
    @Update
    fun update(interestCoinEntity: InterestCoinEntity)

    // getSelectedCoinList -> 내가 관심있어한 코인데이터를 가져오는 것.
    // coin1 / coin2/ coin3 -> coin1 data / coin2 data / coin3 data
    @Query("SELECT * FROM interset_coin_table WHERE selected = :selected")
    fun getSelectedData(selected : Boolean = true) : List<InterestCoinEntity>


}