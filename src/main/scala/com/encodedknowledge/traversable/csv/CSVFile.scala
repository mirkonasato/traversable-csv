//
// Copyright 2012 Mirko Nasato
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package com.encodedknowledge.traversable.csv

import java.io.FileInputStream
import java.io.InputStreamReader

import au.com.bytecode.opencsv.CSVReader

class CSVFile(fileName: String, charset: String = "UTF-8", separator: Char = ',', quote: Char = '"', escape: Char = '\\') extends Traversable[Array[String]] {

  override def foreach[U](f: Array[String] => U): Unit = {
    val csvReader = new CSVReader(new InputStreamReader(new FileInputStream(fileName), charset), separator, quote, escape)  
    try {
      var next = true
      while (next) {
        val row = csvReader.readNext()
        if (row != null) f(row)
        else next = false
      }
    } finally {
      csvReader.close()
    }
  }

  def toMap[T, U](toPair: Array[String] => (T, U)): Map[T, U] = {
    val mapBuilder = Map.newBuilder[T, U]
    for (row <- this) mapBuilder += toPair(row)
    mapBuilder.result
  }

}
