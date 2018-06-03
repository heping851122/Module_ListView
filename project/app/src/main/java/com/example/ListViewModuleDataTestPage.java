package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;


import com.example.interfacelibrary.ModuleEventListener;
import com.example.module1.FirstModuleNode;
import com.example.module2.MiddleModuleNode;
import com.example.module3.LastModuleNode;
import com.example.rootmodule.RootNode;
import com.modulizedatasource.model.PageContextModel;
import com.example.tony.modulelistview.R;
import com.example.viewcache.ViewCacheManage;
import com.modulizedatasource.datamodule.ModuleDataAdapter;
import com.modulizedatasource.datamodule.ModuleDataGroupNode;
import com.modulizedatasource.datamodule.ModuleDataNode;
import com.modulizedatasource.view.ListViewWithCoveredView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tony on 2018/5/9.
 */

public class ListViewModuleDataTestPage extends Activity implements ModuleDataGroupNode.DataInvalidListener, AdapterView.OnItemClickListener {
    private final PageContextModel mPageContextModel = new PageContextModel();

    private TextView mOpenMiddleDataBtnTextView;
    private ListViewWithCoveredView mListView;
    private ModuleDataAdapter mModuleDataAdapter = new ModuleDataAdapter();
    private ModuleDataGroupNode mTopParent;
    private final List<Class<? extends ModuleDataNode>> mPageNodeList = new ArrayList<>();
    private final List<ModuleDataNode> mPageNodeInstanceList = new ArrayList<>();

    {
        mPageNodeList.add(FirstModuleNode.class);
        mPageNodeList.add(MiddleModuleNode.class);
        mPageNodeList.add(LastModuleNode.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_module_data_test);
        initData();
        mListView = findViewById(R.id.list_view);
        mListView.setDivider(null);
        mListView.setAdapter(mModuleDataAdapter);
        mListView.setOnItemClickListener(this);

        mOpenMiddleDataBtnTextView = findViewById(R.id.open_middle_data_btn);
        mOpenMiddleDataBtnTextView.setOnClickListener(onOpenMiddleDataClick);
    }

    private void initData() {
        mPageContextModel.mContext = this;
        mPageContextModel.mViewCacheManage = new ViewCacheManage();
        mPageContextModel.mViewCacheManage.init(this, this.getLayoutInflater());

        mPageContextModel.mCoveredViewCacheManage = new ViewCacheManage();
        mPageContextModel.mCoveredViewCacheManage.init(this, this.getLayoutInflater());

        mTopParent = new RootNode();
        mTopParent.setDataInvalidListener(this);
        mTopParent.init(mPageContextModel);
        mModuleDataAdapter.setModuleDataNode(mTopParent);
        mModuleDataAdapter.setContext(this.mPageContextModel);

        ModuleDataNode module;
        for (Class clazz : mPageNodeList) {
            try {
                module = (ModuleDataNode) clazz.newInstance();
                module.init(mPageContextModel);
                mPageNodeInstanceList.add(module);
                mTopParent.addChild(module);
            } catch (Exception e) {
                Log.e("tony", "ModuleDataNode newInstance fail " + clazz.getName() + " " + e.getMessage());
            }
        }
    }

    @Override
    public void onDataInvalid() {
        if (mModuleDataAdapter == null) {
            return;
        }

        mModuleDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mTopParent == null) {
            return;
        }

        mTopParent.onItemClick(position);
    }

    private View.OnClickListener onOpenMiddleDataClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            sendPageEvent(ModuleEventListener.Event_Open_Middle_Node);
        }
    };

    private void sendPageEvent(final int event) {
        for (ModuleDataNode instance : mPageNodeInstanceList) {
            if (!(instance instanceof ModuleEventListener)) {
                continue;
            }

            ModuleEventListener listener = (ModuleEventListener) instance;
            listener.onPageEvent(event);
        }
    }
}
