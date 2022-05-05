package com.example.englanguage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englanguage.adapter.ListTopicAdapter
import android.os.Bundle
import com.example.englanguage.R
import androidx.recyclerview.widget.DividerItemDecoration
import android.content.Intent
import android.graphics.Color
import com.example.englanguage.FlipCardActivity
import com.example.englanguage.TextToSpeechActivity
import com.example.englanguage.network.API
import com.example.englanguage.model.topic.Topic
import android.widget.Toast
import android.widget.TextView
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.example.englanguage.model.topic.Success
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class TopicActivity : AppCompatActivity() {
    private var listFlipCart: ImageView? = null
    private var soundTextToSpeech: ImageView? = null
    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ListTopicAdapter? = null
    private val postsList: MutableList<Success> = ArrayList()
    private val context: Context = this@TopicActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)
        adapter = ListTopicAdapter(postsList, context)
        recyclerView?.setAdapter(adapter)
        val itemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(this.resources.getDrawable(R.drawable.divider_rcv))
        recyclerView?.addItemDecoration(itemDecoration)
        listFlipCart = findViewById(R.id.listFlipCart)
        listFlipCart?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@TopicActivity, FlipCardActivity::class.java)
            startActivity(intent)
        })
        soundTextToSpeech = findViewById(R.id.soundTextToSpeech)
        soundTextToSpeech?.setOnClickListener(View.OnClickListener {
            val intent2 = Intent(this@TopicActivity, TextToSpeechActivity::class.java)
            startActivity(intent2)
        })
        clickGetTopic()
    }

    fun clickGetTopic() {
        progressBar!!.visibility = View.VISIBLE
        API.api.getTopics(1).enqueue(object : Callback<Topic?> {
            override fun onResponse(call: Call<Topic?>, response: Response<Topic?>) {
                val topic = response.body()
                for (i in topic!!.success.indices) {
                    val success = Success(
                        topic.success[i].name,
                        topic.success[i].soluong,
                        topic.success[i].id
                    )
                    postsList.add(success)
                }
                adapter!!.notifyDataSetChanged()
                progressBar!!.visibility = View.GONE
            }

            override fun onFailure(call: Call<Topic?>, t: Throwable) {
                val toast =
                    Toast.makeText(this@TopicActivity, "SHOW LIST TOPIC FAILED", Toast.LENGTH_SHORT)
                customToast(toast)
            }
        })
    }

    private fun customToast(toast: Toast) {
        val toastView = toast.view
        val toastMessage = toastView!!.findViewById<View>(android.R.id.message) as TextView
        toastMessage.textSize = 13f
        toastMessage.setTextColor(Color.YELLOW)
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        toastMessage.gravity = Gravity.CENTER
        toastMessage.compoundDrawablePadding = 4
        toastView.setBackgroundColor(Color.BLACK)
        toastView.setBackgroundResource(R.drawable.bg_toast)
        toast.show()
    }
}