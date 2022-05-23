package ru.myitschool.galaxytennis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class ScreenGame implements Screen {
    public static final int SCR_WIDTH = 576, SCR_HEIGHT = 1024;
    final GTennis GT;

    SpriteBatch batch;

    static Sound sndBall, sndMiss;

    OrthographicCamera camera;
    Vector3 touch;

    Ufo[] ufo = new Ufo[2];
    Planet ball;

    int num = 0;

    ScreenGame(GTennis GT){
        this.GT = GT;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();

        batch = new SpriteBatch();

        sndMiss = Gdx.audio.newSound(Gdx.files.internal("miss.mp3"));
        sndBall = Gdx.audio.newSound(Gdx.files.internal("ball.mp3"));

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isTouched(0)){
            touch.set(Gdx.input.getX(0), Gdx.input.getY(0), 0);
            camera.unproject(touch);
            if (touch.y < SCR_HEIGHT / 2f) num = 0;
            else num = 1;

            ufo[num].v = (touch.x - ufo[num].x - ufo[num].sizeX / 2f) / 10f;
        }

        if (Gdx.input.isTouched(1)){
            touch.set(Gdx.input.getX(1), Gdx.input.getY(1), 0);
            camera.unproject(touch);
            if (touch.y < SCR_HEIGHT / 2f) num = 0;
            else num = 1;

            ufo[num].v = (touch.x - ufo[num].x - ufo[num].sizeX / 2f) / 10f;
        }

        if(ball.fly()){
            if (ball.y > SCR_HEIGHT / 2f) {
                ufo[1].goals ++;
                if (GT.is_sound_on()) sndMiss.play();
            }
            else {
                ufo[0].goals ++;
                if (GT.is_sound_on()) sndMiss.play();
            }
        }

        for (Ufo u:ufo){
            u.fly();

        }
        ball.collide(ufo[1], ufo[0]);
        if (ball.collide(ufo[1], ufo[0]) == 1) {
            ufo[1].goals ++;
        }
        else if (ball.collide(ufo[1], ufo[0]) == 2){
            ufo[1].goals ++;
        }
        if (ufo[0].goals == 5) GT.setScreen(new ScreenVictory(ufo[1], ball.count, GT));
        if (ufo[1].goals == 5) GT.setScreen(new ScreenVictory(ufo[0], ball.count, GT));



        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();



        batch.draw(GT.galImages[GT.get_galaxy() - 1], 0, 0);

        for (Ufo u:ufo) for(int i=0; i<u.goals; i++) {
            if (u.half == 2) batch.draw(GT.plImages[GT.get_planet() - 1], i * 20f + GTennis.SCR_WIDTH / 20f, GTennis.SCR_WIDTH / 20f, GTennis.SCR_WIDTH / 20f, GTennis.SCR_WIDTH / 20f);
            else batch.draw(GT.plImages[GT.get_planet() - 1], SCR_WIDTH - (i + 1) * 20f - GTennis.SCR_WIDTH / 20f, SCR_HEIGHT - GTennis.SCR_WIDTH / 20f - GTennis.SCR_WIDTH / 20f, GTennis.SCR_WIDTH / 20f, GTennis.SCR_WIDTH / 20f);
        }

        for(int i=0; i < 2; i++){
            batch.draw(ufo[i].texture, ufo[i].x, ufo[i].y, ufo[i].sizeX, ufo[i].sizeY,
                    0, 0, 597, 418, false, i == 0);
        }

        batch.draw(GT.plImages[GT.get_planet() - 1], ball.x, ball.y, ball.size, ball.size);
        batch.end();
    }


    void start(){
        ufo[0] = new Ufo(SCR_WIDTH / 2f - SCR_WIDTH / 3f / 2,
                0, 1, GT.get_ufo_cat_texture_1());
        ufo[1] = new Ufo(SCR_WIDTH / 2f - SCR_WIDTH / 3f / 2,
                SCR_HEIGHT - SCR_HEIGHT / 15f * 1.5f, 2, GT.get_ufo_cat_texture_2());
        ball = new Planet(GT, SCR_WIDTH / 2f - SCR_WIDTH / 20f,
                SCR_HEIGHT / 2f - SCR_HEIGHT / 20f,
                8 * (MathUtils.randomBoolean() ? 1 : -1), 8 * (MathUtils.randomBoolean() ? 1 : -1), SCR_WIDTH / 20f);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
