package com.fr.plugin.widget.year;

import com.fr.base.FRContext;
import com.fr.base.Formula;
import com.fr.base.ParameterMapNameSpace;
import com.fr.form.ui.DataControl;
import com.fr.form.ui.DirectWriteEditor;
import com.fr.form.ui.FieldEditor;
import com.fr.form.ui.WidgetValue;
import com.fr.form.ui.concept.data.ValueInitializer;
import com.fr.general.ComparatorUtils;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.plugin.transform.ExecuteFunctionRecord;
import com.fr.plugin.transform.FunctionRecorder;
import com.fr.script.Calculator;
import com.fr.stable.ArrayUtils;
import com.fr.stable.CodeUtils;
import com.fr.stable.FormulaProvider;
import com.fr.stable.UtilEvalError;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.script.CalculatorProvider;
import com.fr.stable.script.NameSpace;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLableReader;
import com.fr.web.core.TemplateSessionIDInfo;
import com.fr.web.utils.WebUtils;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@FunctionRecorder
public class Year extends DirectWriteEditor implements DataControl {
  private String startYear;
  
  private String endYear;
  
  private Formula startYearFM;
  
  private Formula endYearFM;
  
  private WidgetValue widgetValue = new WidgetValue();
  
  public static final int TYPE_YEAR = 7;
  
  public static final int TYPE_YEAR_NONE = 8;
  
  public String getXType() {
    return "year";
  }
  
  public int[] getValueType() {
    return new int[] { 8, 7, 3, 2 };
  }
  
  public void setWidgetValue(ValueInitializer paramValueInitializer) {}
  
  public void setWidgetValue(WidgetValue paramWidgetValue) {
    this.widgetValue = paramWidgetValue;
  }
  
  public WidgetValue getWidgetValue() {
    return this.widgetValue;
  }
  
  public void createValueResult(CalculatorProvider paramCalculatorProvider, JSONObject paramJSONObject) {
    if (getWidgetValue() != null) {
      Map map = paramJSONObject.toMap();
      WidgetValue widgetValue = getWidgetValue();
      Object object = null;
      object = widgetValue.executeResult(paramCalculatorProvider);
      try {
        paramJSONObject.put(this.widgetName.toUpperCase(), (object == null) ? "" : object);
      } catch (JSONException jSONException) {
        FRContext.getLogger().error(jSONException.getMessage(), (Throwable)jSONException);
      } 
    } 
  }
  
  public String[] dependence(CalculatorProvider paramCalculatorProvider) {
    return super.dependence(paramCalculatorProvider);
  }
  
  public Object getStartYearValue(Calculator paramCalculator) throws Exception {
    return (this.startYearFM != null) ? paramCalculator.eval((FormulaProvider)this.startYearFM) : this.startYear;
  }
  
  public Object getEndYearValue(Calculator paramCalculator) throws Exception {
    return (this.endYearFM != null) ? paramCalculator.eval((FormulaProvider)this.endYearFM) : this.endYear;
  }
  
  public Object getStartYear() {
    return (this.startYearFM != null) ? this.startYearFM : this.startYear;
  }
  
  public void setStartYear(Object paramObject) {
    if (paramObject instanceof Formula) {
      this.startYearFM = (Formula)paramObject;
      this.startYear = null;
    } else if (paramObject instanceof String) {
      this.startYearFM = null;
      this.startYear = paramObject.toString();
    } else {
      this.startYearFM = null;
      this.startYear = null;
    } 
  }
  
  public Object getEndYear() {
    return (this.endYearFM != null) ? this.endYearFM : this.endYear;
  }
  
  public void setEndYear(Object paramObject) {
    if (paramObject instanceof Formula) {
      this.endYearFM = (Formula)paramObject;
      this.endYear = null;
    } else if (paramObject instanceof String) {
      this.endYearFM = null;
      this.endYear = paramObject.toString();
    } else {
      this.endYearFM = null;
      this.endYear = null;
    } 
  }
  
  public String getStartText() {
    return this.startYear;
  }
  
  public void setStartText(String paramString) {
    this.startYear = paramString;
  }
  
  public String getEndText() {
    return this.endYear;
  }
  
  public void setEndText(String paramString) {
    this.endYear = paramString;
  }
  
  public Formula getStartYearFM() {
    return this.startYearFM;
  }
  
  public void setStartYearFM(Formula paramFormula) {
    this.startYearFM = paramFormula;
  }
  
  public Formula getEndYearFM() {
    return this.endYearFM;
  }
  
  public void setEndYearFM(Formula paramFormula) {
    this.endYearFM = paramFormula;
  }
  
  public Object value2Config(Object paramObject, CalculatorProvider paramCalculatorProvider) {
    return paramObject;
  }
  
  public void createValueResult(DataControl paramDataControl, Calculator paramCalculator, JSONObject paramJSONObject1, JSONObject paramJSONObject2) {
    if (getWidgetValue() != null) {
      Map map = paramJSONObject1.toMap();
      WidgetValue widgetValue = getWidgetValue();
      Object object = null;
      object = widgetValue.executeResult((CalculatorProvider)paramCalculator);
      try {
        paramJSONObject1.put(this.widgetName.toUpperCase(), (object == null) ? "" : object);
      } catch (JSONException jSONException) {
        FRContext.getLogger().error(jSONException.getMessage(), (Throwable)jSONException);
      } 
    } 
  }
  
  public String getFormatText() {
    return null;
  }
  
  public String getDataBindDefaultValue(CalculatorProvider paramCalculatorProvider) {
    return null;
  }
  
  public String getDataBindDefaultValue(Calculator paramCalculator) {
    return null;
  }
  
