package oop.practice;

import com.fasterxml.jackson.core.JsonProcessingException;
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
      Individual individual = null;
      try {
        // Convert JsonNode to Individual object
        individual = mapper.treeToValue(entry, Individual.class);
      } catch (JsonProcessingException e) {
        System.err.println("Error processing JSON entry: " + e.getMessage());
        continue;
      }

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
                starWars.addIndividual(individual);
                break;
              case 2:
                hitchhikers.addIndividual(individual);
                break;
              case 3:
                marvel.addIndividual(individual);
                break;
              case 4:
                rings.addIndividual(individual);
                break;
            }
          }
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Please enter a number between 1 and 4.");
        }
      }

      // Display the individual only if the input was valid
      System.out.println("You added: " + individual);
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

// The Individual class representing each character from the JSON
class Individual {
  private int id;
  private boolean isHumanoid;
  private String planet;
  private int age;
  private List<String> traits;

  // Constructors, Getters, and Setters
  public Individual() {}

  public Individual(int id, boolean isHumanoid, String planet, int age, List<String> traits) {
    this.id = id;
    this.isHumanoid = isHumanoid;
    this.planet = planet;
    this.age = age;
    this.traits = traits;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isHumanoid() {
    return isHumanoid;
  }

  public void setHumanoid(boolean humanoid) {
    isHumanoid = humanoid;
  }

  public String getPlanet() {
    return planet;
  }

  public void setPlanet(String planet) {
    this.planet = planet;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public List<String> getTraits() {
    return traits;
  }

  public void setTraits(List<String> traits) {
    this.traits = traits;
  }

  @Override
  public String toString() {
    return "Individual{" +
            "id=" + id +
            ", isHumanoid=" + isHumanoid +
            ", planet='" + planet + '\'' +
            ", age=" + age +
            ", traits=" + traits +
            '}';
  }
}

// The Universe class, now storing a list of Individual objects
class Universe {
  private String name;
  private List<Individual> individuals;

  public Universe(String name, List<Individual> individuals) {
    this.name = name;
    this.individuals = individuals;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Individual> getIndividuals() {
    return individuals;
  }

  public void addIndividual(Individual individual) {
    this.individuals.add(individual);
  }
}
//this the aka plan i will make it better for the future