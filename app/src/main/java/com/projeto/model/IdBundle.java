package com.projeto.model;

/**
 * Created by leo on 16/03/16.
 */
public enum IdBundle {
    HOST("host");

    private String id;

    private IdBundle(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
