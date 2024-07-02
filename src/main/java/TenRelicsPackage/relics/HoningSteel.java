package TenRelicsPackage.relics;

import static TenRelicsPackage.TenRelicsMain.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;

import TenRelicsPackage.modifiers.PlusXPermanentDamageModifer;
import TenRelicsPackage.util.Wiz;
import basemod.helpers.CardModifierManager;

public class HoningSteel extends BaseRelic {
    private static final String NAME = "HoningSteel";
    public static final String ID = makeID(NAME);
    private static final RelicTier TIER = RelicTier.UNCOMMON;
    private static final LandingSound SFX = LandingSound.SOLID;
    private static final int hone = 2;

    public HoningSteel() {
        super(ID, NAME, TIER, SFX);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], hone);
    }

    @Override
    public void onEquip() {
        for (AbstractCard c : Wiz.adp().masterDeck.group) {
            if (c.type == CardType.ATTACK) {
                CardModifierManager.addModifier(c, new PlusXPermanentDamageModifer(hone));
            }
        }
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HoningSteel();
    }
}
