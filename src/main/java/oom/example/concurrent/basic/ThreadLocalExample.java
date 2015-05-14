package oom.example.concurrent.basic;

/**
 * Each thread will new a value.
 * It's not for shared object problem.
 * Created by yangshuan on 15/5/13.
 */
public class ThreadLocalExample {
    private ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
        @Override
        public Integer initialValue() {
            return 0;
        }
    };

    private Integer increase() {
        value.set(value.get() + 1);
        return value.get();
    }

    private class ThreadLocalTask implements Runnable {
        private ThreadLocalExample example;

        public ThreadLocalTask(ThreadLocalExample example) {
            this.example = example;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " " + example.increase());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalExample example = new ThreadLocalExample();
        ThreadLocalTask task = example.new ThreadLocalTask(example);

        Thread t1 = new Thread(task, "Thread1");
        Thread t2 = new Thread(task, "Thread2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
