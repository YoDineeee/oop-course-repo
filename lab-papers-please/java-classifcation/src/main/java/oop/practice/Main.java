package oop.practice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    ObjectMapper mapper = new ObjectMapper();
    File inputFile = new File("src/main/resources/test-input.json");

    // Read the JSON data
    JsonNode data;
    try {
      data = mapper.readTree(inputFile).get("data");
      if (data == null) {
        System.out.println("No data found in the JSON file.");
        return;
      }
    } catch (IOException e) {
      System.err.println("Error reading the JSON file: " + e.getMessage());
      return;
    }

    Universe starWars = new Universe("starWars", new ArrayList<>());
    Universe hitchhikers = new Universe("hitchHiker", new ArrayList<>());
    Universe marvel = new Universe("marvel", new ArrayList<>());
    Universe rings = new Universe("rings", new ArrayList<>());

    Scanner scanner = new Scanner(System.in);

    System.out.println("Please categorize the individuals. Enter 'exit' to quit.");

    for (JsonNode entry : data) {
      String entryAsString = entry.toString();

      // Read user input
      String userInput = "";
      boolean validInput = false; // Flag to track valid input

      while (!validInput) {
        System.out.println("Enter 1 for Star Wars, 2 for Hitchhiker, 3 for Marvel, 4 for Rings:");
        userInput = scanner.nextLine();

        // Check if the input is an integer and within the range
        try {
          int category = Integer.parseInt(userInput);
          if (category < 1 || category > 4) {
            System.out.println("Invalid input. Please enter a number between 1 and 4.");
          } else {
            validInput = true; // Set flag to true when valid input is received
            // Categorize the entry based on valid input
            switch (category) {
              case 1:
                starWars.individuals().add(entry);
                break;
              case 2:
                hitchhikers.individuals().add(entry);
                break;
              case 3:
                marvel.individuals().add(entry);
                break;
              case 4:
                rings.individuals().add(entry);
                break;
            }
          }
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Please enter a number between 1 and 4.");
        }
      }

      // Display the entry only if the input was valid
      System.out.println("You added: " + entryAsString);
    }

    scanner.close();

    // Write the categorized data to output files
    try {
      mapper.writeValue(new File("src/main/resources/output/starwars.json"), starWars);
      mapper.writeValue(new File("src/main/resources/output/hitchhiker.json"), hitchhikers);
      mapper.writeValue(new File("src/main/resources/output/rings.json"), rings);
      mapper.writeValue(new File("src/main/resources/output/marvel.json"), marvel);
      System.out.println("Output files created successfully.");
    } catch (IOException e) {
      System.err.println("Error writing to output files: " + e.getMessage());
    }
  }
}

record Universe(
        String name,
        List<JsonNode> individuals
) { }
