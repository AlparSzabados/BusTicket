package buyticket.szabados.alpar.busticket;

import android.Manifest;
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
        sendMessage(view);
        clearMessage();

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
