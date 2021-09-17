package com.example.hashgeneratorapp.ui.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hashgeneratorapp.databinding.FragmentSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SuccessFragment : Fragment() {

    private val args: SuccessFragmentArgs by navArgs()

    private var _binding: FragmentSuccessBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)
        _binding?.tvHash?.text = args.hash
        Log.d("Success", args.hash )

        _binding?.btnCopy?.setOnClickListener{
            lifecycleScope.launch {
                onCopyClicked()
                messageCopied()
            }
        }

        return binding.root
    }

    private suspend fun messageCopied() {
        _binding?.include?.tvMessage?.animate()?.translationY(80f)
            ?.setDuration(200L)

        _binding?.include?.viewMessage?.animate()?.translationY(80f)
            ?.setDuration(200L)

        delay(5000L)

        _binding?.include?.tvMessage?.animate()?.translationY(-80f)
            ?.setDuration(500L)

        _binding?.include?.viewMessage?.animate()?.translationY(-80f)
            ?.setDuration(500L)
    }

    private fun onCopyClicked() {
        copyToClipboard(args.hash)
    }

    private fun copyToClipboard(hash: String) {
        val clipboardManager =  requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Encryption", hash)
        clipboardManager.setPrimaryClip(clipData)
    }

}