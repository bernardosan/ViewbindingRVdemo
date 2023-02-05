package com.example.viewbindingrv

import java.util.ArrayList

interface Listener {
    fun setEmptyListTop(visibility: Boolean)
    fun setEmptyListBottom(visibility: Boolean)
    fun updateCartTotal(price: Float)
}