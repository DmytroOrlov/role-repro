fork := true

ThisBuild / scalaVersion := "3.2.1"
ThisBuild / scalacOptions ++= Seq(
  "-Xmax-inlines:64",
  "-Yretain-trees",
  "-language:3.2",
  "-Ykind-projector:underscores",
  "-no-indent",
)

val V = new {
  val catsEffect = "3.4.4"
  val zio = "1.0.14"
  val zioInteropCats = "3.2.9.1"
  val zioInteropReactivestreams = "1.3.12"
  val zioKafka = "0.17.7"
  val distage = "1.1.0-M17"
  val tapir = "1.2.4"
  val sttp = "3.8.6"
  val http4s = "0.23.13"
  val http4sServer = "0.23.16"
  val circe = "0.14.3"
  val avro = "1.11.1"
  val kafkaSchemaSerde = "7.3.1"
  val monixNewtypes = "0.2.3"

  val dataDog = "1.3.0"
  val zioOpentracing = "1.0.0"
  val jaeger = "1.8.1"
  val zipkin = "2.16.3"

  val scalacheck = "1.17.0"
}

val Deps = new {
  val catsEffect = "org.typelevel" %% "cats-effect" % V.catsEffect
  val zioInteropCats = "dev.zio" %% "zio-interop-cats" % V.zioInteropCats
  val zioInteropReactivestreams = "dev.zio" %% "zio-interop-reactivestreams" % V.zioInteropReactivestreams
  val zio = "dev.zio" %% "zio" % V.zio
  val zioStreams = "dev.zio" %% "zio-streams" % V.zio
  val zioKafka = "dev.zio" %% "zio-kafka" % V.zioKafka
  val distageFramework = "io.7mind.izumi" %% "distage-framework" % V.distage
  val distageFrameworkDocker = "io.7mind.izumi" %% "distage-framework-docker" % V.distage
  val distageTestkitScalatest = "io.7mind.izumi" %% "distage-testkit-scalatest" % V.distage
  val logstageAdapterSlf4J = "io.7mind.izumi" %% "logstage-adapter-slf4j" % V.distage
  val apacheAvro = "org.apache.avro" % "avro" % V.avro
  val kafkaSchemaSerializer = "io.confluent" % "kafka-schema-serializer" % V.kafkaSchemaSerde
  val kafkaAvroSerde = "io.confluent" % "kafka-streams-avro-serde" % V.kafkaSchemaSerde
  val monixNewtypes = "io.monix" %% "newtypes-core" % V.monixNewtypes
  val newtypesCirce = "io.monix" %% "newtypes-circe-v0-14" % V.monixNewtypes

  val tapirJsonCirce = "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % V.tapir
  val tapirHttp4sServer = "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % V.tapir
  val tapirOpenapiDocs = "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % V.tapir
  val tapirSwaggerUiHttp4s = "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % V.tapir
  val tapirSttpClient = "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % V.tapir
  val tapirMonixNewtype = "com.softwaremill.sttp.tapir" %% "tapir-monix-newtype" % V.tapir
  val sttpClientCirce = "com.softwaremill.sttp.client3" %% "circe" % V.sttp
  val circeGeneric = "io.circe" %% "circe-generic" % V.circe
  val circeLiteral = "io.circe" %% "circe-literal" % V.circe

  val asyncHttpClientBackendZio =
    "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio1" % V.sttp excludeAll ExclusionRule(organization =
      "dev.zio",
    )

  val http4sBlazeServer = "org.http4s" %% "http4s-blaze-server" % V.http4s
  val http4sDsl = "org.http4s" %% "http4s-dsl" % V.http4sServer
  val http4sServer = "org.http4s" %% "http4s-server" % V.http4sServer

  val zioOpentracing = "dev.zio" %% "zio-opentracing" % V.zioOpentracing
  val ddTraceApi = "com.datadoghq" % "dd-trace-api" % V.dataDog
  val jaegerZipkin = "io.jaegertracing" % "jaeger-zipkin" % V.jaeger
  val zipkinReporter = "io.zipkin.reporter2" % "zipkin-reporter" % V.zipkin
  val zipkinSenderOkhttp3 = "io.zipkin.reporter2" % "zipkin-sender-okhttp3" % V.zipkin

  val scalacheck = "org.scalacheck" %% "scalacheck" % V.scalacheck
  val scalatestScalacheck = "org.scalatestplus" %% "scalacheck-1-16" % "3.2.14.0"
}

lazy val `my-project` = (project in file("."))
  .settings(
    resolvers ++= Seq(
      "Confluent Maven Repository" at "https://packages.confluent.io/maven/",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    ),
    libraryDependencies ++= Seq(
      Deps.catsEffect,
      Deps.zio,
      Deps.zioInteropCats,
      Deps.zioInteropReactivestreams,
      Deps.logstageAdapterSlf4J,
      Deps.distageFramework,
      Deps.distageFrameworkDocker,
      Deps.zioStreams,
      Deps.monixNewtypes,
      Deps.newtypesCirce,
      Deps.zioKafka,
      Deps.kafkaSchemaSerializer,
      Deps.kafkaAvroSerde,
      Deps.distageTestkitScalatest % Test,
      Deps.scalatestScalacheck % Test,
      Deps.scalacheck % Test,
      Deps.apacheAvro,
      Deps.asyncHttpClientBackendZio,
      Deps.tapirJsonCirce,
      Deps.tapirHttp4sServer,
      Deps.tapirOpenapiDocs,
      Deps.tapirSwaggerUiHttp4s,
      Deps.tapirSttpClient,
      Deps.tapirMonixNewtype,
      Deps.sttpClientCirce % Test,
      Deps.circeGeneric,
      Deps.circeLiteral,
      Deps.http4sBlazeServer,
      Deps.http4sDsl,
      Deps.http4sServer,
      Deps.zioOpentracing,
      Deps.ddTraceApi,
      Deps.jaegerZipkin,
      Deps.zipkinReporter,
      Deps.zipkinSenderOkhttp3,
    ),
    scalacOptions ++= Seq(
      s"-Xmacro-settings:product-name=${name.value}",
      s"-Xmacro-settings:product-version=${version.value}",
      s"-Xmacro-settings:product-group=${organization.value}",
      s"-Xmacro-settings:scala-version=${scalaVersion.value}",
      s"-Xmacro-settings:scala-versions=${crossScalaVersions.value.mkString(":")}",
      s"-Xmacro-settings:sbt-version=${sbtVersion.value}",
      s"-Xmacro-settings:git-repo-clean=${git.gitUncommittedChanges.value}",
      s"-Xmacro-settings:git-branch=${git.gitCurrentBranch.value}",
      s"-Xmacro-settings:git-described-version=${git.gitDescribedVersion.value.getOrElse("")}",
      s"-Xmacro-settings:git-head-commit=${git.gitHeadCommit.value.getOrElse("")}",
    ),
  )
