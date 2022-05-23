package ru.myitschool.galaxytennis;

import com.badlogic.gdx.graphics.Texture;

public class Ufo {
    float x, y;
    float v;
    int half;
    float sizeX, sizeY;
    int hp;
    boolean block;
    int self_l_or_r;
    int goals;
    Texture texture;

    Ufo(float x, float y, int half, Texture texture){
        this.x = x;
        this.y = y;
        v = 0;
        this.half = half;
        this.texture = texture;
        sizeX = GTennis.SCR_WIDTH / 3f;
        sizeY = GTennis.SCR_HEIGHT / 9f;
        hp = 5;
        block = false;
        self_l_or_r = 0;
    }

    void fly(){
        x += v;
        if (x < 0){
            v = 0;
            x = 0;
        }
        if (x > ScreenGame.SCR_WIDTH - sizeX) {
            v = 0;
            x = GTennis.SCR_WIDTH - sizeX;
        }
    }

}
