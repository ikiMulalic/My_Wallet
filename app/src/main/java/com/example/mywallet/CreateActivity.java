package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.mywallet.core.MyWalletDbHelper;
import static com.example.mywallet.core.MyWalletDbContract.ItemTable.*;

public class CreateActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ContentValues values;
    EditText titleEditText;
    EditText costEditText;
    RadioButton incomeRadioButton;
    RadioButton expenseRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        MyWalletDbHelper myWalletDbHelper = new MyWalletDbHelper(this);
        db = myWalletDbHelper.getWritableDatabase();
        values = new ContentValues();
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        costEditText = (EditText) findViewById(R.id.costEditText);
        incomeRadioButton = (RadioButton)findViewById(R.id.incomeRadioButton);
        expenseRadioButton = (RadioButton) findViewById(R.id.expenseRadioButton);

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
        db.insert(TABLE_NAME,null,values);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

        titleEditText.setText("");
        costEditText.setText("");
    }


}
