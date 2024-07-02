package TenRelicsPackage.util;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import TenRelicsPackage.TenRelicsMain;

public class GeneralUtils {
    public static String arrToString(Object[] arr) {
        if (arr == null)
            return null;
        if (arr.length == 0)
            return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length - 1; ++i) {
            sb.append(arr[i]).append(", ");
        }
        sb.append(arr[arr.length - 1]);
        return sb.toString();
    }

    public static String removePrefix(String ID) {
        return ID.substring(ID.indexOf(":") + 1);
    }

    public static AbstractCard generateRandomStatusCard() {
        List<AbstractCard> statusCards = new ArrayList<>();

        for (AbstractCard card : CardLibrary.getAllCards()) {
            if (card.type == CardType.STATUS) {
                statusCards.add(card);
            }
        }

        if (!statusCards.isEmpty()) {
            AbstractCard randomStatusCard = statusCards
                    .get(AbstractDungeon.cardRandomRng.random(statusCards.size() - 1)).makeCopy();
            return randomStatusCard;
        }
        throw new RuntimeException("No Status cards found");
    }

    public static String makeKeyword(String keyword) {
        return TenRelicsMain.modID + ":" + keyword;
    }
}
