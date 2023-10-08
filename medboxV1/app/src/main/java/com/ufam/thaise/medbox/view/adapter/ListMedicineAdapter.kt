import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ufam.thaise.medbox.R
import com.ufam.thaise.medbox.databinding.ItemMedicineCardBinding
import com.ufam.thaise.medbox.model.entity.DataMedBox

class ListMedicineAdapter(private val onClick: (codeCopy: DataMedBox) -> Unit) :
    RecyclerView.Adapter<ListMedicineAdapter.MedBoxViewHolder>() {
    private val list: MutableList<DataMedBox> = mutableListOf()


    inner class MedBoxViewHolder(private val binding: ItemMedicineCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataMedBox) {
            if (!item.name.isNullOrEmpty()) {
                binding.txtName.text = item.name
                binding.itemLayoutBackgroud.setBackgroundResource(R.color.primary)
                itemView.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedBoxViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMedicineCardBinding.inflate(inflater, parent, false)
        return MedBoxViewHolder(binding)
    }


    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MedBoxViewHolder, position: Int) {
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun insertItems(list: List<DataMedBox>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

}