import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Shoot {
    public static  int[] rooms = {0,1,2,3};

    public static int [][] links ={{1,2,3},{2,3,0},{3,0,1},{0,1,2}};

    public static String BAT = "Bat";
    public static String PIT = "pit";
    public static String WUMPUS = "Wumpus";
    public static String NOTHING = "Nothing";

    public static String[] hazards = {NOTHING, BAT, PIT, WUMPUS};

    public static int currentRoom = 0;
    public static int wumpusRoom = 3;

    public static int arrowCount = 5;

    public static Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("I'm in room\t" + currentRoom);

            int[] nextRooms = links[currentRoom];

            System.out.println("Which room would you like to shoot the arrow in?");
            System.out.println(Arrays.toString(nextRooms));

            int roomNumber = scanner.nextInt();

            shoot(roomNumber);
        }
    }

    public static void shoot(int room){
        if (hazards[room].equals(WUMPUS)) {
            System.out.println("Congratulations. You killed the Wumpus!");
        } else {
            arrowCount = arrowCount -1;

            if(arrowCount == 0) {
                System.out.println("You have failed your Wumpus hunt.");
            } else if (random.nextInt(4) != 0){
                System.out.println("Wumpus has awakened.");

                int[] nextRooms = links[wumpusRoom];
                int nextRoom = nextRooms[random.nextInt(3)];

                if(hazards[nextRoom].equals(NOTHING)) {
                    hazards[wumpusRoom] = NOTHING;
                    hazards[nextRoom] = WUMPUS;
                    wumpusRoom = nextRoom;
                }

                if (wumpusRoom == currentRoom) {
                    System.out.println("Eaten by Wumpus.");
                    System.out.println("You have failed your Wumpus hunt.");
                    //TODO. game over1

                } else if (wumpusRoom == nextRoom) {
                    System.out.println("Wumpus ran away.");
                }
            }
        }
    }
}
