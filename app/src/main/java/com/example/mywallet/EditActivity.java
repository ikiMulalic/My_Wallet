package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mywallet.core.MyWalletDbContract;
import com.example.mywallet.core.MyWalletDbHelper;

import static com.example.mywallet.core.MyWalletDbContract.*;
import static com.example.mywallet.core.MyWalletDbContract.ItemTable.*;

public class EditActivity extends AppCompatActivity {

    Cursor cursor;
    ContentValues values;
    EditText titleEditText;
    EditText costEditText;
    RadioButton incomeRadioButton;
    RadioButton expenseRadioButton;
    SQLiteDatabase db;
    Intent intent;
    long id;
    String title;
    String expense;
    int realIndex;
    int cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        MyWalletDbHelper myWalletDbHelper = new MyWalletDbHelper(this);
        db = myWalletDbHelper.getWritableDatabase();
        values = new ContentValues();
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        costEditText = (EditText) findViewById(R.id.costEditText);
        incomeRadioButton = (RadioButton) findViewById(R.id.incomeRadioButton);
        expenseRadioButton = (RadioButton) findViewById(R.id.expenseRadioButton);
        intent = getIntent();
        id = intent.getLongExtra("id", id);
        cursor = db.query(TABLE_NAME,null,_ID + "=" + String.valueOf(id),null,null,null,null);

        if(cursor.moveToNext())
        {
            title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            expense = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE));
            cost = Integer.parseInt(expense);
            titleEditText.setText(title);
            if(cost > 0)
            {
                costEditText.setText(String.valueOf(cost));
            }
            else {
                costEditText.setText(String.valueOf(cost * -1));
            }

        }
    }

    public void submitButton(View view)
    {
        values.put(TITLE, String.valueOf(titleEditText.getText()));
        if(incomeRadioButton.isChecked()) {
            values.put(EXPENSE, String.valueOf(costEditText.getText()));
        }
        else
        {
            int a = Integer.valueOf(String.valueOf(costEditText.getText())) * -1;
            values.put(EXPENSE, String.valueOf(a));
        }
        db.update(TABLE_NAME,values,_ID + "=" + id,null);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
