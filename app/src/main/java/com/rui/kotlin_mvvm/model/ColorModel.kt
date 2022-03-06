package com.rui.kotlin_mvvm.model

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.rui.retrofit2.basemodel.BaseModel

/**
 * Created by rui on 2019/6/25
 */
class ColorModel : BaseModel() {

    /**
     * 颜色图的集合（包括本地及网络图片）
     */
    var localCLImgs: ObservableArrayList<LocalMedia> = ObservableArrayList()
    /**
     * 颜色图的url
     */
    var clImgUrl = ObservableField<String>()
    /**
     * color_NUM : L07
     * color_NAME : 紫色
     * prod_ID : 23
     * id : 13153
     * backup : null
     * loaded : false
     */
    var color_NUM: String? = null
        get() = if (field == null) "" else field!!.toLowerCase()
    var color_NAME: String? = null
        get() = if (field == null) "" else field
    var prod_ID: Int = 0
    var id: Int = 0
    var backup: String? = null
        get() = if (field == null) "" else field
    var isLoaded: Boolean = false
    var creatTime: Long = 0
    /**
     * viewpager当前展示的下标
     */
    val currentPosition = ObservableInt(0)
    /**
     * 上一次颜色详情图排到的序号
     */
    var maxIndex = 0
    /**
     * 各颜色下详情图图的集合（包括本地及网络图片）
     */

    var localZSImgs = ObservableArrayList<LocalMedia>()


    //    public List<LocalMedia> getLocalZSImgs() {
    //        if (localZSImgs == null) {
    //            localZSImgs = new ArrayList<>();
    //        }
    //        return localZSImgs;
    //    }
    //
    //    public void setLocalZSImgs(List<LocalMedia> localZSImgs) {
    //        this.localZSImgs = localZSImgs;
    //    }

    val clImgUrlStr: String
        get() {
            if (localCLImgs != null && localCLImgs!!.size > 0) {
                val smallMineType = localCLImgs!![0].getMimeType()
                return if (smallMineType == PictureMimeType.ofImage()) {
                    localCLImgs!![0].getCompressPath()
                } else {
                    ""
//                    ImageHelper.addImageDomain(localCLImgs!![0].getCompressPath())
                }
            }
            return ""
        }


    val loadTime: Long
        get() {
            if (localCLImgs != null && localCLImgs!!.size > 0) {
                val smallMineType = localCLImgs!![0].getMimeType()
                return if (smallMineType == PictureMimeType.ofImage()) {
                    localCLImgs!![0].getDuration()
                } else {
                    0
                }
            }
            return 0
        }

}
