package weatherapi.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProjectStructureDumper {

  private static final String OUTPUT_FILE = "full_project_structure.txt";

  public static void main(String[] args) {
    File rootDir = new File("src"); // Путь к корневой папке проекта
    try (FileWriter writer = new FileWriter(OUTPUT_FILE)) {
      listFiles(rootDir, writer, 0);
      System.out.println("Project structure written to " + OUTPUT_FILE);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void listFiles(File file, FileWriter writer, int level) throws IOException {
    if (file.isDirectory()) {
      writeIndent(writer, level);
      writer.write("+" + file.getName() + "\n");
      File[] files = file.listFiles();
      if (files != null) {
        for (File subFile : files) {
          listFiles(subFile, writer, level + 1);
        }
      }
    } else {
      writeIndent(writer, level);
      writer.write("ª   " + file.getName() + "\n");
      // Append file content
      writer.write("ª   " + file.getName() + " content:\n");
      try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
          writer.write("ª   " + line + "\n");
        }
      }
      writer.write("\n"); // Add an empty line after file content
    }
  }

  private static void writeIndent(FileWriter writer, int level) throws IOException {
    for (int i = 0; i < level; i++) {
      writer.write("ª   ");
    }
  }
}

