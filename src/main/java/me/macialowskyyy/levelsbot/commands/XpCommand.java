package me.macialowskyyy.levelsbot.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import me.macialowskyyy.levelsbot.main.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

public class XpCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        if(args[0].equals("!exp") || args[0].equals("!xp") || args[0].equals("!rank")) {
            if(e.getMember() != null && !e.getMember().getUser().isBot()) {
                Main.data.createPlayer(e.getMember().getId(), e.getMember().getUser().getName());
                String si = e.getMember().getId();
                if (args.length == 1) {
                    //e.getChannel().sendMessage("Xp: " + Main.xp.get(si) + "/" + Main.wymaganyxp.get(si) + " Lvl: " + Main.lvl.get(si)).queue();
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    try {
                        ImageIO.write(prepareImage(e.getMember().getUser(), Main.data.getLvl(si), Main.data.getXp(si), Main.data.getWymagane(si)), "png", bao);
                    } catch (IOException | FontFormatException ioException) {
                        ioException.printStackTrace();
                    }
                    e.getChannel().sendFile(bao.toByteArray(), "xp.png").queue();
                } else if (args.length == 2) {
                    if (args[1].equals("stats")) {
                        if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                            e.getChannel().sendMessage(String.valueOf(Main.data.getXpAll())).queue();
                        }
                    }
                    if (args[1].contains("<@!")) {
                        String id = args[1];
                        id = id.replace("<@!", "");
                        id = id.replace(">", "");
                        User user = e.getJDA().retrieveUserById(id).complete();
                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        try {
                            ImageIO.write(prepareImage(user, Main.data.getLvl(id), Main.data.getXp(id), Main.data.getWymagane(id)), "png", bao);
                        } catch (IOException | FontFormatException ioException) {
                            ioException.printStackTrace();
                        }
                        e.getChannel().sendFile(bao.toByteArray(), "xp.png").queue();
                    } else if (args[1].matches("[0-9]+")) {
                        String id = args[1];
                        User user = e.getJDA().retrieveUserById(id).complete();
                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        try {
                            ImageIO.write(prepareImage(user, Main.data.getLvl(id), Main.data.getXp(id), Main.data.getWymagane(id)), "png", bao);
                        } catch (IOException | FontFormatException ioException) {
                            ioException.printStackTrace();
                        }
                        e.getChannel().sendFile(bao.toByteArray(), "xp.png").queue();
                    }
                }
            }


        }
    }

    public BufferedImage prepareImage(User member, int level, int xp, int wymaganexp) throws IOException, FontFormatException {

        BufferedImage img = new BufferedImage(1200, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());

        g2d.setColor(new Color(38, 34, 34));
        g2d.fillRect(264, 214, 800, 50);
        // server_xp bar
        g2d.setColor(new Color(0, 118, 177));
        BigDecimal bd = new BigDecimal(xp).divide(new BigDecimal(wymaganexp), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(800));

        g2d.fillRect(264, 214, bd.intValue(), 50);

        // users name
        g2d.setColor(new Color(0, 130, 148));
        Font font = new Font ("Roboto", Font.PLAIN , 7);
        g2d.setFont(font.deriveFont(70F));
        g2d.drawString(member.getName(), 270, 105);

        //bar name

        g2d.setColor(new Color(20, 150, 100, 110));

        g2d.fillRect(266, 123, g2d.getFontMetrics().stringWidth(member.getName())+8, 10);


        // level int
        g2d.setColor(new Color(0, 180, 148));
        g2d.setFont(font.deriveFont(45F));
        String slevel = Integer.toString(level);
        g2d.drawString(slevel, 458, 180);

        //%
        g2d.setColor(new Color(0, 180, 148));
        g2d.setFont(font.deriveFont(45F));
        String xpsa =  (xp*100) / wymaganexp +"%";
        g2d.drawString(xpsa, 1090, 255);


        // level str
        g2d.setColor(new Color(0, 110, 148));
        g2d.setFont(font.deriveFont(45F));
        String xpalls = "Poziom:";
        g2d.drawString(xpalls, 270, 180);

        // xp int
        g2d.setColor(new Color(0, 99, 148));
        g2d.setFont(font.deriveFont(45F));
        String xps = xp + " / " + wymaganexp + " xp";
        g2d.drawString(xps, 1140 - g2d.getFontMetrics().stringWidth(xps), 180);
        // avatar image

        URL urls = new URL(member.getEffectiveAvatarUrl());
        HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0");

        int scale = 220;
        Image avatar = null;
        try {
            avatar = ImageIO.read(conn.getInputStream()).getScaledInstance(scale, scale, Image.SCALE_AREA_AVERAGING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2d.drawImage(applyAlphaMask(toBufferedImage(avatar), getCircleMask(scale, scale)), 20, 151 - (scale / 2), null);

        g2d.dispose();

        return img;
    }

    private BufferedImage applyAlphaMask(BufferedImage image, BufferedImage mask) {
        BufferedImage maskedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = maskedImage.createGraphics();

        // draw original
        g2.drawImage(image, 0, 0, null);

        // set "masking settings"
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_IN, 1.0F);
        g2.setComposite(ac);

        // draw mask
        g2.drawImage(mask, 0, 0, null);

        g2.dispose();

        return maskedImage;
    }

    private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) return (BufferedImage) img;
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return bi;
    }

    private BufferedImage getCircleMask(int imageSize, int circleSize) {
        BufferedImage img = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D)img.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw circle
        g2d.setColor(Color.BLACK);
        g2d.fillOval(0, 0, circleSize, circleSize);
        g2d.dispose();

        return img;
    }






}
