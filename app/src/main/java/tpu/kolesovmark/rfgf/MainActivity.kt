package tpu.kolesovmark.rfgf

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import tpu.kolesovmark.rfgf.databinding.ActivityMainBinding
import tpu.kolesovmark.rfgf.recyclerview.ItemAdapter


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var webView: WebView
    var tds = mutableListOf<String>()
    lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.addJavascriptInterface(WebAppInterface(this), "Android")
        initRecViewAndAdapter()
        binding.goSearching.setOnClickListener {
            startEvaluateJS()
        }
    }

    fun updateRecyclerViewData(newData: MutableList<String>) {
        tds.clear()
        tds.addAll(newData)
        itemAdapter.notifyDataSetChanged()
    }


    fun initRecViewAndAdapter() {
        itemAdapter = ItemAdapter(tds)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = itemAdapter
    }

    private fun startEvaluateJS() {
        binding.progressBar.visibility = View.VISIBLE
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView.evaluateJavascript(
                    """
            (function() {
                Android.isLoginPage(document.querySelector('input[name="login"]') !== null);
            })();
            """.trimIndent(), null
                )
                webView.evaluateJavascript(
                    """
            setTimeout(() => {
                document.querySelector('input[name="ftext"]').value = '${binding.cityEnter.text}';
                document.querySelector('input[type="submit"]').click();

                setTimeout(() => {
                    Android.processHTML(document.documentElement.outerHTML);
                }, 12000);
            }, 6000);
            """.trimIndent(), null
                )
            }

        }

        webView.loadUrl("https://rfgf.ru/gkm")
    }

}