package com.example.reverendcode.constellation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var roomDb by Delegates.notNull<RoomDb>()
    private var relayAdapter : RelayRecyclerAdapter? = null
    private var networkQueue by Delegates.notNull<RequestQueue>()
    private var relayDispatch by Delegates.notNull<RelayDispatch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = null
        recyclerView.layoutManager = LinearLayoutManager(this)

        networkQueue = Volley.newRequestQueue(this)
        roomDb = Room.databaseBuilder(this, RoomDb::class.java,"foo")
                .fallbackToDestructiveMigration()
                .build()

        relayDispatch = RelayDispatch(roomDb.getRelayDao(), networkQueue)

        ViewModelProviders.of(this).get(MyViewModel::class.java)
                .getRelays(roomDb.getRelayDao()).observe(this, Observer { list ->
                    if (recyclerView.adapter == null) {
                        relayAdapter = RelayRecyclerAdapter(list ?: listOf(),this) {
                            Log.d("click!","message sent: $it")
                            relayDispatch.updateRelay(it)
                        }
                        recyclerView.adapter = relayAdapter
                    }
                    relayAdapter?.replaceItems(list)
                    relayAdapter?.notifyDataSetChanged()
        })

//        async(UI) {
//           bg { roomDb.run {
//               getRelayDao().insert(
//                       Relay(1L,"192.168.4.1","Thing Led","Test",2,false))
//                }
//           }
//            Log.d("async","this should be an update")
//        }
    }
}
