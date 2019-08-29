package com.example.zdroa.myapplication.aid;

import java.util.ArrayList;

/**
 * Created by zdroa on 18/05/2017.
 */

public class PersonTypesArrays {

    public ArrayList<String> anxious;
    public ArrayList<String> paranoid;
    public ArrayList<String> histrionic;
    public ArrayList<String> obsessive;
    public ArrayList<String> narcissist;
    public ArrayList<String> schizoid;
    public ArrayList<String> depressive;
    public ArrayList<String> dependent;

    public ArrayList<String> PersonTypesArrays(String arrayListName) {
        anxious = new ArrayList<>(setAnxious());
        paranoid = new ArrayList<>(setParanoid());
        histrionic = new ArrayList<>(setHistrionic());
        obsessive = new ArrayList<>(setObsessive());
        narcissist = new ArrayList<>(setNarcissist());
        schizoid = new ArrayList<>(setSchizoid());
        depressive = new ArrayList<>(setDepressive());
        dependent = new ArrayList<>(setDependent());

        switch (arrayListName){
            case "anxious":
                return anxious;
            case "paranoid":
                return paranoid;
            case "histrionic":
                return histrionic;
            case "obsessive":
                return obsessive;
            case "narcissist":
                return narcissist;
            case "schizoid":
                return schizoid;
            case "depressive":
                return depressive;
            case "dependent":
                return dependent;
        }

        return null;
    }

    private ArrayList<String> setAnxious() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Crime");
        arrayList.add("Drama");
        arrayList.add("Romance");
        arrayList.add("Thriller");
        return arrayList;
    }
    private ArrayList<String> setParanoid() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Crime");
        arrayList.add("Horror");
        arrayList.add("Mystery");
        arrayList.add("Science Fiction");
        arrayList.add("Thriller");
        return arrayList;
    }
    private ArrayList<String> setHistrionic() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Adventure");
        return arrayList;
    }
    private ArrayList<String> setObsessive() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Adventure");
        return arrayList;
    }
    private ArrayList<String> setNarcissist() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Horror");
        arrayList.add("Thriller");
        return arrayList;
    }
    private ArrayList<String> setSchizoid() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Crime");
        arrayList.add("Drama");
        arrayList.add("Fantasy");
        arrayList.add("Horror");
        arrayList.add("Science Fiction");
        return arrayList;
    }
    private ArrayList<String> setDepressive() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Action");
        arrayList.add("Adventure");
        arrayList.add("Animation");
        arrayList.add("Comedy");
        arrayList.add("Fantasy");
        arrayList.add("Romance");
        return arrayList;
    }
    private ArrayList<String> setDependent() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Crime");
        arrayList.add("Drama");
        arrayList.add("Romance");
        arrayList.add("Thriller");
        return arrayList;
    }

    public void ClearLists() {
        for (int i = 0; i < anxious.size(); i++) {
            anxious.remove(i);
        }
        for (int i = 0; i < paranoid.size(); i++) {
            paranoid.remove(i);
        }
        for (int i = 0; i < histrionic.size(); i++) {
            histrionic.remove(i);
        }
        for (int i = 0; i < obsessive.size(); i++) {
            obsessive.remove(i);
        }
        for (int i = 0; i < narcissist.size(); i++) {
            narcissist.remove(i);
        }
        for (int i = 0; i < schizoid.size(); i++) {
            schizoid.remove(i);
        }
        for (int i = 0; i < depressive.size(); i++) {
            depressive.remove(i);
        }
        for (int i = 0; i < dependent.size(); i++) {
            dependent.remove(i);
        }
    }
}
