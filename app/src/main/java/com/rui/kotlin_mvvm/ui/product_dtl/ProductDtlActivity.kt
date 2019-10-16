package com.rui.kotlin_mvvm.ui.product_dtl

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.tools.PictureFileUtils
import com.rui.common.base.BasePageVMActivity
import com.rui.kotlin_mvvm.APPValue
import com.rui.kotlin_mvvm.R
import com.rui.kotlin_mvvm.databinding.ActivityProductDtlBinding
import com.rui.kotlin_mvvm.databinding.RvHeadBinding
import com.rui.mvvm.binding.RvOnListChangedCallback
import com.rui.mvvm.binding.VPOnListChangedCallback
import com.rui.mvvm.screenWith
import com.rui.mvvm.toast
import com.rui.viewkit.PhotoDialog
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import javax.inject.Inject


class ProductDtlActivity : BasePageVMActivity<
        ActivityProductDtlBinding,
        ProductDtlVModel,
        ProductImgAdapter,
        LinearLayoutManager,
        RvOnListChangedCallback<ObservableList<Any>>
        >() {

    companion object {
        fun actionStart(context: Context?, prodId: Int, prodNum: String?) {
            //apply函数中使用this，并返回自己
            //also函数中使用it，并返回自己
            val intent = Intent(context, ProductDtlActivity::class.java).apply {
                putExtra("prodId", prodId)
                putExtra("prodNum", prodNum)
            }
            context?.startActivity(intent)
        }
    }

    @Inject
    lateinit var headImageAdapter: ImagePagerAdapter

    @Inject
    lateinit var vpOnListChangedCallback: VPOnListChangedCallback<ObservableList<LocalMedia>>

    override val recyclerView: RecyclerView
        get() = binding.rvData

    override val refreshLayout: SmartRefreshLayout?
        get() = null

    override fun getVMClass(): Class<ProductDtlVModel> = ProductDtlVModel::class.java

    override fun getLayoutID(savedInstanceState: Bundle?): Int =
        R.layout.activity_product_dtl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
        initRVHeader()
        initData()
    }

    private fun initVM() {
        val prodNum = intent.getStringExtra("prodNum")
        viewModel.productNum.set(prodNum)
        val prodId = intent.getIntExtra("prodId", -1)
        viewModel.prodId = prodId
    }

    private fun initData() {
        viewModel.getData(APPValue.LOAD_FIRST)
    }

    private fun initRVHeader() {
        vpOnListChangedCallback.adapter = headImageAdapter
        viewModel.headImgs.addOnListChangedCallback(vpOnListChangedCallback)

        adapter.addHeaderView(getRVHeader())

        //列表item的控件点击事件处理
        adapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                //点击添加item详情图
                R.id.item_camera_right, R.id.item_camera -> {
                    val localZSImgs = viewModel.items[position].localZSImgs
                    showSelectDialog(
                        APPValue.RESULTCODE_ITEM_TAKEPHOTO,
                        APPValue.MAX_IMG_NUM - localZSImgs.size, true
                    )
                }
                //点击添加item颜色图
                R.id.rl_color -> showSelectDialog(
                    APPValue.RESULTCODE_COLOR_TAKEPHOTO,
                    1,
                    true
                )
            }
        }
    }

    /**
     * 加载recyclerview头部相关
     */
    private fun getRVHeader(): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(applicationContext),
            R.layout.rv_head,
            null,
            false
        ) as RvHeadBinding
        headImageAdapter.setSelectList(viewModel.headImgs)
        binding.viewModel = viewModel
        binding.adapter = headImageAdapter

        //头部的相机小图标添加点击事件
        binding.ivCamera.setOnClickListener {
            showSelectDialog(
                APPValue.RESULTCODE_HEAD_TAKEPHOTO,
                APPValue.MAX_IMG_NUM - viewModel.headImgs.size,
                true
            )
        }
        binding.ivCameraRight.setOnClickListener {
            showSelectDialog(
                APPValue.RESULTCODE_HEAD_TAKEPHOTO,
                APPValue.MAX_IMG_NUM - viewModel.headImgs.size,
                true
            )
        }

        binding.rlImg.layoutParams.height = screenWith()
        binding.pager.layoutParams.height = screenWith()

        binding.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                viewModel.headCurrentPos.set(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        return binding.root
    }


    private fun showSelectDialog(requestCode: Int, rest: Int, hideVideo: Boolean) {
        if (rest <= 0) {
            toast("最多只能选择10张图片")
            return
        }
        PhotoDialog.createDialog(this, hideVideo) { v, position ->
            when (v.id) {
                R.id.takePhoto -> openCamera(requestCode)
                R.id.choosePhoto -> openPicSelector(requestCode, rest)
                R.id.chooseVideo -> {
                    toast("去选择视频")
                }
                R.id.btn_cancel -> {
                    toast("取消了")
                }
            }
        }
    }

    /**
     * 打开相机去拍图并裁剪
     *
     * @param requestCode
     */
    private fun openCamera(requestCode: Int) {
        PictureSelector.create(this)
            .openCamera(PictureMimeType.ofImage())
            .enableCrop(true)
            .compress(true)
            //                .cropCompressQuality(50)// 裁剪压缩质量 默认100 int
            .minimumCompressSize(100)// 小于100kb的图片不压缩
            .withAspectRatio(1, 1)
            .showCropFrame(true)
            .showCropGrid(true)
            .scaleEnabled(true)
            .freeStyleCropEnabled(false)
            .isDragFrame(false)
            .rotateEnabled(false)
            .forResult(requestCode)
    }

    /**
     * 选择图片并裁剪
     *
     * @param requestCode
     * @param maxSelectNum
     */
    private fun openPicSelector(requestCode: Int, maxSelectNum: Int) {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .maxSelectNum(maxSelectNum)
            .isCamera(false)
            //                .selectionMedia(lastHeadList)
            .enableCrop(true)
            .compress(true)
            //                .cropCompressQuality(50)// 裁剪压缩质量 默认100 int
            .minimumCompressSize(100)// 小于100kb的图片不压缩
            .withAspectRatio(1, 1)
            .showCropFrame(true)
            .showCropGrid(true)
            .scaleEnabled(true)
            .freeStyleCropEnabled(false)
            .isDragFrame(false)
            .rotateEnabled(false)
            .forResult(requestCode)
        //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
    }

    override fun onDestroy() {
        super.onDestroy()
        PictureFileUtils.deleteCacheDirFile(this)
        viewModel.headImgs.removeOnListChangedCallback(vpOnListChangedCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            viewModel.isShowCommit.set(true)
            when (requestCode) {
                //头部主图选择相册及相机拍照回调
                APPValue.RESULTCODE_HEAD_TAKEPHOTO -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    //with函数不能进行空检查
                    with(viewModel) {
                        headImgs.addAll(selectList)
                        headCurrentPos.set(viewModel.headImgs.size - 1)
                    }
                }
                //头部主图从编辑页面返回，保存返回结果
                APPValue.HEAD_REQUESTCODE -> {
                    data?.run {
                        val imgData =
                            getParcelableArrayListExtra<Parcelable>("items") as ArrayList<LocalMedia>
                        val currentPosition = getIntExtra("currentPostion", 0)
                        with(viewModel){
                            headImgs.clear()//这种全部刷新的方式还可以尝试用DiffUtil进行优化
                            headImgs.addAll(imgData)
                            headCurrentPos.set(currentPosition)
                        }
                    }
                }
                //列表颜色图选择相册和拍照回调
                APPValue.RESULTCODE_COLOR_TAKEPHOTO -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    viewModel.items[viewModel.rvClickPos.get()].apply {
                        localCLImgs.clear()
                        localCLImgs.addAll(selectList)
                        clImgUrl.set(clImgUrlStr)
                    }

                }
                //列表点击item中间相机、右边相机选择相册及拍照回调
                APPValue.RESULTCODE_ITEM_TAKEPHOTO -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    viewModel.items[viewModel.rvClickPos.get()].run {
                        localZSImgs.addAll(selectList)
                        currentPosition.set(localZSImgs.size - 1)
                    }
                }
                //列表从编辑页面返回，保存item后返回结果
                APPValue.ITEM_REQUESTCODE -> {
                    data?.run {
                        val imgData =
                            getParcelableArrayListExtra<Parcelable>("items") as ArrayList<LocalMedia>
                        val currentPosition = getIntExtra("currentPostion", 0)
                        val rvItemPosition = getIntExtra("rvItemPosition", 0)
                        viewModel.items[rvItemPosition - 1].let {
                            it.localZSImgs.clear()
                            it.localZSImgs.addAll(imgData)
                            it.currentPosition.set(currentPosition)
                        }
                    }
                }
            }
        }
    }


}
