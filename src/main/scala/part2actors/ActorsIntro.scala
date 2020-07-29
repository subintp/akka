package part2actors

import akka.actor.{Actor, ActorSystem, Props}

object ActorsIntro extends App {

  val actorSystem = ActorSystem("firstActorSystem")
  println(actorSystem.name)

  class WordCountActor extends Actor {
    var totalWords: Int = 0
    override def receive: Receive = {
      case message: String =>
        println(s"Message received: $message")
        totalWords += message.split("").length
      case _ => println("hello")
    }
  }
  val wordCounter = actorSystem.actorOf(Props[WordCountActor], "wordCountActor")
  wordCounter ! "Hello world"

}
