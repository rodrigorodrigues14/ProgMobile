package com.example.projetothefinal

import Frag1RecyclerAdapter
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Frag1 : Fragment() {


    companion object {
        fun newInstance() = Frag1()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_frag2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = Frag1ViewModel()
        viewModel.symbolSummaryList.observe(viewLifecycleOwner){ symbolSummaryList ->
            val adapter = Frag1RecyclerAdapter(symbolSummaryList)
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }

    }


}
