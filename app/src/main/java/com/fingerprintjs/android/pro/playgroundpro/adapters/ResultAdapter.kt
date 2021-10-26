package com.fingerprintjs.android.pro.playgroundpro.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fingerprintjs.android.pro.playgroundpro.R
import com.fingerprintjs.android.pro.playgroundpro.demo.ResultItemViewImpl
import com.fingerprintjs.android.pro.playgroundpro.demo.api.Verdict


class ResultAdapter(
    private val context: Context
) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    private var dataset: List<Verdict> = emptyList()

    inner class ViewHolder(var cardView: ResultItemViewImpl) : RecyclerView.ViewHolder(
        cardView
    )

    fun setDataset(dataset: List<Verdict>) {
        this.dataset = dataset
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.result_item, parent, false) as ResultItemViewImpl

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardView.setName(dataset[position].name)
        holder.cardView.setValue(dataset[position].value)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
