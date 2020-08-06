package com.gwj.electricity;

import android.app.Application;

import com.gwj.latte.core.app.Latte;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * @Author: GWJ
 * @Date: 2020/7/28
 * @Explain:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withApiHost("https://gank.io/api/v2/")
                .configure();

    }
}