package com.harshalbenake.workmanager.worker;

import android.support.annotation.NonNull;

import androidx.work.Worker;


public class WorkerB extends Worker {

    @NonNull
    @Override
    public Result doWork() {
        printWorkerB();
        // Indicate success or failure with your return value:
        return Result.SUCCESS;

    }

    private void printWorkerB() {
        System.out.println("harshalbenake printWorkerB");
    }
}