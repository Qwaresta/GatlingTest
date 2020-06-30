package load.Objects
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Vacation {
  val vacation = exec(
    http("Home")
      .get("/home")
       .check(status.is(200))
  )
    .pause(6)
    .exec(
      http("Vacation")
      .get("/vacation.html")
       .check(status.is(200))
      )
}
