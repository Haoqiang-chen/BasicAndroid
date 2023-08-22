package cc.aiknow.basicandroid.androidwebview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import cc.aiknow.basicandroid.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        progressBar.max = 100
        webView.settings.let {
            it.setJavaScriptEnabled(true)
            it.pluginState = WebSettings.PluginState.ON
            it.useWideViewPort = true
            it.loadWithOverviewMode = true
            it.setSupportZoom(true)
        }
        webView.loadUrl("https://www.baidu.com")
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
        webView.webChromeClient = object: WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
            }


        }
    }
}