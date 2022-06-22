package com.example.login;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
public class AboutUs extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        drawerLayout=findViewById(R.id.drawer_layout);
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
    public void ClickMenu(View view)
    {
        user_home.redirectActivity(this,FoodList.class);
    }
    public void ClickAboutUs(View view)
    {
        recreate();
    }
    public void ClickLogout(View view)
    {
        user_home.logout(this);
    }
    //public  void ClickCart(View View) {user_home.redirectActivity(this,CartActivity.class);}

    @Override
    protected void onPause() {
        super.onPause();
        user_home.closeDrawer(drawerLayout);
    }
}

