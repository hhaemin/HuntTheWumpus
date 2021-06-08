import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class MoveAndSituation {
    public static  int[] rooms = {0,1,2,3};

    public static int [][] links ={{1,2,3},{2,3,0},{3,0,1},{0,1,2}};

    public static String BAT = "Bat";
    public static String PIT = "pit";
    public static String WUMPUS = "Wumpus";
    public static String NOTHING = "Nothing";

    public static String[] hazards = {NOTHING, BAT, PIT, WUMPUS};

    public static int currentRoom = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("I'm in room\t" +  currentRoom  );

            int[] nextRooms = links[currentRoom];

            System.out.println("Please enter the room number you want to " +
                    "move to from among the following numbers.");
            System.out.println(Arrays.toString(nextRooms));

            int roomNumber = scanner.nextInt();

            move(roomNumber);
        }
    }

    /*
    This method handles the movement and subsequent events when the player moves.
    When you move, determine what dangers there are in the room and
    We process the events corresponding to each risk.
     */
    private static void move(int room) {
        //Go to the entered room.
        currentRoom = room;

        String hazardInRoom = hazards[currentRoom];

        //If there are Wumpus in the current room
        if (hazardInRoom.equals(WUMPUS)) {
            System.out.println("Eaten by Wumpus.");
            // TODO. game over
        }

        //If the current room has a pit
        else if (hazardInRoom.equals(PIT)) {
            System.out.println("fell into a pit.");
            // TODO. game over
        }

        //If there are bats in the current room
        else if (hazardInRoom.equals(BAT)) {
            System.out.println("The bat caught you and dropped you into another room.");

            Random random = new Random();

            do {
                currentRoom = random.nextInt(rooms.length);
            } while (hazards[currentRoom].equals(BAT));

            hazards[room] = NOTHING;

            while(true) {
                int newBatRoom = random.nextInt(rooms.length);

                if(newBatRoom == currentRoom) {
                    continue;
                }
                if(hazards[newBatRoom].equals(NOTHING)) {
                    hazards[newBatRoom] = BAT;
                    break;
                }
            }

            move(currentRoom);
        }
    }
}
