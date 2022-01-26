package com.example.mailbox.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mailbox.R
import com.example.mailbox.activity.HomeActivity
import com.example.mailbox.databinding.FragmentSignInBinding
import com.example.mailbox.model.LoginAccData
import com.example.mailbox.model.LoginDataResponse
import com.example.mailbox.service.PreferencesProvider
import com.example.mailbox.service.RestClient
import com.example.mailbox.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferenceProvider: PreferencesProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginAccBtn.onClick { if (validate()) doLogin() }
    }

    private fun validate(): Boolean {
        return when {
            binding.etLogEmail.checkIsEmpty() -> {
                binding.etLogEmail.showError(getString(R.string.error_field_required))
                false
            }
            binding.etLogPassword.checkIsEmpty() -> {
                binding.etLogPassword.showError(getString(R.string.error_field_required))
                false
            }
            else -> true
        }
    }

    private fun doLogin() {
        preferenceProvider = PreferencesProvider(requireActivity())

        val userEmail = binding.etLogEmail.text.toString().trim()
        val userPassword = binding.etLogPassword.text.toString().trim()

        val loginAccountData = LoginAccData(
            userEmail,
            userPassword
        )

        val createAcc = RestClient.get().loginAccount(loginAccountData)
        createAcc.enqueue(object :
            Callback<LoginDataResponse> {
            override fun onFailure(call: Call<LoginDataResponse>, t: Throwable) {
                snackBar("Failed response!")
            }

            override fun onResponse(
                call: Call<LoginDataResponse>,
                response: Response<LoginDataResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    loginSuccess(response.body()!!)
                    snackBar("Successfully Login")
                } else {
                    snackBar("Something went wrong!!")
                }
            }
        })
    }


    private fun loginSuccess(loginData: LoginDataResponse) {
        preferenceProvider.putBoolean(SharedPrefData.IS_LOGGED_IN, true)
        preferenceProvider.putString(SharedPrefData.USER_ID, loginData.id)
        preferenceProvider.putString(SharedPrefData.TOKEN, loginData.token)
        activity?.launchActivityWithNewTask<HomeActivity>()

    }
}