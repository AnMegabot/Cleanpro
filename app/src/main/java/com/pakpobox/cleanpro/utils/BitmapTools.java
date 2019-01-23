package com.pakpobox.cleanpro.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * User:Sean.Wei
 * Date:2018/3/21
 * Time:16:17
 */

public class BitmapTools {
    /**
     * 从Uri获取byte数组
     * @param context 上下文
     * @param uri Uri
     * @return byte[]
     */
    public static byte[] getBytesByUri(Context context, Uri uri){
        ByteArrayOutputStream mByteBuffer = new ByteArrayOutputStream();
        Bitmap bm = null;
        try {
            bm = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri),
                    null, new BitmapFactory.Options());
            bm.compress(Bitmap.CompressFormat.JPEG, 100, mByteBuffer);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while ( mByteBuffer.toByteArray().length / 1024 > 0.5*1024) {  //循环判断如果压缩后图片是否大于2M,大于继续压缩
                mByteBuffer.reset();//重置baos即清空baos
                bm.compress(Bitmap.CompressFormat.JPEG, options, mByteBuffer);//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;//每次都减少10
            }
            byte[] data = mByteBuffer.toByteArray();
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
                bm = null;
            }
            if (null != mByteBuffer) {
                try {
                    mByteBuffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //图片压缩
    private static byte[] compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while ( baos.toByteArray().length / 1024 > 0.5*1024) {  //循环判断如果压缩后图片是否大于2M,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        return baos.toByteArray();
    }
}
