package oom.example.concurrent.basic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * What's the difference between CountDownLatch and CyclicBarrier?
 * Created by yangshuan on 15/5/14.
 */
public class CyclicBarrierExample implements Runnable {
    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierExample(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println("Begin to develop module " + Thread.currentThread().getName());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Module " + Thread.currentThread().getName() + " is finished.");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        System.out.println("We are going to have a new release window next week...");
        final CyclicBarrier barrier = new CyclicBarrier(10, new Runnable() {
            @Override
            public void run() {
                System.out.println("All modules are finished, release can be started now.");
            }
        });

        for (int i = 0; i < 10; i++) {
            service.submit(new CyclicBarrierExample(barrier));
        }
        service.shutdown();
    }
}
