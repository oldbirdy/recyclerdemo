package com.demo.recyclerxulc;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;



public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int row = 1;
    private int space = 1;

    public SpaceItemDecoration(int row, int space) {
        this.row = row;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.bottom = space;
//        if (parent.getChildAdapterPosition(view) % row == 0)
//            outRect.left = 0;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

    }

}
