package com.drchip.projectdeadman;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    EditText etDate, etMessage, etPlatform;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(Register.this, Enter.class));
        Register.this.finish();
    }
    DatePickerDialog datePickerDialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.stmlogo);
        actionBar.setTitle(" Register");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        etDate= findViewById(R.id.etDate);
        etMessage = findViewById(R.id.etMessage);
        etPlatform = findViewById(R.id.etPlatform);




        etMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    AlertDialog.Builder message = new AlertDialog.Builder(Register.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.message_dialog, null);
                    final EditText etReleaseMessage = dialogView.findViewById(R.id.etReleaseMessage);

                    message.setView(dialogView);
                    message.setTitle("Message to Release");
                    message.setMessage("Enter the message that you want to release here!!");

                    message.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            etMessage.setText(etReleaseMessage.getText());
                        }
                    });
                    message.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    message.show();
                }

                return false;
            }
        });

        etDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
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


                return false;
            }
        });


        etPlatform.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {


                    final AlertDialog.Builder message = new AlertDialog.Builder(Register.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.platform_dialog, null);
                    //   final EditText etReleaseMessage = dialogView.findViewById(R.id.etReleaseMessage);
                    final RadioButton rbSMS = dialogView.findViewById(R.id.rbSMS);
                    final RadioButton rbEmail = dialogView.findViewById(R.id.rbEmail);
                    final EditText etEmail = dialogView.findViewById(R.id.etMail);
                    final EditText etPhone = dialogView.findViewById(R.id.etPhone);


                    message.setView(dialogView);
                    message.setTitle("Platform to Release");
                    message.setMessage("Enter the platform that you want to release here!!");


                    rbSMS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            etPhone.setVisibility(View.VISIBLE);
                            etEmail.setVisibility(View.GONE);
                        }
                    });

                    rbEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            etEmail.setVisibility(View.VISIBLE);
                            etPhone.setVisibility(View.GONE);

                        }
                    });

                    message.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (rbEmail.isChecked()) {
                                if (etEmail.getText().toString().isEmpty()) {
                                    Toast.makeText(Register.this, "Please put an email!", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                } else {
                                    etPlatform.setText("Email " + etEmail.getText().toString().trim());
                                }
                            } else if (rbSMS.isChecked()) {
                                if (etPhone.getText().toString().isEmpty()) {
                                    Toast.makeText(Register.this, "Please put an phone number!", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();

                                } else {
                                    etPlatform.setText("SMS " + etPhone.getText().toString().trim());
                                }
                            } else {
                                Toast.makeText(Register.this, "Please select an option!!", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }

                        }
                    });
                    message.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    message.show();

                    return false;
                }
                return false;

            }
        });


    }
}
