package com.lab3.arduino

import java.util.concurrent.atomic.AtomicInteger

import com.pi4j.io.serial._

class SerialMessenger {
   private[this] val serial = SerialFactory.createInstance()
   private[this] var unAcked = new AtomicInteger()
   val maxUnacked = 5
   init

   private def init: Unit = {

      serial.addListener(new SerialDataEventListener {
         def dataReceived(event: SerialDataEvent): Unit = {
            onDataReceived(event)
         }
      })

      val config = new SerialConfig()
      config.device("/dev/ttyAMA0")
         .baud(Baud._115200)
         .dataBits(DataBits._8)
         .parity(Parity.NONE)
         .stopBits(StopBits._1)
         .flowControl(FlowControl.NONE)

      serial.open(config)
   }

   def sendMessage(m: Message): Unit = {
      try {
         if (unAcked.get() < maxUnacked) {
            m.WriteToStream(serial)
            unAcked.incrementAndGet()
         }
      }
      catch {
         case ex: Exception => println("ex1:" + ex.getMessage)
      }
   }

   def getUnackedCount: Int = {
      unAcked.get()
   }

   def readyToSend: Boolean ={
      unAcked.get() < maxUnacked
   }

   private def onDataReceived(event: SerialDataEvent): Unit = {
      try {
         unAcked.set(0)
      } catch {
         case ex: Exception => println("ex2:" + ex.getMessage)
      }
   }
}
