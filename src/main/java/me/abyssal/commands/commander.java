package me.abyssal.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class commander extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("startnewdungeon", "starts a new dungeon with a fixed amount of rooms and set difficulty")
                .addOptions(new OptionData(OptionType.INTEGER, "rooms", "the number of rooms in the dungeon", true)
                        .setMinValue(1)
                        .setMaxValue(15)
                )
                .addOptions(new OptionData(OptionType.STRING, "difficulty", "how difficult should the dungeon be, harder gives best loot", true)
                        .addChoice("very easy", "ez")
                        .addChoice("easy", "somewhatEasy")
                        .addChoice("medium", "medium")
                        .addChoice("hard", "hard")
                        .addChoice("very hard", "veryHard")
                        .addChoice("impossible", "uhOh")
                )
        );
        commandData.add(Commands.slash("info", "get info about DungeonBot"));
        event.getJDA().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("startnewdungeon")) {
            event.deferReply();

            int rooms = event.getOption("rooms").getAsInt();
            String difficulty = event.getOption("difficulty").getAsString();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Coming soon!");
            embed.setDescription("This feature has not been implemented yet, sorry! \n Here is some data collected: \n" + "Rooms: " + rooms + "\n Difficulty Level: " + difficulty);
            embed.setFooter("Made by @Abyssal#5704");
            embed.setColor(Color.red);

            event.getHook().sendMessageEmbeds(embed.build()).queue();
        }

        if (event.getName().equals("info")) {
            event.deferReply().queue();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("DungeonBot Information");
            embed.setAuthor("Made by @Abyssal#5704");
            embed.setDescription("DungeonBot is a discord bot where you can create randomly generated dungeons. You can battle monsters, get items and flex on your friends with those items you earned from the dungeons. \n \n You can find the source code here: https://github.com/abysssal/DungeonBot");
            embed.setColor(Color.blue);

            event.getHook().sendMessageEmbeds(embed.build()).queue();
        }
    }
}
