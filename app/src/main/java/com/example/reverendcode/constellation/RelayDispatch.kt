package com.example.reverendcode.constellation

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by ReverendCode on 3/22/18.
 */

class RelayDispatch(private val relayDao: RelayDao, private val networkQueue: RequestQueue) {

    fun updateRelay(relay: Relay) {

         val url = "http://${relay.deviceId}/${relay.pinId}/${if (relay.currentValue) 1 else 0 }/"
         val resp = object : StringRequest(Request.Method.GET, url,
                 Response.Listener {
                     //update liveData
                     bg {
                         relayDao.update(parseResponse(it, relay))
                     }
                 },
                 Response.ErrorListener {
                     Log.e("volleyError",it.message ?: "No message")

                 }) {
             override fun getParams(): MutableMap<String, String> {
                 return mutableMapOf(Pair("relay",Gson().toJson(relay)))
             }
         }

         networkQueue.add(resp)
    }
    private fun parseResponse(words: String, relay: Relay) : Relay {
        val response = Gson().fromJson(words, RelayResponse::class.java)
        return relay.copy(
                currentValue = response.value == 0,
                pinId = response.pinId)
    }
}
data class RelayResponse(
        val value: Int,
        val pinId: Int
)