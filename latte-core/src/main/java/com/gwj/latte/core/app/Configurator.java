package com.gwj.latte.core.app;

import android.util.Log;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author: GWJ
 * @Date: 2020/7/28
 * @Explain: 获取 开始配置
 */
public class Configurator {

    private static final HashMap<String, Object> LATTE_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    public static  HashMap<String, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    public Configurator() {
        //开始配置但是没有完成
        LATTE_CONFIGS.put(ConfigType.COINFIG_READY.name(), false);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        //配置完成
        LATTE_CONFIGS.put(ConfigType.COINFIG_READY.name(), true);
        initIncos();
    }

    public final Configurator withApiHost(String host) {
        //配置ApiHost
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }

    //字体图标
    private void initIncos() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer iconifyInitializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                iconifyInitializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    private void checkConfigurtion() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.COINFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configuration is not read,call configure");
        }
    }

    final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfigurtion();
        return (T) LATTE_CONFIGS.get(key);
    }


}
