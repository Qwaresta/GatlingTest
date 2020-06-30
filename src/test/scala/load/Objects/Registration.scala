package load.Objects
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

object Registration {
  val someExtraHeaders = Map("Origin" -> "https://blazedemo.com")
  val registration = exec(http("Register")
      .get("/register"))
//      .check(status.is(200))
    .pause(15)
    .exec(http("request_1")
      .post("/register")
      .headers(someExtraHeaders)
      .formParam("_token", "MrZMFIJgxWmUvnjKLnMAUHN453OXeKFrlEwbMVXN")
      .formParam("name", "Qwaresta")
      .formParam("company", "QwaCompany")
      .formParam("email", "Qwa@qwa.ru")
      .formParam("password", "123456")
      .formParam("password_confirmation", "123456")
      .check(status.is(419)))
      .pause(15)
}
