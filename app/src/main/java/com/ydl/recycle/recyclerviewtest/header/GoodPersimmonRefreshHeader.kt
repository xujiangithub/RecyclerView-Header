package com.ydl.recycle.recyclerviewtest.header

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.ydl.recycle.recyclerviewtest.R
import kotlinx.android.synthetic.main.header_layout.view.*

/**
 * @author <a href="https://www.jianshu.com/u/c1e5310dd724">xujian</a>
 * @描述:
 * @Copyright Copyright (c) 2019
 * @Company 壹点灵
 * @date 2019/03/27
 */
class GoodPersimmonRefreshHeader: LinearLayout, IHeader {

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
    }

    override fun pullToLoad() {
        super.pullToLoad()
        header_txt.text = "下拉刷新"
    }

    override fun releaseToLoad() {
        super.releaseToLoad()
        header_txt.text = "释放刷新"
    }

    override fun completeLoad() {
        super.completeLoad()
        header_txt.text = "刷新完成"
    }

}