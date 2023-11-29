package com.example.projetothefinal.interfaceFrags.Home.CardFrags.Cards

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetothefinal.R
import com.example.projetothefinal.interfaceFrags.Home.CardFrags.EditList.FragList
import com.example.projetothefinal.interfaceFrags.Home.CardFrags.cardDetalhe.CardDetalhe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit.Sumario

class CardFragment : Fragment(), CardRecyclerAdapter.DetailedViewOnClick {

    private lateinit var viewModel: CardViewModel
    private val watchList = mutableListOf<Sumario>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Lifecycle", "CardFragment onViewCreated()")
        viewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        val button: Button = view.findViewById(R.id.btnEditlist)
        button.text = "Edit Watchlist"
        button.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.display_fragment, FragList(watchList))
                .addToBackStack("CardFragment")
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        val itemAdapter = CardRecyclerAdapter(requireContext(), watchList)
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recycleView)
        itemAdapter.setDetailedItemClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = itemAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun saveData() {
        val sharedPreferences = requireContext().getSharedPreferences("userWatchlist", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val json = gson.toJson(watchList)

        editor.putString("tickerList", json)
        editor.apply()
    }

    private fun retrieveData() {
        val sharedPreferences = requireContext().getSharedPreferences("userWatchlist", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("tickerList", "")

        val gson = Gson()
        val type = object : TypeToken<List<Sumario>>() {}.type

        val savedList = gson.fromJson<List<Sumario>>(json, type) ?: emptyList()
        watchList.clear()
        watchList.addAll(savedList)
    }

    fun onDetailedViewClick(SumarioDetalhe: Sumario) {
        Log.e("Tag", "Arrived on CardFragment")

        parentFragmentManager.beginTransaction()
            .replace(R.id.display_fragment, cardDetalhe(SumarioDetalhe))
            .addToBackStack(null)
            .commit()
    }
}
