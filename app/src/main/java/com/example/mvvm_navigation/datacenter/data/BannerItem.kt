package com.example.mvvm_navigation.datacenter.data

import android.graphics.Bitmap

data class BannerItem(var imgUrl: String? = null,
                      var linkUrl: String? = null,
                      var title: String? = null,
                      var type: Int = 0,
                      var bitmap: Bitmap? = null)
