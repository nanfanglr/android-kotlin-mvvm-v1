package com.rui.kotlin_mvvm.model

import com.luck.picture.lib.entity.LocalMedia
import com.rui.retrofit2.basemodel.BaseModel
import java.util.*

/**
 * Created by rui on 2018/10/8
 */
class ProductDtlModel : BaseModel() {

    /**
     * colors : []
     * imgs : [{"img64_URL":"/b8vb1786/b8vb1786_1.jpg","img32_URL":"/b8vb1786/b8vb1786_1.jpg","prod_ID":1,"img_TYPE":"DT","create_TIME":1537000986000,"line_ID":383,"seq_NUM":0,"status":"A","backup":null,"loaded":false},{"img64_URL":"/b8vb1786/b8vb1786_2.jpg","img32_URL":"/b8vb1786/b8vb1786_2.jpg","prod_ID":1,"img_TYPE":"DT","create_TIME":1537000986000,"line_ID":384,"seq_NUM":1,"status":"A","backup":null,"loaded":false},{"img64_URL":"/b8vb1786/b8vb1786_3.jpg","img32_URL":"/b8vb1786/b8vb1786_3.jpg","prod_ID":1,"img_TYPE":"DT","create_TIME":1537000987000,"line_ID":385,"seq_NUM":2,"status":"A","backup":null,"loaded":false},{"img64_URL":"/b8vb1786/b8vb1786_4.jpg","img32_URL":"/b8vb1786/b8vb1786_4.jpg","prod_ID":1,"img_TYPE":"DT","create_TIME":1537000987000,"line_ID":386,"seq_NUM":3,"status":"A","backup":null,"loaded":false},{"img64_URL":"/b8vb1786/b8vb1786_ys_n00.jpg","img32_URL":"/b8vb1786/b8vb1786_ys_n00.jpg","prod_ID":1,"img_TYPE":"CL","create_TIME":1537000988000,"line_ID":387,"seq_NUM":4,"status":"A","backup":null,"loaded":false},{"img64_URL":"/b8vb1786/b8vb1786_ys_n03.jpg","img32_URL":"/b8vb1786/b8vb1786_ys_n03.jpg","prod_ID":1,"img_TYPE":"CL","create_TIME":1537000988000,"line_ID":388,"seq_NUM":5,"status":"A","backup":null,"loaded":false}]
     * prod_ID : 1
     * prod_NAME : 女长裤(铅笔裤)
     * rack_RATE : 189.0
     * prod_NUM : B8VB1786
     */
    //    /**
    //     * 头部视图唯一视频,用来标记删除
    //     */
    //    private LocalMedia videoDT;

    //    public LocalMedia getVideoDT() {
    //        return videoDT;
    //    }
    //
    //    public void setVideoDT(LocalMedia videoDT) {
    //        this.videoDT = videoDT;
    //    }

    var prod_ID: Int = 0
    var prod_NAME: String? = null
        get() = if (field == null) "" else field
    var rack_RATE: Double = 0.toDouble()
    var prod_NUM: String? = null
        get() = if (field == null) "" else field!!.toLowerCase()
    
    var colors: List<ColorModel> = ArrayList()

    var imgs: List<ImgModel> = ArrayList()


    /**
     * 上一次主图排到的序号
     */
    var maxIndex: Int = 0

    /**
     * 图片的数据(包括网络和本地图片)
     */
    var imgsDT: List<LocalMedia> = ArrayList()


}
