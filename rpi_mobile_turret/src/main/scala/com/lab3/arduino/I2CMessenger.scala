package com.lab3.arduino

import com.pi4j.io.i2c.{I2CBus, I2CDevice, I2CFactory}

class I2CMessenger {
   private[this] val serial = I2CFactory.getInstance(I2CBus.BUS_1)
   private[this] var device: I2CDevice = null
   var lastMotorControl: Long = System.currentTimeMillis()

   def init: Unit = {
      try {
         device = serial.getDevice(0x05)
      } catch {
         case ex: Throwable => println("I2C bus not available")
      }
   }

   def sendMotorControlMessage(left: Int, right: Int): Unit = {
      this.synchronized {
         try {
            val wb = new Array[Byte](5)
            val rb = new Array[Byte](2)
            wb(0) = 'C'
            wb(1) = 'A'
            wb(2) = MessageType.MotorControlMessageAbsolute
            wb(3) = left.toByte
            wb(4) = right.toByte
            val ret = device.read(wb, 0, 5, rb, 0, 2)

            lastMotorControl = System.currentTimeMillis()

            //TODO: what is ret used for?

            if (rb(0).toChar != 'Z' && rb(0).toChar != 'X') {
               //TODO: handle ack failure
            }
         }
         catch {
            case ex: Exception => println("ex1:" + ex.getMessage)
         }
      }
   }

   def sendMotorControlFailsafe(): Unit = {
      this.synchronized {
         try {
            val wb = new Array[Byte](5)
            val rb = new Array[Byte](2)
            wb(0) = 'C'
            wb(1) = 'A'
            wb(2) = MessageType.MotorControlMessageFailsafe
            wb(3) = 0
            wb(4) = 0
            val ret = device.read(wb, 0, 5, rb, 0, 2)

            lastMotorControl = System.currentTimeMillis()

            //TODO: what is ret used for?

            if (rb(0).toChar != 'Z' && rb(0).toChar != 'X') {
               //TODO: handle ack failure
            }
         }
         catch {
            case ex: Exception => println("ex1:" + ex.getMessage)
         }
      }
   }
}

//object I2CMessengerTest {
//   def main(args: Array[String]): Unit = {
//      var last = System.currentTimeMillis()
//      var cnt = 0
//      val i2c = new I2CMessenger()
//      val mod = 1000
//
//      println("Entering main loop")
//
//      while (true) {
//
//         i2c.sendMotorControlMessage(1, 1)
//         cnt += 1
//
//         if (cnt % mod == 0) {
//            print(s"CNT: $cnt\tRPS: ${mod.toDouble / ((System.currentTimeMillis() - last).toDouble / 1000)}\r")
//            last = System.currentTimeMillis()
//         }
//      }
//   }
//}