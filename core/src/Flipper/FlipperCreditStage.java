package Flipper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class FlipperCreditStage extends MyStage {
    BackButton backButton;
    ClickListener c1;
    public FlipperCreditStage(MyGame game) {
        super(new ExtendViewport(90, 160), game);
        addBackButtonScreenBackByStackPopListener();
        setCameraResetToCenterOfScreen();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getMyAssetManager().getFont("Flipper/font2.ttf");
        labelStyle.fontColor = Color.YELLOW;






        MyLabel label = new MyLabel(game, "Creators:", labelStyle);
        label.setFontScale(0.2f);
        label.setPosition(0, 135);
        addActor(label);

        label = new MyLabel(game, "Fellner Milan", labelStyle);
        label.setFontScale(0.2f);
        label.setPosition(0, 120);
        addActor(label);

        label = new MyLabel(game, "Kancsal Mate", labelStyle);
        label.setFontScale(0.2f);
        label.setPosition(0, 110);
        addActor(label);

        label = new MyLabel(game, "Kele Lorand", labelStyle);
        label.setFontScale(0.2f);
        label.setPosition(0, 100);
        addActor(label);

        label = new MyLabel(game, "Zsebok David Ferenc", labelStyle);
        label.setFontScale(0.2f);
        label.setPosition(0, 90);
        addActor(label);

        label = new MyLabel(game, "Teacher:", labelStyle);
        label.setFontScale(0.2f);
        label.setPosition(0, 75);
        addActor(label);

        label = new MyLabel(game, "Tuske Balazs", labelStyle);
        label.setFontScale(0.2f);
        label.setPosition(0, 60);
        addActor(label);

        MyLabel label1 = new MyLabel(game, "Back", labelStyle);
        label1.setFontScale(0.2f);
        label1.setPosition(0,0);
        addActor(label1);

        label1.addListener(c1 = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.getMyAssetManager().getSound("click.mp3").play();
                game.setScreen(new FlipperMenuScreen(game));
            }
        });

    }}
