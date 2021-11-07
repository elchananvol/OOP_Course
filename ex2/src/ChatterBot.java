import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 *
 * @author Dan Nirel
 */
class ChatterBot {
    static final String REQUEST_PREFIX = "say ";

    Random rand = new Random();
    String[] repliesToIllegalRequest;
    String[] repliesToLegalRequest;
    String name;
    static final String REQUESTED_PHRASE_PLACEHOLDER = "<phrase>";
    static final String ILLEGAL_REQUEST_PLACEHOLDER = "<request>";


    ChatterBot(String name, String[] repliesToLegalRequest, String[]
            repliesToIllegalRequest) {
        this.name = name;
        this.repliesToLegalRequest = repliesToLegalRequest;
        System.arraycopy(repliesToLegalRequest, 0, this.repliesToLegalRequest,
                0, repliesToLegalRequest.length);
        this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
        System.arraycopy(repliesToIllegalRequest, 0, this.repliesToIllegalRequest,
                0, repliesToIllegalRequest.length);
    }

    /**
     * @return the name of bot
     */
    String getName() {
        return name;
    }

    /**
     * @param statement string that you feed the bot
     * @return answer of the bot. depend if the "statement" start with the prefix
     */
    String replyTo(String statement) {
        if (statement.startsWith(REQUEST_PREFIX)) {
            return respondToLegalRequest(statement);
        }
        return respondToIllegalRequest(statement);
    }


    String respondToLegalRequest(String statement) {
        String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
        return replacePlaceholderInARandomPattern(repliesToLegalRequest, REQUESTED_PHRASE_PLACEHOLDER, phrase);
    }


    /**
     * the method choose randomly from the possible answers, and replace sub string
     * @param answers  an arrey of strings that one of them will choose randomly
     * @param subStrToReplace as is.
     * @param str string that will in the string instead of the previous
     * @return the new string
     */
    String replacePlaceholderInARandomPattern(String[] answers, String subStrToReplace, String str) {
        int randomIndex = rand.nextInt(answers.length);
        String reply = answers[randomIndex];
        return reply.replaceAll(subStrToReplace, str);
    }


    String respondToIllegalRequest(String statement) {
        return replacePlaceholderInARandomPattern(repliesToIllegalRequest, ILLEGAL_REQUEST_PLACEHOLDER, statement);
    }
}
