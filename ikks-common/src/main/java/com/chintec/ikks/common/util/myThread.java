package com.chintec.ikks.common.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class myThread {
    private static AtomicInteger atomicInteger = new AtomicInteger(1);
    private static volatile boolean flag = true;

    static class MyThread1 implements Runnable {
        @Override
        public void run() {
            while (atomicInteger.get() < 100) {
                while (flag) {
                    System.out.println("My Thread1:"+atomicInteger.get());
                    atomicInteger.incrementAndGet();
                    flag = false;
                }
            }
        }
    }

    static class MyThread2 implements Runnable {
        @Override
        public void run() {
            while (atomicInteger.get() < 100) {
                while (!flag) {
                    System.out.println("My Thread2:"+atomicInteger.get());
                    atomicInteger.incrementAndGet();
                    flag = true;
                }
            }
        }
    }

    public static int lengthOfLongestSubstring(String str){
        Queue<Character> queue = new LinkedList<>();
        int res=0;
        for (Character c:str.toCharArray()){
            while (queue.contains(c)){
                queue.poll();
            }
            queue.add(c);
            res = Math.max(res,queue.size());
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("asdwqwdq"));
        /*MyThread1 myThread1=new MyThread1();
        new Thread(myThread1).start();
        MyThread2 myThread2=new MyThread2();
        new Thread(myThread2).start();*/
    }
}
