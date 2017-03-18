package com.lab3.arduino

//object SerialMessengerTest {
//   def main(args: Array[String]): Unit = {
//      val sm = new SerialMessenger()
//      var last = System.currentTimeMillis()
//      var cnt = 0
//
//      println("Started:" + sm.isOpen())
//
//      while (true) {
//         if (sm.readyToSend) {
//            sm.sendMessage(new MotorControlMessageAbsolute((-127).toByte, 127.toByte))
//            cnt += 1
//
//            if(cnt % 2000 == 0) {
//               print(s"RPS: ${2000.toDouble / ((System.currentTimeMillis() - last).toDouble / 1000)}\r")
//               last = System.currentTimeMillis()
//            }
//         } else {
//            Thread.sleep(1)
//         }
//      }
//   }
//}
