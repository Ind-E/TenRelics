package TenRelicsPackage.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import TenRelicsPackage.relics.Toothpaste;
import TenRelicsPackage.util.Wiz;

@SpirePatch(clz = AbstractMonster.class, method = "damage")
public class ToothpastePatch {
    private static boolean wasHP40 = false;

    @SpirePrefixPatch
    public static void Prefix(AbstractMonster __instance) {
        if (!Wiz.adp().hasRelic(Toothpaste.ID))
            return;
        if (!__instance.isDying && !__instance.isEscaping && !__instance.isDead && !__instance.halfDead) {
            wasHP40 = __instance.currentHealth > MathUtils.floor(__instance.maxHealth * 0.40F);
        } else {
            wasHP40 = false;
        }
    }

    @SpirePostfixPatch
    public static void Postfix(AbstractMonster __instance) {
        if (!Wiz.adp().hasRelic(Toothpaste.ID))
            return;
        if (!__instance.isDying && !__instance.isEscaping && !__instance.isDead && !__instance.halfDead) {
            if (wasHP40 && __instance.currentHealth <= MathUtils.floor(__instance.maxHealth * 0.40F)) {
                Wiz.adp().getRelic(Toothpaste.ID).flash();
                Wiz.atb(new RelicAboveCreatureAction(Wiz.adp(), Wiz.adp().getRelic(Toothpaste.ID)));
                Wiz.applyToEnemy(__instance, new WeakPower(__instance, 1, false));
            }
        }
    }

}
