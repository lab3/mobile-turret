#ifndef I2CSLAVEDISPATCHER_H
#define I2CSLAVEDISPATCHER_H
#include "Arduino.h"
#include "Messages.h"
#include "MotorHandler.h"
#include <Wire.h>

#define SLAVE_ADDRESS 0x04
#define MESSAGE_SIZE 5
#define ACK_SIZE 2

class i2cSlaveDispatcher {
private:

  static int count;
  static void (*toScreen)(const String&,
                          uint16_t screenColor,
                          uint16_t textColor);

  // These need to be static since that's what Wire.h expects
  static void receiveData(int size);
  static void sendData();

public:

  i2cSlaveDispatcher() {}

  void setToScreen(void (    *toScreen)(
                     const String&,
                     uint16_t screenColor,
                     uint16_t textColor));

  void setMotorControlHandler(void (*motorControl)(char, char));

  void begin() {
    Wire.begin(SLAVE_ADDRESS);
    Wire.onReceive(receiveData);
    Wire.onRequest(sendData);
  }
};

int i2cSlaveDispatcher::count = 0;

void(*i2cSlaveDispatcher::toScreen)(const String&,
                                    uint16_t screenColor,
                                    uint16_t textColor);

void i2cSlaveDispatcher::setToScreen(void (    *function)(
                                       const String&,
                                       uint16_t screenColor,
                                       uint16_t textColor))
{
  toScreen = function;
}

void i2cSlaveDispatcher::receiveData(int size) {
  char buffer[MESSAGE_SIZE];

  Wire.readBytes(buffer, MESSAGE_SIZE);

  if ((buffer[0] == 'C') && (buffer[1] == 'A')) {
    if (buffer[2] == MotorControlFailsafe) {
      MotorHandler::setSpeed(0, 0);
      count = 0;
      toScreen("Failsafe", RED, BLACK);
    } else if (buffer[2] == MotorControlAbsolute) {
      MotorHandler::setSpeed(buffer[3], buffer[4]);
      count++;
    }
  }

  if (count == 2) {
    toScreen("Connected", GREEN, BLACK);
  }
}

void i2cSlaveDispatcher::sendData()    {
  char buffer[ACK_SIZE];

  buffer[0] = 'Z';
  buffer[1] = 'X';
  Wire.write(buffer, ACK_SIZE);
}

#endif // ifndef I2CMESSENGER_H
