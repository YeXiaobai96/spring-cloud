package com.cloud.redis.config;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLockThread implements Runnable {
    private Lock lock = new ReentrantLock();

    public void testmethod() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " :" + (i + 1) + "s");
        }
    }

    /*常规的lock获取锁和释放锁,lock.lock()和lock.unlock();方法注释掉了，可以放出来运行下看结果*/
 /*@Override
 public void run() {
  lock.lock();
  try {
   System.out.println(Thread.currentThread().getName()+" :start");
   testmethod();
  } catch (Exception e) {   
  }finally{
   System.out.println(Thread.currentThread().getName()+" :end");
   lock.unlock();
  }  
 }*/
 
 /*lock.tryLock()没有获取到锁的线程，不会再尝试获取锁，所以它就不走了。
 注释掉了，可以放出来运行下看结果*/
/* @Override
 public void run() {
  try {
   if(lock.tryLock()){
    try {
     System.out.println(Thread.currentThread().getName()+" :start");
     testmethod();
    } catch (Exception e) {    
    }finally{
     System.out.println(Thread.currentThread().getName()+" :end");
     lock.unlock();
    }
   }else{
    System.out.println(Thread.currentThread().getName()+" :fail to get the lock");
   }
  } catch (InterruptedException e) {
   e.printStackTrace();
  }
 }*/

    /**
     * 使用tryLock(time，timeUnit),规定时间内如果没有获取到锁，则不再等待。就是这个等待时间必须大于单线程的运行时间才可以，考虑到状态刷新，
     * 至少大于1s，你测试的时候多写点。
     */
    @Override
    public void run() {
        try {
            if (lock.tryLock(15, TimeUnit.SECONDS)) {
                try {
                    System.out.println(Thread.currentThread().getName() + " :start");
                    testmethod();
                } catch (Exception e) {
                } finally {
                    System.out.println(Thread.currentThread().getName() + " :end");
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " :fail to get the lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
   /* @Override
    public void run() {
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e1) {
            System.out.println(Thread.currentThread().getName() + " :is stopped waitting!");
        }
        try {
            System.out.println(Thread.currentThread().getName() + " :start");
            testmethod();
        } catch (Exception e) {

        } finally {
            System.out.println(Thread.currentThread().getName() + " :end");
            lock.unlock();
        }
    }*/
}