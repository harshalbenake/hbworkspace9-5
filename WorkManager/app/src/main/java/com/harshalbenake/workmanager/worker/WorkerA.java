package com.harshalbenake.workmanager.worker;

import android.support.annotation.NonNull;

import androidx.work.Worker;


public class WorkerA extends Worker {

    @NonNull
    @Override
    public Result doWork() {
        printWorkerA();
        // Indicate success or failure with your return value:
        return Result.SUCCESS;

    }

    private void printWorkerA() {
        System.out.println("harshalbenake printWorkerA");
    }
}