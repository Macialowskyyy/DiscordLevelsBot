package me.macialowskyyy.levelsbot.sql;

import com.google.common.collect.LinkedListMultimap;
import me.macialowskyyy.levelsbot.main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLGetter {


    public void createTable() {
        PreparedStatement ps;
        try {
            ps = Main.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS xp " + "(id CHAR(255), xpp INT(255), lvl INT(255), wymaganylvl INT(255), razemxp INT(255), nickname CHAR(255))");
            ps.executeUpdate();
        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }

    }

    public void createPlayer(String id, String name) {
        try {
            if (!exists(id)) {
                PreparedStatement ps2 = Main.SQL.getConnection().prepareStatement("INSERT IGNORE INTO xp (id,xpp,lvl,wymaganylvl,razemxp,nickname) VALUES (?,?,?,?,?,?)");
                ps2.setString(1, id);
                ps2.setInt(2, 0);
                ps2.setInt(3, 0);
                ps2.setInt(4, 1000);
                ps2.setInt(5, 0);
                ps2.setString(6, name);
                ps2.executeUpdate();
                return;
            }
        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }

    }

    public boolean exists(String id) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT * FROM xp WHERE id=?");
            ps.setString(1, id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
        return false;
    }

    public void setLvl(String id, Integer pl) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE xp SET lvl=? WHERE id=?");
            ps.setInt(1, pl);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
    }

    public void addLvl(String id, Integer pl) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE xp SET lvl=? WHERE id=?");
            ps.setInt(1, (getLvl(id) + pl));
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
    }

    public void setRazem(String id, Integer pl) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE xp SET razemxp=? WHERE id=?");
            ps.setInt(1, (getRazem(id) + pl));
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
    }



    public void setXp(String id, Integer pl) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE xp SET xpp=? WHERE id=?");
            ps.setInt(1, (pl));
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
    }

    public void addXp(String id, Integer pl) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE xp SET xpp=? WHERE id=?");
            ps.setInt(1, (getXp(id) + pl));
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
    }
    public int getAllestTokens() {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT SUM(xpp) FROM xp");
            ResultSet rs = ps.executeQuery();
            int players = 0;
            if (rs.next()) {
                players = rs.getInt(1);
                return players;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setWymagane(String id, Integer pl) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE xp SET wymaganylvl=? WHERE id=?");
            ps.setInt(1, pl);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
    }


    public LinkedListMultimap<String, Integer> getTopXp() {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT id, lvl, xpp FROM xp ORDER BY lvl DESC, xpp DESC LIMIT 15");
            ResultSet rs = ps.executeQuery();
            LinkedListMultimap<String, Integer> map = LinkedListMultimap.create();
            while(rs.next()) {
                map.put(rs.getString("id"), rs.getInt("lvl"));
                map.put(rs.getString("id"), rs.getInt("xpp"));
            }
            //Collection<Integer> values = map.get("ford");
            return map;
        }catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
            return null;
        }
    }







    public int getLvl(String id) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT lvl FROM xp WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            int players = 0;
            if (rs.next()) {
                players = rs.getInt("lvl");
                return players;
            }


        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
        return 0;
    }

    public int getXp(String id) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT xpp FROM xp WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            int players = 0;
            if (rs.next()) {
                players = rs.getInt("xpp");
                return players;
            }


        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
        return 0;
    }

    public int getWymagane(String id) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT wymaganylvl FROM xp WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            int players = 0;
            if (rs.next()) {
                players = rs.getInt("wymaganylvl");
                return players;
            }


        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
        return 0;
    }

    public int getRazem(String id) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT razemxp FROM xp WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            int players = 0;
            if (rs.next()) {
                players = rs.getInt("razemxp");
                return players;
            }


        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
        return 0;
    }


    public int getXpAll() {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT SUM(razemxp) FROM xp");
            ResultSet rs = ps.executeQuery();
            int players = 0;
            if (rs.next()) {
                players = rs.getInt(1);
                return players;
            }


        } catch (SQLException e) {
            Main.onRelogin();
            e.printStackTrace();
        }
        return 0;
    }


}
