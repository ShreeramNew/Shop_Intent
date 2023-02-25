package com.example.appintent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class TouchHelp  extends ItemTouchHelper.SimpleCallback {
    private Adapter ad;
    private DataBase db;

    public TouchHelp(Adapter ad) {


        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.ad = ad;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ad.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure want to delete this Item?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ad.deleteItem(position);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ad.notifyItemChanged(viewHolder.getAbsoluteAdapterPosition());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            ad.editItem(position);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dx, float dy, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
        Drawable icon;
        ColorDrawable background;
        View itemView = viewHolder.itemView;
        int backgroundcorneroffset = 20;
        if (dx > 0) {
            icon = ContextCompat.getDrawable(ad.getContext(), R.drawable.editbutton);
            background = new ColorDrawable(ContextCompat.getColor(ad.getContext(), com.google.android.material.R.color.design_dark_default_color_on_surface));

        } else {
            icon = ContextCompat.getDrawable(ad.getContext(), R.drawable.ic_baseline_delete_24);
            background = new ColorDrawable(Color.RED);
        }
        int iconheight = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconButtom = iconTop + icon.getIntrinsicHeight();
        if (dx > 0) {
            int iconLeft = itemView.getLeft() + iconheight;
            int iconRight = itemView.getLeft() + iconheight + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconButtom);
            background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dx) + backgroundcorneroffset, itemView.getBottom());

        } else if (dx < 0) {
            int iconLeft = itemView.getRight() - iconheight - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconheight;
            icon.setBounds(iconLeft, iconTop, iconRight, iconButtom);
            background.setBounds(itemView.getRight() + ((int)dx) - backgroundcorneroffset, itemView.getTop(), itemView.getRight(),itemView.getBottom());


        } else {
            background.setBounds(0, 0, 0, 0);
        }
        background.draw(c);
        icon.draw(c);
    }

}

