package test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        String text = "02:59";

        String regex_0 = "\\d\\d:\\d\\d";
        boolean isMatched_0 = Pattern.matches(regex_0,text);

//---------------------------------------

        String regex_1 = "\\d+";
        Pattern p=Pattern.compile(regex_1);
        Matcher m=p.matcher(text);
        ArrayList<String> numberStrings = new ArrayList<>();
        while(m.find()) {
            numberStrings.add(m.group());
        }
//---------------------------------------

    }
}
