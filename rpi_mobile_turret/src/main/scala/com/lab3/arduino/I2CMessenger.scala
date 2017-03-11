package com.lab3.arduino

import java.util.concurrent.atomic.AtomicInteger

import com.pi4j.io.i2c.{I2CBus, I2CDevice, I2CFactory}


class I2CMessenger {
   private[this] val serial = I2CFactory.getInstance(I2CBus.BUS_1)
   private[this] var device: I2CDevice = null
   init

   private def init: Unit = {
      device = serial.getDevice(0x04)
   }

   def sendTestMessage(): Unit = {
      this.synchronized {
         try {
            val wb = new Array[Byte](5)
            wb(0) = 'A'
            wb(1) = 'B'
            wb(2) = 'C'
            wb(3) = 'D'
            wb(4) = 'E'
            val rb = Array[Byte](5)
            val ret = device.read(wb, 0, 5, rb, 0, 5)
            //println("ret:" + ret + " rb(0):" + rb(0))
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

         if (cnt % mod == 0) {
            print(s"CNT: $cnt\tRPS: ${mod.toDouble / ((System.currentTimeMillis() - last).toDouble / 1000)}\r")
            last = System.currentTimeMillis()
         }

         //Thread.sleep(1000)
      }
   }
}