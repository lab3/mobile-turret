package com.lab3.webserver

import com.twitter.finatra.http.routing.HttpWarmup
import com.twitter.finatra.httpclient.RequestBuilder._
import com.twitter.inject.utils.Handler
import javax.inject.Inject

class CustomWarmupHandler @Inject()(
   httpWarmup: HttpWarmup)
   extends Handler {

   override def handle() = {
      httpWarmup.send(
         get("/hello"))
   }
}