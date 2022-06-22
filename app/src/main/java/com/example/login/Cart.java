package com.example.login;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.example.login.CartActivity.dh;

public class Cart {
    String dish_name=new String();
    String dish_price;
    int quantity=1;
    Cart()
    {
        //quantity++;
    }
    Cart(String dn,String dp)
    {dish_name=dn;
        dish_price=dp;
        //new DatabaseHelper(new CartActivity());
        SQLiteDatabase s=dh.getReadableDatabase();
        String[] cname={dish_name};
        Cursor c=s.rawQuery("SELECT cart_quantity FROM cart WHERE cartdish_name = ? ", cname );
        int q=1;
        int count=c.getCount();
        Log.d("cursor",c.getPosition()+" in CartActivity");
        //  c.moveToPosition(0);
        //if this item is not in cart.
        if(count<1)
        {
            quantity=1;
        }
        else{
        while(c.moveToNext())
        {
            q=c.getInt(0);
            Log.d("q","quantity in cart table CA: "+q);
        }
        quantity=q;}


       // quantity++;

    }

    public String getDish_name() {
        return dish_name;
    }

    public String getDish_price() {
        return dish_price;
    }

    public int getQuantity() {
        return quantity;
    }
}
