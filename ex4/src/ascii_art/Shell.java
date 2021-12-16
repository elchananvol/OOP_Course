package ascii_art;


import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Shell {
    private static final String CMD_EXIT = "exit";
    private static final String START_LINE = ">>> ";
    private static final String CMD_CHAR_PRESENT = "chars";
    private static final String CMD_ADD = "add";
    private static final String CMD_REMOVE = "remove";
    private static final String ERROR_MSG = "this commend will not accept second or third parameter";
    private static final String THIS_COMMEND_GET_SECOND_PARAMETER = "this commend get second parameter ";
    private static final String ADD_REMOVE_ERROR_MSG2 = "/{char}/{character}-{character}(low case)/";
    private static final String CMD_ADD_ALL = "all";
    private static final String CMD_ADD_SPACE = "space";
    private static final String CMD_CHANGE_RESOLUTION = "res";
    private static final String CMD_CHANGE_RESOLUTION_UP = "up";
    private static final String CMD_CHANGE_RESOLUTION_DOWN = "down";

    private static final Character[] init_charset = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private static final String CHANGE_WIDTH_MSG = "Width set to ";
    private static final String MAX_RESOLUTION_MSG = "its maximum resolution that optional:";
    private static final String MIN_RESOLUTION_MSG = "its minimum resolution that optional";
    private static final String CMD_CONSOLE = "console";
    private static final String CMD_RENDER = "render";
    private static final String ERROR_MSG_FOR_WRONG_CMD = "the command that are ok is: add/remove/res with " +
            "second parameter,\nand render/console/chars/exit without second param in lowercase letters";
    private static final int FIXED_MULTIPLICATION = 2;
    private static final int ADD_RANGE_OF_LETTERS = 3;
    private static final Character SPACE = ' ';
    private boolean console = false;
    private static final String FONT_NAME = "Courier New";
    private static final String OUTPUT_FILENAME = "out.html";
    private BrightnessImgCharMatcher charMatcher;


    private final Set<Character> charSet = Stream.of(init_charset).collect(Collectors.toSet());

    private static final int INITIAL_CHARS_IN_ROW = 64;
    private final int minCharsInRow;
    private final int maxCharsInRow;
    private int charsInRow;
    //    new HashSet<>().addAll(init_charset);
    private final Image img;

    public Shell(Image img) {
        this.img = img;
        if (img == null) { //todo
            Logger.getGlobal().severe("Failed to open image file ");
        }
        minCharsInRow = Math.max(1, img.getWidth() / img.getHeight());
        maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        charMatcher = new BrightnessImgCharMatcher(img, FONT_NAME);

    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(START_LINE);
        String cmd = scanner.next();
        var param = scanner.nextLine().trim();
        while (!(cmd.equals(CMD_EXIT) && param.isEmpty())) {

            switch (cmd) {
                case CMD_CHAR_PRESENT:
                    if (param.isEmpty()) {
                        showChars();
                    } else {
                        System.out.println(ERROR_MSG);
                    }
                    break;
                case CMD_CONSOLE:
                    if (param.isEmpty()) {
                        console = true;
                    } else {
                        System.out.println(ERROR_MSG);
                    }
                    break;
                case CMD_RENDER:
                    if (param.isEmpty()) {
                        render();
                    } else {
                        System.out.println(ERROR_MSG);
                    }
                    break;
                case CMD_ADD:
                    add_remove(param, charSet::add);
                    break;
                case CMD_REMOVE:
                    add_remove(param, charSet::remove);
                    break;
                case CMD_CHANGE_RESOLUTION:
                    resChange(param);
                    break;
                default:
                    System.out.println(ERROR_MSG_FOR_WRONG_CMD);
            }

//            System.out.println(param);
            System.out.print(START_LINE);
            cmd = scanner.next();
            param = scanner.nextLine().trim();
        }
    }

    private void render() {
        Character[] chars = new Character[charSet.size()];
        char[][] convertedImg = charMatcher.chooseChars(charsInRow, charSet.toArray(chars));
        AsciiOutput output = new ConsoleAsciiOutput();
        if (!console) {
            output = new HtmlAsciiOutput(OUTPUT_FILENAME, FONT_NAME);
        }
        output.output(convertedImg);


    }

    private void showChars() {
        charSet.stream().sorted().forEach(c -> System.out.print(c + " "));
        System.out.println();
    }

    private void add_remove(String param, Consumer<Character> addOrRemove) {
        //System.out.println(param.length());
        String[] next = param.split(" ");
//        System.out.println(Arrays.toString(next));
        if (next.length != 1) {
            System.out.println(ERROR_MSG);
            return;
        }
//        System.out.println(param.length());
        if (next[0].length() == 1) {
            addOrRemove.accept(param.charAt(0));
            return;

        }
        if (next[0].equals(CMD_ADD_SPACE)) {
            addOrRemove.accept(SPACE);
            return;

        }

        if (next[0].equals(CMD_ADD_ALL)) {
//            for (char c = ' '; c <= '~'; c++) {
//                addOrRemove.accept(c);
//            }
            Stream.iterate(SPACE, c -> c <= '~', c -> (char) ((int) c + 1)).forEach(addOrRemove);
            return;
        }
        if (next[0].length() == ADD_RANGE_OF_LETTERS) {
            char first = param.charAt(0);
            char second = param.charAt(ADD_RANGE_OF_LETTERS -1);
            //todo
            if (first >= 'a' && first <= 'z' && second >= 'a' && second <= 'z' && param.charAt(1) == '-') {
                if (second < first) {
                    first = second;
                    second = param.charAt(0);

                }
                for (char c = first; c <= second; c++) {
                    addOrRemove.accept(c);

                }
                return;


            }
        }

        System.out.println(THIS_COMMEND_GET_SECOND_PARAMETER + CMD_ADD_ALL + ADD_REMOVE_ERROR_MSG2 + CMD_ADD_SPACE);

    }

    private void resChange(String up_or_down) {
        if (!charSet.isEmpty()) {
            switch (up_or_down) {
                case CMD_CHANGE_RESOLUTION_UP:
                    charsInRow *= FIXED_MULTIPLICATION;
                    if (charsInRow >= maxCharsInRow) {
                        charsInRow = maxCharsInRow;
                        System.out.println(MAX_RESOLUTION_MSG + charsInRow);

                    } else {
                        System.out.println(CHANGE_WIDTH_MSG + charsInRow);
                    }
                    break;

                case CMD_CHANGE_RESOLUTION_DOWN:
                    charsInRow /= FIXED_MULTIPLICATION;
                    if (charsInRow <= minCharsInRow) {
                        charsInRow = minCharsInRow;
                        System.out.println(MIN_RESOLUTION_MSG + charsInRow);
                    } else {
                        System.out.println(CHANGE_WIDTH_MSG + charsInRow);
                    }
                    break;
                default:
                    System.out.println(THIS_COMMEND_GET_SECOND_PARAMETER + CMD_CHANGE_RESOLUTION_UP +
                            "/" + CMD_CHANGE_RESOLUTION_DOWN);
            }
        }

    }


}
