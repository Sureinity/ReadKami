package com.example.readkami_beta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Journals> journals;
    public RecyclerviewAdapter(Context context, ArrayList<Journals> journals){
    this.context=context;
    this.journals=journals;
    }

    @NonNull
    @Override
    public RecyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.fragment_bookmark, parent, false);
        return new RecyclerviewAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.MyViewHolder holder, int position) {

        holder.tvTitle.setText(journals.get(position).getTitle());
        holder.tvSubtitle.setText(journals.get(position).getSubtitle());
        holder.tvDescription.setText(journals.get(position).getDescription());
        holder.imageView.setImageResource(journals.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return journals.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvTitle, tvSubtitle, tvDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvTitle = itemView.findViewById(R.id.title);
            tvSubtitle = itemView.findViewById(R.id.subtitle);
            tvDescription = itemView.findViewById(R.id.description);

        }
    }
}
*/
