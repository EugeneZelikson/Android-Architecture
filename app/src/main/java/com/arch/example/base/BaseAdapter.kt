package com.arch.example.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T : ViewDataBinding, P, V : BaseViewHolder<T, P>> :
    RecyclerView.Adapter<V>() {

    private val items = ArrayList<P>()

    override fun getItemCount(): Int = items.size

    /**
     * Creates exemplar of TrainingData Binding class.
     *
     * @param inflater - LayoutInflater for inflating view to ViewHolder.
     * @param parent   - ViewGroup that will contains RecyclerView items.
     * @return - exemplar of TrainingData Binding.
     */
    protected abstract fun getBinding(inflater: LayoutInflater, parent: ViewGroup): T

    /**
     * Creates and inflates ViewHolder for item.
     *
     * @param binding - exemplar of TrainingData Binding for bind view in ViewHolder.
     * @return - exemplar of ViewHolder with view and binding exemplar inside.
     */
    protected abstract fun getViewHolder(binding: T): V

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): V {
        val inflater = LayoutInflater.from(parent.context)
        return getViewHolder(getBinding(inflater, parent))
    }

    override fun onBindViewHolder(@NonNull holder: V, position: Int) {
        holder.bind(items[position], position)
    }

    /**
     * Clears current list of items and add new items.
     *
     * @param items - new list of data class objects.
     */
    fun setItems(items: List<P>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * Adds one item to list.
     *
     * @param item - new data object.
     */
    fun addItem(item: P) {
        items.add(item)
    }

    /**
     * Adds list of the items to current list.
     *
     * @param items - list with new data objects.
     */
    fun addItems(items: List<P>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItems(): List<P> {
        return items
    }

    fun getItem(position: Int): P {
        return items[position]
    }
}