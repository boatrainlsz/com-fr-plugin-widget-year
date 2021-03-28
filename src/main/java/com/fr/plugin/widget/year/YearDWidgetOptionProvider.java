package com.fr.plugin.widget.year;

import com.fr.design.fun.impl.AbstractFormWidgetOptionProvider;
import com.fr.form.ui.Widget;

public class YearDWidgetOptionProvider extends AbstractFormWidgetOptionProvider {
  public Class<? extends Widget> classForWidget() {
    return (Class)Year.class;
  }
  
  public Class<?> appearanceForWidget() {
    return DXYearEditor.class;
  }
  
  public String iconPathForWidget() {
    return "/com/fr/plugin/widget/year/images/year.png";
  }
  
  public String nameForWidget() {
    return "年份8,9老版本专用";
  }
}
