package com.drchip.projectdeadman;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    EditText etDate, etMessage;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etDate= findViewById(R.id.etDate);
        





      etDate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              final Calendar c = Calendar.getInstance();
              int mYear = c.get(Calendar.YEAR); // current year
              int mMonth = c.get(Calendar.MONTH); // current month
              int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
              // date picker dialog
              datePickerDialog = new DatePickerDialog(Register.this,
                      new DatePickerDialog.OnDateSetListener() {

                          @Override
                          public void onDateSet(DatePicker view, int year,
                                                int monthOfYear, int dayOfMonth) {
                              // set day of month , month and year value in the edit text
                              etDate.setText(dayOfMonth + "/"
                                      + (monthOfYear + 1) + "/" + year);

                          }
                      }, mYear, mMonth, mDay);
              datePickerDialog.show();
          }
      });
    }
}
