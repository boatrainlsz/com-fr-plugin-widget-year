package com.fr.plugin.widget.year;

import com.fr.design.designer.creator.*;
import com.fr.design.gui.itextfield.UITextField;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.form.ui.WidgetValue;
import com.fr.form.ui.concept.data.ValueInitializer;
import com.fr.general.Inter;
import com.fr.plugin.widget.year.editors.YearRangeEditor;
import com.fr.plugin.widget.year.editors.YearWidgetValueEditor;
import com.fr.stable.ArrayUtils;
import com.fr.stable.core.PropertyChangeAdapter;

import javax.swing.*;
import java.awt.*;
import java.beans.IntrospectionException;

public class XYearEditor extends XDirectWriteEditor {
    private UITextField textField;

    private XWidgetCreator.LimpidButton btn;

    public XYearEditor(YearControl paramYearControl, Dimension paramDimension) {
        super(paramYearControl, paramDimension);
    }

    public XYearEditor(Year paramYear, Dimension paramDimension) {
        super(paramYear, paramDimension);
    }

    public CRPropertyDescriptor[] supportedDescriptor() throws IntrospectionException {
        String str = Inter.getLocText("Fine-Module_Year_Advanced");
        return (CRPropertyDescriptor[]) ArrayUtils.addAll((Object[]) super.supportedDescriptor(), (Object[]) new CRPropertyDescriptor[]{(new CRPropertyDescriptor("widgetValue", this.data.getClass())).setI18NName(Inter.getLocText("Fine-Module_Year_Value")).setEditorClass(YearWidgetValueEditor.class).setPropertyChangeListener(new PropertyChangeAdapter() {
            public void propertyChange() {
                XYearEditor.this.initFieldText();
            }
        }), (new CRPropertyDescriptor("startYear", this.data.getClass())).setI18NName(Inter.getLocText("Fine-Module_Year_Start")).putKeyValue("category", str).setEditorClass(YearRangeEditor.class), (new CRPropertyDescriptor("endYear", this.data.getClass())).setI18NName(Inter.getLocText("Fine-Module_Year_End")).putKeyValue("category", str).setEditorClass(YearRangeEditor.class), (new CRPropertyDescriptor("waterMark", this.data.getClass())).setI18NName(Inter.getLocText("Fine-Module_Year_WaterMark")).putKeyValue("category", str)});
    }

    private void initFieldText() {
        YearControl yearControl = (YearControl) this.data;
        if (yearControl.getWidgetValue() != null) {
            ValueInitializer valueInitializer = yearControl.getWidgetValue();
            Object object = valueInitializer.getValue();
            if (object == null) {
                object = "";
                yearControl.setWidgetValue((ValueInitializer) new WidgetValue(null));
            }
            this.textField.setText(object.toString());
        }
    }

    protected void initXCreatorProperties() {
        super.initXCreatorProperties();
        initFieldText();
    }

    protected JComponent initEditor() {
        if (this.editor == null) {
            this.editor = FRGUIPaneFactory.createBorderLayout_S_Pane();
            this.textField = new UITextField(5);
            this.textField.setOpaque(false);
            this.editor.add((Component) this.textField, "Center");
            this.btn = new XWidgetCreator.LimpidButton("", getIconPath(), toData().isVisible() ? 1.0F : 0.4F);
            this.btn.setPreferredSize(new Dimension(21, 21));
            this.btn.setOpaque(true);
            this.editor.add((Component) this.btn, "East");
            this.editor.setBackground(Color.WHITE);
        }
        return this.editor;
    }

    public String getIconPath() {
        return "/com/fr/plugin/widget/year/images/year.png";
    }

    protected void makeVisible(boolean paramBoolean) {
        this.btn.makeVisible(paramBoolean);
    }

    protected XLayoutContainer getCreatorWrapper(String paramString) {
        return (XLayoutContainer) new XWScaleLayout();
    }

    protected void addToWrapper(XLayoutContainer paramXLayoutContainer, int paramInt1, int paramInt2) {
        setSize(paramInt1, paramInt2);
        paramXLayoutContainer.add((Component) this);
    }

    public boolean shouldScaleCreator() {
        return true;
    }
}
