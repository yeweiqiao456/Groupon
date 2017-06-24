package com.ywq.tarena.groupon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ywq.tarena.groupon.R;
import com.ywq.tarena.groupon.bean.TuanBean;
import com.ywq.tarena.groupon.util.HttpUtil;
import com.ywq.tarena.groupon.util.VolleyClient;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tarena on 2017/6/20.
 */

public class DealAdapter extends MyBaseAdapter<TuanBean.DealsBean> {
    public DealAdapter(Context context, List<TuanBean.DealsBean> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.main_item_layout,viewGroup,false);
            viewHolder = new ViewHolder(view);
            /*viewHolder.textView_Title = (TextView) view.findViewById(R.id.textView_main_item_title);
            viewHolder.textView_Detail = (TextView) view.findViewById(R.id.textView_main_item_massage);
            viewHolder.textView_Count = (TextView) view.findViewById(R.id.textView_main_item_quantity);
            viewHolder.textView_Distance = (TextView) view.findViewById(R.id.textView_main_item_distance);
            viewHolder.textView_Price = (TextView) view.findViewById(R.id.textView_main_item_price);*/
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();

        }

        TuanBean.DealsBean dealsBean = getItem(i);
        // 图片加载 GLIDE(可以加载GIF图)    /    universal-ImageLoader
        //HttpUtil.loadImage(dealsBean.getS_image_url(),viewHolder.imageView_Pic);
        HttpUtil.displayImage(dealsBean.getS_image_url(),viewHolder.imageView_Pic);
        viewHolder.textView_Title.setText(dealsBean.getTitle());
        viewHolder.textView_Detail.setText(dealsBean.getDescription());
        viewHolder.textView_Price.setText("￥" + dealsBean.getList_price() + "");
        Random random = new Random();
        int count = random.nextInt(2000)+500;
        viewHolder.textView_Count.setText("已售" + count);
        //TODO 距离 viewHolder.textView_Distance.setText("xxxx")

        return view;
    }




    public class ViewHolder{
        @BindView(R.id.imageView_main_item)
        ImageView imageView_Pic;
        @BindView(R.id.textView_main_item_title)
        TextView textView_Title;
        @BindView(R.id.textView_main_item_massage)
        TextView textView_Detail;
        @BindView(R.id.textView_main_item_distance)
        TextView textView_Distance;
        @BindView(R.id.textView_main_item_price)
        TextView textView_Price;
        @BindView(R.id.textView_main_item_quantity)
        TextView textView_Count;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
