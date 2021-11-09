package com.example.textviewlinesplitterdemo

import android.os.Bundle
import android.text.StaticLayout
import android.view.View.MeasureSpec
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.doOnNextLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val internalText = TextView(this)
        val storyLines = mutableListOf<TextView>(
            findViewById(R.id.textView),
            findViewById(R.id.textView2),
            findViewById(R.id.textView3),
            findViewById(R.id.textView4),
            findViewById(R.id.textView5),
        )

        findViewById<LinearLayoutCompat>(R.id.layout).doOnNextLayout {
            val lp =
                LinearLayoutCompat.LayoutParams(
                    it.width - it.paddingLeft - it.paddingRight,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                )
            internalText.layoutParams = lp
            internalText.text = getString(R.string.story_text)
            val widthMeasureSpec = MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY)
            val heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            // Force creation of StaticLayout.
            internalText.measure(widthMeasureSpec, heightMeasureSpec)
            val layout = internalText.layout as StaticLayout

            // For demo, we will look at maximum of five lines.
            for (i in 0 until kotlin.math.min(5, layout.lineCount)) {
                layout.apply {
                    storyLines[i].text = layout.text.substring(getLineStart(i), getLineEnd(i))
                }
            }
        }
    }
}