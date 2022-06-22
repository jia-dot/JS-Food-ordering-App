package com.example.login;
import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodListAdaptor extends BaseAdapter {
    private Context context;
    private  int layout;
    private ArrayList<food> foodsList;

    public FoodListAdaptor(Context context, int layout, ArrayList<food> foodsList) {
        this.context = context;
        this.layout = layout;
        this.foodsList = foodsList;
    }

    @Override
    public int getCount() {
        return foodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName, txtPrice;
        Button btn;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtPrice = (TextView) row.findViewById(R.id.txtPrice);
            holder.imageView = (ImageView) row.findViewById(R.id.imgFood);
            holder.btn=(Button) row.findViewById(R.id.button2);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        final food food = foodsList.get(position);

        holder.txtName.setText(food.getName());
        holder.txtPrice.setText(food.getPrice());

        byte[] foodImage = food.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imageView.setImageBitmap(bitmap);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("btn","sara");
                Toast.makeText(context, "is clicked"+position, LENGTH_SHORT).show();
                Cart cart=new Cart();
                cart.dish_name=foodsList.get(position).getName();
                cart.dish_price=foodsList.get(position).getPrice();
                Intent intent=new Intent(context,CartActivity.class);
                intent.putExtra("added_dishname",cart.dish_name);
                intent.putExtra("added_dishprice",cart.dish_price);
                context.startActivity(intent);
            }
        });

        return row;
    }
}

