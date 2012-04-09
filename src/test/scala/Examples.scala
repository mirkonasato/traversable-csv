import com.encodedknowledge.traversable.csv.CSVFile
import com.encodedknowledge.traversable.csv.CSVTable

object Examples extends App {

  csvFileExamples()
  csvTableExamples()

  def csvFileExamples() {
    val csv = new CSVFile("src/test/resources/test.csv")

    // traverse each line with values provided as Array[String]
    for (row <- csv)
      println(row.toList)

    // all Traversable methods should work
    // (note that the file will be re-read for every method though)
    println(csv.head.toList)
    println(csv.last.toList)
    //...

    // custom toMap - equivalent to but more efficient than
    // csv.map(row => (row(1), row(2))).toMap
    val englishToItalian = csv.toMap(row => (row(1), row(2)))
    println(englishToItalian)
  }

  def csvTableExamples() {
    // define a class representing a row
    class Number(val symbol: Int, val english: String, val italian: String)

    // create a CSVTable with a function mapping string values to an object of the above class
    val numberTable = new CSVTable(new CSVFile("src/test/resources/test.csv"), row =>
      new Number(row(0).toInt, row(1), row(2))
    )

    // iterate over each object
    for (number <- numberTable)
      printf("%d. the Italian for '%s' is: '%s'\n", number.symbol, number.english, number.italian)
    
    // build a sort of in-memory index
    val numberBySymbol = numberTable.toMap(number => (number.symbol -> number))
    printf(numberBySymbol(3).english)
  }

}
