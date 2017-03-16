package com.intercom.invite.greatcircledistance

import com.intercom.invite.greatcircledistance.Coordinate.Coordinate
import com.typesafe.scalalogging.LazyLogging

import scala.math._


class GreatCircleDistance extends LazyLogging {

  // oblate spheroid, take the mean and allow for inaccuracy
  private val MEAN_RADIUS_OF_EARTH = 6371

  /**
    * Get the distance betwee two coordinates using Great Circle Distance formula
    *
    * ArcCos( SIN(lat1)*SIN(lat2) + COS(lat1)*COS(lat2)*COS(lon2-lon1) ) * 6371000
    *
    * c.f https://en.wikipedia.org/wiki/Great-circle_distance
    *
    * @param from First Coordinate
    * @param to   Second Coordinate
    * @return Calculated distance based on MEAN_RADIUS_OF_EARTH
    */
  def calculateDistance(from: Coordinate, to: Coordinate): Double = {
    val absDifferenceLon = abs((to.lon - from.lon).toRadians)
    val centralAngle = sin(from.lat.toRadians) * sin(to.lat.toRadians) +
      cos(from.lat.toRadians) * cos(to.lat.toRadians) * cos(absDifferenceLon)
    val distanceKm = acos(centralAngle) * MEAN_RADIUS_OF_EARTH
    logger.debug(s"Distance from $from to $to: ${distanceKm}Km")
    distanceKm
  }

}

/**
  * Coordinate class, will round to acceptable longitudinal and latitudinal values when created
  */
object Coordinate {
  val intercomCoordinates = Coordinate(53.3393, -6.2576841)

  case class Coordinate(lat: Double, lon: Double)

  private val MAX_LAT = 90
  private val MAX_LON = 180

  def apply(lat: Double, lon: Double): Coordinate = {
    val latitude = Math.max(-MAX_LAT, Math.min(lat, MAX_LAT))
    val longitude = Math.max(-MAX_LON, Math.min(lon, MAX_LON))
    Coordinate(latitude, longitude)
  }
}