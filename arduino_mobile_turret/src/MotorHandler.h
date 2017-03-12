#ifndef MOTORHANDLER_H
#define MOTORHANDLER_H
#include "Arduino.h"

class MotorHandler {
private:

  int  _leftSpeed    = 0;
  bool _leftForward  = true;
  int  _rightSpeed   = 0;
  bool _rightForward = true;

public:

  MotorHandler() {
    _leftSpeed    = 0;
    _leftForward  = true;
    _rightSpeed   = 0;
    _rightForward = true;
  }

  void kill() {
    // left motor
    digitalWrite(A0, LOW);
  }

  void ebable() {
    // left motor
    digitalWrite(A0, HIGH);
  }

  void begin() {
    pinMode(A0, OUTPUT);
    digitalWrite(A0, HIGH);

    // set left motor direction forward
    pinMode(A1, OUTPUT);
    digitalWrite(A1, HIGH);
    delay(10);
    digitalWrite(A1, LOW);

    // left motor pwm
    pinMode(5, OUTPUT);
    analogWrite(5, 0);
  }

  void setLeftSpeed(int speed) {
    _leftSpeed = speed;
    analogWrite(5, _leftSpeed);
  }

  void setLeft(int speed, bool forward) {
    _leftSpeed = speed;
    analogWrite(5, _leftSpeed);

    if (_leftForward != forward) {
      _leftForward = forward;

      if (forward) {
        digitalWrite(A1, LOW);
      } else {
        digitalWrite(A1, HIGH);
      }
    }
  }
};
#endif // ifndef MOTORHANDLER_H
