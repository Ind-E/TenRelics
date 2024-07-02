package TenRelicsPackage.modifiers;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.AbstractCardModifier;

public class PlusXPermanentDamageModifer extends AbstractCardModifier {
    private int bonusDamage;

    public PlusXPermanentDamageModifer(int bonusDamage) {
        this.bonusDamage = bonusDamage;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card.baseDamage > 0) {
            card.baseDamage += 2;
            card.damage = card.baseDamage;
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PlusXPermanentDamageModifer(bonusDamage);
    }

}
