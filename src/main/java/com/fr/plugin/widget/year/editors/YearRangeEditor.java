package com.fr.plugin.widget.year.editors;

import com.fr.design.Exception.ValidationException;
import com.fr.design.editor.ValueEditorPane;
import com.fr.design.editor.ValueEditorPaneFactory;
import com.fr.design.editor.editor.Editor;
import com.fr.design.editor.editor.FormulaEditor;
import com.fr.design.editor.editor.NoneEditor;
import com.fr.design.mainframe.widget.editors.AbstractPropertyEditor;
import com.fr.general.Inter;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class YearRangeEditor extends AbstractPropertyEditor {
    private ValueEditorPane dv = ValueEditorPaneFactory.createValueEditorPane(new Editor[]{(Editor) new NoneEditor(null, Inter.getLocText("Fine-Module_Year_Value_Null")), new YearValueEditor(null, Inter.getLocText("Fine-Module_Year_Name")), (Editor) new FormulaEditor(Inter.getLocText("Fine-Module_Year_Value_Formula"))});

    public YearRangeEditor() {
        this.dv.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
                YearRangeEditor.this.firePropertyChanged();
            }
        });
        for (Editor editor : this.dv.getCards()) {
            editor.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent param1ChangeEvent) {
                    YearRangeEditor.this.firePropertyChanged();
                }
            });
        }
    }

    public void validateValue() throws ValidationException {
    }

    public Component getCustomEditor() {
        return (Component) this.dv;
    }

    public Object getValue() {
        return this.dv.update();
    }

    public void setValue(Object paramObject) {
        this.dv.populate(paramObject);
    }
}
