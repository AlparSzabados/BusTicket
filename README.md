# BusTicket
Simple app for buying bus tickets in Cluj-Napoca. It automatically sends the right text message to `7479`. The message with your ticket will be received by your default text message app, not by this application.

By default the phone number that the app is sending messages to is `(650) 555-1212`, this is the default phone number of the emulator. If you want to buy tickets with this app (for real), you'll need to change the code in the `BusTicket.java` file to:

```java
private final String PHONE_ADDRESS = "7479";
```

### How it works:
- for normal tickets you can type in the line number of the bus you are on into the text field:
![text](https://cloud.githubusercontent.com/assets/15666137/26754473/2845f456-4884-11e7-9778-e6054883c80d.png)
- for other tickets, you can use the quick-list:
![quick](https://cloud.githubusercontent.com/assets/15666137/26754476/2f5bfa7e-4884-11e7-864f-fbfb88882b6b.png)
- after choosing your ticket you will be prompted by this dialog window:
![alert_1](https://cloud.githubusercontent.com/assets/15666137/26754448/b26bfe9c-4883-11e7-8d8a-8b54ee30a575.png)
- this is for additional price information and also for safety, in case your pocket decides to buy a ticket for you.

- you will also get another prompt from your phone by default (if you did not deactivate it):
![alert_2](https://cloud.githubusercontent.com/assets/15666137/26754450/b6caa222-4883-11e7-8823-3b0fb4e44218.png)
- after buying the ticket you wanted a timer will count down in the bottom left corner (depending on which ticket you have chosen) indicating the duration of your ticket.


### Bugs:
- if you close the app the timer will stop counting down and will display "00:00:00". I'm working on it to make it persistent

### TODOs

 - Write tests
 - Make timer work properly
 - Other great improvements

### Note: This is a work in progress!! You have been warned!