package com.example.login;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

    public class DatabaseHelper extends SQLiteOpenHelper {
        ArrayList<Cart> carts=new ArrayList<>();
        // Database Version
        private static final int DATABASE_VERSION = 1;
        // Database Name
        private static final String DATABASE_NAME = "OnlineOrder.sqlite";
        // User table name
        private static final String TABLE_USER = "user";
        // User Table Columns names
        private static final String COLUMN_USER_ID = "user_id";
        private static final String COLUMN_USER_NAME = "user_name";
        private static final String COLUMN_USER_EMAIL = "user_email";
        private static final String COLUMN_USER_PASSWORD = "user_password";

        //Table name
        private static final String TABLE_CART="cart";
        private static final String CART_ITEMID="cartdish_id";
        private static final String CART_DISHNAME="cartdish_name";
        private static final String CART_PRICE="cart_price";
        private static final String CART_QUANTITY="cart_quantity";

        // create table sql query
        private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

        private String CREATE_CART_TABLE ="CREATE TABLE " + TABLE_CART + "("
                + CART_ITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CART_DISHNAME + " TEXT,"
                + CART_PRICE + " INTEGER," + CART_QUANTITY + " INTEGER" + ")";

            private String CREATE_FOOD_TABLE="CREATE TABLE IF NOT EXISTS FOOD" +
                    " ( Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name VARCHAR, price VARCHAR, image BLOB)";

        // drop table sql query
        private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
        private String DROP_CART_TABLE = "DROP TABLE IF EXISTS " + TABLE_CART;
        private String DROP_FOOD_TABLE="DROP TABLE IF EXISTS FOOD";

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
             Log.d("database","created");
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_CART_TABLE);
            db.execSQL(CREATE_FOOD_TABLE);
            Log.d("database","tables created");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            //Drop User Table if exist
            db.execSQL(DROP_USER_TABLE);
            db.execSQL(DROP_CART_TABLE);
            db.execSQL(DROP_FOOD_TABLE);
            // Create tables again
            onCreate(db);

        }

        public void addUser(Users user) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_NAME, user.getName());
            values.put(COLUMN_USER_EMAIL, user.getEmail());
            values.put(COLUMN_USER_PASSWORD, user.getPassword());

            // Inserting Row
            db.insert(TABLE_USER, null, values);
            db.close();
        }




        public boolean checkUser(String email) {

            // array of columns to fetch
            String[] columns = {
                    COLUMN_USER_ID
            };
            SQLiteDatabase db = this.getReadableDatabase();

            // selection criteria
            String selection = COLUMN_USER_EMAIL + " = ?";
            // selection argument
            String[] selectionArgs = {email};

            Cursor cursor = db.query(TABLE_USER, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                      //filter by row groups
                    null);                      //The sort order
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if (cursorCount > 0) {
                return true;
            }

            return false;
        }

        public boolean checkUser(String email, String password) {

            // array of columns to fetch
            String[] columns = {COLUMN_USER_ID};
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

            String[] selectionArgs = {email, password};


            Cursor cursor = db.query(TABLE_USER, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                       //filter by row groups
                    null);                      //The sort order

            int cursorCount = cursor.getCount();

            cursor.close();
            db.close();
            if (cursorCount > 0) {
                return true;
            }

            return false;
        }
        public void addtocart(Cart cart)
        {
            SQLiteDatabase db=this.getReadableDatabase();
            String columns[]={CART_DISHNAME};
            String selectArgs[]={cart.dish_name};
            Cursor c=db.query(TABLE_CART,columns,CART_DISHNAME +"= ?",selectArgs,null,null,null);
            int count=c.getCount();
            Log.d("q",cart.dish_name+" count: "+count+"");
            if(count>0)
            {
                incQtyInCartDish(cart.dish_name);
            }
            else {


             db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(CART_DISHNAME,cart.getDish_name());
            values.put(CART_PRICE, cart.getDish_price());
            values.put(CART_QUANTITY, cart.getQuantity());

            // Inserting Row
            long rowid=db.insert(TABLE_CART, null, values);
            Log.d("cartrow",rowid+"");
            db.close();
        }}
        Cart c1=new Cart();
        Cart c2=new Cart();
        Cart c3=new Cart();
        Cart cc[]={c1,c2,c3};
        public void getCart()
        {
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor c=db.query(TABLE_CART,null,null,null,null,null,null);
            int count=c.getCount();
            Log.d("CartCount",count+"");
            for (int i=0;i<count;i++)
            {c.moveToPosition(i);
                long id=c.getLong(0);
                String dn=c.getString(1);
                String dishprice=c.getString(2);
                int q=c.getInt(3);
               /* cc[i].dish_name=dn;
                cc[i].dish_price=dishprice;
                cc[i].quantity=q;*/
                carts.add(new Cart(dn,dishprice));
            }
            c.close();
            db.close();
        }
        public void deleteFromCart(String dishname)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            int a=db.delete(TABLE_CART,"cartdish_name='"+dishname+"';",null);
            Log.d("deleted from data: ",a+"");
            db=this.getReadableDatabase();
            Cursor c=db.query(TABLE_CART,null,null,null,null,null,null);
            int count=c.getCount();
            Log.d("CartCount",count+"/DH");

        }
        public int incQtyInCartDish(String cartdish_name)
        {
            SQLiteDatabase database = getReadableDatabase();
            //Cursor c=database.query(TABLE_CART,CART_QUANTITY,null,null,null,null,null,null);
            String[] cname={cartdish_name};
            Cursor c=database.rawQuery("SELECT "+CART_QUANTITY+" FROM " + TABLE_CART +" WHERE " + CART_DISHNAME + " = ?", cname );
            int q=0;
            int count=c.getCount();
            Log.d("cursor",c.getPosition()+"");
          //  c.moveToPosition(0);
            while(c.moveToNext())
            {
                q=c.getInt(0);
                Log.d("q","quantity in cart table: "+q);
            }
                        q=q+1;
            database = getWritableDatabase();
            String sql = "UPDATE cart SET cart_quantity = ? WHERE cartdish_name = ?";
            SQLiteStatement statement = database.compileStatement(sql);

            Log.d("q","q increased: "+q);
            statement.bindLong(1,q);
            statement.bindString(2,cartdish_name);
            statement.execute();
//checking the value of quantity is updated in cart or not
          /*  database = getReadableDatabase();
            c=database.rawQuery("SELECT "+CART_QUANTITY+" FROM " + TABLE_CART +" WHERE " + CART_DISHNAME + " = ?", cname );

            int count2=c.getCount();
            Log.d("cursor",c.getPosition()+"");
            //  c.moveToPosition(0);
            while(c.moveToNext())
            {
                q=c.getInt(0);
                Log.d("q","quantity in cart table after updation: "+q);
            }*/
            database.close();

            return q;
        }

        public int decQtyInCartDish(String cartdish_name)
        {
            SQLiteDatabase database = getReadableDatabase();
            //Cursor c=database.query(TABLE_CART,CART_QUANTITY,null,null,null,null,null,null);
            String[] cname={cartdish_name};
            Cursor c=database.rawQuery("SELECT "+CART_QUANTITY+" FROM " + TABLE_CART +" WHERE " + CART_DISHNAME + " = ?", cname );
            int q=0;
            int count=c.getCount();
            Log.d("cursor",c.getPosition()+"");
            //  c.moveToPosition(0);
            while(c.moveToNext())
            {
                q=c.getInt(0);
                Log.d("q","quantity in cart table: "+q);
            }
            q=q-1;
            database = getWritableDatabase();
            String sql = "UPDATE cart SET cart_quantity = ? WHERE cartdish_name = ?";
            SQLiteStatement statement = database.compileStatement(sql);

            Log.d("q","q decreases: "+q);
            statement.bindLong(1,q);
            statement.bindString(2,cartdish_name);
            statement.execute();
//checking the value of quantity is updated in cart or not
          /*  database = getReadableDatabase();
            c=database.rawQuery("SELECT "+CART_QUANTITY+" FROM " + TABLE_CART +" WHERE " + CART_DISHNAME + " = ?", cname );

            int count2=c.getCount();
            Log.d("cursor",c.getPosition()+"");
            //  c.moveToPosition(0);
            while(c.moveToNext())
            {
                q=c.getInt(0);
                Log.d("q","quantity in cart table after updation: "+q);
            }*/
            database.close();

            return q;
        }

        public void queryData(String sql){

            SQLiteDatabase database = getWritableDatabase();
            database.execSQL(sql);
        }

        public void insertData(String name, String price, byte[] image){
            SQLiteDatabase database = this.getWritableDatabase();
            String sql = "INSERT INTO FOOD VALUES (NULL, ?, ?, ?)";

            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();

            statement.bindString(1, name);
            statement.bindString(2, price);
            statement.bindBlob(3, image);

            statement.executeInsert();
        }

        public void updateData(String name, String price, byte[] image, int id) {
            SQLiteDatabase database = getWritableDatabase();

            String sql = "UPDATE FOOD SET name = ?, price = ?, image = ? WHERE id = ?";
            SQLiteStatement statement = database.compileStatement(sql);

            statement.bindString(1, name);
            statement.bindString(2, price);
            statement.bindBlob(3, image);
            statement.bindDouble(4, (double)id);

            statement.execute();
            database.close();
        }

        public  void deleteData(int id) {
            SQLiteDatabase database = getWritableDatabase();

            String sql = "DELETE FROM FOOD WHERE id = ?";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindDouble(1, (double)id);

            statement.execute();
            database.close();
        }

        public Cursor getData(String sql){
            SQLiteDatabase database = this.getReadableDatabase();
            return database.rawQuery(sql, null);
        }

}
