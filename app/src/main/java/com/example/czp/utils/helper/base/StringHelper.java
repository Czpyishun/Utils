package com.example.czp.utils.helper.base;

import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    public int length(String paramString) {
        int i = 0;
        for (int j = 0; j < paramString.length(); j++) {
            if (paramString.substring(j, j + 1).matches("[Α-￥]")) {
                i += 2;
            } else {
                i++;
            }
        }
        return i / 2;
    }

    public boolean isContainSpace(String passWord) {
        boolean isContainSpace = false;
        for (int i = 0; i < passWord.toCharArray().length; i++) {
            if (Character.isSpaceChar(passWord.charAt(i))) {
                isContainSpace = true;
                break;
            }
        }
        return isContainSpace;
    }

    public boolean isEmpty(EditText editText) {
        return (editText == null || editText.getText() == null
                || (editText.getText().toString().trim().equals("")));
    }

    public boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }


    public boolean isSame(String str) {
        String regex = str.substring(0, 1) + "{" + str.length() + "}";
        return str.matches(regex);
    }


    public String formatMobile(String mobile) {
        return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
    }

    public int getWordCount(String s) {
        s = s.replaceAll("[^\\x00-\\xff]", "**");
        int length = s.length();
        return length;
    }

    public String substring(String str, int count) {
        int num = 0;// 已经截取字符的长度
        int length = 0;// 每个字符的长度
        StringBuffer sb = new StringBuffer();
        char ch[] = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            try {
                length = String.valueOf(ch[i]).getBytes("GBK").length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            num += length;
            if (num > count) {
                break;
            }
            sb.append(ch[i]);
        }
        return sb.toString();
    }


    private static final Pattern PATTERN_HTTP = Pattern.compile("http://[a-zA-Z0-9+&@#/%?=~_\\\\-|!:,\\\\.;]*[a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_HTTPS = Pattern.compile("https://[a-zA-Z0-9+&@#/%?=~_\\\\-|!:,\\\\.;]*[a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);
    private static final String HTTP_SCHEME = "http://";
    private static final String HTTPS_SCHEME = "https://";

    /*public SpannableString span(String text, Context mContext) {
        return this.span(text, mContext, null);
    }*/


    /*public SpannableString span(String text, Context mActivity, Integer color) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);
        Linkify.addLinks(ssb, PATTERN_HTTP, HTTP_SCHEME);
        Linkify.addLinks(ssb, PATTERN_HTTPS, HTTPS_SCHEME);
        URLSpan[] spans = ssb.getSpans(0, ssb.length(), URLSpan.class);
        for (URLSpan span : spans) {
            HttpClickSpan s = new HttpClickSpan(span.getURL(), mActivity, color);
            int start = ssb.getSpanStart(span);
            int end = ssb.getSpanEnd(span);
            ssb.removeSpan(span);
            ssb.setSpan(s, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return SpannableString.valueOf(ssb);
    }*/

}
