package akka.io.aeron

import akka.actor.Actor

/**
 * Represents a connection in Aeron context this would be either subscribe, 
 * publish or both depending on the configuration.
 */
class AeronConnection(connectType : ConnectType) extends Actor {
  
  
  def receive: Actor.Receive = {
    ???
  }
  
  /**
   * Setup behavior based on constructor arguments
   */
  override def preStart() {
    connectType match {
      case ALL => context.become(subscribe)
      case PUBLISH => context.become(subscribe)
      case SUBSCRIBE => context.become(subscribe)
    }
  }
  
  
  def all: Actor.Receive = {
    ???
  }
  
  def subscribe: Actor.Receive = {
    ???
  }
  
  def publish: Actor.Receive = {
    ???
  }
}