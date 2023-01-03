package example.distage

import distage.plugins.{PluginConfig, PluginDef}
import distage.Lifecycle
import izumi.distage.roles.RoleAppMain
import izumi.distage.roles.model.definition.RoleModuleDef
import izumi.distage.roles.model.{RoleDescriptor, RoleService}
import izumi.functional.bio.Applicative2
import izumi.fundamentals.platform.IzPlatform
import izumi.fundamentals.platform.cli.model.raw.{RawEntrypointParams, RawRoleParams}
import logstage.LogIO2
import zio.IO

import scala.annotation.unused

trait TestApi

object TestPlugin extends PluginDef with RoleModuleDef {
  makeRole[TestRole[IO]]

  make[TestApi].fromValue(new TestApi {})
}

final class TestRole[F[+_, +_]: Applicative2](
    @unused testApi: TestApi,
    log: LogIO2[F],
) extends RoleService[F[Throwable, _]] {
  override def start(
      roleParameters: RawEntrypointParams,
      freeArgs: Vector[String],
  ): Lifecycle[F[Throwable, _], Unit] = {
    Lifecycle.liftF(log.info("Test role started!"))
  }
}

object TestRole extends RoleDescriptor {
  final val id = "test"
}

object TestLauncher extends RoleAppMain.LauncherBIO2[IO] {
  override def pluginConfig: PluginConfig =
    if (IzPlatform.isGraalNativeImage) PluginConfig.const(List(TestPlugin))
    else PluginConfig.cached(pluginsPackage = "example.distage")
}
