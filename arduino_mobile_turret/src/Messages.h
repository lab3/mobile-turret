#ifndef MESSAGES_H
#define MESSAGES_H

enum MessageType {
        SimpleMessage = 0,
        MotorControlRelative = 255,
        MotorControlAbsolute = 254
};

struct Message {
public:
        Message(){
          _type = SimpleMessage;
        }

        MessageType GetMessageType() {
            return _type;
        };

        void SetMessageType(MessageType t) {
            _type = t;
        };

        virtual ~Message(){

        }

      protected:
        MessageType _type;
};

struct MotorControlMessageAbsolute: public Message {
public:
        char _L;
        char _R;
};

#endif
