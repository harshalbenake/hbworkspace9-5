package com.harshalbenake.workmanager.worker;

import android.support.annotation.NonNull;

import androidx.work.Worker;

public class BasicWorker extends Worker{

    @NonNull
    @Override
    public Result doWork() {
        printBasicWork();
        // Indicate success or failure with your return value:
        return Result.SUCCESS;

    }

    private void printBasicWork() {
        System.out.println("harshalbenake printBasicWork");
    }
}