#include "Arduino.h"
#include "MessageHandler.h"
#include "Messages.h"

void      MessageHandler::writeMessage() {}

Message * MessageHandler::readMessage()  {
  if ((Serial.available() >= HEADER_SIZE) && readHeader()) {
    MessageType type = readType();

    if (type == SimpleMessage) {
      return NULL;
    } else if (type == MotorControlAbsolute) {
      MotorControlMessageAbsolute *m = readMotorControlMessage();
      return m;
    }
  }
  return NULL;
}

bool MessageHandler::readHeader()   {
  if (Serial.available() >= HEADER_SIZE) {
    if ((Serial.read() == 12) &&
        (Serial.read() == 10)) {
      return true;
    } else {
      return false;
    }
  }
  return false;
}

MessageType MessageHandler::readType() {
  return static_cast<MessageType>(Serial.read());
}

MotorControlMessageAbsolute * MessageHandler::readMotorControlMessage() {
  int l = Serial.read();
  int r = Serial.read();

  MotorControlMessageAbsolute *msg =
    (MotorControlMessageAbsolute *)malloc(sizeof(MotorControlMessageAbsolute));

  msg->SetMessageType(MotorControlAbsolute);
  msg->_L = l;
  msg->_R = r;
  return msg;
}
