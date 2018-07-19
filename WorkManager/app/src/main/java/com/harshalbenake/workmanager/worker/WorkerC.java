package com.harshalbenake.workmanager.worker;

import android.support.annotation.NonNull;

import androidx.work.Worker;


public class WorkerC extends Worker {

    @NonNull
    @Override
    public Result doWork() {
        printWorkerC();
        // Indicate success or failure with your return value:
        return Result.SUCCESS;

    }

    private void printWorkerC() {
        System.out.println("harshalbenake printWorkerC");
    }
}