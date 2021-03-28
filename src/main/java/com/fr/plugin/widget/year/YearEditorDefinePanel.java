package com.fr.plugin.widget.year;

import com.fr.base.FRContext;
import com.fr.base.Formula;
import com.fr.design.editor.ValueEditorPane;
import com.fr.design.editor.ValueEditorPaneFactory;
import com.fr.design.editor.editor.Editor;
import com.fr.design.editor.editor.FormulaEditor;
import com.fr.design.editor.editor.NoneEditor;
import com.fr.design.gui.icheckbox.UICheckBox;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.design.widget.ui.FieldEditorDefinePane;
import com.fr.design.widget.ui.WaterMarkDictPane;
import com.fr.form.ui.FieldEditor;
import com.fr.form.ui.WaterMark;
import com.fr.general.Inter;
import com.fr.plugin.widget.year.editors.YearValueEditor;
import com.fr.plugin.widget.year.editors.YearWidgetValueEditor;
import com.fr.script.Calculator;
import com.fr.stable.UtilEvalError;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class YearEditorDefinePanel extends FieldEditorDefinePane<YearControl> {
  private ValueEditorPane startDv;
  
  private ValueEditorPane endDv;
  
  private YearWidgetValueEditor widgetValueEditor;
  
  public UICheckBox directWriteCheckBox;
  
  private WaterMarkDictPane waterMarkDictPane;
  
  public YearEditorDefinePanel() {
    initComponets();
  }
  
  protected void populateSubFieldEditorBean(YearControl paramYearControl) {
    this.directWriteCheckBox.setSelected(paramYearControl.isDirectEdit());
    this.waterMarkDictPane.populate((WaterMark)paramYearControl);
    populateStartEnd(paramYearControl);
  }
  
  protected YearControl updateSubFieldEditorBean() {
    YearControl yearControl = new YearControl();
    yearControl.setDirectEdit(this.directWriteCheckBox.isSelected());
    this.waterMarkDictPane.update((WaterMark)yearControl);
    updateStartEnd(yearControl);
    return yearControl;
  }
  
  private void initComponets() {
    initComponents();
  }
  
  protected String title4PopupWindow() {
    return "Year";
  }
  
  protected JPanel setFirstContentPane() {
    JPanel jPanel1 = FRGUIPaneFactory.createY_AXISBoxInnerContainer_L_Pane();
    jPanel1.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
    JPanel jPanel2 = FRGUIPaneFactory.createMediumHGapFlowInnerContainer_M_Pane();
    jPanel1.add(jPanel2);
    this.directWriteCheckBox = new UICheckBox(Inter.getLocText("Fine-Module_Year_Allow_Edit"), true);
    jPanel2.add((Component)this.directWriteCheckBox);
    this.waterMarkDictPane = new WaterMarkDictPane();
    jPanel1.add((Component)this.waterMarkDictPane);
    initStartEndYearPane(jPanel1);
    return jPanel1;
  }
  
  protected void initWidgetValue(JPanel paramJPanel) {
    JPanel jPanel = FRGUIPaneFactory.createX_AXISBoxInnerContainer_L_Pane();
    jPanel.add((Component)new UILabel(Inter.getLocText("Fine-Module_Year_Value") + ":"));
    YearControl yearControl = new YearControl();
    this.widgetValueEditor = new YearWidgetValueEditor(yearControl);
    jPanel.add(this.widgetValueEditor.getCustomEditor());
    paramJPanel.add(jPanel);
  }
  
  protected JPanel initStartEndYearPane(JPanel paramJPanel) {
    JPanel jPanel1 = FRGUIPaneFactory.createX_AXISBoxInnerContainer_L_Pane();
    jPanel1.add((Component)new UILabel(Inter.getLocText("Fine-Module_Year_Start") + ":"));
    this.startDv = ValueEditorPaneFactory.createValueEditorPane(new Editor[] { (Editor)new NoneEditor(null, Inter.getLocText("Fine-Module_Year_Value_Null")), (Editor)new YearValueEditor(null, Inter.getLocText("Fine-Module_Year_Name")), (Editor)new FormulaEditor(Inter.getLocText("Fine-Module_Year_Value_Formula")) });
    jPanel1.add((Component)this.startDv);
    JPanel jPanel2 = FRGUIPaneFactory.createX_AXISBoxInnerContainer_L_Pane();
    jPanel2.add((Component)new UILabel(Inter.getLocText("Fine-Module_Year_End") + ":"));
    this.endDv = ValueEditorPaneFactory.createValueEditorPane(new Editor[] { (Editor)new NoneEditor(null, Inter.getLocText("Fine-Module_Year_Value_Null")), (Editor)new YearValueEditor(null, Inter.getLocText("Fine-Module_Year_Name")), (Editor)new FormulaEditor(Inter.getLocText("Fine-Module_Year_Value_Formula")) });
    jPanel2.add((Component)this.endDv);
    paramJPanel.add(jPanel1);
    paramJPanel.add(jPanel2);
    return jPanel1;
  }
  
  public void populateStartEnd(YearControl paramYearControl) {
    Formula formula1 = paramYearControl.getStartYearFM();
    Formula formula2 = paramYearControl.getEndYearFM();
    if (formula1 != null) {
      this.startDv.populate(formula1);
    } else {
      String str = paramYearControl.getStartText();
      this.startDv.populate(str);
    } 
    if (formula2 != null) {
      this.endDv.populate(formula2);
    } else {
      String str = paramYearControl.getEndText();
      this.endDv.populate(str);
    } 
  }
  
  public void updateStartEnd(YearControl paramYearControl) {
    Object object1 = this.startDv.update();
    Object object2 = this.endDv.update();
    Calculator calculator = null;
    if (object1 instanceof Formula) {
      calculator = Calculator.createCalculator();
      Formula formula = (Formula)object1;
      try {
        formula.setResult(calculator.evalValue(formula.getContent()));
      } catch (UtilEvalError utilEvalError) {
        FRContext.getLogger().error(utilEvalError.getMessage(), (Throwable)utilEvalError);
      } 
      object1 = formula.getResult();
      paramYearControl.setStartYearFM(formula);
      paramYearControl.setStartText((String)null);
    } else {
      try {
        paramYearControl.setStartText((object1 == null) ? null : object1.toString());
      } catch (ClassCastException classCastException) {}
    } 
    if (object2 instanceof Formula) {
      calculator = Calculator.createCalculator();
      Formula formula = (Formula)object2;
      try {
        formula.setResult(calculator.evalValue(formula.getContent()));
      } catch (UtilEvalError utilEvalError) {
        FRContext.getLogger().error(utilEvalError.getMessage(), (Throwable)utilEvalError);
      } 
      object2 = formula.getResult();
      paramYearControl.setEndYearFM(formula);
      paramYearControl.setEndText((String)null);
    } else {
      try {
        paramYearControl.setEndText((object2 == null) ? null : object2.toString());
      } catch (ClassCastException classCastException) {}
    } 
  }
}
