package controllers

import javax.inject._
import model.{Breed, Search}
import play.api.libs.json.{Json}
import play.api.mvc._
import repo.SearchesDAO
import services.CatApiService

import scala.concurrent._
import ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, catApiService:CatApiService, searchDb:SearchesDAO) extends BaseController {

  implicit val breedFormat = Json.format[Breed]
  implicit val searchFormat = Json.format[Search]

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def searchBreed(searchString:String) = Action.async { implicit request: Request[AnyContent] =>
    catApiService.searchBreed(searchString).flatMap { result =>
      searchDb.saveSearch(Search(-1, searchString, System.currentTimeMillis())).map { _ =>
        Ok(Json.toJson(result))
      }
    }
  }

  def getSearches() = Action.async { implicit request: Request[AnyContent] =>
    searchDb.getAllSearches().map { searchesResult =>
      Ok(Json.toJson(searchesResult))
    }
  }

  def saveFav() = Action.async { implicit request: Request[AnyContent] =>
    val fav:Breed = request.body.asJson.get.as[Breed]
    searchDb.saveFav(fav).map { saveResult =>
      Ok("Breed Saved")
    }
  }

  def getFavs() = Action.async { implicit request: Request[AnyContent] =>
    searchDb.getFavs().map { favsResult =>
      Ok(Json.toJson(favsResult))
    }
  }


}
