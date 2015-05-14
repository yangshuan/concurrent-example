package oom.example.concurrent.callback;

import java.util.concurrent.*;

/**
 * Can we write code as callable programming in JavaScript?
 * Created by yangshuan on 15/5/13.
 */
public class CallbackExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallbackExample call = new CallbackExample();
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return 100;
            }
        });
        System.out.println(future.get());
        service.shutdown();
    }
}
