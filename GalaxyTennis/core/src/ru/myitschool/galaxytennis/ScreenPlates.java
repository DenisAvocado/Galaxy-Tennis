package ru.myitschool.galaxytennis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class ScreenPlates implements Screen {
    final GTennis GT;
    GalButton btnBack;
    boolean first = true;

    ScreenPlates(GTennis GT){
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
            for (GalImage p: GT.ufos){
                if (GT.touch.x > p.x && GT.touch.x < p.x + p.width
                        && GT.touch.y > p.y && GT.touch.y < p.y + p.height) {
                    choose_plates(p.num);
                    break;
                }
            }
            if(btnBack.hit(GT.touch.x, GT.touch.y)) GT.setScreen(GT.screenMenu);
        }
        GT.batch.begin();
        GT.batch.draw(GT.galImages[GT.get_galaxy() - 1], 0, 0);

        for (int i = 1; i < 3; i ++) draw_plates(i);

        for (int i = 3; i < 5; i ++) draw_plates(i);

        GT.font.draw(GT.batch, btnBack.text, btnBack.x, btnBack.y);

        GT.batch.end();
        first = false;
    }

    void draw_plates(int i){
        GT.batch.draw(GT.ufoImages[i-1], GT.ufos[i-1].x, GT.ufos[i-1].y,
                GT.ufos[i-1].width, GT.ufos[i-1].height);
        if (get_bought_plates().charAt((i - 1) % 4) == '1'){
            if (i == GT.get_plates()) {
                GT.batch.draw(GT.imgMark, GT.ufos[i - 1].x, GT.ufos[i - 1].y,
                        GT.ufos[i - 1].width, GT.ufos[i - 1].height);
                GT.update_coef(3, GT.get_plates() * 0.5f);
                GT.font.draw(GT.batch, "X " + GT.coefficient.plate, GTennis.SCR_WIDTH / 10f,
                        GTennis.SCR_HEIGHT / 9f);
            }
        }
        else {
            GT.batch.draw(GT.imgUnlock, GT.ufos[i-1].x + GT.ufos[i-1].width / 2f, GT.ufos[i-1].y,
                    GT.ufos[i-1].width / 2f, GT.ufos[i-1].height / 2f);
            GT.font.draw(GT.batch, "" + GT.plates_prices[i-1], GT.ufos[i-1].x, GT.ufos[i-1].y);
        }
        GT.font.draw(GT.batch, "Money: " + GT.load_money(), GTennis.SCR_WIDTH / 20f, GTennis.SCR_HEIGHT / 20f);
    }

    void choose_plates(int plate){
        if (get_bought_plates().charAt(plate - 1) == '1') {
            GT.prefs.putInteger("plates", plate);
            GT.prefs.flush();
        }
        else {
            if (GT.plates_prices[plate - 1] <= GT.load_money()) {
                GT.change_money(-GT.pl_prices[plate - 1]);
                buy_plates(plate);
                if (GT.is_sound_on()) GT.sndMoney.play();
            }
            else if (GT.is_sound_on()) GT.sndNo.play();
        }
    }

    String get_bought_plates(){
        if (GT.prefs.contains("plates_buy")) return GT.prefs.getString("plates_buy", "1000");
        return "1000";
    }

    void buy_plates(int plate){
        String buy_line = "";
        for (int i = 0; i < 4; i++) {
            if (i + 1 == plate) buy_line = buy_line.concat("1");
            else if (get_bought_plates().charAt(i) == '1') buy_line = buy_line.concat("1");
            else buy_line = buy_line.concat("0");
        }
        GT.prefs.putString("plates_buy", buy_line);
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
