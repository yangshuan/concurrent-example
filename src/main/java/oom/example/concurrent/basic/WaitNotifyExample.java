package oom.example.concurrent.basic;

/**
 * Why we need synchronized? What will happen if we remove synchronized?
 * Created by yangshuan on 15/5/13.
 */
public class WaitNotifyExample {
    private class Queue {
        private Integer value;

        public synchronized boolean hasValue() {
            return value != null;
        }

        public synchronized void setValue(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    private class Producer implements Runnable {
        private final Queue q;

        Producer(Queue q) {
            this.q = q;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (q) {
                q.setValue(1);
                q.notify();
            }
        }
    }

    private class Consumer implements Runnable {
        private final Queue q;

        Consumer(Queue q) {
            this.q = q;
        }

        @Override
        public void run() {
            synchronized (q) {
                while (!q.hasValue()) {
                    try {
                        q.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " " + q.getValue());
            }
        }
    }

    public static void main(String[] args) {
        WaitNotifyExample example = new WaitNotifyExample();
        Queue q = example.new Queue();
        new Thread(example.new Producer(q), "Producer").start();
        new Thread(example.new Consumer(q), "Consumer").start();
    }
}
