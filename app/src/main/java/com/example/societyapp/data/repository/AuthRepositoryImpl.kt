package com.example.societyapp.data.repository

import android.util.Log
import android.view.View
import com.example.societyapp.MainActivity
import com.example.societyapp.data.UserInfo
import com.example.societyapp.domain.repository.AuthRepository
import com.example.societyapp.presentation.enterMobile.EnterMobileFragment
import com.example.societyapp.util.Resource
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor
    (var firebaseAuth: FirebaseAuth,var firebaseFirestore: FirebaseFirestore) : AuthRepository{

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun phoneNumberSignIn(phoneNumber: String, activity: MainActivity,fragment: EnterMobileFragment,view: View): Flow<Resource<Boolean>> =
        channelFlow {
            try {
                trySend(Resource.Loading).isSuccess
                val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                    .setPhoneNumber(phoneNumber)
                    .setActivity(activity)
                    .setTimeout(60,TimeUnit.SECONDS)
                    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                            coroutineScope.launch {
                                signInWithAuthCredential(p0)
                            }
                        }

                        override fun onVerificationFailed(p0: FirebaseException) {
                            coroutineScope.launch {
                                trySend(Resource.Failure(p0.localizedMessage  ?: "An Error Occurred")).isSuccess
                            }
                        }

                        override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken
                        ) {
                            coroutineScope.launch {
                                withContext(Dispatchers.Main){
                                    fragment.openOtpFragment(view,verificationId)
                                }
                                fragment.otpValue.collect {
                                    if (it.isNotBlank()) {
                                        trySend(
                                            signInWithAuthCredential(
                                                PhoneAuthProvider.getCredential(
                                                    verificationId, it
                                                )
                                            )
                                        ).isSuccess
                                    }
                                }
                            }
                        }

                    }).build()
                PhoneAuthProvider.verifyPhoneNumber(options)
                awaitClose()
            }catch (exception: Exception) {
                Resource.Failure(exception.localizedMessage ?: "An Error Occurred")
            }

        }

    override fun verifyOtp(verificationId: String, otp: String): Flow<Resource<Boolean>> =

            channelFlow {
                try{
                val phoneAuthCredential = PhoneAuthProvider.getCredential(
                    verificationId, otp
                )

                trySend(Resource.Loading)
                firebaseAuth.signInWithCredential(phoneAuthCredential)
                    .addOnSuccessListener {
                        coroutineScope.launch {
                            Log.d("TAG", "verifyOtp: Login Success")
                            trySend(Resource.Success(true)).isSuccess
                        }
                    }
                    .addOnFailureListener { exception ->
                        coroutineScope.launch {
                            trySend(
                                Resource.Failure(
                                    exception.localizedMessage ?: "An Error Occured"
                                )
                            )
                        }
//                    Resource.Failure(exception.localizedMessage ?: "An Error Occured")
                    }
                    awaitClose()
            }
            catch(exception: Exception) {
                Resource.Failure(exception.localizedMessage ?: "An Error Occurred")
            }
        }


    override fun isUserAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun getUserId(): String {
        return firebaseAuth.currentUser?.let {
            it.uid
        } ?: ""
    }

    override suspend fun signInWithAuthCredential(phoneAuthCredential: PhoneAuthCredential): Resource<Boolean> =
        suspendCoroutine {continuation ->
            firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnSuccessListener {
                    continuation.resume(Resource.Success(true))
                }
                .addOnFailureListener { exception->
                    continuation.resume(
                        Resource.Failure(exception.localizedMessage ?: "An Error Occured")
                    )
                }

        }

    override fun createUserProfile(userInfo: UserInfo): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
    }
}