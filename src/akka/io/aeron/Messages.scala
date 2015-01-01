package akka.io.aeron

import java.net.InetSocketAddress
import akka.actor.ActorRef

abstract sealed class ConnectType

case object ALL extends ConnectType
case object PUBLISH extends ConnectType
case object SUBSCRIBE extends ConnectType

//Connecting from client side
case class Connect(address: InetSocketAddress, connectType: ConnectType = ALL)

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