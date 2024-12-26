package com.example.pixijs_android

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.webkit.WebViewAssetLoader
import com.example.pixijs_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val domain = "xxx.xxx.xxx"
    private val games = arrayOf("bubbo-bubbo", "puzzling-potions")
    private var currentGameIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupWebView()

        binding.fab.setOnClickListener {
            currentGameIndex = (currentGameIndex + 1) % games.size
            binding.webView.loadUrl("https://$domain/games/${games[currentGameIndex]}/index.html")
        }

        binding.webView.loadUrl("https://$domain/games/${games[currentGameIndex]}/index.html")
    }

    private fun setupWebView() {
        binding.webView.run {
            with(settings) {
                javaScriptEnabled = true
                domStorageEnabled = true
            }

            webViewClient = object : WebViewClient() {
                private val webViewAssetLoader by lazy {
                    WebViewAssetLoader.Builder()
                        .setDomain(domain)
                        .addPathHandler(
                            "/", WebViewAssetLoader.AssetsPathHandler(context)
                        ).build()
                }

                override fun shouldInterceptRequest(
                    view: WebView,
                    request: WebResourceRequest
                ): WebResourceResponse? {
                    if (request.url.scheme.equals("https")) {
                        return webViewAssetLoader.shouldInterceptRequest(request.url)
                    }
                    return super.shouldInterceptRequest(view, request)
                }
            }
        }
    }
}