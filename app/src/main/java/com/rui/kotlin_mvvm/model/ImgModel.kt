package com.rui.kotlin_mvvm.model

import com.rui.retrofit2.basemodel.BaseModel

/**
 * Created by rui on 2018/10/8
 */
class ImgModel : BaseModel() {
    /**
     * img64_URL : /b8vb1786/b8vb1786_1.jpg
     * img32_URL : /b8vb1786/b8vb1786_1.jpg
     * prod_ID : 1
     * img_TYPE : DT
     * create_TIME : 1537000986000
     * line_ID : 383
     * seq_NUM : 0
     * status : A
     * backup : null
     * loaded : false
     */
    private var img64_URL: String? = null
    private var img32_URL: String? = null
    var prod_ID: Int = 0
    private var img_TYPE: String? = null
    var create_TIME: Long = 0
    var line_ID: Int = 0
    var seq_NUM: Int = 0
    private var status: String? = null
    private var backup: String? = null
    var isLoaded: Boolean = false

    val seqIndex: Int
        get() {
            val index = img64_URL!!.lastIndexOf("_")
            val indexPoint = img64_URL!!.lastIndexOf(".")
            var seq_Index = 0
            if (index != -1) {
                val substring = img64_URL!!.substring(index + 1, indexPoint)
                try {
                    seq_Index = Integer.parseInt(substring)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            return seq_Index
        }

    fun getImg64_URL(): String {
        return img64_URL ?: ""
    }

    fun setImg64_URL(img64_URL: String) {
        this.img64_URL = img64_URL
    }

    fun getImg32_URL(): String {
        return img32_URL ?: ""
    }

    fun setImg32_URL(img32_URL: String) {
        this.img32_URL = img32_URL
    }

    fun getImg_TYPE(): String {
        return img_TYPE ?: ""
    }

    fun setImg_TYPE(img_TYPE: String) {
        this.img_TYPE = img_TYPE
    }

    fun getStatus(): String {
        return status ?: ""
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getBackup(): String {
        return backup ?: ""
    }

    fun setBackup(backup: String) {
        this.backup = backup
    }

   override fun toString(): String {
        return "ImgModel{" +
                "img64_URL='" + img64_URL + '\''.toString() +
                ", img32_URL='" + img32_URL + '\''.toString() +
                ", prod_ID=" + prod_ID +
                ", img_TYPE='" + img_TYPE + '\''.toString() +
                ", create_TIME=" + create_TIME +
                ", line_ID=" + line_ID +
                ", seq_NUM=" + seq_NUM +
                ", status='" + status + '\''.toString() +
                ", backup='" + backup + '\''.toString() +
                ", loaded=" + isLoaded +
                '}'.toString()
    }
}
