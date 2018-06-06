package com.example.module2.childnode;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tony.modulelistview.R;
import com.example.viewcache.viewholderItem.ViewHolderStyle2;
import com.modulizedatasource.datamodule.ModuleDataNode;


/**
 * Created by tony on 2018/5/9.
 */

public class MiddleModuleChildNodeStyle1 extends ModuleDataNode<String> {

    @Override
    public View innerGetView(final View convertView,int index, Context context) {
        ViewHolderStyle2 viewHolder;
        if (convertView != null &&
                convertView.getTag() instanceof ViewHolderStyle2) {
            viewHolder = (ViewHolderStyle2) convertView.getTag();
        } else {
            viewHolder = (ViewHolderStyle2) getPageContextModel().mViewCacheManage.getViewHolder(ViewHolderStyle2.class.getName());
        }

        ImageView imageView = viewHolder.image;
        imageView.setImageResource(R.drawable.a);

        TextView textView = viewHolder.title;
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        ssb.append("MiddleModuleChildNodeStyle1 \n");
        ssb.append(this.getItemDataLocally(index));
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(context, R.style.text_16_f5d222);
        ssb.setSpan(textAppearanceSpan, 0, ssb.length(), SpannableStringBuilder.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(ssb);

        return viewHolder.mContentView;
    }

    @Override
    protected String getViewType(int index) {
        return ViewHolderStyle2.class.getName();
    }
}
