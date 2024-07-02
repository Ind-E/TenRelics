package TenRelicsPackage.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import TenRelicsPackage.relics.GoldenBoot;

public class GoldenBootPatch {
    @SpirePatch(clz = AbstractPlayer.class, method = "gainGold")
    public static class onGainGoldIncrementCounter {
        public static void Prefix(AbstractPlayer __instance, @ByRef int[] amount) {
            if (__instance.hasRelic(Ectoplasm.ID))
                return;
            GoldenBoot idol = (GoldenBoot) __instance.getRelic(GoldenBoot.ID);
            if (idol == null)
                return;

            int temp = amount[0];
            while (temp >= idol.counter) {
                temp -= idol.counter;
                idol.counter = GoldenBoot.gold;
                idol.flash();
                upgradeRandomCard();
            }
            if (idol.counter > temp) {
                idol.counter -= temp;
            }
        }
    }

    private static void upgradeRandomCard() {
        ArrayList<AbstractCard> upgradableCards = new ArrayList<>();
        AbstractDungeon.player.masterDeck.group.stream()
                .filter(AbstractCard::canUpgrade)
                .forEach(upgradableCards::add);

        if (!upgradableCards.isEmpty()) {
            AbstractCard randomCard = upgradableCards
                    .get(AbstractDungeon.cardRandomRng.random(upgradableCards.size() - 1));
            randomCard.upgrade();
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(randomCard.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects
                    .add(new UpgradeShineEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
        }

    }

}
