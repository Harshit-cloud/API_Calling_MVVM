package com.sample.androidtask.models

import com.google.gson.annotations.SerializedName


data class CommonResponse (

   var page: Int? = null,
 var perPage: Int? = null,
  @SerializedName("total") var total : Int? = null,
  @SerializedName("total_pages" ) var totalPages : Int? = null,
  @SerializedName("data" ) var data: ArrayList<Data> = arrayListOf(),
  @SerializedName("support") var support: Support? = Support()

)