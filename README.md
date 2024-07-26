# Semantic RDF Crawler Project

## Overview
The Semantic RDF Crawler is a Java-based project designed to explore and retrieve data from RDF resources available on the web. It utilizes the Apache Jena library to interact with RDF models and traverse linked data using semantic predicates such as OWL.sameAs. The project is structured to facilitate the extension and customization of the crawling process to accommodate various RDF data sources and exploration strategies.

## Features
- **Dynamic RDF Data Retrieval**: Fetches RDF data dynamically from specified URIs.
- **Semantic Link Following**: Implements recursive searching through OWL.sameAs links to discover new RDF resources.
- **Character Encoding Check**: Ensures that URIs are correctly encoded in ISO-8859-1, enhancing compatibility and reducing errors.
- **Anonymous Node Handling**: Provides specialized handling for anonymous nodes (blank nodes) within RDF data.
- **Visited URI Tracking**: Prevents cyclic retrieval and re-processing of previously visited URIs.

## Modules
This project is divided into three main packages:
1. **org.example.Crawler**: Contains interfaces for the crawler.
2. **org.example.impl**: Includes implementations of the crawling mechanisms.
3. **org.example.Test**: Offers a test class to demonstrate the usage and functionality of the crawler.

## Dependencies
- **Apache Jena**: Required for manipulating RDF models and executing semantic web operations. Ensure that Apache Jena libraries are included in your project's classpath.

## Installation
1. **Clone the Repository**: 
   ```bash
   git clone [repository-url]

## Include Dependencies
Add the Apache Jena library to your project. This can typically be done by including it in your project's `pom.xml` if using Maven:

```xml
    <dependency>
        <groupId>org.apache.jena</groupId>
        <artifactId>apache-jena-libs</artifactId>
        <version>[version-number]</version>
        <type>pom</type>
    </dependency>
```
## Usage

To use the Semantic RDF Crawler, follow these steps:

1. **Initialize a Model**: Create an RDF model using Jena's ModelFactory.
   ```java
     Model model = ModelFactory.createDefaultModel();
    ```
2. **Create a Crawler Instance: Instantiate SemanticCrawlerImpl.
  ```java
      SemanticCrawlerImpl crawler = new SemanticCrawlerImpl();
  ```
3. **Invoke the search Method: Call the search method with the initial URI and the model.
```java
crawler.search(model, "http://dbpedia.org/resource/Zico");
```
4. **Output the Model: After crawling, output the model in TTL format to review the fetched data.
```java
model.write(System.out, "TTL");
```

##Example
```java
String URI = "http://dbpedia.org/resource/Zico";
Model model = ModelFactory.createDefaultModel();
SemanticCrawlerImpl crawler = new SemanticCrawlerImpl();
crawler.search(model, URI);
model.write(System.out, "TTL");
```


