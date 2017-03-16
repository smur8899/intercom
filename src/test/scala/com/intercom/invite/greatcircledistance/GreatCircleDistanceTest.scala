package com.intercom.invite.greatcircledistance

import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.FunSuite

class GreatCircleDistanceTest extends FunSuite {

  val greatCircleDistance = new GreatCircleDistance

  /*
   Accounting for variation in Earths radius, tolerant to 1% of expected value,
   obtained from http://www.movable-type.co.uk/scripts/latlong.html
   */
  implicit val doubleEquality: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(1)

  test("test get distance for user 12") {
    val coord1 = Coordinate(52.986375, -6.043701)
    val distance = greatCircleDistance.calculateDistance(coord1, Coordinate.intercomCoordinates)
    val expected = 41.1
    assert(distance === expected)
  }

  test("test get very close distance") {
    val coord1 = Coordinate(52.986375, -6.043701)
    val coord2 = Coordinate(52.986370, -6.043700)
    val distance = greatCircleDistance.calculateDistance(coord1, coord2)
    val expected = 0.0005600
    assert(distance === expected)
  }

  test("test distance to same point") {
    val coord1 = Coordinate(52.986375, -6.043701)
    val distance = greatCircleDistance.calculateDistance(coord1, coord1)
    val expected = 0.0
    assert(distance === expected)
  }

  test("test Infinity is rounded to acceptable values") {
    val coord1 = Coordinate(Double.PositiveInfinity, -6.043701)
    val distance = greatCircleDistance.calculateDistance(coord1, coord1)
    val expected = 0.0
    assert(distance === expected)
  }

}
