package com.example.societyapp.presentation.verifyOtp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.societyapp.domain.usecase.AuthenticationUseCase
import com.example.societyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationOtpViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel()  {

    private val _progressBarVerifyOtp = MutableLiveData<Boolean>()
    val progressBarVerifyOtp: LiveData<Boolean> = _progressBarVerifyOtp

    private val _signInSuccess = MutableLiveData<Resource<Boolean>>()
    val signInSuccess: LiveData<Resource<Boolean>> = _signInSuccess


    fun signInWithAuthCredential(verificationId:String,otp:String ){
        viewModelScope.launch {
            authenticationUseCase.verifyOtp(verificationId, otp).collect{

                when(it){
                    is Resource.Loading ->{
                        _progressBarVerifyOtp.postValue(true)
                    }
                    is Resource.Success ->{
                        _progressBarVerifyOtp.postValue(false)
                        _signInSuccess.postValue(Resource.Success(  true))

                    }
                    is Resource.Failure ->{
                        _progressBarVerifyOtp.postValue(false)
                        _signInSuccess.postValue(Resource.Failure(it.message))
                    }

                    else -> {}
                }
            }
        }
    }

    fun isUserAuthenticated()= authenticationUseCase.isUserAuthenticated()
}