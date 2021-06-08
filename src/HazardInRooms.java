import java.util.HashMap;
import java.util.Arrays;
import java.util.Scanner;

public class HazardInRooms {

    public static void main(String[] args) {
        int [] rooms = {0,1,2,3};

        String BAT = "Bat";
        String PIT = "pit";
        String WUMPUS = "Wumpus";
        String NOTHING = "Nothing";

        String [] hazards = {NOTHING, BAT, PIT, WUMPUS};

        HashMap<String, String> hazardMessages = new HashMap<>();

        hazardMessages.put(WUMPUS,"\"Somewhere there is a terrible smell.\"");
        hazardMessages.put(BAT,"\"Somewhere there is a rustling sound.\"");
        hazardMessages.put(PIT, "\"It sounds like the wind is blowing.\"");
        hazardMessages.put(NOTHING, "\"There seems to be nothing in that room.\"");

        int [][] links = {{1,2,3},{2,3,0},{3,0,1},{0,1,2}};

        int currentRoom = 0;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("I'm in room\t" + currentRoom);

            int[] nextRooms = links[currentRoom];  //List of accessible rooms in the current room

            /*
            while looping through the room numbers
            Print the element corresponding to the room number in the hazards array
             */
            for (int nextRoom : nextRooms) {
                String hazard = hazards[nextRoom];
                String message = hazardMessages.get(hazard);
                System.out.println(message);
            }

            System.out.println("Please enter the room number you want to " +
                    "move to from among the following numbers.");
            System.out.println(Arrays.toString(nextRooms));

            int roomNumber = scanner.nextInt();

            currentRoom = roomNumber;
        }
    }
}
