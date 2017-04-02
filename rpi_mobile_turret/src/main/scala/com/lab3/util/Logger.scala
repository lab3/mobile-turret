package com.lab3.util
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.lab3.webserver.CustomJacksonModule
import org.joda.time.DateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Scribe {
   private[this] val log: Logger = LoggerFactory.getLogger("mobile-turret")

   val mapper = new ObjectMapper()
   mapper.registerModule(DefaultScalaModule)

   def info(cat: String, msg: String) = {
      log.info(mapper.writeValueAsString(LogMessage(DateTime.now().toString(), cat, msg)))
   }
}

case class LogMessage(
   ts: String,
   cat: String,
   msg: String
)