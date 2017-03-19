#ifndef MOTORHANDLER_H
#define MOTORHANDLER_H
#include "Arduino.h"

#define L_PWM 5
#define L_ENA A0
#define L_DIR A1

#define R_PWM 6
#define R_ENA A2
#define R_DIR A3


class MotorHandler {
private:

  static int leftSpeed;
  static bool leftForward;
  static int rightSpeed;
  static bool rightForward;
  static void (*toScreen)(const String&,
                          uint16_t screenColor,
                          uint16_t textColor);

public:

  MotorHandler() {}

  void setToScreen(void (    *toScreen)(
                     const String&,
                     uint16_t screenColor,
                     uint16_t textColor));

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

void(*MotorHandler::toScreen)(const String&,
                              uint16_t screenColor,
                              uint16_t textColor);

void MotorHandler::setToScreen(void (    *function)(
                                 const String&,
                                 uint16_t screenColor,
                                 uint16_t textColor))
{
  toScreen = function;
}

void MotorHandler::begin() {
  // left motor pwm to off just to be safe
  pinMode(L_PWM, OUTPUT);
  analogWrite(L_PWM, 0);

  // enable left motor
  pinMode(L_ENA, OUTPUT);
  digitalWrite(L_ENA, HIGH);

  // set left motor direction forward
  pinMode(L_DIR, OUTPUT);
  digitalWrite(L_DIR, HIGH);
  delay(10);
  digitalWrite(L_DIR, LOW);

  // right motor pwm to off just to be safe
  pinMode(R_PWM, OUTPUT);
  analogWrite(R_PWM, 0);

  // enable right motor
  pinMode(R_ENA, OUTPUT);
  digitalWrite(R_ENA, HIGH);

  // set right motor direction forward
  pinMode(R_DIR, OUTPUT);
  digitalWrite(R_DIR, HIGH);
  delay(10);
  digitalWrite(R_DIR, LOW);
}

void MotorHandler::kill() {
  // left motor
  digitalWrite(L_ENA, LOW);
  analogWrite(L_PWM, 0);

  // left motor
  digitalWrite(R_ENA, LOW);
  analogWrite(R_PWM, 0);
}

void MotorHandler::enable() {
  // left motor
  digitalWrite(L_ENA, HIGH);

  // right motor
  digitalWrite(R_ENA, HIGH);
}

void MotorHandler::setSpeed(char left, char right) {
  // LEFT MOTOR
  if (left >= 0) {
    leftSpeed = left * 2; // convert to 0-255 scale
    analogWrite(L_PWM, leftSpeed);

    if (!leftForward) {
      leftForward = true;
      digitalWrite(L_DIR, LOW);
    }
  }
  else {
    leftSpeed = left * -2; // convert to 0-255 scale
    analogWrite(L_PWM, leftSpeed);

    if (leftForward) {
      leftForward = false;
      digitalWrite(L_DIR, HIGH);
    }
  }

  // RIGHT MOTOR
  if (right >= 0) {
    rightSpeed = right * 2; // convert to 0-255 scale
    analogWrite(R_PWM, rightSpeed);

    if (!rightForward) {
      rightForward = true;
      digitalWrite(R_DIR, LOW);
    }
  }
  else {
    rightSpeed = right * -2; // convert to 0-255 scale
    analogWrite(R_PWM, rightSpeed);

    if (rightForward) {
      rightForward = false;
      digitalWrite(R_DIR, HIGH);
    }
  }
}

#endif // ifndef MOTORHANDLER_H
