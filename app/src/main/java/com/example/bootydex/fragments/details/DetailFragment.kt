package com.example.bootydex.fragments.details

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bootydex.R
import kotlinx.android.synthetic.main.custom_row.view.tvAge
import kotlinx.android.synthetic.main.custom_row.view.tvLastName
import kotlinx.android.synthetic.main.custom_row.view.tvName
import kotlinx.android.synthetic.main.fragment_add.view.ivBoot
import kotlinx.android.synthetic.main.fragment_detail.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        view.ivBoot.apply {
            setImageURI(Uri.parse(args.currentBooty.image))
            setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentToUpdateFragment(args.currentBooty)
                findNavController().navigate(action)
            }
        }
        view.tvName.text = args.currentBooty.firstName
        view.tvLastName.text = args.currentBooty.lastName
        view.tvPhoneNumber.text = args.currentBooty.phoneNumber
        view.tvAptNumber.text = args.currentBooty.address?.apartmentNumber
        view.tvAddress.text = args.currentBooty.address?.streetAddress
        view.tvAge.text = args.currentBooty.age.toString()
        return view
    }

}