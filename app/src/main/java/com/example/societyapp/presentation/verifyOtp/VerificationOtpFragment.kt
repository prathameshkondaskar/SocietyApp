package com.example.societyapp.presentation.verifyOtp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.societyapp.databinding.FragmentEnterMobileBinding
import com.example.societyapp.databinding.FragmentVerificationOtpBinding
import com.example.societyapp.presentation.enterMobile.EnterMobileFragment
import com.example.societyapp.presentation.enterMobile.EnterMobileViewModel
import com.example.societyapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationOtpFragment : Fragment() ,VerificationOtpNavigator.VerificationOtpFragment {

    private lateinit var binding : FragmentVerificationOtpBinding
    private val viewModel : VerificationOtpViewModel by viewModels()
    private val args: VerificationOtpFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerificationOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnclickListener(view)
        progressBarEnterMobileObserver()
        signInSuccessObserver(view)
    }

    override fun setOnclickListener(view: View) {
        val verificationId = args.verification
        binding.btnVerify.setOnClickListener {
           val otp =  binding.etCodeSendVerification.text.toString()
            EnterMobileFragment().otpValue.value = binding.etCodeSendVerification.text.toString()
            Log.d("TAG", "setOnclickListener: "+EnterMobileFragment().otpValue.value)
            viewModel.signInWithAuthCredential(verificationId,otp)
        }

    }


    override fun progressBarEnterMobileObserver() {
        viewModel.progressBarVerifyOtp.observe(viewLifecycleOwner) { result ->
            if (result) showProgressBarEnterMobile()
            else hideProgressBarEnterMobile()
        }
    }

    override fun validEditTextShowError(): Boolean {
        return false
    }

    override fun showProgressBarEnterMobile() {
        binding.progressVerifyOtp.visibility = View.VISIBLE
    }

    override fun hideProgressBarEnterMobile() {
        binding.progressVerifyOtp.visibility = View.GONE
    }

    override fun checkAuthenticationStatus(): Boolean {
        return viewModel.isUserAuthenticated()
    }

    override fun signInSuccessObserver(view: View) {
        viewModel.signInSuccess.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Failure -> showToastLengthLong("Sign in fail: ${result.message}")
                is Resource.Success -> showToastLengthLong("Success")
                else -> {}
            }
        }
    }

    override fun showToastLengthLong(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

}