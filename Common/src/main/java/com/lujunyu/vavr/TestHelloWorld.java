package com.lujunyu.vavr;

import io.vavr.*;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Test;

public class TestHelloWorld {

    /**
     * tuple 元组，通过它可以把多个属性看做一个整体，进行传递。不同于array或者list，它在传递过程中是不可变的。
     */
    @Test
    public void testTuple(){
        //最大长度是8
        Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple8 = Tuple.of(1, 2, 3, 4, 5, 6, 7, 8);
        System.out.println(tuple8.toString());

        Tuple3<Integer, Integer, Integer> tuple3 = Tuple.of(1, 2, 3);
        System.out.println(tuple3);
        //可以通过map转换成另一个Tuple.
        System.out.println(tuple3.map((t1,t2,t3)->Tuple.of(t1*2,t2*3,t3*4)));
        System.out.println(tuple3);

        //update 会返回一个新的Tuple3，原来的Tuple3不变。
        System.out.println(tuple3.update1(12));
        System.out.println(tuple3);

        // Transform a tuple
        int sum = tuple3.apply((t1,t2,t3)->(t1+t2+t3));
        System.out.println(sum);
    }

    /**
     * Function 普通函数
     * CheckFunction 可以抛异常的函数。
     *
     * compose的特性是将多个function组合成一个新的function。
     */
    @Test
    public void testFunctionCompose(){
        //composition
        Function1<Integer,Integer> plus = a->a+1;
        Function1<Integer,Integer> multiply = a -> a * 2;
        Function1<Integer, Integer> plusAndMultiply = plus.andThen(multiply);
        System.out.println(plusAndMultiply.apply(1));

        //另一种方式。
        Function1<Integer, Integer> plusAndMultiply1 = multiply.compose(plus);
        System.out.println(plusAndMultiply1.apply(2));
    }

    /**
     * lifting可以将一个函数的异常行为catch住，然后返回option的absent形式。
     */
    @Test
    public void testFunctionLifting(){
        Function2<Integer,Integer,Integer> divide = (t1, t2)->t1/t2;

        Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);

        System.out.println(safeDivide.apply(2,1));
        System.out.println(safeDivide.apply(1,0));
    }

    /**
     * 可以减少一个多值函数的输入。把一些参数设置为固定的值。
     */
    @Test
    public void testFunctionPartial(){
        Function5<Integer,Integer,Integer,Integer,Integer,Integer> multiply = (t1,t2,t3,t4,t5) -> t1*t2*t3*t4*t5;

        //此操作相当于把前四个参数固定为1,2,3,4的单参数函数。
        Function1<Integer, Integer> apply = multiply.apply(1, 2, 3, 4);

        System.out.println(apply.apply(5));

    }

    /**
     * 函数科里化。
     * 函数科里化操作是把多值参数的函数转变为只有一个参数的函数。
     */
    @Test
    public void testFunctionCurrying(){
        Function2<Integer,Integer,Integer> add2 = (a,b)->a+b;
        Function1<Integer, Function1<Integer, Integer>> curried = add2.curried();
        System.out.println(curried.apply(1).apply(1));

        Function4<Integer,Integer,Integer,Integer,Integer> add4 = (a,b,c,d)->a+b+c+d;
        Function1<Integer, Function1<Integer, Function1<Integer, Function1<Integer, Integer>>>> curried1 = add4.curried();
        System.out.println(curried1.apply(1).apply(2).apply(3).apply(4));
    }

    /**
     * memoized是把函数返回的结果记住，内部使用HashMap进行的缓存。
     */
    @Test
    public void testMemoized(){
        Function0<Double> random = Function0.of(Math::random).memoized();
        System.out.println(random.apply());
        System.out.println(random.apply());
    }


    @Test
    public void testOption(){
        Option<String> option = Option.of("fa");
//        此种操作会产生空指针异常。
//        Option<String> maybenull = option.map(s->(String)null).map(String::toUpperCase);
//        System.out.println(maybenull.get());

        Option<String> maybenull = option.flatMap(s->Option.of((String)null)).map(String::toUpperCase);
        System.out.println(maybenull.isEmpty());
    }

    /**
     * Try是对函数的异常进行一个柔性处理。可方便使用者处理异常。
     * Try一般把CheckFunction进行包装。
     */
    @Test
    public void testTry(){
        //实际recover方法用于捕获异常并进行降级处理，此时如果异常都被部落getOrElse的降级结果不起作用。
        String res = Try.of(this::dosmt).recover(this::dealException).getOrElse("方法降级");
        System.out.println(res);
        //如果只使用getOrElse的话，异常将被catch住，
        System.out.println(Try.of(this::dosmt).getOrElse("方法降级"));
    }

    private String dealException(Throwable t) {
        t.printStackTrace();
        return "异常";
    }

    public String dosmt() throws Exception{
        throw new Exception("error");
    }

    /**
     * 懒惰模式的包装。
     */
    @Test
    public void testLazy(){
        Lazy<Double> lazy = Lazy.of(Math::random);
        System.out.println(lazy.isEvaluated());
        System.out.println(lazy.get());
        System.out.println(lazy.isEvaluated());
        System.out.println(lazy.get());
    }

    @Test
    public void testEither(){
    }

    /**
     *
     */
    @Test
    public void testFuture(){
        Future<Double> future = Future.of(Math::random);
    }
}
