package com.bignerdranch.android.fiztehradio.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.fiztehradio.R
import com.bignerdranch.android.fiztehradio.Router
import com.bignerdranch.android.fiztehradio.fragments.HostFragment

class ClickableAdapter(activity: FragmentActivity) : RecyclerView.Adapter<ClickableViewHolder>() {

    private lateinit var router : Router
    private val mActivity: FragmentActivity = activity

    val arrayOfItems = arrayOf(
            "Ведущий 1",
            "Ведущий 2",
            "Ведущий 3",
            "Отстающий"
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        router = Router(mActivity, R.id.fragment_container)
        return ClickableViewHolder(
                inflater.inflate(R.layout.clickable_item, parent, false),
                ::onItemClick)
    }

    override fun getItemCount(): Int = arrayOfItems.size

    override fun onBindViewHolder(holder: ClickableViewHolder, position: Int) {
        holder.setText(arrayOfItems[position])
    }

    fun onItemClick(view: View, position: Int) {
        router.clearStackUntillLevel(2)//Так я убеждаюсь что в стеке только два фрагмета, MainFragment и HostListFragment
        router.navigateTo(true, fragmentFactory = ::HostFragment)
        Toast.makeText(view.context, arrayOfItems[position], Toast.LENGTH_SHORT).show()
    }

}

class ClickableViewHolder(view : View,
                          private val clickListener : (View, Int) -> Unit ) : RecyclerView.ViewHolder(view) {
    private val text: TextView = view.findViewById(R.id.text)

    init {
        view.setOnClickListener {
            clickListener(it, adapterPosition)
        }
    }

    fun setText(text : String) {
        this.text.text = text
    }
}