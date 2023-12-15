package com.example.societyapp.domain.repository

import android.app.Activity
import android.view.View
import com.example.societyapp.MainActivity
import com.example.societyapp.data.UserInfo
import com.example.societyapp.presentation.enterMobile.EnterMobileFragment
import com.example.societyapp.util.Resource
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

        fun phoneNumberSignIn(phoneNumber: String , activity: MainActivity,fragment: EnterMobileFragment,view: View): Flow<Resource<Boolean>>

        fun verifyOtp(verificationId:String,otp:String):Flow<Resource<Boolean>>

        fun isUserAuthenticated():Boolean

        fun getUserId():String

        suspend fun signInWithAuthCredential(phoneAuthCredential: PhoneAuthCredential):Resource<Boolean>

        fun createUserProfile(userInfo: UserInfo) : Flow<Resource<Boolean>>

        suspend fun signOut(): Flow<Resource<Boolean>>
}