#include "Adafruit_Defaults.h"
#include "Adafruit_GFX.h"
#include "Adafruit_SSD1331.h"
#include "MessageHandler.h"
#include "MotorHandler.h"
#include <SPI.h>
#include <Wire.h>

#define SLAVE_ADDRESS 0x04

Adafruit_SSD1331 display  = Adafruit_SSD1331(cs, dc, rst);
MessageHandler   messages = MessageHandler();
MotorHandler     motors   = MotorHandler();

// void receiveData(int byteCount) {
//   ++count;
//
//   while (Wire.available()) {
//     number = Wire.read();
//   }
// }
//
// void sendData() {
//   Wire.write(b, 5);
// }

void toScreen(const String& s, uint16_t screenColor, uint16_t textColor) {
  display.fillScreen(screenColor);
  display.setTextColor(textColor);
  display.setCursor(0, 0);
  display.println(s);
}

void setup(void) {
  display.begin();
  motors.begin();
  toScreen("starting in 5", BLACK, GREEN);
  delay(5000);
  toScreen("25", BLACK, GREEN);
  motors.setLeft(50, true);
  delay(5000);
  motors.setLeft(50, false);
  delay(5000);
  motors.kill();
}

void loop() {}
