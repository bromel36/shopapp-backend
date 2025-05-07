package vn.ptithcm.shopapp.util;



public class StringUtil {
    public static final String EMAIL_SUBJECT = "VERIFY YOUR EMAIL";
    public static final String WEB_HEADER = "WEB";
    public static final String ANDROID_HEADER = "ANDROID";
    public static boolean isValid(String str) {
        return str != null && !str.isBlank();
    }
}
