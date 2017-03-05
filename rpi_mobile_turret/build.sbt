name := "mobile-turret-server"
organization := "com.lab3"
version := "1.0"
scalaVersion := "2.11.8"
shellPrompt := { state => scala.Console.YELLOW + "[" + scala.Console.CYAN + Project.extract(state).currentProject.id + scala.Console.YELLOW + "]" + scala.Console.RED + " $ " + scala.Console.RESET }

resolvers := Seq(
   "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
   Classpaths.typesafeReleases
)

val dispatch = "net.databinder.dispatch" %% "dispatch-core" % "0.11.2"
val guice = "net.codingwell" %% "scala-guice" % "4.1.0"
val joda = "joda-time" % "joda-time" % "2.9.4"
val slf4jNop = "org.slf4j" % "slf4j-nop" % "1.6.4"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.0" % "test"
val finatra = "com.twitter" %% "finatra-http" % "2.8.0"
val pi4j_core = "com.pi4j" % "pi4j-core" % "1.1"

libraryDependencies ++= Seq(
   dispatch,
   guice,
   joda,
   slf4jNop,
   scalaTest,
   finatra,
   pi4j_core
)

enablePlugins(JavaServerAppPackaging)