package part2actors

import akka.actor.{Actor, ActorSystem, Props}
import part2actors.Counter.CounterActor.{Decrement, Increment, Show}

object Counter extends App {

  class CounterActor extends Actor {
    var counter = 0
    override def receive: Receive = {
      case Increment => counter += 1
      case Decrement => counter -= 1
      case Show      => println(counter)
    }
  }

  object CounterActor {
    case object Increment
    case object Decrement
    case object Show
  }

  val system = ActorSystem("counter")
  val counterActor = system.actorOf(Props[CounterActor], "counterActor")

  counterActor ! Increment
  counterActor ! Increment
  counterActor ! Decrement
  counterActor ! Decrement
  counterActor ! Decrement
  counterActor ! Show
}
