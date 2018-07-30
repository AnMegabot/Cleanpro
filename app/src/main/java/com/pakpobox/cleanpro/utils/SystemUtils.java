package com.pakpobox.cleanpro.utils;

import android.content.Context;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * User:Sean.Wei
 * Date:2018/4/2
 * Time:17:37
 */

public class SystemUtils {
    /**
     * 四舍五入保留两位小数
     * @param d
     * @return
     */
    public static double formatDouble(double d) {
        return formatDouble(d, 2);
    }

    public static double formatDouble(double d, int scale) {
        return new BigDecimal(d).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 格式化浮点型数值为带两位小数的数值并转为字符串
     * @param f 浮点数值
     * @return String
     */
    public static String formatFloat2Str(double f) {
        DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//        if ((f*100)%100 == 0)
//            decimalFormat=new DecimalFormat("#");
//        else if ((f*100)%10 == 0)
//            decimalFormat=new DecimalFormat("0.0");

        return decimalFormat.format(f);//format 返回的是字符串
    }

    /**
     * dip转px
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
