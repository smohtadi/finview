package com.smohtadi.finView.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvParser {

  public List<String[]> parse(String path, int n) {
    List<String[]> list =  new ArrayList<>();
    try (FileInputStream fileInputStream = new FileInputStream(path); Scanner scanner = new Scanner(fileInputStream, "UTF-8")) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] lineSplit = line.split(",");
        int n1 = lineSplit.length;
        String[] data = new String[n];
        for (int i = 0; i < n; i++) {
          if (i < n1) {
            if (lineSplit[i].trim().length() == 0)
              data[i] = null;
            data[i] = lineSplit[i];
          }
        }
        list.add(data);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }
}
