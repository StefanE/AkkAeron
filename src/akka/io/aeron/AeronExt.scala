package akka.io.aeron

import akka.actor.ExtensionId
import akka.actor.ExtensionIdProvider
import akka.actor.ExtendedActorSystem
import akka.actor.ActorSystem

object AeronExt extends ExtensionId[AeronImpl] with ExtensionIdProvider {
  def createExtension(system: ExtendedActorSystem): AeronImpl = new AeronImpl(system)

  def lookup(): ExtensionId[AeronImpl] = AeronExt
  
  override def get(system: ActorSystem): AeronImpl = super.get(system)
}