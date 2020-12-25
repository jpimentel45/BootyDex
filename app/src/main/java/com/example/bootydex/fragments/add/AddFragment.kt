package com.example.bootydex.fragments.add

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.bootydex.R
import com.example.bootydex.model.Address
import com.example.bootydex.model.BootyCall
import com.example.bootydex.viewmodel.BootyCallViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private val GALLERY_REQUEST: Int = 9

class AddFragment : Fragment() {
    private  var imageUri: Uri = Uri.parse("")
    private lateinit var mBootyCallViewModel: BootyCallViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun requestPermission(){
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_DOCUMENTS
        )
        for(permission in permissions){
            if(ActivityCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)){
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                }else{
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(requireActivity(), permissions, PERMISSION_CODE)
                }
            }else{
                getImageFromGallery()
            }
        }
    }

    companion object {
        //image pick code
//        private val GALLERY_REQUEST = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    getImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        mBootyCallViewModel = ViewModelProvider(this).get(BootyCallViewModel::class.java)
        view.button.setOnClickListener {
            lifecycleScope.launch {
                insertDataToDatabase()
            }
        }
        view.ivBoot.apply {
            setOnClickListener {
                requestPermission()
            }
            lifecycleScope.launch {
                background = BitmapDrawable(resources, getBitMap())
            }
        }
        return view
    }

    private suspend fun insertDataToDatabase() {
        val firstName = etName.text.toString()
        val lastName = etLastName.text.toString()
        val age = etAge.text.toString()
        val number = etPhoneNumber.text.toString()
//        val streetNumber = etAddressNumber.text.toString()
        val aptNumber = etAptNumber.text.toString()
        val streetAddress = etAddressName.text.toString()
        val address = Address(streetAddress, aptNumber)
//        val image = ivBoot.drawable.va
        if (inputCheck(firstName, lastName, age, number)) {
            val bootyCall = BootyCall(
                0,
                firstName,
                lastName,
                Integer.parseInt(age),
                number,
                address,
                imageUri.toString()
            )
            mBootyCallViewModel.addBootyCall(bootyCall)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()

            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Bitch fill everything out!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(
        firstName: String,
        lastName: String,
        age: String,
        number: String
    ): Boolean {
        return (firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty() && !TextUtils.isEmpty(
            age
        ))
    }

    private suspend fun getBitMap(): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://cdn4.iconfinder.com/data/icons/avatars-xmas-giveaway/128/batman_hero_avatar_comics-512.png")
            .build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    //open Phone Gallery
    private fun getImageFromGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        intent.type = "image/*"
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, GALLERY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            ivBoot.setImageURI(imageUri)
            ivBoot.background = null
        }
    }
}