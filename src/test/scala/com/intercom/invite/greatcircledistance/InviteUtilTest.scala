package com.intercom.invite.greatcircledistance

import org.scalatest.FunSuite

class InviteUtilTest extends FunSuite {

  test("testSendInvitesForCustomersWithin") {
    val resource = getClass.getResourceAsStream("customers.json")
    val customers = new InviteUtil().getCustomersWithin(100, Coordinate.intercomCoordinates, resource)
    val totalCustomers = customers.foldLeft(0)((sum, cust) => {
      println(cust)
      sum + 1
    })
    assert(totalCustomers == 16)
  }

  test("test negative distance no results") {
    val resource = getClass.getResourceAsStream("customers.json")
    val customers = new InviteUtil().getCustomersWithin(-100, Coordinate.intercomCoordinates, resource)
    val totalCustomers = customers.foldLeft(0)((sum, cust) => {
      println(cust)
      sum + 1
    })
    assert(totalCustomers == 0)
  }

  test("test distance infinity") {
    val resource = getClass.getResourceAsStream("customers.json")
    val customers = new InviteUtil().getCustomersWithin(Double.PositiveInfinity, Coordinate.intercomCoordinates, resource)
    val totalCustomers = customers.foldLeft(0)((sum, cust) => {
      println(cust)
      sum + 1
    })
    assert(totalCustomers == 32)
  }

  test("test null resource") {
    val customers = new InviteUtil().getCustomersWithin(100, Coordinate.intercomCoordinates, null)
    val totalCustomers = customers.foldLeft(0)((sum, cust) => {
      println(cust)
      sum + 1
    })
    assert(totalCustomers == 0)
  }

}
