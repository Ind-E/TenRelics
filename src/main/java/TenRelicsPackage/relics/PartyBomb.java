package TenRelicsPackage.relics;

import static TenRelicsPackage.TenRelicsMain.imagePath;
import static TenRelicsPackage.TenRelicsMain.makeID;
import static TenRelicsPackage.TenRelicsMain.stsWords;
import static TenRelicsPackage.TenRelicsMain.threeLetterCombos;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.RelicWithButton;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.TheBombPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

import TenRelicsPackage.actions.TypingAction;
import TenRelicsPackage.util.TextureLoader;
import TenRelicsPackage.util.Wiz;

public class PartyBomb extends BaseRelic implements RelicWithButton {

    private static final String NAME = "PartyBomb";
    public static final String ID = makeID(NAME);
    private static final RelicTier TIER = RelicTier.RARE;
    private static final LandingSound SFX = LandingSound.FLAT;

    public PartyBomb() {
        super(ID, NAME, TIER, SFX);
        counter = 3;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public ArrayList<PowerTip> getHoverTips() {
        ArrayList<PowerTip> tips = new ArrayList<>();
        if (counter <= 0) {
            tips.add(new PowerTip("Party Bomb", "Detonated."));
        } else if (grayscale) {
            tips.add(new PowerTip("Party Bomb", "Used up this combat."));
        } else {
            tips.add(new PowerTip("Party Bomb", "Click to Activate."));
        }
        return tips;
    }

    @Override
    public Texture getTexture() {
        return TextureLoader.getTexture(imagePath("relics/PartyBombButton.png"));
    }

    @Override
    public boolean isButtonDisabled() {
        return grayscale || AbstractDungeon.getCurrRoom().monsters == null
                || AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()
                || AbstractDungeon.actionManager.turnHasEnded
                || AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT;
    }

    @Override
    public void onButtonPress() {
        grayscale = true;
        String three_letters = Wiz.getRandomItem(threeLetterCombos, AbstractDungeon.relicRng);
        addToBot(new TypingAction((userInput) -> {
            if (!stsWords.contains(userInput) || !userInput.contains(three_letters)) {
                flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                counter--;
                if (counter <= 0) {
                    AbstractDungeon.player.loseRelic(PartyBomb.ID);
                    addToBot(new VFXAction(new ExplosionSmallEffect(hb.cX, hb.cY), 0.1F));
                }
                return;
            }
            flash();
            Wiz.applyToSelf(new TheBombPower(AbstractDungeon.player, 3, 40));

        }, "Type a word that contains " + three_letters));
    }

    @Override
    public void onVictory() {
        grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PartyBomb();
    }

}
