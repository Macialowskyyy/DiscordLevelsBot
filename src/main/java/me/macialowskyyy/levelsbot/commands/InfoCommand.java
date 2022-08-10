package me.macialowskyyy.levelsbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Date;

public class InfoCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        if (args[0].equals("!info") || args[0].equals("!pomoc")) {
            EmbedBuilder eb = new EmbedBuilder();

            eb.setTitle("Informacje o systemie poziomow PoisonLevels", null);
            eb.setColor(new Color(160, 232, 77));
            eb.setDescription("**Lista kanalow z wylaczonyn XP:**\n<#932052922936737862>\n<#931680329373204521>\n\nWszelkie problemy techniczne z botem, prosimy zglaszac wiadomosci prywatnej <@546408772805853189>\n**Lista komend:**");
            eb.addField("!xp, !exp, !rank (@użytkownik/id)", "Sprawdzanie statystyk swoich lub wybranego uzytkownika", false);
            eb.addField("!top, !ranking", "Sprawdzanie topki osob z najwiekszym poziomem i xp", false);
            eb.addField("!info, !pomoc", "Lista przydatnych informacji o systemie", false);
            eb.setFooter("Komenda zostala wywolana przez " + e.getMember().getUser().getName(), e.getMember().getEffectiveAvatarUrl());
            eb.setTimestamp(new Date().toInstant());
            eb.addBlankField(false);
            e.getChannel().sendMessageEmbeds(eb.build()).queue();
        }
    }
}
