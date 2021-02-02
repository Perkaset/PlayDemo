package repo

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject
import model.{Breed, Search}
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

class SearchesDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val searches = TableQuery[SearchesTable]
  private val favs = TableQuery[FavouritesTable]

  def saveSearch(search: Search): Future[Unit] = db.run(searches += search).map { _ => () }

  def getAllSearches():Future[Seq[Search]] = db.run(searches.result)

  def saveFav(fav: Breed): Future[Unit] = db.run(favs += fav).map { _ => () }

  def getFavs():Future[Seq[Breed]] = db.run(favs.result)

  private class SearchesTable(tag: Tag) extends Table[Search](tag, "searches") {

    def id = column[Long] ("search_id", O.PrimaryKey, O.AutoInc)
    def searchString = column[String]("search_string")
    def date = column[Long]("date")

    def * = (id, searchString, date) <> (Search.tupled, Search.unapply)
  }

  private class FavouritesTable(tag: Tag) extends Table[Breed](tag, "favs") {
    def id = column[String] ("id", O.PrimaryKey)
    def name = column[String] ("name")
    def description = column[String]("description")
    def temperament = column[String]("temperament")
    def origin = column[String]("origin")

    def * = (id, name, description, temperament, origin) <> (Breed.tupled, Breed.unapply)
  }
}