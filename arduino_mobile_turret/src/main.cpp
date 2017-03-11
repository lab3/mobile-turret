#include "Adafruit_Defaults.h"
#include "Adafruit_GFX.h"
#include "Adafruit_SSD1331.h"
#include "MessageHandler.h"
#include <SPI.h>

Adafruit_SSD1331 display = Adafruit_SSD1331(cs, dc, rst);
MessageHandler   handler = MessageHandler();

long count  = 0;
long count2 = 0;
long doAck  = 0;

int testl = 0;
int testr = 0;

void setup(void) {
  display.begin();
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
  display.fillScreen(GREEN);
  display.setCursor(0, 0);
  display.println("READY");
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

char b[5];

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

  while (Serial.available() >= 5) {
    Serial.readBytes(b, 5);

    if (++doAck == 7) {
      doAck = 0;
      Serial.write('Z');
    }

    if (++count % 30000 == 0) {
      display.fillScreen(BLACK);
      display.setCursor(0, 0);
      display.println(count);
    }
  }
}
