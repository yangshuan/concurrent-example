package oom.example.concurrent.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Test for try lock of ReentrantLock
 * Created by yangshuan on 4/30/15.
 */
public class TryLockExample {
    private Lock lock = new ReentrantLock();

    private class LockTest implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "-- start to run...");
            try {
//            if (lock.tryLock()) {
                if (lock.tryLock(4l, TimeUnit.SECONDS)) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "-- Do something.");
                        Thread.sleep(3000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println(Thread.currentThread().getName() + "-- Unlock.");
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-- Done.");
        }
    }

    public static void main(String[] args) {
        TryLockExample test = new TryLockExample();
        Thread t1 = new Thread(test.new LockTest());
        Thread t2 = new Thread(test.new LockTest());

        t1.start();
        t2.start();
    }
}
