package com.example.finance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.finance.model.DataManager;
import com.example.finance.model.Question;

import butterknife.ButterKnife;

/**
 * Base class for View Holder so adapter didn't care about types.
 */
public abstract class InfoViewHolder extends RecyclerView.ViewHolder {
    protected DataManager dataManager;

    public InfoViewHolder(View itemView, DataManager dataManager) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.dataManager = dataManager;
    }

    public abstract void bind(Question question);
}
