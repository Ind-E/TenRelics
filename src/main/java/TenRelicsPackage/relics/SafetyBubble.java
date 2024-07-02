package TenRelicsPackage.relics;

import static TenRelicsPackage.TenRelicsMain.makeID;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import TenRelicsPackage.util.Wiz;

public class SafetyBubble extends BaseRelic {
    private static final String NAME = "SafetyBubble";
    public static final String ID = makeID(NAME);
    private static final RelicTier TIER = RelicTier.UNCOMMON;
    private static final LandingSound SFX = LandingSound.FLAT;
    private static final int block = 1;

    public SafetyBubble() {
        super(ID, NAME, TIER, SFX);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], block);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageType.THORNS && info.type != DamageType.HP_LOSS && info.owner != null
                && info.owner != Wiz.adp()) {
            counter++;
        }
        return damageAmount;
    }

    @Override
    public void onPlayerEndTurn() {
        if (counter > 0) {
            flash();
            Wiz.atb(new RelicAboveCreatureAction(Wiz.adp(), this));
            Wiz.atb(new GainBlockAction(Wiz.adp(), counter, true));
        }
    }

    @Override
    public void atBattleStart() {
        counter = 0;
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SafetyBubble();
    }
}
