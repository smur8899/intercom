package com.intercom.invite.greatcircledistance

import java.io.InputStream

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.{DeserializationFeature, JsonMappingException, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.typesafe.scalalogging.LazyLogging

import scala.io.Source

case class CustomerRecord(latitude: Double, @JsonProperty("user_id") userId: Long, name: String, longitude: Double)

class JsonFileReader extends LazyLogging {

  private val mapper = new ObjectMapper().registerModule(DefaultScalaModule)
    .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)

  /**
    * Lazily read the file line by line in case of large files.
    *
    * Imposes a restriction on
    *
    * @param input
    * @return
    */
  def readJsonFile(input: InputStream): Stream[CustomerRecord] = {
    try {
      val stream = Source.fromInputStream(input)
      val maybeRecords = for (line <- stream.getLines().toStream) yield getCustomerFromJsonStr(line)
      maybeRecords.flatten
    } catch {
      case _: NullPointerException =>
        logger.error("InputStream is null")
        Seq().toStream
    }
  }

  /**
    * Parse a JSON string to a CustomerRecord
    *
    * @param dataLine JSON string representation of CustomerRecord
    * @return Parsed CustomerRecord object
    */
  def getCustomerFromJsonStr(dataLine: String): Option[CustomerRecord] = {
    try {
      Some(mapper.readValue(dataLine, classOf[CustomerRecord]))
    } catch {
      case _: JsonParseException =>
        logger.error("Make sure you're passing a flattened JSON structure.")
        None
      case _: JsonMappingException =>
        logger.error("Expecting a flat JSON file, format file so each record is contained on one line, e.g. " +
          "\n{\"latitude\":\"52.833502\",\"user_id\":25,\"name\":\"David Behan\",\"longitude\":\"-8.522366\"}\n" +
          "v.s.\n" +
          "{\n\"latitude\":\"52.833502\",\n\"longitude\":\"-8.522366\",\n\"name\":\"David Behan\",\n\"user_id\": \"25\"\n}" +
          "\n")
        None
    }
  }

}