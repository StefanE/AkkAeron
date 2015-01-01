package akka.io.aeron

import akka.actor.Actor
import akka.io.aeron.communication.AeronInternals
import uk.co.real_logic.aeron.Aeron
import java.util.concurrent.CountDownLatch

/**
 * This is the manager used for handling connection through Aeron.
 * The responsibility is similar to that of UDP and TCP managers.
 */
class AeronManager extends Actor {
  //Currently no buffer, or other optimizations are found here(compared to TCP), 
  //since the Aeron framework should know how to best do this
  //In future something might be added, but so far not.

  //Starts network driver
  val internals = new AeronInternals

  def receive: Actor.Receive = {
    //Connect to a new address as a client
    case Connect(address, connectType) => {

      connectType match {
        case ALL => {
          val ctx = new Aeron.Context()
          
        }
        case PUBLISH => {

        }
        case SUBSCRIBE => {

        }
      }
    }
  }

  val channel = "channel1";
  val streamid = 1
  val PONG_CONNECTION_LATCH = new CountDownLatch(1)
  
  private def newPongConnectionHandler(channel: String, streamId: Int, sessionId: Int, sourceInfo: String) {
    if (channel.equals(channel) && streamid == streamId) {
      PONG_CONNECTION_LATCH.countDown();
    }
  }
} 