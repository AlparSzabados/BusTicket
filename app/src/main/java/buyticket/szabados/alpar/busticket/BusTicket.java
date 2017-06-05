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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;

import static android.content.DialogInterface.OnClickListener;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

public class BusTicket extends AppCompatActivity {
    private static final SmsManager SMS_MANAGER = SmsManager.getDefault();
    private final int PERMISSION_REQUEST_SEND_SMS = 1;

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

    public void onClick(final View view) {
        setupPressedTicket(view);
        getDialog(alertMessage, onAcceptSend(), onCancelSend(), "Yes", "No");
    }

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                getDialog("You need to allow permission: SEND_SMS", onAccept(), null, "OK", "Cancel");
                return;
            }
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_SEND_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_SEND_SMS:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    getToast("SEND_SMS Permission Denied").show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getDialog(String message, OnClickListener yesListener, OnClickListener noListener, String affirmative, String negative) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(affirmative, yesListener)
                .setNegativeButton(negative, noListener)
                .create()
                .show();
    }

    private OnClickListener onAcceptSend() {
        return new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (!timer.toString().isEmpty() && countDownTimer != null)
                    countDownTimer.cancel();
                createCountdownTimer(ticketDuration, 1000);
                sendMessage();
                clearMessage();
            }
        };
    }

    private OnClickListener onCancelSend() {
        return new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        };
    }

    private OnClickListener onAccept() {
        return new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_SEND_SMS);
            }
        };
    }

    private void createCountdownTimer(long start, long countdownUnit) {
        countDownTimer = new CountDownTimer(start, countdownUnit) {
            public void onTick(long millisUntilFinished) {
                timer.setText(getTimer(millisUntilFinished));
            }

            public void onFinish() {
                timer.setText("");
            }
        }.start();
    }

    private String getTimer(long millisUntilFinished) {
        StringBuilder timer = new StringBuilder();
        Formatter formatter = new Formatter(timer);
        long hours = MILLISECONDS.toHours(millisUntilFinished);
        long minutes = MILLISECONDS.toMinutes(millisUntilFinished) - HOURS.toMinutes(hours);
        long seconds = MILLISECONDS.toSeconds(millisUntilFinished) - MINUTES.toSeconds(MILLISECONDS.toMinutes(millisUntilFinished));
        formatter.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timer.toString();
    }

    private void sendMessage() {
        if (!message.isEmpty()) {
            SMS_MANAGER.sendTextMessage(getString(R.string.phoneNumber), null, message, null, null);
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
        for (Tickets t : Tickets.values()) {
            if (view.getId() == t.id) {
                if (t.message == null) message = busNumber.getText().toString();
                else message = t.message;
                alertMessage = getString(R.string.sendMessagePrompt) + getString(R.string.totalCost) + " " + calculateCost(t.cost) + " " + getString(R.string.currency) + " " + getString(R.string.vTAText);
                ticketDuration = t.ticketDuration;
            }
        }
    }

    private String calculateCost(float cost) {
        float vTA = Float.parseFloat(getString(R.string.vTA));
        return new Formatter(new StringBuilder()).format("%.2f", (vTA * cost) + cost).toString();
    }
}
