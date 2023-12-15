package com.example.societyapp.presentation.verifyOtp

import android.view.View

interface VerificationOtpNavigator {

    interface VerificationOtpFragment {


        fun setOnclickListener(view: View)

        fun progressBarEnterMobileObserver()

        fun validEditTextShowError(): Boolean

        fun showProgressBarEnterMobile()

        fun hideProgressBarEnterMobile()

        fun checkAuthenticationStatus():Boolean

        fun signInSuccessObserver(view: View)

        fun showToastLengthLong(text: String)
    }
}