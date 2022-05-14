package com.francis.maonassessment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francis.maonassessment.data.model.table.TeamEntity
import com.francis.maonassessment.data.repository.Repository
import com.francis.maonassessment.util.Event
import com.francis.maonassessment.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _result = MutableLiveData<State<*>>(State.Idle)
    val result: LiveData<State<*>>
        get() = _result

    private val _navEvent = MutableLiveData<Event<*>>()
    val navEvent: LiveData<Event<*>>
        get() = _navEvent

    fun navigate(entity: TeamEntity) {
        _navEvent.value = Event(entity)
    }

    fun getTeamsInCompetition(id: Long) = repository.getTeams(id)

    fun getTeamSquad(id: Long) = repository.getSquad(id)

    fun fetchTeamsInCompetition(id: Long) {
        _result.value = State.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val state = repository.fetchTeamsData(id)
            _result.postValue(state)
        }
    }

    fun fetchTeamSquad(id: Long) {
        _result.value = State.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val state = repository.fetchSquad(id)
            _result.postValue(state)
        }
    }
}