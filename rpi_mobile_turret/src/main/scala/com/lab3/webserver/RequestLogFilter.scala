package com.lab3.webserver

import javax.inject.{Inject, Singleton}

import com.lab3.util.Scribe
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.finatra.http.contexts.RouteInfo
import com.twitter.util.Future

@Singleton
class RequestLogFilter[R <: Request] @Inject()(scribe: Scribe) extends SimpleFilter[R, Response] {

   override def apply(request: R, service: Service[R, Response]): Future[Response] = {
      val name = RouteInfo(request) match {
         case Some(ri) => ri.name
         case None => "ROUTE_NAME_MISSING:" + request.uri
      }
      scribe.info("endpoint." + name, request.remoteHost)
      service(request)
   }
}
