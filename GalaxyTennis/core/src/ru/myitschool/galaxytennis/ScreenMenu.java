package ru.myitschool.galaxytennis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenMenu implements Screen {
    final GTennis GT;
    Texture imgBG;
    GalButton btnPlay, btnOptions, btnGalaxy, btnPlanet, btnPlates, btnExit;
    boolean first = true;

    ScreenMenu(GTennis GT){
        this.GT = GT;

        btnPlay = new GalButton(GT, "PLAY", GT.font_big, GTennis.SCR_WIDTH/2f, GTennis.SCR_HEIGHT*8f/10);
        btnOptions = new GalButton(GT, "OPTIONS", GT.font_big, GTennis.SCR_WIDTH/2f, GTennis.SCR_HEIGHT*7f/10);
        btnGalaxy = new GalButton(GT, "GALAXIES", GT.font_big, GTennis.SCR_WIDTH/2f, GTennis.SCR_HEIGHT*6f/10);
        btnPlanet = new GalButton(GT, "PLANETS", GT.font_big, GTennis.SCR_WIDTH/2f, GTennis.SCR_HEIGHT*5f/10);
        btnPlates = new GalButton(GT, "SPACE PLATES", GT.font_big, GTennis.SCR_WIDTH/2f, GTennis.SCR_HEIGHT*4f/10);
        btnExit = new GalButton(GT, "EXIT", GT.font_big, GTennis.SCR_WIDTH/2f, GTennis.SCR_HEIGHT*3f/10);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched() && !first){
            GT.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            GT.camera.unproject(GT.touch);

            if(btnPlay.hit(GT.touch.x, GT.touch.y)){
                GT.screenGame.start();
                GT.setScreen(GT.screenGame);
            }

            if(btnExit.hit(GT.touch.x, GT.touch.y)) Gdx.app.exit();

            if(btnOptions.hit(GT.touch.x, GT.touch.y)) GT.setScreen(GT.screenOptions);

            if(btnGalaxy.hit(GT.touch.x, GT.touch.y)) GT.setScreen(GT.screenGalaxies);

            if(btnPlanet.hit(GT.touch.x, GT.touch.y)) GT.setScreen(GT.screenPlanets);

            if(btnPlates.hit(GT.touch.x, GT.touch.y)) GT.setScreen(GT.screenPlates);
        }
        first = false;


        GT.camera.update();
        GT.batch.setProjectionMatrix(GT.camera.combined);
        GT.batch.begin();
        GT.batch.draw(GT.galImages[GT.get_galaxy() - 1], 0, 0);

        GT.font_big.draw(GT.batch, "Money: " + GT.load_money(), GTennis.SCR_WIDTH / 20f, GTennis.SCR_HEIGHT / 18f);
        GT.font_big.draw(GT.batch, btnPlay.text, btnPlay.x, btnPlay.y);
        GT.font_big.draw(GT.batch, btnOptions.text, btnOptions.x, btnOptions.y);
        GT.font_big.draw(GT.batch, btnGalaxy.text, btnGalaxy.x, btnGalaxy.y);
        GT.font_big.draw(GT.batch, btnPlanet.text, btnPlanet.x, btnPlanet.y);
        GT.font_big.draw(GT.batch, btnPlates.text, btnPlates.x, btnPlates.y);
        GT.font_big.draw(GT.batch, btnExit.text, btnExit.x, btnExit.y);
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

