package load.Objects
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._

object Registration {
  //Header
  val someExtraHeaders = Map("Origin" -> "https://blazedemo.com")
//Feeders
  val Email = Iterator.continually(Map("email" -> (Random.alphanumeric.take(20).mkString + "@gmail.com")))

  val registration = exec(
    http("Registration")
      .get("/register")
       .check(status.is(200))
  )
    .pause(15)
    feed(Email)
    .exec(
      http("EnterInform")
      .post("/register")
      .headers(someExtraHeaders)
      .formParam("_token", "MrZMFIJgxWmUvnjKLnMAUHN453OXeKFrlEwbMVXN")
      .formParam("name", "Qwaresta")
      .formParam("company", "QwaCompany")
      .formParam("email", Email)
      .formParam("password", "123456")
      .formParam("password_confirmation", "123456")
       .check(status.is(419))
    )
}
