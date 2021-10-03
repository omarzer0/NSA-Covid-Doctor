package az.zero.nsacoviddoctor.presentation.check_symptom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import az.zero.nsacoviddoctor.common.Resource
import az.zero.nsacoviddoctor.common.logMe
import az.zero.nsacoviddoctor.core.BaseViewModel
import az.zero.nsacoviddoctor.data.repository.CovidRepositoryImpl
import az.zero.nsacoviddoctor.domain.model.ai_model_data.AIModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class CheckViewModel @Inject constructor(
    private val repository: CovidRepositoryImpl
) : BaseViewModel() {

    private val aiModelDataMutableLiveData = MutableLiveData<Resource<AIModelData>>()
    val aiModelDataLiveData: LiveData<Resource<AIModelData>>
        get() = aiModelDataMutableLiveData

    fun getAIModelData(image: MultipartBody.Part) = viewModelScope.launch {
        try {
            val call = repository.getAIModelData(image)
            if (call.isSuccessful) {
                call.body()?.let { aiModelData ->
                    aiModelDataMutableLiveData.value = Resource.Success(aiModelData)
                }
            }

        } catch (e: Exception) {
            logMe(e.localizedMessage ?: "Unknown")
            aiModelDataMutableLiveData.value =
                Resource.Error(e.localizedMessage ?: "Unknown error")
        }

    }

}