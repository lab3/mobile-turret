package com.lab3.webserver.controllers

import java.util.concurrent.atomic.AtomicInteger

import com.lab3.util.Scribe
import com.lab3.webserver.ServerMain
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller


class DefaultController extends Controller {

   var helloCount = new AtomicInteger()
   var mcaCount = new AtomicInteger()
   val okJson = """{"result":"ok"}"""

   get("/hello", name = "hello") { request: Request =>
      okJson
   }

   get("/mca/:L/:R", name = "mca") { request: Request =>
      val c = mcaCount.incrementAndGet()
      val l = request.getIntParam("L", 0)
      val r = request.getIntParam("R", 0)
      ServerMain.i2c.sendMotorControlMessage(l, r)
      okJson
   }

   get("/terminate", name = "terminate") { request: Request =>
      sys.exit(0)
   }
}