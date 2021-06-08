import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class HuntTheWumpus {
    public static Integer[] rooms = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19
    };
    public static Integer[][] links = {
            {1, 7, 4}, {0, 9, 2}, {1, 11, 3}, {2, 13, 4}, {3, 0, 5},
            {4, 14, 6}, {5, 16, 7}, {6, 0, 8}, {7, 17, 9}, {8, 1, 10},
            {9, 18, 11}, {10, 2, 12}, {11, 19, 13}, {12, 3, 14}, {13, 5, 15},
            {14, 19, 16}, {15, 6, 17}, {16, 8, 18}, {17, 10, 19}, {18, 12, 15}
    };

    public static String BAT = "Bat";
    public static String PIT = "pit";
    public static String WUMPUS = "Wumpus";
    public static String NOTHING = "Nothing";

    public static ArrayList<String> hazards = new ArrayList<>();
    public static HashMap<String, String> hazardMessages = new HashMap<>();

    public static Random random = new Random();
    public static Scanner scanner = new Scanner(System.in);

    public static boolean gameOver = true;

    public static int currentRoom;
    public static int arrowCount;
    public static int wumpusRoom;

    public static void main(String[] args) {
        showIntro();

        initializeStaticValues();

        while (true) {
            initializePlayVariables();

            setupHazards();

            delay(1000L);
            System.out.println("\n...");
            delay(1000L);
            System.out.println("...\n");
            delay(1000L);
            System.out.println("entered the cave...\n");
            delay(1000L);
            System.out.println("\"It's a creepy place.\"");
            delay(1000L);

            game();

            selectReplay();
        }
    }

    private static void game() {
        while (gameOver == false) {
            System.out.println("I'm in room\t" +  currentRoom );
            System.out.println("Choose your action.");
            System.out.println("1. Move");
            System.out.println("2. Arrow shooting");
            System.out.println("3. Aisle list");
            System.out.println("0. Quit the game");

            String action = scanner.nextLine();

            if (action.equals("1")) {
                Integer[] nextRooms = links[currentRoom];

                System.out.println("\nWhich room do you want to move?");
                System.out.println(Arrays.toString(nextRooms));

                String nextRoomInput = scanner.nextLine();
                int nextRoom = parseIntegerOrNegative1(nextRoomInput);

                if (Arrays.asList(nextRoom).contains(nextRoom)) {
                    move(nextRoom);
                } else {
                    System.out.println("You cannot move to the selected room.");
                }
            } else if (action.equals("2")) {
                Integer[] nextRooms = links[currentRoom];

                System.out.println("\nWhich room do you want to shoot arrow?");
                System.out.println(Arrays.toString(nextRooms));

                String targetRoomInput = scanner.nextLine();
                int targetRoom = parseIntegerOrNegative1(targetRoomInput);

                if(Arrays.asList(nextRooms).contains(targetRoom)) {
                    shoot(targetRoom);
                } else {
                    System.out.println("You cannot shoot arrows in the selected room.");
                }
            }

            else if(action.equals("3")) {
                System.out.println("The passages leading to the current room are:");
                System.out.println(Arrays.asList(links[currentRoom]));
            }

            else if(action.equals("0")) {
                System.out.println("Quit the game.");
                gameOver = true;
            }
            else {
                System.out.println("Invalid input.");
            }
        }
    }

    private static void showIntro() {
        try {
            FileInputStream inputStream = new FileInputStream("src/intro.txt");
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
                delay(500L);
            }
        } catch (FileNotFoundException e) {
            System.out.println("I can't read the intro, so I skip it.");
        }
    }

    private static void initializePlayVariables() {
        gameOver = false;

        currentRoom = random.nextInt(rooms.length);
        arrowCount = 5;
    }

    public static void initializeStaticValues() {
        hazardMessages.put(WUMPUS, "\"Somewhere there is a terrible smell.\"");
        hazardMessages.put(BAT, "\"Somewhere there is a rustling sound.\"");
        hazardMessages.put(PIT, "\"It sounds like the wind is blowing.\"");
        hazardMessages.put(NOTHING, "\"There seems to be nothing in that room.\"");
    }

    public static void setupHazards() {
        if (hazards.size() == 0) {
            for (int i = 0; i < rooms.length; i = i + 1) {
                hazards.add(NOTHING);
            }
        }

        for (int i = 0; i < rooms.length; i = i + 1) {
            hazards.set(i, NOTHING);
        }

        String[] ordinals = {WUMPUS, BAT, BAT, PIT, PIT};

        for (String hazard : ordinals) {
            int room;

            while (true) {
                room = random.nextInt(rooms.length);

                if (isTooCloseWithPlayer(room)) {
                    continue;
                }

                if (hazards.get(room).equals(NOTHING)) {
                    break;
                }
            }

            hazards.set(room, hazard);

            if (hazard.equals(WUMPUS)) {
                wumpusRoom = room;
            }
        }
    }

    public static boolean isTooCloseWithPlayer(int room) {
        if (currentRoom == room) {
            return true;
        }

        List<Integer> linkedRooms = Arrays.asList(links[currentRoom]);
        if (linkedRooms.contains(room)) {
            return true;
        }

        return false;
    }

    public static void move(int room) {
        currentRoom = room;
        System.out.println("I moved to room" + currentRoom);

        String hazard = hazards.get(currentRoom);

        delay(1000L);

        if(hazard.equals(WUMPUS)) {
            System.out.println("\"aaaaaaaaaa!\"");
            delay(300L);
            System.out.println("Wumpus has eaten you.");
            gameOver = true;
        }

        else if (hazard.equals(PIT)) {
            System.out.println("\"aaaaaaaaaaa~!\"");
            delay(1000L);
            System.out.println("thud!");
            delay(300L);
            System.out.println("You fell into a pit.");
            delay(300L);
            System.out.println("You can no longer hunt Wumpus.");
            gameOver = true;
        }

        else if (hazard.equals(BAT)) {
            System.out.println("thud!");
            delay(300L);
            System.out.println("The bat caught you and dropped you into another room.");

            do {
                currentRoom = random.nextInt(rooms.length);
            }while (hazards.get(currentRoom).equals(BAT));

            hazards.set(room, NOTHING);

            while(true) {
                int newBatRoom = random.nextInt(rooms.length);

                if(newBatRoom == currentRoom) {
                    continue;
                }

                if(hazards.get(newBatRoom).equals(NOTHING)) {
                    hazards.set(newBatRoom,BAT);
                    break;
                }
            }

            move(currentRoom);
        }
        else {
            List<Integer> nextRooms = Arrays.asList(links[currentRoom]);

            System.out.println("\nlook at the connecting passages");
            for (int nextRoom : nextRooms) {
                delay(700L);
                String hazardAround = hazards.get(nextRoom);
                System.out.println(hazardMessages.get(hazardAround));
            }
            Collections.shuffle(nextRooms);
        }
    }

    public static void shoot(int room) {
        arrowCount = arrowCount -1;

        delay(1000L);
        System.out.println("Shuung");
        delay(300L);

        if(hazards.get(room).equals(WUMPUS)) {
            System.out.println("pushhhh");
            delay(100L);
            System.out.println("\"ku eh!\"");
            delay(1000L);
            System.out.println("Congratulations. You killed the Wumpus!");
            gameOver = true;
        }

        else {
            System.out.println("(...)");
            delay(1000L);
            System.out.println("\"I wasted my arrows.\"");

            if(arrowCount == 0) {
                delay(300L);
                System.out.println("\"Oh my, no arrows left!\"");
                delay(300L);
                System.out.println("You have failed the Wumpus Hunt");
                gameOver = true;
            }

            else if (random.nextInt(4) != 0) {
                System.out.println("You have awakened the Wumpus.");
                delay(1000L);

                Integer[] nextRooms = links[wumpusRoom];

                int nextRoom = nextRooms[random.nextInt(3)];

                if(hazards.get(nextRoom).equals(NOTHING)) {
                    hazards.set(wumpusRoom, NOTHING);
                    hazards.set(nextRoom, WUMPUS);
                    wumpusRoom = nextRoom;
                }

                if(wumpusRoom ==currentRoom) {
                    System.out.println("\"aaaaaaaaaa!\"");
                    delay(500L);
                    System.out.println("Wumpus has eaten you.");
                    gameOver =true;
                }

                else if(wumpusRoom == nextRoom) {
                    System.out.println("Wumpus ran away.");
                }
            }
        }
    }

    private static void selectReplay() {
        System.out.println("The game is over. Would you like to play again?");

        while (true) {
            System.out.println("(0: Quit, 1: replay)");
            String action = scanner.nextLine();

            if (action.equals("0")) {
                System.out.println("Quit the game.");
                System.exit(0);
            } else if (action.equals("1")) {
                System.out.println("Replay the game.");
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    public static int parseIntegerOrNegative1(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void delay(Long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
