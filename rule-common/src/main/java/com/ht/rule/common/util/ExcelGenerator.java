package com.ht.rule.common.util;

import com.ht.rule.common.util.CreateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class ExcelGenerator {

    public static void main(String[] args) throws Exception, InvalidFormatException {
        //批量生成实体类
   //    excuteEntity("D:\\user2.xlsx");
    }

    /**
     * 老的生成一个的
     * @param path
     * @throws Exception
     */
    public static  void excuteEnityOne(String  path) throws  Exception{
        File file = new File(path);
       // File file = new File("D:\\user.xlsx");
        String fileName = file.getName();
        String className = "demo";
        File file1 = null;
        FileOutputStream fop = null;
        List<String> types = new ArrayList<String>();
        List<String> attributes = new ArrayList<String>();
        List<String> marks = new ArrayList<String>();
        if (fileName.endsWith("xlsx")) {
            XSSFWorkbook workbooks = new XSSFWorkbook(file);
            XSSFSheet xssfSheet = workbooks.getSheetAt(0);
            int totalRows = xssfSheet.getPhysicalNumberOfRows();
            XSSFRow row = xssfSheet.getRow(0);
            className = row.getCell(0).getStringCellValue();
            System.out.println(className + "total:" + totalRows);
            for (int i = 2; i < totalRows; i++) {
                XSSFRow row2 = xssfSheet.getRow(i);
                attributes.add(row2.getCell(0).getStringCellValue());
                types.add(row2.getCell(1).getStringCellValue());
                marks.add(row2.getCell(2).getStringCellValue());
            }
        } else {
            HSSFWorkbook workbook = new HSSFWorkbook(FileUtils.openInputStream(file));
            //读取默认第一个工作表sheet
            HSSFSheet sheet = workbook.getSheetAt(0);
            int firstRowNum = 0;
            //获取sheet中最后一行行号
            int lastRowNum = sheet.getLastRowNum();
            HSSFRow row = sheet.getRow(firstRowNum);
            className = row.getCell(0).getStringCellValue();
            for (int i = 2; i <= lastRowNum; i++) {
                HSSFRow row1 = sheet.getRow(i);
                attributes.add(row1.getCell(0).getStringCellValue());
                types.add(row1.getCell(1).getStringCellValue());
                marks.add(row1.getCell(2).getStringCellValue());
            }
        }


        StringBuffer sb = new StringBuffer();
        //导包
        sb.append("import io.swagger.annotations.ApiModelProperty;" + "\n");
        sb.append("import io.swagger.annotations.ApiModel;" + "\n");
        sb.append("import lombok.Data;" + "\n\n");
        //生成类
        sb.append("@Data" + "\n");
        sb.append("@ApiModel" + "\n");
        sb.append("public class " + className + "{\n\n");
        sb.append(com.ht.rule.common.util.CreateUtil.appendPrivate(types, attributes, marks));
        sb.append("}");

        file1 = new File("d:/" + className + ".java");
        fop = new FileOutputStream(file1);
        if (!file1.exists()) {
            file1.createNewFile();
        }
        byte[] contentInBytes = sb.toString().getBytes();

        fop.write(contentInBytes);
        fop.flush();
        fop.close();

        System.out.println("Done");
    }

    /**
     * 批量生成的
     * @param path
     * @throws Exception
     */
    public static void excuteEntity(String  path) throws Exception {
        File file = new File(path);
        String className = "";
        List<String> types = new ArrayList<String>();
        List<String> attributes = new ArrayList<String>();
        List<String> marks = new ArrayList<String>();
        Map<String, Integer> data = new HashMap<>();
        XSSFWorkbook workbooks = new XSSFWorkbook(file);
        XSSFSheet xssfSheet = workbooks.getSheetAt(0);

        //总行数
        int totalRows = xssfSheet.getPhysicalNumberOfRows();
        XSSFRow row = xssfSheet.getRow(0);
        className = row.getCell(0).getStringCellValue();
        System.out.println(className + "total:" + totalRows);
        int index = 0;
        for (int i = 0; i < totalRows; i++) {
            XSSFRow row2 = xssfSheet.getRow(i);
            String cell0 = row2.getCell(0).getStringCellValue();
            if(StringUtils.isBlank(cell0)){
                break;
            }
            short maxn = row2.getLastCellNum();
            if(maxn <= 2 ){
                //设置进去相关信息
                className = cell0;
                continue;
            }
            if(maxn > 2){
                String cell1 = row2.getCell(1).getStringCellValue();
                String cell2 = row2.getCell(2).getStringCellValue();
                if("属性名".equals(cell0)){
                    continue;
                }
                data.put(className,index);
                attributes.add(cell0);
                types.add(cell1);
                marks.add(cell2);
                index ++;
            }
        }
        //map排序
        Map<String,Integer> linkMap =  sortMap(data);
        int start = 0 ;
        //组合封装
        for(Map.Entry<String, Integer> mapEntry : linkMap.entrySet()){
            System.out.println("key:"+mapEntry.getKey()+"  value:"+mapEntry.getValue());
            String clazz = mapEntry.getKey();
            int  end = data.get(clazz) +1;
            generalListEntity(clazz,types.subList(start,end),
                    attributes.subList(start,end),
                    marks.subList(start,end));
            start = end;
        }


    }
    /**
     * 生成
     * @param className
     * @param types
     * @param attributes
     * @param marks
     * @throws Exception
     */
    public static void generalListEntity( String className, List<String> types,List<String> attributes, List<String> marks) throws Exception{
        File file1 = null;
        FileOutputStream fop = null;
        StringBuffer sb = new StringBuffer();
        //导包
        sb.append("import io.swagger.annotations.ApiModelProperty;" + "\n");
        sb.append("import io.swagger.annotations.ApiModel;" + "\n");
        sb.append("import lombok.Data;" + "\n\n");
        //生成类
        sb.append("@Data" + "\n");
        sb.append("@ApiModel" + "\n");
        sb.append("public class " + className + "{\n\n");
        sb.append(CreateUtil.appendPrivate(types, attributes, marks));
        sb.append("}");

        file1 = new File("d:/" + className + ".java");
        fop = new FileOutputStream(file1);
        if (!file1.exists()) {
            file1.createNewFile();
        }
        byte[] contentInBytes = sb.toString().getBytes();

        fop.write(contentInBytes);
        fop.flush();
        fop.close();
        System.out.println("生成了"+className);


    }
    public static Map<String,Integer> sortMap(Map<String, Integer> map){
        //获取entrySet
        Set<Map.Entry<String,Integer>> mapEntries = map.entrySet();

        for(Map.Entry<String, Integer> entry : mapEntries){
            System.out.println("key:" +entry.getKey()+"   value:"+entry.getValue() );
        }

        //使用链表来对集合进行排序，使用LinkedList，利于插入元素
        List<Map.Entry<String, Integer>> result = new LinkedList<>(mapEntries);
        //自定义比较器来比较链表中的元素
        Collections.sort(result, new Comparator<Map.Entry<String, Integer>>() {
            //基于entry的值（Entry.getValue()），来排序链表
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {

                return o1.getValue().compareTo(o2.getValue()) ;
            }

        });

        //将排好序的存入到LinkedHashMap(可保持顺序)中，需要存储键和值信息对到新的映射中。
        Map<String,Integer> linkMap = new LinkedHashMap<>();
        for(Map.Entry<String,Integer> newEntry :result){
            linkMap.put(newEntry.getKey(), newEntry.getValue());
        }
        //根据entrySet()方法遍历linkMap
        for(Map.Entry<String, Integer> mapEntry : linkMap.entrySet()){
            System.out.println("key:"+mapEntry.getKey()+"  value:"+mapEntry.getValue());
        }
        return linkMap;
    }
    public String appendPrivate(List<String> types, List<String> attributes, List<String> mark) {
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