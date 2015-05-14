package oom.example.concurrent.atomic;

/**
 * This is the example for volatile
 * Memory visibility?
 * Atomicity?
 * Created by yangshuan on 15/5/13.
 */
public class VolatiteExample {
    private volatile int value = 0;

    public void increase() {
        value++;
    }

    public int getValue() {
        return value;
    }

    public static void main(String[] args) {
        final VolatiteExample example = new VolatiteExample();
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        example.increase();
                    }
                }
            }.start();
        }
        while (Thread.activeCount() > 2) {
            System.out.println(Thread.activeCount());
            Thread.yield();
        }
        // rerun several times, sometimes the result is wrong
        System.out.println(example.getValue());
    }
}
