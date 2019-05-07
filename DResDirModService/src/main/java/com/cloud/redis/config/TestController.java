package com.cloud.redis.config;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Filename: TestController
 * @Author: wm
 * @Date: 2019/4/22 15:00
 * @Description:test
 * @History:
 */
public class TestController {
    public static void main(String[] args) {
       /* System.out.println(new Date());
        Object a=new Object();
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        try {
            a.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }*/
        TestThread thred1 = new TestThread();
        Thread t = new Thread(thred1, "thread1");

        //Thread t2=new Thread(thred1,"thread2");
        t.start();
        try {
            t.wait(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
        // t2.start();


    }


}
