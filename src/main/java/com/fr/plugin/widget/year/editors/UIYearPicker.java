package com.fr.plugin.widget.year.editors;

import com.fr.base.FRContext;
import com.fr.design.gui.icombobox.UIComboBox;
import com.fr.design.gui.icombobox.UIComboBoxUI;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.design.utils.gui.GUICoreUtils;
import com.fr.general.ComparatorUtils;
import com.fr.general.Inter;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class UIYearPicker extends UIComboBox implements Serializable {
  public boolean isWillHide = false;
  
  private DefaultComboBoxModel model = new DefaultComboBoxModel();
  
  public UIYearPicker() throws UnsupportedOperationException {
    this(Integer.valueOf(Calendar.getInstance().get(1)));
  }
  
  public UIYearPicker(Integer paramInteger) {
    setEditable(true);
    setCursor(new Cursor(0));
    setModel(this.model);
    setSelectedItem(Integer.valueOf((paramInteger == null) ? Calendar.getInstance().get(1) : paramInteger.intValue()));
    updateUI();
  }
  
  public Integer getSelectedDate() {
    synchronized (this) {
      if (getSelectedItem() == null)
        return Integer.valueOf(Calendar.getInstance().get(1)); 
      return Integer.valueOf(getSelectedItem().toString());
    } 
  }
  
  public void setSelectedDate(Integer paramInteger) {
    setSelectedItem(paramInteger);
  }
  
  public void setSelectedItem(Object paramObject) {
    this.model.setSelectedItem(paramObject);
    super.setSelectedItem(paramObject);
  }
  
  protected ComboBoxUI getUIComboBoxUI() {
    return (ComboBoxUI)new UIComboBoxUI() {
        protected ComboPopup createPopup() {
          return new UIYearPicker.YearPopup(this.comboBox);
        }
        
        public void mousePressed(MouseEvent param1MouseEvent) {
          if (UIYearPicker.this.isPopupVisible()) {
            UIYearPicker.this.isWillHide = true;
            UIYearPicker.this.hidePopup();
          } else {
            UIYearPicker.this.isWillHide = false;
            UIYearPicker.this.showPopup();
          } 
        }
      };
  }
  
  public static void main(String[] paramArrayOfString) {
    LayoutManager layoutManager = null;
    JFrame jFrame = new JFrame(Inter.getLocText("Fine-Module_Year_Name"));
    jFrame.setDefaultCloseOperation(3);
    JPanel jPanel = (JPanel)jFrame.getContentPane();
    jPanel.setLayout(layoutManager);
    UIYearPicker uIYearPicker = new UIYearPicker();
    uIYearPicker.setEditable(false);
    if (paramArrayOfString.length != 0)
      uIYearPicker = new UIYearPicker(); 
    uIYearPicker.setBounds(20, 20, (uIYearPicker.getPreferredSize()).width, (uIYearPicker.getPreferredSize()).height);
    jPanel.add((Component)uIYearPicker);
    GUICoreUtils.centerWindow(jFrame);
    jFrame.setSize(400, 400);
    jFrame.setVisible(true);
  }
  
  class YearPopup extends BasicComboPopup implements ChangeListener {
    UIYearPanel yearPanel = null;
    
    public YearPopup(JComboBox param1JComboBox) {
      super(param1JComboBox);
      setLayout(FRGUIPaneFactory.createBorderLayout());
      this.yearPanel = new UIYearPanel();
      this.yearPanel.addDateChangeListener(this);
      add(this.yearPanel, "Center");
      setBorder(BorderFactory.createEmptyBorder());
    }
    
    public void hide() {
      if (UIYearPicker.this.isWillHide)
        super.hide(); 
    }
    
    public void show() {
      if (UIYearPicker.this.isWillHide || !UIYearPicker.this.isEnabled())
        return; 
      if (this.yearPanel != null);
      super.show();
    }
    
    protected void firePropertyChange(String param1String, Object param1Object1, Object param1Object2) {
      if (ComparatorUtils.equals(param1String, "visible"))
        if (ComparatorUtils.equals(param1Object1, Boolean.FALSE) && ComparatorUtils.equals(param1Object2, Boolean.TRUE)) {
          try {
            String str = this.comboBox.getSelectedItem().toString();
            synchronized (this) {
              this.yearPanel.setSelectedYear(Integer.valueOf(str));
            } 
          } catch (Exception exception) {
            FRContext.getLogger().error(exception.getMessage(), exception);
          } 
        } else if (!ComparatorUtils.equals(param1Object1, Boolean.TRUE) || ComparatorUtils.equals(param1Object2, Boolean.FALSE)) {
        
        }  
      super.firePropertyChange(param1String, param1Object1, param1Object2);
    }
    
    public void stateChanged(ChangeEvent param1ChangeEvent) {
      if (this.yearPanel.getSelectedYear() != null) {
        Integer integer = this.yearPanel.getSelectedYear();
        if (this.comboBox.isEditable() && this.comboBox.getEditor() != null)
          this.comboBox.configureEditor(this.comboBox.getEditor(), integer); 
        this.comboBox.setSelectedItem(integer);
      } 
      this.comboBox.repaint();
      setVisible(false);
    }
  }
}
