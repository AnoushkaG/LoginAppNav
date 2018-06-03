package com.example.anoushka.loginappnav;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ProgressDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button butn;
    private EditText emailID;
    private EditText pass;
    private TextView txt;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private TextView login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ListScreen.class));

        }
        progressDialog = new ProgressDialog(this);

        butn = (Button) findViewById(R.id.button1);
        emailID = (EditText) findViewById(R.id.editText1);
        pass = (EditText) findViewById(R.id.editText2);
        txt = (TextView) findViewById(R.id.textCred);
        login = (TextView) findViewById(R.id.TextViewLogin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        butn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            userLogin();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void registerUser() {

        String email = emailID.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(MainActivity.this, "Please enter your emailID", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering user");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this,"User is Registered",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                        else {
                            Toast.makeText(MainActivity.this,"Error occured while registering",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void userLogin()
    {
        String email = emailID.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(MainActivity.this, "Please enter your emailID", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging in");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ListScreen.class));

                            //list screen
                        }

                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

   int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
