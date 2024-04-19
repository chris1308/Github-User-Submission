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
import com.example.githubusersubmission.databinding.FragmentFollowerBinding
import com.example.githubusersubmission.viewmodel.MainViewModel

class FollowerFragment : Fragment() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: FragmentFollowerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireActivity())
        val rv_follower : RecyclerView = view.findViewById(R.id.rv_follower)

        rv_follower.layoutManager = layoutManager
        mainViewModel.listFollowers.observe(viewLifecycleOwner){
            setFollowerData(it)
        }
        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        mainViewModel.setFollowers()
    }
    private fun showLoading(isLoading: Boolean)  {
        if (isLoading){
            binding.progressBar3.visibility = View.VISIBLE
        }else binding.progressBar3.visibility = View.GONE
    }
    fun setFollowerData(items : List<ItemsItem>){
        val adapter = FollowAdapter()
        adapter.submitList(items)
        binding.rvFollower.adapter = adapter
    }
}