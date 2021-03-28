package com.fr.plugin.widget.year;

import com.fr.design.beans.BasicBeanPane;
import com.fr.design.fun.impl.AbstractCellWidgetOptionProvider;
import com.fr.form.ui.Widget;
import com.fr.general.Inter;

public class CellYearWidgetOptionProvider extends AbstractCellWidgetOptionProvider {
  public Class<? extends Widget> classForWidget() {
    return (Class)YearControl.class;
  }
  
  public Class<? extends BasicBeanPane<? extends Widget>> appearanceForWidget() {
    return (Class)YearEditorDefinePanel.class;
  }
  
  public String iconPathForWidget() {
    return "/com/fr/plugin/widget/year/images/year.png";
  }
  
  public String nameForWidget() {
    return Inter.getLocText("Fine-Module_Year_Name");
  }
}
