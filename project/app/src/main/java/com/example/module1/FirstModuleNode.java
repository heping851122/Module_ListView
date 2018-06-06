package com.example.module1;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.viewcache.viewholderItem.ViewHolderStyle3;
import com.modulizedatasource.datamodule.ModuleDataNode;

import java.util.ArrayList;


/**
 * Created by tony on 2018/5/9.
 */

public class FirstModuleNode extends ModuleDataNode<String> {

    public FirstModuleNode() {
        final ArrayList<String> dataList = new ArrayList<>();

        for (int j = 0; j < 6; j++) {
            dataList.add(String.format("FirstModuleNode %d", j));
        }

        this.setData(dataList);
    }

    @Override
    public View innerGetView(final View convertView, final int localIndex, final Context context) {
        ViewHolderStyle3 viewHolderStyle3;
        if (convertView != null &&
                convertView.getTag() instanceof ViewHolderStyle3) {
            viewHolderStyle3 = (ViewHolderStyle3) convertView.getTag();
        } else {
            viewHolderStyle3 = (ViewHolderStyle3) getPageContextModel().mViewCacheManage.getViewHolder(ViewHolderStyle3.class.getName());
        }

        viewHolderStyle3.mTextView.setText(this.getItemDataLocally(localIndex));

        return viewHolderStyle3.mContentView;
    }

    @Override
    protected String getViewType(int index) {
        return ViewHolderStyle3.class.getName();
    }

    @Override
    public void onItemClick(int index) {
        Toast.makeText(getPageContextModel().mContext,
                "FirstModuleNode on click" + "\n" + (index - getStartIndex()),
                Toast.LENGTH_SHORT).
                show();
    }
}
