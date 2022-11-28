package com.rui.common.htmltext

import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.text.style.BulletSpan
import org.xml.sax.XMLReader

class HtmlListTagsHandler : Html.TagHandler {

    companion object {
        private const val LIST_ITEM_HTML_TAG = "li"
        private const val PARAGRAPH_TOKEN = "\n\n"
    }

    private var isFirstListItem = true

    override fun handleTag(
        openingTag: Boolean,
        tag: String,
        output: Editable,
        xmlReader: XMLReader
    ) {
        if (tag == LIST_ITEM_HTML_TAG && openingTag) {
            if (isFirstListItem) {
                output.append(PARAGRAPH_TOKEN)
                isFirstListItem = false
            }
        }

        if (tag == LIST_ITEM_HTML_TAG && !openingTag) {
            output.append(PARAGRAPH_TOKEN)
            val lastMark = output.getSpans(0, output.length, String::class.java).lastOrNull()
            lastMark?.let {
                val start = output.getSpanStart(it)
                output.removeSpan(it)
                if (start != output.length) {
                    output.setSpan(
                        BulletSpan(),
                        start,
                        output.length,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
    }

}