package com.rui.common.htmltext

import android.text.SpannableStringBuilder
import android.text.style.URLSpan

interface HtmlTextFormatter {

    fun makeLinksClickable(
        stringBuilder: SpannableStringBuilder,
        spanned: URLSpan,
        ATagClickListener: ATagClickListener
    )

    fun format(html: String, bulletRadius: Int, gapWidth: Int): SpannableStringBuilder

}