package com.dinpay.demo.base.dagger2;





import com.dinpay.demo.base.mvp.BaseFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by Lenovo on 2018/4/8.
 */
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseFragmentComponent extends AndroidInjector<BaseFragment> {
    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<BaseFragment>{

    }
}
