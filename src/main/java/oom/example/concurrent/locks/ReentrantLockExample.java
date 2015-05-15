package oom.example.concurrent.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * What will happen if just call the unlock method one time?
 * It's also a fair lock.
 * Created by yangshuan on 15/5/13.
 */
public class ReentrantLockExample {
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            private ReentrantLock lock = new ReentrantLock();
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " got the lock.");
                    Thread.sleep(1000);
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " got the lock again.");
                    Thread.sleep(1000);
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + " released the lock.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + " released the lock.");
                }
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
    }
}
