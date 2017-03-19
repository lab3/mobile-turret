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
  display.setCursor(3, 20);
  display.println(s);
}

void setup(void) {
  display.begin();
  toScreen("No control", YELLOW, BLACK);

  motors.setToScreen(toScreen);
  motors.begin();

  i2c.setToScreen(toScreen);
  i2c.begin();

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
