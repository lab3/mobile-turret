name := "mobile-turret-server"
organization := "com.lab3"
version := "1.0"
scalaVersion := "2.11.8"
shellPrompt := { state => scala.Console.YELLOW + "[" + scala.Console.CYAN + Project.extract(state).currentProject.id + scala.Console.YELLOW + "]" + scala.Console.RED + " $ " + scala.Console.RESET }

resolvers := Seq(
   "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
   Classpaths.typesafeReleases
)

libraryDependencies ++= Seq(
   "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
   "net.codingwell" %% "scala-guice" % "4.1.0",
   "joda-time" % "joda-time" % "2.9.4",
   "org.scalatest" %% "scalatest" % "3.0.0" % "test",
   "com.twitter" %% "finatra-http" % "2.8.0",
   "com.twitter" %% "finatra-httpclient" % "2.8.0",
   "com.pi4j" % "pi4j-core" % "1.1",
   "ch.qos.logback" % "logback-classic" % "1.2.2",
   "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.7.2",
   "com.typesafe" % "config" % "1.3.0"
)

enablePlugins(JavaServerAppPackaging)