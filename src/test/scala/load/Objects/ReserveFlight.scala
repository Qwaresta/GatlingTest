package load.Objects
package load.default

import io.gatling.core.Predef._
import io.gatling.http.Predef.http

import scala.concurrent.duration._

object ReserveFlight {
  val UpgradeInsecureRequestsHeaders = Map("Upgrade-Insecure-Requests" -> "1")

  val AcceptHeaders = Map("Accept" -> "image/webp,*/*")

  val OriginHeaders = Map(
    "Origin" -> "https://blazedemo.com",
    "Upgrade-Insecure-Requests" -> "1")
  val reserveflight =exec(http("request_0")
    .get("/")
    .headers(UpgradeInsecureRequestsHeaders)
    .resources(http("request_1")
      .get("/favicon.ico")
      .headers(AcceptHeaders)))
    .pause(38)
    .exec(http("request_2")
      .post("/reserve.php")
      .headers(OriginHeaders)
      .formParam("fromPort", "Paris")
      .formParam("toPort", "Buenos Aires"))
    .pause(16)
    .exec(http("request_3")
      .post("/purchase.php")
      .headers(OriginHeaders)
      .formParam("flight", "234")
      .formParam("price", "432.98")
      .formParam("airline", "United Airlines")
      .formParam("fromPort", "Paris")
      .formParam("toPort", "Buenos Aires"))
    .pause(49)
    .exec(http("request_4")
      .post("/confirmation.php")
      .headers(OriginHeaders)
      .formParam("_token", "")
      .formParam("inputName", "Qwaresta")
      .formParam("address", "QwaStreet")
      .formParam("city", "QwaCity")
      .formParam("state", "QwaState")
      .formParam("zipCode", "8888")
      .formParam("cardType", "visa")
      .formParam("creditCardNumber", "7777777")
      .formParam("creditCardMonth", "11")
      .formParam("creditCardYear", "2020")
      .formParam("nameOnCard", "Qwaresta"))

}
