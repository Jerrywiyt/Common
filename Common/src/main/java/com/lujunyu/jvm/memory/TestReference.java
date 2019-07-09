package com.lujunyu.jvm.memory;

import org.junit.Test;
import sun.nio.ch.DirectBuffer;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

public class TestReference {
    @Test
    public void testSoft() {
        Byte[] bytes = new Byte[1024];
        SoftReference<Byte[]> softReference = new SoftReference<>(bytes);
        softReference.get();
    }

    /**
     * 通过此方法，我们可以明确了引用队列中所持有的的对象只能为引用对象。
     * 引用对象被加入引入队列的时机是持有对象被垃圾回收掉
     * 引用对象加入到引用队列后，引用对象所持有的对象已经被回收，此时调用get方法只能返回null。
     */
    @Test
    public void testWeakReference() {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        Object object = new Object();
        MyWeakReference objectMyWeakReference = new MyWeakReference<Object>(object, referenceQueue);
        object = null;
        System.gc();
        while (true) {
            MyWeakReference<Object> myWeakReference = (MyWeakReference<Object>) referenceQueue.poll();
            if (myWeakReference != null) {
                System.out.println(objectMyWeakReference);
                System.out.println(objectMyWeakReference.hashCode());
                System.out.println(myWeakReference.get());
                System.out.println(myWeakReference.hashCode());
                break;
            }
        }
    }

    private static class MyWeakReference<T> extends WeakReference<T> {
        MyWeakReference(T referent, ReferenceQueue<? super T> q) {
            super(referent, q);
        }
    }


    /**
     * 虚引用不影响对象的生命周期。
     * 其通过ReferenceQueue进行通知对象被回收的事件。
     */
    @Test
    public void testPhantomReference() {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        Object object = new Object();
        PhantomReference<Object> p = new PhantomReference<>(object, referenceQueue);
        object = null;
        System.gc();
        while (true) {
            Object o = referenceQueue.poll();
            if (o != null) {
                PhantomReference<Object> phantomReference = (PhantomReference<Object>) o;
                System.out.println(phantomReference.get());
                System.out.println(o.getClass().getName());
                System.out.println(o.hashCode());
            }
        }
    }
    
    @Test
    public void testDirectBuffer(){
        ByteBuffer.allocateDirect(1024);
    }
}
