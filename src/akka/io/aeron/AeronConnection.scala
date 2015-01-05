package akka.io.aeron

import akka.actor.Actor
import uk.co.real_logic.aeron.Aeron
import akka.io.aeron.communication.AeronSetup
import java.nio.ByteBuffer
import uk.co.real_logic.agrona.concurrent.UnsafeBuffer
import uk.co.real_logic.aeron.Publication
import uk.co.real_logic.aeron.common.concurrent.logbuffer.Header
import uk.co.real_logic.agrona.DirectBuffer
import uk.co.real_logic.aeron.common.concurrent.logbuffer.DataHandler

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
    case _ => ()
  }

  def all: Actor.Receive = {
    case other: String => println("received:" + other)
    //TODO: indirection could also be improved here
    //    case Write(body) => {
    //      publication.offer(BUFFER, 0, body.getBytes().length);
    //    }
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
    setupContext()
    connectType match {
      case ALL => {
        context.become(all)
        createSubscriberHandler()
      }
      case PUBLISH => {
        context.become(subscribe)
      }
      case SUBSCRIBE => {
        context.become(subscribe)
        createSubscriberHandler()
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

    //    val aeron = Aeron.connect(ctx)
    channel = "udp://" + config.address + ":" + config.port
    //    publication = aeron.addPublication(channel, config.streamID)

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

        val streamId = config.streamID
        val datahandler: DataHandler =
          ((buffer: DirectBuffer, offset: Int, length: Int, header: Header) =>
            handleChannelMessage(streamId, buffer, offset, length, header))
        Thread.sleep(2000)
        val aeron = Aeron.connect(ctx)

        val subscription = aeron.addSubscription(channel, config.streamID, datahandler)
        while (true) {
          //TODO: some tuning / config could be done here
          subscription.poll(5);
        }

      }
    }
    thread.start()
  }

  private def handleChannelMessage(streamId: Int, buffer: DirectBuffer, offset: Int, length: Int, header: Header) {
    val data = new Array[Byte](length);
    buffer.getBytes(offset, data);

    val t= Array(streamId, header.sessionId(), length, offset, new String(data)).asInstanceOf[Array[AnyRef]]
    println(String.format(
      "message to stream %d from session %x (%d@%d) <<%s>>",  t : _*))

    self ! new String(data)
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