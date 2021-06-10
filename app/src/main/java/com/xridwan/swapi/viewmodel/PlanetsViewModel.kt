package com.xridwan.swapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.xridwan.swapi.data.PlanetsModel
import com.xridwan.swapi.utils.Utils.Companion.urlPlanets
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class PlanetsViewModel : ViewModel() {

    private val planetsModel = MutableLiveData<MutableList<PlanetsModel>>()

    fun setPlanets() {
        val listItems = ArrayList<PlanetsModel>()
        val client = AsyncHttpClient()
        client.get(urlPlanets, object : AsyncHttpResponseHandler() {
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

                        val planetsModel = PlanetsModel()
                        planetsModel.name = planets.getString("name")
                        planetsModel.terrain = planets.getString("terrain")
                        planetsModel.gravity = planets.getString("gravity")
                        planetsModel.populations = planets.getString("population")

                        listItems.add(planetsModel)
                    }
                    planetsModel.postValue(listItems)

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

    fun getPlanets(): LiveData<MutableList<PlanetsModel>> {
        return planetsModel
    }
}