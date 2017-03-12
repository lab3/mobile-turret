#include "Adafruit_Defaults.h"
#include "Adafruit_GFX.h"
#include "Adafruit_SSD1331.h"
#include "MessageHandler.h"
#include "MotorHandler.h"
#include "i2cSlaveDispatcher.h"
#include <SPI.h>

Adafruit_SSD1331   display  = Adafruit_SSD1331(cs, dc, rst);
MessageHandler     messages = MessageHandler();
MotorHandler       motors   = MotorHandler();
i2cSlaveDispatcher i2c      = i2cSlaveDispatcher();

void toScreen(const String& s, uint16_t screenColor, uint16_t textColor) {
  display.fillScreen(screenColor);
  display.setTextColor(textColor);
  display.setCursor(0, 0);
  display.println(s);
}

void toScreen(const String& s) {
  display.fillScreen(BLACK);
  display.setTextColor(RED);
  display.setCursor(0, 0);
  display.println(s);
}

void getData() {
  char buffer[MESSAGE_SIZE];
  int  x = Wire.readBytes(buffer, MESSAGE_SIZE);

  display.fillScreen(BLACK);
  display.setCursor(0, 0);
  display.println(x);
  display.println(buffer[0]);
  display.println(buffer[1]);
  display.println(buffer[2]);
  display.println(buffer[3]);
  display.println(buffer[4]);

  if ((buffer[0] == 'C') && (buffer[1] == 'A')) {
    if (static_cast<MessageType>(buffer[2]) == MotorControlAbsolute) {
      display.println("cwg");
    } else {
      display.println(static_cast<MessageType>(buffer[2]));
      display.println("no dice");
    }
  }
}

void sendData() {
  char buffer[2];

  buffer[0] = 'Z';
  buffer[1] = 'Z';
  Wire.write(buffer, 2);
}

void setup(void) {
  display.begin();
  display.fillScreen(BLACK);
  display.println("Display init");

  // motors.begin();

  // i2c.setToScreen(toScreen);
  // i2c.begin();

  Wire.begin(SLAVE_ADDRESS);
  Wire.onReceive(getData);
  Wire.onRequest(sendData);

  // delay(5000);
  // toScreen("25", BLACK, GREEN);
  // motors.setLeft(50, true);
  // delay(5000);
  // motors.setLeft(50, false);
  // delay(5000);
  // motors.kill();
}

void loop() {
  delay(100);
}
