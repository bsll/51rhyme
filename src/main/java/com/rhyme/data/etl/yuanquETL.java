package com.rhyme.data.etl;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class yuanquETL {

    public static final String separate = "全元曲";
    public static final String defaultTital = "无题";
    public static final String defaultAuthor = "无名氏";
    public static final String source = "全元曲";
    public static final String year = "元朝";
    public static final String hanziPattern = "[\\u4e00-\\u9fa5|，|。|（|）|！|；|、|《|》]+";
    public static final String authorPattern = "☆【[\\u4e00-\\u9fa5]+】";
    public static final String titleAndBodyPattern = "【.*】+[\\u4e00-\\u9fa5|，|。|！|；|、|《|》]+";

    public static boolean isAuthor(String line) {
        return Pattern.matches(authorPattern, line);
    }

    public static boolean isTitleAndBody(String line) {
        return Pattern.matches(titleAndBodyPattern, line);
    }

    public static boolean isALLHanzi(String line) {
        return Pattern.matches(hanziPattern, line);
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            dest = dest.replace((char) 12288, ' ');
            dest = dest.trim();
        }
        return dest;
    }

    public static String[] getTitleBody(String line) {
        String[] ret = new String[2];
        String[] splits = line.split("】");
        if (splits.length == 3) {
            String title = splits[0].replace("【", "") +
                    "（" + splits[1].replace("【", "") + "）";
            String body = splits[2].trim();
            ret[0] = title;
            ret[1] = body;

        } else if (splits.length == 2) {
            String title = splits[0].replace("【", "");
            String body = splits[1].trim();
            ret[0] = title;
            ret[1] = body;
        }
        return ret;
    }

    public static String getLastWord(String body) {
        String result = "";
        String[] splits = body.split("。|；|！|，");
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
            line = br.readLine();
            List<String> tempList = new ArrayList<String>();
            while (line != null) {

                if (isAuthor(replaceBlank(line))) {

                    String author = line.replace("☆", "")
                            .replace("【", "").replace("】", "");

                    line = br.readLine();
                    while (line != null && !isAuthor(line)) {
                        line = replaceBlank(line);
                        if (isTitleAndBody(line)) {
                            String[] titleAndBody = getTitleBody(line);
                            String body = titleAndBody[1];
                            if (body.length() > 5 && (body.contains("，") || body.contains("。"))) {
                                String lastWord = getLastWord(titleAndBody[1]);
                                rhymeBean bean = new rhymeBean(lastWord, titleAndBody[1], source, author, year, titleAndBody[0]);
                                String t = bean.getJSONObject().toString();
                                System.out.println(t);
                                bw.write(t);
                                bw.newLine();
                                bw.flush();
                            }

                        }
                        line = br.readLine();
                    }

                    line = br.readLine();

                } else {

                    line = br.readLine();
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        process("/Users/gaominghui/51rhyme/src/main/resources/全元曲.txt",
                "/Users/gaominghui/51rhyme/src/main/resources/yuanquJson.txt");
        System.out.println("done");

    }


}
