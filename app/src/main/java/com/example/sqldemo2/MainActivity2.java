package com.example.sqldemo2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * @noinspection resource
 */
public class MainActivity2 extends AppCompatActivity implements SearchView.OnQueryTextListener {


    //references to buttons and other "" on layout

    ArrayList<InventoryItem> arrayList;
    ArrayList<InventoryItem> searchList=new ArrayList<>();
    ArrayList<String> l_list;
    MyAdapter adapter;
    DatabaseHelper db_helper;
    ListView listview;
    String d_name;
    int d_id,rightPosition=0,percentage;
    InventoryItem invent_item;
    FloatingActionButton click;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db_helper = new DatabaseHelper(MainActivity2.this);
        listview = findViewById(R.id.r_list);
        click=findViewById(R.id.fab);

        showInventoryData();

       listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (rightPosition==1){
                    invent_item =searchList.get(position);
                }else {
                    invent_item = arrayList.get(position);
                }

                String name=invent_item.getName();
                String price= invent_item.getPrice();
                String quantity= invent_item.getQuantity();

                Intent intent = new Intent(MainActivity2.this, Inventory_pg_activity.class);
                intent.putExtra("ID", String.valueOf(d_id));
                intent.putExtra("NAME", String.valueOf(name));
                intent.putExtra("PRICE", String.valueOf(price));
                intent.putExtra("QUANTITY", String.valueOf(quantity));

                startActivity(intent);
            }
        });


        listview.setMultiChoiceModeListener(modeListener);
        listview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList = db_helper.getAll();
                l_list=new ArrayList<>();

                for (InventoryItem invent_item:arrayList) {
                    l_list.add(invent_item.getQuantity());
                }
                Intent intent=new Intent(MainActivity2.this, display_piechart.class);
                intent.putStringArrayListExtra("list",l_list);
                startActivity(intent);
            }
        });

    }

    private void showInventoryData() {
        arrayList = db_helper.getAll();
        adapter = new MyAdapter(arrayList, MainActivity2.this);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.insert_menu, menu);
        MenuItem search= menu.findItem(R.id.search_);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals("new")){

            LayoutInflater inflater = (LayoutInflater) MainActivity2.this.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_entry, null);

            final EditText et_name = view.findViewById(R.id.prd_name);
            final EditText et_price = view.findViewById(R.id.prd_price);
            final EditText et_quantity = view.findViewById(R.id.prd_quantity);


            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
            builder.setView(view)
                    .setTitle("Adding new Product ")
                    .setMessage("Enter Product info ")
                    .setIcon(R.drawable.sharp_inventory_24)
                    .setPositiveButton("Add new Product ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String name = et_name.getText().toString();
                            String price = et_price.getText().toString();
                            String quantity = et_quantity.getText().toString();

                            boolean result = db_helper.insertData(name, price, quantity);

                            if (result == true) {
                                showInventoryData();
                                Toast.makeText(MainActivity2.this, "New Product added!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity2.this, "Error adding product!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create().show();
        }


        return super.onOptionsItemSelected(item);
    }

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {


        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            if (rightPosition==1){
                invent_item =searchList.get(position);
            }else {
                invent_item = arrayList.get(position);
            }

            d_id = invent_item.getId();
            d_name = invent_item.getName();
            mode.setTitle(d_name);
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.abs_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            if (item.getTitle().equals("delete")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setTitle("Deleting " + d_name)
                        .setMessage("Are you sure? ")
                        .setIcon(R.drawable.baseline_delete_23)
                        .setPositiveButton("Delete " + d_name, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int res = db_helper.deleteOne(String.valueOf(d_id));

                                if (res > 0) {
                                    showInventoryData();
                                    mode.finish();
                                    Toast.makeText(MainActivity2.this, d_name + " has been deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity2.this, d_name + " has not been deleted", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();

            }
            else {
                LayoutInflater inflater = (LayoutInflater) MainActivity2.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.item_entry, null);

                final EditText et_name = view.findViewById(R.id.prd_name);
                final EditText et_price = view.findViewById(R.id.prd_price);
                final EditText et_quantity = view.findViewById(R.id.prd_quantity);

                String old_price= invent_item.getPrice();
                String old_quantity=invent_item.getQuantity();

                et_name.setText(d_name);
                et_price.setText(old_price);
                et_quantity.setText(old_quantity);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setView(view)
                        .setTitle("Updating "+d_name+" information")
                        .setMessage("Change " +d_name+" information")
                        .setIcon(R.drawable.round_update_24)
                        .setPositiveButton("Update ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String name = et_name.getText().toString();
                                String price = et_price.getText().toString();
                                String quantity = et_quantity.getText().toString();

                                boolean result = db_helper.updateData(String.valueOf(d_id),name, price, quantity);

                                if (result == true) {
                                    showInventoryData();
                                    Toast.makeText(MainActivity2.this, d_name+" information updated.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity2.this, d_name+" information not updated.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();

            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText=newText.toLowerCase();
        searchList=new ArrayList<>();
        rightPosition=1;

        for (InventoryItem invent_item:arrayList){
            String name=invent_item.getName().toLowerCase();
            if (name.contains(newText)){
                searchList.add(invent_item);

            }
        }
        adapter.searchFilter(searchList);
        adapter.notifyDataSetChanged();
        return true;
    }





}