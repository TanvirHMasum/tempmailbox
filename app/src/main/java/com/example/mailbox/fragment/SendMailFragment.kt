package com.example.mailbox.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mailbox.R
import com.example.mailbox.databinding.FragmentSendMailBinding
import com.example.mailbox.model.AccResponse
import com.example.mailbox.model.LoginDataResponse
import com.example.mailbox.service.PreferencesProvider
import com.example.mailbox.service.RestClient
import com.example.mailbox.service.RestClientAuth
import com.example.mailbox.utils.SharedPrefData
import com.example.mailbox.utils.onClick
import com.example.mailbox.utils.snackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendMailFragment : BaseFragment() {
    private var _binding: FragmentSendMailBinding? = null

    private val binding: FragmentSendMailBinding get() = _binding!!
    private lateinit var preferenceProvider: PreferencesProvider
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendMailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceProvider = PreferencesProvider(requireActivity())
        emailAddressShow()

    }

    private fun emailAddressShow() {

        val userId = preferenceProvider.getString(SharedPrefData.USER_ID)
        RestClientAuth.authToken(preferenceProvider.getString(SharedPrefData.TOKEN)!!)
        val getAcc = RestClientAuth.get().getAccount(userId!!)
        getAcc.enqueue(object :
            Callback<AccResponse> {
            override fun onFailure(call: Call<AccResponse>, t: Throwable) {
                snackBar("Failed response!")
            }
            override fun onResponse(
                call: Call<AccResponse>,
                response: Response<AccResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    getDataSuccess(response.body()!!)
                } else {
                    snackBar("Something went wrong!!")
                }
            }
        })
    }

    private fun getDataSuccess(body: AccResponse) {
        val emailAddress = body.address

        preferenceProvider.putString(SharedPrefData.USER_ID, emailAddress)

        binding.tvUserAddress.text = emailAddress

        binding.sendMailBtn.onClick {

            sendToMail(emailAddress)
        }

    }

    private fun sendToMail(emailAddress: String) {

        val subject = binding.etMailSubject.text.toString().trim()
        val textMessage = binding.etMailText.text.toString().trim()

        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        //put the Subject in the intent
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put the message in the intent
        mIntent.putExtra(Intent.EXTRA_TEXT, textMessage)

        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        } catch (e: Exception) {

            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }

    }
}