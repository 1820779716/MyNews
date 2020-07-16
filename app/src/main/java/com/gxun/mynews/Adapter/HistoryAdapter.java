package com.gxun.mynews.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gxun.mynews.DetailActivity;
import com.gxun.mynews.R;
import com.gxun.mynews.entity.NewsInfo;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    List<NewsInfo> newsInfoList;

    ListView listView;

    public HistoryAdapter(List<NewsInfo> newsInfoList, ListView listView) {
        this.listView = listView;
        this.newsInfoList = newsInfoList;
    }

    @Override
    public int getCount() {
        return newsInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
            holder = new ViewHolder();
            holder.newsId = convertView.findViewById(R.id.newsId);
            holder.icon = convertView.findViewById(R.id.icon);
            holder.title = convertView.findViewById(R.id.title);
            holder.author = convertView.findViewById(R.id.author);
            holder.time = convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(parent.getContext()).load(newsInfoList.get(position).getImgUrl()).into(holder.icon);
        holder.newsId.setText(newsInfoList.get(position).getNewsId());
        holder.title.setText(newsInfoList.get(position).getTitle());
        holder.author.setText("作者："+newsInfoList.get(position).getAuthor());
        holder.time.setText("时间："+newsInfoList.get(position).getTime());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("newsId", newsInfoList.get(position).getNewsId());
                intent.setClass(parent.getContext(), DetailActivity.class);
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView newsId;
        ImageView icon;
        TextView title;
        TextView author;
        TextView time;
    }
}
