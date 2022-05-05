package com.example.englanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englanguage.adapter.ListVocabularyOfTopicAdapter;
import com.example.englanguage.model.vocabulary.SuccessVocabulary;
import com.example.englanguage.model.vocabulary.Vocabulary;
import com.example.englanguage.network.API;
import com.example.englanguage.viewmodel.VocabularyOfTopicViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VocabularyOfTopicActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ListVocabularyOfTopicAdapter adapter;
    private List<SuccessVocabulary> postsList = new ArrayList<>();
    private Context context = VocabularyOfTopicActivity.this;
    private TextView tv_topic;
    private ImageView imgBack;
    private VocabularyOfTopicViewModel vocabularyOfTopicViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_of_topic);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        int position = bundle.getInt("id");
        String nameTopic = bundle.getString("nameTopic");
        tv_topic = findViewById(R.id.tv_topic);
        tv_topic.setText(nameTopic);

        recyclerView = findViewById(R.id.rcv_vocabulary_of_topic);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ListVocabularyOfTopicAdapter(postsList, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.divider_rcv));
        recyclerView.addItemDecoration(itemDecoration);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(VocabularyOfTopicActivity.this, TopicActivity.class);
                startActivity(intent1);
            }
        });
        vocabularyOfTopicViewModel = new VocabularyOfTopicViewModel(adapter, postsList, context);
        vocabularyOfTopicViewModel.mutableLiveDataClickGetVocabularyOfTopic(position);
    }
}