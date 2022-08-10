package me.macialowskyyy.levelsbot.main;

import me.macialowskyyy.levelsbot.commands.InfoCommand;
import me.macialowskyyy.levelsbot.commands.TopCommand;
import me.macialowskyyy.levelsbot.commands.XpCommand;
import me.macialowskyyy.levelsbot.events.onChat;
import me.macialowskyyy.levelsbot.events.onShutdownE;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import me.macialowskyyy.levelsbot.sql.MySQL;
import me.macialowskyyy.levelsbot.sql.SQLGetter;
import me.macialowskyyy.levelsbot.uttil.BazaSystem;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {
    public static JDA jda;
    public static MySQL SQL;
    public static SQLGetter data;

    public static HashMap<String, Integer> lvl = new HashMap<>();
    public static HashMap<String, Integer> xp = new HashMap<>();
    public static HashMap<String, Integer> wymaganyxp = new HashMap<>();
    public static HashMap<String, Integer> razem = new HashMap<>();
    public static void main(String[] args) {
        //MySQL
        SQL = new MySQL();
        data = new SQLGetter();
        try {
            JDABuilder jdaBuilder = JDABuilder.createDefault("token");
            jdaBuilder.setActivity(Activity.playing("Levels"));
            jda = jdaBuilder.build();
            jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Nie udalo sie polaczyc z baza danych!");
            e.printStackTrace();
        }
        if (SQL.isConnected()) {
            System.out.println("Udalo sie polaczyc z baza danych!");
            data.createTable();
        }

        new BazaSystem();
        jda.addEventListener(new onChat());
        jda.addEventListener(new onShutdownE());
        jda.addEventListener(new XpCommand());
        jda.addEventListener(new TopCommand());
        jda.addEventListener(new InfoCommand());
        //jda.addEventListener(new onChannel(plugin));

    }

    public static void onRelogin() {
        SQL.disconnect();
        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
