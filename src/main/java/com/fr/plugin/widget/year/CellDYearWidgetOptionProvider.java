package com.fr.plugin.widget.year;

import com.fr.design.beans.BasicBeanPane;
import com.fr.design.fun.impl.AbstractCellWidgetOptionProvider;
import com.fr.form.ui.Widget;

public class CellDYearWidgetOptionProvider extends AbstractCellWidgetOptionProvider {
  public Class<? extends Widget> classForWidget() {
    return (Class)Year.class;
  }
  
  public Class<? extends BasicBeanPane<? extends Widget>> appearanceForWidget() {
    return (Class)DYearEditorDefinePanel.class;
  }
  
  public String iconPathForWidget() {
    return "/com/fr/plugin/widget/year/images/year.png";
  }
  
  public String nameForWidget() {
    return "年份8,9老版本专业";
  }
}
