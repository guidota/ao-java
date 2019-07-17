package design.editors;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import model.descriptors.Descriptor;
import model.descriptors.WeaponDescriptor;

public class WeaponEditor extends DescriptorEditor {

    public WeaponEditor(Descriptor descriptor) {
        super(descriptor);
    }

    public static Table create(WeaponDescriptor descriptor) {
        return new WeaponEditor(descriptor).getTable(descriptor);
    }

}
