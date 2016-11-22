package com.sample.elisabelgenereux.chiffrage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class CostFragment extends Fragment {

    private EventBus eventBus;
    private CompositeSubscription subscriptions;
    private TextView costView;
    private TextView librariesView;

    public CostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cost, container, false);
        costView = (TextView) view.findViewById(R.id.cost);
        librariesView = (TextView) view.findViewById(R.id.libraries);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eventBus = ((MainActivity) getActivity()).getRxBusSingleton();
    }

    @Override
    public void onStart() {
        super.onStart();
        subscriptions = new CompositeSubscription();

        subscriptions
                .add(eventBus.toObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(event -> updateInfos((ClickListItemEvent) event))
                );
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriptions.clear();
    }

    private void updateInfos(ClickListItemEvent event) {
        costView.setText(this.getContext().getString(R.string.cost, MainActivity.globalProjectCost));

        StringBuilder text = new StringBuilder();
        for (JsLibrary library : MainActivity.chosenJsLibraries) {
            if (text.length() != 0) {
                text.append(", ");
            }
            text.append(library.getName());
        }
        librariesView.setText(text.toString());
    }

}