  @ExecuteFunctionRecord
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    try {
      Object object = jSONObject.get("value");
      if (object == null) {
        jSONObject.remove("value");
      } else if (object instanceof String) {
        jSONObject.put("value2", (new JSONObject()).put("year", object));
        jSONObject.put("year", object);
      } 
    } catch (Exception exception) {}
    if (this.startYear != null) {
      jSONObject.put("startYear", value2Config(this.startYear, (CalculatorProvider)paramCalculator));
    } else if (this.startYearFM != null) {
      try {
        jSONObject.put("startYear", value2Config(Calculator.createCalculator().evalValue(this.startYearFM.getContent()), (CalculatorProvider)paramCalculator));
      } catch (UtilEvalError utilEvalError) {
        FRContext.getLogger().error(utilEvalError.getMessage());
      } 
    } 
    if (this.endYear != null) {
      jSONObject.put("endYear", value2Config(this.endYear, (CalculatorProvider)paramCalculator));
    } else if (this.endYearFM != null) {
      try {
        jSONObject.put("endYear", value2Config(Calculator.createCalculator().evalValue(this.endYearFM.getContent()), (CalculatorProvider)paramCalculator));
      } catch (UtilEvalError utilEvalError) {
        FRContext.getLogger().error(utilEvalError.getMessage());
      } 
    } 
    String[] arrayOfString = dependence((CalculatorProvider)paramCalculator);
    if (!ArrayUtils.isEmpty((Object[])arrayOfString))
      jSONObject.put("dependence", arrayOfString); 
    return jSONObject;
  }
  
  public JSONArray createJSONData(TemplateSessionIDInfo paramTemplateSessionIDInfo, Calculator paramCalculator, HttpServletRequest paramHttpServletRequest) throws Exception {
    JSONArray jSONArray = new JSONArray();
    ParameterMapNameSpace parameterMapNameSpace = ParameterMapNameSpace.create(paramTemplateSessionIDInfo.getParameterMap4Execute());
    FieldEditor.DependenceNameSpace dependenceNameSpace = new FieldEditor.DependenceNameSpace(CodeUtils.cjkDecode(WebUtils.getHTTPRequestParameter(paramHttpServletRequest, "dependence")));
    paramCalculator.pushNameSpace((NameSpace)parameterMapNameSpace);
    paramCalculator.pushNameSpace((NameSpace)dependenceNameSpace);
    JSONObject jSONObject = new JSONObject();
    String str = getWidgetStringValue(paramCalculator);
    jSONObject.put("value2", str);
    jSONObject.put("value2", (new JSONObject()).put("year", str));
    jSONObject.put("startYear", getStartYearValue(paramCalculator));
    jSONObject.put("endYear", getEndYearValue(paramCalculator));
    jSONArray.put(jSONObject);
    paramCalculator.removeNameSpace((NameSpace)parameterMapNameSpace);
    paramCalculator.removeNameSpace((NameSpace)dependenceNameSpace);
    return jSONArray;
  }
  
  private String getWidgetStringValue(Calculator paramCalculator) throws Exception {
    String str = "";
    if (this.widgetValue.getValue() == null) {
      str = "";
    } else if (this.widgetValue.getValue() instanceof Formula) {
      Object object = paramCalculator.eval((FormulaProvider)this.widgetValue.getValue());
      str = valueChangeToString(object);
    } else if (this.widgetValue.getValue() instanceof String) {
      str = (String)this.widgetValue.getValue();
    } 
    return str;
  }
  
  private String valueChangeToString(Object paramObject) {
    String str = "";
    if (paramObject instanceof String) {
      str = (String)paramObject;
    } else {
      try {
        str = String.valueOf(paramObject);
      } catch (Exception exception) {
        exception.printStackTrace();
        str = "";
      } 
    } 
    return str;
  }
  
  public String[] supportedEvents() {
    return new String[] { "afterinit", "afteredit", "stopedit" };
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject instanceof Year && super.equals(paramObject) && ComparatorUtils.equals(this.startYear, ((Year)paramObject).startYear) && ComparatorUtils.equals(this.endYear, ((Year)paramObject).endYear));
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if (str.equals("YearAttr")) {
        String str1 = null;
        if ((str1 = paramXMLableReader.getAttrAsString("start", (String)null)) != null) {
          this.startYear = str1;
        } else if ((str1 = paramXMLableReader.getAttrAsString("startyearfm", (String)null)) != null) {
          this.startYearFM = new Formula(str1);
        } 
        if ((str1 = paramXMLableReader.getAttrAsString("end", (String)null)) != null) {
          this.endYear = str1;
        } else if ((str1 = paramXMLableReader.getAttrAsString("endyearfm", (String)null)) != null) {
          this.endYearFM = new Formula(str1);
        } 
      } else if ("widgetValue".equals(str)) {
        this.widgetValue = new WidgetValue();
        paramXMLableReader.readXMLObject((XMLReadable)this.widgetValue);
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("YearAttr");
    if (this.startYear != null) {
      paramXMLPrintWriter.attr("start", this.startYear);
    } else if (this.startYearFM != null) {
      paramXMLPrintWriter.attr("startyearfm", this.startYearFM.getContent());
    } 
    if (this.endYear != null) {
      paramXMLPrintWriter.attr("end", this.endYear);
    } else if (this.endYearFM != null) {
      paramXMLPrintWriter.attr("endyearfm", this.endYearFM.getContent());
    } 
    paramXMLPrintWriter.end();
    if (this.widgetValue != null)
      this.widgetValue.writeXML(paramXMLPrintWriter); 
  }
}
