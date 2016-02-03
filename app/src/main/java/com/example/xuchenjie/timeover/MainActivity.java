package com.example.xuchenjie.timeover;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button setTime;
    private Button startTime;
    private Button endTime;
    private TextView getTime;
    private EditText timeEdit;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        timeEdit = (EditText) findViewById(R.id.timeEdit);
        setTime = (Button) findViewById(R.id.setTime);
        getTime = (TextView) findViewById(R.id.getTime);
        startTime = (Button) findViewById(R.id.startTime);
        endTime = (Button) findViewById(R.id.endTime);

        timeEdit.setOnClickListener(this);
        setTime.setOnClickListener(this);
        getTime.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.setTime:
                try {
                    int i = Integer.parseInt(timeEdit.getText().toString());
                    getTime.setText(Integer.toString(i));
                } catch (Exception e) {
                    Toast.makeText(this, "bushishuzi", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.getTime:
                break;
            case R.id.startTime:
                startTime.setEnabled(false);
                timer.schedule(new MyTask(), 0, 60000);
                break;
            case R.id.endTime:
                startTime.setEnabled(true);
                timer.cancel();
                break;
            default:
                break;
        }
    }
//andor
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 <= 0) {
                getTime.setEnabled(true);
                timer.cancel();
            } else {
                getTime.setText(Integer.toString(msg.arg1));
            }
        }
    };

    private class MyTask extends TimerTask {

        @Override
        public void run() {
            int i = Integer.parseInt(getTime.getText().toString()) - 1;
            Message message = mhandler.obtainMessage();
            message.arg1 = i;
            mhandler.sendMessage(message);
        }

    }
}
