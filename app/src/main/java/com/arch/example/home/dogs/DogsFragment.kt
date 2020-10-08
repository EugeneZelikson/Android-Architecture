package com.arch.example.home.dogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arch.example.R
import com.arch.example.base.BaseFragment
import com.arch.example.databinding.FragmentDogsBinding
import com.arch.example.viewmodel.DogsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogsFragment : BaseFragment<FragmentDogsBinding>() {

    private val viewModel: DogsViewModel by viewModels()
    private val dogsAdapter = DogsRecyclerAdapter()

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_dogs
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getDogsListFromDB()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel

        mBinding.recyclerView.layoutManager = LinearLayoutManager(mContext)
        mBinding.recyclerView.adapter = dogsAdapter

        initObservers()
    }

    private fun initObservers() {
        viewModel.dogsList.observe(viewLifecycleOwner) {
            dogsAdapter.setItems(it.images)
        }

        viewModel.dogsResponseRemote.observe(viewLifecycleOwner, { response ->
            response?.let {
                viewModel.remoteDogsListResponse(it)
            }
        })

        viewModel.dogsResponseDB.observe(viewLifecycleOwner) { response ->
            response?.let {
                viewModel.localDogsListResponse(it)
            }
        }

        viewModel.clearDataBaseCallback.observe(viewLifecycleOwner) {
            if (it) {
                showToast("Cleared")
            }
        }

        viewModel.saveToDataBaseCallback.observe(viewLifecycleOwner) {
            if (it) {
                showToast("Saved")
            }
        }

        viewModel.showMessageEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }
    }
}