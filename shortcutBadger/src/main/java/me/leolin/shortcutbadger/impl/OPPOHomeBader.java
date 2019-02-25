package me.leolin.shortcutbadger.impl;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import java.util.Collections;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.util.BroadcastHelper;

/**
 * Create By DaYin(gaoyin_vip@126.com) on 2018/10/20 上午11:31
 */
public class OPPOHomeBader implements Badger {

    private static final String PROVIDER_CONTENT_URI = "content://com.android.badge/badge";
    private static final String INTENT_ACTION = "com.oppo.unsettledevent";
    private static final String INTENT_EXTRA_PACKAGENAME = "pakeageName";
    private static final String INTENT_EXTRA_BADGE_COUNT = "number";
    private static final String INTENT_EXTRA_BADGE_UPGRADENUMBER = "upgradeNumber";
    private static final String INTENT_EXTRA_BADGEUPGRADE_COUNT = "app_badge_count";
    private int mCurrentTotalCount = -1;

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        if (mCurrentTotalCount == badgeCount) {
            return;
        }
        mCurrentTotalCount = badgeCount;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            executeBadgeByContentProvider(context, componentName, badgeCount);
        } else {
            executeBadgeByBroadcast(context, componentName, badgeCount);
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Collections.singletonList("com.oppo.launcher");
    }

    private void executeBadgeByBroadcast(Context context, ComponentName componentName,
                                         int badgeCount) throws ShortcutBadgeException {
        if (badgeCount == 0) {
            badgeCount = -1;
        }
        Intent intent = new Intent(INTENT_ACTION);
        intent.putExtra(INTENT_EXTRA_PACKAGENAME, componentName.getPackageName());
        intent.putExtra(INTENT_EXTRA_BADGE_COUNT, badgeCount);
        intent.putExtra(INTENT_EXTRA_BADGE_UPGRADENUMBER, badgeCount);

        BroadcastHelper.sendIntentExplicitly(context, intent);
    }

    /**
     * Send request to OPPO badge content provider to set badge in OPPO home launcher.
     *
     * @param context    the context to use
     * @param badgeCount the badge count
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void executeBadgeByContentProvider(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        try {
            Bundle extras = new Bundle();
            extras.putInt(INTENT_EXTRA_BADGEUPGRADE_COUNT, badgeCount);
            context.getContentResolver().call(Uri.parse(PROVIDER_CONTENT_URI), "setAppBadgeCount", null, extras);
        } catch (Throwable ignored) {

            executeBadgeByBroadcast(context, componentName, badgeCount);
        }
    }
}