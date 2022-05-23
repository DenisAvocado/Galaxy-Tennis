package ru.myitschool.galaxytennis;

public class Planet {
    GTennis GT;
    float x, y;
    float vx, vy;
    float size;
    int count;

    Planet(GTennis GT, float x, float y, float vx, float vy, float size){
        this.GT = GT;
        this.x = x; this.y = y;
        this.vx = vx; this.vy = vy;
        this.size = size;
        count = 0;
    }

    boolean fly(){
        x += vx;
        y += vy;
        if (x < 0 || x > GTennis.SCR_WIDTH - size) vx *= -1;
        if (y < 0 || y > GTennis.SCR_HEIGHT - size){
            vy *= -1;
            return true;
        }
        return false;
    }

    int collide(Ufo ufoUp, Ufo ufoDown){
        if ((y >= ufoUp.y - size) && (x > ufoUp.x - size && x < ufoUp.x + ufoUp.sizeX))
            if (vy > 0) {
                vy = -8;
                if (y + size >= GTennis.SCR_HEIGHT) {
                    return 1;
                }
                if (GT.is_sound_on())ScreenGame.sndBall.play();
                count ++;
            }
        if ((y <= ufoDown.y + ufoDown.sizeY) && (x > ufoDown.x - size && x < ufoDown.x + ufoDown.sizeX)){
            if (vy < 0) vy = 8;
            if (y <= 0) {
                return 2;
            }
            if (GT.is_sound_on())ScreenGame.sndBall.play();
            count ++;
        }

        return 0;
    }
}
