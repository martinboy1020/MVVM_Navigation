package com.example.mvvm_navigation.ui.main.model

import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.ui.main.vm.bottom_sheet.BottomSheetDetailContract
import com.example.mvvm_navigation.ui.main.vm.second.SecondContract
import com.example.mvvm_navigation.ui.main.vm.third.ThirdContract

class BottomSheetDetailModel constructor(val repository: Repository): BaseModel(), BottomSheetDetailContract.ModelImpl{
    companion object{
        fun getInstance(repository: Repository) = BottomSheetDetailModel(repository)
    }

    override fun getBetList(): List<BetData> {
        return repository.getBetList()
    }
}