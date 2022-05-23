package ru.myitschool.galaxytennis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenOptions implements Screen {
    final GTennis GT;
    Texture imgBG;
    GalButton btnSound, btnBack;
    boolean first = true;

    ScreenOptions(GTennis GT){
        this.GT = GT;
        btnSound = new GalButton(GT, "SOUND: " + (GT.is_sound_on()? "ON": "OFF"), GT.font_big, GTennis.SCR_WIDTH/2f, GTennis.SCR_HEIGHT*8f/10);
        btnBack = new GalButton(GT, "BACK", GT.font_big, GTennis.SCR_WIDTH/2f, GTennis.SCR_HEIGHT*6f/10);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched() && !first){
            GT.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            GT.camera.unproject(GT.touch);

            if(btnSound.hit(GT.touch.x, GT.touch.y)){
                GT.change_sound();
            }
            if(btnBack.hit(GT.touch.x, GT.touch.y)){
                GT.setScreen(GT.screenMenu);
            }

        }
        first = false;
        GT.camera.update();
        GT.batch.setProjectionMatrix(GT.camera.combined);
        GT.batch.begin();
        GT.batch.draw(GT.galImages[GT.get_galaxy() - 1], 0, 0);

        GT.font_big.draw(GT.batch,"SOUND: " + (GT.is_sound_on()? "ON": "OFF"), btnSound.x, btnSound.y);
        GT.font_big.draw(GT.batch, btnBack.text, btnBack.x, btnBack.y);
        GT.batch.end();
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
        imgBG.dispose();
    }
}

