package com.example.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CartActivity extends AppCompatActivity {
    RecyclerView recycler_itemlist;
    public static TextView tv_total;
    //CartListAdapter cartListAdapter;
    public static int total=0;
    String jsonCartList;
    static DatabaseHelper dh;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recycler_itemlist=findViewById(R.id.recycler_cart);
        drawerLayout=findViewById(R.id.drawer_layout);
        recycler_itemlist.setLayoutManager(new LinearLayoutManager(this));
        String[] languages={"java","ulu","anda","paratha"};
        try {
            Intent intent = getIntent();
            dh = new DatabaseHelper(this);
            String c1 = intent.getStringExtra("added_dishname");
            String c2 = intent.getStringExtra("added_dishprice");
            Cart cart = new Cart(c1, c2);
            //taking quantity from database
            SQLiteDatabase s = dh.getReadableDatabase();
            String[] cname = {c1};
            Cursor c = s.rawQuery("SELECT cart_quantity FROM cart WHERE cartdish_name = ? ", cname);
            int q = 1;
            int count = c.getCount();
            Log.d("cursor", c.getPosition() + " in CartActivity");
            //  c.moveToPosition(0);
            while (c.moveToNext()) {
                q = c.getInt(0);
                Log.d("q", "quantity in cart table CA: " + q);
            }
            cart.quantity = q;
            Log.d("q", "cart quantity in CartActivity " + cart.quantity);
            dh.addtocart(cart);
        }
        catch(Exception e)
        {System.out.println("Exception in cart activity");}
        finally {

            dh.getCart();
            Log.d("products", "cart size:" + dh.carts.size());
            Log.d("q", "dh.cart quantity" + dh.carts.get(0).quantity);
            OrderAdapter od = new OrderAdapter(dh.carts);
            recycler_itemlist.setAdapter(od);
            tv_total = (TextView) findViewById(R.id.tv_total);
        }

    }

    public void goToCheckOut(View view) {
        Intent i=new Intent(this,OrderPlace.class);
        i.putExtra("subtotal",OrderAdapter.p);
        Log.d("total","CartActivity/subtotal: "+OrderAdapter.p);
        i.putExtra("items",OrderAdapter.q);
        Log.d("total","CartActivity/no of items in checkOut "+OrderAdapter.q);
        startActivity(i);
    }
    public void Clickmenu(View view) {
        user_home.openDrawer(drawerLayout);
    }
    public void clicklogo(View view)
    {
        user_home.closeDrawer(drawerLayout);
    }
    public void clickHome(View view)
    {
        user_home.redirectActivity(this, user_home.class);
    }
    public void ClickFeedBack(View view)
    {
        user_home.redirectActivity(this,FeedBack.class);
    }
    public void ClickMenu(View view)    { user_home.redirectActivity(this,FoodList.class);}
    public void ClickAboutUs(View view)
    {
        user_home.redirectActivity(this,AboutUs.class);
    }
    public void ClickLogout(View view)
    {
        user_home.logout(this);
    }
    public void clickCart(View view)
    {
        recreate();
    }
    @Override
    protected void onPause() {
        super.onPause();
        user_home.closeDrawer(drawerLayout);
    }

    //Set back button to activity
/*       ActionBar actionBar=getSupportActionBar();
       if(actionBar!=null){
          actionBar.setHomeButtonEnabled(true);
           actionBar.setTitle("Cart");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tv_total =(TextView) findViewById(R.id.tv_total);

        recycler_itemlist =(RecyclerView) findViewById(R.id.recycler_cart);
        recycler_itemlist.setHasFixedSize(true);
        recycler_itemlist.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recycler_itemlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recycler_itemlist.getRecycledViewPool().setMaxRecycledViews(0, 0);

        cartListAdapter = new CartListAdapter(CartActivity.this,ItemListAdapter.selecteditems);
        recycler_itemlist.setAdapter(cartListAdapter);

        getIntentData();

        calculateTotal();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getIntentData(){
        if(getIntent()!=null && getIntent().getExtras()!=null){
            // Get the Required Parameters for sending Order to server.
        }
    }

    public static void calculateTotal(){
        int i=0;
        total=0;
        while(i<ItemListAdapter.selecteditems.size()){
            total=total + ( Integer.valueOf(ItemListAdapter.selecteditems.get(i).getRate()) * Integer.valueOf(ItemListAdapter.selecteditems.get(i).getQuantity()) );
            i++;
        }
        tv_total.setText(""+total);
    }

    public void insertOrder(View view){

        if(total>0){

            Gson gson = new Gson();
            jsonCartList = gson.toJson(ItemListAdapter.selecteditems);

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            placeOrderRequest();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
            builder.setMessage("Do you want to place Order ?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }else{
            Toast.makeText(CartActivity.this,"No items in Cart !",Toast.LENGTH_LONG).show();
        }


    }


    private void placeOrderRequest(){
        //Send Request to Server with required Parameters

 //  jsonCartList - Consists of Objects of all product selected.


    }*/
}