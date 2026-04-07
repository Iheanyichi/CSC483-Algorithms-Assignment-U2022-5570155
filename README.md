# CSC483-Algorithms-Assignment-U2022-5570155
# CSC483-Algorithms-Assignment

An Assignment on Algorithm Design, Analysis, and Optimisation for Real-World Systems

---

## Dependencies

- **Java:** 11 or higher
- **Testing:** JUnit 5 (junit-jupiter 5.10.0)
- **Build:** No build tool required — pure `javac` compilation

---

## How to Run

### From the project root:

```bash
# Compile source files
javac Main.java

# Run compiled code
java Main.java

```

---

## Sample Usage

````java
# CSC483 Algorithms Assignment

A small collection of Java programs demonstrating sorting and search algorithms used for a university assignment.

## Prerequisites

- Java 11 or later installed and on your `PATH`.

## Project structure

- algorithms_comparison/
	- `SortingAlgorithms.java` — implementations of sorting algorithms
	- `SortingBenchmark.java` — simple benchmark harness for sorting
- online_store_search_optimization/
	- `Product.java` — sample data model
	- `SearchAlgorithms.java` — implementations of search methods and helpers
	- `SearchBenchmark.java` — simple benchmark harness for search

## Build

From the project root run:

```bash
javac algorithms_comparison/*.java online_store_search_optimization/*.java
````

This compiles the Java source code into `.class` files.

## Run

Run the sorting benchmark (example dataset size 10000):

```bash
java algorithms_comparison.SortingBenchmark 10000
```

Run the search benchmark (example: dataset size, then number of queries):

```bash
java online_store_search_optimization.SearchBenchmark 100000 5000
```


## Notes

- No external libraries are required; this is plain Java source code.
