package com.example.buttons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    private Button GreenTeaPlus;
    private Button GreenTeaMinus;
    private TextView Count;
    private String productId="Product 1";
    private String TextOP="";
    DatabaseReference test;
    int GreenTeaCount;
    final String Drink = "Vending Machine 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap<String, Long> promap = new HashMap<String, Long>();
        HashMap<String, long[]> locmap = new HashMap<String, long[]>();


        DatabaseReference mRootDatabaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference GwenTea = mRootDatabaseRef.child(productId); //for product 1
        DatabaseReference MachineLoc = mRootDatabaseRef.child("Machine"); // for location

        GreenTeaPlus = findViewById(R.id.GreenTeaplus);
        GreenTeaMinus = findViewById(R.id.GreenTeaminus);
        Count = findViewById(R.id.countvalue);




        GreenTeaPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mRootDatabaseRef.child(Drink).child("Green Tea").setValue(5); //Drink = parent, Gwen Tea = Child of Drink
            }
                                    }
        );

        GwenTea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.getChildren()){
                    promap.put(i.getKey(),i.getValue(Long.class)); //Hashmap to show which machine has how many of that product (Nani?)
                    TextOP += i.getKey() + ": " + i.getValue(Long.class) + '\n';
                    //System.out.print(TextOP);
                }

                Count.setText(TextOP);
                //System.out.println ("Promap :" + promap);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        MachineLoc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot j : snapshot.getChildren()){
                    long[] locarray = new long[2]; //store x and y
                    locarray[0] = (long)j.child("locX").getValue();
                    locarray[1] = (long)j.child("locY").getValue();
                    locmap.put(j.getKey(), locarray);
                }
                for (long[] array : locmap.values()) {
                    System.out.println("locmap: " + array[0]);
                    System.out.println("locmap: " + array[1]);
                    //System.out.println("locarray: " + locarray);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}