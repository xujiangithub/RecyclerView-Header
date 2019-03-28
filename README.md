# RecyclerView-Header
一个通用的header管理器，用法简便，其实主要目的是为了能自定义一个header，这个辅助用



### GoodPersimmonRefreshHeader

![ezgif.com-resize.gif](https://upload-images.jianshu.io/upload_images/12377851-3ed4439f04ca04cc.gif?imageMogr2/auto-orient/strip)


### 使用方式
自定义一个View，实现IHeader接口，里面有四个状态的回调方法，可以自行选择实现哪一个或者都实现。
```
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
```
