package com.example.aop_part6_chapter01.screen.main.like

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.aop_part6_chapter01.databinding.FragmentRestaurantLikeListBinding
import com.example.aop_part6_chapter01.model.restaurant.RestaurantModel
import com.example.aop_part6_chapter01.screen.base.BaseFragment
import com.example.aop_part6_chapter01.screen.main.home.restaurant.detail.RestaurantDetailActivity
import com.example.aop_part6_chapter01.util.provider.ResourceProvider
import com.example.aop_part6_chapter01.widget.adapter.ModelRecyclerAdapter
import com.example.aop_part6_chapter01.widget.adapter.listener.restaurant.RestaurantLikeListListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantLikeListFragment: BaseFragment<RestaurantLikeListViewModel, FragmentRestaurantLikeListBinding>() {

    override fun getViewBinding(): FragmentRestaurantLikeListBinding = FragmentRestaurantLikeListBinding.inflate(layoutInflater)

    override val viewModel by viewModel<RestaurantLikeListViewModel>()

    private val resourceProvider by inject<ResourceProvider>()

    private var isFirstShown = false

    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantModel, RestaurantLikeListViewModel>(
            listOf(),
            viewModel,
            resourceProvider = resourceProvider,
            adapterListener = object : RestaurantLikeListListener {

            override fun onDislikeItem(model: RestaurantModel) {
                viewModel.dislikeRestaurant(model.toEntity())
            }

            override fun onClickItem(model: RestaurantModel) {
                startActivity(
                    RestaurantDetailActivity.newIntent(requireContext(), model.toEntity())
                )
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (isFirstShown.not()) {
            isFirstShown = true
        } else {
            viewModel.fetchData()
        }
    }

    override fun initViews() = with(binding) {
        recyclerView.adapter = adapter
    }

    override fun observeData() {
        viewModel.restaurantListLiveData.observe(viewLifecycleOwner) {
            checkListEmpty(it)
        }
    }

    private fun checkListEmpty(restaurantList: List<RestaurantModel>) = with(binding) {
        val isEmpty = restaurantList.isEmpty()
        recyclerView.isGone = isEmpty
        emptyResultTextView.isVisible = isEmpty
        if (isEmpty.not()) {
            adapter.submitList(restaurantList)
        }
    }

    companion object {
        fun newInstance() = RestaurantLikeListFragment()

        const val TAG = "likeFragment"
    }

}