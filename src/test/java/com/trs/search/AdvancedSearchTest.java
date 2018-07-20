package com.trs.search;

/**
 * Created by xuo on 2017/4/5.
 * 高级检索表达式测试
 */
//not/seg  同一段中只能出现前者
//
//测试语句sql
    //(name,keyword,content)+=(优化 not/sen 科技 or (技术,格式) or/sen=1 创新)
public class AdvancedSearchTest {
    public static void main(String[] args) {
        //111 "222" (333) -(444) author:(555) journal:(666)
        String word = "化工  range:(1)";
//        拼接的展示检索语句：  化工 "化学" (化学) -(硫酸) author:(马) company:(北京)

        //包含全部检索词
        String keyword = "";
        //包含精确词
        String accurate = "";
        //最少包含一个词
        String least = "";
        //不包含检索词
        String noInclude = "";
        //检索词位置
        String position = "name,keyword,content";
        //作者
        String author = "";
        //单位
        String company = "";
        //时间
        String startTime = "";
        String endTime = "";
        //范围
        String range = "";

        String[] split = word.split(" ");
        StringBuffer buffer = new StringBuffer();
        for (int i = 0 ;i<split.length;i++){
            keyword = split[0];
            if(split[i].startsWith("\"")){
                accurate = split[i].replace("\"","");
            }
            if(split[i].startsWith("(")){
                least = split[i].replace("(","").replace(")","");
            }
            if(split[i].startsWith("-(")){
                noInclude=split[i].substring(1).replace("(","").replace(")","");
            }
            if(split[i].startsWith("author:")){
                author = split[i].split(":")[1].replace("(","").replace(")","");
            }
            if(split[i].startsWith("company:")){
                company = split[i].split(":")[1].replace("(","").replace(")","");
            }
            if(split[i].startsWith("range:")){
                range = split[i].split(":")[1].replace("(","").replace(")","");
            }
        }
        StringBuffer sqlBuffer = new StringBuffer();
        StringBuffer reBuffer = new StringBuffer();
        sqlBuffer.append("("+position+")+=("+keyword+"");
        reBuffer.append(keyword);
       if(!accurate.equals("")){
           sqlBuffer.append(" or \""+accurate+"\"");
           reBuffer.append(" \""+accurate+"\"");
       }
        if(!least.equals("")){
            sqlBuffer.append(" and ("+ least+") or/att ("+accurate+")");
            reBuffer.append(" ("+least+")");
        }
       if(!noInclude.equals("")){
           sqlBuffer.append(" not/sen ("+noInclude+")");
           reBuffer.append(" -("+noInclude+")");
       }
       sqlBuffer.append(",50)");
        if(!author.equals("")){
            sqlBuffer.append(" and author="+author+"");
            reBuffer.append(" author:("+author+")");
        }
        if(!company.equals("")){
            sqlBuffer.append(" and company="+company+"");
            reBuffer.append(" company:("+company+")");
        }
        if(!range.equals("")){
            sqlBuffer.append(" and type="+range+"");
        }
        if(!startTime.equals("")){
            sqlBuffer.append(" and pub_date="+startTime+"");
        }
        if(!endTime.equals("")){
            sqlBuffer.append(" and pub_date="+endTime+"");
        }
        String s = sqlBuffer.toString();



        //返回页面的检索表达式
        System.out.println("拼接的展示检索表达式：  "+reBuffer);
        System.out.println("检索语句：  "+s);
    }
}
