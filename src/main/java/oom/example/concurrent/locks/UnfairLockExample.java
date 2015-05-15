package oom.example.concurrent.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * What's the difference between fair lock and unfair lock?
 * Created by yangshuan on 15/5/13.
 */
public class UnfairLockExample {
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            private ReentrantLock lock = new ReentrantLock(false);
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " got the lock.");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + " released the lock.");
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(task).start();
        }
    }
}
