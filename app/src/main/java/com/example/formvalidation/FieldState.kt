package com.example.formvalidation

sealed class FieldState(){
    object Empty : FieldState()
    object Valid : FieldState()
    data class Invalid(val error : Int) : FieldState()
}


sealed class FormState(){
    object Valid : FormState()
    object InValid : FormState()
    object Empty : FormState()
}