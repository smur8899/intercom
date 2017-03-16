package com.intercom.invite.greatcircledistance

import java.io.ByteArrayInputStream

import org.scalatest.FunSuite

class JsonFileReaderTest extends FunSuite {

  test("read whole file") {
    val resource = getClass.getResourceAsStream("customers.json")
    val reader = new JsonFileReader()
    val customers = reader.readJsonFile(resource)
    assert(customers.head.latitude == 52.986375)
  }

  test("test file size") {
    val resource = getClass.getResourceAsStream("customers.json")
    val reader = new JsonFileReader()
    val customers = reader.readJsonFile(resource)
    assert(customers.size == 32)
  }

  test("test can read partially formatted file") {
    val resource = getClass.getResourceAsStream("customersArray.json")
    val reader = new JsonFileReader()
    val customers = reader.readJsonFile(resource)
    assert(customers.size == 3)
  }

  test("test graceful fail on XML") {
    val resource = getClass.getResourceAsStream("customers.xml")
    val reader = new JsonFileReader()
    val customers = reader.readJsonFile(resource)
    assert(customers.isEmpty)
  }

  test("test ByteArrayInputStream invalid") {
    val resource = new ByteArrayInputStream("This is not valid".getBytes())
    val reader = new JsonFileReader()
    val customers = reader.readJsonFile(resource)
    assert(customers.isEmpty)
  }

  test("test ByteArrayInputStream valid") {
    val resource = new ByteArrayInputStream(
      "{\"latitude\": \"52.833502\", \"user_id\": 25, \"name\": \"David Behan\", \"longitude\": \"-8.522366\"}"
        .getBytes())
    val reader = new JsonFileReader()
    val customers = reader.readJsonFile(resource)
    assert(customers.size == 1)
  }

}
