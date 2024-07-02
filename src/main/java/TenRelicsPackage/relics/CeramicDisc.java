package TenRelicsPackage.relics;

import static TenRelicsPackage.TenRelicsMain.makeID;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import TenRelicsPackage.util.Wiz;

public class CeramicDisc extends BaseRelic {
    private static final String NAME = "CeramicDisc";
    public static final String ID = makeID(NAME);
    private static final RelicTier TIER = RelicTier.COMMON;
    private static final LandingSound SFX = LandingSound.FLAT;
    private static final int GOLD = 50;
    private int monstersKilledThisTurn = 0;

    public CeramicDisc() {
        super(ID, NAME, TIER, SFX);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        monstersKilledThisTurn++;
        if (monstersKilledThisTurn == 2) {
            flash();
            addToBot(new RelicAboveCreatureAction(Wiz.adp(), this));
            Wiz.adp().gainGold(GOLD);
        }
    }

    @Override
    public void atBattleStart() {
        monstersKilledThisTurn = 0;
    }
}
