package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object Backpressure extends App {
  implicit val system = ActorSystem("backpressure")
  implicit val materializer = ActorMaterializer()

  val fastSource = Source(1 to 1000)
  val slowSink = Sink.foreach[Int] { x =>
    Thread.sleep(1000)
    println(s"Sink $x")
  }

//  fastSource.async.to(slowSink).run()

  val simpleFlow = Flow[Int].map { x =>
    println(s"incoming: $x")
    x + 1
  }

  fastSource.async.via(simpleFlow).async.to(slowSink).run()
}
