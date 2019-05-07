package com.cloud.redis.config;

public class TestThread implements Runnable {

    @Override
    public  void run() {
        System.out.println(Thread.currentThread().getName() + "线程     "+"start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "线程     "+"end");
    }

    public void method() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "线程     " + (i + 1) + "次");
        }
    }
}