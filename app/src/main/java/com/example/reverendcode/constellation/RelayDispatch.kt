package com.example.reverendcode.constellation

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

/**
 * Created by ReverendCode on 3/22/18.
 */

class RelayDispatch(private val relayDao: RelayDao, private val networkQueue: RequestQueue) {

    fun updateRelay(relay: Relay) {

         val url = relay.deviceId
         val resp = object : StringRequest(Request.Method.GET, url,
                 Response.Listener {
                     //update liveData
                     relayDao.update(parseResponse(it))
                 },
                 Response.ErrorListener {
                     //send error message?
                     Log.e("volleyError",it.message)

                 }) {
             override fun getParams(): MutableMap<String, String> {
                 return mutableMapOf(Pair("relay",Gson().toJson(relay)))
             }
         }

         networkQueue.add(resp)
    }
    private fun parseResponse(words: String) : Relay {
        return Gson().fromJson(words,Relay::class.java)
    }
}