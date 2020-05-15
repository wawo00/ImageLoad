package com.openup.app2;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app2
 * @ClassName: MyExecutorPool
 * @Description: MyExecutorPool
 * @Author: Roy
 * @CreateDate: 2020/5/11 14:40
 */

public class MyExecutorPool {

    // 默认线程数
    private static int WORK_NUM = 5;

    //队列中默认任务数
    private static int Task_Num = 100;

    // 工作组
    private WorkThread[] mWorkThreads;

    // 任务队列
    private BlockingQueue<Runnable> taskQueue = null;

    // 用户设置启动的线程数
    private int worker_num;


    public MyExecutorPool() {
        this(WORK_NUM);
    }

    public MyExecutorPool(int worker_num) {
        if (worker_num <= 0) {
            worker_num = WORK_NUM;
        }
        this.worker_num = worker_num;
        taskQueue = new ArrayBlockingQueue<>(Task_Num);
        mWorkThreads = new WorkThread[worker_num];
        for (int i = 0; i < worker_num; i++) {
            mWorkThreads[i] = new WorkThread();
            mWorkThreads[i].start();
        }
    }

    // 执行任务,其实只是把任务加入任务队列，什么时候执行有线程池管理器决定
    public void execute(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void destroy() {
        System.out.println("ready close pool ...");
        for (WorkThread thread : mWorkThreads) {
            thread.stopWorker();
            thread = null;
        }
        taskQueue.clear();
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            Runnable runnable = null;
            try {
                while (!isInterrupted()) {
                    runnable = taskQueue.take();
                    if (runnable != null) {
                        System.out.println(getId() + " ready exec " + runnable);
                        runnable.run();
                    }
                    runnable = null;
                }
            } catch (InterruptedException e) {
                System.out.println(" has interException in "+getId() +" msg "+e.getMessage());
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        public void stopWorker() {
            interrupt();
        }
    }

    @Override
    public String toString() {
        return "WorkThread number:" + worker_num
                + "  wait task number:" + taskQueue.size();
    }


    public static void main(String[] args) throws InterruptedException {

        // 创建3个线程的线程池
        MyExecutorPool t = new MyExecutorPool(3);
        t.execute(new MyTask("testA"));
        t.execute(new MyTask("testB"));
        t.execute(new MyTask("testC"));
        t.execute(new MyTask("testD"));
        t.execute(new MyTask("testE"));
        System.out.println(t.toString());
        Thread.sleep(10000);
        t.destroy();// 所有线程都执行完成才destory
        System.out.println(t);
    }

    static class MyTask implements Runnable {

        private String name;

        MyTask(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Random r = new Random();
                Thread.sleep(r.nextInt(1000)+2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getId()+" sleep InterruptedException:"
                        +Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }
            System.out.println("任务 " + name + " 完成");

        }
    }
}
