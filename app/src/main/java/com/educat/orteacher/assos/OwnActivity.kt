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
import androidx.compose.ui.input.key.type
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
    val webChromeClient = remember { object : WebChromeClient() {
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
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA_PERMISSION)
            } else {
                launcher.launch(chooserIntent)
            }

            return true
            return true
        }
            override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
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
                val rootView = (context as? Activity)?.findViewById<FrameLayout>(android.R.id.content)
                webView?.visibility = View.GONE
                rootView?.addView(view, FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                ))
                customViewCallback = callback
                customView = view
            }
            override fun onHideCustomView() {
                val rootView = (context as? Activity)?.findViewById<FrameLayout>(android.R.id.content)
                rootView?.removeView(customView)
                webView?.visibility = View.VISIBLE
                customViewCallback?.onCustomViewHidden()
                customViewCallback = null
                customView = null
            }
        } }
    val rememberedWebChromeClient = remember { webChromeClient }
    val webViewClient = remember {
        object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
    }
    val rememberedWebViewClient = remember { webViewClient }
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                isSaveEnabled = true
                isFocusableInTouchMode = true
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                setDownloadListener { url, _, _, _, _ ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
                CookieManager.getInstance().setAcceptCookie(true)
                isFocusable = true
                setLayerType(View.LAYER_TYPE_HARDWARE, null)
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
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
                webView = this

            }
        },
        update = { webViews ->
            webViews.loadUrl(url)
            webViews.webChromeClient = rememberedWebChromeClient
            webViews.webViewClient = rememberedWebViewClient
        },
        modifier = Modifier.imePadding()
    )
}


