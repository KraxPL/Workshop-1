package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasks;
    public static void main(String[] args) {

        File file = new File("tasks.csv");
        Path path = Paths.get(String.valueOf(file));
        tasks = loadDataToTab("tasks.csv");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                System.out.println(e1);
            }
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists.");
        }
        String line = "", content = "";
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String choice = scanner.nextLine();

            switch (choice) {
                case "add":
                    System.out.println(ConsoleColors.GREEN_BACKGROUND + "'add' has been chosen" + ConsoleColors.RESET);
                    adding(String.valueOf(file));
                    System.out.println("Task added successfully");
                    break;
                case "remove":
                    System.out.println(ConsoleColors.GREEN_BACKGROUND + "'remove' has been chosen" + ConsoleColors.RESET);
                    removing(tasks, getTheNumber());
                    System.out.println("Value was successfully deleted. Changes in the list will be seen after program restart");
                    break;
                case "list":
                    System.out.println(ConsoleColors.GREEN_BACKGROUND + " 'list' has been chosen" + ConsoleColors.RESET);
                    Scanner scanner1 = null;
                    try {
                        scanner1 = new Scanner(file);
                    } catch (FileNotFoundException e2) {
                        System.err.println("File not found" + e2);
                    }
                    int i = -1;
                    while (scanner1.hasNextLine()) {
                        line = scanner1.nextLine();
                        i++;
                        System.out.println(i + ": " + line);
                    }
                    break;
                case "exit":
                    System.out.println(ConsoleColors.GREEN_BACKGROUND + "'exit' has been chosen" + ConsoleColors.RESET);
                    saving(String.valueOf(file), tasks);
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
        }
    }
        public static void adding(String file){
        Path path = Paths.get(file);
            Scanner scanner2 = new Scanner(System.in);
            String input = "";
            System.out.println("Please add task description");
            input = scanner2.nextLine() + ", ";
            System.out.println("Please add task due date");
            input += scanner2.nextLine() + ", ";
            System.out.println("Please specify is your task important true/false");
            input += scanner2.nextBoolean();
            try {
                Files.writeString(path, input + " \n", StandardOpenOption.APPEND);
            } catch (IOException e3) {
                System.out.println(e3);
            }
        }
    public static String[][] loadDataToTab(String file) {
        Path dir = Paths.get(file);
        if (!Files.exists(dir)) {
            System.out.println("File not exist.");
            System.exit(0);
        }
        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(dir);
            tab = new String[strings.size()][strings.get(0).split(",").length];
            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }
    public static void removing(String[][] tab, int number) {
        try {
            if (number < tab.length) {
                tasks = ArrayUtils.remove(tab, number);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist in tab");
        }
    }
    public static int getTheNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove");

        String n = scanner.nextLine();
        while (!isNumberGreaterEqualZero(n)) {
            System.out.println("Incorrect argument passed. Please give number >= 0");
            scanner.nextLine();
        }
        return Integer.parseInt(n);
    }
    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }
    public static void saving(String fileName, String[][] tab) {
        Path dir = Paths.get(fileName);
        String[] lines = new String[tasks.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }
        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}