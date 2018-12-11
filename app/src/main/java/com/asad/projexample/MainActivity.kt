package com.asad.projexample

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.text.Spannable
import android.text.style.AbsoluteSizeSpan


class MainActivity : AppCompatActivity(), ActionMode.Callback {
    lateinit var textView: TextView
    var copy_id: Int = 123456
    val start = arrayListOf<String>()


    var txt: String =
        "If you’re an Android developer, you may have heard of Flutter. It’s a relatively new, supposedly simple framework designed for making cross-platform native apps. It’s not the first of its kind, but it’s being used by Google, giving its claims some credence. Despite my initial reservations upon hearing about it, I decided on a whim to give it a chance — and it dramatically changed my outlook on mobile development within a weekend. Here is what I learned."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.mytext) as TextView

        var arry: Array<String>
        arry = txt.split(".").toTypedArray()
        textView.text = txt

        textView.setCustomSelectionActionModeCallback(this)

    }


    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item!!.itemId) {
            copy_id -> {
                var min = 0
                var max = textView.getText().length
                if (textView.isFocused()) {
                    val selStart = textView.getSelectionStart()
                    val selEnd = textView.getSelectionEnd()

                    min = Math.max(0, Math.min(selStart, selEnd))
                    max = Math.max(0, Math.max(selStart, selEnd))
                    start.add("$selStart-$selEnd")

                }
                // Perform your definition lookup with the selected text
                val selectedText = textView.getText().subSequence(min, max)
                // Finish and close the ActionMode
                Log.d("ASAD", selectedText.toString())
                start.forEach { s: String ->
                    doBold(s.split("-")[0].toInt(),
                        s.split("-")[1].toInt()
                    )
                }
                mode!!.finish()
                return true
            }
            else -> {
            }
        }
        return false
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        menu!!.add(0, copy_id, 0, "Copy").setIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha);
        return true;
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        menu!!.removeItem(android.R.id.selectAll);
        // Remove the "cut" option
        menu.removeItem(android.R.id.cut);
        // Remove the "copy all" option
        menu.removeItem(android.R.id.copy);
        return true;
    }

    override fun onDestroyActionMode(mode: ActionMode?) {

    }

    fun doBold(start: Int, end: Int) {
        val spannable = textView.text as Spannable
        //text.setSpan(AbsoluteSizeSpan(18, true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannable
    }
}
