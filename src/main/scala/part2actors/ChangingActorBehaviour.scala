package part2actors

import akka.actor.Actor

object ChangingActorBehaviour extends App {

  object FuzzyKid {
    case object KidAccept
    case object KidReject
    val HAPPY = "happy"
    val SAD = "sad"
  }

  class FuzzyKid extends Actor {
    import FuzzyKid._
    import Mom._

    var state = HAPPY

    override def receive: Receive = {
      case Food(VEGETABLE) => state = SAD
      case Food(CHOCOLATE) => state = HAPPY
      case Ask(message) =>
        if (state == HAPPY) sender() ! KidAccept
        else {
          sender() ! KidReject
        }
    }
  }

  object Mom {

    case class Food(food: String)

    case class Ask(message: String)

    val VEGETABLE = "veggies"
    val CHOCOLATE = "chocolate"
  }
}
