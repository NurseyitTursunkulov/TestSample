package com.example.testrussia.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.testrussia.R
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NewsFragment : Fragment() {

    val newsViewModel:NewsViewModel by viewModel()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel.newsRepository
        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            val action =
                NewsFragmentDirections.actionFirstFragmentToSecondFragment(
                    "From FirstFragment"
                )
            findNavController().navigate(action)
        }
    }
}