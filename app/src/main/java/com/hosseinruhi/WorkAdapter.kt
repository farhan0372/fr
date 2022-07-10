package com.hosseinruhi



import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hosseinruhi.databinding.ItemWorkBinding
import java.text.SimpleDateFormat
import java.util.*


class WorkAdapter(private val works: List<Works>) : RecyclerView.Adapter<WorkAdapter.UserViewHolder>() {

    lateinit var binding:ItemWorkBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : UserViewHolder {
        binding = ItemWorkBinding.inflate(LayoutInflater.from(parent.context))


        binding.root.setOnClickListener {
            Intent(binding.root.context,Main2Activity::class.java).apply {
                it.context.startActivity(this)
            }
        }

           // Toast.makeText(parent.context,"aa",Toast.LENGTH_SHORT).show()

        return UserViewHolder(binding)
    }


    override fun getItemCount() = works.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(works[position])
    }
    override fun getItemId(position: Int): Long {
        return works [position].id
    }



class UserViewHolder(private val binding: ItemWorkBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: Works) {

        with(binding.root) {
            val color = resources.getColor(R.color.blue,null)
            binding.viewColorTag.setBackgroundColor(color)
            binding.txtShowTitle.text = user.title
            binding.txtShowtask.text = user.description
            binding.txtTypeCategory.text= user.category
            updateTime(user.time)
            updateDate(user.date)


        }

    }

    private fun updateTime(time: Long) {
        val myFormat = "hh:mm a"
        val sdf = SimpleDateFormat(myFormat)
        binding.txtShowTime.text = sdf.format(Date(time))

    }

    private fun updateDate(date: Long) {
        val myFormat = "EEE, d MMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        binding.txtShowDate.text = sdf.format(Date(date))
    }


}

}



