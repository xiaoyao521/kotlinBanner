# kotlinBanner

轮播图是app必备的元素之一。


ConvenientBanner是github大神封装的一个通用广告栏控件

基于kotlin来将ConvenientBanner进行了简单的使用

mCanLoop用来控制如果是一张图片不能滑动，不能自动轮播，不展示指示器

##
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
    
    
##    这个是网络图片对应的holder
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
    
##    如果基于轮播则可以在onResume() 和onPuse()两个方法来控制开始和结束
    
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
## 最后
    项目中有两种加载方式，一种是网络，一种是本地，有需求的可以自行参考
    
