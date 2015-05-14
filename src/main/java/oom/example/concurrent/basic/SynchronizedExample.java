package oom.example.concurrent.basic;

/**
 * Synchronized is reentrant lock.
 * Created by yangshuan on 15/5/13.
 */
public class SynchronizedExample implements Runnable {
    private int value = 0;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " begins to run...");
        synchronized (this) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            value++;
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + " gets the lock second time.");
            }
            System.out.println(Thread.currentThread().getName() + " value has been increased.");
        }
    }

    public int getValue() {
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedExample task = new SynchronizedExample();
        Thread t1 = new Thread(task, "Thread1");
        Thread t2 = new Thread(task, "Thread2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(task.getValue());
    }
}
