package akka.io.aeron

import akka.actor.Extension
import java.util.concurrent.atomic.AtomicLong
import akka.actor.ExtendedActorSystem
import akka.actor.Props

class AeronImpl(system : ExtendedActorSystem) extends Extension {
 
  //Since this Extension is a shared instance
  // per ActorSystem we need to be thread-safe
  private val counter = new AtomicLong(0)
 
  //This is the operation this Extension provides
  def increment() = counter.incrementAndGet()
  
  val manager = system.actorOf(Props[AeronManager],"AeronManager")
  
 
} 