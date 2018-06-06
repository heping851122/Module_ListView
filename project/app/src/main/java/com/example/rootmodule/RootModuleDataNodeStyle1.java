package com.example.rootmodule;

import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tony.modulelistview.R;
import com.example.viewcache.viewholderItem.ViewHolderStyle2;
import com.modulizedatasource.datamodule.ModuleDataNode;


/**
 * Created by tony on 2018/5/9.
 */

public class RootModuleDataNodeStyle1 extends ModuleDataNode<String, ViewHolderStyle2> {

    @Override
    protected void bindData(ViewHolderStyle2 viewHolder, int localIndex) {
        ImageView imageView = viewHolder.image;
        imageView.setImageResource(R.drawable.a);

        TextView textView = viewHolder.title;
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        ssb.append("RootModuleDataNodeStyle1 \n");
        ssb.append(this.getItemDataLocally(localIndex));
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(getPageContextModel().mContext, R.style.text_16_f5d222);
        ssb.setSpan(textAppearanceSpan, 0, ssb.length(), SpannableStringBuilder.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(ssb);
    }

}
