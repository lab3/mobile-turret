#ifndef MOTORHANDLER_H
#define MOTORHANDLER_H
#include "Arduino.h"

class MotorHandler {
private:

  static int leftSpeed;
  static bool leftForward;
  static int rightSpeed;
  static bool rightForward;

public:

  MotorHandler() {}

  static void setSpeed(char Left,
                       char Right);
  void        kill();
  void        enable();
  void        begin();
};

int  MotorHandler::leftSpeed    = 0;
bool MotorHandler::leftForward  = true;
int  MotorHandler::rightSpeed   = 0;
bool MotorHandler::rightForward = true;

void MotorHandler::begin() {
  // left motor pwm to off just to be safe
  pinMode(5, OUTPUT);
  analogWrite(5, 0);

  // enable left motor
  pinMode(A0, OUTPUT);
  digitalWrite(A0, HIGH);

  // set left motor direction forward
  pinMode(A1, OUTPUT);
  digitalWrite(A1, HIGH);
  delay(10);
  digitalWrite(A1, LOW);

  // TODO: right motor
}

void MotorHandler::kill() {
  // left motor
  digitalWrite(A0, LOW);

  // TODO: right motor
}

void MotorHandler::enable() {
  // left motor
  digitalWrite(A0, HIGH);

  // TODO: right motor
}

void MotorHandler::setSpeed(char left, char right) {
  // LEFT MOTOR
  if (left >= 0) {
    leftSpeed = left * 2; // convert to 0-255 scale

    if (!leftForward) {
      leftForward = true;
      digitalWrite(A1, LOW);
    }
  }
  else {
    leftSpeed = left * -2; // convert to 0-255 scale

    if (leftForward) {
      leftForward = false;
      digitalWrite(A1, HIGH);
    }
  }

  // TODO: right motor
}

#endif // ifndef MOTORHANDLER_H
