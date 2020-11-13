package com.example.rumo0716_bna.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rumo0716_bna.R;
import com.example.rumo0716_bna.ui.collect.CollectArticles;


import java.util.ArrayList;

public class CollectRecycleAdapter extends RecyclerView.Adapter<CollectRecycleAdapter.myViewHodler> {
    private Context context;
    private ArrayList<CollectArticles> collectArticlesList;

    //建立建構函式
    public CollectRecycleAdapter(Context context, ArrayList<CollectArticles> collectArticlesList) {
        //將傳遞過來的資料，賦值給本地變數
        this.context = context;//上下文
        this.collectArticlesList  = collectArticlesList ;//實體類資料ArrayList
    }

    /**
     * 建立viewhodler，相當於listview中getview中的建立view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CollectRecycleAdapter.myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //建立自定義佈局
        View itemView = View.inflate(context, R.layout.recycler_view_item_collect, null);
        return new myViewHodler(itemView);
    }

    /**
     * 繫結資料，資料與view繫結
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final CollectRecycleAdapter.myViewHodler holder, int position) {
        //根據點選位置繫結資料
        CollectArticles data = collectArticlesList.get(position);
        if(data.imgPath.equals("alert")){
            holder.mImg.setImageResource(R.drawable.alert);
        }else{
            holder.mImg.setImageResource(R.drawable.wrong);
        }
        holder.mSource.setText(data.source);//獲取實體類中的source欄位並設定
        holder.mContent.setText(data.content);//獲取實體類中的content欄位並設定
    }

    /**
     * 得到總條數
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return collectArticlesList.size();
    }

    //自定義viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private ImageView mImg;
        private TextView mSource;
        private TextView mContent;

        @SuppressLint("WrongViewCast")
        public myViewHodler(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.imgView);
            mSource = (TextView) itemView.findViewById(R.id.article_source);
            mContent = (TextView) itemView.findViewById(R.id.article_content);
            //點選事件放在adapter中使用，也可以寫個介面在activity中呼叫
            //方法一：在adapter中設定點選事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以選擇直接在本位置直接寫業務處理
                    //Toast.makeText(context,"點選了xxx",Toast.LENGTH_SHORT).show();
                    //此處回傳點選監聽事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, collectArticlesList.get(getLayoutPosition()));
                    }
                }
            });

        }
    }

    /**
     * 設定item的監聽事件的介面
     */
    public interface OnItemClickListener {
        /**
         * 介面中的點選每一項的實現方法，引數自己定義
         *  @param view 點選的item的檢視
         * @param data 點選的item的資料
         */
        public void OnItemClick(View view, CollectArticles data);

    }

    //需要外部訪問，所以需要設定set方法，方便呼叫
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void removeItem(int position){
        collectArticlesList.remove(position);
        notifyItemRemoved(position);
    }


}
