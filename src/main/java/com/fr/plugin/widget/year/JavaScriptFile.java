package com.fr.plugin.widget.year;

import com.fr.stable.fun.impl.AbstractJavaScriptFileHandler;

public class JavaScriptFile extends AbstractJavaScriptFileHandler {
  public String[] pathsForFiles() {
    return new String[] { "/com/fr/plugin/widget/year/web/YearPicker.js", "/com/fr/plugin/widget/year/web/BaseYearEditor.js", "/com/fr/plugin/widget/year/web/comboYear.js" };
  }
}
