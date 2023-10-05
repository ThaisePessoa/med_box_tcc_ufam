package com.ufam.thaise.medbox.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ufam.thaise.medbox.R
import com.ufam.thaise.medbox.databinding.FragmentAddMedicineBinding

class AddMedicineFragment:Fragment() {

    private var _binding: FragmentAddMedicineBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentAddMedicineBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
        }

        configLayoutClick()
        return root
    }

    private fun configLayoutClick() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow)
        binding.toolbar.title = getString(R.string.adicionar_medicamento_add)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}