package com.francis.maonassessment.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.francis.maonassessment.data.model.table.CompetitionEntity
import com.francis.maonassessment.data.repository.Repository
import com.francis.maonassessment.data.type.Plan
import com.francis.maonassessment.util.Event
import com.francis.maonassessment.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val filter = MutableLiveData(Plan.FREE)
    val competitions: LiveData<PagingData<CompetitionEntity>> =
        Transformations.switchMap(filter) { plan ->
            repository.getCompetitions(plan).cachedIn(viewModelScope)
        }

    private val _result = MutableLiveData<State<*>>(State.Idle)
    val result: LiveData<State<*>>
        get() = _result

    private val _navEvent = MutableLiveData<Event<*>>()
    val navEvent: LiveData<Event<*>>
        get() = _navEvent

    fun navigate(competition: CompetitionEntity) {
        _navEvent.value = Event(competition)
    }

    fun fetchCompetitions() {
        _result.value = State.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val state = repository.fetchCompetitions()
            _result.postValue(state)
        }
    }

    fun filter(plan: Plan) {
        filter.value = plan
    }
}