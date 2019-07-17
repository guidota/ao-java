package design.screens.views;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import design.designers.DescriptorDesigner;
import design.editors.WeaponEditor;
import model.descriptors.Descriptor;
import model.descriptors.WeaponDescriptor;
import org.jetbrains.annotations.NotNull;

public class WeaponsView extends DescriptorView {
    public WeaponsView() {
        super(new DescriptorDesigner(WeaponDescriptor.class));
    }

    @NotNull
    @Override
    Table getTable(Descriptor descriptor) {
        return WeaponEditor.create((WeaponDescriptor) descriptor);
    }
}
