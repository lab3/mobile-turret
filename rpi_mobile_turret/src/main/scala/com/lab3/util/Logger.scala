package com.lab3.util

import javax.inject.{Inject, Singleton}

import com.fasterxml.jackson.databind.ObjectMapper
import org.joda.time.DateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class Scribe @Inject()(json: ObjectMapper) {
   private[this] val log: Logger = LoggerFactory.getLogger("mobile-turret")

   def info(cat: String, msg: String) = {
      log.info(json.writeValueAsString(LogMessage(DateTime.now().toString(), cat, msg)))
   }
}

case class LogMessage(
   ts: String,
   cat: String,
   msg: String
)