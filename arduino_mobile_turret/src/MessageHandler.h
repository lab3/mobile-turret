#ifndef MESSAGEHANDLER_H
#define MESSAGEHANDLER_H

#include "Messages.h"

class MessageHandler {
public:
        const int HEADER_SIZE = 3;
        MessageHandler() {}
        Message *readMessage();
        void writeMessage();
private:
        MotorControlMessageAbsolute _motorControlMessageAbsolute;
        bool readHeader();
        MessageType readType();
        MotorControlMessageAbsolute* readMotorControlMessage();
};

#endif
