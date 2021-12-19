package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;


/**
 * this program convert an image to character image that every sub image will replace in character that
 * have the closest brightness to the average of grey sub image.
 * the class  constructor get an image and in run function you can start the process.
 * few command are allowed in run time,
 * see details below at help section or type help during run time.
 */
public class Shell {
    private static final String CMD_EXIT = "exit";
    private static final String START_LINE = ">>> ";
    private static final String CMD_CHAR_PRESENT = "chars";
    private static final String CMD_ADD = "add";
    private static final String CMD_REMOVE = "remove";
    private static final String HELP = "help";
    private static final String ERROR_MSG = "this commend will not accept second or third parameter";
    private static final String THIS_COMMEND_GET_SECOND_PARAMETER = "this commend get second parameter ";
    private static final String ADD_REMOVE_ERROR_MSG2 = "/{char}/{character}-{character}(low case)/";
    private static final String CMD_ADD_ALL = "all";
    private static final String CMD_ADD_SPACE = "space";
    private static final String CMD_CHANGE_RESOLUTION = "res";
    private static final String CMD_CHANGE_RESOLUTION_UP = "up";
    private static final String CMD_CHANGE_RESOLUTION_DOWN = "down";
    private static final String CHANGE_WIDTH_MSG = "Width set to ";
    private static final String MAX_RESOLUTION_MSG = "its maximum resolution that optional:";
    private static final String MIN_RESOLUTION_MSG = "its minimum resolution that optional";
    private static final String CMD_CONSOLE = "console";
    private static final String CMD_RENDER = "render";
    private static final String INIT_CHARSET = "0-9";
    private static final char LEST_LETTER_AT_ASCII = '~';
    private static final String ERROR_MSG_FOR_WRONG_CMD = "the command that are ok is: add/remove/res with " +
            "second parameter,\nand render/console/chars/exit without second param in lowercase letters. " +
            "type help for more information";
    private static final Character SPACE = ' ';
    private static final String FONT_NAME = "Courier New";
    private static final String OUTPUT_FILENAME = "out.html";
    private static final String HELP_MASSAGE = "" +
            "this program convert an image to character image that every sub image will replace in character that" +
            "\n\thave closest brightness to the sub image. (default is converting to numbers characters) the command" +
            " \n\tthat can be using in run time is:\n" +
            "add {char}  - for adding character to the subset, more possible option is  add all for all characters" +
            "\n\tthat possible, add space, and add a-c for range (low case)\n" +
            "remove - have same option as add. see above.\n" +
            "chars - for show all chars that are in list.\n" +
            "res up/down - to double or split image resolution (default set to image_width/64).\n" +
            "console - to show output at console when you finish process in command 'render'.\n" +
            "render  - to render image. default save to file out.html, see also console command for details.\n" +
            "exit - to exit this process";

    private final BrightnessImgCharMatcher charMatcher;
    private AsciiOutput output = new HtmlAsciiOutput(OUTPUT_FILENAME, FONT_NAME);
    private final Set<Character> charSet = new HashSet<>();
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private static final int INITIAL_CHARS_IN_ROW = 64;
    private static final int FIXED_MULTIPLICATION = 2;
    private static final int ADD_RANGE_OF_LETTERS = 3;
    private final int minCharsInRow;
    private final int maxCharsInRow;
    private int charsInRow;
    private final Image img;


    /**
     * constructor. get image to convert to characters image
     *
     * @param img image to convert
     */
    public Shell(Image img) {
        this.img = img;
        minCharsInRow = Math.max(1, img.getWidth() / img.getHeight());
        maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        charMatcher = new BrightnessImgCharMatcher(img, FONT_NAME);
        add_remove(INIT_CHARSET, charSet::add);

    }

    /**
     * starting the process of converting.
     */
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
                        output = new ConsoleAsciiOutput();
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
                case HELP:
                    System.out.println(HELP_MASSAGE);
                    break;
                default:
                    System.out.println(ERROR_MSG_FOR_WRONG_CMD);
            }
            System.out.print(START_LINE);
            cmd = scanner.next();
            param = scanner.nextLine().trim();
        }
    }

    /**
     * implementation details: easy to understand.
     */
    private void render() {
        if (!charSet.isEmpty() && img != null) {
            Character[] chars = new Character[charSet.size()];
            char[][] convertedImg = charMatcher.chooseChars(charsInRow, charSet.toArray(chars));
            output.output(convertedImg);
        }
    }

    /**
     * implementation details: running using stream to print all letter in char set.
     */
    private void showChars() {
        charSet.stream().sorted().forEach(c -> System.out.print(c + " "));
        System.out.println();
    }

    /**
     * implementation details: get rest of the command add/remove, and lambda function to handle with the input.
     */
    private void add_remove(String param, Consumer<Character> addOrRemove) {
        String[] next = param.split("" + SPACE);
        if (param.split("" + SPACE).length != 1) {
            System.out.println(ERROR_MSG);
            return;
        }
        if (param.length() == 1) {
            addOrRemove.accept(param.charAt(0));
        } else if (param.equals(CMD_ADD_SPACE)) {
            addOrRemove.accept(SPACE);
        } else if (param.equals(CMD_ADD_ALL)) {
            Stream.iterate(SPACE, c -> c <= LEST_LETTER_AT_ASCII, c -> (char) ((int) c + 1)).forEach(addOrRemove);
        } else if (next[0].length() == ADD_RANGE_OF_LETTERS) {
            char first = param.charAt(0);
            char second = param.charAt(ADD_RANGE_OF_LETTERS - 1);
            if (first > SPACE && first <= LEST_LETTER_AT_ASCII && second > SPACE && second <= LEST_LETTER_AT_ASCII
                    && param.charAt(1) == '-') {
                if (second < first) {
                    first = second;
                    second = param.charAt(0);
                }
                for (char c = first; c <= second; c++) {
                    addOrRemove.accept(c);
                }
            }
        } else {
            System.out.println(THIS_COMMEND_GET_SECOND_PARAMETER + CMD_ADD_ALL + ADD_REMOVE_ERROR_MSG2 + CMD_ADD_SPACE);
        }
    }

    /**
     * implementation details: get rest of the command, and should be up/down.
     * multi/divide charsInRow parameter until the maximum/minimum accordingly and print massage of current resolution.
     * see at constructor the default min and max that optional
     */
    private void resChange(String up_or_down) {
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
