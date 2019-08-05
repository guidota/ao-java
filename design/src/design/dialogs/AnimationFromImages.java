package design.dialogs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import design.designers.AnimationDesigner;
import design.designers.IDesigner;
import design.screens.ScreenEnum;
import design.screens.ScreenManager;
import design.screens.views.View;
import game.handlers.AnimationHandler;
import graphics.AOImageActor;
import model.textures.AOAnimation;
import model.textures.AOImage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static launcher.DesignCenter.SKIN;

public class AnimationFromImages {

    private static void sort(List<AOImage> images) {
        images.sort((AOImage o1, AOImage o2) ->
                o1.getY() == o2.getY() ?
                        Comparator.comparingInt(AOImage::getX).compare(o1, o2) :
                        Comparator.comparingInt(AOImage::getY).compare(o1, o2));
    }

    public static void show(List<AOImage> images) {
        View current = (View) ScreenManager.getInstance().getCurrent();
        Stage stage = current.getStage();
        sort(images);
        AnimationDesigner designer = (AnimationDesigner) ScreenEnum.ANIMATION_VIEW.getScreen().getDesigner();
        NewAnimation animation = new NewAnimation(designer, current.getAnimationHandler(), images);
        animation.show(stage);
    }

    static class NewAnimation extends Dialog {

        private final int columns;
        private final AnimationDesigner designer;
        private final AnimationHandler animationHandler;
        private final List<AOImage> images;

        public NewAnimation(AnimationDesigner designer, AnimationHandler animationHandler, List<AOImage> images) {
            super("New Animation", SKIN);
            this.designer = designer;
            this.animationHandler = animationHandler;
            this.images = images;
            columns = Math.max(images.size() / 4, 1);
            createContent();
        }

        public void reduce(List<AOImage> images) {
            this.images.removeAll(images);
        }

        @Override
        protected void result(Object object) {
            if (object instanceof List) {
                List<AOImage> list = (List<AOImage>) object;
                float speed = (float) 2 / list.size() * 1000;
                int[] frames = list
                        .stream()
                        .mapToInt(AOImage::getId)
                        .toArray();
                AOAnimation animation = new AOAnimation();
                animation.setFrames(frames);
                animation.setSpeed(speed);
                animation.setId(designer.getFreeId());
                designer.add(animation);
                reduce(list);
                if (images.isEmpty()) {
                    end();
                } else {
                    refresh();
                }
            } else if ((Boolean) object) {
                end();
            }
        }

        private void end() {
            ScreenEnum.ANIMATION_VIEW.getScreen().loadItems(Optional.empty());
            hide();
        }

        private void refresh() {
            clear();
            createContent();
        }

        private void createContent() {
            Table content = new Table(SKIN);
            List<AOImage> selected = getAoImages(content);
            add(new ScrollPane(content)).growX();

            Table buttons = getButtons(selected);
            add(buttons).growX();
        }

        @NotNull
        private Table getButtons(List<AOImage> selected) {
            Table buttons = new Table();
            buttons.defaults().space(20);
            TextButton done = new TextButton("Done", SKIN);
            done.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    result(true);
                }
            });
            TextButton create = new TextButton("Create", SKIN);
            create.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    result(selected);
                }
            });
            buttons.add(create).row();
            buttons.add(done);
            return buttons;
        }

        @NotNull
        private List<AOImage> getAoImages(Table content) {
            content.add("Select images to use: ").left().row();
            List<AOImage> selected = new ArrayList<>();
            Table imagesTable = new Table();
            imagesTable.defaults().space(5);
            for (int i = 1; i <= images.size(); i++) {
                SelectableImage image = new SelectableImage(images.get(i - 1), animationHandler);
                image.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (selected.contains(image.getImage())) {
                            selected.remove(image.getImage());
                        } else {
                            selected.add(image.getImage());
                        }
                    }
                });
                imagesTable.add(image).maxSize(64, 64);
                if (i % columns == 0 && i < images.size()) {
                    imagesTable.row();
                }
            }
            content.add(new ScrollPane(imagesTable)).expandX();
            return selected;
        }
    }

    static class SelectableImage extends Button {

        private final AOImageActor aoImageActor;

        SelectableImage(AOImage image, AnimationHandler animationHandler) {
            super(SKIN, "color-base-select");
            aoImageActor = new AOImageActor(image, animationHandler);
            Container<Image> container = new Container<>();
            container.setActor(aoImageActor);
            container.setClip(true);
            container.fill(false);
            add(container);
        }

        public AOImage getImage() {
            return aoImageActor.getImage();
        }

    }

}
