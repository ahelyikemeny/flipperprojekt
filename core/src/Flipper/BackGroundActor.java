package Flipper;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class BackGroundActor extends OneSpriteStaticActor {
    public BackGroundActor(MyGame game) {
        super(game, "Flipper/background.png");
        this.setSize(90,160);
        this.setPositionCenter(30);
    }
}
