package ElixyrMain;

import Commands.*;
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
        jda = new JDABuilder(AccountType.BOT).setToken(Info.TOKEN).setGame(Game.playing("ely;help for help\n Running version" + Info.VERSION)).build();
        jda.addEventListener(new BotListener(commandMap));
        System.out.println("Bot connected.");



        jda.addEventListener(new Clear());
        jda.addEventListener(new Cutest());
        jda.addEventListener(new Invite());
        jda.addEventListener(new Mute());
        jda.addEventListener(new Online());
        jda.addEventListener(new Ban());
        jda.addEventListener(new Help());
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