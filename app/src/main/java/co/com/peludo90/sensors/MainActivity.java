package co.com.peludo90.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private final int MIN_TIME = 500;

    private SensorManager mSensorManager;
    private Sensor sensorAcel;
    private Sensor sensorGravity;
    private Sensor sensorLinearAceleration;
    private Sensor sensorRotationVector;
    private Sensor sensorGyro;
    private Sensor sensorSignificantVector;
    private Sensor sensorStepCounter;
    private Sensor sensorStepDetec;

    private TriggerEventListener mTriggerEventListener;

    private TextView tvAcel;
    private TextView tvGrav;
    private TextView tvLinear;
    private TextView tvRotationVec;
    private TextView tvGyro;


    private long lastUpdateAcel;
    private long lastUpdateGrav;
    private long lastUpdateGyro;
    private long lastUpdateRot;
    private long lastUpdateLinear;
    DateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAcel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorLinearAceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        /*sensorSignificantVector = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        sensorStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorStepDetec = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);*/


       /* mTriggerEventListener = new TriggerEventListener() {
            @Override
            public void onTrigger(TriggerEvent event) {
                // Do work
            }
        };*/
        tvAcel = (TextView) findViewById(R.id.tv_acel);
        tvGrav = (TextView) findViewById(R.id.tv_gravity);
        tvLinear = (TextView) findViewById(R.id.tv_linear);
        tvRotationVec = (TextView) findViewById(R.id.tv_vector_rot);
        tvGyro = (TextView) findViewById(R.id.tv_gyro);

        df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


    }

    @Override
    protected void onResume() {
        super.onResume();
     //   mSensorManager.registerListener(this, sensorAcel, SensorManager.SENSOR_DELAY_NORMAL);
       // mSensorManager.registerListener(this, sensorGravity, SensorManager.SENSOR_DELAY_NORMAL);
      //  mSensorManager.registerListener(this, sensorLinearAceleration, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, sensorRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
      //  mSensorManager.registerListener(this, sensorGyro, SensorManager.SENSOR_DELAY_NORMAL);
        /*mSensorManager.registerListener(this, sensorSignificantVector, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, sensorStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, sensorStepDetec, SensorManager.SENSOR_DELAY_NORMAL);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  mSensorManager.unregisterListener(this, sensorAcel);
       // mSensorManager.unregisterListener(this, sensorGravity);
      //  mSensorManager.unregisterListener(this, sensorLinearAceleration);
        mSensorManager.unregisterListener(this, sensorRotationVector);
    //   mSensorManager.unregisterListener(this, sensorGyro);
       /* mSensorManager.unregisterListener(this, sensorSignificantVector);
        mSensorManager.unregisterListener(this, sensorStepCounter);
        mSensorManager.unregisterListener(this, sensorStepDetec);*/
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        long curTime = System.currentTimeMillis();
        Date date = new Date(curTime);

        switch (sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                if (curTime - lastUpdateAcel > MIN_TIME) {
                    appendEvent(date, tvAcel, event.values);
                    lastUpdateAcel = curTime;
                }
                break;
            case Sensor.TYPE_GYROSCOPE:
                if (curTime - lastUpdateGyro > MIN_TIME) {
                    appendEvent(date, tvGyro, event.values);
                    lastUpdateGyro = curTime;
                }
                break;
            case Sensor.TYPE_GRAVITY:
                if (curTime - lastUpdateGrav > MIN_TIME) {
                    appendEvent(date, tvGrav, event.values);
                    lastUpdateGrav = curTime;
                }
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                if (curTime - lastUpdateLinear > MIN_TIME) {
                    appendEvent(date, tvLinear, event.values);
                    lastUpdateLinear = curTime;
                }
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                if (curTime - lastUpdateRot > MIN_TIME) {
                    tvRotationVec.append(df.format(date) + "\n" +
                            "X: " + event.values[0] +
                            " Y: " + event.values[1] +
                            " Z: " + event.values[2] +
                            " Scalar: " + event.values[3] + "\n");
                    lastUpdateRot = curTime;
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void appendEvent(Date date, TextView tv, float[] values) {
        tv.append(df.format(date) + "\n" +
                "X: " + values[0] +
                " Y: " + values[1] +
                " Z: " + values[2] + "\n");
    }
}
