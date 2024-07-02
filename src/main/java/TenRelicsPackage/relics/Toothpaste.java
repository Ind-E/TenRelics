package TenRelicsPackage.relics;

import static TenRelicsPackage.TenRelicsMain.makeID;

import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Toothpaste extends BaseRelic {
    private static final String NAME = "Toothpaste";
    public static final String ID = makeID(NAME);
    private static final RelicTier TIER = RelicTier.COMMON;
    private static final LandingSound SFX = LandingSound.FLAT;

    public Toothpaste() {
        super(ID, NAME, TIER, SFX);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Toothpaste();
    }
}
