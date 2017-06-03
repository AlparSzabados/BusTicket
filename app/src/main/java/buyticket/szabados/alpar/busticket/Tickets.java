package buyticket.szabados.alpar.busticket;

enum Tickets {
    NORMAL_TICKET(R.id.normalTicket, 1_800_000, "Total cost SMS: 0.5 Euro + TVA", "FILLED BY R.id.bus"),
    T(R.id.T, 3_600_000, "Total cost SMS: 0.9 Euro + TVA", "T"),
    A(R.id.A, 86_400_000, "Total cost SMS: 2.60 Euro + TVA", "A"),
    M40(R.id.M40, 2_400_000, "Total cost SMS: 0.9 Euro + TVA", "M40"),
    M60(R.id.M60, 3_600_000, "Total cost SMS: 1.35 Euro + TVA", "M60"),
    M80(R.id.M80, 4_800_000, "Total cost SMS: 1.8 Euro + TVA", "M80"),
    MA(R.id.MA, 86_400_000, "Total cost SMS: 4 Euro + TVA", "MA");

    public final long ticketDuration;
    public final String cost;
    public final int id;
    public final String message;

    Tickets(int id, long ticketDuration, String cost, String message) {
        this.ticketDuration = ticketDuration;
        this.cost = cost;
        this.id = id;
        this.message = message;
    }
}