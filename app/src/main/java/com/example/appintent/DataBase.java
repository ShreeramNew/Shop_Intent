package com.example.appintent;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DataBase(Context context) {
        super(context,"ItemCheck", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {



        String table="CREATE TABLE ItemCheck (id INTEGER PRIMARY KEY AUTOINCREMENT,status INTEGER,name TEXT,weight TEXT)";
        sqldb.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String d=String.valueOf("DROP TABLE IF EXISTS");

        db.execSQL(d,new String[]{"ItemCheck"});
        onCreate(db);
    }
    public void opendb(){
       SQLiteDatabase db=this.getWritableDatabase();

    }

    public long additems(Employee2 emp) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ct = new ContentValues();
        ct.put("name", emp.getName());

        ct.put("weight",emp.getWeight());
//        ct.put("id",emp.getID());
        ct.put("status",emp.getStatus());
        long k=db.insert("ItemCheck", null, ct);
        Log.d("Insert",Long.toString(k));
        db.close();
        return k;

    }
    public long update(int id,String task,String weight){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ct = new ContentValues();
        ct.put("name",task);
        ct.put("weight",weight);
        long r=db.update("ItemCheck",ct,"id=?",new String[]{String.valueOf(id)});
        db.close();
        return r;

    }
    public void update_status(int id,int status){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ct = new ContentValues();
        ct.put("status",status);
        db.update("ItemCheck",ct,"id=?",new String[]{String.valueOf(id)});
        db.close();

    }
    public void delete_task(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long k=db.delete("ItemCheck","id=?",new String[]{String.valueOf(id)});
        Log.d("deleted",Long.toString(k));
        db.close();
    }


    public List<Employee2> getGetAll() {
        List<Employee2> itemlist=new ArrayList<>();
        Cursor cursor=null;
        db=this.getReadableDatabase();
        db.beginTransaction();
        try{
            cursor=db.query("ItemCheck",null,null,null,null,null,null,null);
            if(cursor !=null)
            {
               if(cursor.moveToFirst()) {
                   do{
                       Employee2 em=new Employee2(cursor.getInt(cursor.getColumnIndexOrThrow("status")),cursor.getString(cursor.getColumnIndexOrThrow("name")),cursor.getString(cursor.getColumnIndexOrThrow("weight")));
                       em.setID(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                       itemlist.add(em);

                   }while (cursor.moveToNext());
               }
            }
        }finally {
            db.endTransaction();
//            assert cursor!=null;
            assert cursor != null;
            cursor.close();
        }
        return itemlist;
    }
}
