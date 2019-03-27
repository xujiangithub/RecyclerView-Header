package com.ydl.recycle.recyclerviewtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ydl.recycle.recyclerviewtest.adapter.StringAdapter
import com.ydl.recycle.recyclerviewtest.widget.HeaderLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job()

        var list: ArrayList<String> = ArrayList()
        for (i in 0..20) {list.add("test$i")}

        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = StringAdapter(this, list)

        header_layout.setListener(object : HeaderLayout.OnRefreshListener {
            override fun onRefreshCallBack() {
                launch(Dispatchers.IO) {

                    delay(2000)

                    withContext(Dispatchers.Main) {
                        header_layout.completeLoad()
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        //当acitivity结束之后,我们不需要再请求网络了,结束当前协程
        job.cancel()
    }
}
