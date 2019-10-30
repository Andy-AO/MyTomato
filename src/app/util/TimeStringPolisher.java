package app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeStringPolisher {

    private  String srcText;
    public static final String CHECK_REGEX = "\\d+:\\d+";
    public static final String SPLIT_REGEX = "\\d+";

    private  String minuteString;
    private String hourString;
    private int minute;
    private int hour;
    private String polishedText;

    public TimeStringPolisher(String srcText) {
        this.srcText = srcText;
    }

    public static void main(String[] args) throws Exception {

        String text = "2:3";
        TimeStringPolisher timeStringPolisher = new TimeStringPolisher(text);
        String newText = timeStringPolisher.polish();
        System.out.println("newText -> " + newText);

    }

    public String polish() throws Exception {
        preCheck();
        splitString();
        parseToInt();
        numberRangeCheck();
        polishedText = String.format("%02d:%02d", hour, minute);
        return polishedText;
    }

    private void numberRangeCheck() throws Exception {
        boolean isHourMatched = (hour >= 0) && (hour <= 23) ;
        if(!isHourMatched){
            String hourFormat = "Text '%s' could not be parsed:valid values is 0-23.";
            throw new Exception(String.format(hourFormat, hourString));
        }

        boolean isMinuteMatched = (minute >= 0) && (minute <= 59);
        if(!isMinuteMatched){
            String minuteFormat = "Text '%s' could not be parsed:valid values is 0-59.";
            throw new Exception(String.format(minuteFormat, minuteString));
        }
    }

    private void parseToInt() {
        hour = Integer.valueOf(hourString);
        minute = Integer.valueOf(minuteString);
    }

    private void splitString() {
        Pattern pattern = Pattern.compile(SPLIT_REGEX);
        Matcher matcher = pattern.matcher(srcText);
        matcher.find();
        hourString = matcher.group();
        System.out.println("hourString -> " + hourString);
        matcher.find();
        minuteString = matcher.group();
        System.out.println("minuteString -> " + minuteString);
    }

    private void preCheck() throws Exception {
        boolean isMatched = Pattern.matches(CHECK_REGEX, srcText);
        if (!isMatched) {
            String mes = String.format("Text'%s' could not be parsed:valid values is \\d+:\\d+(RegEx).", srcText);
            throw new Exception(mes);
        }
    }
}
