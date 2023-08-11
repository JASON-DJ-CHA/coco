package com.example.testcoco.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcoco.dataModel.CurrentPrice
import com.example.testcoco.dataModel.CurrentPriceResult
import com.example.testcoco.dataStore.MyDataStore
import com.example.testcoco.repository.NetWorkRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class SelectViewModel : ViewModel() {

    private val netWorkRepository = NetWorkRepository()

    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    // 데이터 변화를 관찰 LiveData
    private val _currentPreceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult : LiveData<List<CurrentPriceResult>>
        get() = _currentPreceResult

    fun getCurrentCoinList() = viewModelScope.launch {

        val result = netWorkRepository.getCurrentCoinList()

        currentPriceResultList = ArrayList()


        for(coin in result.data){


            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)

                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)

                currentPriceResultList.add(currentPriceResult)
            }catch (e : java.lang.Exception){
                Timber.d(e.toString())

            }
        }

        _currentPreceResult.value = currentPriceResultList


    }

    fun setUpFirstFlag() = viewModelScope.launch {
        MyDataStore().setupFirestData()
    }

    //DB에 데이터 저장
    fun savaSelectedCoinList() = viewModelScope.launch (Dispatchers.IO){

        // 1. 전체 코인 데이터를 가져와서
        for(coin in currentPriceResultList){

        }
        // 2. 내가 선택한 코인인지 아닌지 구분해서

        // 3. 저장

    }

}