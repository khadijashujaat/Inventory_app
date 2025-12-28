package com.example.sqldemo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter{
    Context context;
    ArrayList<InventoryItem> arrayList;

    public MyAdapter(ArrayList<InventoryItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.card_layout,null);
        TextView c_name=convertView.findViewById(R.id.card_name);
      //  TextView c_price= convertView.findViewById(R.id.card_price);
        //TextView c_quantity=convertView.findViewById(R.id.card_quantity);

        InventoryItem item=arrayList.get(position);
        String name=item.getName();
     //   String price=item.getPrice();
       // String quantity=item.getQuantity();

        c_name.setText(name);
        //c_price.setText(price);
      //  c_quantity.setText(quantity);

        return convertView;
    }

    public void searchFilter(ArrayList<InventoryItem> searchList) {
        arrayList=new ArrayList<>();
        arrayList.addAll(searchList);

    }
}