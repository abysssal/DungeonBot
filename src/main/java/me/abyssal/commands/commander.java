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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class commander extends ListenerAdapter {

    public int rooms;
    public String difficulty;

    public String levelToMake;

    public String player = ":monkey:";
    public String wall = ":red_square";
    public String blank = ":black_square:";
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
            difficulty = event.getOption("difficulty").getAsString();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Confirm Dungeon Creation");
            embed.setAuthor("Are you sure you want to create a dungeon with these settings?");
            embed.setDescription("Rooms: " + rooms + "\n Difficulty Level: " + difficulty);
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
            System.out.println("user has replied with yes for creating a new dungeon");
            createLevel(difficulty);

            EmbedBuilder embed = new EmbedBuilder();

            embed.setTitle("Your Dungeon");
            embed.setColor(Color.black);
            embed.setDescription(getLevelToMake());

            event.replyEmbeds(embed.build()).queue();
        } else if (event.getComponentId().equals("no")) {
            System.out.println("user has decided to restart");
            event.reply("Mistakes happen, but you can try again").setEphemeral(true).queue();
        }
    }

    public void createLevel(String diff) {
        int row = 0;
        int column = 0;

        int iteration = 0;

        int width = 10;
        int height = 10;

        String level = "";

        while (iteration < 100) {
            level = level + " " + blank + " ";
            column++;
            if (column == width) {
                row++;
                column = 0;
                level = level + "\n";
            }

            if (row == height && column == width) {
                System.out.println("finished early");
                levelToMake = level;
                break;
            }
            iteration++;
            System.out.println("creation iteration completed currently on iteration " + iteration + ",and we are on column " + column + " and on row " + row);
        }

        levelToMake = level;
        System.out.println(levelToMake);
        System.out.println("finished");
    }

    public String getLevelToMake() {
        return levelToMake;
    }
}
