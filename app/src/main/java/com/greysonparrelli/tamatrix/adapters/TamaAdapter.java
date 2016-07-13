package com.greysonparrelli.tamatrix.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greysonparrelli.tamatrix.R;
import com.greysonparrelli.tamatrix.models.AllTama;
import com.greysonparrelli.tamatrix.models.Tama;
import com.greysonparrelli.tamatrix.ui.TamaCanvas;

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
        holder.canvas.setTama(mTamaList.get(position));
    }

    public void updateItems(AllTama allTama) {
        for (Tama tama : allTama.tama) {
            int index = mTamaList.indexOf(tama);
            if (index >= 0) {
                mTamaList.set(index, tama);
            } else {
                mTamaList.add(tama);
            }
        }
        notifyDataSetChanged();
    }

    public static class TamaViewHolder extends RecyclerView.ViewHolder {

        public TamaCanvas canvas;

        public TamaViewHolder(View itemView) {
            super(itemView);
            canvas = (TamaCanvas) itemView.findViewById(R.id.canvas);
        }
    }
}
