# 可定制化的RecyclerView-Header
一个通用的header管理器，用法简便，其实主要目的是为了能自定义一个header，这个辅助用



### GoodPersimmonRefreshHeader

![ezgif.com-resize.gif](https://upload-images.jianshu.io/upload_images/12377851-3ed4439f04ca04cc.gif?imageMogr2/auto-orient/strip)


### 使用方式
自定义一个View，实现IHeader接口，里面有四个状态的回调方法，可自行选择实现哪一个或者都实现，在实现的方法内部调用super.当前fun名。
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
### xml中的布局方式
```
<!--最外层使用HeaderLayout-->
<com.ydl.recycle.recyclerviewtest.widget.HeaderLayout
        android:id="@+id/header_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <!--第一个view为自定义的headerView-->
        <com.ydl.recycle.recyclerviewtest.header.GoodPersimmonRefreshHeader
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
        </com.ydl.recycle.recyclerviewtest.header.GoodPersimmonRefreshHeader>
        <!--第二个view就是正常的recyclerview了-->
        <android.support.v7.widget.RecyclerView
            android:id="@+id/recycle"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        </android.support.v7.widget.RecyclerView>
    </com.ydl.recycle.recyclerviewtest.widget.HeaderLayout>
```

### 代码中的使用
```
//设置一个刷新得回调监听
header_layout.setListener(object : HeaderLayout.OnRefreshListener {
            override fun onRefreshCallBack() {
                //...一系列耗时操作之后调用
                header_layout.completeLoad()
            }
        })
```

