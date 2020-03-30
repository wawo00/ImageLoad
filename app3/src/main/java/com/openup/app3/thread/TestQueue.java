package com.openup.app3.thread;

import com.openup.app3.reflect.Person;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.thread
 * @ClassName: TestQueue
 * @Description: TestQueue
 * @Author: Roy
 * @CreateDate: 2020/3/30 15:36
 */

public class TestQueue {
    private static BlockingQueue<Person> mBlockingQueue=new PriorityBlockingQueue<Person>();



    public static void main(String[] args) {

        for (int i = 0; i < 4; i++) {
            Person person=new Person("name"+i,12+i);
            mBlockingQueue.add(person);
        }
        System.out.println("mBlockingQueue .size "+mBlockingQueue.size()+mBlockingQueue.toString());

    }


}
