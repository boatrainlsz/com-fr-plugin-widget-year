package com.fr.plugin.widget.year.editors;

import com.fr.base.BaseUtils;
import com.fr.design.editor.editor.Editor;
import com.fr.design.layout.FRGUIPaneFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

public class YearValueEditor extends Editor<String> {
    private UIYearPicker uiYearPicker;

    public YearValueEditor(String paramString) {
        this(paramString, (String) null);
    }

    public YearValueEditor() {
        this((String) null);
    }

    public YearValueEditor(String paramString1, String paramString2) {
        setLayout(FRGUIPaneFactory.createBorderLayout());
        this.uiYearPicker = new UIYearPicker();
        this.uiYearPicker.setEditable(false);
        this.uiYearPicker.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent param1ItemEvent) {
                YearValueEditor.this.fireStateChanged();
            }
        });
        this.uiYearPicker.setFocusTraversalKeysEnabled(false);
        add((Component) this.uiYearPicker, "Center");
        setValue(paramString1);
        setName(paramString2);
    }

    public JComponent getEditComp() {
        return (JComponent) this.uiYearPicker;
    }

    public String getValue() {
        return String.valueOf(this.uiYearPicker.getSelectedDate());
    }

    public void setValue(String paramString) {
        if (paramString == null)
            return;
        if (paramString.equals("")) {
            this.uiYearPicker.setSelectedDate((Integer) null);
        } else {
            this.uiYearPicker.setSelectedDate(Integer.valueOf(Integer.parseInt(paramString)));
        }
    }

    public void setEnabled(boolean paramBoolean) {
        super.setEnabled(paramBoolean);
        this.uiYearPicker.setEnabled(paramBoolean);
    }

    public void selected() {
        this.uiYearPicker.setSelectedItem(Integer.valueOf(Calendar.getInstance().get(1)));
    }

    public void requestFocus() {
        this.uiYearPicker.requestFocus();
    }

    public Icon getIcon() {
        String str = "com/fr/plugin/widget/year/images/type_year.png";
        try {
            return BaseUtils.readIcon(str);
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    public boolean accept(Object paramObject) {
        return paramObject instanceof String;
    }
}
