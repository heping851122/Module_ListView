package com.example.viewcache;


import com.example.viewcache.viewholderItem.ViewHolderStyle1;
import com.example.viewcache.viewholderItem.ViewHolderStyle2;
import com.example.viewcache.viewholderItem.ViewHolderStyle3;
import com.modulizedatasource.viewcache.ViewCacheManagerBaseClass;

/**
 * Created by tony on 2018/5/10.
 */

public class ViewCacheManage extends ViewCacheManagerBaseClass {

    {
        this.mViewCacheTypeMap.put(ViewHolderStyle1.class.getName(), ViewHolderStyle1.class);
        this.mViewCacheTypeMap.put(ViewHolderStyle2.class.getName(), ViewHolderStyle2.class);
        this.mViewCacheTypeMap.put(ViewHolderStyle3.class.getName(), ViewHolderStyle3.class);
    }

}
