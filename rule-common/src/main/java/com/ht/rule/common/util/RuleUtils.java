package com.ht.rule.common.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：规则工具类
 * CLASSPATH: com.sky.RuleUtils
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/26
 */
public class RuleUtils {


    /**
     * 字符串为 null 或者为  "" 时返回 true
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 字符串不为 null 而且不为  "" 时返回 true
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 截取两个$之间的内容
     *
     * @param str 字符串
     */
    public static List<String> getConditionParamBetweenChar(String str) {

        Matcher m = Pattern.compile("\\$(.*?)\\$").matcher(str);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取运算符
     *
     * @param str 字符串
     */
    public static String getCondition(String str,String val) {

        Matcher m = Pattern.compile("\\$(.*?)"+val).matcher(str.substring(1));
        while (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public static String getConditionByMe(String str,String val) {

        str = str.substring(str.lastIndexOf("$")+1,str.lastIndexOf(val));
        return str;
    }
    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取运算符
     *
     * @param str 字符串
     */
    public static String getCondition(String str) {

        Matcher m = Pattern.compile("\\$(.*?)"+"").matcher(str.substring(1));
        while (m.find()) {
            return m.group(1);
        }
        return null;
    }
    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明:截取两个@之间的内容
     */
    public static List<String> getValueBetweenChar(String str) {

        Matcher m = Pattern.compile("\\@(.*?)\\@").matcher(str);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 截取两个#之间的内容
     */
    public static List<String> getActionParamBetweenChar(String str) {
        Matcher m = Pattern.compile("\\#(.*?)\\#").matcher(str);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 截取{{}}之间的内容
     */
    public static String getValBetweenChar(String str, Map<String,Long> data ) {
        Matcher m = Pattern.compile("\\#(.*?)\\#").matcher(str);
        List<String> list = new ArrayList<>();
        String result = "";
        while (m.find()) {
            list.add(data.get(m.group(1)).toString());
        }
        Matcher m2 = Pattern.compile("\\{\\{(.*?)\\}\\}").matcher(str);
        while (m2.find()) {
            Long id = data.get(m2.group(1));
            if( id != null ){
                list.add(id.toString());
            }
        }
        result = StringUtils.join(list,",");
        return result;
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 截取{{}}之间的内容
     */
    public static String getValBetweenChar2(String str, Map<String,Long> data ) {
        Matcher m = Pattern.compile("\\{\\{(.*?)\\}\\}").matcher(str);
        List<String> list = new ArrayList<>();
        String result = "";
        while (m.find()) {
            list.add(data.get(m.group(1)).toString());
        }
        result = StringUtils.join(list,",");
        return result;
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 截取两个#之间的内容
     */
    public static String getParamBetweenChar(String str, String charStr) {
        Matcher m = Pattern.compile("/" + charStr + "(.*?)/" + charStr + "").matcher(str);
        String value = null;
        while (m.find()) {
            value = m.group(1);
        }
        return value;
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 如果是整数或者小数，则返回true，否则返回false
     */
    public static boolean checkStyleOfString(String str) {
        return str.matches("^(\\-|\\+)?\\d+(\\.\\d+)?$");
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明:得到 > >= < <= == != 后面的字符串
     */
    public static String getConditionOfVariable(String str) {

        String[] arr = str.split(">(=)?|<(=)?|={1,2}|!=|\\^contains|contains|memberOf|\\^memberOf");
        if (arr.length < 2) {
            return "";
        }
        return arr[1];
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明:检查是否包含 + - × / % 如果包含返回true
     */
    public static boolean checkContainOfOperator(String str) {
        return str.contains("+") || str.contains("-") || str.contains("*") ||
                str.contains("/") || str.contains("%");
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明:判断当前字符串是否包含 特殊字符 信息,如果包含返回true
     */
    public static boolean checkContainOfOperator(String str, String charStr) {
        return str.contains(charStr);
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据属性得到其get方法名
     */
    public static String getMethodByProperty(String property) {
        //为防止原参数发生变化，此处单独创建变量接收
        String tempStr = property;
        if (RuleUtils.isNotEmpty(property)) {
            tempStr = tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1);
            return "get" + tempStr + "()";
        }
        return null;
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据属性得到其set方法名
     */
    public static String setMethodByProperty(String property) {
        //为防止原参数发生变化，此处单独创建变量接收
        String tempStr = property;
        if (RuleUtils.isNotEmpty(property)) {
            tempStr = tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1);
            return "set" + tempStr;
        }
        return null;
    }

    public static void main(String[] args) {
     //   String aa = "$11$==100"
//        System.out.print( getCondition("$11$==100","100"));
//        int count = 1;
//        double maxVersion = 1.0 + count*0.1;
//        System.out.print(maxVersion);
        System.out.print( getConditionByMe("$参数:性别$等于'2'","'2'"));

    }
}
