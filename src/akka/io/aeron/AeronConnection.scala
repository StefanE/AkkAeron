package akka.io.aeron

import akka.actor.Actor
import uk.co.real_logic.aeron.Aeron
import akka.io.aeron.communication.AeronSetup
import java.nio.ByteBuffer
import uk.co.real_logic.agrona.concurrent.UnsafeBuffer
import uk.co.real_logic.aeron.Publication
import uk.co.real_logic.aeron.common.concurrent.logbuffer.Header
import uk.co.real_logic.agrona.DirectBuffer

/**
 * Represents a connection in Aeron context this would be either subscribe,
 * publish or both depending on the configuration.
 */
class AeronConnection(connectType: ConnectType, config: AeronSetup) extends Actor {

  private[this] var ctx: Aeron.Context = null;

  private[this] val BUFFER = new UnsafeBuffer(ByteBuffer.allocateDirect(512));

  private[this] var publication: Publication = null;

  private[this] var channel = "";

  def receive: Actor.Receive = {
    ???
  }

  def all: Actor.Receive = {
    //TODO: indirection could also be improved here
    case Write(body) => {
      publication.offer(BUFFER, 0, body.getBytes().length);
    }
    case msg => {
      ???
    }
  }

  def subscribe: Actor.Receive = {
    ???
  }

  def publish: Actor.Receive = {
    ???
  }

  /**
   * Setup behavior based on constructor arguments
   */
  override def preStart() {
    connectType match {
      case ALL => {
        context.become(subscribe)
      }
      case PUBLISH => {
        context.become(subscribe)
      }
      case SUBSCRIBE => {
        context.become(subscribe)
      }
    }
  }

  def setupContext() {
    val ctx = new Aeron.Context().
      //Create connection handler
      newConnectionHandler(
        (channel: String, streamid: Int, sessionid: Int, sourceinfo: String) =>
          newConnectionHandler(channel, streamid, sessionid, sourceinfo))

    //TODO: ignore fragmentassembler

    val aeron = Aeron.connect(ctx)
    channel = "udp://" + config.address + ":" + config.port
    publication = aeron.addPublication(channel, config.streamID)

  }

  private def createSubscriberHandler() {
    //TODO: In later version, we could implement a thread pool which handles a set of channels
    //Create new thread for subscribing
    val thread = new Thread() {
      override def run() {
        val ctx = new Aeron.Context()
          .newConnectionHandler((channel: String, streamid: Int, sessionid: Int, sourceinfo: String) =>
            newConnectionHandler(channel, streamid, sessionid, sourceinfo)).
          inactiveConnectionHandler((channel: String, streamid: Int, sessionid: Int) =>
            onInactiveConnection(channel, streamid, sessionid))

        val datahandler = null

      }
    }

  }

  private def handleChannelMessage(streamID: Int, buffer: DirectBuffer, offset: Int, length: Int, header: Header) {
    val data = new Array[Byte](length);
    buffer.getBytes(offset, data);

    println(String.format(
      "message to stream %d from session %x (%d@%d) <<%s>>", 
      Array(streamID, header.sessionId(), length, offset, new String(data))));
  }

  private def newConnectionHandler(channel: String, streamId: Int, sessionId: Int, sourceInfo: String) {
    if (channel.equals(channel) && config.streamID == streamId) {
      println("New connection:" + sourceInfo)
    }
  }

  private def onInactiveConnection(channel: String, streamId: Int, sessionId: Int) {
    if (channel.equals(channel) && config.streamID == streamId) {
      println("Inactive channel:" + channel)
    }
  }

}