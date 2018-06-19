package com.ht.rule.common.generator;

/**
 * 说明：
 *
 * @auther 张鹏
 * @create
 */
public class InputConfig {

    private  String basePackage = "com.ht.litigation.";
    //项目名称
    /**
     * 项目名
     */
    private  String projectName = "litigation-service";
    /**
     * 包模块名
     */
    private  String packageName="service";    //包模块名
    /**
     * 作者
     */
    private  String authorName="张鹏";     //作者

    private  String table="tb_litigation_process";                  //table名字
    private  String prefix="tb_";

    private String superBasePackage = "com.ht.litigation.common";
    /**
     *
     * @param superBasePackage 父类生成的包 主要是 ***.BaseService,***.BaseMapper,
     * @param packageName 包模块名
     * @param projectName 项目名称
     * @param prefix 前缀
     */
    public InputConfig(String basePackage,String superBasePackage ,String packageName,String projectName,String prefix){
        this.basePackage = basePackage;
        this.superBasePackage = superBasePackage;
        this.projectName = projectName;
        this.packageName = packageName;
        this.prefix = prefix;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuperBasePackage() {
        return superBasePackage;
    }

    public void setSuperBasePackage(String superBasePackage) {
        this.superBasePackage = superBasePackage;
    }
}
