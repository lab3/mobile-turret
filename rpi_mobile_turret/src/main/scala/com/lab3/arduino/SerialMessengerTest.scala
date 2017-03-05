package com.lab3.arduino

object SerialMessengerTest {
   def main(args: Array[String]): Unit = {
      val smt = new SerialMessenger()

      while(true){
         if(smt.readyToSend) {
            smt.sendMessage(new MotorControlMessageAbsolute(4.toByte, 78.toByte))
         }else{
            Thread.sleep(10)
         }
      }
   }
}
