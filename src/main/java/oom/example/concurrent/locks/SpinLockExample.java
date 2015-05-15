package oom.example.concurrent.locks;

import java.util.concurrent.atomic.AtomicReference;

/**
 * What's spin lock's cons and pros?
 * Created by yangshuan on 15/5/13.
 */
public class SpinLockExample {
    private class SpinLock {
        private AtomicReference<Thread> thread = new AtomicReference<>();

        public void lock() {
            while (!thread.compareAndSet(null, Thread.currentThread())) {
                System.out.println(Thread.currentThread().getName() + " is waiting to get the lock.");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " got the lock.");
        }

        public void unlock() {
            System.out.println(Thread.currentThread().getName() + " released the lock");
            thread.compareAndSet(Thread.currentThread(), null);
        }
    }
    public static void main(String[] args) {
        SpinLockExample example = new SpinLockExample();
        final SpinLock lock = example.new SpinLock();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " is running...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
    }
}
