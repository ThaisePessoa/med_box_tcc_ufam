package com.ufam.thaise.medbox.view.fragment

import ListMedicineAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ufam.thaise.medbox.Constant
import com.ufam.thaise.medbox.Constant.data
import com.ufam.thaise.medbox.R
import com.ufam.thaise.medbox.databinding.FragmentListMedicineBinding
import com.ufam.thaise.medbox.model.entity.DataMedBox
import com.ufam.thaise.medbox.viewmodel.MedBoxViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListMedicineFragment:Fragment() {
    private lateinit var adapterMedBox:ListMedicineAdapter
    private val mViewModel: MedBoxViewModel by viewModels()
    private var _binding: FragmentListMedicineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMedicineBinding.inflate(inflater, container, false)
        val root: View = binding.root
        adapterMedBox = ListMedicineAdapter(::onClick)
        onConfigLayout()
        return root
    }
    private fun onConfigLiveDataViewModel() {
        mViewModel.toastMensage.observe(viewLifecycleOwner) { mensage ->
            mensage?.let {
                Toast.makeText(requireContext(), mensage, Toast.LENGTH_SHORT).show()
                mViewModel.toastMensageMedBox(null)
            }
        }
        mViewModel.toCheckList.observe(viewLifecycleOwner) { disable ->
            disable?.let {
                if (disable){
                   findNavController().popBackStack()
                }
            }
        }
    }
    private fun onClick(dataMedBox: DataMedBox, position: Int) {
        data  = dataMedBox
        when (position){
            0->Constant.position = "A"
            1->Constant.position = "B"
            2->Constant.position = "C"
            else ->Constant.position = "D"
        }
        findNavController().navigate(R.id.action_list_medicine_to_detail_medicine)
    }

    private fun onConfigLayout() {
        toCheckList()
        onClickToolbar()
        handleConfigAdapter()
        onConfigLiveDataViewModel()
    }

    private fun toCheckList() {
        lifecycleScope.launch {
            mViewModel.toCheckListVoid()
        }
    }

    private fun handleConfigAdapter() {
        lifecycleScope.launch {
            adapterMedBox.insertItems(mViewModel.getAllMedBox())
        }
        binding.rvMedBox.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMedBox.adapter = adapterMedBox
    }
    private fun onClickToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow)
        binding.toolbar.title = getString(R.string.adicionar_medicamento_list)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.toastMensage.removeObservers(viewLifecycleOwner)
    }
}