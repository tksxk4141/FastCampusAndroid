package com.example.aop_part5_chapter06.presentation

interface BaseView<PresenterT: BasePresenter> {

    val presenter: PresenterT
}
