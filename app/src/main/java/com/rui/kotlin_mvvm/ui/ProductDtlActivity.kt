package com.rui.kotlin_mvvm.ui

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
import com.rui.kotlin_mvvm.databinding.ActivityProductDtlBinding
import com.rui.kotlin_mvvm.databinding.RvHeadBinding
import com.rui.kotlin_mvvm.di.vmodel.ProductDtlVModel
import com.rui.mvvm.RvOnListChangedCallback
import com.rui.mvvm.VPOnListChangedCallback
import com.rui.toolkit.DisplayUtils
import com.rui.toolkit.toast
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

    companion object{
        fun actionStart(context: Context?, prodId: Int, prodNum: String?) {
            val intent = Intent(context, ProductDtlActivity::class.java)
            intent.putExtra("prodId", prodId)
            intent.putExtra("prodNum", prodNum)
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
        com.rui.kotlin_mvvm.R.layout.activity_product_dtl

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
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                //点击添加item详情图
                com.rui.kotlin_mvvm.R.id.item_camera_right, com.rui.kotlin_mvvm.R.id.item_camera -> {
                    val localZSImgs = viewModel.items[position].localZSImgs
                    showSelectDialog(
                        APPValue.RESULTCODE_ITEM_TAKEPHOTO,
                        APPValue.MAX_IMG_NUM - localZSImgs.size, true
                    )
                }
                //点击添加item颜色图
                com.rui.kotlin_mvvm.R.id.rl_color -> showSelectDialog(
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
            com.rui.kotlin_mvvm.R.layout.rv_head,
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
            );
        }
        binding.ivCameraRight.setOnClickListener {
            showSelectDialog(
                APPValue.RESULTCODE_HEAD_TAKEPHOTO,
                APPValue.MAX_IMG_NUM - viewModel.headImgs.size,
                true
            );
        }

        val rlImgsParams = binding.rlImg.layoutParams
        rlImgsParams.height = DisplayUtils.getScreenWidthAndHight(this.applicationContext)[0]
        binding.rlImg.layoutParams = rlImgsParams

        val layoutParams = binding.pager.layoutParams
        layoutParams.height = DisplayUtils.getScreenWidthAndHight(this.applicationContext)[0]
        binding.pager.layoutParams = layoutParams

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
        return binding.getRoot()
    }


    private fun showSelectDialog(requestCode: Int, rest: Int, hideVideo: Boolean) {
        if (rest <= 0) {
            toast("最多只能选择10张图片")
            return
        }
        PhotoDialog.createDialog(this, hideVideo) { v, position ->
            when (v.id) {
                com.rui.kotlin_mvvm.R.id.takePhoto -> openCamera(requestCode)
                com.rui.kotlin_mvvm.R.id.choosePhoto -> openPicSelector(requestCode, rest)
                com.rui.kotlin_mvvm.R.id.chooseVideo -> {
                }
                com.rui.kotlin_mvvm.R.id.btn_cancel -> {
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
                    viewModel.headImgs.addAll(selectList)
                    viewModel.headCurrentPos.set(viewModel.headImgs.size - 1)
                }
                //头部主图从编辑页面返回，保存返回结果
                APPValue.HEAD_REQUESTCODE -> {
                    data?.run {
                        val imgData =
                            getParcelableArrayListExtra<Parcelable>("items") as ArrayList<LocalMedia>
                        val currentPostion = getIntExtra("currentPostion", 0)
                        viewModel.headImgs.clear()//这种全部刷新的方式还可以尝试用DiffUtil进行优化
                        viewModel.headImgs.addAll(imgData)
                        viewModel.headCurrentPos.set(currentPostion)
                    }

                }
                //列表颜色图选择相册和拍照回调
                APPValue.RESULTCODE_COLOR_TAKEPHOTO -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    val colorModel = viewModel.items[viewModel.rvClickPos.get()]
                    colorModel.localCLImgs.clear()
                    colorModel.localCLImgs.addAll(selectList)
                    colorModel.clImgUrl.set(colorModel.clImgUrlStr)
                }
                //列表点击item中间相机、右边相机选择相册及拍照回调
                APPValue.RESULTCODE_ITEM_TAKEPHOTO -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    val colorModel = viewModel.items[viewModel.rvClickPos.get()]
                    colorModel.localZSImgs.addAll(selectList)
                    colorModel.currentPosition.set(colorModel.localZSImgs.size - 1)
                }
                //列表从编辑页面返回，保存item后返回结果
                APPValue.ITEM_REQUESTCODE -> {
                    data?.run {
                        val imgData =
                            getParcelableArrayListExtra<Parcelable>("items") as ArrayList<LocalMedia>
                        val currentPostion = getIntExtra("currentPostion", 0)
                        val rvItemPostion = getIntExtra("rvItemPosition", 0)
                        val item = viewModel.items[rvItemPostion - 1]
                        item.localZSImgs.clear()
                        item.localZSImgs.addAll(imgData)
                        item.currentPosition.set(currentPostion)
                    }
                }
            }
        }
    }


}
