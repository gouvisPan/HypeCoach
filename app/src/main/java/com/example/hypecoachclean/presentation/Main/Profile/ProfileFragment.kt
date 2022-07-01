package com.example.hypecoachclean.presentation.Main.Profile

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.hypecoachclean.R
import com.example.hypecoachclean.databinding.FragmentProfileBinding
import com.example.hypecoachclean.presentation.base.BaseFragment
import java.io.IOException
import java.util.*

class ProfileFragment : BaseFragment<ProfileViewModel,FragmentProfileBinding>() {

    companion object{
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMG_REQ_CODE = 10
    }

    private var mySelectedImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser()
        setClickListeners()
        observeViewModel()
    }



    private fun setClickListeners(){

        binding.btnUpdate.setOnClickListener {
                updateUserProfileData()
        }

        binding.etProfileUserAge.setOnClickListener {
            selectDate()
        }

        binding.profileUserImg.setOnClickListener {
            if(ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )== PackageManager.PERMISSION_GRANTED){

                showImagePicker()

            }else{
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }

    }

    private fun observeViewModel(){

        viewModel.user.observe(viewLifecycleOwner,Observer{

            Glide.with(this)
                .load(it.image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(binding.profileUserImg)

            binding.etProfileUserName.setText(it.name)
            binding.etProfileHeight.setText(it.height)
            binding.etProfileMail.setText(it.email)
            binding.etProfileUserAge.setText(it.birthString)

            if(it.genderString == "Female") binding.rbFemale.isEnabled = true
            if(it.genderString == "Other")  binding.rbOther.isEnabled = true

            binding.loadingViewProgressBar.visibility = View.GONE
        })

        viewModel.savedL.observe(viewLifecycleOwner,Observer{
            if(it){
                Toast.makeText(activity, "Profile Updated", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_profileFragment_to_mainScreenFragment)
            }
        })

    }

    private fun  showImagePicker(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMG_REQ_CODE)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_IMG_REQ_CODE && data!!.data != null){

                mySelectedImageUri = data.data

                try {

                    Glide.with(this)
                        .load(mySelectedImageUri)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_place_holder)
                        .into(binding.profileUserImg)

                }catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }

    }

    private fun updateUserProfileData(){

        val uName =  binding.etProfileUserName.text.toString()
        val uHeight= binding.etProfileHeight.text.toString()
        val uAge =   binding.etProfileUserAge.text.toString()
        val uGender : String

        if(binding.rbMale.isChecked){
            uGender = "Male"
        }else if(binding.rbFemale.isChecked){
            uGender = "Female"
        }else{
            uGender = "Other"
        }
        viewModel.updateIfNecessary(uName,uHeight,uAge,uGender,mySelectedImageUri)
        println(uGender)
    }

    private fun   selectDate(){
        val myCalendar = Calendar.getInstance()

        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(),
            DatePickerDialog.OnDateSetListener { _, sYear, sMonth, sDay ->

                val selectedDate = "$sDay/${sMonth + 1}/$sYear"
                binding.etProfileUserAge.setText(selectedDate)

            }, year, month, day)
        dpd.datePicker.maxDate = Date().time
        dpd.show()
    }



    override fun getViewModel()= ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentProfileBinding.inflate(inflater,container,false)

}