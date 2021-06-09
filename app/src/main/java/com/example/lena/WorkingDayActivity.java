package com.example.lena;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

public class WorkingDayActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton BtnBack;
    private Button startWDBtn;
    private Button finishWDBtn;
    private Button startLunchBtn;
    private Button finishLunchBtn;
    private Button savePlan;

    private TextView startDayTime;
    private TextView finishDayTime;
    private TextView startLunchTime;
    private TextView finishLunchTime;

    private int startDayHour;
    private int finishDayHour;
    private int startLunchHour;
    private int finishLunchHour;

    private int startDayMinutes;
    private int finishDayMinutes;
    private int startLunchMinutes;
    private int finishLunchMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_day);

        BtnBack = findViewById(R.id.workingDayBtnBack);
        savePlan = findViewById(R.id.saveWorkingDayBtn);

        startWDBtn = findViewById(R.id.startWDBtn);
        finishWDBtn = findViewById(R.id.finishWDBtn);
        startLunchBtn = findViewById(R.id.startLunchBtn);
        finishLunchBtn = findViewById(R.id.finishLunchBtn);

        startDayTime = findViewById(R.id.startDayTime);
        finishDayTime = findViewById(R.id.finishDayTime);
        startLunchTime = findViewById(R.id.startLunchTime);
        finishLunchTime = findViewById(R.id.finishLunchTime);

        startDayHour = 0;
        startDayMinutes = 0;

        finishDayHour = 0;
        finishDayMinutes = 0;

        startLunchHour = 0;
        startLunchMinutes = 0;

        finishLunchHour = 0;
        finishLunchMinutes = 0;

        BtnBack.setOnClickListener(this);
        savePlan.setOnClickListener(this);

        startWDBtn.setOnClickListener(this);
        finishWDBtn.setOnClickListener(this);
        startLunchBtn.setOnClickListener(this);
        finishLunchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.workingDayBtnBack:
                finish();
                break;
            case R.id.saveWorkingDayBtn:
                Toast.makeText(this, "Tus jornada laboral se ha actualizado!", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.startWDBtn:
                popTimerPicker(v, 0);
                break;
            case R.id.finishWDBtn:
                popTimerPicker(v, 1);
                break;
            case R.id.startLunchBtn:
                popTimerPicker(v, 2);
                break;
            case R.id.finishLunchBtn:
                popTimerPicker(v, 3);
                break;
        }
    }

    public void popTimerPicker(View view, int indexBtn){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute ) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                calendar.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
                String time = format.format(calendar.getTime());

                switch (indexBtn){
                    case 0:
                        startDayHour = selectedHour;
                        startDayMinutes = selectedMinute;
                        loadHours(0, time);
                        break;
                    case 1:
                        finishDayHour = selectedHour;
                        finishDayMinutes = selectedMinute;
                        loadHours(1, time);
                        break;
                    case 2:
                        startLunchHour = selectedHour;
                        startLunchMinutes = selectedMinute;
                        loadHours(2, time);
                        break;
                    case 3:
                        finishLunchHour = selectedHour;
                        finishLunchMinutes = selectedMinute;
                        loadHours(3, time);
                        break;

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, 0, 0, false);
        switch (indexBtn){
            case 0:
                timePickerDialog = new TimePickerDialog(this, onTimeSetListener, startDayHour, startDayMinutes, false);
                break;
            case 1:
                timePickerDialog = new TimePickerDialog(this, onTimeSetListener, finishDayHour, finishDayMinutes, false);
                break;
            case 2:
                timePickerDialog = new TimePickerDialog(this, onTimeSetListener, startLunchHour, startLunchMinutes, false);
                break;
            case 3:
                timePickerDialog = new TimePickerDialog(this, onTimeSetListener, finishLunchHour, finishLunchMinutes, false);
                break;
        }


        timePickerDialog.setTitle("Selecciona tu horario");
        timePickerDialog.show();
    }

    public void loadHours(int indexBtn, String time){
        switch (indexBtn){
            case 0:
                startDayTime.setText(time);
                break;
            case 1:
                finishDayTime.setText(time);
                break;
            case 2:
                startLunchTime.setText(time);
                break;
            case 3:
                finishLunchTime.setText(time);
                break;
        }
    }
}