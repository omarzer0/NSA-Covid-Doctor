package az.zero.nsacoviddoctor.presentation.check_symptom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import az.zero.nsacoviddoctor.core.BaseViewModel
import az.zero.nsacoviddoctor.data.repository.CovidRepositoryImpl
import az.zero.nsacoviddoctor.domain.model.ai_model_data.AIModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class CheckViewModel @Inject constructor(
    private val repository: CovidRepositoryImpl
) : BaseViewModel() {

    private val aiModelDataMutableLiveData = MutableLiveData<AIModelData>()
    val aiModelDataLiveData: LiveData<AIModelData>
        get() = aiModelDataMutableLiveData

    fun getAIModelData(image: MultipartBody.Part) = safeCallApi({
        repository.getAIModelData(image)
    }, {
        aiModelDataMutableLiveData.value = it
    })

}