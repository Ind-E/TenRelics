package TenRelicsPackage.relics;

import static TenRelicsPackage.TenRelicsMain.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import TenRelicsPackage.util.Wiz;

public class DemonicPact extends BaseRelic {
    private static final String NAME = "DemonicPact";
    public static final String ID = makeID(NAME);
    private static final RelicTier TIER = RelicTier.SHOP;
    private static final LandingSound SFX = LandingSound.MAGICAL;
    private static final int punishment = 6;
    private static final int draw = 1;

    public DemonicPact() {
        super(ID, NAME, TIER, SFX);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], draw, punishment);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == CardType.ATTACK) {
            Wiz.atb(new DrawCardAction(draw));
            if (counter == 1) {
                Wiz.atb(new RelicAboveCreatureAction(Wiz.adp(), this));
                Wiz.atb(new LoseHPAction(Wiz.adp(), Wiz.adp(), punishment));
                flash();
            }
            counter = 1;
        } else {
            counter = 0;
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
        return new DemonicPact();
    }
}
