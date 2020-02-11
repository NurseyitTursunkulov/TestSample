package com.example.testrussia.news

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testrussia.Event
import com.google.android.material.snackbar.Snackbar

fun NewsFragment.setupListAdapter() {
    val viewModel = viewDataBinding.viewmodel
    if (viewModel != null) {
        listAdapter = NewsAdapter(viewModel)
        viewDataBinding.newsList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
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

fun NewsFragment.showSnackBarMessage(
    event: Event<String>,
    view: View
) {
    event.getContentIfNotHandled()?.let {
        Snackbar.make(view, it, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }
}