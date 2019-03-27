package com.ydl.recycle.recyclerviewtest.header

/**
 * @author <a href="https://www.jianshu.com/u/c1e5310dd724">xujian</a>
 * @描述:
 * @Copyright Copyright (c) 2019
 * @Company 壹点灵
 * @date 2019/03/27
 */
interface IHeader {

    private var state: Int    //初始时候初始化为下拉刷新状态
        get() = state
        set(value) { value }

    companion object {
        const val TYPE_IS_LOADING = 100000  //正在加载
        const val TYPE_UP_TO_LOADING = 100001  //松手刷新
        const val TYPE_PULL_TO_LOADING = 100002  //下拉刷新
        const val TYPE_COMPLETE_LOADING = 100003  //刷新完成
    }

    /**
     * 正在刷新实现方法
     */
    fun isLoading() {
        state = TYPE_IS_LOADING
    }

    /**
     * 下拉刷新实现方法
     */
    fun pullToLoad() {
        state = TYPE_PULL_TO_LOADING
    }

    /**
     * 释放刷新实现方法
     */
    fun releaseToLoad() {
        state = TYPE_UP_TO_LOADING
    }

    /**
     * 刷新完成实现方法
     */
    fun completeLoad() {
        state = TYPE_COMPLETE_LOADING
    }
}