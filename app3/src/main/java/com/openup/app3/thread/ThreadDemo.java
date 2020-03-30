package com.openup.app3.thread;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.thread
 * @ClassName: ThreadDemo
 * @Description: ThreadDemo
 * @Author: Roy
 * @CreateDate: 2020/3/24 18:02
 */

public class ThreadDemo {

    class MyRuannabel implements Runnable {
        int ticket = 5;

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                if (ticket > 0) {
                    System.out.println("ticket is " + ticket);
                    ticket--;
                }
            }
        }
    }

    public MyRuannabel getRunale() {
        return new MyRuannabel();
    }


    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        MyRuannabel myRuannabel = td.getRunale();
        new Thread(myRuannabel).start();
        new Thread(myRuannabel).start();
        new Thread(myRuannabel).start();
    }
}
