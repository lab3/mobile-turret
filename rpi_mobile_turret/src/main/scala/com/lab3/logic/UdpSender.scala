package com.lab3.logic

import java.net.{DatagramPacket, DatagramSocket, InetAddress}

class UdpSender(host: String, port: Int) {
   private[this] val ip = InetAddress.getByName(host)
   private[this] val socket = new DatagramSocket

   socket.connect(ip, port)

   def send(data: Array[Byte]): Unit = {
      send(data, data.length)
   }

   def send(data: Array[Byte], length: Int): Unit = {
      if (length < 65500) // This is close to the underlying IPv4 limit
         socket.send(new DatagramPacket(data, length))
   }

   def close(): Unit = {
      if (!socket.isClosed) {
         socket.close()
      }
   }
}