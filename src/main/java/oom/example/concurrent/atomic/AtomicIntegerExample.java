package oom.example.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is the example for AtomicBoolean
 * Created by yangshuan on 15/5/13.
 */
public class AtomicIntegerExample implements Runnable {
    private AtomicInteger value = new AtomicInteger(0);

    public void run() {
        for (int i = 0; i < 100; i++) {
            value.incrementAndGet();
        }
    }

    public long getValue() {
        return value.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerExample o = new AtomicIntegerExample();
        Thread t1 = new Thread(o);
        Thread t2 = new Thread(o);
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(o.getValue());
    }
}
