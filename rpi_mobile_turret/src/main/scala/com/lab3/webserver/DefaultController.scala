package com.lab3.webserver

import java.util.concurrent.atomic.AtomicInteger
import com.lab3.domain.MotorControlRequestAbsolute
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller


class DefaultController extends Controller {

   var helloCount = new AtomicInteger()

   get("/hello") { request: Request =>
      println(s"""{"hello":${helloCount.incrementAndGet()}}""")
      "hello"
   }

   get("/mca/:L/:R") { request: Request =>
      val l = request.getIntParam("L", 0)
      val r = request.getIntParam("R", 0)

      println(l + ":" + r)
      "hello"
   }

   get("/terminate") { request: Request =>
      sys.exit(0)
   }
}