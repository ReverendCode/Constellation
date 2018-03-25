package com.example.reverendcode.constellation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.relay_view.view.*

/**
 * Created by ReverendCode on 3/23/18.
 */


class RelayRecyclerAdapter(private val relayList : List<Relay>,
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

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val switch = view.relay_name!!
        val roomName = view.room_name!!
    }
}
