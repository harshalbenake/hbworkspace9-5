package com.harshalbenake.workmanager;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.harshalbenake.workmanager.worker.BasicWorker;
import com.harshalbenake.workmanager.worker.PeriodicWorker;
import com.harshalbenake.workmanager.worker.WorkerA;
import com.harshalbenake.workmanager.worker.WorkerB;
import com.harshalbenake.workmanager.worker.WorkerC;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

public class MainActivity extends AppCompatActivity {
    public class CustomLifecycleObserver implements LifecycleObserver {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create a Constraints that defines when the task should run
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                // Many other constraints are available, see the
                // Constraints.Builder reference
                .build();
        // ...then create a OneTimeWorkRequest that uses those constraints
        OneTimeWorkRequest basicOneTimeWorkRequest = new OneTimeWorkRequest.Builder(BasicWorker.class)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance().enqueue(basicOneTimeWorkRequest);
      /*  UUID compressionWorkId = basicOneTimeWorkRequest.getId();
        WorkManager.getInstance().cancelWorkById(compressionWorkId);*/


        PeriodicWorkRequest.Builder periodicWorkRequestBuilder = new PeriodicWorkRequest.Builder(PeriodicWorker.class, 10, TimeUnit.SECONDS);
        periodicWorkRequestBuilder.keepResultsForAtLeast(1,TimeUnit.MINUTES);
        // ...if you want, you can apply constraints to the builder here...
        // Create the actual work object:
        PeriodicWorkRequest periodicWorkRequest = periodicWorkRequestBuilder.build();
        // Then enqueue the recurring task:
        WorkManager.getInstance().enqueue(periodicWorkRequest);

        OneTimeWorkRequest workerARequest = new OneTimeWorkRequest.Builder(WorkerA.class).build();
        OneTimeWorkRequest workerBRequest = new OneTimeWorkRequest.Builder(WorkerB.class).build();
        OneTimeWorkRequest workerCRequest = new OneTimeWorkRequest.Builder(WorkerC.class).build();

        WorkManager.getInstance()
                // First, run all the A tasks (in parallel):
                .beginWith(workerCRequest, workerBRequest)
                // ...when all A & B tasks are finished, run the single C task:
                .then(workerARequest)
                .enqueue();
    }
}
