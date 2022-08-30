package com.sample.androidtask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sample.androidtask.models.Data
import com.sample.androidtask.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val repository: UsersRepository
):ViewModel() {
    fun getUserList(): LiveData<PagingData<Data>> {
        return repository.getUsers().cachedIn(viewModelScope)
    }

}