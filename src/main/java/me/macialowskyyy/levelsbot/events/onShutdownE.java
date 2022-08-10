package me.macialowskyyy.levelsbot.events;

import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import me.macialowskyyy.levelsbot.main.Main;

import java.util.Map;

public class onShutdownE extends ListenerAdapter {
    @Override
    public void onShutdown(ShutdownEvent e) {
        for(Map.Entry<String, Integer> lvl : Main.lvl.entrySet()) {
            Main.data.setLvl(lvl.getKey(), lvl.getValue());
        }
        Main.lvl.clear();
        for(Map.Entry<String, Integer> xp : Main.xp.entrySet()) {
            Main.data.setXp(xp.getKey(), xp.getValue());
            //Main.data.setRazem(xp.getKey(), xp.getValue());
        }
        Main.xp.clear();
        for(Map.Entry<String, Integer> wymagany : Main.wymaganyxp.entrySet()) {
            Main.data.setWymagane(wymagany.getKey(), wymagany.getValue());
        }
        Main.wymaganyxp.clear();
    }
}
