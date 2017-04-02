package com.lab3.logic

import java.net.{DatagramPacket, DatagramSocket, InetAddress}
import javax.inject.Inject

import com.lab3.util.Scribe

import scala.concurrent.Future

class UdpReceiver(port: Int) {
   private[this] val socket = new DatagramSocket(port)
   private[this] var run = true

   private[this] val data = new Array[Byte](65500)

   def listen() = {

      println("starting listener")
      var count = 0

      while (run) {
         val receivePacket = new DatagramPacket(data, data.length)
         socket.receive(receivePacket)
         count += 1
         println(s"$count:${receivePacket.getData()(0)}")
      }
   }

   def close(): Unit = {
      run = false
      if (!socket.isClosed) {
         socket.close()
      }
   }
}

object UdpReceiverTest {

   import scala.concurrent.ExecutionContext.Implicits.global

   def main(args: Array[String]): Unit = {
      val udpReceiver = new UdpReceiver(14000)

      Future {
         udpReceiver.listen()
      }

      scala.io.StdIn.readLine()
      udpReceiver.close()
   }
}