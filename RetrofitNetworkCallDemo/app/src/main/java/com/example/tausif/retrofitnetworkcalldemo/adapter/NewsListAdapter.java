package com.example.tausif.retrofitnetworkcalldemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.tausif.retrofitnetworkcalldemo.R;
import com.example.tausif.retrofitnetworkcalldemo.model.Article;
import java.util.List;


//This adapter class to written to show data in recycler view

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder>{

    private List<Article> articles;
    private int rowLayout;
    private Context context;


    public NewsListAdapter( Context context,List<Article> articleList, int rowLayout) {

        this.context = context;
        this.articles = articleList;
        this.rowLayout = rowLayout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Article article = articles.get(position);

        Glide.with(context).load(article.getUrlToImage())
                .thumbnail(0.5f)
                .into(viewHolder.imageViewNews);

        viewHolder.textViewNewsTitle.setText(article.getTitle());
        viewHolder.textViewDescription.setText(article.getDescription());





        //viewHolder.cityImage.setImageDrawable(mContext.getResources().getDrawable(city.getImageResourceId(mContext)));
    }

    @Override
    public int getItemCount() {

        return articles == null ? 0 : articles.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewNews;
        public TextView textViewNewsTitle, textViewDescription;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewNewsTitle =  itemView.findViewById(R.id.text_view_news_title);
            textViewDescription = itemView.findViewById(R.id.text_view_news_description);
            imageViewNews = itemView.findViewById(R.id.image_view_news);


            //  cityImage = (ImageView)itemView.findViewById(R.id.city_image);
//            itemView.setTag(itemView);
//            itemView.setOnClickListener(this);
        }


    }
}
