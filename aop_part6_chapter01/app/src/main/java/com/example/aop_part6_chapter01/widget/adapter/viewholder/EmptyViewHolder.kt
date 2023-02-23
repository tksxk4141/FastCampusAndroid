package com.example.aop_part6_chapter01.widget.adapter.viewholder

import com.example.aop_part6_chapter01.databinding.ViewholderEmptyBinding
import com.example.aop_part6_chapter01.model.Model
import com.example.aop_part6_chapter01.screen.base.BaseViewModel
import com.example.aop_part6_chapter01.util.provider.ResourceProvider
import com.example.aop_part6_chapter01.widget.adapter.listener.AdapterListener

class EmptyViewHolder(
    private val binding: ViewholderEmptyBinding,
    viewModel: BaseViewModel,
    resourceProvider: ResourceProvider
): ModelViewHolder<Model>(binding, viewModel, resourceProvider) {
    override fun reset() = Unit
    override fun bindViews(model: Model, adapterListener: AdapterListener) = Unit
}