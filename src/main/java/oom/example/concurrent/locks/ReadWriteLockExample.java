package oom.example.concurrent.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * There are some other kinds of read write locks.
 * Read write lock is a very good way to understand database transaction.
 * Created by yangshuan on 15/5/13.
 */
public class ReadWriteLockExample {
    private class ReadWriteTask implements Runnable {
        private Data data;
        private boolean read;

        ReadWriteTask(Data data, boolean read) {
            this.data = data;
            this.read = read;
        }

        @Override
        public void run() {
            if (read) {
                data.get();
            } else {
                data.set();
            }
        }
    }

    private class Data {
        private ReadWriteLock lock = new ReentrantReadWriteLock();
        private Lock readLock = lock.readLock();
        private Lock writeLock = lock.writeLock();

        void set() {
            try {
                writeLock.lock();
                System.out.println(Thread.currentThread().getName() + " begin to set " + System.currentTimeMillis());
            } finally {
                System.out.println(Thread.currentThread().getName() + " end to set " + System.currentTimeMillis());
                writeLock.unlock();
            }
        }

        void get() {
            try {
                readLock.lock();
                System.out.println(Thread.currentThread().getName() + " begin to get " + System.currentTimeMillis());
            } finally {
                System.out.println(Thread.currentThread().getName() + " end to get " + System.currentTimeMillis());
                readLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReadWriteLockExample example = new ReadWriteLockExample();
        Data data = example.new Data();
        new Thread(example.new ReadWriteTask(data, true), "read thread1").start();
        new Thread(example.new ReadWriteTask(data, true), "read thread2").start();

        new Thread(example.new ReadWriteTask(data, false), "write thread1").start();
        new Thread(example.new ReadWriteTask(data, true), "read thread3").start();
        new Thread(example.new ReadWriteTask(data, false), "write thread2").start();
    }
}
