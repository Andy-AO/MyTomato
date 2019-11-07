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

    public String polish(){
        preCheck();
        splitString();
        parseToInt();
        numberRangeCheck();
        polishedText = String.format("%02d:%02d", hour, minute);
        return polishedText;
    }

    private void numberRangeCheck() {
        boolean isHourMatched = (hour >= 0) && (hour <= 23) ;
        if(!isHourMatched){
            String hourFormat = "Text '%s' could not be parsed:valid values is 0-23.";
            throw new RuntimeException(String.format(hourFormat, hourString));
        }

        boolean isMinuteMatched = (minute >= 0) && (minute <= 59);
        if(!isMinuteMatched){
            String minuteFormat = "Text '%s' could not be parsed:valid values is 0-59.";
            throw new RuntimeException(String.format(minuteFormat, minuteString));
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
        matcher.find();
        minuteString = matcher.group();
    }

    private void preCheck(){
        boolean isMatched = Pattern.matches(CHECK_REGEX, srcText);
        if (!isMatched) {
            String mes = String.format("Text'%s' could not be parsed:valid values is \\d+:\\d+(RegEx).", srcText);
            throw new RuntimeException(mes);
        }
    }
}
