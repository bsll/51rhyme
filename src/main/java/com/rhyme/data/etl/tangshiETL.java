package com.rhyme.data.etl;


import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class tangshiETL {

    public static  final Set<String> digitalSet = new HashSet<String>(){{
        add("一");
        add("二");
        add("三");
        add("四");
        add("五");
        add("六");
        add("七");
        add("八");
        add("九");
        add("十");
        add("百");
    }};

    public static  final Set<String> titalFlagCharacter = new HashSet<String>(){{
        add("】");
        add("【");
        add("_");
    }};

    public static final String defaultTital ="无题";
    public static final String defaultAuthor ="无名氏";
    public static final String source = "全唐诗";
    public static final String year = "唐朝";


    public static boolean IsTital(String initial){
        boolean flag =true;
        for(String str :titalFlagCharacter){
            if(!initial.contains(str)){
                flag =false;
                break;
            }
        }
        return flag;
    }



    public static final String hanziPattern="[\\u4e00-\\u9fa5|，|。]+";

    public static boolean isALLHanzi(String line){
        return Pattern.matches(hanziPattern, line);
    }

    public static boolean IsHaveDigits(String initial){
         boolean flag =false;
        for(String str :digitalSet){
            if(initial.contains(str)){
                flag =true;
                break;
            }
        }
         return flag;
    }


    public static String getLastWord(String body){
        String result="";
        String[] splits=body.split("。");
        for(String str:splits){
            result+=str.substring(str.length()-1);
        }



        return result;
    }
    public static  void process(String file, String out) {
        File f = new File(file);
        FileInputStream fis = null;
        InputStreamReader  isr =null;
        BufferedReader br = null;
        File o = new File(out);
        FileOutputStream fos =null;
        OutputStreamWriter osw = null;
        BufferedWriter bw=null;




        try{
           fis = new FileInputStream(f);
           isr = new InputStreamReader(fis);
           br = new BufferedReader(isr);
           fos = new FileOutputStream(o);
           osw = new OutputStreamWriter(fos);
           bw = new BufferedWriter(osw);
            String line =null;
            List<String> tempList = new ArrayList<String>();
            line = br.readLine();
            while(line!=null){
                if(IsTital(line)){
                    String []splits = line.split("【|】");

                    if(splits.length==3){
                        String title = splits[1].trim();
                        String author = splits[2].trim();
                        if(title.equals("")){title= defaultTital;}
                        if(author.equals("")){title= defaultAuthor;}
                        line = br.readLine();
                        while(line!=null&&!IsTital(line)){
                            if(!line.contains("制作")&&!line.contains("----------")&&!(line.trim().startsWith("卷")&&IsHaveDigits(line.trim()))){
                                tempList.add(line);
                            }
                            line = br.readLine();
                        }
                        String body = "";
                        for(String str:tempList){
                            body+=str.trim();
                        }
                        tempList.clear();
                        body = body.trim();


                        if(isALLHanzi(body)){
                            String lastWord = getLastWord(body);
                            rhymeBean bean = new rhymeBean(lastWord,body,source,author,year,title);
                            String t = bean.getJSONObject().toString();
                            System.out.println(t);
                            bw.write(t);
                            bw.newLine();
                            bw.flush();

                        }




                    }

                }else{
                    line =br.readLine();
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            bw.close();
            br.close();
        }catch (Exception e){
            e.printStackTrace();

        }

    }
    public static void main(String[]args){
        process("/Users/gaominghui/51rhyme/src/main/resources/全唐诗副本.txt",
                "/Users/gaominghui/51rhyme/src/main/resources/json.txt");
        System.out.println("done");

    }


}
