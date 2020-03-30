package com.example.mywallet;


import android.app.Activity;
import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mywallet.core.MyWalletDbHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.mywallet.core.MyWalletDbContract.ItemTable.EXPENSE;
import static com.example.mywallet.core.MyWalletDbContract.ItemTable.TABLE_NAME;
import static com.example.mywallet.core.MyWalletDbContract.ItemTable.TITLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAllFragment extends ListFragment {


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


    public interface ItemSelected
    {
        void onItemSelected(int id);
    }

    public ShowAllFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_all,container,false);
        //list = (ListView) view.findViewById(R.id.list);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,arrayList);
        myWalletDbHelper = new MyWalletDbHelper(getActivity());
        db = myWalletDbHelper.getWritableDatabase();
        values = new ContentValues();
        totalTextView = (TextView) view.findViewById(R.id.totalTextView);
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

        return view;
    }
}
