package com.example.module3;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tony.modulelistview.R;
import com.example.viewcache.viewholderItem.ViewHolderStyle2;
import com.modulizedatasource.datamodule.ModuleDataNode;

import java.util.ArrayList;


/**
 * Created by tony on 2018/5/13.
 */

public class LastModuleNode extends ModuleDataNode<String> {

    public LastModuleNode() {
        final ArrayList<String> dataList = new ArrayList<>();

        for (int j = 0; j < 10; j++) {
            dataList.add(String.format("LastModuleNode %d", j));
        }

        this.setData(dataList);
    }

    @Override
    public View innerGetView(int index, Context context) {
        ViewHolderStyle2 viewHolder = (ViewHolderStyle2) getPageContextModel().mViewCacheManage.getViewHolder(ViewHolderStyle2.class.getName());

        ImageView imageView = viewHolder.image;
        imageView.setImageResource(R.drawable.c);

        TextView textView = viewHolder.title;
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        ssb.append(this.getItemDataLocally(index));
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(context, R.style.text_16_holo_orange_dark);
        ssb.setSpan(textAppearanceSpan, 0, ssb.length(), SpannableStringBuilder.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(ssb);

        return viewHolder.mContentView;
    }
}
