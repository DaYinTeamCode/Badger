package me.leolin.shortcutbadger.util;

import android.database.Cursor;

import java.io.Closeable;
import java.io.IOException;

/**
 * Create By DaYin(gaoyin_vip@126.com) on 2018/10/20 上午11:31
 */
public class CloseHelper {

    public static void close(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }


    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException var2) {

        }
    }
}
