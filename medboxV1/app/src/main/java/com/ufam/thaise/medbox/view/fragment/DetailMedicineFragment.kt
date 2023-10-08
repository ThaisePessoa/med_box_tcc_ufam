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
import com.ufam.thaise.medbox.Constant.data
import com.ufam.thaise.medbox.R
import com.ufam.thaise.medbox.databinding.FragmentDetailMedicineBinding
import com.ufam.thaise.medbox.viewmodel.MedBoxViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMedicineFragment : Fragment() {
    private val mViewModel: MedBoxViewModel by viewModels()
    private var _binding: FragmentDetailMedicineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMedicineBinding.inflate(inflater, container, false)
        val root: View = binding.root
        onConfigLayout()
        return root
    }


    private fun onConfigLayout() {
        binding.txtMediceDetail.text = data.name
        binding.txtRemaining.text = getString(R.string.restante, data.amount)
        onConfigLiveDataViewModel()
        onClickToolbar()
        onClickOpenMedBox()
        onClickEdit()
        onClickDelete()
    }

    private fun onClickOpenMedBox() {
        binding.layoutMedicineCard.itemLayoutBackgroud.setBackgroundResource(R.color.primary)
        binding.layoutMedicineCard.txtName.text = getString(R.string.open_medbox)
        binding.layoutMedicineCard.cardButtonMedbox.setOnClickListener {

        }

    }

    private fun onClickEdit() {
        binding.cardButtonEdit.setOnClickListener {

        }
    }

    private fun onClickDelete() {
        binding.cardButtonDelete.setOnClickListener {
            lifecycleScope.launch {
                mViewModel.deleteMedBox(data)
            }
        }
    }

    private fun onConfigLiveDataViewModel() {
        mViewModel.toastMensage.observe(viewLifecycleOwner) { mensage ->
            mensage?.let {
                Toast.makeText(requireContext(), mensage, Toast.LENGTH_SHORT).show()
                mViewModel.toastMensageMedBox(null)
            }
        }
        mViewModel.deleteSuccess.observe(viewLifecycleOwner) { mensage ->
            mensage?.let {
                Toast.makeText(requireContext(), mensage, Toast.LENGTH_SHORT).show()
                mViewModel.toastMensageMedBox(null)
                findNavController().popBackStack()
            }
        }

    }


    private fun onClickToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow)
        binding.toolbar.title = getString(R.string.adicionar_medicamento_detail)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.deleteSuccess.removeObservers(viewLifecycleOwner)
        mViewModel.toastMensage.removeObservers(viewLifecycleOwner)
    }
}