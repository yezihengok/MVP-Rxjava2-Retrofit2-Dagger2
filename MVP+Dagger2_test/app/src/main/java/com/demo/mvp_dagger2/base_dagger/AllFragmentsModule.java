package com.demo.mvp_dagger2.base_dagger; /**
 * AllActivitysModule  2018-04-03
 * Copyright (c) 2018 JS Co.Ltd. All right reserved.
 */


import com.demo.mvp_dagger2.fragment.Fragment01;
import com.demo.mvp_dagger2.fragment.Fragment01ProModule;
import com.demo.mvp_dagger2.fragment.Fragment02;
import com.demo.mvp_dagger2.fragment.Fragment02ProModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * class description here
 * @author Jackson
 * @version 1.0.0
 * since 2018 04 03
 */
@Module(subcomponents = BaseFragmentComponent.class)
public abstract class AllFragmentsModule {

    @ContributesAndroidInjector(modules = Fragment01ProModule.class)
    abstract Fragment01 contributeFragment01Injector();

    @ContributesAndroidInjector(modules = Fragment02ProModule.class)
    abstract Fragment02 contributeFragment02Injector();

}

