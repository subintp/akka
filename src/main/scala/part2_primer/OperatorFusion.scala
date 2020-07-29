package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object OperatorFusion extends App {
  implicit val system = ActorSystem("OperatorFusion")
  implicit val materializer = ActorMaterializer()

  val simpleSource = Source(1 to 1000)
  val simpleFlow = Flow[Int].map(_ + 1)
  val simpleFlow2 = Flow[Int].map(_ * 10)

  val simpleSink = Sink.foreach(println)

//  simpleSource.via(simpleFlow).via(simpleFlow2).to(simpleSink).run()

  val complexFlow = Flow[Int].map { x =>
    Thread.sleep(1000)
    x + 1
  }

  val complexFlow2 = Flow[Int].map { x =>
    Thread.sleep(1000)
    x * 10
  }

  // operator fusion
  //simpleSource.via(complexFlow).via(complexFlow2).to(simpleSink).run()

  simpleSource
    .via(complexFlow)
    .async
    .via(complexFlow2)
    .async
    .to(simpleSink)
    .run()
}
