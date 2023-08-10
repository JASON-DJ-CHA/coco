package com.example.testcoco.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.testcoco.App

class MyDataStore {

    private val context = App.context()

    companion object{
        private val Context.dataStroe : DataStore<Preferences> by preferencesDataStore("user_pref")
    }

    private val mDataStore : DataStore<Preferences> = context.dataStroe

    private  val FIRST_FLAG = booleanPreferencesKey("FIRST_FLAG")


    // mainActivty로 이동시 True로 변경

    // 처음 접솓하는 유저가 아니면 True
    // 처음 접속하는 유저면 False


    suspend fun setupFirestData(){
        mDataStore.edit { preferences ->
        preferences[FIRST_FLAG] = true
        }
    }

    suspend fun getFirstData() : Boolean {
        var currentValue = false

        mDataStore.edit { preferences ->
            currentValue = preferences[FIRST_FLAG] ?: false
        }

        return currentValue
    }


}