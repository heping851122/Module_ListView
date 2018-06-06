package com.example.module4;

import com.example.module3.LastModuleNode;

import java.util.ArrayList;

/**
 * Created by he_p on 2018/6/6.
 */

public class LastModuleNode2 extends LastModuleNode {

    public LastModuleNode2() {
        final ArrayList<String> dataList = new ArrayList<>();

        for (int j = 0; j < 10; j++) {
            dataList.add(String.format("LastModuleNode2 %d", j));
        }

        this.setData(dataList);
    }
}
