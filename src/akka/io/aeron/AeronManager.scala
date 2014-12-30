package akka.io.aeron

import akka.actor.Actor

/**
 * This is the manager used for handling connection through Aeron.
 * The responsibility is similar to that of UDP and TCP managers.
 */
class AeronManager extends Actor {
  //Currently no buffer, or other optimizations are found here(compared to TCP), 
  //since the Aeron framework should know how to best do this
  //In future something might be added, but so far not.
  
  def receive: Actor.Receive = {
    //Connect to a new address as a client
    case Connect(address) => {
      
    }
  }
} 