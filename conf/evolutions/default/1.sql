# --- !Ups

CREATE TABLE "searches"("search_id" SERIAL NOT NULL PRIMARY KEY,"search_string" VARCHAR NOT NULL, "date" BIGINT NOT NULL);
CREATE TABLE "favs"("id" VARCHAR NOT NULL PRIMARY KEY,"name" VARCHAR NOT NULL, "description" VARCHAR NOT NULL, "temperament" VARCHAR NOT NULL, "origin" VARCHAR NOT NULL);

# --- !Downs

DROP TABLE "searches";
DROP TABLE "favs";
