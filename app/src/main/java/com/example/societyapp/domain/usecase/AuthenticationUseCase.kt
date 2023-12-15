package com.example.societyapp.domain.usecase

import android.view.View
import com.example.societyapp.MainActivity
import com.example.societyapp.data.UserInfo
import com.example.societyapp.domain.repository.AuthRepository
import com.example.societyapp.presentation.enterMobile.EnterMobileFragment
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor
    (private  val authRepository: AuthRepository) {

        fun phoneNumberSignIn (phoneNumber: String,activity: MainActivity,fragment: EnterMobileFragment,view: View)=
        authRepository.phoneNumberSignIn(phoneNumber = phoneNumber,activity = activity,fragment = EnterMobileFragment(),view = view)

        fun verifyOtp(verificationId:String, otp:String) = authRepository.verifyOtp(verificationId=verificationId,otp = otp)

        fun isUserAuthenticated() = authRepository.isUserAuthenticated()

        fun getUserId() = authRepository.getUserId()

        fun createProfile(userInfo: UserInfo) = authRepository.createUserProfile(userInfo = userInfo)


}