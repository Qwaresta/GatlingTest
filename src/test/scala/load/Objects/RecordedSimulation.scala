package default

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RecordedSimulation extends Simulation {

	val httpConf = http
		.baseURL("https://blazedemo.com/ ")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:77.0) Gecko/20100101 Firefox/77.0")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map("Accept" -> "image/webp,*/*")

	val headers_2 = Map(
		"Origin" -> "https://blazedemo.com",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("RecordedSimulation")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/favicon.ico")
			.headers(headers_1)))
		.pause(38)
		.exec(http("request_2")
			.post("/reserve.php")
			.headers(headers_2)
			.formParam("fromPort", "Paris")
			.formParam("toPort", "Buenos Aires"))
		.pause(16)
		.exec(http("request_3")
			.post("/purchase.php")
			.headers(headers_2)
			.formParam("flight", "234")
			.formParam("price", "432.98")
			.formParam("airline", "United Airlines")
			.formParam("fromPort", "Paris")
			.formParam("toPort", "Buenos Aires"))
		.pause(49)
		.exec(http("request_4")
			.post("/confirmation.php")
			.headers(headers_2)
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