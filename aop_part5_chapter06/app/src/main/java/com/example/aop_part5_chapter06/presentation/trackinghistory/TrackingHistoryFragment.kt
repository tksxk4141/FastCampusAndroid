package com.example.aop_part5_chapter06.presentation.trackinghistory

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aop_part5_chapter06.data.entity.TrackingInformation
import com.example.aop_part5_chapter06.data.entity.TrackingItem
import com.example.aop_part5_chapter06.databinding.FragmentTrackingHistoryBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment
import org.koin.core.parameter.parametersOf

class TrackingHistoryFragment : ScopeFragment(), TrackingHistoryContract.View {

    override val presenter: TrackingHistoryContract.Presenter by inject {
        parametersOf(arguments.item, arguments.information)
    }

    private var binding: FragmentTrackingHistoryBinding? = null

    private val arguments: TrackingHistoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTrackingHistoryBinding.inflate(inflater)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
        presenter.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun bindViews() {
        binding?.refreshLayout?.setOnRefreshListener {
            presenter.refresh()
        }
        binding?.deleteTrackingItemButton?.setOnClickListener {
            presenter.deleteTrackingItem()
        }
    }

    private fun initViews() {
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = TrackingHistoryAdapter()
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showTrackingItemInformation(
        trackingItem: TrackingItem,
        trackingInformation: TrackingInformation
    ) {
        binding?.resultTextView?.text = trackingInformation.level?.label
        binding?.invoiceTextView?.text = "${trackingInformation.invoiceNo} (${trackingItem.company.name})"

        binding?.itemNameTextView?.text =
            if (trackingInformation.itemName.isNullOrBlank()) {
                "이름 없음"
            } else {
                trackingInformation.itemName
            }

        (binding?.recyclerView?.adapter as? TrackingHistoryAdapter)?.run {
            data = trackingInformation.trackingDetails ?: emptyList()
            notifyDataSetChanged()
        }
    }

    override fun finish() {
        findNavController().popBackStack()
    }

    override fun hideLoadingIndicator() {
        binding?.refreshLayout?.isRefreshing = false
    }
}
