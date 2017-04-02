package com.lab3.webserver

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.twitter.finatra.json.modules.FinatraJacksonModule
import com.twitter.finatra.json.utils.CamelCasePropertyNamingStrategy

object CustomJacksonModule extends FinatraJacksonModule {

   override val serializationInclusion = Include.NON_NULL

   override val propertyNamingStrategy = CamelCasePropertyNamingStrategy

}