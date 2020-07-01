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
  val password ="12345678"
  val Name =("Qwaresta")

  val registration = exec(
    http("Home")
      .get("/home")
      .check(status.is(200))
      .check(header("XSRF-TOKEN=").saveAs("token"))
      .check(header("laravel_session=").saveAs("cookie_session"))
  )
      .pause(10)
    .exec(
    http("Registration")
      .get("/register")
      .header("XSRF-TOKEN", "${token}")
      .header("laravel_session", "${cookie_session}")
       .check(status.is(200))
      .check(xpath("/*/input id/@id").saveAs("inputId"))
  )
    .pause(15)
    feed(Email)
    .exec(
      http("EnterInform")
      .post("/register")
        .header("XSRF-TOKEN", "${token}")
        .header("laravel_session", "${cookie_session}")
        .headers(someExtraHeaders)
      .formParam("_token", "${token}")
      .formParam("name", Name)
      .formParam("company", Name +"Company")
      .formParam("email", Email)
      .formParam("password", password)
      .formParam("password_confirmation", password)
       .check(status.is(419))  //Ошибка на сайте ¯\_(ツ)_/¯
  )
}
