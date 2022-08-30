package com.sample.androidtask.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.sample.androidtask.api.ApiInterface
import com.sample.androidtask.databinding.ActivityMainBinding
import com.sample.androidtask.loadstate_list_item.LoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val videoListViewModel: UsersListViewModel by viewModels()
    private val adapter = UsersPagerAdapter()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ApiInterface.page=1
        lifecycleScope.launch {
            videoListViewModel.getUserList().observe(this@MainActivity) {
                it?.let {
                    adapter.submitData(lifecycle, it)
                }
            }
        }

        binding.apply {
            recyclerview.adapter= adapter.withLoadStateHeaderAndFooter(
                header = LoadStateAdapter { adapter.retry() },
                footer = LoadStateAdapter { adapter.retry() }
            )
            retryButton.setOnClickListener {
                adapter.retry()
            }

        }

        adapter.addLoadStateListener { loadState ->
            // show empty list
            binding.apply {
                loadState.apply {
                    if(this.source.refresh is LoadState.Error || this.source.append is LoadState.Error){
                        val errorState = when {
                            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                            loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                            else -> null
                        }
                        if(errorState?.error !is NullPointerException) {
                            binding.root.snack(errorState?.error?.message.toString())
                        }
                    }
                    retryButton.isVisible = this.source.refresh is LoadState.Error
                    textRetryRequest.isVisible = this.source.refresh is LoadState.Error
                    recyclerview.isVisible=this.source.refresh is LoadState.NotLoading

                }

            }
        }
    }


     private fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
        val snack = Snackbar.make(this, message, length)
        snack.show()
    }
}