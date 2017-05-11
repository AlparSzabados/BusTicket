package buyticket.szabados.alpar.busticket;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BusTicket extends AppCompatActivity {
    public static final SmsManager SMS_MANAGER = SmsManager.getDefault();
    public final int MY_PERMISSION_REQUEST_SEND_SMS = 1;
    public EditText busNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_ticket);

        busNumber = (EditText) findViewById(R.id.bus);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getAlertMessage(view));
        builder.setCancelable(true);

        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendMessage(view);
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

    private String getAlertMessage(View view) {
        String sendMessagePromptText = getString(R.string.sendMessagePrompt);

        switch (view.getId()) {
            case R.id.normalTicket:
                return sendMessagePromptText + getString(R.string.costNormalTicket);
            case R.id.T:
                return sendMessagePromptText + getString(R.string.costT);
            case R.id.A:
                return sendMessagePromptText + getString(R.string.costA);
            case R.id.M40:
                return sendMessagePromptText + getString(R.string.costM40);
            case R.id.M60:
                return sendMessagePromptText + getString(R.string.costM60);
            case R.id.M80:
                return sendMessagePromptText + getString(R.string.costM80);
            case R.id.MA:
                return sendMessagePromptText + getString(R.string.costMA);
        }

        return getString(R.string.noTicket);
    }

    private void sendMessage(View view) {
        String message = getMessage(view);
        String phoneAddress = "6505551212"; //Emulator Phone number

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

    private String getMessage(View view) {
        if (view.getId() == R.id.normalTicket) return busNumber.getText().toString();
        else return ((Button) view).getText().toString();
    }
}
