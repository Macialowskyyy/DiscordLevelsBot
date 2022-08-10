package me.macialowskyyy.levelsbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import me.macialowskyyy.levelsbot.main.Main;

import java.awt.*;
import java.util.*;

public class TopCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        if(args[0].equals("!top") || args[0].equals("!ranking")) {
            EmbedBuilder eb = new EmbedBuilder();

            eb.setTitle("Top 15 osob z najwiekszym poziomem", null);
            eb.setColor(new Color(232, 176, 77));
            int siemian = 0;
            for (String str : Main.data.getTopXp().keySet()) {
                siemian++;
                //System.out.println(str + ": " + Main.data.getTopXp().get(str));
                User user = e.getJDA().retrieveUserById(str).complete();
                String sto = String.valueOf(Main.data.getTopXp().get(str));
                sto = sto.replace("[","");
                sto = sto.replace("]","");
                sto = sto.replace(",","");
                String[] strArray = sto.split(" ");


                eb.addField("#" + siemian + " " + user.getName(), "**LVL:** " + strArray[0] + " **XP:** " + strArray[1] + " **Lacznie XP:** " + Main.data.getRazem(str), false);

            }


            eb.setFooter("Komenda zostala wywolana przez " + e.getMember().getUser().getName(), e.getMember().getEffectiveAvatarUrl());
            eb.setTimestamp(new Date().toInstant());
            eb.addBlankField(false);
            e.getChannel().sendMessageEmbeds(eb.build()).queue();
        }
    }


}
