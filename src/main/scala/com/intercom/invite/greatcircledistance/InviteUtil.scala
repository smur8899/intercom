package com.intercom.invite.greatcircledistance

import java.io.InputStream

import com.intercom.invite.greatcircledistance.Coordinate.Coordinate

case class CustomerDetailSimple(name: String, userId: Long)

class InviteUtil {

  val greatCircleDistance = new GreatCircleDistance

  /**
    * Retrieves customers within a certain distance from a central point.
    *
    * @param distanceKm     Max distance for customer
    * @param fromCoordinate Central point to measure distance from
    * @param customerStream Customers to evaluate
    * @return Customers name and id, where the customer is within the specified distance
    */
  def getCustomersWithin(distanceKm: Double, fromCoordinate: Coordinate, customerStream: InputStream):
  Stream[CustomerDetailSimple] = {
    val customers = new JsonFileReader().readJsonFile(customerStream)
    customers.filter(customer => {
      val customerCoordinates = Coordinate(customer.latitude, customer.longitude)
      greatCircleDistance.calculateDistance(customerCoordinates, fromCoordinate) <= distanceKm
    }).map(invitees => CustomerDetailSimple(invitees.name, invitees.userId))
  }

}
