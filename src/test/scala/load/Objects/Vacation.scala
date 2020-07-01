package load.Objects
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Vacation {

  //Headers
  val UpgradeInsecure = Map("Upgrade-Insecure-Requests" -> "1")

  val vacation =
    exec(
    http("Home")
      .get("/home")
      .headers(UpgradeInsecure)
       .check(status.is(200))
  )
    .pause(6, 10)
    .exec(
      http("Vacation")
      .get("/vacation.html")
        .headers(UpgradeInsecure)
       .check(status.is(200))
      )
}
