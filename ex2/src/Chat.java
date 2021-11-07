import java.util.Scanner;

public class Chat {
    public static void main(String[] args) {


        ChatterBot[] bots = {null, null};
        String[] stringForFirstBot = {"What <request>? say say", "say . you should say \"say\""};
        String[] stringForSecondBot = {"Whaaat?", "say \"say\"!"};
        String[] strings_a= {"To say <phrase>? okey i say out loud \"<phrase>\""};
        String[] strings_b= {"sey ? Okey here i say \"<phrase>\"!!"};
        bots[0] = new ChatterBot("me",strings_a,stringForFirstBot);
        bots[1] = new ChatterBot("my wife",strings_b, stringForSecondBot);
        Scanner scanner = new Scanner(System.in);
        String statement = "hello";
        while(true) {
            for (ChatterBot bot : bots) {
                statement = bot.replyTo(statement);
                System.out.print(bot.getName() +": "+ statement);
                scanner.nextLine();
            }
        }
    }


}
