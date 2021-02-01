package Flipper;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class AnimatedKatapultActor extends OneSpriteAnimatedActor {

    public AnimatedKatapultActor(MyGame game) {
        super(game, "Flipper/katapults.png");
        setSize(64,64);
        setPositionCenter(50);
    }
}
