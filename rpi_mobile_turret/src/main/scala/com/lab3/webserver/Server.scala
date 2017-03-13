package com.lab3.webserver

import com.lab3.arduino.I2CMessenger
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import org.joda.time.DateTime

object ServerMain {
   val i2c = new I2CMessenger()
   val server = new Server()

   def main(args: Array[String]): Unit = {
      val motorFailsafe = new Thread(new Runnable {
         override def run() = motorFailSafe()
      })

      motorFailsafe.start()

      server.main(args)
   }


   def motorFailSafe(): Unit = {
      while (true) {
         if (System.currentTimeMillis() - i2c.lastMotorControl > 350) {
            i2c.sendMotorControlMessage(0, 0)
         }
         Thread.sleep(100)
      }
   }
}


class Server extends HttpServer {

   override protected def defaultFinatraHttpPort: String = ":8080"

   override protected def defaultHttpServerName: String = "mobile-turret-server"

   override protected def configureHttp(router: HttpRouter): Unit = {
      router.add(new DefaultController)
   }
}
