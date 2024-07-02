package TenRelicsPackage.relics;

import static TenRelicsPackage.TenRelicsMain.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnTrulyRandomColorlessCardFromAvailable;

import com.evacipated.cardcrawl.mod.stslib.relics.CardRewardSkipButtonRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class BowlOfRice extends BaseRelic implements CardRewardSkipButtonRelic {
    private static final String NAME = "BowlOfRice";
    public static final String ID = makeID(NAME);
    private static final RelicTier TIER = RelicTier.UNCOMMON;
    private static final LandingSound SFX = LandingSound.FLAT;

    public BowlOfRice() {
        super(ID, TIER, SFX);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public String getButtonLabel() {
        return DESCRIPTIONS[1];
    }

    @Override
    public void onClickedButton() {
        AbstractCard randomCard = returnTrulyRandomColorlessCardFromAvailable(Wound.ID, AbstractDungeon.miscRng);
        randomCard.upgrade();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(randomCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BowlOfRice();
    }
}
