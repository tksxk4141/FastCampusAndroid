package com.example.aop_part5_chapter05.presentation

interface BaseView<PresenterT : BasePresenter> {

    val presenter: PresenterT
}