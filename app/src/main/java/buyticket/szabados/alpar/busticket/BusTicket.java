package buyticket.szabados.alpar.busticket;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static buyticket.szabados.alpar.busticket.Tickets.NORMAL_TICKET;

public class BusTicket extends AppCompatActivity {
    private static final SmsManager SMS_MANAGER = SmsManager.getDefault();
    private final int MY_PERMISSION_REQUEST_SEND_SMS = 1;
    private final String PHONE_ADDRESS = "7479"; //Emulator Phone number

    private EditText busNumber;
    private TextView timer;
    private String alertMessage;
    private String message;
    private long ticketDuration;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_ticket);

        busNumber = (EditText) findViewById(R.id.bus);
        timer = (TextView) findViewById(R.id.chronometer);
        checkPermission();
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
        setupPressedTicket(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(alertMessage);
        builder.setCancelable(true);

        startService(new Intent(this, TimerService.class));
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!timer.toString().isEmpty() && countDownTimer != null) countDownTimer.cancel();
                        createCountdownTimer(ticketDuration, 1000);
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

    private void createCountdownTimer(long start, long countdownUnit) {
        countDownTimer = new CountDownTimer(start, countdownUnit) {
            public void onTick(long millisUntilFinished) {
                timer.setText(getHour(millisUntilFinished) + ":" + getMinute(millisUntilFinished) + ":" + getSecond(millisUntilFinished));
            }

            public void onFinish() {
                timer.setText("");
            }
        }.start();
    }

    private void sendMessage() {
        if (!message.isEmpty()) {
            SMS_MANAGER.sendTextMessage(PHONE_ADDRESS, null, message, null, null);
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

    private void setupPressedTicket(View view) {
        String sendMessagePromptText = getString(R.string.sendMessagePrompt);

        for (Tickets t : Tickets.values()) {
            if (view.getId() == t.id && t == NORMAL_TICKET) {
                message = busNumber.getText().toString();
                alertMessage = sendMessagePromptText + t.cost;
                ticketDuration = t.ticketDuration;
            } else if (view.getId() == t.id) {
                message = t.message;
                alertMessage = sendMessagePromptText + t.cost;
                ticketDuration = t.ticketDuration;
            }
        }
    }

    private long getHour(long millisUntilFinished) {
        return (millisUntilFinished / 3600000) % 24;
    }

    private long getSecond(long millisUntilFinished) {
        return (millisUntilFinished / 1000) % 60;
    }

    private long getMinute(long millisUntilFinished) {
        return (millisUntilFinished / 60000) % 60;
    }
}
