package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.mywallet.core.MyWalletDbHelper;
import java.util.ArrayList;


import static com.example.mywallet.core.MyWalletDbContract.ItemTable.*;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    SQLiteDatabase db;
    Cursor cursor;
    MyWalletDbHelper myWalletDbHelper;
    TextView totalTextView;
    ContentValues values;
    int total = 0;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    ListView list;
    int index;
    long realId;
    long _id;
    String title;
    String expense;
    String helpExpense;
    String arrayMember;
    String databaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList);
        myWalletDbHelper = new MyWalletDbHelper(this);
        db = myWalletDbHelper.getWritableDatabase();
        values = new ContentValues();
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        list.setAdapter(adapter);
        totalTextView.setText("TOTAL : " + String.valueOf(total));


        cursor = db.query(TABLE_NAME,null,null,null,null,null,null);

        while(cursor.moveToNext()) {
            _id = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            expense = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE));

            total = total + Integer.valueOf(expense);
            totalTextView.setText("TOTAL : " + String.valueOf(total));

            arrayList.add(_id + " " + title + " " + expense);

        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                arrayMember = arrayList.get(index);
                String[] parts = arrayMember.split(" ");
                realId = Long.parseLong(parts[0]);
                helpExpense = parts[2];
            }
        });
        adapter.notifyDataSetChanged();
    }


    public void createButton(View view)
    {
        intent = new Intent (this,CreateActivity.class);
        startActivity(intent);

    }

    public void deleteButton(long id)
    {

        db.delete(TABLE_NAME, _ID + "=" + id,null);
        arrayList.remove(index);
        total = total - Integer.parseInt(helpExpense);
        adapter.notifyDataSetChanged();
        totalTextView.setText("TOTAL : " + String.valueOf(total));

    }

    public void editButton(long id)
    {
        intent = new Intent (this,EditActivity.class);
        intent.putExtra("id",id);
        total = total - Integer.parseInt(helpExpense);
        startActivity(intent);
    }


   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.deleteButton:
                //if(_id != 0)
                deleteButton(realId);
                return  true;
            case R.id.editButton:
                editButton(realId);
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
