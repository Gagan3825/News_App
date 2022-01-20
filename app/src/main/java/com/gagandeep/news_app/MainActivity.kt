package com.gagandeep.news_app

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.gagandeep.news_app.databinding.ActivityMainBinding
import android.view.LayoutInflater as LayoutInflater1

class MainActivity : AppCompatActivity(), newItemClicked {


private lateinit var mAdapter: NewListAdapter
private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val  view = binding.root


        setContentView(view)
           binding.recyclerView.layoutManager = LinearLayoutManager(this);
        fetch()
         mAdapter = NewListAdapter(this)
        binding.recyclerView.adapter = mAdapter
    }

//    private fun LayoutInflater1(): LayoutInflater {
//
//    }


    private fun fetch(){
      val url = "https://saurav.tech/NewsAPI/top-headlines/category/technology/in.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                mAdapter.updatedNews(newsArray)
            },
            Response.ErrorListener {

            }
        )
        MySingletone.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemclicked(item: News) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}