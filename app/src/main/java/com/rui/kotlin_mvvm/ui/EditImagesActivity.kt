package com.rui.kotlin_mvvm.ui

import android.app.Activity
import android.content.Intent
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.chad.library.adapter.base.listener.OnItemDragListener
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.rui.common.base.BasePageVMActivity
import com.rui.kotlin_mvvm.APPValue
import com.rui.kotlin_mvvm.databinding.ActivityEditImagesBinding
import com.rui.kotlin_mvvm.di.vmodel.EditImagesVModel
import com.rui.kotlin_mvvm.ui.adapter.EditImagesAdapter
import com.rui.kotlin_mvvm.ui.adapter.ImagePagerAdapter
import com.rui.mvvm.RvOnListChangedCallback
import com.rui.mvvm.VPOnListChangedCallback
import com.rui.toolkit.DisplayUtils
import com.rui.viewkit.GridSpacingItemDecoration
import com.rui.viewkit.PhotoDialog
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import javax.inject.Inject


class EditImagesActivity : BasePageVMActivity<
        ActivityEditImagesBinding,
        EditImagesVModel,
        EditImagesAdapter,
        GridLayoutManager,
        RvOnListChangedCallback<ObservableList<Any>>>()
    , OnItemDragListener {

    companion object {
        fun actionStart(
            context: Activity,
            data: ArrayList<LocalMedia>,
            currentPostion: Int,
            rvItemPosition: Int,
            requestCode: Int
        ) {
            val intent = Intent(context, EditImagesActivity::class.java)
            intent.putExtra("items", data)
            intent.putExtra("currentPostion", currentPostion)
            intent.putExtra("rvItemPosition", rvItemPosition)
            context.startActivityForResult(intent, requestCode)
        }
    }

    @Inject
    lateinit var mainImageAdapter: ImagePagerAdapter
    @Inject
    lateinit var decoration: GridSpacingItemDecoration
    @Inject
    lateinit var vpOnListChangedCallback: VPOnListChangedCallback<ObservableList<LocalMedia>>

    override val recyclerView: RecyclerView
        get() = binding.rvSmallImage

    override val refreshLayout: SmartRefreshLayout?
        get() = null

    override fun getVMClass(): Class<EditImagesVModel> = EditImagesVModel::class.java

    override fun getLayoutID(savedInstanceState: Bundle?): Int =
        com.rui.kotlin_mvvm.R.layout.activity_edit_images

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
        initVP()
        initRVClick()
    }

    private fun initVM() {
        val imgData = intent.getParcelableArrayListExtra<LocalMedia>("items") as List<LocalMedia>
        viewModel.items.addAll(imgData)
        val currentPostion = intent.getIntExtra("currentPostion", 0)
        viewModel.currentPostion.set(currentPostion)
        val rvItemPosition = intent.getIntExtra("rvItemPosition", -1)
        viewModel.rvItemPosition.set(rvItemPosition)
    }

    private fun initVP() {
        mainImageAdapter.setDisableClick(true)
        binding.headAdapter = mainImageAdapter
        vpOnListChangedCallback.adapter = mainImageAdapter
        viewModel.items.addOnListChangedCallback(vpOnListChangedCallback)

        mainImageAdapter.setSelectList(viewModel.items)

        val layoutParams = binding.pagerMain.layoutParams
        layoutParams.height = DisplayUtils.getScreenWidthAndHight(this.applicationContext)[0]
        binding.pagerMain.layoutParams = layoutParams
        binding.headBarMain.setIvRightOnclickListener {
            val intent = Intent()
            intent.putParcelableArrayListExtra("items", viewModel.items as ArrayList<LocalMedia>)
            intent.putExtra("currentPostion", viewModel.currentPostion.get())
            intent.putExtra("rvItemPosition", viewModel.rvItemPosition.get())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        binding.pagerMain.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                viewModel.currentPostion.set(position)
                adapter.setCurrentPosition(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun initRV() {
        super.initRV()
        recyclerView.addItemDecoration(decoration)
    }

    private fun initRVClick() {
//        binding.setDecoration(decoration)
//        recyclerView.addItemDecoration(decoration)
        //        rvOnListChangedCallback.setImageMainActivity(this);
        adapter.setOnItemChildClickListener { adapter, view, position ->
            viewModel.currentPostion.set(position)
            this.adapter.setCurrentPosition(position)
        }

        //设置拖动相关
        val itemDragAndSwipeCallback = ItemDragAndSwipeCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(itemDragAndSwipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvSmallImage)
        //开启拖拽
        adapter.enableDragItem(itemTouchHelper, com.rui.kotlin_mvvm.R.id.iv_small, true)
        adapter.setOnItemDragListener(this)
        setFootView()
    }

    /**
     * +号图片的view
     */
    private var footView: View? = null

    /**
     * +添加的显示及隐藏
     */
    private fun setFootView() {
        if (footView == null) {
            footView =
                LayoutInflater.from(applicationContext)
                    .inflate(com.rui.kotlin_mvvm.R.layout.layout_foot_small, null)
            footView?.setOnClickListener {
                showSelectDialog(
                    APPValue.RESULTCODE_DT_TAKEPHOTO,
                    APPValue.MAX_IMG_NUM - viewModel.items.size
                )
            }
        }
        if (viewModel.items.size < 10) {
            if (adapter.footerLayoutCount == 0) {
                adapter.addFooterView(footView)
                EditImagesAdapter.setImageViewSize(applicationContext, footView!!)
                //设置脚部不占满一行
                adapter.isFooterViewAsFlow = true
            }
        } else {
            adapter.removeFooterView(footView)
        }
    }

    private fun showSelectDialog(requestCode: Int, rest: Int) {
        PhotoDialog.createDialog(this, true) { v1: View, position: Int ->
            when (v1.id) {
                com.rui.kotlin_mvvm.R.id.takePhoto -> openCamera(requestCode)
                com.rui.kotlin_mvvm.R.id.choosePhoto -> openPicSelector(requestCode, rest)
                com.rui.kotlin_mvvm.R.id.chooseVideo -> {
                }
                com.rui.kotlin_mvvm.R.id.btn_cancel -> {
                }
            }
        }
    }

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
            .forResult(requestCode)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
    }

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

    // 拖动监听
    override fun onItemDragMoving(
        source: RecyclerView.ViewHolder?,
        from: Int,
        target: RecyclerView.ViewHolder?,
        to: Int
    ) {

    }

    // 拖动监听
    override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

    }

    // 拖动监听
    override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
        //拖动完成之后通知头部及列表刷新数据
//        mainImageAdapter.notifyDataSetChanged();
        viewModel.currentPostion.set(pos);
        adapter.setCurrentPosition(pos);
        viewModel.isShowSave.set(true); }
}
