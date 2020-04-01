package com.lujunyu.juice.best;

public class Test {
  /** guice适合在都是单例的情况下，通过@Inject和@Single实现简单的依赖注入。 */
  public static void main(String args[]) {
    //加上@Singleton后，变成单例。
    System.out.println(InjectorInstance.injector.getInstance(Service.class));
    System.out.println(InjectorInstance.injector.getInstance(Service.class));
    //如果没有加任何注释或者provider，guice也可以获取该类的实例，只不过不是单例的，每次调用都会生成新对象。
    System.out.println(InjectorInstance.injector.getInstance(Apple.class));
    System.out.println(InjectorInstance.injector.getInstance(Apple.class));
    //如果依赖也没有被Guice明确的管理的话，也可以正常使用。
    System.out.println(InjectorInstance.injector.getInstance(Tree.class));
    System.out.println(InjectorInstance.injector.getInstance(Tree.class));
    //如果依赖的是一个接口的话，必须明确指定其实现。
    System.out.println(InjectorInstance.injector.getInstance(Tree1.class));
    System.out.println(InjectorInstance.injector.getInstance(Tree1.class));
    // 通过bind().toInstance()绑定的对象是单例的。
    System.out.println(InjectorInstance.injector.getInstance(Tree2.class));
    System.out.println(InjectorInstance.injector.getInstance(Tree2.class));
  }
}
