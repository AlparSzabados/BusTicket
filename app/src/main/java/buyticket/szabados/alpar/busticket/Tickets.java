package buyticket.szabados.alpar.busticket;

import android.app.Activity;
import android.widget.EditText;

import static java.security.AccessController.getContext;

enum Tickets {
    NORMAL_TICKET(R.id.normalTicket, 1_800_000, 0.5f, null),
    T(R.id.T, 3_600_000, 0.9f, "T"),
    A(R.id.A, 86_400_000, 2.60f , "A"),
    M40(R.id.M40, 2_400_000, 0.9f, "M40"),
    M60(R.id.M60, 3_600_000, 1.35f, "M60"),
    M80(R.id.M80, 4_800_000, 1.8f, "M80"),
    MA(R.id.MA, 86_400_000, 4f, "MA");

    public final long ticketDuration;
    public final float cost;
    public final int id;
    public final String message;

    Tickets(int id, long ticketDuration, float cost, String message) {
        this.ticketDuration = ticketDuration;
        this.cost = cost;
        this.id = id;
        this.message = message;
    }
}