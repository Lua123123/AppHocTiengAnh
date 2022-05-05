package com.example.englanguage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englanguage.R;
import com.example.englanguage.model.vocabulary.SuccessVocabulary;

import java.util.List;

public class ListVocabularyOfTopicAdapter extends RecyclerView.Adapter<ListVocabularyOfTopicAdapter.ListVocabularyOfTopicViewHolder> {

    private List<SuccessVocabulary> postsList;
    private Context context;

    public ListVocabularyOfTopicAdapter(List<SuccessVocabulary> postsList, Context context) {
        this.postsList = postsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListVocabularyOfTopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_vocabulary_of_topic_item, parent, false);
        return new ListVocabularyOfTopicAdapter.ListVocabularyOfTopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListVocabularyOfTopicViewHolder holder, int position) {
        SuccessVocabulary successVocabulary = postsList.get(position);
        if (successVocabulary == null) {
            return;
        }
        holder.tvWord.setText(successVocabulary.getWord());
        holder.tvMean.setText(successVocabulary.getMean());
    }

    @Override
    public int getItemCount() {
        if (postsList != null) {
            return postsList.size();
        }
        return 0;
    }

    public class ListVocabularyOfTopicViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWord;
        private TextView tvMean;
        private LinearLayout layout_item;

        public ListVocabularyOfTopicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.word);
            tvMean = itemView.findViewById(R.id.mean);
            layout_item = itemView.findViewById(R.id.layout_item);
        }
    }
}
