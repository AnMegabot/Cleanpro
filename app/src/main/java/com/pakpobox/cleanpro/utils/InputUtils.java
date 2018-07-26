package com.pakpobox.cleanpro.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User:Sean.Wei
 * Date:2018/4/3
 * Time:16:12
 */

public class InputUtils {
    public static final String REG_NAME = "^[A-Za-z \u4e00-\u9fa5]+$";
    public static final String REG_CHINESE_ID = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
    public static final String REG_SINGAPOR_ID = "^[a-zA-Z]\\d{7}[a-zA-Z]$";
    public static final String REG_CHINA_PHONE = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
    public static final String REG_SINGAPOR_PHONE = "\\d{8}$";
    public static final String REG_CHINESE_NAME = "^[\u4e00-\u9fa5]+$";
    /** 检查护照是否合法 */
    public static final String REG_PASSPORT1 = "^[a-zA-Z]{5,17}$";
    public static final String REG_PASSPORT2 = "^[a-zA-Z0-9]{5,17}$";
    public static final String REG_LICENSE = "(^\\d{15}$)|(^[A-Za-z\\d]{18}$)";
    public static final String REG_LICENSE_SINGAPOR = "^\\d{9}[a-zA-Z]$";
    /**
     * 设置输入过滤
     * @param editText EditText
     * @param inputFilters InputFilter数组
     */
    public static void setEditFilter(EditText editText, InputFilter... inputFilters) {
        if (null != editText && null != inputFilters) {
            editText.setFilters(inputFilters);
        }
    }

    public static void setOnLeftInputFocusChangeListener(EditText editText) {
        if (null != editText) {
            editText.setOnFocusChangeListener(leftFocusListener);
        }
    }

    private static View.OnFocusChangeListener leftFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            EditText editextView = (EditText)view;
            if (b) {
                editextView.setGravity(Gravity.LEFT);
                editextView.setSelection(editextView.getText().toString().trim().length());
            } else {
                editextView.setGravity(Gravity.RIGHT);
            }

        }
    };

    public static InputFilter CHINESE_FILTER = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
//                if (!isChinese(source.charAt(i))) {
//                    return "";
//                }
                if (!isContainChinese(source.charAt(i)+"")) {
                    return "";
                }
            }
            return null;
        }
    };

    public static InputFilter NAME_FILTER = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                Pattern p = Pattern.compile("[a-zA-Z\u4e00-\u9fa5.]| ");
                Matcher m = p.matcher(source.charAt(i)+"");
                boolean b =  m.find();
                if (!b) {
                    return "";
                }
            }
            return null;
        }
    };

    /**
     * 中国身份证过滤
     */
    public static InputFilter CHINESE_ID_FILTER = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                Pattern p = Pattern.compile("([0-9]|X|x)");
                Matcher m = p.matcher(source.charAt(i)+"");
                boolean b =  m.find();
                if (!b) {
                    return "";
                }
                if (source.charAt(i) == 'x')
                    return "X";
            }
            return null;
        }
    };

    /**
     * 新加坡身份证过滤
     */
    public static InputFilter SINGAPOR_ID_FILTER = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                Pattern p = Pattern.compile("[a-zA-Z0-9]");
                Matcher m = p.matcher(source.charAt(i)+"");
                boolean b =  m.find();
                if (!b) {
                    return "";
                }
            }
            return null;
        }
    };

    public static InputFilter CUBE_TYE_FILTER = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            return null;
        }
    };

    /**
     * 判定输入汉字
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符串内容是否包含下列非法字符<br>
     * `~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆
     * @param content 字符串内容
     * @return 't'代表不包含非法字符，otherwise代表包含非法字符。
     */
    public static char validateLegalString(String content) {
        String illegal = "`~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆";
        char isLegalChar = 't';
        L1: for (int i = 0; i < content.length(); i++) {
            for (int j = 0; j < illegal.length(); j++) {
                if (content.charAt(i) == illegal.charAt(j)) {
                    isLegalChar = content.charAt(i);
                    break L1;
                }
            }
        }
        return isLegalChar;
    }

    /**
     * 判断字符串是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 校验输入字符串是否合法
     * @param inputStr 输入字符串
     * @param regs 正则表达式
     * @return
     */
    public static boolean isValidInput(String inputStr, String... regs) {
        if (null==inputStr || "".equals(inputStr)) return false;
        for (String reg : regs) {
            Pattern p =  Pattern.compile(reg);
            Matcher m = p.matcher(inputStr);
            if (m.matches())
                return true;
        }

        return false;
    }

}
