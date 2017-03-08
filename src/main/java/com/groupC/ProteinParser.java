package com.groupC;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProteinParser {

    private Scanner sc;
    private HashMap<String, String> proteins = new HashMap();

    public ProteinParser(String inputFile) {
        try {
            sc = new Scanner(new File(inputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> createMap() {
        while (sc.hasNext("[>].*")) {
            String protein = "";
            sc.useDelimiter("\\n");
            String creature = sc.next().substring(1).replaceAll("([0-9].*)", " ").trim();
            while (sc.hasNext("[A-Za-z].*")) {
                protein += sc.next();
            }
            proteins.put(creature, protein);
        }
        return proteins;
    }
}
