package com.example.formvalidation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.formvalidation.databinding.FragmentFirstFormBinding


class FirstFormFragment : Fragment(R.layout.fragment_first_form) {

    private lateinit var binding: FragmentFirstFormBinding

    private val viewModel: FirstFormViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFirstFormBinding.bind(view)

        binding.tlName.editText!!.doOnTextChanged { text, start, before, count ->
            viewModel._nameLiveData.value = text.toString()
        }
        binding.tlEmail.editText!!.doOnTextChanged { text, start, before, count ->
            viewModel._emailLiveData.value = text.toString()
        }

        viewModel.formState.observe(viewLifecycleOwner) {
            if (it is FormState.Valid) {
                findNavController().navigate(R.id.action_firstFormFragment_to_secondFormFragment)
                viewModel.resetFormValidState()

            }
        }

        viewModel.nameErrorLiveData.observe(viewLifecycleOwner) {
            when (it) {
                FieldState.Empty -> {
                }
                FieldState.Valid -> {
                    binding.tlName.error = ""
                }
                is FieldState.Invalid -> {
                    binding.tlName.error = getString(it.error)
                }
            }
        }

        viewModel.emailErrorLiveData.observe(viewLifecycleOwner) {
            when (it) {
                FieldState.Empty -> {
                }
                FieldState.Valid -> {
                    binding.tlEmail.error = ""
                }
                is FieldState.Invalid -> {
                    binding.tlEmail.error = getString(it.error)
                }
            }
        }

        binding.button.setOnClickListener {
            viewModel.isFormValid()
        }


    }
}