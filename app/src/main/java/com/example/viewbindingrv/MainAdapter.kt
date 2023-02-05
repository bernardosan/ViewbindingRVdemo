package com.example.viewbindingrv

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.example.viewbindingrv.databinding.RecyclerviewItemBinding

class MainAdapter(var itemList: ArrayList<Item>, val listener: Listener, val isLayoutDistinct: Boolean = false):RecyclerView.Adapter<MainAdapter.MainViewHolder>(), View.OnTouchListener {


    inner class MainViewHolder (val itemBinding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bindItem(item: Item){

            itemBinding.titleTV.text = item.title
            itemBinding.priceTv.text = item.price.toString()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val task = itemList[position]
        holder.bindItem(task)

       /*  set different layout parameters
        if(isLayoutDistinct) {

            holder.itemBinding.flItem.setPadding(
                50.toDp(),
                5.toDp(),
                200.toDp(),
                50.toDp()
            )
        }
        */

        holder.itemBinding.flItem.tag = position
        holder.itemBinding.flItem.setOnTouchListener(this)
        holder.itemBinding.flItem.setOnDragListener(DragListener(listener))


    }

    override fun getItemCount(): Int {
        return itemList.size
    }



    fun getList(): ArrayList<Item> {
        return itemList
    }

    fun getTask(position: Int): Item {
        return itemList[position]
    }

    fun removeItem(position: Int){
       if(position>=0) itemList.removeAt(position)
    }

    fun updateList(taskListR: ArrayList<Item>){
        itemList = taskListR
    }


    fun getDragInstance(): DragListener? {
        return if (listener != null) {
            DragListener(listener)
        } else {
            Log.e("ListAdapter", "Listener wasn't initialized!")
            null
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = DragShadowBuilder(v)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v?.startDragAndDrop(data, shadowBuilder, v, 0)
                } else {
                    v?.startDrag(data, shadowBuilder, v, 0)
                }
                return true
            }
        }
        return false
    }

    private fun Int.toDp(): Int =
        (this/Resources.getSystem().displayMetrics.density).toInt()

    private fun Int.toPx(): Int =
        (this * Resources.getSystem().displayMetrics.density).toInt()
}
