package com.example.testrussia.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testrussia.Event
import com.example.testrussia.EventObserver
import com.example.testrussia.R
import com.example.testrussia.databinding.FragmentNewsBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NewsFragment : Fragment() {

    val newsViewModel: NewsViewModel by sharedViewModel()

    lateinit var viewDataBinding: FragmentNewsBinding

    lateinit var listAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding = FragmentNewsBinding.inflate(inflater, container, false).apply {
            viewmodel = newsViewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        newsViewModel.snackbarText.observe(viewLifecycleOwner, Observer {event->
            showSnackBarMessage(event, view)
        })
        newsViewModel.openDetailsEvent.observe(viewLifecycleOwner, EventObserver{
            val action = NewsFragmentDirections.actionNewsFragmentToDetailFragtament(it.id)
            findNavController().navigate(action)
        })
    }

}