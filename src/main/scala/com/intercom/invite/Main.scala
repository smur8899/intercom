package com.intercom.invite

import com.intercom.invite.greatcircledistance.{Coordinate, InviteUtil}

object Main {

  /**
    * We have some customer records in a text file (customers.json) -- one customer per line, JSON-encoded. We want to
    * invite any customer within 100km of our Dublin office for some food and drinks on us. Write a program that will
    * read the full list of customers and output the names and user ids of matching customers (within 100km), sorted
    * by User ID (ascending).
    *
    * You can use the first formula from this Wikipedia article to calculate distance. Don't forget, you'll need to
    * convert degrees to radians.
    *
    * The GPS coordinates for our Dublin office are 53.3393,-6.2576841.
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {
    val resource = getClass.getResourceAsStream("customers.json")
    val distanceKm = 100

    val customersWithin100Km = new InviteUtil().getCustomersWithin(distanceKm, Coordinate.intercomCoordinates, resource)
    customersWithin100Km.foreach(println)
  }
}
