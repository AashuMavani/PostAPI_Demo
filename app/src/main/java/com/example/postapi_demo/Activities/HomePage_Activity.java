package com.example.postapi_demo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.postapi_demo.Activities.Fragments.Fragment_Add_Product;
import com.example.postapi_demo.Activities.Fragments.Fragment_All_Product;
import com.example.postapi_demo.Activities.Fragments.Fragment_Show_Product;
import com.example.postapi_demo.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

public class HomePage_Activity extends AppCompatActivity
{
    DrawerLayout drawerLayout;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView drawer_txt_name,drawer_txt_email;
    ImageView drawer_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        drawerLayout=findViewById(R.id.drawer);
        appBarLayout=findViewById(R.id.appBar);
        toolbar=findViewById(R.id.toolBar);
        navigationView=findViewById(R.id.nav_View);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle;
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open_Drawer,R.string.Close_Drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View view = navigationView.getHeaderView(0);
        drawer_txt_name=view.findViewById(R.id.drawer_txt_name);
        drawer_txt_email=view.findViewById(R.id.drawer_txt_email);
        drawer_txt_name.setText(""+SplashActivity.sp.getString("name",null));
        drawer_txt_email.setText(""+SplashActivity.sp.getString("email",null));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.addProduct)
                {
                    addFragment(new Fragment_Add_Product());
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else if (item.getItemId()==R.id.viewProduct) {
                    addFragment(new Fragment_Show_Product());
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else if (item.getItemId()==R.id.showAll) {
                    addFragment(new Fragment_All_Product());
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else if (item.getItemId()==R.id.logout) {
                    Intent intent=new Intent(HomePage_Activity.this,Login_Activity.class);
                    startActivity(intent);
                    finish();
                }

                return true;
            }
        });
    }

    private void addFragment(Fragment fragment)
    {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction= fm.beginTransaction();
        transaction.add(R.id.frame, fragment);
        transaction.commit();
    }
}