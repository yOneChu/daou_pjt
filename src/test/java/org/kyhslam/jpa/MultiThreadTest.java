package org.kyhslam.jpa;

public class MultiThreadTest {

    public static void main(String[] args) {

        comThread t1 = new comThread();
        subThread t2 = new subThread();

        t1.start();
        t2.start();

    }



}


class comThread extends Thread {

    @Override
    public void run() {

        for(int i=0; i < 1000; i++){
            System.out.println(i);
        }

    }
}

class subThread extends Thread {
    @Override
    public void run() {
        for(int i=1000; i < 2000; i++){
            System.out.println(i);
        }

    }
}

