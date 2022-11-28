package com.rui.common.htmltext

import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.text.style.URLSpan
import android.view.View

class HtmlTextFormatterImpl : HtmlTextFormatter {

    override fun makeLinksClickable(
        stringBuilder: SpannableStringBuilder,
        spanned: URLSpan,
        ATagClickListener: ATagClickListener
    ) {
        stringBuilder.run {
            val flags = getSpanFlags(spanned)
            val start = getSpanStart(spanned)
            val end = getSpanEnd(spanned)
            val clickableSpan = object : URLSpan(spanned.url) {
                override fun onClick(widget: View) {
                    ATagClickListener.onClick(url)
                }
            }
            setSpan(clickableSpan, start, end, flags)
            removeSpan(spanned)
        }
    }


    override fun format(html: String, bulletRadius: Int, gapWidth: Int): SpannableStringBuilder {
        val htmlSpannable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html, null, HtmlListTagsHandler())
        }
        val spannableStringBuilder = SpannableStringBuilder(htmlSpannable)
        return spannableStringBuilder.apply {
            val bulletSpans = getSpans(0, length, BulletSpan::class.java)
            bulletSpans.forEach {
                val start = getSpanStart(it)
                val end = getSpanEnd(it)
                removeSpan(it)
                setSpan(
                    CustomBulletSpan(bulletRadius, gapWidth),
                    start,
                    end,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

}