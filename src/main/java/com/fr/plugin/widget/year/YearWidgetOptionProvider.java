package com.fr.plugin.widget.year;

import com.fr.design.fun.impl.AbstractFormWidgetOptionProvider;
import com.fr.form.ui.Widget;

public class YearWidgetOptionProvider extends AbstractFormWidgetOptionProvider {
    public Class<? extends Widget> classForWidget() {
        return (Class) YearControl.class;
    }

    public Class<?> appearanceForWidget() {
        return XYearEditor.class;
    }

    public String iconPathForWidget() {
        return "/com/fr/plugin/widget/year/images/year.png";
    }

    public String nameForWidget() {
        return "年份插件新版专用";
    }
}
