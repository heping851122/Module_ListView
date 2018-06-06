package com.modulizedatasource.datamodule;

import android.content.Context;
import android.view.View;

import com.modulizedatasource.datamodule.coverview.CoveredViewInterface;
import com.modulizedatasource.datamodule.coverview.FetchCoveredViewResult;
import com.modulizedatasource.viewcache.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tony on 2018/5/9.
 */

public class ModuleDataGroupNode extends ModuleDataNode implements CoveredViewInterface {

    public interface DataInvalidListener {
        void onDataInvalid();
    }

    private final List<ModuleDataNode> mChildList = new ArrayList<>();
    private DataInvalidListener mDataInvalidListener;


    public final FetchCoveredViewResult getCoveredView(final int startIndex, final List<ModuleDataNode> exitModuleNodeList) {
        FetchCoveredViewResult result = null;

        if (!isIndexInRange(startIndex)) {
            return result;
        } else if (exitModuleNodeList.contains(this)) {
            result = dispatchGetCoveredView(startIndex, exitModuleNodeList);
        } else if (isHasCoveredView()) {
            result = new FetchCoveredViewResult();
            result.coveredView = getCoveredViewInner();
            result.lastReturnNode = this;
        }

        return result;
    }

    @Override
    public boolean isHasCoveredView() {
        return false;
    }

    @Override
    public View getCoveredViewInner() {
        return null;
    }

    private FetchCoveredViewResult dispatchGetCoveredView(final int startIndex, final List<ModuleDataNode> exitModuleNodeList) {
        for (ModuleDataNode node : mChildList) {
            if (node instanceof ModuleDataGroupNode) {
                ModuleDataGroupNode coveredViewInterface = (ModuleDataGroupNode) node;
                FetchCoveredViewResult result = coveredViewInterface.getCoveredView(startIndex, exitModuleNodeList);

                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    @Override
    protected final View getView(final View convertView, final int index, final Context context) {
        for (ModuleDataNode node : mChildList) {
            if (node == null) {
                continue;
            }

            if (node.isIndexInRange(index)) {
                return node.getView(convertView, index, context);
            }
        }

        return null;
    }

    @Override
    protected void bindData(BaseViewHolder viewHolder, int localIndex) {
        //do noting
    }

    @Override
    protected String getViewType(final int index) {
        for (ModuleDataNode node : mChildList) {
            if (node == null) {
                continue;
            }

            if (node.isIndexInRange(index)) {
                return node.getViewType(index);
            }
        }

        return "";
    }

    public void addChildList(final List<ModuleDataNode> childList) {
        if (childList == null ||
                childList.isEmpty()) {
            detachAllChild();
        } else {
            for (ModuleDataNode node : childList) {
                addChildNode(node);
            }
        }

        requestDataInvalid();
    }

    public void addChild(final ModuleDataNode node) {
        if (node == null ||
                node.getParent() != null) {
            return;
        }

        addChildNode(node);
        requestDataInvalid();
    }

    public void removeAllChild() {
        for (ModuleDataNode node : this.mChildList) {
            if (node == null) {
                continue;
            }

            node.detach();
        }

        this.mChildList.clear();
        requestDataInvalid();
    }

    public void removeChild(ModuleDataNode node) {
        if (node == null) {
            return;
        }

        mChildList.remove(node);
        node.detach();
        requestDataInvalid();
    }

    public boolean isChildEmpty() {
        return this.mChildList.isEmpty();
    }

    @Override
    public final void setData(List inputList) {

    }

    public void setDataInvalidListener(final DataInvalidListener listener) {
        this.mDataInvalidListener = listener;
    }

    @Override
    public final void onItemClick(int index) {
        for (ModuleDataNode node : mChildList) {
            if (node == null) {
                continue;
            }

            if (node.isIndexInRange(index)) {
                node.onItemClick(index);
                break;
            }
        }
    }

    @Override
    protected void requestDataInvalid() {
        if (mParent == null) {
            invalidData(0);

            if (mDataInvalidListener != null) {
                mDataInvalidListener.onDataInvalid();
            }
        } else {
            super.requestDataInvalid();
        }
    }

    @Override
    protected void invalidData(final int startIndex) {
        int beginIndex = startIndex;
        int count = 0;

        for (ModuleDataNode node : mChildList) {
            if (node == null) {
                continue;
            }

            node.invalidData(beginIndex);

            beginIndex += node.getCount();
            count += node.getCount();
        }

        this.mCount = count;

        if (this.mCount > 0) {
            this.mStartIndex = startIndex;
            this.mEndIndex = this.mStartIndex + this.mCount - 1;
        } else {
            resetIndex();
        }
    }

    private void detachAllChild() {
        for (ModuleDataNode node : mChildList) {
            if (node == null) {
                continue;
            }

            node.detach();
        }

        mChildList.clear();
    }

    private boolean addChildNode(ModuleDataNode node) {
        if (node == null ||
                node.getParent() != null) {
            return false;
        }

        this.mChildList.add(node);
        node.setParent(this);

        return true;
    }

}
