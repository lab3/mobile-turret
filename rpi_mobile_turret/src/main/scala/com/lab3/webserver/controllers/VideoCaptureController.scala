package com.lab3.webserver.controllers

import java.io.{ByteArrayOutputStream, FileInputStream}

import com.lab3.logic.VideoSender
import com.lab3.util.Scribe
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import org.opencv.core.{Core, Mat, MatOfByte}
import org.opencv.highgui.{Highgui, VideoCapture}


class VideoCaptureController extends Controller {

   private def getImageBytes = {
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
         .body(getImageBytes)
   }

   get("/captureImage", name = "captureImage") { request: Request =>
      val f = VideoSender.currentFrame
      val b = new MatOfByte()
      Highgui.imencode(".jpg", f, b)
      val tmp = b.toArray

      response
         .ok
         .header("Content-Type", "image/jpeg")
         .body(tmp)
   }


   get("/startStream", name = "startStream") { request: Request =>
      VideoSender.start(System.currentTimeMillis().toString)
   }

   get("/stopStream", name = "stopStream") { request: Request =>
      VideoSender.stop
   }

}