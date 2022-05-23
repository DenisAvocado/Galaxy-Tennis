package ru.myitschool.galaxytennis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;

public class ScreenVictory implements Screen {
    final GTennis GT;
    Ufo winUfo;
    Sound sndMeow;
    boolean first;
    int count;

    ScreenVictory(Ufo winUfo, int count, GTennis GT){
        this.GT = GT;
        this.winUfo = winUfo;
        this.count = count;
        first= true;
        sndMeow = Gdx.audio.newSound(Gdx.files.internal("meow.mp3"));
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isTouched()){
            GT.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            GT.camera.unproject(GT.touch);
            GT.setScreen(new ScreenMenu(GT));
        }
        GT.batch.begin();
        GT.batch.draw(GT.galImages[GT.get_galaxy() - 1], 0, 0);
        GT.font.draw(GT.batch, "CONGRATULATIONS!!!", GTennis.SCR_WIDTH / 3.5f, GTennis.SCR_HEIGHT / 4f);
        GT.font.draw(GT.batch, "MONEY: " + (int)(count * GT.coefficient.get_sum()),
                GTennis.SCR_WIDTH / 3.5f, GTennis.SCR_HEIGHT / 5f);
        GT.batch.draw(winUfo.texture, GTennis.SCR_WIDTH / 2f - GTennis.SCR_WIDTH / 1.2f / 2f, GTennis.SCR_HEIGHT / 3f,
                GTennis.SCR_WIDTH / 1.2f, GTennis.SCR_HEIGHT / 3f);

        if (first) {
            if (GT.is_sound_on()) sndMeow.play();
            first = false;
            save_money();
        }
        GT.batch.end();

    }

    void save_money(){
        int money = GT.load_money();
        GT.prefs.putInteger("money", (int)(count * GT.coefficient.get_sum()) + money);
        GT.prefs.flush();
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
