package com.demo.mvp_dagger2.base_dagger;



import com.demo.mvp.base.BaseFragment;

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
