package me.abyssal.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class commander extends ListenerAdapter {

    public int width;
    public int height;

    public int rooms;
    public String difficulty;
    public String levelToMake;

    public String player = ":monkey:";
    public String wall = ":red_square:";
    public String blank = ":black_large_square:";
    public String spikeTrap = ":large_orange_diamond:";
    public String slownessTrap = ":spider_web:";

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("startnewdungeon", "starts a new dungeon with a fixed amount of rooms and set difficulty")
                .addOptions(new OptionData(OptionType.INTEGER, "rooms", "the number of rooms in the dungeon", true)
                        .setMinValue(1)
                        .setMaxValue(15)
                )
                .addOptions(new OptionData(OptionType.INTEGER, "width", "how wide the blank space in the rooms will be", true)
                        .setMinValue(10)
                        .setMaxValue(30)
                )
                .addOptions(new OptionData(OptionType.INTEGER, "height", "how tall the blank space in the rooms will be", true)
                        .setMinValue(10)
                        .setMaxValue(30)
                )
                .addOptions(new OptionData(OptionType.STRING, "difficulty", "how difficult should the dungeon be, harder gives best loot", true)
                        .addChoice("very easy", "veryEasy")
                        .addChoice("easy", "easy")
                        .addChoice("medium", "medium")
                        .addChoice("hard", "hard")
                        .addChoice("very hard", "veryHard")
                        .addChoice("impossible", "impossible")
                )
        );
        commandData.add(Commands.slash("info", "get info about DungeonBot"));
        event.getJDA().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("startnewdungeon")) {
            event.deferReply().queue();

            rooms = event.getOption("rooms").getAsInt();
            width = event.getOption("width").getAsInt();
            height = event.getOption("height").getAsInt();
            difficulty = event.getOption("difficulty").getAsString();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Confirm Dungeon Creation");
            embed.setAuthor("Are you sure you want to create a dungeon with these settings?");
            embed.setDescription("Rooms: " + rooms + "\n Difficulty Level: " + difficulty + "\n Width: " + width + "\n Height: " + height);
            embed.setFooter("Made by @Abyssal#5704");
            embed.setColor(Color.red);

            event.getHook().sendMessageEmbeds(embed.build())
                    .addActionRow(
                            Button.success("yes", "Yes, create the dungeon!"),
                            Button.danger("no", "No, let me restart.")
                    ).queue();
        }

        if (event.getName().equals("info")) {
            event.deferReply().queue();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("DungeonBot Information");
            embed.setAuthor("Made by @Abyssal#5704");
            embed.setImage("https://cdn.discordapp.com/attachments/1038484728187191366/1038979598421065749/Untitled.png");
            embed.setDescription("DungeonBot is a discord bot where you can create randomly generated dungeons. You can battle monsters, get items and flex on your friends with those items you earned from the dungeons. \n \n You can find the source code here: https://github.com/abysssal/DungeonBot");
            embed.setColor(Color.blue);

            event.getHook().sendMessageEmbeds(embed.build()).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("yes")) {
            event.deferReply().queue();
            System.out.println("user has replied with yes for creating a new dungeon");
            createLevel(difficulty, width, height);

            EmbedBuilder embed = new EmbedBuilder();

            embed.setTitle("Your Dungeon");
            embed.setColor(Color.black);
            embed.setDescription(getLevelToMake());

            event.getHook().sendMessageEmbeds(embed.build())
                    .addActionRow(
                            Button.primary("moveRight", "Move Right"),
                            Button.primary("moveDown", "Move Down"),
                            Button.primary("moveUp", "Move Up"),
                            Button.primary("moveLeft", "Move Left"),
                            Button.danger("abortDungeon", "Abort Dungeon")
                    )
                    .queue();
        } else if (event.getComponentId().equals("no")) {
            System.out.println("user has decided to restart");
            event.reply("Mistakes happen, but you can try again").setEphemeral(true).queue();
        }
    }

    public void createLevel(String diff, int width, int height) {
        int enemies;
        int crates;
        int spikeTraps;
        int slownessTraps;

        int roomArea = width * height;

        int xCoordinate;
        int yCoordinate;

        String[][] level;
        level = new String[width][height];

        if (diff == "veryEasy") {
            enemies = ThreadLocalRandom.current().nextInt(1, 3);
            crates = ThreadLocalRandom.current().nextInt(0, 1);
            spikeTraps = 0;
            slownessTraps = ThreadLocalRandom.current().nextInt(0, 1);
        } else if (diff == "easy") {
            enemies = ThreadLocalRandom.current().nextInt(2, 4);
            crates = ThreadLocalRandom.current().nextInt(1, 2);
            spikeTraps = ThreadLocalRandom.current().nextInt(0, 1);
            slownessTraps = ThreadLocalRandom.current().nextInt(0, 2);
        } else if (diff == "medium") {
            enemies = ThreadLocalRandom.current().nextInt(3, 6);
            crates = ThreadLocalRandom.current().nextInt(2, 3);
            spikeTraps = ThreadLocalRandom.current().nextInt(1, 2);
            slownessTraps = ThreadLocalRandom.current().nextInt(1, 3);
        } else if (diff == "hard") {
            enemies = ThreadLocalRandom.current().nextInt(5, 8);
            crates = ThreadLocalRandom.current().nextInt(3, 4);
            spikeTraps = ThreadLocalRandom.current().nextInt(2, 3);
            slownessTraps = ThreadLocalRandom.current().nextInt(2, 4);
        } else if (diff == "veryHard") {
            enemies = ThreadLocalRandom.current().nextInt(7, 11);
            crates = ThreadLocalRandom.current().nextInt(4, 5);
            spikeTraps = ThreadLocalRandom.current().nextInt(3, 4);
            slownessTraps = ThreadLocalRandom.current().nextInt(3, 5);
        } else if (diff == "impossible") {
            enemies = ThreadLocalRandom.current().nextInt(9, 14);
            crates = ThreadLocalRandom.current().nextInt(5, 6);
            spikeTraps = ThreadLocalRandom.current().nextInt(4, 5);
            slownessTraps = ThreadLocalRandom.current().nextInt(4, 6);
        }

        for (int i = 0; i < level.length; i++) {
            if (xCoordinate == level[0][yCoordinate]) {

            }
            for (int j = 0; j < level[i].length; j++) {

            }
        }

        setLevelToMake(level.toString());
    }

    public String setLevelToMake(String whatToSet) {
        levelToMake = levelToMake + " " + whatToSet;
        return levelToMake;
    }

    public String getLevelToMake() {
        return levelToMake;
    }
}
