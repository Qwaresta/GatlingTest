package load

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import load.Objects.{Registration, Vacation}
import load.Objects.load.default.ReserveFlight
import io.gatling.core.scenario.Simulation

import scala.concurrent.duration._  // used for specifying duration unit, eg "5 second"

class test extends Simulation {

  val httpProtocol = http
    .baseURL("https://blazedemo.com")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
  //  .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:77.0) Gecko/20100101 Firefox/77.0")


  val Newuser = scenario("New Users").exec(Registration.registration, Vacation.vacation, ReserveFlight.reserveflight)
  val RegUser = scenario("Registered  Users").exec(Vacation.vacation, ReserveFlight.reserveflight)

//  setUp(Newuser.inject(atOnceUsers(10)).protocols(httpProtocol))
//  setUp(RegUser.inject(atOnceUsers(100)).protocols(httpProtocol))
  setUp(
    Newuser.inject(rampUsers(10) during (10 seconds)),
    RegUser.inject(rampUsers(2) during (10 seconds))
  ).protocols(httpProtocol)


  val scn = scenario("test")
//  before {
//    println("Simulation is about to start!")
//  }
    .exec(http("HTTP Request HOME")
      .get("/home"))
    .pause(6)
    .exec(http("rHTTP Request Vacation")
      .get("/vacation.html"))
//  after {
//    println("Simulation is finished!")
//  }

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}