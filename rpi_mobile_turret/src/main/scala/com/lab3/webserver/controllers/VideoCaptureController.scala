package com.lab3.webserver.controllers

import java.io.{ByteArrayOutputStream, FileInputStream}
import javax.inject.Inject
import com.lab3.logic.VideoSender
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import org.opencv.core.MatOfByte
import org.opencv.highgui.Highgui

class VideoCaptureController @Inject()(videoSender: VideoSender) extends Controller {

   private def getSampleImageBytes = {
      val is = new FileInputStream("/apps/finatra_logo.png")
      val buffer = new ByteArrayOutputStream()

      var b = is.read()
      while (b != -1) {
         buffer.write(b.byteValue)
         b = is.read()
      }

      buffer.toByteArray
   }

   get("/sampleImage", name = "sampleImage") { request: Request =>
      response
         .ok
         .header("Content-Type", "image/jpeg")
         .body(getSampleImageBytes)
   }

   get("/captureImage", name = "captureImage") { request: Request =>
      val f = videoSender.currentFrame
      val b = new MatOfByte()
      Highgui.imencode(".jpg", f, b)
      val tmp = b.toArray

      response
         .ok
         .header("Content-Type", "image/jpeg")
         .body(tmp)
   }

   get("/startStream/:HOST/:PORT", name = "startStream") { request: Request =>
      videoSender.start(request.getParam("HOST"), request.getIntParam("PORT"))
   }

   get("/stopStream", name = "stopStream") { request: Request =>
      videoSender.stop
   }
}