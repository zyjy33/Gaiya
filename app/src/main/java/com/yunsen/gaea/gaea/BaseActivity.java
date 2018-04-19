package com.yunsen.gaea.gaea;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by yunsenA on 2018/4/18.
 */

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        initData();
        initListener();
    }

    public abstract int getLayout();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();


}
