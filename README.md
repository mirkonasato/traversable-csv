traversable-csv
===============

A simple Scala wrapper for [opencsv](http://opencsv.sourceforge.net) that
leverages the Traversable trait so a CSV file can be read in a for loop.

Also provides a simple way to map each row to a typed object.

Usage
-----

Processing each line in a CSV file is as simple as:

    val csv = new CSVFile("data.csv")
    for (row <- csv) {
      println("Field 1: "+ row(0))
      println("Filed 2: "+ row(1))
      //...
    }

To treat each row as a typed object rather than an array of strings:

    class User(val id: Int, val name: String)
    val userTable = new CSVTable(new CSVFile("users.csv"), row =>
      new User(row(0).toInt, row(1))
    )
    for (user <- userTable)
      printf("user '%s' has id %d\n"", user.name, user.id)

All the Traversable methods like map, filter, etc. should work - be aware
that the file will be re-read at each invocation (or for loop) though.

License
-------

This software is distributed under the
[Apache License v2.0](http://www.apache.org/licenses/LICENSE-2.0.html).

