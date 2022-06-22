package com.example.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderPlace extends AppCompatActivity {

    TextView items,subtotal,TVtotal;
    EditText address,phno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_place);
        Intent i=getIntent();
        int a=i.getIntExtra("subtotal",0);
        int b=i.getIntExtra("items",0);
        items=(TextView) findViewById(R.id.items);
        subtotal=(TextView) findViewById(R.id.subtotal);
        TVtotal=(TextView) findViewById(R.id.total);
        address=(EditText) findViewById(R.id.editTextTextPostalAddress);
        phno=(EditText) findViewById(R.id.phno);
        // delivcharge=(TextView) findViewById(R.id.delivcharge);
        Log.d("total","OrderPlace/no of items "+b);
        Log.d("total","OrderPlace/subtotal "+a);
        items.setText(""+b);
        subtotal.setText("Rs."+a);
        int c=a+100;
        TVtotal.setText("Rs."+c);
    }

    public void insertOrder(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderPlace.this);

        if(address.getText().toString().equals("")||phno.getText().toString().equals(""))
        {
            builder.setMessage("Please fill all the fields!").show();
        }
        else {
            builder.setMessage("Do you want to place Order ?").setPositiveButton("Yes,Sure", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            })
                    .setCancelable(false)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        }
    }
}