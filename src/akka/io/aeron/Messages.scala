package akka.io.aeron

import java.net.InetSocketAddress
import akka.actor.ActorRef

//Connecting from client side
case class Connect(address: InetSocketAddress)

//
case class Connected(remote: InetSocketAddress, local: InetSocketAddress)

//
case class ConnectFailure()

//Request to setup a listener for incoming connections
case class Bind(server: ActorRef, address: InetSocketAddress)

//
case class Bound()

case class BindFailure()

case class Failure()