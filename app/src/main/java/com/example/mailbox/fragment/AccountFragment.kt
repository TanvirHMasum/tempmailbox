package com.example.mailbox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mailbox.R
import com.example.mailbox.databinding.FragmentAccountBinding
import com.example.mailbox.service.PreferencesProvider
import com.example.mailbox.utils.SharedPrefData

class AccountFragment : BaseFragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding: FragmentAccountBinding get() = _binding!!

    private lateinit var preferenceProvider: PreferencesProvider


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceProvider = PreferencesProvider(requireActivity())
        //implementation
    }
}