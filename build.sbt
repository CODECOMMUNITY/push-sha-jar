name := "push"

organization := "org.scala-lang.tools"

libraryDependencies += "net.databinder" % "dispatch_2.9.2" % "0.8.8"

scalaVersion := "2.9.2"

com.github.retronym.SbtOneJar.oneJarSettings

mainClass in oneJar := Some("Main")
