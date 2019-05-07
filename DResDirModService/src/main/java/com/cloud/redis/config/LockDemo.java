package com.cloud.redis.config;

public class LockDemo {
    public static void main(String[] args) {
        TestLockThread tthread = new TestLockThread();
        Thread tt = new Thread(tthread, "thread1");
        tt.start();
        Thread ttt = new Thread(tthread, "thread2");
        ttt.start();
        //ttt.interrupt();
    }
}