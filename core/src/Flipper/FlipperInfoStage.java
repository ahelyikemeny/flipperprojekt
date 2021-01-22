package Flipper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class FlipperInfoStage extends MyStage {
    BackButton backButton;
    public FlipperInfoStage(MyGame game) {
        super(new ExtendViewport(90,160), game);
        addBackButtonScreenBackByStackPopListener();
        setCameraResetToCenterOfScreen();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getMyAssetManager().getFont("Flipper/font.ttf");
        labelStyle.fontColor = Color.GREEN;

        backButton = new BackButton(game, 20, 160);
        addActor(backButton);

        MyLabel label = new MyLabel(game, "Kancsal csinalj texturat!!4!", labelStyle);
        label.setFontScale(0.2f);
        label.setPosition(0,70);
        addActor(label);

} }
