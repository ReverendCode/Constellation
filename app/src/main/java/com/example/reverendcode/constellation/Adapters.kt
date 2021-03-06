package com.example.reverendcode.constellation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.relay_view.view.*
import kotlinx.android.synthetic.main.room_view.view.*

/**
 * Created by ReverendCode on 3/23/18.
 */


class RelayRecyclerAdapter(private var relayList : List<Relay>,
                           private val context: Context,
                           private val dispatch: (relay: Relay) -> Unit)
    : RecyclerView.Adapter<RelayRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.relay_view,parent,false))
    }

    override fun getItemCount(): Int {
        return relayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val relay = relayList[position]
        if (holder != null) {
            holder.switch.text = relay.name
            holder.roomName.text = relay.roomName
            holder.switch.isChecked = relay.currentValue
            holder.switch.setOnClickListener {
                dispatch(relay)
            }
        }
    }

    fun replaceItems(list: List<Relay>?) {
        Log.d("replaceItems","List: $list")
        relayList = list?.toList() ?: relayList
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val switch = view.relay_name!!
        val roomName = view.room_name!!
    }

}

class HouseRoomRecyclerAdapter(private var roomList : List<HouseRoom>,
                          private val context: Context) : RecyclerView.Adapter<HouseRoomRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_view,parent,false))
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val name = roomList[position].roomName
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val roomName = view.text_room_name
        val roomTemp = view.text_room_temp
        val switch1 = view.switch_room_1
        val switch2 = view.switch_room_2
        val switch3 = view.switch_room_3
        val switch4 = view.switch_room_4
        val switch5 = view.switch_room_5

    }
}
