import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private HashMap<String, String> contacts;
    private String filePath;

    public AddressBook(String filePath) {
        this.contacts = new HashMap<>();
        this.filePath = filePath;
        load();
    }
    
    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    contacts.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void create(String number, String name) {
        if (contacts.containsKey(number)) {
            System.out.println("El número ya existe en la agenda.");
        } else {
            contacts.put(number, name);
            System.out.println("Contacto agregado.");
            save();
        }
    }

    public void delete(String number) {
        if (contacts.remove(number) != null) {
            System.out.println("Contacto eliminado.");
            save();
        } else {
            System.out.println("El contacto no existe.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook("contacts.txt");

        while (true) {
            System.out.println("Menú:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea

            switch (choice) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    System.out.print("Introduce el número: ");
                    String number = scanner.nextLine();
                    System.out.print("Introduce el nombre: ");
                    String name = scanner.nextLine();
                    addressBook.create(number, name);
                    break;
                case 3:
                    System.out.print("Introduce el número a eliminar: ");
                    String numberToDelete = scanner.nextLine();
                    addressBook.delete(numberToDelete);
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
