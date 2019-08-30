package com.xiaoyao.banner

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.bigkoo.convenientbanner.listener.OnItemClickListener
import com.bumptech.glide.Glide
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @作者 luckly
 * @创建日期 2019/8/30 9:56
 * 使用kotlin来完成轮播图
 */
class MainActivity : AppCompatActivity() {
    //MutableList 等同于java 中的ArrayList ,mutableListOf() 则属于实例化ArrayList,也属于可变集合，用法与java中List 没有太大区别
    //listOf 等同于数组
    var arrayLocal: List<Int>? = listOf(R.mipmap.img1, R.mipmap.img2)
    var mutableListOf: MutableList<String>? = null
    private var options: DisplayImageOptions? = null
    private var mCanLoop = true//是否自动轮播,控制如果是一张图片，不能滑动
    private var convenientNetBanner: ConvenientBanner<String>? = null
    private var convenientBannerLocal: ConvenientBanner<Int>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        convenientNetBanner = findViewById(R.id.convenientbanner)
        convenientBannerLocal = findViewById(R.id.convenientbanner1)
        initView()
        initNetBanner()
        initLocalBanner()

    }

    /**
     * 初始化
     * 添加三张展示照片，网上随便找的，正常形式是调用接口从自己的后台服务器拿取
     */
    fun initView() {
        mutableListOf = mutableListOf()
        mutableListOf?.add("http://img2.imgtn.bdimg.com/it/u=1447362014,2103397884&fm=200&gp=0.jpg")
        mutableListOf?.add("http://img1.imgtn.bdimg.com/it/u=111342610,3492888501&fm=26&gp=0.jpg")
        mutableListOf?.add("http://imgsrc.baidu.com/imgad/pic/item/77094b36acaf2eddc8c37dc7861001e9390193e9.jpg")
    }

    /**
     * 初始化轮播图1
     * setPageIndicator 设置指示器样式
     * setPageIndicatorAlign 设置指示器位置
     * setPointViewVisible 设置指示器是否显示
     * setCanLoop 设置是否轮播
     * setOnItemClickListener 设置每一张图片的点击事件
     */
    private fun initNetBanner() {

        // TODO: 2018/11/22 控制如果只有一张网络图片，不能滑动，不能轮播
        if (mutableListOf?.size!! <= 1) {
            mCanLoop = false
        }

        convenientNetBanner?.setPages(object : CBViewHolderCreator {
            override fun createHolder(itemView: View): Holder<*> {
                return NetImageHolderView(itemView)
            }

            override fun getLayoutId(): Int {
                //设置加载哪个布局
                return R.layout.item_net_banner
            }
        }, mutableListOf)?.setPageIndicator(intArrayOf(R.drawable.past_guide, R.drawable.now_guide))
            ?.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
            ?.setPointViewVisible(mCanLoop)
            ?.setCanLoop(mCanLoop)?.setOnItemClickListener(OnItemClickListener { position ->
                Toast.makeText(
                    this@MainActivity,
                    "你点击了网络第" + position + "张图片",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }

    /**
     * 初始化轮播图2
     * setPageIndicator 设置指示器样式
     * setPageIndicatorAlign 设置指示器位置
     * setPointViewVisible 设置指示器是否显示
     * setCanLoop 设置是否轮播
     * setOnItemClickListener 设置每一张图片的点击事件
     */
    private fun initLocalBanner() {
        convenientBannerLocal?.setPages(object : CBViewHolderCreator {
            override fun createHolder(itemView: View): Holder<*> {
                return LocalImageHolderView(itemView)
            }

            override fun getLayoutId(): Int {
                //设置加载哪个布局
                return R.layout.item_local_banner
            }
        }, arrayLocal)?.setPageIndicator(intArrayOf(R.drawable.past_guide, R.drawable.now_guide))
            ?.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
            ?.setPointViewVisible(mCanLoop)
            ?.setCanLoop(mCanLoop)?.setOnItemClickListener(OnItemClickListener { position ->
                Toast.makeText(
                    this@MainActivity,
                    "你点击了本地第" + position + "张图片",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }

    override fun onResume() {
        super.onResume()
        //开始执行轮播，并设置轮播时长
        convenientbanner?.startTurning(4000)
    }

    override fun onPause() {
        super.onPause()
        //停止轮播
        convenientbanner?.stopTurning()
    }

    //打印
    fun loge(tag: String, content: String?) {
        Log.e(tag, content ?: tag)
    }


    /**
     * 轮播图1 对应的holder
     */
    inner class NetImageHolderView//构造器
        (itemView: View) : Holder<String>(itemView) {
        private var banner: ImageView? = null

        override fun initView(itemView: View) {
            //设置图片加载模式为铺满，具体请搜索 ImageView.ScaleType.FIT_XY
            banner = itemView.findViewById(R.id.iv_net_banner)
            banner!!.scaleType = ImageView.ScaleType.FIT_XY
            //初始化options，可以加载不同情况下的默认图片
            options = DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)//设置加载图片时候的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)//设置获取图片失败的默认图片
                .cacheInMemory(true)//设置内存缓存
                .cacheOnDisk(true)//设置外存缓存
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build()
        }

        override fun updateUI(data: String) {
            //使用ImageLoader加载图片
            ImageLoader.getInstance().displayImage(data, banner!!, options)
        }
    }

    /**
     * 轮播图2 对应的holder,本地加载
     */
    inner class LocalImageHolderView
        (itemView: View) : Holder<Int>(itemView) {
        private var localBanner: ImageView? = null
        override fun initView(itemView: View) {
            localBanner = itemView.findViewById(R.id.iv_local_banner)
            localBanner!!.scaleType = ImageView.ScaleType.FIT_XY
        }

        override fun updateUI(data: Int) {
            Glide.with(this@MainActivity).load(data).into(localBanner!!)
        }
    }
}
