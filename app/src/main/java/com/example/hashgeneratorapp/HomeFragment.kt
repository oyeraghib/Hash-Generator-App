package com.example.hashgeneratorapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hashgeneratorapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, hashAlgorithms)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        binding.btnGenerate.setOnClickListener{
            lifecycleScope.launch(Dispatchers.Main) {
                updateAnimations()
                navigateHomeTOSuccess()
            }
        }

        return binding.root

    }

    private suspend fun updateAnimations() {
        binding.tvTitle.animate().alpha(0f).setDuration(400L)
        binding.btnGenerate.animate().alpha(0f).setDuration(400L)

        binding.textInputLayout.animate().alpha(0f)
            .translationXBy(1200f)
            .setDuration(400L)

        binding.editTextPlainText.animate().alpha(0f)
            .translationXBy(-1200f)
            .setDuration(400L)

        delay(400L)

        binding.successFragmentBackground.animate()
            .alpha(1f).duration = 600L
        binding.successFragmentBackground.animate()
            .rotationBy(720f)
            .setDuration(600L)

        binding.successFragmentBackground.animate()
            .scaleXBy(900f).setDuration(800L)

        binding.successFragmentBackground.animate()
            .scaleYBy(900f).setDuration(800L)

        binding.ivCheck.animate().alpha(1f).setDuration(600L)
        delay(1500L)

    }

    private fun navigateHomeTOSuccess(){
        findNavController().navigate(R.id.actionHomeToSuccess)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.menu, menu)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}