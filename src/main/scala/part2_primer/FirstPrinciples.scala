package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.Future

object FirstPrinciples extends App {

  implicit val system = ActorSystem("FirstPrinciples")
  implicit val materializer =
    ActorMaterializer()

  val source = Source(1 to 10)
  val sink = Sink.foreach[Int](println)

  val graph = source.to(sink)

  val flow = Flow[Int].map(x => x + 1)
  val sourceWithFlow = source.via(flow)
  val sinkWithFlow = flow.to(sink)

  sourceWithFlow.to(sinkWithFlow).run()

  val finitesource = Source.single(1)
  val anotherFiniteSource = Source(1 to 10)
  val emptySource = Source.empty[Int]

  import scala.concurrent.ExecutionContext.Implicits.global
  val futureSource = Source.fromFuture(Future(42))

}
