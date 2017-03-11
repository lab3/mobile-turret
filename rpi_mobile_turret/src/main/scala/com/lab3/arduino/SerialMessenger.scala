package com.lab3.arduino

import java.util.concurrent.atomic.AtomicInteger

import com.pi4j.io.serial._

class SerialMessenger {
   private[this] val serial = SerialFactory.createInstance()
   private[this] var unAcked = new AtomicInteger(0)
   val maxUnacked = 7
   init

   private def init: Unit = {
      val config = new SerialConfig()
      config.device("/dev/ttyAMA0")
         .baud(Baud._115200)
         .dataBits(DataBits._8)
         .parity(Parity.NONE)
         .stopBits(StopBits._1)
         .flowControl(FlowControl.NONE)

      serial.open(config)
   }

   def isOpen() = {
      serial.isOpen
   }

   def sendMessage(m: Message): Unit = {
      this.synchronized {
         try {
            if (readyToSend) {
               m.WriteToStream(serial)
               unAcked.incrementAndGet()
            }
         }
         catch {
            case ex: Exception => println("ex1:" + ex.getMessage)
         }
      }
   }

   def getUnackedCount: Int = {
      unAcked.get()
   }

   def readyToSend: Boolean = {
      if (unAcked.get() > maxUnacked) {
         val x = serial.read(1)
         if (x.nonEmpty && x(0) == 'Z') {
            unAcked.set(0)
         }
      }

      unAcked.get() <= maxUnacked
   }
}
