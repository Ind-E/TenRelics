package TenRelicsPackage.relics;

import static TenRelicsPackage.TenRelicsMain.makeID;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CreditCard extends BaseRelic {
    private static final String NAME = "CreditCard";
    public static final String ID = makeID(NAME);
    private static final RelicTier TIER = RelicTier.COMMON;
    private static final LandingSound SFX = LandingSound.FLAT;
    private static final int cooldown = 7;
    private static final int extra_cards = 4;

    public CreditCard() {
        super(ID, NAME, TIER, SFX);
        counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], cooldown, extra_cards);
    }

    @Override
    public int changeNumberOfCardsInReward(int numberOfCards) {
        counter++;
        if (counter >= cooldown) {
            flash();
            counter = 0;
            return numberOfCards + extra_cards;
        }
        return numberOfCards;
    }

    @Override
    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 40;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CreditCard();
    }
}
