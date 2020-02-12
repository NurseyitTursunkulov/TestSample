package com.example.testrussia

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.testrussia.news.NewsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {

    val newsViewModel: NewsViewModel by sharedViewModel()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(webview){
            val unencodedHtml =
                newsViewModel.openDetailsEvent.value?.peekContent()?.content?.rendered
            val encodedHtml = Base64.encodeToString(unencodedHtml?.toByteArray(), Base64.NO_PADDING)
            loadData(encodedHtml, "text/html", "base64")
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }
    }
}