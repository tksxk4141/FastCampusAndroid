package com.example.aop_part6_chapter01.screen.main.home.restaurant.detail.review

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.aop_part6_chapter01.databinding.FragmentListBinding
import com.example.aop_part6_chapter01.model.restaurant.RestaurantReviewModel
import com.example.aop_part6_chapter01.screen.base.BaseFragment
import com.example.aop_part6_chapter01.util.provider.ResourceProvider
import com.example.aop_part6_chapter01.widget.adapter.ModelRecyclerAdapter
import com.example.aop_part6_chapter01.widget.adapter.listener.AdapterListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantReviewListFragment : BaseFragment<RestaurantReviewListViewModel, FragmentListBinding>() {

    override fun getViewBinding(): FragmentListBinding = FragmentListBinding.inflate(layoutInflater)

    override val viewModel by viewModel<RestaurantReviewListViewModel> {
        parametersOf(
            arguments?.getString(RESTAURANT_TITLE_KEY)
        )
    }
    private val resourceProvider by inject<ResourceProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantReviewModel, RestaurantReviewListViewModel>(
            listOf(),
            viewModel,
            resourceProvider,
            adapterListener = object : AdapterListener { }
        )
    }

    override fun observeData() = viewModel.reviewStateLiveData.observe(viewLifecycleOwner) {
        Log.e("reviews", it.toString())
        when (it) {
            is RestaurantReviewState.Success -> {
                handleSuccess(it)
            }
            else -> Unit
        }
    }

    override fun initViews() {
        binding.recyclerView.adapter = adapter
    }

    private fun handleSuccess(state: RestaurantReviewState.Success) {
        adapter.submitList(state.reviewList)
    }

    companion object {

        const val RESTAURANT_TITLE_KEY = "restaurantTitle"

        fun newInstance(restaurantTitle: String): RestaurantReviewListFragment {
            val bundle = bundleOf(
                RESTAURANT_TITLE_KEY to restaurantTitle
            )
            return RestaurantReviewListFragment().apply {
                arguments = bundle
            }
        }

    }
}