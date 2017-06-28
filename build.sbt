lazy val slickJoins = (project in file("."))
  .settings (
    name := "SlickJoins",
    organization := "com.ami",
    version := "1.0.0",
    scalaVersion in ThisBuild := "2.11.8",
    scalacOptions in ThisBuild ++= Seq(
      "-feature",
      "-deprecation",
      "-Yno-adapted-args",
      "-Ywarn-value-discard",
      "-Ywarn-numeric-widen",
      "-Ywarn-dead-code",
      "-Xlint",
      "-Xfatal-warnings",
      "-unchecked",
      "-language:implicitConversions"
    ),

    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.2.0",
      "com.h2database"     %  "h2"    % "1.4.196"
    ),

    initialCommands := ""
  )
