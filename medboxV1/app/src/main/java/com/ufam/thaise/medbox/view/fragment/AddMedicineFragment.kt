package com.ufam.thaise.medbox.view.fragment

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
import com.ufam.thaise.medbox.R
import com.ufam.thaise.medbox.databinding.FragmentAddMedicineBinding
import com.ufam.thaise.medbox.model.entity.DataMedBox
import com.ufam.thaise.medbox.viewmodel.MedBoxViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddMedicineFragment : Fragment() {

    private val mViewModel: MedBoxViewModel by viewModels()
    private var _binding: FragmentAddMedicineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddMedicineBinding.inflate(inflater, container, false)
        val root: View = binding.root
        onConfigLayout()
        return root
    }


    private fun onConfigLayout() {
        onConfigLiveDataViewModel()
        binding.txtNumberAmount.text = "0"
        onClickToolbar()
        onClickAmount()
        onClickSave()
    }

    private fun onConfigLiveDataViewModel() {
        mViewModel.toastMensage.observe(viewLifecycleOwner) { mensage ->
            mensage?.let {
                Toast.makeText(requireContext(), mensage, Toast.LENGTH_SHORT).show()
                mViewModel.toastMensageMedBox(null)
            }
        }
        mViewModel.numberAmount.observe(viewLifecycleOwner) { number ->
            number?.let {
                binding.txtNumberAmount.text = number
            }
        }
        mViewModel.saveSuccess.observe(viewLifecycleOwner) { mensage ->
            mensage?.let {
                Toast.makeText(requireContext(), mensage, Toast.LENGTH_SHORT).show()
                mViewModel.toastMensageMedBox(null)
                findNavController().popBackStack()
            }
        }
    }

    private fun onClickSave() {
        binding.btnSave.setOnClickListener {
            handleBtnSave()
        }
    }

    private fun onClickAmount() {
        binding.btnMinus.setOnClickListener {
            val txtNumber = binding.txtNumberAmount.text.toString().toInt()
            mViewModel.handleTxtAmountminus(txtNumber)
        }
        binding.btnAdd.setOnClickListener {
            val txtNumber = binding.txtNumberAmount.text.toString().toInt()
            mViewModel.TxtAmountAddMedBox(txtNumber)
        }
    }

    private fun handleBtnSave() {
        val dataSave = DataMedBox(
            name = binding.fieldMedicamento.text.toString(),
            amount = binding.txtNumberAmount.text.toString()
        )
        lifecycleScope.launch {
            mViewModel.saveMedBox(dataSave)
        }
    }

    private fun onClickToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow)
        binding.toolbar.title = getString(R.string.adicionar_medicamento_add)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.toastMensage.removeObservers(viewLifecycleOwner)
        mViewModel.numberAmount.removeObservers(viewLifecycleOwner)
        mViewModel.saveSuccess.removeObservers(viewLifecycleOwner)
    }
}