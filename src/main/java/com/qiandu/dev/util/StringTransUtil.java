package com.qiandu.dev.util;

import java.util.ArrayList;

/**
 * Created by LTN on 2016/11/14.
 */
public class StringTransUtil {
    public static ArrayList<Integer> transStr2Int(String idStr) {
        ArrayList<Integer> idList = new ArrayList<>();
        String[] idStr_a = idStr.split(",");
        for(int i=0;i<idStr_a.length;i++) {
            if (idStr_a[i].contains("-")) {
                String[] range = idStr_a[i].split("-");
                int startNum = Integer.parseInt(range[0]);
                int endNum = Integer.parseInt(range[1]);
                if (startNum >= endNum) {
                    continue;
                } else {
                    for(int j=startNum;j<=endNum;j++) {
                        idList.add(j);
                    }
                }
            }else {
                idList.add(Integer.parseInt(idStr_a[i]));
            }
        }
        return idList;
    }
}
