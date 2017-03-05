package com.lab3.arduino

object SerialMessengerTest {
   def main(args: Array[String]): Unit = {
      val sm = new SerialMessenger()

      while(true){
         if(sm.readyToSend) {
            sm.sendMessage(new MotorControlMessageAbsolute((-127).toByte, 127.toByte))
         }else{
            print(".")
            Thread.sleep(20)
         }
      }
   }
}
