package com.example.readkami_beta.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readkami_beta.Model.journal1_model;
import com.example.readkami_beta.R;

import java.util.List;

public class journal1_adapter extends RecyclerView.Adapter<journal1_adapter.ArticleViewHolder> {
    private List<journal1_model> articleList;
    private OnItemClickListener onItemClickListener;

    public journal1_adapter(List<journal1_model> articleList) {
        this.articleList = articleList;
    }

    // Setter for item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        final int currentPosition = holder.getAdapterPosition(); // Retrieve the current position

        journal1_model article = articleList.get(currentPosition);
        holder.textViewTitle.setText(article.getTitle());
        holder.textViewAuthors.setText(article.getAuthors());

        // Set click listener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(currentPosition);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewAuthors;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAuthors = itemView.findViewById(R.id.textViewAuthors);
        }
    }
}
