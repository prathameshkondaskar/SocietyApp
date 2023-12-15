package com.example.societyapp.di

import com.example.societyapp.data.repository.AuthRepositoryImpl
import com.example.societyapp.domain.repository.AuthRepository
import com.example.societyapp.domain.usecase.AuthenticationUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth,firebaseFirestore: FirebaseFirestore):AuthRepository{
        return AuthRepositoryImpl(firebaseAuth,firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideAuthenticationUseCase(authRepository: AuthRepository): AuthenticationUseCase {
        return AuthenticationUseCase(authRepository)
    }






}