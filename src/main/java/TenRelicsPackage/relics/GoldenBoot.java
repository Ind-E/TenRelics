package TenRelicsPackage.relics;

import static TenRelicsPackage.TenRelicsMain.makeID;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Boot;
import com.megacrit.cardcrawl.relics.Ectoplasm;

import TenRelicsPackage.util.Wiz;

public class GoldenBoot extends BaseRelic {
    private static final String NAME = "GoldenBoot";
    public static final String ID = makeID(NAME);
    private static final RelicTier TIER = RelicTier.RARE;
    private static final LandingSound SFX = LandingSound.FLAT;
    public static final int gold = 75;

    public GoldenBoot() {
        super(ID, NAME, TIER, SFX);
        counter = gold;
    }

    @Override
    public void onEquip() {
        AbstractDungeon.commonRelicPool.remove(Boot.ID);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], gold);
    }

    @Override
    public boolean canSpawn() {
        return Settings.isEndless
                || AbstractDungeon.floorNum <= 30 && !Wiz.adp().hasRelic(Ectoplasm.ID) && !Wiz.adp().hasRelic(Boot.ID);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GoldenBoot();
    }
}
