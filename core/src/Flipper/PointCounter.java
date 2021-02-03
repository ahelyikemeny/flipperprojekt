package Flipper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

public class PointCounter extends Label.LabelStyle {
    PointActorCircle pointActorCircle;
    public PointCounter(MyGame game) {
        super(game.getMyAssetManager().getFont("Flipper/font2.ttf"), Color.YELLOW);
    }

    public void setText(String s) {
    }
}
