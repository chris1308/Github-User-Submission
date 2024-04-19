package com.example.githubusersubmission.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersubmission.R
import com.example.githubusersubmission.adapter.FollowAdapter
import com.example.githubusersubmission.data.response.ItemsItem
import com.example.githubusersubmission.databinding.FragmentFollowingBinding
import com.example.githubusersubmission.viewmodel.MainViewModel

class FollowingFragment : Fragment() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: FragmentFollowingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireActivity())
        val rv_following : RecyclerView = view.findViewById(R.id.rv_following)

        rv_following.layoutManager = layoutManager

        mainViewModel.listFollowing.observe(viewLifecycleOwner){
            setFollowerData(it)
        }
        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        mainViewModel.setFollowing()
    }

    private fun setFollowerData(items : List<ItemsItem>){
        val adapter = FollowAdapter()
        adapter.submitList(items)
        binding.rvFollowing.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean)  {
        if (isLoading){
            binding.progressBar4.visibility = View.VISIBLE
        }else binding.progressBar4.visibility = View.GONE
    }
}