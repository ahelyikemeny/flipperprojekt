package Flipper;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class FlipperCreditScreen extends MyScreen {
    public FlipperCreditScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new FlipperCreditStage(game),1,true);
    }

    @Override
    public AssetList getAssetList() {
        return null;
    }
}
