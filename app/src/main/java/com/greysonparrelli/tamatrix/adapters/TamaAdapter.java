package com.greysonparrelli.tamatrix.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greysonparrelli.tamatrix.R;
import com.greysonparrelli.tamatrix.models.AllTama;
import com.greysonparrelli.tamatrix.models.Tama;

import java.util.ArrayList;
import java.util.List;

public class TamaAdapter extends RecyclerView.Adapter <TamaAdapter.TamaViewHolder> {

    private List<Tama> mTamaList;

    public TamaAdapter() {
        mTamaList = new ArrayList<>();
    }

    @Override
    public TamaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tama, parent, false);
        return new TamaViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mTamaList.size();
    }

    @Override
    public void onBindViewHolder(TamaViewHolder holder, int position) {
        Tama tama = mTamaList.get(position);
        holder.text.setText(tama.toString());
    }

    public void updateItems(AllTama allTama) {
        mTamaList.clear();
        mTamaList.addAll(allTama.tama);
        notifyDataSetChanged();
    }

    public static class TamaViewHolder extends RecyclerView.ViewHolder {

        public TextView text;

        public TamaViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
