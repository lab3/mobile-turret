#include "Adafruit_GFX.h"
#include "Adafruit_SSD1331.h"
#include "MessageHandler.h"
#include <SPI.h>

// You can use any (4 or) 5 pins
#define sclk 13
#define mosi 11 // sda on my chip
#define cs   10
#define rst  9
#define dc   8

// Color definitions
#define BLACK           0x0000
#define BLUE            0x001F
#define RED             0xF800
#define GREEN           0x07E0
#define CYAN            0x07FF
#define MAGENTA         0xF81F
#define YELLOW          0xFFE0
#define WHITE           0xFFFF

Adafruit_SSD1331 display = Adafruit_SSD1331(cs, dc, rst);
MessageHandler   handler = MessageHandler();

int  count      = 0;
int  doAck      = 0;
bool readSerial = false;
char itoaBuffer[33];
void setup(void) {
  display.begin();
  display.fillScreen(BLACK);
  Serial.begin(115200);
}

int testl = 0;
int testr = 0;

void loop() {
  // while (Serial.available()) {
  //   readSerial = true;
  //   Serial.read();
  //
  //   // display.print(itoa(Serial.read(), itoaBuffer, 10));
  //   // display.print(' ');
  // }
  //
  // if (readSerial) {
  //   count++;
  //
  //   if (count % 100 == 0) {
  //     display.fillScreen(BLACK);
  //     display.setCursor(0, 0);
  //     display.println("");
  //     display.print("cnt:");
  //     display.println(count);
  //   }
  //   readSerial = false;
  //   Serial.write(97);
  // }
  if (Serial.available() >= 5) {
    Message *m = handler.readMessage();

    if (m != NULL) {
      if (++doAck % 7 == 0) {
        Serial.write(97);
      }

      if (m->GetMessageType() == MotorControlAbsolute) {
        MotorControlMessageAbsolute *mcma = m;

        // if (count % 10 == 0) {
        // display.fillScreen(BLACK);
        // display.setCursor(0, 0);
        // display.print("type: ");
        // display.println(mcma->GetMessageType());
        // display.print((int)mcma->_L);
        // display.print(' ');
        // display.println((int)mcma->_R);
        // display.print("count:");
        // display.println(count);
        // }

        testl = (int)mcma->_L;
        testr = (int)mcma->_R;

        free(mcma);
        count++;

        // delay(1000);
      } else {
        display.fillScreen(BLACK);
        display.setCursor(0, 0);
        display.println("invalid message");
        display.println(m->GetMessageType());
      }
    } else {
      display.fillScreen(RED);
      display.setCursor(0, 0);
      display.println("null from readmessage");
    }
  } else {
    if (count % 200 == 0) {
      display.fillScreen(BLACK);
      display.setCursor(0, 0);
      display.print("count:");
      display.println(count);
      display.print("l:");
      display.println(testl);
      display.print("r:");
      display.println(testr);
    }
  }
}
