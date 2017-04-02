package com.lab3.logic

import com.lab3.util.Scribe
import com.lab3.webserver.ServerMain.motorFailSafe
import org.opencv.core.{Core, Mat}
import org.opencv.highgui.VideoCapture

import scala.concurrent.Future

object VideoSender {

   private[this] var cap: VideoCapture = null
   @volatile private[this] var frame: Mat = null
   @volatile private[this] var sender: SenderHelper = _

   init

   def init = {
      Scribe.info("VideoCaptureController.init", "dynamic load opencv core")
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
      cap = new VideoCapture(0)
   }

   def start(address: String) = {
      this.synchronized {
         if (sender != null) {
            sender.stop
         }

         sender = new SenderHelper(address)
         sender.start
      }
   }

   def stop = {
      this.synchronized {
         if (sender != null) {
            sender.stop
            sender = null
         }
      }
   }

   def currentFrame: Mat = {
      val f = new Mat()
      cap.read(f)
      frame = f
      frame
   }

   private class SenderHelper(address: String) {
      @volatile private[this] var run = true

      def stop = {
         run = false
      }

      def start: Unit = {
         new Thread(new Runnable {
            override def run(): Unit = doIt
         }).start()
      }

      def doIt: Unit = {
         while (run) {
            Scribe.info("SenderHelper.sending", address)
            Thread.sleep(1000)
         }
      }
   }

}
