package com.jzyd.redpoint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShortcutBadger.applyCount(this, 5);
    }
}
