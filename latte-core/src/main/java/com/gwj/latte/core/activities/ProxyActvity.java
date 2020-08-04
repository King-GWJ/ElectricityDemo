package com.gwj.latte.core.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.ContentFrameLayout;

import com.gwj.latte.core.R;
import com.gwj.latte.core.delegates.LatteDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * @Author: GWJ
 * @Date: 2020/7/29
 * @Explain:
 */
public abstract class ProxyActvity extends SupportActivity {

    public abstract LatteDelegate setRootDelegare();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    @SuppressLint({"RestrictedApi", "ResourceType"})
    private void initContainer(Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegates_container);
        setContentView(container);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegates_container, setRootDelegare());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
