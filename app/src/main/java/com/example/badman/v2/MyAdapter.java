package com.example.badman.v2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    RecyclerView recyclerView;
    Context cotext;
    ArrayList<String> itms = new ArrayList<>();
    ArrayList<String> urls = new ArrayList<>();

    public void Update(String name,String url)
    {
        itms.add(name);
        urls.add(url);
        notifyDataSetChanged();
    }

    public MyAdapter(RecyclerView recyclerView, Context cotext, ArrayList<String> itms,ArrayList<String> urls) {
        this.recyclerView = recyclerView;
        this.cotext = cotext;
        this.itms = itms;
        this.urls = urls;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(cotext).inflate(R.layout.item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        viewHolder.nameOfFile.setText(itms.get(i));
    }

    @Override
    public int getItemCount() {
        return itms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameOfFile;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nameOfFile = itemView.findViewById(R.id.nameOfFile);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position =  recyclerView.getChildAdapterPosition(v);
                    Intent intent = new Intent();
                    intent.setType(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(urls.get(position)));
                    cotext.startActivity(intent);

                }
            });
        }
    }
}
