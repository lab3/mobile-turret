package com.lab3.arduino

object SerialMessengerTest {
   def main(args: Array[String]): Unit = {
      val sm = new SerialMessenger()
      var last = System.currentTimeMillis()
      var cnt = 0

      while (true) {
         if (sm.readyToSend) {
            sm.sendMessage(new MotorControlMessageAbsolute((-127).toByte, 127.toByte))

            if(cnt % 100 == 0) {
               print(s"RPS: ${100 / ((System.currentTimeMillis() - last).toDouble / 1000)}\r")
               last = System.currentTimeMillis()
            }
            cnt += 1

         } else {
            Thread.sleep(1)
         }
      }
   }
}
