package com.example.dto;

public class RequestEntity {
    private Long id;
    private String uid;

    public RequestEntity(String uid) {
        this.uid = uid;
    }

    public RequestEntity(Long id, String uid) {
        this.id = id;
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
