package load

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadScriptNew extends Simulation{

  val httpConf = http
    .baseURL("https://blazedemo.com/ ")
    .disableFollowRedirect
    .inferHtmlResources()
    //.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
    //.acceptEncodingHeader("gzip, deflate, br")
   // .acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
   // .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:77.0) Gecko/20100101 Firefox/77.0")

  val basicLoadNew = scenario("BASIC_LOAD").during(1 minutes) {
    exec(BasicLoad.start)
  }
  setUp(
    basicLoadNew.inject(rampUsers(1) over (2 minutes))
      .protocols(httpConf))
    .maxDuration(2 minutes)

}

object BasicLoadNew {

  def getTen = 10
  val start =
    exec(
      http("HTTP Request Home")
        .get("/home")
        .check(status is 200)
    )
      .exec(
        http("HTTP Request Vacation")
          .get("/vacation.html")
          .check(status is 200)
      )
}

