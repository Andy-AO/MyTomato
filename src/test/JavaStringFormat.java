package test;

public class JavaStringFormat {
    public static void main(String[] args) {
        String minuteFormat = "Text'%s' could not be parsed:valid values is 0-59.";
        String s = String.format(minuteFormat, minuteFormat);
    }
}
