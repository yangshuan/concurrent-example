package oom.example.concurrent.basic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * What's the difference between CountDownLatch and CyclicBarrier?
 * Created by yangshuan on 15/5/14.
 */
public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch manager = new CountDownLatch(1);
        final CountDownLatch resources = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Resource " + Thread.currentThread().getName() + " has nothing to do.");
                    try {
                        manager.await();
                        System.out.println("Resource " + Thread.currentThread().getName() + " got a new task.");
                        Thread.sleep(3000);
                        System.out.println("Resource " + Thread.currentThread().getName() + " has finished his own task.");
                        resources.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Thread.sleep(1000);
        System.out.println("Manager " + Thread.currentThread().getName() + " starts a new project...");
        manager.countDown();
        resources.await();
        System.out.println("Manager " + Thread.currentThread().getName() + " announces that the project is finished successfully.");
        service.shutdown();
    }
}
