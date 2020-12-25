package com.example.bootydex.fragments.list

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.bootydex.R
import com.example.bootydex.viewmodel.BootyCallViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private lateinit var mBootyCallViewModel: BootyCallViewModel
    private lateinit var recyclerView : RecyclerView
    private lateinit var bootyAdapter : ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_list, container, false)

        //Adapter
        bootyAdapter = ListAdapter()
        //RecyclerView
        recyclerView = view.recyclerView
        recyclerView.adapter = bootyAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mBootyCallViewModel = ViewModelProvider(this).get(BootyCallViewModel::class.java)
        observeBootyData()
        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        return view
    }

    private fun observeBootyData(){
        mBootyCallViewModel.readAllData.observe(viewLifecycleOwner, Observer {booty ->
            bootyAdapter.setData(booty)
            setHasOptionsMenu(booty.isNotEmpty())
            if(booty.isNotEmpty()){
                list_layout.background = ResourcesCompat.getDrawable(resources,R.drawable.gradient_sexy, null)
            } else {
                list_layout.background = ResourcesCompat.getDrawable(resources,R.drawable.gradient, null)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllBootyCalls()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun deleteAllBootyCalls() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("yes"){ _ , _ ->
            mBootyCallViewModel.deleteAllBootyCalls()
            Toast.makeText(requireContext(), "Successfully removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_ , _ ->
        }
        builder.setTitle("Delete All Booties")

        builder.setMessage("Are you sure you want to delete all your captured BootyCalls?")
        builder.create().show()
    }


    private suspend fun getBitMap() : Bitmap{
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://cdn4.iconfinder.com/data/icons/avatars-xmas-giveaway/128/batman_hero_avatar_comics-512.png")
            .build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}