package load.Objects

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

object Vacation {
  val vacation = exec(http("Home")
      .get("/home"))
    .pause(6)
    .exec(http("Vacation")
      .get("/vacation.html"))
    .pause(6)

}
