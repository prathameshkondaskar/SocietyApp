package com.example.societyapp.presentation.enterMobile

import android.view.View
import com.example.societyapp.domain.model.UserAuth
import com.example.societyapp.domain.model.UserDetails

interface EnterMobileNavigator {

    interface EnterMobileFragment{

        fun setOnclickListener(view: View)

        fun progressBarEnterMobileObserver()

        fun validEditTextShowError(): Boolean

        fun userDetailsInit()

        fun showProgressBarEnterMobile()

        fun hideProgressBarEnterMobile()

        fun checkAuthenticationStatus():Boolean

        fun signInSuccessObserver(view: View)

        fun showToastLengthLong(text: String)

    }

    interface EnterMobileViewModel{

        fun sendVerificationCode(userAuth: UserAuth)

        fun getCurrentUserUid()

        fun deleteCurrentUserAuth()

        fun saveUserDetails(userDetails: UserDetails)

    }
}