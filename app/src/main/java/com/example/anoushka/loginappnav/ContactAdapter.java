package com.example.anoushka.loginappnav;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter{
private List list=new ArrayList();
ContactHolder contactHolder;
    ContactAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);

    }

    @Override
    public int getCount() {
       // return super.getCount();
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row=convertView;

        if(row==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.list_item,parent,false);
            contactHolder=new ContactHolder();
            contactHolder.tx_id=(TextView)row.findViewById(R.id.tx_id);
            contactHolder.tx_name=(TextView)row.findViewById(R.id.tx_name);
            contactHolder.tx_email=(TextView)row.findViewById(R.id.tx_email);
            row.setTag(contactHolder);

        }
        else
        {
            contactHolder= (ContactHolder) row.getTag();

        }
        Contacts contacts=(Contacts)this.getItem(position);
        contactHolder.tx_id.setText(contacts.getId());
        contactHolder.tx_name.setText(contacts.getName());
        contactHolder.tx_email.setText(contacts.getEmail());
        return row;




    }
    static class ContactHolder
    {

        TextView tx_id,tx_name,tx_email;

    }
}
