import java.util.regex.*;


public class test {

    public static String getLastWord(String body){
        String result="";
        String[] splits=body.split("。");
        for(String str:splits){
            result+=str.substring(str.length()-1);
        }
        return result;
    }
    public static final String hanziPattern = "[\\u4e00-\\u9fa5|，|。|（|）]+";
    public static final String authorPattern = "☆【[\\u4e00-\\u9fa5]+】";
    public static final String titlePattern = "【.*】+[\\u4e00-\\u9fa5|，|。|！|；|、|《|》]+";
    public static boolean isALLHanzi(String line) {
        return Pattern.matches(hanziPattern, line);
    }
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n| | ");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            dest = dest.replace((char) 12288, ' ');
            dest=dest.trim();
        }
        return dest;
    }
    public static String[] getTitleBody(String line){
        String[] ret = new String[2];
        String[] splits = line.split("】");
        if(splits.length==3){
            String title = splits[0].replace("【","")+
                           "（"+splits[1].replace("【","")+"）";
            String body =splits[2].trim();
            ret[0] = title;
            ret[1]=body;

        }else if (splits.length==2){
            String title = splits[0].replace("【","");
            String body =splits[1].trim();
            ret[0] = title;
            ret[1]=body;
        }
        return ret;
    }
    public static void main(String[]args){
        String tempLine ="刘伯亨，一作刘百亨，瞽者，为书会艺人，生平、里籍均不详。";

        System.out.println(Pattern.matches(authorPattern,tempLine));





    }
}
