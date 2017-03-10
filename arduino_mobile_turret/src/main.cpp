#include "Adafruit_Defaults.h"
#include "Adafruit_GFX.h"
#include "Adafruit_SSD1331.h"
#include "MessageHandler.h"
#include <SPI.h>

Adafruit_SSD1331 display = Adafruit_SSD1331(cs, dc, rst);
MessageHandler   handler = MessageHandler();

long count = 0;
long doAck = 0;

int testl = 0;
int testr = 0;

void setup(void) {
  display.begin();
  display.fillScreen(BLACK);
  Serial.begin(115200);

  // // left motor on/off
  // pinMode(A0, OUTPUT);
  // digitalWrite(A0, HIGH);
  //
  // // left motor direction
  // pinMode(A1, OUTPUT);
  // digitalWrite(A1, HIGH);
  //
  // // left motor pwm
  // pinMode(5, OUTPUT);
  // analogWrite(5, 0);
}

int curPwm = 0;

void ramp_pwm(int newval) {
  if (curPwm < newval) {
    while (curPwm < newval) {
      analogWrite(5, ++curPwm);
      delay(100);
    }
  } else {
    curPwm = newval;
    analogWrite(5, curPwm);
  }
}

void loop() {
  // display.fillScreen(BLACK);
  // display.setCursor(0, 0);
  // display.println("sleeping for 5s");
  // delay(5000);
  //
  // display.fillScreen(BLACK);
  // display.setCursor(0, 0);
  // display.println("setting motor speed to 50/255 percent");
  // ramp_pwm(50);
  // display.println("sleeping for 10s");
  // delay(10000);
  // ramp_pwm(0);
  //
  // delay(2000);
  // digitalWrite(A1, LOW);
  // ramp_pwm(50);
  // delay(10000);

  if (Serial.available() >= 5) {
    Message *m = handler.readMessage();

    if (m != NULL) {
      if (++doAck % 7 == 0) {
        Serial.write(97);
      }

      if (m->GetMessageType() == MotorControlAbsolute) {
        MotorControlMessageAbsolute *mcma = m;

        testl = (int)mcma->_L;
        testr = (int)mcma->_R;

        free(mcma);
        count++;
      } else {
        display.fillScreen(BLACK);
        display.setCursor(0, 0);
        display.println("invalid message");
        display.println(m->GetMessageType());
      }
    } else {
      display.fillScreen(RED);
      display.setCursor(0, 0);
      display.println("null from readmessage");
    }
  } else {
    // if (count % 200 == 0) {
    //   display.fillScreen(BLACK);
    //   display.setCursor(0, 0);
    //   display.print("count:");
    //   display.println(count);
    //   display.print("l:");
    //   display.println(testl);
    //   display.print("r:");
    //   display.println(testr);
    // }
  }
}
