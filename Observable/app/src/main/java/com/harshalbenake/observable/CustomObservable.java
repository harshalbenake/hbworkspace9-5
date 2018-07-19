package com.harshalbenake.observable;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by sonam.aslekar on 7/13/2018.
 */

public class CustomObservable extends Observable implements Serializable {

    private String data;

    public String getValue() {
        return data;
    }

    public void setValue(String data) {
        this.data = data;
        this.setChanged();
        this.notifyObservers(data);
    }
}