package load.Scenarios

import io.gatling.core.Predef._
import io.gatling.core.feeder.RecordSeqFeederBuilder
import io.gatling.core.structure.ChainBuilder
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.Predef._
import load.Objects.{Registration, Vacation}
import load.Objects.load.default.ReserveFlight
import io.gatling.core.scenario.Simulation
import scala.language.postfixOps
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._

class Reliability extends Simulation {

  val httpConf = http
    .baseURL("https://blazedemo.com")
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0")

  val reliability = scenario("Reliability")randomSwitch(
    (70, pace(40 seconds).exec(Registration.registration)),
    (20, pace(25 seconds).exec(Vacation.vacation)),
    (10, pace(45 seconds).exec(ReserveFlight.reserveflight))
  )

  setUp(
    reliability.inject(rampUsers(100) over (720 minutes))
      .protocols(httpConf)).assertions(
    global.responseTime.max.lte(10),
    details("ReserveFlight").responseTime.percentile3.lt(10))
    .maxDuration(721 minutes)


}
