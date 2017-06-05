# BusTicket
Simple app for buying bus tickets in Cluj-Napoca. It automatically sends the right text message to `7479`. The message with your ticket will be received by your default text message app, not by this application.

By default the phone number that the app is sending messages to is `7479`, this is the phone number provided by `CTPCJ` (local transportation company) for buying tickets using SMS. If you want to change this number to something else, you'll need to change this line in the strings.xml file:

```xml
<string name="phoneNumber">7479</string>
```

### Permissions:
- you will be prompted to accept the `SEND_SMS` permission when you first use the application

### How it works:
- for normal tickets you can type in the line number of the bus you are on into the text field:

![text_small](https://cloud.githubusercontent.com/assets/15666137/26754718/9140d6a2-4888-11e7-9c2d-78b4cac6811b.png)
- for other tickets, you can use the quick-list:

![quick_small](https://cloud.githubusercontent.com/assets/15666137/26754726/b13daa48-4888-11e7-9b46-22cdf3c587f1.png)
- after choosing your ticket you will be prompted by this dialog window:

![alert_1_small](https://cloud.githubusercontent.com/assets/15666137/26754730/e38a29ae-4888-11e7-8012-0c7187856a26.png)
- this is for additional price information and also for safety, in case your pocket decides to buy a ticket for you.

- you will also get another prompt from your phone by default (if you did not deactivate it):

![alert_2_small](https://cloud.githubusercontent.com/assets/15666137/26754731/e4a2af78-4888-11e7-9600-aea5aad4e262.png)
- after buying the ticket you wanted a timer will count down in the bottom left corner (depending on which ticket you have chosen) indicating the duration of your ticket.


### Bugs:
- if you close the app the timer will stop counting down and will display "00:00:00". I'm working on it to make it persistent

### Note1: This is a very early version, use it with caution! 
### Note2: I always welcome feedback :)