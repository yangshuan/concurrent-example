package oom.example.concurrent.basic;

import java.util.concurrent.Semaphore;

/**
 * Group of threads access limited resource
 * Created by yangshuan on 15/5/13.
 */
public class SemaphoreExample {
    public static void main(String[] args) {
        SemaphoreExample example = new SemaphoreExample();

        int meetings = 10;
        int meetingRooms = 5;
        Semaphore semaphore = new Semaphore(meetingRooms);

        for (int i = 0; i < meetings; i++) {
            new Thread(example.new Meeting(semaphore), "meeting" + i).start();
        }
    }

    private class Meeting implements Runnable {
        private Semaphore semaphore;

        public Meeting(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                String meetingName = Thread.currentThread().getName();
                semaphore.acquire();
                System.out.println(meetingName + " is started...");
                Thread.sleep(2000);
                System.out.println(meetingName + " is end.");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
