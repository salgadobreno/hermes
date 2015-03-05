package com.avixy.qrtoken.core.extensions.components;

import com.avixy.qrtoken.core.extensions.hacks.CustomColorDialog;
import com.avixy.qrtoken.negocio.template.TemplateColor;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import org.apache.commons.lang.ArrayUtils;

/**
 * Created on 23/02/2015
 *
 * @author I7
 */
public class TemplateColorPicker extends ComboBox<TemplateColor> {
    public TemplateColorPicker() {
        Object[] presets = ArrayUtils.removeElement(TemplateColor.Preset.values(), TemplateColor.Preset.TEMPLATE_COLOR_RGB);
//        presets = ArrayUtils.removeElement(presets, TemplateColor.Preset.TEMPLATE_COLOR_FETCH_FROM_MESSAGE);
        for (Object preset : presets) {
            getItems().add(TemplateColor.get((TemplateColor.Preset) preset));
            setCellFactory(new Callback<ListView<TemplateColor>, ListCell<TemplateColor>>() {
                @Override
                public ListCell<TemplateColor> call(ListView<TemplateColor> p) {
                    return new ListCell<TemplateColor>() {
                        private final Rectangle rectangle;

                        {
                            rectangle = new Rectangle(30, 10);
                        }

                        @Override
                        protected void updateItem(TemplateColor item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item == null || empty) {
                                setGraphic(null);
                            } else {
                                rectangle.setFill(item.toColor());
                                setGraphic(rectangle);

                                setText(getItem().getPreset().name().replace("TEMPLATE_COLOR", "").replace("_", " "));
                            }
                        }
                    };
                }
            });
            setButtonCell(new ListCell<TemplateColor>() {
                private final Rectangle rectangle;

                {
                    rectangle = new Rectangle(50, 10);
                }

                @Override
                protected void updateItem(TemplateColor item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        rectangle.setFill(item.toColor());
                        setGraphic(rectangle);
                        setText(getItem().getPreset().name().replace("TEMPLATE_COLOR", "").replace("_", " "));
                    }
                }
            });
        }
        TemplateColor rgbTemplateColor = new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_RGB, 0, 0, 0);
        getItems().add(rgbTemplateColor);
        getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TemplateColor>() {
            @Override
            public void changed(ObservableValue<? extends TemplateColor> observable, TemplateColor oldValue, TemplateColor newValue) {
                if (newValue == rgbTemplateColor) {
                    CustomColorDialog customColorDialog = new CustomColorDialog(getScene().getWindow());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            customColorDialog.show();
                        }
                    });
                }
            }
        });
        getSelectionModel().select(0);
    }
}
