package com.joojn.LucidClient.Utils;

import com.joojn.LucidClient.Events.EventTarget;
import com.joojn.LucidClient.Events.impl.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;


public class TaskUtil {
    private static HashMap<Runnable, Long> taskLaterHash = new HashMap<>();
    private static HashMap <Runnable, Long> taskLaterAsyncHash = new HashMap<>();
    private static HashMap <Runnable, Long> loopForTimeHash = new HashMap<>();

    public static void runTaskLater(Runnable runnable, long time){
        taskLaterHash.putIfAbsent(runnable, time);
    }

    public static void runTaskLaterAsync(Runnable runnable, long time){
        taskLaterAsyncHash.putIfAbsent(runnable, time);
    }

    public static void runEveryTickFor(Runnable runnable, long time){
        loopForTimeHash.putIfAbsent(runnable, time);
    }

    @EventTarget
    public void onTick(TickEvent event){
        ArrayList <Runnable> taskLaterAsyncHashRemove = new ArrayList<>();
        ArrayList <Runnable> taskLaterHashRemove = new ArrayList<>();
        ArrayList <Runnable> loopForTimeHashRemove = new ArrayList<>();

        // async hashes
        for(Runnable runnable : taskLaterAsyncHash.keySet()){
            long time = taskLaterAsyncHash.get(runnable) - 1;
            taskLaterAsyncHash.replace(runnable, time);

            if(time < 1){
                taskLaterAsyncHashRemove.add(runnable);
                new Thread(runnable).start();
            }
        }

        // loops
        for(Runnable runnable : loopForTimeHash.keySet()){
            long time = loopForTimeHash.get(runnable) - 1;
            loopForTimeHash.replace(runnable, time);
            runnable.run();

            if(time < 1){
                loopForTimeHashRemove.add(runnable);
            }
        }

        // sync hashes
        for(Runnable runnable : taskLaterHash.keySet()){
            long time = taskLaterHash.get(runnable) - 1;
            taskLaterHash.replace(runnable, time);

            if(time < 1){
                taskLaterHashRemove.add(runnable);
                runnable.run();
            }
        }

        taskLaterAsyncHashRemove.forEach(taskLaterAsyncHash.keySet()::remove);
        taskLaterHashRemove.forEach(taskLaterHash.keySet()::remove);
        loopForTimeHashRemove.forEach(loopForTimeHash.keySet()::remove);
    }
}
