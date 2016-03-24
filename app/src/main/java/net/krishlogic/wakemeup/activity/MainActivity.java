package net.krishlogic.wakemeup.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import net.krishlogic.wakemeup.R;
import net.krishlogic.wakemeup.Utilities.Constants;
import net.krishlogic.wakemeup.Utilities.MySharedPreferences;
import net.krishlogic.wakemeup.receiver.AlarmReceiver;
import net.krishlogic.wakemeup.service.Ringer;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextClock mTextClock;
    private TextView mTotalStepCount;
    private TextView mAlarmtime;
    private long lastUpdate;
    private ToggleButton mToggleButton;
    private PendingIntent pendingIntent;
    private TimePicker mTimePicker;
    private Button mButtonOk;
    private RelativeLayout mAlarmLayout;
    private RelativeLayout mTimePickerLayout;
    AlarmManager alarmManager;

    private int count = 0;

    private boolean isAlarmOn = false;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        mTextClock = (TextClock) findViewById(R.id.text_clock);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mTotalStepCount = (TextView) findViewById(R.id.label_step_count);
        mAlarmtime = (TextView) findViewById(R.id.label_alarm_time);
        mToggleButton = (ToggleButton) findViewById(R.id.alarm_switch);
        mTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mAlarmLayout = (RelativeLayout) findViewById(R.id.main_frame);
        mTimePickerLayout = (RelativeLayout) findViewById(R.id.time_picker);
        mButtonOk = (Button) findViewById(R.id.button_ok);

        MySharedPreferences.init_SP_Instance(this);

        mAlarmtime.setText(getAlarmTime());

        //check the saved state of the alarm
        mToggleButton.setChecked(MySharedPreferences.get_Boolean(Constants.KEY_SWITCH_STATE, false));
        isAlarmOn = MySharedPreferences.get_Boolean(Constants.KEY_IS_ALARM_ON, false);

        Log.d("TEST", "IS ALARM ON: " + isAlarmOn);

        if (!isAlarmOn) {
            setOrUnsetAlarm();
        }

        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAlarmLayout.setVisibility(View.VISIBLE);
                mTimePickerLayout.setVisibility(View.INVISIBLE);

                saveAlarmTime(mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());

                mAlarmtime.setText(getAlarmTime());
            }
        });

        mAlarmtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarmLayout.setVisibility(View.GONE);
                mTimePickerLayout.setVisibility(View.VISIBLE);
            }
        });

        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onToggleClicked(buttonView);
            }
        });
    }

    //returns default if shared prefs is null
    private String getAlarmTime() {
        int m = getMin();
        int h = getHr();
        String min = getMin() <= 9  ? "0"+m : ""+m;
        String hr = getHr() <= 9 ? "0"+h :""+h;

        return hr + ":" + min;
    }

    private int getHr() {
        return MySharedPreferences.get_Int(Constants.KEY_ALARM_HR, mTimePicker.getCurrentHour());
    }

    private int getMin() {
        return MySharedPreferences.get_Int(Constants.KEY_ALARM_MIN, mTimePicker.getCurrentMinute());
    }

    private void saveAlarmTime(int hr, int min) {
        MySharedPreferences.put_Int(Constants.KEY_ALARM_HR, hr);
        MySharedPreferences.put_Int(Constants.KEY_ALARM_MIN, min);
    }

    public void onToggleClicked(View view) {
        setOrUnsetAlarm();
    }

    private void setOrUnsetAlarm() {
        if (mToggleButton.isChecked() && !isAlarmOn) {
            setAlarm();
        } else {
            stopAlarm();
        }
    }

    private void setAlarm() {
        //reset the state and make sure the pending intent is cancelled.
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, getHr());
        calendar.set(Calendar.MINUTE, getMin());
        Intent myIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        isAlarmOn = true;
        MySharedPreferences.put_Boolean(Constants.KEY_SWITCH_STATE, isAlarmOn);

        Log.d("TEST", "Alarm On");
    }

    private void stopAlarm() {
        alarmManager.cancel(pendingIntent);
        Log.d("TEST", "Alarm Off");

        isAlarmOn = false;

        Ringer ringer = Ringer.getInstance();
        ringer.stop();

        resetStepCount();

        MySharedPreferences.put_Boolean(Constants.KEY_SWITCH_STATE, isAlarmOn);
        MySharedPreferences.put_Boolean(Constants.KEY_IS_ALARM_ON, isAlarmOn);
    }

    private void updateStepCount()  {
        mTotalStepCount.setText("" + count);
    }

    private void resetStepCount() {
        count = 0;
        updateStepCount();
    }

    //breadk this one into smaller methods
    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        long actualTime = event.timestamp;

        if (isAlarmOn && mToggleButton.isChecked()) {
            if (accelationSquareRoot >= 2.3 &&
                    event.accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH) {

                Log.d("TEST", "" + accelationSquareRoot);
                Log.d("TEST", "STEP COUNT = " + count++);

                if (actualTime - lastUpdate < 200) {
                    return;
                }

                lastUpdate = actualTime;

                updateStepCount();
            }

            if (count >= Constants.COUNT_THRESHOLD) {
                stopAlarm();
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        //update alarmTime;
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
