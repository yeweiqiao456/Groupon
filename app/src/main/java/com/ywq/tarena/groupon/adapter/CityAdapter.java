package com.ywq.tarena.groupon.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.ywq.tarena.groupon.R;
import com.ywq.tarena.groupon.bean.CityBean;
import com.ywq.tarena.groupon.bean.CitynameBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tarena on 2017/6/22.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> implements SectionIndexer{

    //申明基本属性
    //上下文
    Context context;
    //数据源
    List<CitynameBean> datas;
    //LayoutInflater
    LayoutInflater inflater;

    //在构造器中完成对数据的初始化
    public CityAdapter(Context context,List<CitynameBean> datas){
        this.context = context;
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);
    }



    @Override
    public CityAdapter.CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.inflate_city_item_layout,parent,false);
        CityViewHolder viewHolder = new CityViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CityAdapter.CityViewHolder holder, int position) {


        //将第position位置的数据放到ViewHolder中显示
        CitynameBean citynameBean = datas.get(position);
        holder.textView_Name.setText(citynameBean.getCityName());
        holder.textView_Letter.setText(citynameBean.getLetter()+"");
        //position这个位置的数据是不是该数据所属位分组的起始位置
        if (position == getPositionForSection(getSectionForPosition(position))){
            holder.textView_Letter.setVisibility(View.VISIBLE);
        } else {
            holder.textView_Letter.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addAll(List<CitynameBean> list,boolean isClear){
        if (isClear){
            datas.clear();
        }
        datas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 某一个分组的起始位置是什么
     * @param section
     * @return
     */
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0;i < datas.size(); i++){

            if (datas.get(i).getLetter() == section){

                return i;
            }
        }
        //当前的数据源(datas)中没有任何一个数据属于传入的section分组
        //只要返回一个数据源中不存在的下标即可。datas.size()或更大，-1或更小
        return datas.size()+1;
    }

    /**
     * 第position位置上的数据的分组是什么
     * @param position
     * @return
     */
    @Override
    public int getSectionForPosition(int position) {
        return datas.get(position).getLetter();
    }


    public class CityViewHolder extends RecyclerView.ViewHolder{

        //利用ButterKnife完成对ViewHolder中控件的赋值
        //显示城市拼音首字母
        @Nullable
        @BindView(R.id.textView_city_item_letter)
        TextView textView_Letter;
        //显示城市中文名称
        @Nullable
        @BindView(R.id.textView_city_item_name)
        TextView textView_Name;


        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
