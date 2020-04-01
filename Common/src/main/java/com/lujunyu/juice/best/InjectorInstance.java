package com.lujunyu.juice.best;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class InjectorInstance {
  public static final Injector injector =
      Guice.createInjector(
          new AbstractModule() {
            @Override
            protected void configure() {
              bind(Tree2.class).toInstance(new Tree2());
            }
          });
}
