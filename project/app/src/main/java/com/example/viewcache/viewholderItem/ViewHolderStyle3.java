package com.example.viewcache.viewholderItem;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.modulizedatasource.viewcache.BaseViewHolder;
import com.tools.CommonTools;

/**
 * Created by he_p on 2018/5/15.
 */

public class ViewHolderStyle3 extends BaseViewHolder {
    public TextView mTextView;

    @Override
    protected View createView() {
        mTextView = new TextView(mContext);
        mTextView.setPadding(CommonTools.getPixeFromDip(10),
                CommonTools.getPixeFromDip(10),
                CommonTools.getPixeFromDip(10),
                CommonTools.getPixeFromDip(10));
        mTextView.setBackgroundColor(Color.GRAY);

        return mTextView;
    }

    @Override
    protected void bindView(View contentView) {

    }
}
