package com.rhyme.data.etl;


import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class songciETL {

    public static final String separate ="全宋词";
    public static final String defaultTital = "无题";
    public static final String defaultAuthor = "无名氏";
    public static final String source = "全宋词";
    public static final String year = "宋朝";
    public static final String hanziPattern = "[\\u4e00-\\u9fa5|，|。|（|）|！|；|、]+";

    public static boolean isALLHanzi(String line) {
        return Pattern.matches(hanziPattern, line);
    }
    public static boolean isTitle(String tempLine){
        return isALLHanzi(tempLine)&&(!tempLine.contains("。")&&!tempLine.contains("，"))||(tempLine.contains("（")&&tempLine.contains("）"));
    }
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    public static String getLastWord(String body) {
        String result = "";
        String[] splits = body.split("。|；|！");
        for (String str : splits) {
            result += str.substring(str.length() - 1);
        }
        return result;
    }

    public static void process(String file, String out) {
        File f = new File(file);
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        File o = new File(out);
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;


        try {
            fis = new FileInputStream(f);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            fos = new FileOutputStream(o);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            String line = null;
            List<String> tempList = new ArrayList<String>();
            line = br.readLine();
            while (line != null) {

                if(line.trim().endsWith(separate)){
                    line = br.readLine();
                    while(line!=null &&!line.trim().endsWith(separate)){
                        if(!line.trim().equals("")){
                            tempList.add(replaceBlank(line.trim()));

                        }
                        line = br.readLine();
                    }
                    //解析list
                    String author = tempList.get(0);
                    int index = 1;
                    while(index<tempList.size()){
                        String tempLine = tempList.get(index);
                        String title =null;
                        tempLine =replaceBlank(tempLine.trim());
                        if(isTitle(tempLine)){
                            title = tempLine;
                            index++;
                            tempLine = tempList.get(index);
                            String body="";
                            while(!isTitle(tempLine)&&index<tempList.size()-1){
                                body+=tempLine;
                                index++;
                                tempLine = tempList.get(index);
                            }
                           if(body.trim()!=""&&title.trim()!=null&&isALLHanzi(body)&&isALLHanzi(title)){
                               String lastWord = getLastWord(body);
                               rhymeBean bean = new rhymeBean(lastWord, body, source, author, year, title);
                               String t = bean.getJSONObject().toString();
                               System.out.println(t);
                               bw.write(t);
                               bw.newLine();
                               bw.flush();
                           }
                        }else{
                            index++;
                        }



                    }

                    /*
                    String temp ="";
                    for(String s:tempList){
                        temp +=s;
                    }
                    System.out.println(temp);
                    */
                    tempList.clear();

                }else{
                    line = br.readLine();

                }



            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        process("/Users/gaominghui/51rhyme/src/main/resources/全宋词副本.txt",
                "/Users/gaominghui/51rhyme/src/main/resources/songCiJson.txt");
        System.out.println("done");

    }


}
