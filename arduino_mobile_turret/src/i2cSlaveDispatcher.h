#ifndef I2CSLAVEDISPATCHER_H
#define I2CSLAVEDISPATCHER_H
#include "Arduino.h"
#include <Wire.h>

extern "C" {
  #include <stdlib.h>
  #include <string.h>
  #include <inttypes.h>
  #include "utility/twi.h"
}

#define SLAVE_ADDRESS 0x04
#define MESSAGE_SIZE 5

class i2cSlaveDispatcher {
public:

  i2cSlaveDispatcher() {}

  void setToScreen(void (*toScreen)(const String&));
  void begin() {
    Wire.begin(SLAVE_ADDRESS);
    Wire.onReceive(receiveData);
    Wire.onRequest(sendData);
  }

private:

  int count = 0;
  static void (*toScreen)(const String&);

  // These need to be static since that's what Wire.h expects
  static void receiveData();
  static void sendData();
};

void(*i2cSlaveDispatcher::toScreen)(const String&);

void i2cSlaveDispatcher::setToScreen(void (*function)(const String&))
{
  toScreen = function;
}

void i2cSlaveDispatcher::receiveData() {
  char buffer[MESSAGE_SIZE];

  toScreen("in");

  if (Wire.available() != MESSAGE_SIZE) {}
  Wire.readBytes(buffer, MESSAGE_SIZE);

  // header
  if ((buffer[0] == 'C') && (buffer[1] == 'A')) {
    if (buffer[2] == 0xFE) { // 254
      toScreen("motor");
    }
  }
}

void i2cSlaveDispatcher::sendData()    {
  // simple ack
  Wire.write('Z');
}

#endif // ifndef I2CMESSENGER_H
