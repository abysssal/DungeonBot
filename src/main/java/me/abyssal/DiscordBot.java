package me.abyssal;

import io.github.cdimascio.dotenv.Dotenv;
import me.abyssal.commands.commander;
import me.abyssal.listeners.botEar;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    public final Dotenv config;
    private String guildID;
    private String mainChan;
    private final ShardManager shardManager;

    public DiscordBot() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        guildID = config.get("GUILD_ID");
        mainChan = config.get("MAIN_CHANNEL_ID");
        
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.IDLE);
        builder.setActivity(Activity.watching("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
        shardManager = builder.build();

        shardManager.addEventListener(new botEar(), new commander());
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public Dotenv getConfig() {
        return config;
    }

    public static void main(String[] args) {
            try {
                DiscordBot bot = new DiscordBot();
            } catch (LoginException e) {
                System.out.println("not a valid bot token buddy");
            }
        }
}