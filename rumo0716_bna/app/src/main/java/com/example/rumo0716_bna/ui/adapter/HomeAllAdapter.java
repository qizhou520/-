package com.example.rumo0716_bna.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumo0716_bna.R;
import com.example.rumo0716_bna.ui.home.Home_item;

import java.util.ArrayList;

public class HomeAllAdapter extends RecyclerView.Adapter<HomeAllAdapter.myViewHodler> {
    private ArrayList<Home_item> Home_item_List;

    //建立建構函式
    public HomeAllAdapter(ArrayList<Home_item> Home_item_List) {
        //改資料內容
        this.Home_item_List = Home_item_List;//實體類資料ArrayList
    }

    /**
     * 建立viewhodler，相當於listview中getview中的建立view和viewhodler
     * @return
     */
    @Override
    public myViewHodler onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //建立自定義佈局
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_article_card, viewGroup, false);
        return new myViewHodler(itemView);
    }

    /**
     * 繫結資料，資料與view繫結
     */
    @Override
    public void onBindViewHolder(myViewHodler holder, int position) {
        //根據點選位置繫結資料
        //獲取實體類中的欄位並設定
        Home_item data = Home_item_List.get(position);
        holder.mItemArticle.setText(data.article);
        holder.mItemContext.setText(data.context);
        holder.mItemSource.setText(data.source);
        holder.mItemCount_search.setText(data.search);
        holder.mItemCount_love.setText(data.love);
    }

    /**
     * 得到總條數
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return Home_item_List.size();
    }

    //自定義viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private CardView cv ;
        private TextView mItemArticle;
        private TextView mItemContext;
        private TextView mItemSource;
        private TextView mItemCount_love;
        private TextView mItemCount_search;

        public myViewHodler(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            mItemArticle = (TextView) itemView.findViewById(R.id.article);
            mItemContext = (TextView) itemView.findViewById(R.id.context);
            mItemSource = (TextView) itemView.findViewById(R.id.source);
            mItemCount_love = (TextView) itemView.findViewById(R.id.count_love);
            mItemCount_search = (TextView) itemView.findViewById(R.id.count_search);
            //點選事件放在adapter中使用，也可以寫個介面在activity中呼叫
            //方法一：在adapter中設定點選事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以選擇直接在本位置直接寫業務處理
                    //Toast.makeText(context,"點選了xxx",Toast.LENGTH_SHORT).show();
                    //此處回傳點選監聽事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, Home_item_List.get(getLayoutPosition()));
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
         *
         * @param view 點選的item的檢視
         * @param data 點選的item的資料
         */
        public void OnItemClick(View view, Home_item data);
    }

    //需要外部訪問，所以需要設定set方法，方便呼叫
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
