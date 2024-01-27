import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Solution launch point for exercise, responsible for reading the input from a file and processing it.
 *
 * @author Nicholas Fox
 * @version 1.0
 */
public class Solution {
    private static final String USAGE = "Pass the file to be used as data as the sole argument to this program";
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(USAGE);
        } else {
            Scanner scanner = null;
            try {
                File inputFile = new File(args[0]);
                scanner = new Scanner(inputFile);
                System.out.println(start(scanner));
                scanner.close();
            } catch (FileNotFoundException fnfe) {
                System.out.println("Please pass a filepath as the argument for this program.");
            } catch (InputMismatchException ime) {
                System.out.println("Input file is malformed.");
                scanner.close();
            }
        }
    }

    public static String start(Scanner scanner) {
        List<String> data = getDataFromFile(scanner);

        if (!data.isEmpty()) {
            List<Resident> residents = new ArrayList<>();
            for (String datum : data) {
                Resident resident = constructResident(datum);
                if (resident != null) {
                    residents.add(resident);
                }
            }

            if (data.size() != residents.size()) {
                throw new InputMismatchException();
            }

            SortedMap<Address, List<Resident>> households = getHouseholds(residents);

            return getHouseholdsDisplay(households);
        }

        return "";
    }
    static String getHouseholdsDisplay(SortedMap<Address, List<Resident>> households) {
        StringBuilder res = new StringBuilder();

        for (Map.Entry<Address, List<Resident>> entry : households.entrySet()) {
            res.append(String.format("The household at %s has %d occupant%s:\n",
                    entry.getKey(), entry.getValue().size(), entry.getValue().size() == 1 ? "" : "s"));
            entry.getValue().stream()
                    .filter(resident -> resident.getAge() > 18)
                    .sorted()
                    .forEach(resident -> res.append("    ").append(resident).append("\n"));
        }

        return res.toString();
    }

    static SortedMap<Address, List<Resident>> getHouseholds(List<Resident> residents) {
        List<Address> addresses = residents.stream()
                .map(Resident::getAddress)
                .distinct()
                .collect(Collectors.toList());

        SortedMap<Address, List<Resident>> households = new TreeMap<>();
        for (Address address : addresses) {
            households.put(address, new ArrayList<>());
        }

        for (Resident resident : residents) {
            households.get(resident.getAddress()).add(resident);
        }

        return households;
    }

    static Resident constructResident(String line) {
        // Input expected as FirstName,LastName,AddressStreet,AddressCity,AddressState,Age
        line = line.replaceAll("\",\"", "\"-\"")
                .replaceAll("\"", "");
        String[] data = line.split("-");
        if (data.length == 6) {
            String firstName = data[0];
            String lastName = data[1];
            String addressLine1 = data[2];
            String addressCity = data[3];
            String addressState = data[4];
            int age = Integer.parseInt(data[5]);

            Address address = new Address(addressLine1, addressCity, addressState);
            return new Resident(firstName, lastName, address, age);
        }
        return null;
    }

    static List<String> getDataFromFile(Scanner input) {
        List<String> res = new ArrayList<>();
        while (input.hasNextLine()) {
            res.add(input.nextLine());
        }
        return res;
    }
}