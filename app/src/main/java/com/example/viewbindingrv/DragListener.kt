package com.example.viewbindingrv

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener

class DragListener internal constructor(private val listener: Listener) : OnDragListener {
    private var isDropped = false
    private var isStarted = false


    @SuppressLint("NotifyDataSetChanged")
    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            /*DragEvent.ACTION_DRAG_LOCATION -> {
                isStarted = true
                var positionTarget = -1
                val viewSource = event.localState as View?
                val viewId = v.id
                val flItem: Int = R.id.fl_item
                val tvEmptyListTop: Int = R.id.tv_empty_list_1
                val tvEmptyListBottom: Int = R.id.tv_empty_list_2
                val rvTop: Int = R.id.taskRv
                val rvBottom: Int = R.id.taskRv2
                when (viewId) {
                    flItem, tvEmptyListTop, tvEmptyListBottom, rvTop, rvBottom -> {
                        val target: RecyclerView
                        when (viewId) {
                            rvTop -> target =
                                v.rootView.findViewById<View>(rvTop) as RecyclerView
                            rvBottom -> target =
                                v.rootView.findViewById<View>(rvBottom) as RecyclerView
                            else -> {
                                target = v.parent as RecyclerView
                                positionTarget = v.tag as Int
                            }
                        }
                        if (viewSource != null) {
                            val source: RecyclerView? = viewSource.parent as RecyclerView?
                            val adapterSource: MainAdapter? = source?.adapter as MainAdapter?
                            val positionSource = viewSource.tag as Int
                            val sourceId = source?.id
                            //val item: Item = adapterSource.getTask(positionSource)
                            //val listSource: ArrayList<Item> = adapterSource.getList()
                            //listSource.removeAt(positionSource)
                            val adapterTarget: MainAdapter? = target?.adapter as MainAdapter?
                            //val customListTarget: ArrayList<Item> = adapterTarget.getList()

                            if (!isDropped) {

                                if (positionTarget < adapterTarget?.itemCount!! && if(adapterSource == adapterTarget) positionSource != positionTarget else true)  {
                                    //customListTarget.add(positionTarget, item)
                                    adapterTarget?.notifyItemMoved(
                                        adapterTarget.itemCount - 1,
                                        positionTarget
                                    )
                                }

                                /* if(sourceId == rvTop && target.id != sourceId){
                               listener.updateCartTotal(item.price)
                            } else if(sourceId == rvBottom && target.id != sourceId){
                               listener.updateCartTotal(item.price.times(-1))
                           }*/
                            }
                        } else if (isDropped){


                        }
                    }
                }
            }
           */



            DragEvent.ACTION_DROP -> {
                isDropped = true
                var positionTarget = -1
                val viewSource = event.localState as View?
                val viewId = v.id
                val flItem: Int = R.id.fl_item
                val tvEmptyListTop: Int = R.id.tv_empty_list_1
                val tvEmptyListBottom: Int = R.id.tv_empty_list_2
                val rvTop: Int = R.id.taskRv
                val rvBottom: Int = R.id.taskRv2
                when (viewId) {
                    flItem, tvEmptyListTop, tvEmptyListBottom, rvTop, rvBottom -> {
                        val target: RecyclerView
                        when (viewId) {
                            tvEmptyListTop, rvTop -> target =
                                v.rootView.findViewById<View>(rvTop) as RecyclerView
                            tvEmptyListBottom, rvBottom -> target =
                                v.rootView.findViewById<View>(rvBottom) as RecyclerView
                            else -> {
                                target = v.parent as RecyclerView
                                positionTarget = v.tag as Int
                            }
                        }
                        if (viewSource != null) {
                            val source: RecyclerView = viewSource.parent as RecyclerView
                            val adapterSource: MainAdapter = source.adapter as MainAdapter
                            val positionSource = viewSource.tag as Int
                            val sourceId = source.id
                            val item: Item = adapterSource.getTask(positionSource)
                            val listSource: ArrayList<Item> = adapterSource.getList()
                            listSource.removeAt(positionSource)
                            adapterSource.updateList(listSource)
                            adapterSource.notifyDataSetChanged()
                            val adapterTarget: MainAdapter = target.adapter as MainAdapter
                            val customListTarget: ArrayList<Item> = adapterTarget.getList()
                            if (positionTarget >= 0) {
                                customListTarget.add(positionTarget, item)
                                adapterTarget.updateList(customListTarget)
                                adapterTarget.notifyItemInserted(positionTarget)
                                adapterSource.notifyDataSetChanged()
                            } else {
                                customListTarget.add(item)
                                adapterTarget.updateList(customListTarget)
                                adapterTarget.notifyItemInserted(positionTarget)
                                adapterSource.notifyDataSetChanged()
                            }

                            if (sourceId == rvBottom && adapterSource.itemCount < 1) {
                                listener.setEmptyListBottom(true)
                            }
                            if (viewId == tvEmptyListBottom) {
                                listener.setEmptyListBottom(false)
                            }
                            if (sourceId == rvTop && adapterSource.itemCount < 1) {
                                listener.setEmptyListTop(true)
                            }
                            if (viewId == tvEmptyListTop) {
                                listener.setEmptyListTop(false)
                            }

                            if(sourceId == rvTop && target.id != sourceId){
                                listener.updateCartTotal(item.price)
                            } else if(sourceId == rvBottom && target.id != sourceId){
                                listener.updateCartTotal(item.price.times(-1))
                            }
                        }
                    }
                }
            }
        }
        if (!isDropped && event.localState != null) {
            (event.localState as View).visibility = View.VISIBLE
        }

        return true
    }

}
