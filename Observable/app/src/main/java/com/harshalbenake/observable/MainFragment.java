package com.harshalbenake.observable;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainFragment extends Fragment {
    public static final String PARAM = "param";
    private CustomObservable customObservable;
    TextView textView;
    public MainFragment() {
    }

    private Observer observer = new Observer() {
        @Override
        public void update(Observable observable, Object data) {
            // customObservable changed!
            // data is the customObservable data (it's the same as customObservable.getValue())
            textView.setText("Observer has changed to new data:\n" + data);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get CustomObservable created in activity
            customObservable = (CustomObservable) getArguments().getSerializable(PARAM);
            // Add listener for value change
            customObservable.addObserver(observer);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textView=(TextView)view.findViewById(R.id.textview);
        return view;
    }


}