package com.example.formvalidation

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class FirstFormViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val _nameLiveData = savedStateHandle.getLiveData<String>("name", "")
    val _emailLiveData = savedStateHandle.getLiveData<String>("email", "")

    private val _nameErrorLiveData = MutableLiveData<FieldState>()
    val nameErrorLiveData: LiveData<FieldState>
        get() = _nameErrorLiveData

    private val _emailErrorLivaData = MutableLiveData<FieldState>()
    val emailErrorLiveData: LiveData<FieldState>
        get() = _emailErrorLivaData

    private val _formStateLiveData = MutableLiveData<FormState>()
    val formState: LiveData<FormState>
        get() = _formStateLiveData

    fun isFormValid() {

        val isNameValid = _nameLiveData.value!!.isNotBlank()
        _nameErrorLiveData.value =
            if (isNameValid) FieldState.Valid else FieldState.Invalid(R.string.invalid_name)

        val isEmailValid = _emailLiveData.value!!.isEmailValid()
        _emailErrorLivaData.value =
            if (isEmailValid) FieldState.Valid else FieldState.Invalid(R.string.invalid_email)

        val isFormValid = isNameValid.and(isEmailValid)
        _formStateLiveData.value = if (isFormValid) FormState.Valid else FormState.InValid
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }

    fun resetFormValidState() {
        _formStateLiveData.value = FormState.Empty
    }
}