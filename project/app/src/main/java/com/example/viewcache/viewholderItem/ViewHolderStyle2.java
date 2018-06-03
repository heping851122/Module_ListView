package com.example.viewcache.viewholderItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tony.modulelistview.R;
import com.modulizedatasource.viewcache.BaseViewHolder;


/**
 * Created by tony on 2018/5/10.
 */

public class ViewHolderStyle2 extends BaseViewHolder {
    public ImageView image;
    public TextView title;


    @Override
    protected View createView() {
        return View.inflate(this.mContext, R.layout.style2_item, null);
    }

    @Override
    protected void bindView(View contentView) {
        title = (TextView) contentView.findViewById(R.id.title);
        image = (ImageView) contentView.findViewById(R.id.image);
    }
}
