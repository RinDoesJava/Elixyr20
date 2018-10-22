package ElixyrMain;

import Commands.*;
import Images.Danbooru;
import Images.Search;
import Images.e621;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.Scanner;





public class Main implements Runnable{

    private final JDA jda;
    private CommandMap commandMap = new CommandMap(this);
    private final Scanner scanner = new Scanner(System.in);

    private boolean running;

    public Main() throws LoginException, IllegalArgumentException, RateLimitedException {
        jda = new JDABuilder(AccountType.BOT).setToken(Info.TOKEN).setGame(Game.playing("ely;help for help")).build();
        jda.addEventListener(new BotListener(commandMap));
        System.out.println("Bot connected.");



        jda.addEventListener(new Clear());
        jda.addEventListener(new Cutest());
        jda.addEventListener(new Invite());
        jda.addEventListener(new Mute());
        jda.addEventListener(new Online());
        jda.addEventListener(new Ban());
        jda.addEventListener(new Help());
        jda.addEventListener(new BotInfo());
        jda.addEventListener(new Kick());
        jda.addEventListener(new UserInfo());
        jda.addEventListener(new Say());
        jda.addEventListener(new Search());
        jda.addEventListener(new e621());
        jda.addEventListener(new Danbooru());
        jda.addEventListener(new Images());
    }

    public JDA getJda() {
        return jda;
    }


    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        running = true;

        while (running) {
            if(scanner.hasNextLine()) commandMap.commandConsole(scanner.nextLine());
        }

        scanner.close();
        System.out.println("Bot stopped.");
        jda.shutdown();
        commandMap.save();
        System.exit(0);
    }


    public static void main(String[] args) {
        try {



            Main botDiscord = new Main();
            new Thread(botDiscord, "bot").start();
        } catch (LoginException | IllegalArgumentException | RateLimitedException e) {
            e.printStackTrace();
        }
    }
}
