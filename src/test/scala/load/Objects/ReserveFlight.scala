package load.Objects
package load.default
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

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
  val Name =("Qwaresta")


  val reserveflight =exec(
    http("request_0")
    .get("/")
    .headers(UpgradeInsecureRequestsHeaders)
    .resources(http("Favicon")
      .get("/favicon.ico")
      .headers(AcceptHeaders))
       .check(status.is(200))
  )
    .pause(38)
    .exec(
      http("Reserve")
      .post("/reserve.php")
      .headers(OriginHeaders)
      .formParam("fromPort", DepartureCity)
      .formParam("toPort", DestinationCity)
        .check(status.is(200))
    )
    .pause(16)
    .exec(
      http("Purchase")
      .post("/purchase.php")
      .headers(OriginHeaders)
      .formParam("flight", "234")
      .formParam("price", "432.98")
      .formParam("airline", "United Airlines")
      .formParam("fromPort", DepartureCity)
      .formParam("toPort", DestinationCity)
        .check(status.is(200))
    )
    .pause(49)
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
      .formParam("cardType", "visa")
      .formParam("creditCardNumber", "7777777")
      .formParam("creditCardMonth", "11")
      .formParam("creditCardYear", "2020")
      .formParam("nameOnCard", Name)
        .check(status.is(200))
    )
}
