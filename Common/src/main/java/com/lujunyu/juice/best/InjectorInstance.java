package com.lujunyu.juice.best;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class InjectorInstance {
    public static final Injector injector = Guice.createInjector(new Module() {
        @Override
        public void configure(Binder binder) {

        }
    });
}
