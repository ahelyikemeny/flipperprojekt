package Flipper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Timers.OneTickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.OneTickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class FlipperMenuStage extends MyStage {

    ClickListener c1;

    public static AssetList assetList = new AssetList();
    static {
        AssetList.collectAssetDescriptor(AnimatedKatapultActor.class, assetList);
    }

    public FlipperMenuStage(MyGame game) {
        super(new ExtendViewport(90,160), game);
        addBackButtonScreenBackByStackPopListener();
        setCameraResetToLeftBottomOfScreen();
        BackGroundActor backGroundActor = new BackGroundActor(game);
        addActor(backGroundActor);
        //addActor(new AnimatedKatapultActor(game));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getMyAssetManager().getFont("Flipper/font2.ttf");
        labelStyle.fontColor = Color.WHITE;

        MyLabel label1 = new MyLabel(game, "Start", labelStyle);
        label1.setFontScale(0.2f);
        label1.setPosition(35,90);
        addActor(label1);

        MyLabel label2 = new MyLabel(game, "Exit", labelStyle);
        label2.setFontScale(0.2f);
        label2.setPosition(35,0);
        addActor(label2);

        MyLabel label3 = new MyLabel(game, "Info", labelStyle);
        label3.setFontScale(0.2f);
        label3.setPosition(35,60);
        addActor(label3);

        MyLabel label4 = new MyLabel(game, "Credits", labelStyle);
        label4.setFontScale(0.2f);
        label4.setPosition(35,30);
        addActor(label4);


        label1.addListener(c1 = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.getMyAssetManager().getSound("click.mp3").play();
                game.setScreen(new FlipperInGameScreen(game));
            }
        });

        this.addListener(c1 = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.getMyAssetManager().getSound("click.mp3").play();
                addTimer(new OneTickTimer(1, new OneTickTimerListener() {
                    @Override
                    public void onTick(OneTickTimer sender, float correction) {
                        super.onTick(sender, correction);
                        game.setScreenBackByStackPop();
                    }
                }));

                removeListener(c1);
            }
        });

        label3.addListener(c1 = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.getMyAssetManager().getSound("click.mp3").play();
                game.setScreen(new FlipperInfoScreen(game));
            }
        });

        label4.addListener(c1 = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.getMyAssetManager().getSound("click.mp3").play();
                game.setScreen(new FlipperCreditScreen(game));
            }
        });



    }
}
