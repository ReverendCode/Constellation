package com.example.reverendcode.constellation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by ReverendCode on 3/16/18.
 */


class MyViewModel(var relays: LiveData<List<Relay>>? = null) : ViewModel() {

    fun getRelays(relayDao: RelayDao): LiveData<List<Relay>> {
        if (relays == null) {
            relays = relayDao.getAllRelays()
        }
        return relays!!
    }


}