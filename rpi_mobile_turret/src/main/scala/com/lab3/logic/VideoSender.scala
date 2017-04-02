package com.lab3.logic

import javax.inject.{Inject, Singleton}

import com.lab3.util.Scribe
import org.opencv.core.{Core, Mat}
import org.opencv.highgui.{Highgui, VideoCapture}

@Singleton
class VideoSender @Inject()(scribe: Scribe) {

   private[this] var cap: VideoCapture = null
   @volatile private[this] var frame: Mat = null
   @volatile private[this] var sender: SenderHelper = _

   init

   def init = {
      scribe.info("VideoCaptureController.init", "dynamic load opencv core")
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
      cap = new VideoCapture(0)
      cap.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, 320)
      cap.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, 240)

   }

   def start(host: String, port: Int) = {
      this.synchronized {
         if (sender != null) {
            sender.stop
         }

         sender = new SenderHelper(host, port)
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

   private class SenderHelper(host: String, port: Int) {
      @volatile private[this] var run = true
      private[this] val sender = new UdpSender(host, port)

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
            scribe.info("SenderHelper.sending", host + ":" + port)
            val tmp = List.fill(3)('Z').map(_.toByte).toArray
            sender.send(tmp)
            Thread.sleep(1000)
         }
      }
   }

}
