package akka.io.aeron

import akka.actor.ExtensionId
import akka.actor.ExtensionIdProvider
import akka.actor.ExtendedActorSystem
import akka.actor.ActorSystem



object Aeron extends ExtensionId[AeronImpl] with ExtensionIdProvider {
  def createExtension(system: ExtendedActorSystem): AeronImpl = new AeronImpl

  def lookup(): ExtensionId[AeronImpl] = Aeron
  
  override def get(system: ActorSystem): AeronImpl = super.get(system)
}