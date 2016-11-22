package com.sample.elisabelgenereux.chiffrage;


import java.util.HashSet;
import java.util.Set;

public class JsLibrary {

    private String name;
    private int cost;
    private Set<JsLibrary> dependencies = new HashSet<>();

    public JsLibrary() {

    }

    public JsLibrary(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public String getCostAsString() {
        return cost + "";
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Set<JsLibrary> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<JsLibrary> dependencies) {
        this.dependencies = dependencies;
    }

    public void addDependency(JsLibrary dependency) {
        this.dependencies.add(dependency);
    }
}
