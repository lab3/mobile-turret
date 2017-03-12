#ifndef MESSAGEHANDLER_H
#define MESSAGEHANDLER_H

#include "Messages.h"

class MessageHandler {
public:

  MessageHandler() {}

  Message* readMessage();
  void     writeMessage();

private:

  MotorControlMessageAbsolute _motorControlMessageAbsolute;
  bool                         readHeader();
  MessageType                  readType();
  MotorControlMessageAbsolute* readMotorControlMessage();
};

#endif // ifndef MESSAGEHANDLER_H
