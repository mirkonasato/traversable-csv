package com.encodedknowledge.traversable.csv

//import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

//@RunWith(classOf[JUnitRunner])
class CSVDataTest extends FunSuite with ShouldMatchers {

  test("read comma-separated") {
    val data = new CSVData("src/test/resources/test.csv")
    val rows = data.toList
    rows.size should equal(5)
    rows(0) should equal(Array[String]("1", "one", "plain"))
    rows(2)(2) should equal("quote: \" escaped")
    rows(3)(2) should equal("escape: \\ escaped")
    rows(4) should equal(Array[String]("5", "five", "plain"))
  }

  test("read tab-separated") {
    val data = new CSVData("src/test/resources/test.tsv", separator = '\t', quote = '\'')
    val rows = data.toList
    rows.size should equal(5)
    rows(0) should equal(Array[String]("1", "one", "plain"))
    rows(2)(2) should equal("quote: ' escaped")
    rows(3)(2) should equal("escape: \\ escaped")
    rows(4) should equal(Array[String]("5", "five", "plain"))
  }

  test("skip first line") {
    val data = new CSVData("src/test/resources/test.csv", skipFirst = true)
    val rows = data.toList
    rows.size should equal(4)
    rows(0)(0) should equal("2")
  }

  test("to map") {
    val data = new CSVData("src/test/resources/test.csv")
    val numbers = data.toMap(row => row(1) -> row(0).toInt)
    numbers.size should equal(5)
    numbers("one") should equal(1)
    numbers("five") should equal(5)
  }

}
