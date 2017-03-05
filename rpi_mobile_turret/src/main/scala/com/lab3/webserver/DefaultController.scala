package com.lab3.webserver

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller


class DefaultController extends Controller {

   get("/hello") { request: Request =>
      "hello"
   }

   get("/terminate") { request: Request =>
      sys.exit(0)
   }
}