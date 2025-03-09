package tpu.kolesovmark.rfgf.datadownload

import android.content.res.Resources.NotFoundException
import android.os.AsyncTask
import android.text.Html
import android.util.Log
import android.view.ViewStructure.HtmlInfo
import android.webkit.ValueCallback
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import tpu.kolesovmark.rfgf.MainActivity
import tpu.kolesovmark.rfgf.WebAppInterface
import java.io.File
import java.util.Timer

class DownloadWeb {
    companion object {
        var htmlDoc: String? = "null"
        fun loadWebViewClientAndDoJSScript(webView: WebView, name: String, reload: Boolean) {
            var step = 1

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    when (step) {
                        1 -> {
                            Log.d("WEBVIEW", "Проверяем, нужна ли авторизация...")

                            webView.evaluateJavascript(
                                """
                    (function() {
                        return document.querySelector('input[name="login"]') !== null;
                    })();
                    """.trimIndent()
                            ) { needsLogin ->
                                if (needsLogin == "true") {
                                    Log.d("WEBVIEW", "Нужна авторизация, выполняем вход...")

                                    webView.evaluateJavascript(
                                        """
                            (function() {
                                document.querySelector('input[name="login"]').value = 'kolesovmark_tpu';
                                document.querySelector('input[type="password"]').value = 'c@jh82Rh>Q>?Pi)';
                                document.querySelector('input[type="submit"]').click();
                            })();
                            """.trimIndent(), null
                                    )
                                } else {
                                    Log.d("WEBVIEW", "Авторизация уже выполнена, пропускаем вход.")
                                }

                                step++
                            }
                        }

                        2 -> {
                            Log.d("WEBVIEW", "Вводим текст и отправляем форму...")
                            webView.evaluateJavascript(
                                """
                    (function(){
                        document.querySelector('input[name="ftext"]').value = 'Искомый текст';
                        document.querySelector('input[type="submit"]').click();
                    })();
                    """.trimIndent(), null
                            )
                            step++
                        }

                        3 -> {
                            Log.d("WEBVIEW", "Получаем HTML страницы...")
                            webView.evaluateJavascript(
                                "document.documentElement.outerHTML"
                            ) { html ->
                                Log.d("HTML_CONTENT", html)
                            }
                        }
                    }
                }
            }

// Запускаем WebView
            webView.loadUrl("https://rfgf.ru/gkm")
        }

        fun GlobalScope.runDelayedUITask(delay: Long, task: () -> Unit) {
            launch(Dispatchers.IO) {
                delay(delay)
                launch(Dispatchers.Main) {
                    task()
                }
            }
        }
    }
}



