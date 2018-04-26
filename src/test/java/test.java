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
    public static void main(String[]args){

        String line ="循涯不知浅，见底似非深。永日无波浪，澄澄照我心。";
        System.out.println(getLastWord(line));



    }
}
