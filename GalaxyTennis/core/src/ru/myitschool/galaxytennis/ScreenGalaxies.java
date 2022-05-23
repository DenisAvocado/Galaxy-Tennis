package ru.myitschool.galaxytennis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenGalaxies implements Screen {
    final GTennis GT;
    GalButton btnBack;
    Texture imgGreenRect, imgGreyRect;
    boolean first = true;

    ScreenGalaxies(GTennis GT){
        this.GT = GT;
        btnBack = new GalButton(GT, "To menu", GT.font, GTennis.SCR_WIDTH/2f, GTennis.SCR_HEIGHT*3f/10);
        imgGreenRect = new Texture("green_rect.png");
        imgGreyRect = new Texture("gray_rect.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched() && !first){
            GT.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            GT.camera.unproject(GT.touch);
            for (Galaxy g: GT.galaxies){
                if (GT.touch.x > g.x && GT.touch.x < g.x + g.width
                        && GT.touch.y > g.y && GT.touch.y < g.y + g.height) {
                    choose_galaxy(g.num);
                    break;
                }
            }
            if(btnBack.hit(GT.touch.x, GT.touch.y)) GT.setScreen(GT.screenMenu);
        }
        GT.batch.begin();
        GT.batch.draw(GT.galImages[GT.get_galaxy() - 1], 0, 0);

        for (int i = 1; i < 4; i ++) draw_galaxy(i);

        for (int i = 4; i < 7; i ++) draw_galaxy(i);

        GT.font.draw(GT.batch, btnBack.text, btnBack.x, btnBack.y);

        GT.batch.end();
        first = false;
    }

    void draw_galaxy(int i){
        GT.batch.draw(GT.galImages[i-1], GT.galaxies[i-1].x, GT.galaxies[i-1].y,
                GT.galaxies[i-1].width, GT.galaxies[i-1].height);
        if (get_bought_galaxies().charAt(i - 1) == '1'){
            if (i == GT.get_galaxy()) {
                GT.batch.draw(imgGreenRect, GT.galaxies[i - 1].x, GT.galaxies[i - 1].y,
                        GT.galaxies[i - 1].width, GT.galaxies[i - 1].height);
                GT.update_coef(1, GT.get_galaxy() * 0.5f);
                GT.font.draw(GT.batch, "X " + GT.coefficient.galaxy, GTennis.SCR_WIDTH / 10f,
                        GTennis.SCR_HEIGHT / 9f);
            }
            else
                GT.batch.draw(imgGreyRect, GT.galaxies[i-1].x, GT.galaxies[i-1].y,
                        GT.galaxies[i-1].width, GT.galaxies[i-1].height);
        }
        else {
            GT.batch.draw(GT.imgUnlock, GT.galaxies[i-1].x, GT.galaxies[i-1].y,
                    GT.galaxies[i-1].width, GT.galaxies[i-1].height / 2f);
            GT.font.draw(GT.batch, "" + GT.gal_prices[i-1], GT.galaxies[i-1].x, GT.galaxies[i-1].y);
        }
        GT.font.draw(GT.batch, "Money: " + GT.load_money(), GTennis.SCR_WIDTH / 20f, GTennis.SCR_HEIGHT / 20f);
    }

    void choose_galaxy(int gal){
        if (get_bought_galaxies().charAt(gal - 1) == '1') {
            GT.prefs.putInteger("galaxy", gal);
            GT.prefs.flush();
        }
        else {
            if (GT.gal_prices[gal - 1] <= GT.load_money()) {
                GT.change_money(-GT.gal_prices[gal - 1]);
                buy_galaxy(gal);
                if (GT.is_sound_on()) GT.sndMoney.play();
            }
            else if (GT.is_sound_on()) GT.sndNo.play();
        }
    }

    String get_bought_galaxies(){
        if (GT.prefs.contains("galaxy_buy")) return Integer.toString(GT.prefs.getInteger("galaxy_buy", 100000));
        GT.prefs.putInteger("galaxy_buy",100000);
        GT.prefs.flush();
        return "100000";
    }

    void buy_galaxy(int gal){
        String buy_line = "";
        for (int i = 0; i < 6; i++) {
            if (i + 1 == gal) buy_line = buy_line.concat("1");
            else if (get_bought_galaxies().charAt(i) == '1') buy_line = buy_line.concat("1");
            else buy_line = buy_line.concat("0");
        }
        GT.prefs.putInteger("galaxy_buy", Integer.parseInt(buy_line));
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
