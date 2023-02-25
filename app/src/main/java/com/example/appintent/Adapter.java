package com.example.appintent;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private DataBase db;
    private List<Employee2> list;
    private MainActivity activity;


    public Adapter(DataBase db,MainActivity activity){
        this.db=db;

        this.activity=activity;
    }
    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist,parent,false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.opendb();
        list=db.getGetAll();
        final Employee2 em=list.get(position);
        holder.text.setText(em.getName());
        holder.text4.setText(em.getWeight());
        holder.task.setChecked(toBoolean(em.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    db.update_status(em.getID(),1);

                }
                else{
                    db.update_status(em.getID(),0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }
    public void setTasks(List<Employee2> list){
        this.list=list;
        notifyDataSetChanged();
    }


    public Context getContext() {
        return activity;
    }
    public void deleteItem(int position){
        Employee2 em=list.get(position);
        db.delete_task(em.getID());
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        Employee2 em= list.get(position);
        int pos=em.getID();
        String name=em.getName();
        Log.d("position", Integer.toString(pos));
        Log.d("name",name);
//        em.setID(position);
//        Employee2 em=list.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",em.getID());
        bundle.putString("name",em.getName());
        bundle.putString("weight",em.getWeight());
        AddNew fragment=new AddNew();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),AddNew.TAG);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox task;
        TextView text;
        TextView text4;


        ViewHolder(View itemView) {
            super(itemView);
            task=itemView.findViewById(R.id.checkBox2);
            text=itemView.findViewById(R.id.name);
            text4=itemView.findViewById(R.id.weight1);

        }

    }


}
