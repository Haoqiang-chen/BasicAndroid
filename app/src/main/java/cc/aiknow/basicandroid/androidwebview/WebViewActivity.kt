package cc.aiknow.basicandroid.androidwebview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import cc.aiknow.basicandroid.R
import cc.aiknow.basicandroid.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.max = 100
        binding.webView.settings.let {
            it.setJavaScriptEnabled(true)
            it.pluginState = WebSettings.PluginState.ON
            it.useWideViewPort = true
            it.loadWithOverviewMode = true
            it.setSupportZoom(true)
        }
        binding.webView.loadUrl("https://www.baidu.com")
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
        binding.webView.webChromeClient = object: WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.progressBar.progress = newProgress
            }


        }
    }
}