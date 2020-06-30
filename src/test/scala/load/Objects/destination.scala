package default

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class destination extends Simulation {

	val httpConf = http
		.baseURL("https://blazedemo.com/ ")
		.disableFollowRedirect
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:77.0) Gecko/20100101 Firefox/77.0")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map("Accept" -> "text/css,*/*;q=0.1")

	val headers_3 = Map("Accept" -> "*/*")



	val scn = scenario("destination")
		.exec(http("request_0")
			.get("/home")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/login")
			.headers(headers_0),
            http("request_2")
			.get("/css/app.css")
			.headers(headers_2),
            http("request_3")
			.get("/js/app.js")
			.headers(headers_3))
			.check(status.is(302)))
		.pause(3)
		.exec(http("request_4")
			.get("/")
			.headers(headers_0))
		.pause(4)
		.exec(http("request_5")
			.get("/vacation.html")
			.headers(headers_0))

//	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}