package com.xridwan.swapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.xridwan.swapi.data.StarShipsModel
import com.xridwan.swapi.utils.Utils.Companion.urlStarShips
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class StarShipsViewModel : ViewModel() {

    private val starShipsModel = MutableLiveData<MutableList<StarShipsModel>>()

    fun setStarShips() {
        val listItems = ArrayList<StarShipsModel>()
        val client = AsyncHttpClient()
        client.get(urlStarShips, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()) {
                        val planets = list.getJSONObject(i)

                        val starShipsModel = StarShipsModel()
                        starShipsModel.name = planets.getString("name")
                        starShipsModel.model = planets.getString("model")
                        starShipsModel.manufacturer = planets.getString("manufacturer")
                        starShipsModel.consumables = planets.getString("consumables")

                        listItems.add(starShipsModel)
                    }
                    starShipsModel.postValue(listItems)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getStarShips(): LiveData<MutableList<StarShipsModel>> {
        return starShipsModel
    }
}