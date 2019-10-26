package game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import game.ui.SwitchButtons.ActionSwitchListener;
import game.ui.SwitchButtons.State;
import game.utils.Skins;

public class ActionBar extends Table implements ActionSwitchListener {

    private static final float PAD_TOP = -17f;
    private final ClickListener mouseListener;
    private SwitchButtons buttons;
    private SwitchButtons relleno;
    private SpellView spellView;
    private EchisosCompletos echisosCompletos;
    private Inventory inventory;
    private QuickInventory quickInventory;

    ActionBar() {
        super(Skins.COMODORE_SKIN);
        mouseListener = new ClickListener();
        buttons = new SwitchButtons();
        buttons.addListener(this);
        buttons.addListener(mouseListener);
        relleno = new SwitchButtons (  );
        spellView = new SpellView();
        inventory = new Inventory();
        quickInventory =new QuickInventory ();
        echisosCompletos = new EchisosCompletos ();

        add(relleno).top();
        relleno.setVisible(false);
        add(buttons).top().row();
        add(inventory).padTop(PAD_TOP);
        add(quickInventory).padTop(PAD_TOP);
    }

    @Override
    public void notify(State state) {
        switch (state) {
            case SPELLS:
                clear();
                add(relleno).top();
                relleno.setVisible(false);
                add(buttons).top().right().row();
                add(echisosCompletos).padTop(PAD_TOP).right ();
                add(spellView).padTop(PAD_TOP).right();
                break;
            case INVENTORY:
                clear();
                add(relleno).top();
                relleno.setVisible(false);
                add(buttons).top().right ().row();
                add(inventory).padTop(PAD_TOP).right ();
                add(quickInventory).padTop(PAD_TOP);
                break;
        }
    }

    public boolean isOver() {
        return getInventory().isOver() || getSpellView().isOver() || getQuickInventory().isOver()  || mouseListener.isOver();
    }

    public void toggle() {
        buttons.toggle();
    }

    public Inventory getInventory() {
        return inventory;
    }

    protected SpellView getSpellView() {
        return spellView;
    }

    public QuickInventory getQuickInventory() {
        return quickInventory;
    }

    public EchisosCompletos getEchisosCompletos(){
        return echisosCompletos;
    }

    public void scrolled(int amount) {
        if (getInventory().isOver()) {
            getInventory().scrolled(amount);
        } else if (getSpellView().isOver()) {
            // TODO
        }
    }
}
