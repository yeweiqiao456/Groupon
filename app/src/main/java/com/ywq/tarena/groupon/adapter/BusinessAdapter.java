package com.ywq.tarena.groupon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ywq.tarena.groupon.R;
import com.ywq.tarena.groupon.bean.BusinessBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tarena on 2017/6/23.
 */

public class BusinessAdapter extends MyBaseAdapter<BusinessBean> {

    public BusinessAdapter(Context context, List<BusinessBean> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BusinessViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.inflat_business_item_layout,viewGroup,false);
            viewHolder = new BusinessViewHolder(view);
            view.setTag(viewHolder);
        } else {

            viewHolder = (BusinessViewHolder) view.getTag();

        }


        return view;
    }

    public class BusinessViewHolder {
        @BindView(R.id.imageView_business_photo)
        ImageView imageView_photo;
        @BindView(R.id.imageView_business_rating)
        ImageView imageView_rating;
        @BindView(R.id.textView_business_Title)
        TextView textView_title;
        @BindView(R.id.textView_business_price_person)
        TextView textView_price_person;
        @BindView(R.id.textView_business_region)
        TextView textView_region;
        @BindView(R.id.textView_business_categories)
        TextView textView_categories;
        @BindView(R.id.textView_business_distance)
        TextView textView_distance;


        public BusinessViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
