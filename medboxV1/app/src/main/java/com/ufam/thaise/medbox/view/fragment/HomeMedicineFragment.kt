package com.ufam.thaise.medbox.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ufam.thaise.medbox.R
import com.ufam.thaise.medbox.databinding.FragmentHomeBinding
import com.ufam.thaise.medbox.viewmodel.MedBoxViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeMedicineFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mViewModel: MedBoxViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        configLayoutClick()
        return root
    }

    private fun configLayoutClick() {
        onConfigLiveDataViewModel()
        lifecycleScope.launch {
            mViewModel.toCheckAdd()
        }
        lifecycleScope.launch {
            mViewModel.toCheckListVoid()
        }
        binding.cardButtonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_add_medicine)
        }
        binding.cardButtonList.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_list_medicine)
        }
    }

    private fun onConfigLiveDataViewModel() {
        mViewModel.disableAdd.observe(viewLifecycleOwner) { disable ->
           if (disable){
               binding.cardButtonAdd.isClickable = false
               binding.addLayoutBackgroud.setBackgroundResource(R.color.disable)
           }else {
               binding.cardButtonAdd.isClickable = true
               binding.addLayoutBackgroud.setBackgroundResource(R.color.primary)
           }
        }
        mViewModel.toCheckList.observe(viewLifecycleOwner) { disable ->
            disable?.let {
                if (disable){
                    binding.cardButtonList.isClickable = false
                    binding.listLayoutBackgroud.setBackgroundResource(R.color.disable)
                }else {
                    binding.cardButtonList.isClickable = true
                    binding.listLayoutBackgroud.setBackgroundResource(R.color.primary)
                }
            }
        }
        mViewModel.toastMensage.observe(viewLifecycleOwner) { mensage ->
            mensage?.let {
                Toast.makeText(requireContext(), mensage, Toast.LENGTH_SHORT).show()
                mViewModel.toastMensageMedBox(null)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.disableAdd.removeObservers(viewLifecycleOwner)
        mViewModel.toastMensage.removeObservers(viewLifecycleOwner)
    }
}