package com.example.rootmodule;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.modulizedatasource.model.PageContextModel;
import com.example.viewcache.viewholderItem.ViewHolderStyle1;
import com.modulizedatasource.datamodule.ModuleDataGroupNode;

import java.util.ArrayList;


/**
 * Created by tony on 2018/5/13.
 */

public class RootNode extends ModuleDataGroupNode {
    private RootModuleDataNodeStyle1 mDataNode;


    @Override
    public void init(PageContextModel context) {
        super.init(context);
        buildTree();
    }

    @Override
    public boolean isHasCoveredView() {
        return true;
    }

    @Override
    public View getCoveredViewInner() {
        ViewHolderStyle1 viewHolder = (ViewHolderStyle1) getPageContextModel().mCoveredViewCacheManage.getViewHolder(ViewHolderStyle1.class.getName());
        viewHolder.titleTextView.setText("this is RootNode covered view");

        viewHolder.mContentView.setBackgroundColor(Color.GREEN);
        viewHolder.mContentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getPageContextModel().mContext, "RootNode covered view click", Toast.LENGTH_LONG).show();
            }
        });

        return viewHolder.mContentView;
    }

    private void buildTree() {
        mDataNode = new RootModuleDataNodeStyle1();
        ArrayList<String> dataList2 = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            dataList2.add(String.format("TopGroupNode1 %d", j));
        }
        mDataNode.setData(dataList2);

        this.addChild(mDataNode);
    }
}
