package com.xridwan.swapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.xridwan.swapi.data.PeopleModel
import com.xridwan.swapi.utils.Utils.Companion.urlPeople
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class PeopleViewModel : ViewModel() {
    private val peopleModel = MutableLiveData<MutableList<PeopleModel>>()

    fun setPeople() {
        val listItems = ArrayList<PeopleModel>()
        val client = AsyncHttpClient()
        client.get(urlPeople, object : AsyncHttpResponseHandler() {
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

                        val peopleModel = PeopleModel()
                        peopleModel.name = planets.getString("name")
                        peopleModel.birth = planets.getString("birth_year")
                        peopleModel.url = planets.getString("url")
                        peopleModel.gender = planets.getString("gender")
                        peopleModel.height = planets.getString("height")

                        listItems.add(peopleModel)
                    }
                    peopleModel.postValue(listItems)

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

    fun getPeople(): LiveData<MutableList<PeopleModel>> {
        return peopleModel
    }
}