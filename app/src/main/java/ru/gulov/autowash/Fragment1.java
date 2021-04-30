package ru.gulov.autowash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment1 extends Fragment {


    public Fragment1() {
    }

    TextView auth;
    private DatabaseReference mDatabase;

    EditText editText;
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_1, container, false);
        FirebaseApp.initializeApp(getContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editText = v.findViewById(R.id.et_numbers);

        auth = v.findViewById(R.id.autha);
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = ProgressDialog.show(getActivity(), "Загрузка",
                        "Подключение к серверу", true);
                mDatabase.child("account").child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Ошибка подключения!", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                        else {
                            if(String.valueOf(task.getResult().getValue()).equals(editText.getText().toString())) {

                                progress.dismiss();
                                ((MainActivity)getActivity()).switchToFragment2();

                            }
                            else{

                                progress.dismiss();
                                Toast.makeText(getActivity(), "Данные введены неправельно!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                });
            }
        });

        return v;
    }
}