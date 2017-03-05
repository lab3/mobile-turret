package com.lab3

import com.pi4j.io.serial.Serial

object MessageType {
   val MotorControlMessageRelative: Byte = 255.toByte
   val MotorControlMessageAbsolute: Byte = 254.toByte
}

abstract trait Message {

   val buffer = new Array[Byte](3)
   //Header
   buffer(0) = 0xC
   buffer(1) = 0xA

   protected def GetMessageType: Byte

   def WriteToStream(serial: Serial) = {
      buffer(2) = GetMessageType
      serial.write(buffer)
      //serial.write(GetMessageType)
      println("write header and type")
   }

}

case class MotorControlMessageRelative(
   /**
     * THROTTLE
     * -128  reverse max
     * 0:    neutral
     * 128   forward max
     *
     * STEERING WHEEL
     * -128  left max
     * 0:    neutral
     * 128   right max
     */
   throttle: Byte = 0,
   steering: Byte = 0
) extends Message {
   def GetMessageType = MessageType.MotorControlMessageRelative

   override def WriteToStream(serial: Serial) = {
      super.WriteToStream(serial)
      serial.write(throttle, steering)
      println("xxxx")
   }
}

case class MotorControlMessageAbsolute(
   /**
     * LEFT/RIGHT WHEEL
     * -128  reverse max
     * 0:    neutral
     * 128   forward max
     */
   left: Byte = 0,
   right: Byte = 0
) extends Message {
   def GetMessageType = MessageType.MotorControlMessageAbsolute

   override def WriteToStream(serial: Serial) = {
      super.WriteToStream(serial)
      serial.write(left)
      serial.write(right)

      println("write finished")
   }
}
