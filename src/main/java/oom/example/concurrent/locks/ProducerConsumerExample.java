package oom.example.concurrent.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition of ReentrantLock
 * Created by yangshuan on 4/30/15.
 */
public class ProducerConsumerExample {
    private class Queue {
        final Lock lock = new ReentrantLock();
        final Condition get = lock.newCondition();
        final Condition put = lock.newCondition();

        boolean hasValue = false;
        int value = 0;

        int get() {
            try {
                lock.lock();
                while (!hasValue) {
                    get.await();
                }
                System.out.println(Thread.currentThread().getName() + " get " + value);
                hasValue = false;
                put.signal();
                return value;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return 0;
        }

        void put(int num) {
            try {
                lock.lock();
                while (hasValue) {
                    put.await();
                }
                this.value = num;
                hasValue = true;
                System.out.println(Thread.currentThread().getName() + " put " + value);
                get.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private class Producer implements Runnable {
        private Queue q;

        Producer(Queue q) {
            this.q = q;
            new Thread(this, "Producer").start();
        }

        @Override
        public void run() {
            int value = 0;
            while (true) {
                q.put(value);
                value++;
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Consumer implements Runnable {
        private Queue q;

        Consumer(Queue q) {
            this.q = q;
            new Thread(this, "Consumer").start();
        }

        @Override
        public void run() {
            while (true) {
                q.get();
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumerExample test = new ProducerConsumerExample();
        Queue q = test.new Queue();

        test.new Consumer(q);
        test.new Producer(q);
    }
}
