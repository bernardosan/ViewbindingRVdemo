package com.example.viewbindingrv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewbindingrv.ItemList.itemList
import com.example.viewbindingrv.databinding.ActivityMainBinding
import java.util.ArrayList
import kotlin.math.roundToInt
import androidx.recyclerview.widget.ItemTouchHelper

import android.R

import androidx.recyclerview.widget.RecyclerView




class MainActivity : AppCompatActivity(), Listener {

    private var binding: ActivityMainBinding? = null
    private var topList: ArrayList<Item> = ArrayList()
    private var bottomList: ArrayList<Item> = ArrayList()
    private var mPrice: Float = 0f

    companion object{
        const val TOP_LIST = "toplist"
        const val BOTTOM_LIST = "botlist"
        const val PRICE = "price"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if(savedInstanceState != null){
            topList = savedInstanceState.getSerializable(TOP_LIST) as ArrayList<Item>
            bottomList = savedInstanceState.getSerializable(BOTTOM_LIST) as ArrayList<Item>
            mPrice = savedInstanceState.getFloat(PRICE)
        } else {
            for(i in itemList) {
                topList.add(i)
            }
        }

        initTopRecyclerView()
        initBottomRecyclerView()

        binding?.tvTotalPrice?.text = mPrice.toString()

        binding?.tvEmptyList1?.visibility = View.GONE
        binding?.tvEmptyList2?.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(TOP_LIST,topList)
        outState.putSerializable(BOTTOM_LIST,bottomList)
        outState.putFloat(PRICE, mPrice)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun initTopRecyclerView() {
        binding?.taskRv?.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )

        val topListAdapter = MainAdapter(topList, this, true)
        binding?.taskRv?.adapter = topListAdapter
        binding?.taskRv?.setHasFixedSize(true)
        binding?.tvEmptyList1?.setOnDragListener(topListAdapter.getDragInstance())
        binding?.taskRv?.setOnDragListener(topListAdapter.getDragInstance())

    }

    private fun initBottomRecyclerView() {
        binding?.taskRv2?.layoutManager = GridLayoutManager(
            this, 2, GridLayoutManager.VERTICAL, false
        )

        val botListAdapter = MainAdapter(bottomList, this)
        binding?.taskRv2?.adapter = botListAdapter
        binding?.tvEmptyList2?.setOnDragListener(botListAdapter.getDragInstance())
        binding?.taskRv2?.setOnDragListener(botListAdapter.getDragInstance())
    }

    override fun setEmptyListTop(visibility: Boolean) {
        binding?.tvEmptyList1?.visibility = if (visibility) View.VISIBLE else View.GONE
        binding?.taskRv?.visibility = if (visibility) View.INVISIBLE else View.VISIBLE
    }

    override fun setEmptyListBottom(visibility: Boolean) {
        binding?.tvEmptyList2?.visibility = if (visibility) View.VISIBLE else View.GONE
        binding?.taskRv2?.visibility = if (visibility) View.INVISIBLE else View.VISIBLE
    }

    override fun updateCartTotal(price: Float) {
        mPrice += price
        binding?.tvTotalPrice?.text = mPrice.times(100.0).roundToInt().div(100.0).toString()
    }

}