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
    case Connect(address,port, connectType) => {

      connectType match {
        case ALL => {
          val ctx = new Aeron.Context().
            //Create connection handler
            newConnectionHandler(
              (channel: String, streamid: Int, sessionid: Int, sourceinfo: String) => newPongConnectionHandler(channel, streamid, sessionid, sourceinfo))

          //TODO: ignore fragmentassembler

          val aeron = Aeron.connect(ctx)
          val channel = "udp://"+address+":"+port
          val publication = aeron.addPublication(channel, streamid)
          

        }
        case PUBLISH => {

        }
        case SUBSCRIBE => {

        }
      }
    }
  }

  val channel = "udp://localhost:8089";
  val streamid = 1
  val PONG_CONNECTION_LATCH = new CountDownLatch(1)

  private def newPongConnectionHandler(channel: String, streamId: Int, sessionId: Int, sourceInfo: String) {
    if (channel.equals(channel) && streamid == streamId) {
      PONG_CONNECTION_LATCH.countDown();
    }
  }
} 