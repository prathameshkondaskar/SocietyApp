package com.example.societyapp.presentation.enterMobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.societyapp.MainActivity
import com.example.societyapp.R
import com.example.societyapp.databinding.FragmentEnterMobileBinding
import com.example.societyapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class EnterMobileFragment : Fragment(), EnterMobileNavigator.EnterMobileFragment {

    private lateinit var binding : FragmentEnterMobileBinding
    private val viewModel : EnterMobileViewModel by viewModels()
    var otpValue = MutableStateFlow<String>("")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterMobileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnclickListener(view)
        progressBarEnterMobileObserver()
        signInSuccessObserver(view)
    }

    override fun setOnclickListener(view: View) {
        binding.buttonLogin.setOnClickListener{

            if(validEditTextShowError()){

                viewModel.signInWithPhoneNumber("+91 "+binding.editTextNumber.text.toString(),
                    activity as MainActivity,EnterMobileFragment(),view
                )
            }
        }
    }

    override fun progressBarEnterMobileObserver() {
        viewModel.progressBarEnterMobile.observe(viewLifecycleOwner) { result ->
            if (result) showProgressBarEnterMobile()
            else hideProgressBarEnterMobile()
        }
    }

    override fun signInSuccessObserver(view: View) {
        viewModel.signInSuccess.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Failure -> showToastLengthLong("Sign in fail: ${result.message}")
                is Resource.Success -> view?.let { openOtpFragment(view,"") }
                else -> {}
            }
        }
    }

    override fun showToastLengthLong(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    override fun validEditTextShowError(): Boolean {
        val mobileNo = binding.editTextNumber.text.toString()
        var valid  = false

        when{

            mobileNo.isEmpty() -> {
                binding.editTextNumber.setError("Enter mobile No.", null)
                binding.editTextNumber.requestFocus()
            }
            mobileNo.length > 10 || mobileNo.length < 10 -> {
                binding.editTextNumber.setError("Mobile No. length must be 10 digit.", null)
                binding.editTextNumber.requestFocus()
            }
            else -> {
            valid = true
            }
        }

        return valid
    }



    override fun userDetailsInit() {

    }

    override fun showProgressBarEnterMobile() {
        binding.progressEnterMobile.visibility = View.VISIBLE
    }

    override fun hideProgressBarEnterMobile() {
        binding.progressEnterMobile.visibility = View.GONE
    }

    override fun checkAuthenticationStatus(): Boolean {
        return viewModel.isUserAuthenticated()
    }


    fun openOtpFragment(view: View,verificationId:String) {

            val action = EnterMobileFragmentDirections.actionEnterMobileFragmentToVerificationOtpFragment(verificationId)
            Navigation.findNavController(view).navigate(action)

    }


}