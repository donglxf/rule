package com.ht.rule.common.util;

import com.ht.rule.common.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangwenchao  
 * @since 2016-08-26  
 * 公式计算的工具类  
 */  
public class FormulaCalculator {  
      
    private static boolean isRightFormat = true;  
   
    public static double getResult(String formula){    
        double returnValue = 0;    
        try{    
            returnValue = doAnalysis(formula);    
        }catch(NumberFormatException nfe){     
            System.err.println("公式格式有误，请检查:" + formula);
        }catch(Exception e){     
            e.printStackTrace();    
        }   
        if(!isRightFormat){    
            System.err.println("公式格式有误，请检查:" + formula);
        }   
        return returnValue;   
    }  
   
    private static double doAnalysis(String formula){  
        double returnValue = 0;    
        LinkedList<Integer> stack = new LinkedList<Integer>();
        int curPos = 0;    
        String beforePart = "";   
        String afterPart = "";    
        String calculator = "";   
        isRightFormat = true;   
        while(isRightFormat&&(formula.indexOf('(') >= 0||formula.indexOf(')') >= 0)){           
            curPos = 0;    
            for(char s : formula.toCharArray()){     
                if(s == '('){       
                    stack.add(curPos);     
                }else if(s == ')'){       
                    if(stack.size() > 0){       
                        beforePart = formula.substring(0, stack.getLast());       
                        afterPart = formula.substring(curPos + 1);       
                        calculator = formula.substring(stack.getLast() + 1, curPos);        
                        formula = beforePart + doCalculation(calculator) + afterPart;       
                        stack.clear();       
                        break;       
                    }else{        
                        System.out.println("有未关闭的右括号！");       
                        isRightFormat = false;       
                    }     
                }      
                curPos++;     
            }     
            if(stack.size() > 0){      
                System.out.println("有未关闭的左括号！");     
                break;     
            }   
        }    
        if(isRightFormat){    
            returnValue = doCalculation(formula);   
        }    
        return returnValue;   
    }  
      
    private static double doCalculation(String formula) {    
        ArrayList<Double> values = new ArrayList<Double>();
        ArrayList<String> operators = new ArrayList<String>();   
        int curPos = 0;   
        int prePos = 0;  
        int minus = 0;        
        for (char s : formula.toCharArray()) {   
             if ((s == '+' || s == '-' || s == '*' || s == '/') && minus !=0 && minus !=2) {                                               
                 values.add(Double.parseDouble(formula.substring(prePos, curPos).trim()));                    
                 operators.add("" + s);                   
                 prePos = curPos + 1;                  
                 minus = minus +1;  
             }else{                
                  minus =1;                
             }  
             curPos++;        
        }    
        values.add(Double.parseDouble(formula.substring(prePos).trim()));   
        char op;    
        for (curPos = 0; curPos <= operators.size() - 1; curPos++) {                           
            op = operators.get(curPos).charAt(0);     
            switch (op) {    
            case '*':      
                values.add(curPos, values.get(curPos) * values.get(curPos + 1));     
                values.remove(curPos + 1);      
                values.remove(curPos + 1);     
                operators.remove(curPos);    
                curPos = -1;  
                break;     
            case '/':     
                values.add(curPos, values.get(curPos) / values.get(curPos + 1));      
                values.remove(curPos + 1);      
                values.remove(curPos + 1);      
                operators.remove(curPos);    
                curPos = -1;  
                break;     
            }    
        }    
        for (curPos = 0; curPos <= operators.size() - 1; curPos++) {     
            op = operators.get(curPos).charAt(0);    
            switch (op) {    
            case '+':      
                values.add(curPos, values.get(curPos) + values.get(curPos + 1));      
                values.remove(curPos + 1);      
                values.remove(curPos + 1);     
                operators.remove(curPos);    
                curPos = -1;  
                break;   
            case '-':      
                values.add(curPos, values.get(curPos) - values.get(curPos + 1));     
                values.remove(curPos + 1);   
                values.remove(curPos + 1);     
                operators.remove(curPos);    
                curPos = -1;  
                break;    
            }   
        }    
        return values.get(0).doubleValue();  
    }

    /**
     * 描述：替换变量值，并得到结果
     * @auhor 张鹏
     * @date 2018/4/18 13:47
     */
    public static double getResult(String str, Map<String,Object > data){
        if(str.indexOf("@") > -1){
            //转换公式
            str = str.replaceAll("@","");
            //替换{{}}之间的内容
            Matcher m = Pattern.compile("\\{\\{(.*?)\\}\\}").matcher(str);
            while (m.find()) {
                String key = m.group(1);
                String keyS = m.group(0);
                String val = "";
                Object valO = data.get(key);
                if(valO != null ){
                    val = String.valueOf(valO);
                }
                str = str.replace(keyS,val);
            }
            return getResult(str);
        }else{
            return 0.0;
        }
    }

    public static String getDroolsStr(String str){
        if(str.indexOf("@") > -1){
            //转换公式
            str = str.replaceFirst("@","(");
            str = str.replace("@",")");

            //替换{{}}之间的内容
            Matcher m = Pattern.compile("\\{\\{(.*?)\\}\\}").matcher(str);
            while (m.find()) {
                String key = m.group(1);
                String keyS = m.group(0);
                String val = "this[\""+key+"\"]";
                str = str.replace(keyS,val);
            }
            return str;
        }else{
            return str;
        }
    }
    public static String getDroolsStr4action(String str){
        if(str.indexOf("@") > -1){
            //转换公式
            str = str.replaceFirst("@","(");
            str = str.replace("@",")");

            //替换{{}}之间的内容
            Matcher m = Pattern.compile("\\{\\{(.*?)\\}\\}").matcher(str);
            while (m.find()) {
                String key = m.group(1);
                String keyS = m.group(0);
                String val = "$map[\""+key+"\"]";
                str = str.replace(keyS,val);
            }
            return str;
        }else{
            return str;
        }
    }


    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 截取{{}}之间的内容
     */
    public static String getValBetweenChar(String str ) {
        List<String> list = new ArrayList<>();
        String result = "";
        Matcher m = Pattern.compile("\\{\\{(.*?)\\}\\}").matcher(str);
        while (m.find()) {
            list.add(m.group(0));
            list.add(m.group(1));
        }
        result = StringUtils.join(list,",");
        return result;
    }
   
    public static void main(String[] args) {
        Map<String,Object > data = new HashMap<>();
        data.put("sys_user_avgHandleOrderCount",20.0);

        System.out.println(getDroolsStr("@{{sys_user_avgHandleOrderCount}}*1.2+(1-2)@"));


        System.out.println(FormulaCalculator.getResult("7/2-(-4)"));              
        System.out.println(FormulaCalculator.getResult("1287763200000-1276272000000")/(3600*24*1000));  
    }  
      
}  