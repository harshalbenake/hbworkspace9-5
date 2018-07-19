package com.harshalbenake.workmanager.worker;

import android.support.annotation.NonNull;

import androidx.work.Worker;

public class PeriodicWorker extends Worker{
    @NonNull
    @Override
    public Result doWork() {
        printPeriodicWork();
        // Indicate success or failure with your return value:
        return Result.SUCCESS;

    }

    private void printPeriodicWork() {
        System.out.println("harshalbenake printPeriodicWork");
    }
}
