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

class CSVTable[A](csvFile: CSVFile, toObject: Array[String] => A) extends Traversable[A] {

  override def foreach[U](f: A => U): Unit =
    csvFile.foreach(line => f(toObject(line)))

  def toMap[T, U](toPair: A => (T, U)): Map[T, U] = {
    val mapBuilder = Map.newBuilder[T, U]
    for (row <- this) mapBuilder += toPair(row)
    mapBuilder.result
  }

}
