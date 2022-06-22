package com.example.login;

import static com.example.login.CartActivity.dh;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Orderviewholder>{
    static ArrayList<Cart> data=new ArrayList<>();
    static int p=0;
    static int q=0;
    public OrderAdapter(ArrayList<Cart> localdata)
    {data=localdata;
    Log.d("order","orderAdapter constructor");
        }

    public OrderAdapter() {

    }

    @NonNull
    @Override
    public Orderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.cart_item,parent,false);
        Log.d("items","no of items in oncreateVH:"+getItemCount());
        return new Orderviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Orderviewholder holder, int position) {
        Cart x=data.get(position);
        holder.tv1.setText(x.dish_name);
        holder.tv2.setText(x.dish_price);
        holder.qty.setText(x.quantity+"");
        Log.d("onBindView","Quantity in onBindVIEWholder "+ x.quantity);
        Log.d("items","no of items in onBind:"+getItemCount());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ArrayList<Cart> getData() {
        return data;
    }

    public class Orderviewholder extends RecyclerView.ViewHolder{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        Button b;
        TextView qty;
        ImageView minus,plus;
        int quantity=1;
        public Orderviewholder(@NonNull View itemView) {
            super(itemView);

            tv1=(TextView)itemView.findViewById(R.id.tv1);
            tv2=(TextView)itemView.findViewById(R.id.textView);
            tv3=(TextView)itemView.findViewById(R.id.tv3);
            b=(Button) itemView.findViewById(R.id.btnremove);
            qty=(TextView)itemView.findViewById(R.id.qty);
            minus=(ImageView)itemView.findViewById(R.id.minus);
            plus=(ImageView)itemView.findViewById(R.id.plus);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(),data.size());
                    CartActivity.tv_total.setText("Rs."+total());
                }
            });
           minus.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   decQty();
                   notifyItemChanged(getAdapterPosition());
                   CartActivity.tv_total.setText("Rs."+total());
               }
           });
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    incQty();
                    notifyItemChanged(getAdapterPosition());
                    CartActivity.tv_total.setText("Rs."+total());
                }
            });
          //  Intent intent = new Intent(this,OrderAdapter.class);
            //startActivityForResult(intent, 2);
            CartActivity.tv_total.setText("Rs."+total());
           // total();
           // p=total();
        }


        public void removeAt(int position)
        {
            new CartActivity().dh.deleteFromCart( data.get(position).dish_name);
            for (int i=0;i<data.size();i++)
        {if (i==position)
        {
            data.remove(i);
        }
        }

           }
    public void incQty()
    {
        int up=dh.incQtyInCartDish(tv1.getText().toString());
        data.get(getAdapterPosition()).quantity=up;
        Log.d("updated qname",tv1.getText().toString());
        Log.d("updated quantity",up+"");
    }
    public void decQty()
    {Log.d("q",data.get(getAdapterPosition()).quantity+"");
       if(data.get(getAdapterPosition()).quantity>1)
        {
      //  quantity=quantity-1;
        //qty.setText(quantity+"");
            int down=dh.decQtyInCartDish(tv1.getText().toString());
            data.get(getAdapterPosition()).quantity=down;
            Log.d("updated qname",tv1.getText().toString());
            Log.d("updated quantity",down+"");
        }
        else;
    }

    public int total()
    { int price=0;
        int qq=0;
        for (int i=0;i<data.size();i++)
        {
            Log.d("size",data.size()+"");
            Log.d("price",data.get(i).dish_price);
            Log.d("qty",data.get(i).quantity+"");
            int a=Integer.parseInt(data.get(i).dish_price) * data.get(i).quantity;
            Log.d("a",a+"");
            price=price+a;
            qq=qq+data.get(i).quantity;
                    }
        q=qq;
        p=price;
        Log.d("total","total amount: "+price);
       // CartActivity.tv_total.setText("Rs."+price);
        return price;
    }
    }

}