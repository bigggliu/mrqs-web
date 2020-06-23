package com.mediot.ygb.mrqs.analysis.medRecManage.thread.medCaseThread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class UploadThreadPool {

    private final ExecutorService executorService;

    private static volatile UploadThreadPool instance;

    private static ConcurrentHashMap<String,Thread> allThreads=new ConcurrentHashMap<>();

    public UploadThreadPool(int size,String facName,String poolType){
        UploadThreadFactory uploadThreadFactory=UploadThreadPool.newUploadThreadFactory(facName);
        //this.stats=uploadThreadFactory.stats;
        if(poolType.equals("fix")){
            this.executorService = Executors.newFixedThreadPool(size,uploadThreadFactory);
        }else {
            this.executorService = Executors.newCachedThreadPool(uploadThreadFactory);
        }
    }

    public static UploadThreadPool getInstance(int size,String facName,String poolType){
        if(instance==null){
            synchronized (UploadThreadPool.class){
                if(null==instance){
                    instance=new UploadThreadPool(size,facName,poolType);
                }
            }
        }
        return instance;
    }

    public static void releaseUploadThreadPool(){
        instance=null;
    }

    public ExecutorService getExecutorService(){
        return this.executorService;
    }

    public ConcurrentHashMap<String,Thread> getAllRunableThreads(){
        return this.allThreads;
    }

    public static UploadThreadFactory newUploadThreadFactory(String name) {
        return new UploadThreadFactory(name);
    }

    static class UploadThreadFactory implements ThreadFactory {

        private AtomicInteger atomicInteger=new AtomicInteger(0);
        private String name;

        public UploadThreadFactory(String name){
            this.name=name;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t=new Thread(r,name+"-Thread_"+atomicInteger.incrementAndGet());
            allThreads.put(t.getName(),t);
            //stats.add(String.format("created thread %d with name %s on %s\n",t.getId(),t.getName(),new Date()));
            System.out.println("线程"+t.getName()+"已经创建");
            return t;
        }
    }
}
