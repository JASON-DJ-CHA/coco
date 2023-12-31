package com.example.testcoco.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testcoco.R
import com.example.testcoco.dataModel.CurrentPriceResult
import kotlinx.coroutines.selects.select

class SelectRVAdapter(val context: Context, val coinPriceList : List<CurrentPriceResult>)
    :RecyclerView.Adapter<SelectRVAdapter.ViewHolder>(){

    // 내가 선택한 코인을 저장함
    val selectedCoinList = ArrayList<String>()

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        val coinName : TextView = view.findViewById(R.id.coinName)
        val coinPriceUpDown : TextView = view.findViewById(R.id.coinPriceUpDown)
        val likeImage : ImageView = view.findViewById(R.id.likeBtn)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.intro_coin_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int){

        holder.coinName.text = coinPriceList[position].coinName

        val fluctate_24H = coinPriceList[position].coinInfo.fluctate_24H
        if (fluctate_24H.contains("-"))
        {
            holder.coinPriceUpDown.text = "하락"
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#114fed"))
        }
        else
        {
            holder.coinPriceUpDown.text = "상승"
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#ed2e11"))
        }

        val likeLmage = holder.likeImage

        val currentCoin = coinPriceList[position].coinName

        //view 를 그려줄때
        if(selectedCoinList.contains(currentCoin))
        {
            //포함하면
            likeLmage.setImageResource(R.drawable.like_red)
        }
        else
        {
            //포함하지 않으면
            likeLmage.setImageResource(R.drawable.like_grey)
        }

        likeLmage.setOnClickListener{

            if(selectedCoinList.contains(currentCoin))
            {
                //포함하면
                selectedCoinList.remove(currentCoin)
                likeLmage.setImageResource(R.drawable.like_grey)

            }
            else
            {
                //포함하지 않으면
                selectedCoinList.add(currentCoin)
                likeLmage.setImageResource(R.drawable.like_red)

            }


        }


    }

    override fun getItemCount(): Int {
        return coinPriceList.size

    }



}