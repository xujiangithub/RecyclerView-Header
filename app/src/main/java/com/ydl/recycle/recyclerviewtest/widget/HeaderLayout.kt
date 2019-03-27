package com.ydl.recycle.recyclerviewtest.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.ViewTreeObserver
import com.ydl.recycle.recyclerviewtest.header.IHeader


/**
 * @author <a href="https://www.jianshu.com/u/c1e5310dd724">xujian</a>
 * @描述:  通用recyclerview外部header管理类
 * @Copyright Copyright (c) 2019
 * @Company 壹点灵
 * @date 2019/03/26
 */
class HeaderLayout: LinearLayout, View.OnTouchListener {

    private var header: IHeader? = null
    private var headerView: View? = null

    private var mListener: OnRefreshListener? = null

    private var mContext: Context? = null
    private var mInflate: LayoutInflater? = null
    private var headerTopMargin: Int? = null
    private var headerParams: MarginLayoutParams? = null
    private var recyclerview: RecyclerView? = null
    private var canPull: Boolean = false //是否能够下拉
    private var smallestDistance = 15 //最小响应下拉距离

    private var down_x: Float? = null  //点击时的x
    private var down_y: Float? = null  //点击时的y
    private var move_x: Float? = null  //移动时的x
    private var move_y: Float? = null  //移动时的y
    private var up_x: Float? = null  //抬起时的x
    private var up_y: Float? = null  //抬起时的y

    private var hasDown: Boolean = false //用于解决一次动作中下滑再上滑隐藏header之后，第二次触摸事件down事件无法获取问题

    constructor(context: Context): super(context) {
        this.mContext = context
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.mContext = context
        initView()
    }

    private fun initView() {
        orientation = VERTICAL
        mInflate = LayoutInflater.from(mContext)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        if (headerView == null) {
            headerView = getChildAt(0)
            header = headerView as IHeader

            headerParams = headerView?.layoutParams as MarginLayoutParams

            val vto2 = headerView?.viewTreeObserver
            vto2?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    headerView?.viewTreeObserver!!.removeGlobalOnLayoutListener(this)

                    headerParams?.topMargin = -headerView?.height!!
                    headerTopMargin = headerParams?.topMargin
                    headerView?.layoutParams = headerParams
                }
            })
        }

        if (recyclerview == null) {
            recyclerview = getChildAt(1) as RecyclerView
            recyclerview?.setOnTouchListener(this)
        }

    }

    /**
     * RecyclerView的点击监听
     */
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        countCanByPulled()
        if (canPull) {
            val action = event.action
            if (action == MotionEvent.ACTION_DOWN) {
                Log.e("action", "down")
                down_y = event.rawY
                hasDown = true
            }else if (action == MotionEvent.ACTION_MOVE && hasDown) {
                Log.e("action", "move")
                move_y = event.rawY
                var scrollDiatance = move_y!! - down_y!!
                if (scrollDiatance > 0 && scrollDiatance > smallestDistance) {
                    setTopMargin(scrollDiatance.toInt()/2)
                }
                if (scrollDiatance > headerView?.height!! * 2) {
                    header?.releaseToLoad()
                }else {
                    header?.pullToLoad()
                }

            }else if (action == MotionEvent.ACTION_UP && hasDown) {
                up_y = event.rawY
                /**
                 * 达到下拉刷新限度
                 */
                if (up_y!! - down_y!! > headerView!!.height * 2) {
                    setTopMargin(headerView!!.height)
                    mListener?.onRefreshCallBack()
                    header?.isLoading()
                }else {
                    setTopMargin(0)
                }

                Log.e("action", "up")
                canPull = false
                hasDown = false
            }
        }
        return false
    }

    /**
     * 计算是否能下拉
     */
    private fun countCanByPulled() {
        if (canPull) return
        val layoutManager = recyclerview?.layoutManager as LinearLayoutManager
        val position = layoutManager.findFirstCompletelyVisibleItemPosition()

        //如果第一个完全显示的item的下标为0，那么就已经置顶了
        canPull = position == 0
    }


    /**
     * scrollHeight为0+， 代表recyclerview置顶之后的滑动距离
     */
    fun setTopMargin(scrollHeight: Int) {
        headerParams?.topMargin = -headerView?.height!! + scrollHeight
        headerTopMargin = headerParams?.topMargin
        headerView?.layoutParams = headerParams
    }

    /**
     * 外部设置刷新完成
     */
    fun completeLoad() {
        header?.completeLoad()
        setTopMargin(0)
    }


    fun setListener(listener: OnRefreshListener) {
        this.mListener = listener
    }

    interface OnRefreshListener {
        fun onRefreshCallBack()
    }
}