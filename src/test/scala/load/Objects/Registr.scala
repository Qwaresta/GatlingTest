package default

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Registr extends Simulation {

	val httpConf = http
		.baseURL("https://blazedemo.com/ ")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:77.0) Gecko/20100101 Firefox/77.0")

	val headers_1 = Map("Origin" -> "https://blazedemo.com")



	val scn = scenario("Registr")
		.exec(http("request_0")
			.get("/register"))
		.pause(15)
		.exec(http("request_1")
			.post("/register")
			.headers(headers_1)
			.formParam("_token", "MrZMFIJgxWmUvnjKLnMAUHN453OXeKFrlEwbMVXN")
			.formParam("name", "Qwaresta")
			.formParam("company", "QwaCompany")
			.formParam("email", "Qwa@qwa.ru")
			.formParam("password", "123456")
			.formParam("password_confirmation", "123456")
			.check(status.is(419)))

}