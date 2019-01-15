package com.lujunyu.juice.best;

public class Test {
    /**
     * guice适合在都是单例的情况下，通过@Inject和@Single实现简单的依赖注入。
     */
    public static void main(String args[]){
        System.out.println(InjectorInstance.injector.getInstance(Service.class));
        System.out.println(InjectorInstance.injector.getInstance(Service.class));
        System.out.println(InjectorInstance.injector.getInstance(Service.class));
    }
}
