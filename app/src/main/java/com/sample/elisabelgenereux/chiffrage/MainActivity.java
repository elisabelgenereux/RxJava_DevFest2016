package com.sample.elisabelgenereux.chiffrage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private EventBus eventBus = null;
    public final static List<JsLibrary> jsLibraries = new ArrayList<>();
    public static Set<JsLibrary> chosenJsLibraries = new HashSet<>();
    public static int globalProjectCost = 0;

    public EventBus getRxBusSingleton() {
        if (eventBus == null) {
            eventBus = new EventBus();
        }

        return eventBus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fillJsLibrariesList();
    }

    private void fillJsLibrariesList() {
        jsLibraries.add(new JsLibrary("JQuery", 3));

        JsLibrary reactJs = new JsLibrary("ReactJS", 5);
        jsLibraries.add(reactJs);

        JsLibrary reactDom = new JsLibrary("React DOM", 1);
        jsLibraries.add(reactDom);

        JsLibrary babel = new JsLibrary("Babel", 2);
        jsLibraries.add(babel);

        JsLibrary fetch = new JsLibrary("Fetch", 3);
        jsLibraries.add(fetch);

        JsLibrary es6 = new JsLibrary("EcmaScript 6", 4);
        jsLibraries.add(es6);

        JsLibrary commonJs = new JsLibrary("CommonJS", 3);
        jsLibraries.add(commonJs);

        JsLibrary grunt = new JsLibrary("grunt", 1);
        jsLibraries.add(grunt);

        JsLibrary systemJs = new JsLibrary("SystemJS", 2);
        jsLibraries.add(systemJs);

        jsLibraries.add(new JsLibrary("Webpack", 3));
        jsLibraries.add(new JsLibrary("Typescript", 1));
        jsLibraries.add(new JsLibrary("Flow", 1));
        jsLibraries.add(new JsLibrary("AngularJS", 5));
        jsLibraries.add(new JsLibrary("VueJS", 4));
        jsLibraries.add(new JsLibrary("RxJS", 5));

        reactJs.addDependency(reactDom);
        reactJs.addDependency(babel);
        reactJs.addDependency(fetch);

        es6.addDependency(commonJs);
        es6.addDependency(grunt);

        systemJs.addDependency(grunt);
    }

}
