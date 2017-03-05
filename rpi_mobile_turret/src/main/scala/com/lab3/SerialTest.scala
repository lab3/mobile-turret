package com.lab3

import java.util.concurrent.atomic.AtomicInteger

import com.pi4j.io.serial._

object SerialTest {
   val x = MotorControlMessageAbsolute()
   var okToWrite: Boolean = true
   var unacked = new AtomicInteger()

   def main(args: Array[String]): Unit = {
      val serial = SerialFactory.createInstance()

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

      println("starting send it 2s")
      Thread.sleep(2000)

      var byte1: Byte = 3.toByte
      var byte2: Byte = 4.toByte
      var count = 0

      while (true) {
         try {
            if (unacked.get() < 5) {
               count += 1
               var mcma = new MotorControlMessageAbsolute(byte1, byte2)
               mcma.WriteToStream(serial)
               unacked.incrementAndGet()
            }
         }
         catch {
            case ex: Exception => {
               println("ex1:" + ex.getMessage)
               ex.printStackTrace()
            }
         }

         //byte1 = (byte1 + 1).toByte
         //byte2 = (byte2 + -1).toByte

         println(count)

         Thread.sleep(5)
      }
   }

   def onDataReceived(event: SerialDataEvent): Unit = {
      try {
         unacked.set(0)
         println(s"onDataReceived:" + event.getAsciiString)
         println(s"onDataReceived:" + event.getHexByteString)
      } catch {
         case ex: Exception => println("ex2:" + ex.getMessage)
      }
   }
}

