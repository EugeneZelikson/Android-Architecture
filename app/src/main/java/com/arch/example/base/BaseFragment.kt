package com.arch.example.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import javax.inject.Inject

abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    protected lateinit var mBinding: V
    protected lateinit var mContext: Context

    abstract fun getFragmentLayout(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getFragmentLayout(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.lifecycleOwner = this
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mContext = context
    }

    fun showToast(
        text: String,
        length: Int = Toast.LENGTH_LONG,
        gravity: Int = Gravity.BOTTOM
    ) {
        Toast.makeText(mContext, text, length).apply {
            show()
            setGravity(gravity, 0, 81)
        }
    }
}