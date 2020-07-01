package load.Objects
package load.default
import io.gatling.core.feeder.RecordSeqFeederBuilder
import io.gatling.core.Predef._
import io.gatling.core.feeder.RecordSeqFeederBuilder
import io.gatling.core.structure.ChainBuilder
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.Predef._
import io.gatling.core.scenario.Simulation
import scala.util.Random
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

import scala.language.postfixOps
import io.gatling.jdbc.Predef._

import scala.concurrent.duration._

object ReserveFlight {

  //Headers
  val UpgradeInsecureRequestsHeaders = Map("Upgrade-Insecure-Requests" -> "1")

  val AcceptHeaders = Map("Accept" -> "image/webp,*/*")

  val OriginHeaders = Map(
    "Origin" -> "https://blazedemo.com",
    "Upgrade-Insecure-Requests" -> "1")
  //Feeders
  val DepartureCity = csv("DepartureCity.csv").random
  val DestinationCity = csv("DestinationCity.csv").shuffle
  val CardType = csv("CardType.csv").random
  val Name =("Qwaresta")
val ThinkTime = 10

  val reserveflight =exec(
    http("StartPage")
    .get("/")
    .headers(UpgradeInsecureRequestsHeaders)
    .resources(http("Favicon")
      .get("/favicon.ico")
      .headers(AcceptHeaders))
       .check(status.is(200))
  )
    .pause(ThinkTime)
     feed(DepartureCity)
     feed(DestinationCity)
    .exec(
      http("Reserve")
      .post("/reserve.php")
      .headers(OriginHeaders)
      .formParam("fromPort", DepartureCity)
      .formParam("toPort", DestinationCity)
        .check(status.is(200))
        .check(regex("""value="(\d)" name="flight""").saveAs("flight"))
        .check(regex("""value="(\d...)" name="price""").saveAs("price"))
        .check(regex("""value="(.*)" name="airline""").saveAs("airline"))
    )
    .pause(ThinkTime)
    .exec(
      http("Purchase")
      .post("/purchase.php")
      .headers(OriginHeaders)
      .formParam("flight", "${flight}")
      .formParam("price", "${price}")
      .formParam("airline", "${airline}")
      .formParam("fromPort", DepartureCity)
      .formParam("toPort", DestinationCity)
        .check(status.is(200))
    )
    .pause(ThinkTime)
     feed(CardType)
    .exec(
      http("Confirmation")
      .post("/confirmation.php")
      .check(status.is(200))
      .headers(OriginHeaders)
      .formParam("_token", "")
      .formParam("inputName", Name)
      .formParam("address", Name+"Street")
      .formParam("city", Name+"City")
      .formParam("state", Name+"State")
      .formParam("zipCode", "8888")
      .formParam("cardType", CardType)
      .formParam("creditCardNumber", "7777777")
      .formParam("creditCardMonth", "11")
      .formParam("creditCardYear", "2020")
      .formParam("nameOnCard", Name)
        .check(status.is(200))
    )
}
