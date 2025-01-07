package utils;

public class ASCIIArt {
    public static void display(String text) {
        System.out.println("====================================");
        System.out.println("🎶 " + text + " 🎶");
        System.out.println("====================================\n");
    }

    public static void displayMenuTitle() {
        System.out.println("\n--- Music Library Management System ---");
    }

    public static void displaySection(String sectionName) {
        System.out.println("\n--- " + sectionName + " ---");
    }
}
