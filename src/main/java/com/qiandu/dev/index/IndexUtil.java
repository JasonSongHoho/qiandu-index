package com.qiandu.dev.index;

import com.qiandu.dev.util.PropertiesUtil;

import java.util.Properties;

/**
 * Created by LTN on 2016/9/14.
 */
public class IndexUtil {
    public static boolean isSuggestField(String fieldUri) {
        Properties properties = PropertiesUtil.getProperties("elasticsearch.properties");
        String suggestFieldStr = properties.get("suggestField").toString();
        String[] suggestField_a = suggestFieldStr.split("\\|");
        boolean isSuggestField=false;
        for (String field : suggestField_a) {
            if (fieldUri.equals(field)) {
                isSuggestField = true;
            }
        }
        return isSuggestField;
    }
}
