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
import androidx.navigation.fragment.findNavController
import com.ufam.thaise.medbox.R
import com.ufam.thaise.medbox.databinding.FragmentAddMedicineBinding
import com.ufam.thaise.medbox.model.entity.DataMedBox
import com.ufam.thaise.medbox.viewmodel.AddMedicineViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class AddMedicineFragment:Fragment() {

    private val mViewModel: AddMedicineViewModel by viewModels()
    private var _binding: FragmentAddMedicineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        mViewModel = ViewModelProvider(this).get(AddMedicineViewModel::class.java)
        _binding = FragmentAddMedicineBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mViewModel.toastMensage.observe(viewLifecycleOwner) {mensage->
            mensage?.let {
                Toast.makeText(requireContext(),mensage, Toast.LENGTH_SHORT).show()
                mViewModel.handleToastMensage(null)
            }
        }
        mViewModel.numberAmount.observe(viewLifecycleOwner ){ number->
            number?.let {
                binding.txtNumberAmount.text = number
            }
        }
        configLayoutClick()
        return root
    }


    private fun configLayoutClick() {
        binding.txtNumberAmount.text = "0"
        onClickToolbar()
        onClickAmount()
        onClickSave()
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
        binding.btnAdd.setOnClickListener{
            val txtNumber = binding.txtNumberAmount.text.toString().toInt()
            mViewModel.handleTxtAmountAdd (txtNumber)
        }
    }

    private fun handleBtnSave() {
        val dataSave = DataMedBox(
                name = binding.fieldMedicamento.text.toString(),
                amount = binding.txtNumberAmount.text.toString())
       mViewModel.handleSave(dataSave)
    }

    private fun onClickToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow)
        binding.toolbar.title = getString(R.string.adicionar_medicamento_add)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.toastMensage.removeObservers(viewLifecycleOwner)
        mViewModel.numberAmount.removeObservers(viewLifecycleOwner)
    }
}