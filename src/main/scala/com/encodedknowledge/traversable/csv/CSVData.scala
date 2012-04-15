//
// Copyright (c) 2012 Mirko Nasato
//
// Permission is hereby granted, free of charge, to any person obtaining a
// copy of this software and associated documentation files (the "Software"),
// to deal in the Software without restriction, including without limitation
// the rights to use, copy, modify, merge, publish, distribute, sublicense,
// and/or sell copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included
// in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
// THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
// OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
// ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
// OTHER DEALINGS IN THE SOFTWARE.
//
package com.encodedknowledge.traversable.csv

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class CSVData(fileName: String,
  charset: String = "UTF-8", separator: Char = ',', quote: Char = '"', escape: Char = '\\', skipFirst: Boolean = false
) extends Traversable[Array[String]] {

  override def foreach[U](f: Array[String] => U): Unit = {
    val reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset))
    try {
      if (skipFirst) reader.readLine()
      var next = true
      while (next) {
        val line = reader.readLine()
        if (line != null) f(parse(line))
        else next = false
      }
    } finally {
      reader.close()
    }
  }

  def toMap[T, U](toPair: Array[String] => (T, U)): Map[T, U] = {
    val mapBuilder = Map.newBuilder[T, U]
    for (row <- this) mapBuilder += toPair(row)
    mapBuilder.result
  }

  private def parse(line: String): Array[String] = {
    val values = Array.newBuilder[String]
    val buffer = new StringBuilder
    var insideQuotes = false 
    var escapeNext = false
    for (c <- line) {
      if (escapeNext) { buffer += c; escapeNext = false }
      else if (c == escape) escapeNext = true
      else if (c == quote) insideQuotes = !insideQuotes
      else if (c == separator && !insideQuotes) { values += buffer.result; buffer.clear }
      else buffer += c
    }
    values += buffer.result
    return values.result
  }

}
