import java.util.Scanner;
import java.util.Arrays;

public class RoomsInCave {
    public static void main(String[] args) {
        int[] rooms = {0, 1, 2, 3};

        int [][] links ={{1, 2, 3}, {2, 3, 0}, {3, 0, 1}, {0, 1, 2}};

        int currentRoom = 0;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("I'm in room\t" +  currentRoom  );
            System.out.println("Please enter the room number you want to " +
                    "move to from among the following numbers.");

            System.out.println(Arrays.toString(links[currentRoom]));

            int roomNumber = scanner.nextInt();

            currentRoom = roomNumber;
        }
    }
}
