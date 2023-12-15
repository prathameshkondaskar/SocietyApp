package com.example.societyapp.presentation.enterMobile

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.societyapp.MainActivity
import com.example.societyapp.domain.model.UserAuth
import com.example.societyapp.domain.model.UserDetails
import com.example.societyapp.domain.usecase.AuthenticationUseCase
import com.example.societyapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterMobileViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() , EnterMobileNavigator.EnterMobileViewModel {


    private val _progressBarEnterMobile = MutableLiveData<Boolean>()
    val progressBarEnterMobile: LiveData<Boolean> = _progressBarEnterMobile

    private val _signInSuccess = MutableLiveData<Resource<Boolean>>()
    val signInSuccess: LiveData<Resource<Boolean>> = _signInSuccess


    fun signInWithPhoneNumber(phoneNumber: String , activity: MainActivity ,fragment: EnterMobileFragment,view:View){

        viewModelScope.launch {

            authenticationUseCase.phoneNumberSignIn(phoneNumber,activity, fragment,view).collect{
                when(it){

                    is Resource.Loading ->{
                        _progressBarEnterMobile.postValue(true)
                    }
                    is Resource.Success ->{
                        _progressBarEnterMobile.postValue(false)
                        _signInSuccess.postValue(Resource.Success(  true))

                    }
                    is Resource.Failure ->{
                        _progressBarEnterMobile.postValue(false)
                        _signInSuccess.postValue(Resource.Failure(it.message))
                    }

                    else -> {}
                }
            }
        }

    }
    fun isUserAuthenticated()= authenticationUseCase.isUserAuthenticated()

    override fun sendVerificationCode(userAuth: UserAuth) {
        TODO("Not yet implemented")
    }

    override fun getCurrentUserUid() {
        TODO("Not yet implemented")
    }

    override fun deleteCurrentUserAuth() {
        TODO("Not yet implemented")
    }

    override fun saveUserDetails(userDetails: UserDetails) {
        TODO("Not yet implemented")
    }


}