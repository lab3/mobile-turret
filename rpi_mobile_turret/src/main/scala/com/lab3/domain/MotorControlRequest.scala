package com.lab3.domain

import com.twitter.finatra.request.RouteParam

case class MotorControlRequestAbsolute(
   @RouteParam R: String,
   @RouteParam L: String
)

