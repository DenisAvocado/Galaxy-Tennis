package ru.myitschool.galaxytennis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;

public class ScreenPlanets implements Screen {
    final GTennis GT;
    GalButton btnBack;
    boolean first = true;
    ScreenPlanets(GTennis GT){
        this.GT = GT;
        btnBack = new GalButton(GT, "To menu", GT.font, GTennis.SCR_WIDTH/2f, GTennis.SCR_HEIGHT*3f/10);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched() && !first){
            GT.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            GT.camera.unproject(GT.touch);
            for (GalImage p: GT.planets){
                if (GT.touch.x > p.x && GT.touch.x < p.x + p.width
                        && GT.touch.y > p.y && GT.touch.y < p.y + p.height) {
                    choose_planet(p.num);
                    break;
                }
            }
            if(btnBack.hit(GT.touch.x, GT.touch.y)) GT.setScreen(GT.screenMenu);
        }
        GT.batch.begin();
        GT.batch.draw(GT.galImages[GT.get_galaxy() - 1], 0, 0);

        for (int i = 1; i < 5; i ++) draw_planet(i);

        for (int i = 5; i < 9; i ++) draw_planet(i);

        GT.font.draw(GT.batch, btnBack.text, btnBack.x, btnBack.y);

        GT.batch.end();
        first = false;
    }

    void draw_planet(int i){
        GT.batch.draw(GT.plImages[i-1], GT.planets[i-1].x, GT.planets[i-1].y,
                GT.planets[i-1].width, GT.planets[i-1].height);
        if (get_bought_planets().charAt(i - 1) == '1'){
            if (i == GT.get_planet()) {
                GT.batch.draw(GT.imgMark, GT.planets[i - 1].x, GT.planets[i - 1].y,
                        GT.planets[i - 1].width, GT.planets[i - 1].height);
                GT.update_coef(2, GT.get_planet() * 0.25f);
                GT.font.draw(GT.batch, "X " + GT.coefficient.planet, GTennis.SCR_WIDTH / 10f,
                        GTennis.SCR_HEIGHT / 9f);
            }
        }
        else {
            GT.batch.draw(GT.imgUnlock, GT.planets[i-1].x + GT.planets[i-1].width / 2f,
                    GT.planets[i-1].y,
                    GT.planets[i-1].width / 2f, GT.planets[i-1].height / 2f);
            GT.font.draw(GT.batch, "" + GT.pl_prices[i-1], GT.planets[i-1].x, GT.planets[i-1].y);
        }
        GT.font.draw(GT.batch, "Money: " + GT.load_money(), GTennis.SCR_WIDTH / 20f, GTennis.SCR_HEIGHT / 20f);
    }

    void choose_planet(int pl){
        Preferences prefs = Gdx.app.getPreferences("preferences");
        if (get_bought_planets().charAt(pl - 1) == '1') {
            prefs.putInteger("planet", pl);
            prefs.flush();
        }
        else {
            if (GT.pl_prices[pl - 1] <= GT.load_money()) {
                GT.change_money(-GT.pl_prices[pl - 1]);
                buy_planet(pl);
                prefs.putInteger("planet", pl);
                prefs.flush();
                if (GT.is_sound_on()) GT.sndMoney.play();
            }
            else if (GT.is_sound_on()) GT.sndNo.play();
        }
    }


    String get_bought_planets(){
        if (GT.prefs.contains("planet_buy")) return GT.prefs.getString("planet_buy", "10000000");
        return "10000000";
    }

    void buy_planet(int pl){
        String buy_line = "";
        for (int i = 0; i < 8; i++) {
            if (i + 1 == pl) buy_line = buy_line.concat("1");
            else if (get_bought_planets().charAt(i) == '1') buy_line = buy_line.concat("1");
            else buy_line = buy_line.concat("0");
        }
        GT.prefs.putString("planet_buy", buy_line);
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
