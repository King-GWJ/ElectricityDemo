package com.gwj.latte.core.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.HashMap;

/**
 * @Author: GWJ
 * @Date: 2020/7/28
 * @Explain:
 */
public final class Latte {
    public static final Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<String, Object> getConfigurations() {
        return Configurator.getLatteConfigs();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Application getApplicationContext() {
        return getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }
}
