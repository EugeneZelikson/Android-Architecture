package com.arch.example.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import javax.inject.Inject

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var mBinding: V

    abstract fun getActivityLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getActivityLayout())
    }

    fun showToast(
        text: String,
        length: Int = Toast.LENGTH_LONG,
        gravity: Int = Gravity.BOTTOM
    ) {
        Toast.makeText(this, text, length).apply {
            setGravity(gravity, 0, 0)
            show()
        }
    }
}