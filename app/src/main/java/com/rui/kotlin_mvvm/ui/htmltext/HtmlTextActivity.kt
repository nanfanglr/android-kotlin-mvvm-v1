package com.rui.kotlin_mvvm.ui.htmltext

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import androidx.appcompat.widget.AppCompatTextView
import com.rui.common.htmltext.HtmlTextFormatterImpl
import com.rui.common.htmltext.ATagClickListener
import com.rui.kotlin_mvvm.R
import com.rui.mvvm.toast

class HtmlTextActivity : AppCompatActivity(), ATagClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_html_text)
        val tv = findViewById<AppCompatTextView>(R.id.tv_content)
        val htmlText = resources.getString(R.string.html_text)
        enableHtmlText(tv, htmlText)
    }

    private fun enableHtmlText(textView: AppCompatTextView, htmlText: String) {
        val bulletRadius = resources.getDimension(R.dimen.bullet_radius).toInt()
        val gapWidth = resources.getDimension(R.dimen.gap_width).toInt()
        val richTextFormatter = HtmlTextFormatterImpl()
        val formatSpan =
            richTextFormatter.format(htmlText, bulletRadius = bulletRadius, gapWidth = gapWidth)
        formatSpan.getSpans(0, htmlText.length, URLSpan::class.java).apply {
            forEach {
                richTextFormatter.makeLinksClickable(
                    formatSpan,
                    spanned = it,
                    this@HtmlTextActivity
                )
            }
        }
        textView.movementMethod = LinkMovementMethod.getInstance()

        textView.apply {
            text = formatSpan
            highlightColor = Color.TRANSPARENT
            setTextColor(Color.BLACK)
            setLinkTextColor(Color.BLUE)
        }

    }

    override fun onClick(urlString: String) {
        toast(urlString)
    }
}