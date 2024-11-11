import Programming1._

val Breeze = Seq(
    libraryDependencies += "org.scalanlp" %% "breeze" % "2.0.1-RC1",
  )

lazy val root = (project in file(".")).settings(Scala3)
