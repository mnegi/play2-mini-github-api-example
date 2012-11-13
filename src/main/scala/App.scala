package com.example

import com.typesafe.play.mini._
import play.api.mvc._
import play.api.mvc.Results._

import play.api.libs.ws.WS
//import play.api.libs.concurrent.Promise

/**
 * this application is registered via Global
 */
object App extends Application { 
  def route = {
    Through("/repos/search/(.*)".r) {groups: List[String] =>
      Action{
        val keyword :: Nil = groups
        val feedUrl = "https://api.github.com/legacy/repos/search/" + keyword
        Async {
          WS.url(feedUrl).get().map { response =>
            Ok(response.json)
            //Ok("Feed title: " + (response.json \ "title").as[String])
          }
        }
      }
    }
  }
}
