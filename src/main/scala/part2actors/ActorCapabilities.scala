package part2actors
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorCapabilities extends App {
  case class CustomMessage(content: String)
  case class SayHi(ref: ActorRef)

  class SimpleActor extends Actor {
    override def receive: Receive = {
      case "Hey Hi!"       => context.sender() ! s"Hello there ${context.self.path}"
      case message: String => println(s"recieved ${context.self.path} $message")
      case number: Int     => println(s"recieved number: $number")
      case custom: CustomMessage =>
        println(s"recieved custom : ${custom.content}")
      case sayHi: SayHi => sayHi.ref forward s"Hey Hi!"
    }
  }

  val system = ActorSystem("actorCapabilitiesDemo")
  val simpleActor = system.actorOf(Props[SimpleActor], "simpleActor")

  simpleActor ! "helloworld"
  simpleActor ! 12345
  simpleActor ! CustomMessage("hello custom message")

  val alice = system.actorOf(Props[SimpleActor], "alice")
  val bob = system.actorOf(Props[SimpleActor], "bob")

  alice ! SayHi(bob)
}
