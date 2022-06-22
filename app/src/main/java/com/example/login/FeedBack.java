package com.example.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class FeedBack extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        drawerLayout=findViewById(R.id.drawer_layout);
    }
    public void send_click(View view)
    {
        EditText name=(EditText) findViewById(R.id.name);
        EditText email=(EditText) findViewById(R.id.email);
        EditText subject=(EditText) findViewById(R.id.subject);
        EditText message=(EditText) findViewById(R.id.message);
        Button feed_button=(Button) findViewById(R.id.feed_button);
        if( name.getText().toString().equals(""))
            name.setError("Mandatory Field");
        else if(email.getText().toString().equals(""))
        email.setError("Mandatory Field");
        else if(subject.getText().toString().equals(""))
            subject.setError("Mandatory Field");
        else if(message.getText().toString().equals(""))
            message.setError("Mandatory Field");
        else
        {
            Intent intent=new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mail to:"));
            intent.putExtra(Intent.EXTRA_EMAIL,new String []{"sanakhann831@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT,subject.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT,"Dear Sana,\n"
            + message.getText().toString() + "\n Regards, "
            + email.getText().toString());
            Toast.makeText(FeedBack.this," Your feedback has been sent",Toast.LENGTH_SHORT).show();
           /* try
                {
                    startActivity(Intent.createChooser(intent,"Send Email"));
                }
            catch (android.content.ActivityNotFoundException ex)
            {
                Toast.makeText(this,"No Mail App Found",Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex)
            {
                Toast.makeText(this,"UnExpected Error"+ ex.toString(),Toast.LENGTH_SHORT).show();
            }*/
        }
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
        recreate();
    }
    public void ClickMenu(View view)
    {
        user_home.redirectActivity(this,FoodList.class);
    }
    public void ClickAboutUs(View view)
    {
        user_home.redirectActivity(this,AboutUs.class);
    }
    public void ClickLogout(View view)
    {
        user_home.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        user_home.closeDrawer(drawerLayout);
    }
    }
