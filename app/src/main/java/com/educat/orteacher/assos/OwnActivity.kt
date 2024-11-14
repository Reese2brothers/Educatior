package com.educat.orteacher.assos

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Message
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.Manifest
import android.R
import android.content.ActivityNotFoundException
import android.webkit.JavascriptInterface
import android.webkit.URLUtil
import android.webkit.WebResourceResponse
import android.webkit.WebView.WebViewTransport
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.removeLast

private const val REQUEST_CODE_CAMERA_PERMISSION = 101

class OwnActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val url = intent?.getStringExtra("url")
            if (url != null) {
                ViewScreen("https://$url")
            }
        }
        window.decorView.apply {
            systemUiVisibility = systemUiVisibility or
                    android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    android.view.View.SYSTEM_UI_FLAG_FULLSCREEN or
                    android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY }
    }

    override fun onResume() {
        super.onResume()
        window.decorView.apply {
            systemUiVisibility = systemUiVisibility or
                    android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    android.view.View.SYSTEM_UI_FLAG_FULLSCREEN or
                    android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY }
    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ViewScreen(url: String) {
    var webView by remember { mutableStateOf<WebView?>(null) }
    var uploadMessage: ValueCallback<Array<Uri>>? by remember { mutableStateOf(null) }
    var cameraImageUri: Uri? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    var progressBarVisibility by remember { mutableStateOf(false) }
    var progressBarProgress by remember { mutableStateOf(0) }
    var canGoBack by remember { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null && data.data != null) {
                uploadMessage?.onReceiveValue(arrayOf(data.data!!))
            } else if (cameraImageUri != null) {
                uploadMessage?.onReceiveValue(arrayOf(cameraImageUri!!))
                cameraImageUri = null
            }
            uploadMessage = null
        }
    }

    fun createImageFile(context: Context): File? {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return try {
            File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
            )
        } catch (ex: IOException) {
            Log.e("WebView", "error", ex)
            null
        }
    }

    val webChromeClient = remember {
        object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progressBarVisibility = false
                } else {
                    progressBarVisibility = true
                    progressBarProgress = newProgress
                }
            }

            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                uploadMessage?.onReceiveValue(null)
                uploadMessage = filePathCallback
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(context.packageManager) != null) {
                    val photoFile = createImageFile(context)
                    if (photoFile != null) {
                        cameraImageUri = FileProvider.getUriForFile(
                            context,
                            "com.educat.orteacher.assos.fileprovider",
                            photoFile
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri)
                        Log.d("WebView", "cameraImageUri: $cameraImageUri")
                    }
                }
                val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                contentSelectionIntent.type = "*/*" // Все типы файлов
                val intentArray: Array<Intent> =
                    if (takePictureIntent.resolveActivity(context.packageManager) != null) {
                        arrayOf(takePictureIntent, contentSelectionIntent)
                    } else {
                        arrayOf(contentSelectionIntent)
                    }
                val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Выбор файла")
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.CAMERA),
                        REQUEST_CODE_CAMERA_PERMISSION
                    )
                } else {
                    launcher.launch(chooserIntent)
                }

                return true
                return true
            }

            override fun onCreateWindow(
                view: WebView,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {
                val newWebView = WebView(context)
                newWebView.webChromeClient = this
                (resultMsg!!.obj as WebView.WebViewTransport).webView = newWebView
                resultMsg.sendToTarget()
                return true
            }

            override fun onCloseWindow(window: WebView) {
                (window.parent as? ViewGroup)?.removeView(window)

            }

            private var customViewCallback: CustomViewCallback? = null
            private var customView: View? = null
            override fun onShowCustomView(view: View, callback: CustomViewCallback) {
                val rootView =
                    (context as? Activity)?.findViewById<FrameLayout>(R.id.content)
                webView?.visibility = View.GONE
                rootView?.addView(
                    view, FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
                customViewCallback = callback
                customView = view
            }

            override fun onHideCustomView() {
                val rootView =
                    (context as? Activity)?.findViewById<FrameLayout>(R.id.content)
                rootView?.removeView(customView)
                webView?.visibility = View.VISIBLE
                customViewCallback?.onCustomViewHidden()
                customViewCallback = null
                customView = null
            }
        }
    }
    val rememberedWebChromeClient = remember { webChromeClient }
    val webViewClient = remember {
        object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                if (url.contains("OR_BIBED_15")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.setPackage("com.android.chrome")
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        intent.setPackage(null)
                        context.startActivity(intent)
                    }
                    return true
                }
                return if (url.contains("https://m.facebook.com/oauth/error")) true
                else if (url.startsWith("http")) false
                else {
                    try {
                        val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        view?.context?.startActivity(intent)
                    } catch (e: Exception) {
                        if (url.contains("line:")) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=jp.naver.line.android"))
                            view?.context?.startActivity(intent)
                        }
                        if (url.contains("diia:")) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=ua.gov.diia.app"))
                            view?.context?.startActivity(intent)
                        }
                        if (url.contains("viber:")) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.viber.voip"))
                            view?.context?.startActivity(intent)
                        }
                        if (url.contains("whatsapp:")) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp"))
                            view?.context?.startActivity(intent)
                        }
                        if (url.contains("googlepay:")) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.walletnfcrel"))
                            view?.context?.startActivity(intent)
                        }
                        if (url.startsWith("http") || url.startsWith("https"))  false
                    }
                    true
                }
            }

            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                val url = request.url.toString()
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                    return null
                }
                return super.shouldInterceptRequest(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
    }
    val rememberedWebViewClient = remember { webViewClient }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    isSaveEnabled = true
                    isFocusableInTouchMode = true
                    webView?.addJavascriptInterface(WebAppInterface(context), "Android")
                    CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                    setDownloadListener { url, _, _, _, _ ->
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    }
                    CookieManager.getInstance().setAcceptCookie(true)
                    isFocusable = true
                    setLayerType(View.LAYER_TYPE_HARDWARE, null)
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setDownloadListener { url, userAgent, contentDescription, mimetype, _ ->
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        context.startActivity(i)
                    }
                    settings.apply {
                        loadWithOverviewMode = true
                        userAgentString = userAgentString.replace("; wv", "")
                        allowContentAccess = true
                        useWideViewPort = true
                        cacheMode = WebSettings.LOAD_DEFAULT
                        loadsImagesAutomatically = true
                        mixedContentMode = 0
                        builtInZoomControls = true
                        mediaPlaybackRequiresUserGesture = false
                        setSupportMultipleWindows(true)
                        databaseEnabled = true
                        domStorageEnabled = true
                        javaScriptEnabled = true
                        displayZoomControls = false
                        allowFileAccess = true
                        javaScriptCanOpenWindowsAutomatically = true
                    }
                    webView?.evaluateJavascript(
                        "(function() { " +
                                "var links = document.getElementsByTagName('a');" +
                                "for (var i = 0; i < links.length; i++) {" +
                                "var link = links[i];" +
                                "if (!link.href.startsWith('http://') && !link.href.startsWith('https://')) {" +
                                "link.onclick = function(event) { " +
                                "Android.openExternalLink(this.href); " +
                                "event.preventDefault(); " +
                                "return false; " +
                                "}; " +
                                "}" +
                                "}" +
                                "})()"
                    ) { value ->
                        Log.d("WebView", "JavaScript result: $value")
                    }
                    loadUrl(url)
                    webView = this
                }
            },
            update = { webViews ->
                webView?.settings?.javaScriptEnabled = true
                webViews.loadUrl(url)
                webViews.webChromeClient = rememberedWebChromeClient
                webViews.webViewClient = rememberedWebViewClient
            },
            modifier = Modifier.imePadding()
        )
        if (progressBarVisibility) {
            LinearProgressIndicator( progress = progressBarProgress / 100f,
            modifier = Modifier.fillMaxWidth().padding(16.dp).align(Alignment.TopCenter)
        )
        }
    }

   BackHandler(enabled = canGoBack) {
       if (canGoBack) {
           webView?.goBack()
       } else {
           (context as Activity).finishAffinity()
       }
   }
}
class WebAppInterface(private val context: Context) {
    @JavascriptInterface
    fun openExternalLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e("WebView", "Error opening link", e)
        }
    }
}


