name := """playDemo"""
organization := "playDemo"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += javaWs
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
)
libraryDependencies += "org.scalatestplus" %% "mockito-3-4" % "3.2.2.0" % "test"

