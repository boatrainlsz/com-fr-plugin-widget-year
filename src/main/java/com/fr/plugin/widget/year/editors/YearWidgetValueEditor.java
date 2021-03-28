package com.fr.plugin.widget.year.editors;

import com.fr.design.Exception.ValidationException;
import com.fr.design.editor.ValueEditorPane;
import com.fr.design.editor.editor.Editor;
import com.fr.design.editor.editor.FormulaEditor;
import com.fr.design.editor.editor.NoneEditor;
import com.fr.design.mainframe.widget.editors.AbstractPropertyEditor;
import com.fr.design.mainframe.widget.editors.DataBindingEditor;
import com.fr.design.mainframe.widget.editors.ServerDataBindingEditor;
import com.fr.form.ui.DataControl;
import com.fr.form.ui.WidgetValue;
import com.fr.general.Inter;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class YearWidgetValueEditor extends AbstractPropertyEditor {
    private DataControl widget;

    private ValueEditorPane wep;

    public YearWidgetValueEditor(Object paramObject) {
        this(paramObject, false);
    }

    public YearWidgetValueEditor(Object paramObject, boolean paramBoolean) {
        this.widget = (DataControl) paramObject;
        Editor[] arrayOfEditor = createWidgetValueEditor(this.widget, paramBoolean);
        for (Editor editor : arrayOfEditor) {
            editor.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent param1ChangeEvent) {
                    YearWidgetValueEditor.this.firePropertyChanged();
                }
            });
        }
        this.wep = new ValueEditorPane(arrayOfEditor);
        this.wep.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
                YearWidgetValueEditor.this.firePropertyChanged();
            }
        });
    }

    public static Editor[] createWidgetValueEditor(DataControl paramDataControl, boolean paramBoolean) {
        int[] arrayOfInt = paramDataControl.getValueType();
        Editor[] arrayOfEditor = new Editor[arrayOfInt.length];
        for (byte b = 0; b < arrayOfInt.length; b++)
            arrayOfEditor[b] = createWidgetValueEditorByType(arrayOfInt[b], paramBoolean);
        return arrayOfEditor;
    }

    public static Editor createWidgetValueEditorByType(int paramInt, boolean paramBoolean) {
        switch (paramInt) {
            case 8:
                return (Editor) new NoneEditor(null, Inter.getLocText("Fine-Module_Year_Value_Null"));
            case 7:
                return new YearValueEditor(null, Inter.getLocText("Fine-Module_Year_Name"));
            case 3:
                return (Editor) new FormulaEditor(Inter.getLocText("Fine-Module_Year_Value_Formula"));
            case 2:
                return paramBoolean ? (Editor) new ServerDataBindingEditor() : (Editor) new DataBindingEditor();
        }
        return null;
    }

    public void validateValue() throws ValidationException {
    }

    public Component getCustomEditor() {
        return (Component) this.wep;
    }

    public Object getValue() {
        return new WidgetValue(this.wep.update());
    }

    public void setValue(Object paramObject) {
        if (paramObject != null)
            this.wep.populate(((WidgetValue) paramObject).getValue());
    }
}
