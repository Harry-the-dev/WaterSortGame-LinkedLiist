
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class WaterSort {
    Character top = null;
    // create constants for colors
    static Character red = new Character('r');
    static Character blue = new Character('b');
    static Character yellow = new Character('g');
    // Bottles declaration
    static int maxColours = 4;
    static int maxBottles = 5;
    static int MovesMade = 0;
    static String Message;

    public static void showAll(StackAsMyArrayList bottles[]) {
        System.out.println("\n");
        for (int i = 0; i < maxBottles; i++) {
            System.out.println("Bottle " + i + ": " + bottles[i]);
        }
    }

    public static int generateRandom(int min, int max) { // Method to generate random numbers
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void fillBottles(StackAsMyArrayList bottles[], StackAsMyArrayList colours) {
        int rand_bottle = generateRandom(0, maxBottles); // picks a random bottle for each slot
        if (bottles[rand_bottle].getStackSize() < maxColours) // checks if bottle is filled , to avoid spilling ink
                                                              // !!!!!!!!!!
        {
            bottles[rand_bottle].push(colours.pop()); // pops from the color stack and pushes to the random bottle
        } else { // recursivity - if bottle is full
            fillBottles(bottles, colours);
        }

    }

    public static void fillColourStack(StackAsMyArrayList colours) {
        for (int x = maxColours; x > 0; x--) // fills the color stack
        {
            colours.push(red);
            colours.push(blue);
            colours.push(yellow);
        }
    }

    public static boolean Solved(StackAsMyArrayList bottles[]) { // checks if bottles are filled

        for (int x = 0; x < maxBottles; x++) {
            if (bottles[x].checkStackUniform() && (bottles[x].getStackSize() == 4 || bottles[x].getStackSize() == 0)) {
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidMove(StackAsMyArrayList bottles[], StackAsMyArrayList Moves, int from, int to) {

        if (bottles[from].peek() == null) {// from bottle is empty
            Message = "From Bottle is empty !!!";
            return false;
        }
        if (bottles[to].getStackSize() >= maxColours) {// to bottle is filled
            Message = "To bottle is filled !!!";
            return false;
        }
        if (from == to) {
            Message = "Cant move to the same bottle  !!!";
            return false;
        }

        if (bottles[from].peek() != null && bottles[to].peek() == null) // from bottles has colour and to is empty
        {
            do {
                Message = "Great Move";
                bottles[to].push(bottles[from].pop());
                Moves.push(from);
                Moves.push(to);

            } while (bottles[from].peek() == bottles[to].peek());
            MovesMade++;
            System.out.println("moves : " + MovesMade );
            return true;
        }

        if ((bottles[from].peek() != null && bottles[to].peek() != null)
                && bottles[from].peek().equals(bottles[to].peek())) { // if both bottles have colour and colours match
            do {
                Message = "Great Move";
                bottles[to].push(bottles[from].pop());
                Moves.push(from);
                Moves.push(to);

            } while (bottles[from].peek() == bottles[to].peek());
            MovesMade++;
            System.out.println("moves : " + MovesMade );
            return true;

        } else {
            Message = " Oops, Colours don't Match";
            return false;
        }

    }

    public static boolean validateInput(char val) {
        boolean result = false;
        switch (val) {
        case '0':
            result = true;
            break;
        case '1':
            result = true;
            break;
        case '2':
            result = true;
            break;
        case '3':
            result = true;
            break;
        case '4':
            result = true;
            break;

        case 'b':
            result = true;
            break;
        case 'B':
            result = true;
            break;
        default:
            result = false;
            break;
        }
        return result;
    }

    static void Undo(StackAsMyArrayList bottles[], StackAsMyArrayList Moves) { // reverse moves user made- pop from Moves stack- which hold intger value
                                                                               // of bottles that moves were to/from
        if (Moves.peek() != null) {

            int to = (int) (Moves.pop());
            int from = (int) (Moves.pop());
            bottles[from].push(bottles[to].pop());
            showAll(bottles);

        } else {
            System.out.println("\nNo Moves To Undo ");
            operation(bottles, Moves);
        }

    }

    static void operation(StackAsMyArrayList bottles[], StackAsMyArrayList Moves) {
        char to;
        char from;
        int From;
        int To;

        Scanner myObj = new Scanner(System.in);

        while (!Solved(bottles)) { // while bottles not solved

            try // exception handling incase user doesnt enter anything
            {
                System.out.println("Enter Bottle to pop from ( B or b to undo last move ) : ");
                from = myObj.nextLine().charAt(0);

                if (from == ('B') || from == ('b')) {
                    Undo(bottles, Moves);
                } else if (validateInput(from)) {

                    System.out.println("Enter Bottle to push to  : ");
                    to = myObj.nextLine().charAt(0);

                    if (validateInput(to)) {
                        To = Character.getNumericValue(to);
                        From = Character.getNumericValue(from);

                        if (isValidMove(bottles, Moves, From, To)) {
                            System.out.println("\n" + Message);
                            showAll(bottles);

                        } else {
                            System.out.println("\nInvalid Move : " + Message);
                            showAll(bottles);
                        }
                    }

                } else {
                    System.out.println("Inavlid Input , Enter 0 to 4 or B/b to undo last move ");
                }
            } catch (Exception e) {
                validateInput('n');
            }

        }
    }

    public static void main(String args[]) {

        StackAsMyArrayList bottles[] = new StackAsMyArrayList[5];
        bottles[0] = new StackAsMyArrayList<Character>();
        bottles[1] = new StackAsMyArrayList<Character>();
        bottles[2] = new StackAsMyArrayList<Character>();
        bottles[3] = new StackAsMyArrayList<Character>();
        bottles[4] = new StackAsMyArrayList<Character>();

        StackAsMyArrayList colours = new StackAsMyArrayList<Character>();// array to hold colours
        StackAsMyArrayList Moves = new StackAsMyArrayList<Integer>();

        fillColourStack(colours); // fills the color stack with 4* of each colours

        for (int count = 0; count < 12; count++) // 12 slots
        {
            fillBottles(bottles, colours);
        }

        showAll(bottles);

        long startTime = System.currentTimeMillis(); // start time to measure game duarion

        operation(bottles, Moves);

        long endTime = System.currentTimeMillis(); // game end duration

        long millis = endTime - startTime;
        String time = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        System.out.println("\n====================== Congratulation Water Sort Solved ======================= ");
        System.out.println("---------------------- Game Duration : " + time);
        System.out.println("---------------------- Moves Made : " + MovesMade + "\n");

    }
}