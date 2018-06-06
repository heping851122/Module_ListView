package com.example.module1;

import android.widget.Toast;

import com.example.viewcache.viewholderItem.ViewHolderStyle3;
import com.modulizedatasource.datamodule.ModuleDataNode;

import java.util.ArrayList;


/**
 * Created by tony on 2018/5/9.
 */
public class FirstModuleNode extends ModuleDataNode<String, ViewHolderStyle3> {

    public FirstModuleNode() {
        final ArrayList<String> dataList = new ArrayList<>();

        for (int j = 0; j < 6; j++) {
            dataList.add(String.format("FirstModuleNode %d", j));
        }

        this.setData(dataList);
    }

    @Override
    protected void bindData(ViewHolderStyle3 viewHolder, int localIndex) {
        viewHolder.mTextView.setText(this.getItemDataLocally(localIndex));
    }

    @Override
    public void onItemClick(int index) {
        Toast.makeText(getPageContextModel().mContext,
                "FirstModuleNode on click" + "\n" + (index - getStartIndex()),
                Toast.LENGTH_SHORT).
                show();
    }
}
