package com.example.monefy.basic.functionality.fragment.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monefy.R;
import com.example.monefy.Manager.firebase.AuthenticationManager;
import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.Manager.firebase.OnUserDataCallback;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        setupUIElements(view);
        loadGetUserData();

        return view;
    }

    private TextView textFullUserName, textEmail;

    private void setupUIElements(View view){
        this.textFullUserName = view.findViewById(R.id.textViewFullUserName);
        this.textEmail = view.findViewById(R.id.textViewEmail);
    }

    private void displaysUserData(String Name, String lastName){
        textFullUserName.setText(Name + " " + lastName);
        textEmail.setText(AuthenticationManager.getAuthenticationManager().getEmail());
    }

    private void loadGetUserData(){
        String userId = AuthenticationManager.getAuthenticationManager().getUserId();
        FirebaseManager.getUserPersonalData(FirebaseFirestore.getInstance(), userId, new OnUserDataCallback() {
            @Override
            public void onUserDataReceived(DocumentSnapshot documentSnapshot) {
                displaysUserData(
                        documentSnapshot.getString("name"),
                        documentSnapshot.getString("lastName")
                );
            }

            @Override
            public void onUserDataNotFound() {
                Log.d("error","Відсутні дані");
            }

            @Override
            public void onUserDataError(Exception e) {
                Log.e("ERROR", "Помилка при отриманні даних: " + e.getMessage());
            }
        });
    }


}