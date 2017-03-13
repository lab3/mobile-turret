package com.lab3.arduino

import java.util.concurrent.atomic.AtomicInteger

import com.pi4j.io.i2c.{I2CBus, I2CDevice, I2CFactory}


class I2CMessenger {
   private[this] val serial = I2CFactory.getInstance(I2CBus.BUS_1)
   private[this] var device: I2CDevice = null
   init

   var lastMotorControl = System.currentTimeMillis()

   private def init: Unit = {
      device = serial.getDevice(0x04)
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
               ex.printStackTrace()
         }
      }
   }

   def sendTestMessage(): Unit = {
      this.synchronized {
         try {
            val wb = new Array[Byte](5)
            val rb = new Array[Byte](2)
            wb(0) = 'C'
            wb(1) = 'A'
            wb(2) = MessageType.MotorControlMessageAbsolute
            wb(3) = 'H'
            wb(4) = 'I'
            val ret = device.read(wb, 0, 5, rb, 0, 2)

            //TODO: what is ret used for?

            if (rb(0).toChar != 'Z' && rb(0).toChar != 'X') {
               //TODO: handle ack failure
            }
         }
         catch {
            case ex: Exception => println("ex1:" + ex.getMessage)
               ex.printStackTrace()
         }
      }
   }
}

object I2CMessengerTest {
   def main(args: Array[String]): Unit = {
      var last = System.currentTimeMillis()
      var cnt = 0
      val i2c = new I2CMessenger()
      val mod = 1000

      println("Entering main loop")

      while (true) {

         i2c.sendTestMessage()
         cnt += 1
         println(cnt + " messages sent")

         if (cnt % mod == 0) {
            print(s"CNT: $cnt\tRPS: ${mod.toDouble / ((System.currentTimeMillis() - last).toDouble / 1000)}\r")
            last = System.currentTimeMillis()
         }

         Thread.sleep(2000)
      }
   }
}