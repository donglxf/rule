package com.ht.rule.common.util;

import java.util.List;


public class CreateUtil {

    public static String appendPrivate(List<String> types, List<String> attributes, List<String> mark) {
        StringBuffer sb = new StringBuffer();
        if (types.size() == attributes.size() && types.size() == mark.size()) {
            for (int i = 0; i < types.size(); i++) {
                sb.append("\t" + "@ApiModelProperty(value = \"" + mark.get(i) + "\")" + "\n");
                sb.append("\t" + "private " + types.get(i) + " " + attributes.get(i) + ";\n\n");
            }
        }
        return sb.toString();
    }
}