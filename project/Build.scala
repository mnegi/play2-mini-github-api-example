import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object MinimalBuild extends Build with ConfigureScalaBuild{

  lazy val root = scalaMiniProject("com.example","my_app2","1.0").settings(
     //custom settings come here ie 
     //libraryDependencies += "organization" %% "group.id" % "version"
    )
  
}

trait ConfigureScalaBuild {

  
  lazy val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  
  lazy val typesafeSnapshot = "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/"
  
  val netty = Some("play.core.server.NettyServer") 

  def scalaMiniProject(org: String, name: String, buildVersion: String, baseFile: java.io.File = file(".")) = Project(id = name, base = baseFile, settings = Project.defaultSettings ++ assemblySettings).settings(
    version := buildVersion,
    organization := org,
    resolvers += typesafe,
    resolvers += typesafeSnapshot,
    logManager <<= extraLoggers(com.typesafe.util.Sbt.logger),
    libraryDependencies += "com.typesafe" %% "play-mini" % "2.0.1",
    mainClass in (Compile, run) := netty,
    mainClass in assembly := netty,
    ivyXML := <dependencies> <exclude org="org.springframework"/> </dependencies>
  )
}