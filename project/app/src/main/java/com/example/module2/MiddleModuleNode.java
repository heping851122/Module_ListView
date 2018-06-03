package com.example.module2;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.example.interfacelibrary.ModuleEventListener;
import com.example.module2.Model.HeaderModel;
import com.example.module2.childnode.GroupNode;
import com.example.module2.childnode.MiddleModuleChildNodeStyle1;
import com.example.module2.childnode.MiddleNodeFirstNode;
import com.example.module2.childnode.ModuleDataNodeExampleHeader;
import com.example.viewcache.viewholderItem.ViewHolderStyle1;
import com.modulizedatasource.datamodule.ModuleDataGroupNode;
import com.modulizedatasource.datamodule.ModuleDataNode;

import java.util.ArrayList;


/**
 * Created by tony on 2018/5/9.
 */

public class MiddleModuleNode extends ModuleDataGroupNode implements ModuleEventListener {

    @Override
    public void onPageEvent(int eventId) {
        if (eventId != this.Event_Open_Middle_Node) {
            return;
        }

        this.removeAllChild();
        buildTree();
    }

    private void buildTree() {
        final MiddleNodeFirstNode firstNode = new MiddleNodeFirstNode();
        ArrayList<String> firstNodeData = new ArrayList<>();
        for (int firstNodeDataIndex = 0; firstNodeDataIndex < 3; firstNodeDataIndex++) {
            firstNodeData.add("MiddleNodeFirstNode 1");
        }
        firstNode.setData(firstNodeData);
        this.addChild(firstNode);


        for (int i = 0; i < 5; i++) {
            final GroupNode group = new GroupNode();
            group.setGroupNodeIndex(i);

            ModuleDataNodeExampleHeader headerNode = new ModuleDataNodeExampleHeader();
            ArrayList<HeaderModel> headerData = new ArrayList<>();
            HeaderModel headerModel = new HeaderModel();
            headerModel.title = String.format("MiddleNode %d header", i);
            headerData.add(headerModel);
            headerNode.setData(headerData);
            group.addChild(headerNode);

            final MiddleModuleChildNodeStyle1 mDataNode = new MiddleModuleChildNodeStyle1();
            ArrayList<String> dataList2 = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                dataList2.add(String.format("MiddleNode %d item %d", i, j));
            }
            mDataNode.setData(dataList2);

            headerModel.isOpen = true; //默认展开
            group.addChild(mDataNode);

            headerNode.setOnItemClickListener(new onItemClickListener() {

                @Override
                public void onItemClickEvent(int index, ModuleDataNode node) {
                    if (node != null &&
                            mDataNode != null) {
                        final HeaderModel headerModel = (HeaderModel) node.getItemData(index);
                        if (headerModel == null) {
                            return;
                        }

                        if (headerModel.isOpen) {
                            group.removeChild(mDataNode);
                        } else {
                            group.addChild(mDataNode);
                        }

                        headerModel.isOpen = !headerModel.isOpen;
                    }
                }
            });

            this.addChild(group);
        }
    }

    @Override
    public boolean isHasCoveredView() {
        return !isChildEmpty();
    }

    @Override
    public View getCoveredViewInner() {
        ViewHolderStyle1 viewHolder = (ViewHolderStyle1) getPageContextModel().mCoveredViewCacheManage.getViewHolder(ViewHolderStyle1.class.getName());
        viewHolder.titleTextView.setText("MiddleModuleNode covered view");

        viewHolder.mContentView.setBackgroundColor(Color.YELLOW);
        viewHolder.mContentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getPageContextModel().mContext, "MiddleModuleNode \n on click", Toast.LENGTH_LONG).show();
            }
        });

        return viewHolder.mContentView;
    }
}
