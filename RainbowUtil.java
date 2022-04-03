package com.joojn.LucidClient.Utils;

import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class RainbowUtil {

    public float[] vhs;

    public RainbowUtil(){
        this(0F, 1F, 1F);
    }

    public RainbowUtil(float v, float h, float s){
        vhs = new float[] { v, h, s};
    }

    public Color increaseColor(){
        vhs[0]++;

        if(vhs[0] >= 359){
            vhs[0] = 0;
        }

        return getColor();
    }

    public RainbowUtil addColor(int value){
        float v = (this.vhs[0] + value) % 360;

        return new RainbowUtil(v, vhs[1], vhs[2]);
    }

    public Color getColor(int alpha) {
        Color c = Color.getHSBColor(vhs[0] / 360F, vhs[1], vhs[2]);
        return new Color(c.getRed(), c.getBlue(), c.getGreen(), alpha);
    }

    public Color getColor(){
        return this.getColor(255);
    }

    public Color getColor(int r, int g, int b, int a) {
        return new Color(r == 0 ? getColor().getRGB() : r, g == 0 ? getColor().getGreen() : 0, b == 0 ? getColor().getBlue() : b, a == 0 ? getColor().getAlpha() : a);
    }


    public void drawChromaRect(int x, int y, int x2, int y2, int alpha, float speed){
        this.increaseColor();
        alpha = Math.min(Math.max(alpha, 0), 255);

        for(int i = x; i < x2; i++){
            DrawUtil.drawFloatRect(i, y, i + 1, y2, this.addColor((int) (i * speed)).getColor(alpha).getRGB());
        }
    }

    public void drawChromaRect(float x, float y, float x2, float y2, int alpha, float speed){
        drawChromaRect((int) x, (int) y, (int) x2, (int) y2, alpha, speed);
    }

    public void drawChromaString(FontRenderer font, String text, float x, float y, boolean shadow, int alpha, float speed){
        char[] chars = text.toCharArray();
        float offset = 0;
        int i = 0;
        alpha = Math.min(Math.max(alpha, 0), 255);
        this.increaseColor();

        for(char c : chars){
            font.drawString(String.valueOf(c), x+offset, y, this.addColor((int) (i * speed)).getColor(0, 0, 0, alpha).getRGB(), shadow);
            offset += font.getStringWidth(String.valueOf(c));
            i++;
        }
    }


    public static int SkyRainbow(int var2, float bright, float st) {
        double v1 = Math.ceil(System.currentTimeMillis() + (long) (var2 * 109)) / 5;
        return Color.getHSBColor((double) ((float) ((v1 %= 360.0) / 360.0)) < 0.5 ? -((float) (v1 / 360.0)) : (float) (v1 / 360.0), st, bright).getRGB();
    }
}
