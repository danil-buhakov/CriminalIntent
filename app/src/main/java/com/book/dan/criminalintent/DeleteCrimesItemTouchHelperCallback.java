package com.book.dan.criminalintent;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class DeleteCrimesItemTouchHelperCallback extends ItemTouchHelper.Callback {
    public interface ItemTouchHelperAdapter {
        void onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }

    ItemTouchHelperAdapter mAdapter;

    public DeleteCrimesItemTouchHelperCallback(ItemTouchHelperAdapter adapter){
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipeFlag = ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlag,swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
