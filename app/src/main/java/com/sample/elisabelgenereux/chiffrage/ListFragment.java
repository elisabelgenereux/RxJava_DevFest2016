package com.sample.elisabelgenereux.chiffrage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ListFragment extends Fragment {

    private EventBus eventBus;
    private CompositeSubscription subscriptions;
    private JsLibraryRecyclerViewAdapter adapter;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jslibrary_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new JsLibraryRecyclerViewAdapter(((MainActivity) getActivity()).getRxBusSingleton());
            recyclerView.setAdapter(adapter);
        }

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
                        .subscribe(
                                event -> updateChosenLibraries((ClickListItemEvent) event),
                                error -> {
                                    Log.e("ListFragment", "An error occured: " + error.getMessage());
                                },
                                () -> {
                                    Log.i("ListFragment", "Observable's task is done");
                                }
                        )
                );

        eventBus.toObservable()
                .subscribe(
                        event -> updateChosenLibraries((ClickListItemEvent) event),
                        error -> {
                            Log.e("ListFragment", "An error occured: " + error.getMessage());
                        },
                        () -> {
                            Log.i("ListFragment", "Observable's task is done");
                        }
                );
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriptions.clear();
    }

    private void updateChosenLibraries(ClickListItemEvent event) {
        if (event.becomeChosen) {
            MainActivity.chosenJsLibraries.add(event.library);
            MainActivity.globalProjectCost += event.library.getCost();

            for (JsLibrary lib : event.library.getDependencies()) {
                MainActivity.chosenJsLibraries.add(lib);
                MainActivity.globalProjectCost += lib.getCost();
            }

        } else {
            Log.i("ListFragment", "updateChosenLibraries rm from Chosen");
            MainActivity.chosenJsLibraries.remove(event.library);
            MainActivity.globalProjectCost -= event.library.getCost();

            for (JsLibrary lib : event.library.getDependencies()) {
                MainActivity.chosenJsLibraries.remove(lib);
                MainActivity.globalProjectCost -= lib.getCost();
            }
        }

        adapter.notifyDataSetChanged();
    }

}
