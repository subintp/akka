package playground

import akka.actor.ActorSystem

object PlayGround extends App {
  val akkaSystem = ActorSystem("HelloAkka")
  println(akkaSystem.name)
}
