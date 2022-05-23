package ru.myitschool.galaxytennis;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class GalButton {
    String text;
    GTennis GT;
    float x, y;
    float width, height;


    GalButton(GTennis GT, String text, BitmapFont font, float x, float y){
        GlyphLayout gl = new GlyphLayout(font, text);
        this.GT = GT;
        this.text = text;
        this.x = x;
        this.y = y;
        width = gl.width;
        height = gl.height;
        this.x -= width / 2;
    }

    boolean hit(float tx, float ty){
        if (GT.is_sound_on())GT.sndClick.play();
        return tx > x && tx < x + width && ty < y && ty > y - height;
    }
}

