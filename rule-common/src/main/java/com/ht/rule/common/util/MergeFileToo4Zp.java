package com.ht.rule.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 得到一个文件夹下所有某后缀的文件，并合并到一个文件中
 *
 * @author zhangpeng
 * data:2018/4/28
 */
public class MergeFileToo4Zp {
    private final static String SUFFIX = ".java";
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //格式 实例
       // mergeFileAll("D:/work/dispatch/dispatch-rule-service/src/main/java/com/ht/dispatch/rule/service", "d:/test/code2.txt");

    }

    /**
     * 得到一个文件夹下所有sql文件，并合并到一个sql文件中
     *
     * @param startFilePath 输入文件夹名
     * @param endFilePath   要生成的路径
     * @throws IOException
     */
    public static void mergeFileAll(String startFilePath, String endFilePath) throws IOException {
       // java.io.File file = new java.io.File(startFilePath);
       // List<String> listName = getFileList(file);
       //组合所有需要合并的文件
        getFiles(startFilePath);
        //合并文件
        list.forEach(file ->{
            mergeFileOne(file, endFilePath);
        });
    }

    /**
     * 复制单个文件
     * startFilePath :需要去合成的文件路径
     * endFilePath：将要合成的文件路径
     */
    public static void mergeFileOne(String startFilePath, String endFilePath) {
        try {
            // 如果不存在，那么就新建一个
            File file = new File(endFilePath);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file,
                    true);
            File oldfile = new File(startFilePath);
            // 如果不存在，那么就新建一个
            if (!oldfile.exists())
                oldfile.createNewFile();
            FileInputStream fis = new FileInputStream(oldfile);
            byte[] b = new byte[1];
            while ((fis.read(b)) != -1) {
                fos.write(b);
            }
            fos.flush();
            System.out.println(startFilePath + " copy success!");
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }


    /**
     * 获取一个文件夹下的所有文件 要求：后缀名为sql (可自己修改)
     *
     * @param file
     * @return
     */
    public static List<String> getFileList(File file) {
        List<String> result = new ArrayList<String>();
        if (!file.isDirectory()) {
            System.out.println("文件目录" + file.getAbsolutePath());
            result.add(file.getAbsolutePath());
        } else {
            // 内部匿名类，用来过滤文件类型
            File[] directoryList = file.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isFile()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            for (int i = 0; i < directoryList.length; i++) {
                result.add(directoryList[i].getAbsolutePath());
            }
        }
        return result;
    }

    /**
     *
     * 通过递归得到某一路径下所有的目录及其文件
     * @param filePath
     */
    static void getFiles(String filePath) {
        ArrayList<String> filelist = new ArrayList<String>();
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
            /*
            * 递归调用
            */
            getFiles(file.getAbsolutePath());
            filelist.add(file.getAbsolutePath());
                // System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
            } else {
                if (file.getAbsolutePath().indexOf(SUFFIX) > -1)
                    list.add(file.getAbsolutePath());
                // System.out.println("显示"+filePath+"下所有子目录"+file.getAbsolutePath());
            }
        }
    }
}