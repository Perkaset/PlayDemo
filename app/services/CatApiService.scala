package services

import javax.inject.Inject
import play.api.libs.ws._
import model.{Breed, Search}
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

class CatApiService @Inject()(wsClient: WSClient)(implicit ec: ExecutionContext) {

  implicit val breedFormat = Json.format[Breed]
  implicit val searchFormat = Json.format[Search]

  def searchBreed(searchString:String):Future[Seq[Breed]] = {
    wsClient.url(s"https://api.thecatapi.com/v1/breeds/search?q=$searchString")
      .addHttpHeaders(("x-api-key","62bbebb6-f6d4-46da-8f43-0c470bcca601"))
      .get().map(
        response =>
          response.json.as[Seq[Breed]]
      )
  }
}
