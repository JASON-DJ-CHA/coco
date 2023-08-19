package com.example.testcoco.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.testcoco.dataModel.UpDownDataSet
import com.example.testcoco.db.entity.InterestCoinEntity
import com.example.testcoco.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val dbRepository = DBRepository()

    lateinit var selectedCoinList : LiveData<List<InterestCoinEntity>>

    private val _arr15min = MutableLiveData<List<UpDownDataSet>>()
    val arr15min : LiveData<List<UpDownDataSet>>
        get() = _arr15min

    private val _arr30min = MutableLiveData<List<UpDownDataSet>>()
    val arr30min : LiveData<List<UpDownDataSet>>
        get() = _arr30min

    private val _arr45min = MutableLiveData<List<UpDownDataSet>>()
    val arr45min : LiveData<List<UpDownDataSet>>
        get() = _arr45min

    // CoinListFragment



    fun getAllInterestCoinData() = viewModelScope.launch {

        val coinList = dbRepository.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList

    }

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = viewModelScope.launch(Dispatchers.IO) {

        if(interestCoinEntity.selected)
        {
            interestCoinEntity.selected = false
        }
        else
        {
            interestCoinEntity.selected = true
        }

        dbRepository.updateInterestCoinData(interestCoinEntity)

    }

    // PreceChangeFragment
    // 3. 시간대마다 어떻게 변경되었는지 로직 작성
    fun getAllSelectedCoinData() = viewModelScope.launch( Dispatchers.IO ) {
        // 1. 선택한 코인리스트 가져오기
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        val arr15min = ArrayList<UpDownDataSet>()
        val arr30min = ArrayList<UpDownDataSet>()
        val arr45min = ArrayList<UpDownDataSet>()


        // 2. 반복문사용
        for (data in selectedCoinList){

            // 3. 저장된 코인가격 리스트 가져오기
            val coinName = data.coin_name // 만약 coin이름이 BTC일 경우
            // [ 0 1 2 3 4 ] 가장 마지막에 저장된 값이 최신 값 = reversed()사용
            val oneCoinData = dbRepository.getOneSelectedCoinData(coinName).reversed()

            val size = oneCoinData.size
            if (size > 1){
                // DB에 값이 2개이상
                // 현재와 15분전 가격비교를 위해서는 데이터 2개 필요
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[1].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr15min.add(upDownDataSet)

            }
            if (size > 2 ){
                // DB에 값이 3개이상
                // 현재와 30분전 가격비교를 위해서는 데이터 2개 필요
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[2].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr30min.add(upDownDataSet)

            }
            if (size > 3 ){
                // DB에 값이 4개이상
                // 현재와  45분전 가격비교를 위해서는 데이터 2개 필요
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[3].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr45min.add(upDownDataSet)

            }
        }

        withContext(Dispatchers.Main) {
            _arr15min.value = arr15min
            _arr30min.value = arr30min
            _arr45min.value = arr45min
        }


    }


}