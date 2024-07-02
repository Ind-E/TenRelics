package TenRelicsPackage.relics;

import static TenRelicsPackage.TenRelicsMain.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnTrulyRandomCardFromAvailable;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnTrulyRandomColorlessCardFromAvailable;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.daily.mods.Draft;
import com.megacrit.cardcrawl.daily.mods.Insanity;
import com.megacrit.cardcrawl.daily.mods.SealedDeck;
import com.megacrit.cardcrawl.daily.mods.Shiny;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import TenRelicsPackage.util.Wiz;

public class PandorasShoebox extends BaseRelic {
    private static final String NAME = "PandorasShoebox";
    public static final String ID = makeID(NAME);
    private static final RelicTier TIER = RelicTier.RARE;
    private static final LandingSound SFX = LandingSound.FLAT;

    public PandorasShoebox() {
        super(ID, NAME, TIER, SFX);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        for (int i = 0; i < AbstractDungeon.player.drawPile.size(); i++) {
            AbstractCard card = AbstractDungeon.player.drawPile.group.get(i);
            if (card.hasTag(CardTags.STARTER_DEFEND) || card.hasTag(CardTags.STARTER_STRIKE)) {
                AbstractDungeon.player.drawPile.group.set(i, transform(card, AbstractDungeon.miscRng));
            }
        }
        flash();
        Wiz.atb(new RelicAboveCreatureAction(Wiz.adp(), this));
    }

    public static AbstractCard transform(AbstractCard c, Random rng) {
        switch (c.color.ordinal()) {
            case 1:
                return returnTrulyRandomColorlessCardFromAvailable(c, rng).makeCopy();
            case 2:
                return CardLibrary.getCurse(c, rng).makeCopy();
            default:
                return returnTrulyRandomCardFromAvailable(c, rng).makeCopy();
        }

    }

    @Override
    public boolean canSpawn() {
        return !ModHelper.isModEnabled(Draft.ID) && !ModHelper.isModEnabled(SealedDeck.ID)
                && !ModHelper.isModEnabled(Shiny.ID) && !ModHelper.isModEnabled(Insanity.ID)
                && AbstractDungeon.player.masterDeck.group.stream()
                        .filter(card -> card.hasTag(CardTags.STARTER_DEFEND) || card.hasTag(CardTags.STARTER_STRIKE))
                        .count() >= 3;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PandorasShoebox();
    }
}
