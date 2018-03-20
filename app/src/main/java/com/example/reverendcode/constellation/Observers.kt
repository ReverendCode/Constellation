package com.example.reverendcode.constellation

import android.arch.lifecycle.Observer

/**
 * Created by ReverendCode on 3/20/18.
 */

class RelayDispatch : Observer<List<Relay>> {

    override fun onChanged(relays: List<Relay>?) {

        //This needs to dispatch a POST request to the
        // appropriate node/relay based on the diff between relays and the old state.

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}