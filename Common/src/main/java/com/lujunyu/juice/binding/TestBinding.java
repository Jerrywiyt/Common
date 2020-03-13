package com.lujunyu.juice.binding;

import com.google.inject.AbstractModule;
import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import com.lujunyu.juice.start.BillService;
import com.lujunyu.juice.start.TransactionLog;
import com.lujunyu.juice.start.TransactionLogImpl;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

/** 依赖绑定 */
public class TestBinding {

  /** 直接使用bind的方式，绑定一个接口的实现类，但是此种方式一个接口只能绑定一个实现类，不是特别的灵活。 */
  @Test
  public void testLinkedBingding() {
    BillService billService =
        Guice.createInjector(
                new AbstractModule() {
                  @Override
                  protected void configure() {
                    bind(TransactionLog.class).to(TransactionLogImpl.class);
                  }
                })
            .getInstance(BillService.class);
    System.out.println(billService);
  }

  /** 处理多态情况 使用@BindingAnnotation和@Names可以指定不同的实现类。 具体方法是在依赖上指定对应的注解。 */
  @Test
  public void testAnnotation() {
    System.out.println(
        Guice.createInjector(
                new AbstractModule() {
                  @Override
                  protected void configure() {
                    //
                    // bind(TransactionLog.class).annotatedWith(TranImpl.class).to(TransactionLogImpl.class);
                    bind(TransactionLog.class)
                        .annotatedWith(Names.named("TranImpl"))
                        .to(TransactionLogImpl.class);
                  }
                })
            .getInstance(BindingAnnotations.class));
  }

  /** 这个相当于常量绑定，实际没啥卵用。 */
  @Test
  public void testInstanceBindings() {
    System.out.println(
        Guice.createInjector(
                new AbstractModule() {
                  @Override
                  protected void configure() {
                    bind(String.class)
                        .annotatedWith(Names.named("JDBC URL"))
                        .toInstance("jdbc:mysql://localhost/pizza");
                    bind(Integer.class)
                        .annotatedWith(Names.named("login timeout seconds"))
                        .toInstance(10);
                  }
                })
            .getInstance(InstanceBindings.class));
  }

  /** 通过provider接口来提供依赖。 */
  @Test
  public void testProviderBindings() {
    BillService billService =
        Guice.createInjector(
                new AbstractModule() {
                  @Override
                  protected void configure() {
                    bind(TransactionLog.class).toProvider(ProviderBindings.class);
                  }
                })
            .getInstance(BillService.class);
    System.out.println(billService);
  }

  /** 采用@Provides method 的方式提供依赖。 */
  @Test
  public void testProviderMethod() {
    BillService billService =
        Guice.createInjector(
                new AbstractModule() {
                  @Override
                  protected void configure() {
                    super.configure();
                  }

                  @Provides
                  BillService provideBillService() {
                    return new BillService(null, null);
                  }
                })
            .getInstance(BillService.class);
  }

  /**
   * guice有两种方式实现单例，一种是下面这种，另一种通过Singleton修饰类。
   *
   * <p>默认情况下，使用的不是单例，而是每次都创建一个新的对象。
   */
  @Test
  public void testScope() {
    Injector injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                bind(TransactionLog.class).to(TransactionLogImpl.class).in(Singleton.class);
              }
            });
    System.out.println(injector.getInstance(BillService.class));
    System.out.println(injector.getInstance(BillService.class));
    System.out.println(injector.getInstance(BillService.class));
  }


    /**
     * 只能绑定Set，不能绑定List。
     */
  @Test
  public void testMultiBind() {
    Injector injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                Multibinder<Fruit<String>> binder =
                    Multibinder.newSetBinder(binder(), new TypeLiteral<Fruit<String>>() {});
                binder.addBinding().to(Apple.class);
              }

              @Provides
              public Store provideStore(Set<Fruit<String>> fruits) {
                return new Store(fruits);
              }
            });

    System.out.println(injector.getInstance(Store.class).getList());
  }
}
