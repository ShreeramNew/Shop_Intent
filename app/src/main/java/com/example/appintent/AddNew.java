package com.example.appintent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class AddNew extends BottomSheetDialogFragment {
    public static final String TAG="ActionBottomDialog";
    private EditText newT;
    private Button save;
    private DataBase db;
    private EditText getNewT;

    public static AddNew addNew(){
        return new AddNew();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.DialogStyle);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.task,container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return v;
    }
    @SuppressLint("UseRequireInsteadOfGet")
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        newT= Objects.requireNonNull(getView()).findViewById(R.id.task);
        getNewT=Objects.requireNonNull(getView()).findViewById(R.id.weight);
        save=getView().findViewById(R.id.savebutton);
        boolean isUpdate =false;
        final Bundle bundle=getArguments();
        if(bundle!=null)
        {
            isUpdate =true;
            String task=bundle.getString("name");
            String weight=bundle.getString("weight");
            newT.setText(task);
            getNewT.setText(weight);
            assert task!=null;
            if(task.length()>0)
            {
                save.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()),R.color.purple_500));
            }
        }
        db=new DataBase(getActivity());
        db.opendb();
        newT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    save.setEnabled(false);
                    save.setTextColor(Color.GRAY);
                }
                else{
                    save.setEnabled(true);
                    save.setTextColor(ContextCompat.getColor(getContext(),R.color.purple_500));
                }

            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        getNewT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    save.setEnabled(false);
                    save.setTextColor(Color.GRAY);
                }
                else{
                    save.setEnabled(true);
                    save.setTextColor(ContextCompat.getColor(getContext(),R.color.purple_500));
                }

            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        final boolean finalIsupdate = isUpdate;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=newT.getText().toString();
                String weight=getNewT.getText().toString();
                if(finalIsupdate){
                    long upd=db.update(bundle.getInt("id"),text,weight);
                    Log.d("updatename",Long.toString(upd));
                }
                else {
                   long t= db.additems(new Employee2(0,text,weight));
                    Log.d("save",Long.toString(t));
//                    Employee2 em=new Employee2();
//                    em.setName(text);
//                    em.setStatus(0);
////                    em.setID(4);
//                    db.additems(em);

                }
                dismiss();
            }
        });
    }

    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity=getActivity();
        if(activity instanceof DialogCloseListner){
            ((DialogCloseListner)activity).handleDialogClose(dialog);
        }
    }
}
