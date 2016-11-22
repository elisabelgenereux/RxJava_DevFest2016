package com.sample.elisabelgenereux.chiffrage;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class JsLibraryRecyclerViewAdapter extends RecyclerView.Adapter<JsLibraryRecyclerViewAdapter.ViewHolder> {

    private EventBus eventBus;

    public JsLibraryRecyclerViewAdapter(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jslibrary_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final JsLibrary currentLib = MainActivity.jsLibraries.get(position);
        holder.mNameView.setText(currentLib.getName());
        holder.mCostView.setText(currentLib.getCostAsString());
        holder.mView.setSelected(MainActivity.chosenJsLibraries.contains(currentLib));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());

                if (eventBus.hasObservers()) {
                    eventBus.send(new ClickListItemEvent(v.isSelected(), currentLib));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.jsLibraries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mCostView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mCostView = (TextView) view.findViewById(R.id.cost);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCostView.getText() + "'";
        }
    }

}
