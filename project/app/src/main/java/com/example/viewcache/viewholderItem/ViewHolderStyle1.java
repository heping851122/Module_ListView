package com.example.viewcache.viewholderItem;

import android.view.View;
import android.widget.TextView;

import com.application.BaseApplication;
import com.example.tony.modulelistview.R;
import com.modulizedatasource.viewcache.BaseViewHolder;


/**
 * Created by tony on 2018/5/10.
 */

public class ViewHolderStyle1 extends BaseViewHolder {
    public TextView titleTextView;


    @Override
    protected View createView() {
        View contentView = View.inflate(this.mContext, R.layout.header_view, null);
        return contentView;
    }

    @Override
    protected void bindView(View contentView) {
        titleTextView = (TextView) contentView.findViewById(R.id.title);
        this.titleTextView.setTextAppearance(BaseApplication.getCurrentApplication(), R.style.text_16_black);
    }
}
