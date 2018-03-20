package com.example.reverendcode.constellation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by ReverendCode on 3/16/18.
 */


class MyViewModel : ViewModel() {
    private var relays: LiveData<List<Relay>>? = null

    fun getRelays(): LiveData<List<Relay>> {
        if (relays == null) {
            loadRelays()
        }
        return relays!!
    }

    private fun loadRelays() {
        //do async call here.
    }
}