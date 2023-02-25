package com.example.appintent;

public class Employee2 {
    private int id, status;
    private String name;
    private String weight;

    public Employee2( int status, String name,String weight) {
        this.status = status;
        this.name = name;
        this.weight=weight;
    }

    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public int getID() {
            return id;
        }

        public void setID(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


    }
