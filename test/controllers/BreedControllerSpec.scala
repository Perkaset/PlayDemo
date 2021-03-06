package controllers

import model.{Breed, Search}
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import repo.SearchesDAO
import services.CatApiService
import org.mockito.Mockito.when
import org.mockito.ArgumentMatchers.any

import scala.concurrent.Future

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class BreedControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  "BreedController GET" should {

    "return breed when given a searchString" in {
      val apiService = mock[CatApiService]
      val searchDb = mock[SearchesDAO]

      val stringSearch = "bengal"
      val apiResult = Future.successful(Seq(Breed("Beng", "Bengal", "Description", "Lazy", "Brazil")))
      val dbResult = Future.successful(())
      val controller = new BreedController(stubControllerComponents(), apiService, searchDb)

      when(apiService.searchBreed(any[String])).thenReturn(apiResult)
      when(searchDb.saveSearch(any[Search])).thenReturn(dbResult)

      val search = controller.searchBreed(stringSearch).apply(FakeRequest(GET, s"/breed?searchString=$stringSearch"))

      status(search) mustBe OK
      contentType(search) mustBe Some("application/json")
      contentAsString(search) must include ("Bengal")
    }
  }
}
