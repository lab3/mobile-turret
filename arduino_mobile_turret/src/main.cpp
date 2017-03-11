#include "Adafruit_Defaults.h"
#include "Adafruit_GFX.h"
#include "Adafruit_SSD1331.h"
#include "MessageHandler.h"
#include <SPI.h>
#include <Wire.h>

#define SLAVE_ADDRESS 0x04

Adafruit_SSD1331 display = Adafruit_SSD1331(cs, dc, rst);
MessageHandler   handler = MessageHandler();
long count               = 0;
int  number              = 0;
char b[5];


void receiveData(int byteCount) {
  // display.println("receive");
  ++count;

  while (Wire.available()) {
    number = Wire.read();
  }
}

void sendData() {
  // display.println("send");
  Wire.write(b, 5);
}

void setup(void) {
  b[0] = 'Z';
  b[1] = 'Z';
  b[2] = 'Z';
  b[3] = 'Z';
  b[4] = 'Z';
  display.begin();
  Serial.begin(115200);
  Wire.begin(SLAVE_ADDRESS);
  Wire.onReceive(receiveData);
  Wire.onRequest(sendData);
  display.fillScreen(GREEN);
  display.setCursor(0, 0);
  display.println("READY");
}

int last = -1;
void loop() {
  if (count % 1000 == 0) {
    display.fillScreen(BLACK);
    display.setCursor(0, 0);
    display.print("count:");
    display.println(count);
  }
}
