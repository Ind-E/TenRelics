package TenRelicsPackage.actions;

import static basemod.DevConsole.consoleFont;

import java.util.function.Consumer;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import basemod.BaseMod;
import basemod.interfaces.RenderSubscriber;
import basemod.interfaces.TextReceiver;
import basemod.patches.com.megacrit.cardcrawl.helpers.input.ScrollInputProcessor.TextInput;


public class TypingAction extends AbstractGameAction implements TextReceiver, RenderSubscriber {
    private Consumer<String> callback;
    private String text = "";
    private boolean openedScreen = false;
    private String problem;

    public TypingAction(Consumer<String> callback, String problem) {
        this.callback = callback;
        this.problem = problem;
    }

    @Override
    public void update() {
        if (!openedScreen) {
            BaseMod.subscribe(this);
            TextInput.startTextReceiver(this);
            openedScreen = true;
        }
    }

    @Override
    public void receiveRender(SpriteBatch spriteBatch) {
        float curY = 350 * Settings.scale;
        FontHelper.renderDeckViewTip(spriteBatch, problem, curY, Settings.CREAM_COLOR);
        curY -= 50 * Settings.scale;

        FontHelper.renderDeckViewTip(spriteBatch, this.getCurrentText(), curY, Settings.CREAM_COLOR);
    }

    @Override
    public String getCurrentText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean acceptCharacter(char c) {
        return consoleFont.getData().hasGlyph(c);
    }

    @Override
    public boolean onKeyUp(int keycode) {
        if (keycode == Input.Keys.ENTER) {
            isDone = true;
            callback.accept(getCurrentText());
            TextInput.stopTextReceiver(this);
            BaseMod.unsubscribe(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}