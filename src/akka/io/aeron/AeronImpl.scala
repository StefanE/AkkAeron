package akka.io.aeron

import akka.actor.Extension
import java.util.concurrent.atomic.AtomicLong

class AeronImpl extends Extension {
 
  //Since this Extension is a shared instance
  // per ActorSystem we need to be thread-safe
  private val counter = new AtomicLong(0)
 
  //This is the operation this Extension provides
  def increment() = counter.incrementAndGet()
  
} 