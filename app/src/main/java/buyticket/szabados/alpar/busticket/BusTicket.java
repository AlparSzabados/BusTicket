package buyticket.szabados.alpar.busticket;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BusTicket extends AppCompatActivity {
    private static final SmsManager SMS_MANAGER = SmsManager.getDefault();
    private final int MY_PERMISSION_REQUEST_SEND_SMS = 1;

    private final long _30_minutes = 1_800_000;
    private final long _40_minutes = 2_400_000;
    private final long _60_minutes = 3_600_000;
    private final long _80_minutes = 4_800_000;
    private final long _24_hours = 86_400_000;

    private EditText busNumber;
    private TextView chronometer;
    private String alertMessage;
    private String message;
    private long timerDuration;
    private CountDownTimer countDownTimer;

    // TODO parse CTPCJ site to check fairs
    // TODO Create ENUMS
    // TODO add timer
    // TODO disable the first check if an automated exists
    // TODO check is ongoing ticket is still valid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_ticket);

        busNumber = (EditText) findViewById(R.id.bus);
        chronometer = (TextView) findViewById(R.id.chronometer);
        checkPermission();
    }

    private void createCountdownTimer(long start, long countdownUnit) {
        countDownTimer = new CountDownTimer(start, countdownUnit) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                chronometer.setText(hours + ":" + minutes + ":" + seconds);
            }

            public void onFinish() {
                chronometer.setText("");
            }
        }.start();
    }


    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SEND_SMS);
            }
        }
    }

    public void onClick(final View view) {
        setup(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(alertMessage);
        builder.setCancelable(true);

        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!chronometer.toString().isEmpty() && countDownTimer != null) countDownTimer.cancel();
                        createCountdownTimer(timerDuration, 1000);
                        sendMessage();
                        clearMessage();
                    }
                });

        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.create().show();
    }

    private void sendMessage() {
        String phoneAddress = "7479"; //Emulator Phone number

        if (!message.isEmpty()) {
            SMS_MANAGER.sendTextMessage(phoneAddress, null, message, null, null);
            getToast(getString(R.string.messageSent)).show();
        } else {
            getToast(getString(R.string.messageNotSent)).show();
        }
    }

    private Toast getToast(String text) {
        return Toast.makeText(this, text, Toast.LENGTH_SHORT);
    }

    private void clearMessage() {
        busNumber.setText("");
    }

    private void setup(View view) {
        String sendMessagePromptText = getString(R.string.sendMessagePrompt);

        if (view.getId() == R.id.normalTicket) message = busNumber.getText().toString();
        else message = ((Button) view).getText().toString();

        switch (view.getId()) {
            case R.id.normalTicket:
                alertMessage = sendMessagePromptText + getString(R.string.costNormalTicket);
                timerDuration = _30_minutes;
                break;
            case R.id.T:
                alertMessage = sendMessagePromptText + getString(R.string.costT);
                timerDuration = _60_minutes;
                break;
            case R.id.A:
                alertMessage = sendMessagePromptText + getString(R.string.costA);
                timerDuration = _24_hours;
                break;
            case R.id.M40:
                alertMessage = sendMessagePromptText + getString(R.string.costM40);
                timerDuration = _40_minutes;
                break;
            case R.id.M60:
                alertMessage = sendMessagePromptText + getString(R.string.costM60);
                timerDuration = _60_minutes;
                break;
            case R.id.M80:
                alertMessage = sendMessagePromptText + getString(R.string.costM80);
                timerDuration = _80_minutes;
                break;
            case R.id.MA:
                alertMessage = sendMessagePromptText + getString(R.string.costMA);
                timerDuration = _24_hours;
                break;
        }
    }
}
