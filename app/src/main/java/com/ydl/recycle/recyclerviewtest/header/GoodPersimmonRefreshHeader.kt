package com.ydl.recycle.recyclerviewtest.header

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.ydl.recycle.recyclerviewtest.R
import kotlinx.android.synthetic.main.header_layout.view.*
import kotlinx.coroutines.*
import java.lang.Math.abs
import kotlin.coroutines.CoroutineContext

/**
 * @author <a href="https://www.jianshu.com/u/c1e5310dd724">xujian</a>
 * @描述:
 * @Copyright Copyright (c) 2019
 * @Company 壹点灵
 * @date 2019/03/27
 */
class GoodPersimmonRefreshHeader: LinearLayout, IHeader, CoroutineScope {

    private var job: Job? = null

    companion object {
        const val PI = 3.141592653589793
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var mContext: Context? = null

    constructor(context: Context): super(context) {
        this.mContext = context
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.mContext = context
        initView()
    }

    private fun initView() {
        orientation = HORIZONTAL
        View.inflate(mContext, R.layout.header_layout, this)
    }

    override fun isLoading() {
        super.isLoading()
        header_txt.text = "正在刷新"
        val level = 20 //一次动画repeat次数
        job = launch {
            try {
                repeat(100000) {
                    withContext(Dispatchers.Main) {
                        Log.i("it:", it.toString())
                        if (it % level in 0..10) {
                            val params = icon_1.layoutParams as MarginLayoutParams
                            params.bottomMargin = (30 * abs(Math.sin(it * (PI / 10)))).toInt()
                            icon_1.layoutParams = params
                        }

                        if (it % level in 3..13) {
                            val params = icon_2.layoutParams as MarginLayoutParams
                            params.bottomMargin = (30 * abs(Math.sin((it - 3) * (PI / 10)))).toInt()
                            icon_2.layoutParams = params
                        }

                        if (it % level in 6..16) {
                            val params = icon_3.layoutParams as MarginLayoutParams
                            params.bottomMargin = (30 * abs(Math.sin((it - 6) * (PI / 10)))).toInt()
                            icon_3.layoutParams = params
                        }

                        if (it % level in 9..19) {
                            val params = icon_4.layoutParams as MarginLayoutParams
                            params.bottomMargin = (30 * abs(Math.sin((it - 9) * (PI / 10)))).toInt()
                            icon_4.layoutParams = params
                        }
                    }
                    delay(100)
                }
            }finally {
                val params = icon_1.layoutParams as MarginLayoutParams
                params.bottomMargin = 0
                icon_1.layoutParams = params
                val params1 = icon_2.layoutParams as MarginLayoutParams
                params1.bottomMargin = 0
                icon_2.layoutParams = params1
                val params2 = icon_3.layoutParams as MarginLayoutParams
                params2.bottomMargin = 0
                icon_3.layoutParams = params2
                val params3 = icon_4.layoutParams as MarginLayoutParams
                params3.bottomMargin = 0
                icon_4.layoutParams = params3
//
//                (icon_1.layoutParams as MarginLayoutParams).bottomMargin = 0
//
//                (icon_2.layoutParams as MarginLayoutParams).bottomMargin = 0
//
//                (icon_3.layoutParams as MarginLayoutParams).bottomMargin = 0
//
//                (icon_4.layoutParams as MarginLayoutParams).bottomMargin = 0
            }
        }
    }

    override fun pullToLoad() {
        super.pullToLoad()
        header_txt.text = "下拉刷新"
        job?.cancel()
    }

    override fun releaseToLoad() {
        super.releaseToLoad()
        header_txt.text = "释放刷新"
        job?.cancel()
    }

    override fun completeLoad() {
        super.completeLoad()
        header_txt.text = "刷新完成"
        job?.cancel()
    }

}