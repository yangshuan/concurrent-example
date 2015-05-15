package oom.example.concurrent.cow;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Copy on write is very popular in database implementation.
 * Created by yangshuan on 15/5/13.
 */
public class CopyOnWriteExample {
    private CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

    public List<Integer> getList() {
        return list;
    }

    public void add(Integer value) {
        this.list.add(value);
    }

    public static void main(String[] args) {
        final CopyOnWriteExample example = new CopyOnWriteExample();
        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " " + example.getList().toString());
            }
        };
        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                example.add(1);
                example.add(2);
                example.add(3);
                example.add(4);
                example.add(5);
            }
        };

        new Thread(readTask, "read1").start();
        new Thread(readTask, "read2").start();
        new Thread(writeTask, "write1").start();
        new Thread(readTask, "read3").start();
    }
}
