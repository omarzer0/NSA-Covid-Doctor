package az.zero.nsacoviddoctor.presentation.home_fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.getLocation
import az.zero.nsacoviddoctor.core.BaseFragment
import az.zero.nsacoviddoctor.data.data_adapter.PreventionData
import az.zero.nsacoviddoctor.databinding.FragmentHomeBinding
import az.zero.nsacoviddoctor.presentation.adapter.PrevHomeAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private lateinit var prevRecyclerView: RecyclerView
    private lateinit var prevLayoutManager: RecyclerView.LayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        var uri: Uri?

        binding.chooseImageBtn.setOnClickListener {
            pickImage {
                uri = it
                toastMy("$uri", success = true)
            }
        }
        val x = getLocation(requireContext())
        toastMy("$x", true)

        setRecyclerView(fakePreventionData())

        binding.seeGuidelinesBtn.setOnClickListener {
            toastMy("Look below to see more guidelines" , success = true)
        }
    }

    private fun setRecyclerView(preventionData: List<PreventionData>) {

        prevRecyclerView = binding.preventionRv
        prevRecyclerView.setHasFixedSize(true)
        prevRecyclerView.layoutManager = LinearLayoutManager(context , LinearLayoutManager.HORIZONTAL , false)
        prevRecyclerView.adapter = PrevHomeAdapter(preventionData)

    }

    private fun fakePreventionData():List<PreventionData> = listOf(
        PreventionData(R.drawable.prevention1 , getString(R.string.prev1_title)),
        PreventionData(R.drawable.prevention2 , getString(R.string.prev2_title)),
        PreventionData(R.drawable.prevention3 , getString(R.string.prev3_title)),
        PreventionData(R.drawable.prevention4 , getString(R.string.prev4_title)),
        PreventionData(R.drawable.prevention5 , getString(R.string.prev5_title)),
        PreventionData(R.drawable.prevention6 , getString(R.string.prev6_title)),
        PreventionData(R.drawable.prevention7 , getString(R.string.prev7_title))
    )
}