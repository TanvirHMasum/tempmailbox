package com.example.mailbox.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mailbox.R
import com.example.mailbox.databinding.FragmentCreateAccBinding
import com.example.mailbox.model.CrateAccData
import com.example.mailbox.model.CreateAccResponse
import com.example.mailbox.service.PreferencesProvider
import com.example.mailbox.service.RestClient
import com.example.mailbox.utils.checkIsEmpty
import com.example.mailbox.utils.onClick
import com.example.mailbox.utils.showError
import com.example.mailbox.utils.snackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccFragment : BaseFragment() {
    private var _binding: FragmentCreateAccBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferenceProvider: PreferencesProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createAccBtn.onClick { if (validate()) createAccount() }
    }

    private fun validate(): Boolean {
        return when {
            binding.etEmail.checkIsEmpty() -> {
                binding.etEmail.showError(getString(R.string.error_field_required))
                false
            }
            binding.etPassword.checkIsEmpty() -> {
                binding.etPassword.showError(getString(R.string.error_field_required))
                false
            }
            else -> true
        }
    }

    private fun createAccount() {
        preferenceProvider = PreferencesProvider(requireActivity())

        val userEmail = binding.etEmail.text.toString().trim()
        val userPassword = binding.etPassword.text.toString().trim()

        val mailCreateData = CrateAccData(
            userEmail,
            userPassword
        )

        val createAcc = RestClient.get().addAccount(mailCreateData)
        createAcc.enqueue(object :
            Callback<CreateAccResponse> {
            override fun onFailure(call: Call<CreateAccResponse>, t: Throwable) {
                snackBar("Failed response!")
            }

            override fun onResponse(call: Call<CreateAccResponse>, response: Response<CreateAccResponse>) {
                if (response.code() == 201 && response.body() != null) {
                    //loginSuccess(response.body()!!)
                    snackBar("Successfully Create Account")
                }else{
                    snackBar("Something went wrong!!")
                }
            }
        })
    }
}