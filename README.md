# BusTicket
Simple app for buying bus tickets in Cluj-Napoca. It automatically sends the right text message to `7479`. The message with your ticket will be received by your default text message app, not by this application.

By default the phone number that the app is sending messages to is `7479`, this is the phone number provided by `CTPCJ` (local transportation company) for buying tickets using SMS. If you want to change this number to something else, you'll need to change this line in the `utils.xml` file:
VTA percent can also be changed from here:
```xml
<string name="phoneNumber">7479</string>
```

VTA percent can also be changed from the `utils.xml` file:
```xml
<string name="vTA">0.19</string>
```

### Permissions:
- you will be prompted to accept the `SEND_SMS` permission when you first use the application

### How it works:
- for normal tickets you can type in the line number of the bus you are on into the text field:

![text_small](https://cloud.githubusercontent.com/assets/15666137/26793488/da06a80e-4a26-11e7-8c5b-539dc6048120.png)
- for other tickets, you can use the quick-list:

![quick_small](https://cloud.githubusercontent.com/assets/15666137/26793491/db0c2454-4a26-11e7-988e-6ec5d0b5a3c7.png)
- after choosing your ticket you will be prompted by this dialog window:

![alert1_small](https://cloud.githubusercontent.com/assets/15666137/26793497/dde70fae-4a26-11e7-88cb-728197ded9d2.png)
- this is for additional price information and also for safety, in case your pocket decides to buy a ticket for you.

- you will also get another prompt from your phone by default (if you did not deactivate it):

![alert2_small](https://cloud.githubusercontent.com/assets/15666137/26793493/dc223388-4a26-11e7-8a47-e55a994386be.png)
- after buying the ticket you wanted a timer will count down in the bottom left corner (depending on which ticket you have chosen) indicating the duration of your ticket.


### Bugs:
- if you close the app the timer will stop counting down and will display "00:00:00". I'm working on it to make it persistent

### Note1: This is a very early version, use it with caution! 
### Note2: I always welcome feedback :)