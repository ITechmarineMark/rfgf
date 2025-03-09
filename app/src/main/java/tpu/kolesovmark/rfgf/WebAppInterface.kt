package tpu.kolesovmark.rfgf

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface



class WebAppInterface(private val context: Context) {

    var listOfTds = mutableListOf<String>()

    @JavascriptInterface
    fun processHTML(html: String) {
        Log.d("HTML_CONTENT", html)
        var concatText = ""
        concatText += html

        if (concatText.contains("results")) {
            listOfTds = ParseHTML.parse(concatText)
            removeProgressBar()
        }
    }

    fun removeProgressBar() {
        (context as? Activity)?.runOnUiThread {
            (context as MainActivity)
            context.binding.progressBar.visibility = View.GONE

            context.updateRecyclerViewData(listOfTds)
        }
    }

    @JavascriptInterface
    fun isLoginPage(needsLogin: Boolean) {
        Log.d("WEBVIEW", "Нужна авторизация: $needsLogin")
        if (needsLogin) {

            (context as? Activity)?.runOnUiThread {
                (context as MainActivity).webView.evaluateJavascript(
                    """
                    (function() {
                        document.querySelector('input[name="login"]').value = 'kolesovmark_tpu';
                        document.querySelector('input[type="password"]').value = 'c@jh82Rh>Q>?Pi)';
                        document.querySelector('input[type="submit"]').click();
                    })();
                    """.trimIndent(), null
                )
            }
        }
    }
}