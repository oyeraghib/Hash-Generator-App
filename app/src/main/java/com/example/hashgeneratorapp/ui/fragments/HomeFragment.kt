package com.example.hashgeneratorapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashgeneratorapp.R
import com.example.hashgeneratorapp.databinding.FragmentHomeBinding
import com.example.hashgeneratorapp.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        //viewModel
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setHasOptionsMenu(true)

        binding.btnGenerate.setOnClickListener {
            onGenerateClicked()
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuClear){
            binding.editTextPlainText.text.clear()
            return true
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, hashAlgorithms)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    private fun getHashData(): String{
        val algorithm = binding.autoCompleteTextView.text.toString()
        val text = binding.editTextPlainText.text.toString()

        return homeViewModel.getHash(text, algorithm)
    }

    private fun onGenerateClicked() {
        if (binding.editTextPlainText.text.isEmpty()) {
            showSnackBar("Field Empty")
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                updateAnimations()
                val hash = getHashData()
                navigateHomeTOSuccess(hash)
            }
        }

    }

    private suspend fun updateAnimations() {
        binding.btnGenerate.isClickable = false

        binding.tvTitle.animate().alpha(0f).setDuration(400L)
        binding.btnGenerate.animate().alpha(0f).setDuration(400L)

        binding.textInputLayout.animate()
            .alpha(0f)
            .translationXBy(1200f)
            .setDuration(400L)

        binding.editTextPlainText.animate()
            .alpha(0f)
            .translationXBy(-1200f)
            .setDuration(400L)

        delay(600L)

        binding.successFragmentBackground.animate()
            .alpha(1f).setDuration(600L)

        binding.successFragmentBackground.animate()
            .rotationBy(720f)
            .setDuration(600L)

        binding.successFragmentBackground.animate()
            .scaleXBy(900f).setDuration(800L)

        binding.successFragmentBackground.animate()
            .scaleYBy(900f).setDuration(800L)

        binding.ivCheck.animate()
            .alpha(1f)
            .setDuration(1000L)

        delay(1500L)

    }

    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(
            binding.rootLayout,
            message,
            Snackbar.LENGTH_SHORT
        )
        snackBar.setAction("Okay") {}
        snackBar.show()
    }

    private fun navigateHomeTOSuccess(hash: String) {
        val action = HomeFragmentDirections.actionHomeToSuccess(hash)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}