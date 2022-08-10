package me.macialowskyyy.levelsbot.uttil;

import me.macialowskyyy.levelsbot.main.Main;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BazaSystem {

    public BazaSystem() {
        start();
    }

    public void start() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            System.out.println(Main.data.getAllestTokens());
            if(!Main.SQL.isConnected()) {
                Main.onRelogin();
            }
            for(Map.Entry<String, Integer> lvl : Main.lvl.entrySet()) {
                Main.data.setLvl(lvl.getKey(), lvl.getValue());
            }
            Main.lvl.clear();
            for(Map.Entry<String, Integer> xp : Main.xp.entrySet()) {
                Main.data.setXp(xp.getKey(), xp.getValue());
                //Main.data.setRazem(xp.getKey(), Main.data.getRazem(xp.getKey()) + xp.getValue());
            }
            Main.xp.clear();
            for(Map.Entry<String, Integer> wymagany : Main.wymaganyxp.entrySet()) {
                Main.data.setWymagane(wymagany.getKey(), wymagany.getValue());
            }
            Main.wymaganyxp.clear();
        };

        executor.scheduleWithFixedDelay(task, 0, 30, TimeUnit.SECONDS);
    }
}
