package com.lab3.webserver

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter

object ServerMain extends Server

class Server extends HttpServer {

   override protected def defaultFinatraHttpPort: String = ":8080"

   override protected def defaultHttpServerName: String = "mobile-turret-server"

   override protected def configureHttp(router: HttpRouter): Unit = {
      router.add(new DefaultController)
   }
}
