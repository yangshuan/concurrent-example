package oom.example.concurrent.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * It's a good way to write divide and conquer algorithm.
 * Does it look like a map/reduce job?
 * Created by yangshuan on 15/5/13.
 */
public class ForkJoinExample extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 2;

    private final int start;
    private final int end;

    public ForkJoinExample(int start, int end) {
        if (start > end) {
            throw new RuntimeException("Start should larger than end.");
        }
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                result += i;
            }
        } else {
            int middle = (start + end) / 2;
            ForkJoinExample left = new ForkJoinExample(start, middle);
            ForkJoinExample right = new ForkJoinExample(middle + 1, end);

            left.fork();
            right.fork();

            result = left.join() + right.join();
        }
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinExample example = new ForkJoinExample(1, 10);

        ForkJoinPool pool = new ForkJoinPool();
        Future<Integer> future = pool.submit(example);
        System.out.println(future.get());

        pool.shutdown();
    }
}
