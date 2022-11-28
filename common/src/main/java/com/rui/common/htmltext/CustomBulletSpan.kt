package com.rui.common.htmltext

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.text.Layout
import android.text.Spanned
import android.text.style.LeadingMarginSpan

class CustomBulletSpan(
    private val bulletRadius: Int = 4,
    private val gapWidth: Int = 2
) : LeadingMarginSpan {

    private lateinit var mBulletPath: Path

    override fun getLeadingMargin(first: Boolean): Int = 2 * bulletRadius + gapWidth

    override fun drawLeadingMargin(
        c: Canvas,
        p: Paint,
        x: Int,
        dir: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        first: Boolean,
        layout: Layout?
    ) {
        if ((text as Spanned).getSpanStart(this) == start) {
            val style = p.style
            p.style = Paint.Style.FILL

            val yPosition = if (layout != null) {
                val line = layout.getLineForOffset(start)
                layout.getLineBaseline(line).toFloat() - bulletRadius * 2f
            } else {
                (top + bottom) / 2f
            }

            val xPosition = (x + dir * bulletRadius).toFloat()

            c.apply {
                if (isHardwareAccelerated) {
                    mBulletPath = Path()
                    mBulletPath.addCircle(0.0f, 0.0f, bulletRadius.toFloat(), Path.Direction.CW)
                    save()
                    translate(xPosition, yPosition)
                    drawPath(mBulletPath, p)
                    restore()
                } else {
                    drawCircle(xPosition, yPosition, bulletRadius.toFloat(), p)
                }
            }

            p.style = style
        }
    }
}