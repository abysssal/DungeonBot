package me.abyssal.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class commander extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("e", "use with caution"));
        event.getJDA().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("e")) {
                event.reply("https://www.youtube.com/watch?v=dQw4w9WgXcQ \n" + "https://www.youtube.com/watch?v=dPtXaAZHuho \n" + "https://www.youtube.com/watch?v=dQw4w9WgXcQ \n" + "https://www.youtube.com/watch?v=dPtXaAZHuho \n" + "https://www.youtube.com/watch?v=dQw4w9WgXcQ \n" + "https://www.youtube.com/watch?v=dPtXaAZHuho \n" + "https://www.youtube.com/watch?v=dQw4w9WgXcQ \n" + "https://www.youtube.com/watch?v=dPtXaAZHuho \n" + "https://www.youtube.com/watch?v=dQw4w9WgXcQ \n" + "https://www.youtube.com/watch?v=dPtXaAZHuho").queue();
        }
    }
}
