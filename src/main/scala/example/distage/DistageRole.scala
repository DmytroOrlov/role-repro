package example.distage

import distage.plugins.{PluginConfig, PluginDef}
import distage.{Lifecycle, Lifecycle3}
import izumi.distage.roles.RoleAppMain
import izumi.distage.roles.model.definition.RoleModuleDef
import izumi.distage.roles.model.{RoleDescriptor, RoleService}
import izumi.functional.bio.Applicative3
import izumi.fundamentals.platform.IzPlatform
import izumi.fundamentals.platform.cli.model.raw.RawEntrypointParams
import logstage.LogIO3
import zio.*

import scala.annotation.unused

trait TestApi[F[_, _, _]]

object TestPlugin extends PluginDef with RoleModuleDef {
  makeRole[TestRole[ZIO]]

  make[TestApi[ZIO]].fromValue(new TestApi[ZIO] {})
}

final class TestRole[F[-_, +_, +_]: Applicative3](
    @unused testApi: TestApi[F],
    log: LogIO3[F],
) extends RoleService[F[Any, Throwable, _]] {
  override def start(
      roleParameters: RawEntrypointParams,
      freeArgs: Vector[String],
  ): Lifecycle3[F, Any, Throwable, Unit] = {
    Lifecycle.liftF(log.info("Test role started!"))
  }
}

object TestRole extends RoleDescriptor {
  final val id = "test"
}

object TestLauncher extends RoleAppMain.LauncherBIO2[IO] {
  def pluginConfig =
    if (IzPlatform.isGraalNativeImage) PluginConfig.const(List(TestPlugin))
    else PluginConfig.cached(pluginsPackage = "example.distage")
}
