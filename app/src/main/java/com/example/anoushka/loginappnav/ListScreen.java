package com.example.anoushka.loginappnav;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ListScreen extends AppCompatActivity {
    private Button out;
    private FirebaseAuth firebaseAuth;
    private ListView list;
    public String JsonString;
    public LinearLayout item;

    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter contactAdapter;
    String jstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);

      //  item=(LinearLayout)findViewById(R.id.itemid);
   /*   item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      //    Intent intent=new Intent(ListScreen.this,Expand.class);
          //      startActivity(intent);
              //startActivity(new Intent(getApplicationContext(),Expand.class));
            }
        });*/


        firebaseAuth=FirebaseAuth.getInstance();


     if(firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

        list=(ListView)findViewById(R.id.listViewID);
     list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             Intent intent=new Intent(ListScreen.this,Expand.class);
             startActivity(intent);
         }
     });

   /*    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String Tag = null;
                //Log.d("String","test test ");
                Intent newAct= new Intent(ListScreen.this,Expand.class);
                //newAct.putExtra("position",position);

                //String value=list.getItemAtPosition(position).toString();
                //newAct.putExtra("value",value);
                //newAct.putExtra("Value",list.getItemAtPosition(position).toString());

                startActivity(newAct);
            }
        });*/

        out=(Button)findViewById(R.id.button);

       // list.setAdapter(contactAdapter);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
              startActivity(new Intent(getApplicationContext(),MainActivity.class));


            }
        });

        new BackgroundTask().execute();
            if(JsonString == null)
            {
              //  Toast.makeText(getApplicationContext(),"First fetch JSON",Toast.LENGTH_SHORT).show();
                String json_string;
                //json_string=

            }

        contactAdapter=new ContactAdapter(this,R.layout.list_item);


    }
    class BackgroundTask extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url=new URL(" https://api.androidhive.info/contacts/");
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader  bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while ((JsonString=bufferedReader.readLine())!=null) {
                    stringBuilder.append(JsonString).append("\n");

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                 jstring = stringBuilder.toString().trim();


            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                jsonObject = new JSONObject(jstring);
                jsonArray=jsonObject.getJSONArray("contacts");
                int count=0;
                String id,name,email;

                while(count<jsonArray.length())
                {
                    JSONObject JO=jsonArray.getJSONObject(count);
                    id=JO.getString("id");
                    name=JO.getString("name");
                    email=JO.getString("email");
                    Contacts contacts=new Contacts(id,name,email);
                    contactAdapter.add(contacts);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textinfo=(TextView)findViewById(R.id.textView2);
            textinfo.setText(result);
            list.setAdapter(contactAdapter);

        }


    }


    }


