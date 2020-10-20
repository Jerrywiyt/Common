package com.lujunyu.lombok;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

public class TestProvide {

  static Injector injector =
      Guice.createInjector(
          new AbstractModule() {
            @Override
            protected void configure() {
              super.configure();
            }

            @Named("test")
            @Singleton
            @Provides
            public Bean provideBean() {
              return new Bean("test");
            }
          });

  /** */
  public static void main(String[] args) {
    System.out.println(injector.getInstance(Model.class).bean);
    System.out.println(injector.getInstance(Model.class).fellow);
    System.out.println(injector.getInstance(Model.class).bean);
    System.out.println(injector.getInstance(Model.class));
  }

  @Singleton
  private static class Model {
    // @Named("test") 这种不能与 @AllConstruter
    @Inject
    @Named("test")
    private Bean bean;

    @Inject private Fellow fellow;
  }

  @Singleton
  private static class Fellow {}

  private static class Bean {
    private String name;

    public Bean(String name) {
      this.name = name;
    }
  }
}
