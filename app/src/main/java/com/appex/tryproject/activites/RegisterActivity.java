package com.appex.tryproject.activites;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appex.tryproject.R;

import java.util.Calendar;


public class RegisterActivity extends AppCompatActivity {
    private Calendar mCalendar;
    private TextView mDateView;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditText RegisterNameEditText=(EditText)findViewById(R.id.register_name_edit_text);
        RegisterNameEditText.setError("Required");
        TextInputLayout RegisterPhNoTextInputLayout=(TextInputLayout)findViewById(R.id.register_phno_text_input_layout);
        RegisterPhNoTextInputLayout.setErrorEnabled(true);
        RegisterPhNoTextInputLayout.setError("10 characters required");
        EditText RegisterPhNoEditText=(EditText)findViewById(R.id.register_phno_edit_text);
        RegisterPhNoEditText.setError("Required");
        EditText RegisterEmailEditText=(EditText)findViewById(R.id.register_email_edit_text);
        RegisterEmailEditText.setError("Required");
        TextInputLayout RegisterRegNoTextInputLayout=(TextInputLayout)findViewById(R.id.register_reg_no_text_input_layout);
        RegisterRegNoTextInputLayout.setErrorEnabled(true);
        RegisterRegNoTextInputLayout.setError("9 characters required");
        EditText RegisterRegNoEditText=(EditText)findViewById(R.id.register_reg_no_edit_text);
        RegisterRegNoEditText.setError("Required");
        final String Msg = "YOU HAVE SUCCESSFULLY REGISTERED. YOUR T_ID IS 20XX";
        mDateView = (TextView) findViewById(R.id.register_dob_button);
        mDateView.setGravity(Gravity.LEFT);

        Button RegisterButton = (Button) findViewById(R.id.register_button);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), Msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        showDate(mYear, mMonth + 1, mDay);
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        mDateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


}

