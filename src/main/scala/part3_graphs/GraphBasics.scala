package part3_graphs

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object GraphBasics extends App {
  implicit val system = ActorSystem("graph")
  implicit val materializer = ActorMaterializer()

}
