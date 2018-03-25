package com.example.reverendcode.constellation

import android.support.v7.util.DiffUtil

/**
 * Created by ReverendCode on 3/23/18.
 */

class SatelliteDiffUtil<t : Satellite>(
        private val oldList: List<t>,
        private val newList: List<t>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].uid == newList[newItemPosition].uid
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].currentValue == oldList[oldItemPosition].currentValue
    }

}