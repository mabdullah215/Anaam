package com.app.anaamapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.app.anaamapp.R;
import com.app.anaamapp.data.DataSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataSource source=DataSource.getInstance(this);
        if(source.getCurrentUser()==null)
        {
            source.getUserInformation();
        }

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null)
        {
            startActivity(new Intent(getBaseContext(),OnBoardActivity.class));
            finish();
        }
        else
        {
            mAuthListener = new FirebaseAuth.AuthStateListener()
            {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
                {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user==null)
                    {
                        startActivity(new Intent(getBaseContext(),OnBoardActivity.class));
                        finish();
                    }
                }
            };


            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_view);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_dashboard, R.id.navigation_home,R.id.navigation_messages,R.id.navigation_notifications)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);

            bottomNavigationView.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener()
                    {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item)
                        {
                            navController.navigate(item.getItemId());
                            return true;
                        }
                    });
        }
    }

    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    public void onStop()
    {
        super.onStop();
        if (mAuthListener != null)
        { mAuth.removeAuthStateListener(mAuthListener); }
    }



}