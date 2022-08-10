package me.macialowskyyy.levelsbot.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import me.macialowskyyy.levelsbot.main.Main;

import java.util.HashMap;
import java.util.Random;

public class onChat extends ListenerAdapter {

    public static HashMap<String, String> ostatnie = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if(e.getMember() != null && !e.getMember().getUser().isBot()) {

            String si = e.getMember().getId();
            Main.data.createPlayer(e.getMember().getId(), e.getMember().getUser().getName());
            if(e.getChannel().getId().equals("932052922936737862") || e.getChannel().getId().equals("931680329373204521")) {
                return;
            }
            if(!Main.xp.containsKey(si)) {

                Main.xp.put(si, Main.data.getXp(si));
                Main.wymaganyxp.put(si, Main.data.getWymagane(si));
                Main.lvl.put(si, Main.data.getLvl(si));
                if(Main.xp.get(si) == null) {
                    Main.xp.put(si, 0);
                }
                if(Main.lvl.get(si) == null) {
                    Main.lvl.put(si, 0);
                }
                Main.razem.put(si, 0);

            }

            String[] args = e.getMessage().getContentRaw().split("\\s+");
            int con = 0;
            if(args[0].equals("!exp") || args[0].equals("!xp") || args[0].equals("!rank") || args[0].equals("!top") || args[0].equals("info") || args[0].equals("!pomoc")) {
                return;
            }



            for(String arg : args) {

                if(con <= 15) {
                    if(e.getMessage().getContentRaw().equals(ostatnie.get(si))) {
                        Main.xp.put(si, Main.xp.get(si) + 1);
                        Main.data.setRazem(si, 1);
                    } else {
                        if (arg.length() >= 4) {
                            Random rnd = new Random();
                            int range = 12 - 1 + 1;
                            int randomNum = rnd.nextInt(range) + 2;
                            Main.xp.put(si, Main.xp.get(si) + randomNum);
                            //Main.data.addXp(si, randomNum);

                            Main.data.setRazem(si, randomNum);
                        } else {
                            //Main.data.addXp(si, 3);
                            Main.xp.put(si, Main.xp.get(si) + 3);
                            Main.data.setRazem(si, 3);
                        }
                    }

                    //if (Main.data.getXp(si) >= Main.data.getWymagane(si)) {
                    if (Main.xp.get(si) >= Main.wymaganyxp.get(si)) {
                        //Main.data.setWymagane(si, Main.data.getWymagane(si) + 200);
                        Main.wymaganyxp.put(si, Main.wymaganyxp.get(si) + 200);
                        //Main.data.setXp(si, 0);
                        Main.xp.put(si, 0);
                        //Main.data.addLvl(si, 1);
                        Main.lvl.put(si, Main.lvl.get(si) + 1);
                        if(Main.lvl.get(si) != 0) {
                            e.getChannel().sendMessage("Właśnie " + e.getMember().getAsMention() + " awansowal na **" + Main.lvl.get(si) + "** poziom").queue();
                        }
                    }
                    con++;
                } else {
                    break;
                }

            }
            ostatnie.put(si, e.getMessage().getContentRaw());
        }
    }
}
