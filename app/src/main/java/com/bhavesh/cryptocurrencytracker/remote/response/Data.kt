package com.bhavesh.cryptocurrencytracker.remote.response

import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2022 Kotlin com.bhavesh.cryptocurrencytracker.remote.response.Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Data (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("symbol") val symbol : String,
	@SerializedName("slug") val slug : String,
	@SerializedName("num_market_pairs") val num_market_pairs : Int,
	@SerializedName("date_added") val date_added : String,
	@SerializedName("tags") val tags : List<String>,
	@SerializedName("max_supply") val max_supply : Int,
	@SerializedName("circulating_supply") val circulating_supply : Int,
	@SerializedName("total_supply") val total_supply : Int,
	@SerializedName("platform") val platform : String,
	@SerializedName("cmc_rank") val cmc_rank : Int,
	@SerializedName("last_updated") val last_updated : String,
	@SerializedName("quote") val quote : Quote
)