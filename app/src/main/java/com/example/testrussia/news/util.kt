package com.example.testrussia.news

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

 fun NewsFragment.setupListAdapter() {
    val viewModel = viewDataBinding.viewmodel
    if (viewModel != null) {
        listAdapter = NewsAdapter(viewModel)
        viewDataBinding.newsList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        viewDataBinding.lifecycleOwner = this
    } else {
        Log.e("Nurs","ViewModel not initialized when attempting to set up adapter.")
    }
    viewModel?.newsLiveList?.observe(viewLifecycleOwner, Observer {
        it?.let {
            listAdapter.submitList(it)
        }
    })
}