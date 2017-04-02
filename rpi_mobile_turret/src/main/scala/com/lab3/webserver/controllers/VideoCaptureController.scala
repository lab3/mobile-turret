package com.lab3.webserver.controllers

import java.io.{ByteArrayOutputStream, FileInputStream}

import com.lab3.util.Scribe
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import org.opencv.core.{Core, Mat, MatOfByte}
import org.opencv.highgui.{Highgui, VideoCapture}


class VideoCaptureController extends Controller {
   private[this] var cap: VideoCapture = null
   @volatile private[this] var frame: Mat = null

   init

   def init = {
      Scribe.info("VideoCaptureController.init", "dynamic load opencv core")
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
      cap = new VideoCapture(0)
      //cap.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, 320)
      //cap.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, 240)
   }

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
      val f = new Mat()
      cap.read(f)
      val b = new MatOfByte()
      Highgui.imencode(".jpg", f, b)
      val tmp = b.toArray

      response
         .ok
         .header("Content-Type", "image/jpeg")
         .body(tmp)
   }


   get("/startStream", name = "StartStream") { request: Request =>

   }
}