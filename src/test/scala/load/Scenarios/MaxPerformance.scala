package load.Scenarios

import io.gatling.core.Predef._
import io.gatling.core.feeder.RecordSeqFeederBuilder
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.jdbc.Predef._
import load.Objects.load.default.ReserveFlight
import load.Objects.{Registration, Vacation}

import scala.concurrent.duration._
import scala.language.postfixOps

class MaxPerformance extends Simulation {

  val httpConf = http
    .baseURL("https://blazedemo.com")
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0")

//Условное распределение по частоте операций
  val reliability = scenario("Reliability")randomSwitch(
    (70, pace(40 seconds).exec(Registration.registration)),
    (20, pace(25 seconds).exec(Vacation.vacation)),
    (10, pace(45 seconds).exec(ReserveFlight.reserveflight))
  )

  setUp(
      reliability.inject(rampUsers(10) during (10 minutes))
        .throttle(reachRps(100) in (10 seconds), holdFor(10 minutes))
  ).protocols(httpConf)).assertions(
    global.responseTime.max.lte(10),
    details("ReserveFlight").responseTime.percentile3.lt(10))

}
