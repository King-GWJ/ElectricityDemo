package com.gwj.electricity;

import com.gwj.latte.core.activities.ProxyActvity;
import com.gwj.latte.core.delegates.LatteDelegate;

public class ExampleActivity extends ProxyActvity {
    @Override
    public LatteDelegate setRootDelegare() {
        return new ExampleDelegate();
    }
}