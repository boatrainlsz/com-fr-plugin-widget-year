package com.fr.plugin.widget.year.editors;

import com.fr.base.BaseUtils;
import com.fr.design.gui.date.UIDayLabel;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.general.Inter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class UIYearPanel extends JPanel {
  private static final Font FONT_SONG = new Font(Inter.getLocText("Song_TypeFace"), 0, 12);
  
  private boolean isSupportDateChangeListener = false;
  
  private Integer selectedYear = null;
  
  private UILabel yearRangLabel = null;
  
  private JLabel selectedLabel = null;
  
  private Calendar calendar = null;
  
  private Integer minYear;
  
  private Integer maxYear;
  
  private JPanel yearPanel;
  
  protected EventListenerList listenerList = new EventListenerList();
  
  public UIYearPanel() {
    this(Integer.valueOf(Calendar.getInstance().get(1)));
  }
  
  public UIYearPanel(Integer paramInteger) {
    this.selectedYear = paramInteger;
    resetMinMaxYear(this.selectedYear);
    this.calendar = Calendar.getInstance();
    setPreferredSize(new Dimension(218, 179));
    setBackground(new Color(16777215));
    setBorder(BorderFactory.createLineBorder(new Color(9803157)));
    setLayout(FRGUIPaneFactory.createBorderLayout());
    add("North", createNorthPane());
    add("Center", createCenterPane());
    add("South", createSouthPane());
    updateYears();
  }
  
  private void resetMinMaxYear(Integer paramInteger) {
    int i = paramInteger.intValue() % 30;
    if (i == 0) {
      this.minYear = Integer.valueOf(2010);
      this.maxYear = Integer.valueOf(2030);
    } else {
      this.minYear = Integer.valueOf(paramInteger.intValue() - i + 1);
      this.maxYear = Integer.valueOf(paramInteger.intValue() + 20 - i);
    } 
  }
  
  private void updateYears() {
    this.yearPanel.removeAll();
    for (int i = this.minYear.intValue(); i <= this.maxYear.intValue(); i++) {
      JLabel jLabel = new JLabel(String.valueOf(i), 0);
      jLabel.setBorder(BorderFactory.createLineBorder(new Color(14342874)));
      jLabel.setForeground(Color.BLUE);
      jLabel.setBackground(new Color(16777215));
      jLabel.setOpaque(true);
      if (this.selectedYear.intValue() == i) {
        jLabel.setBackground(new Color(4301806));
        this.selectedLabel = jLabel;
      } 
      jLabel.addMouseListener(createSkipListener(jLabel));
      this.yearPanel.add(jLabel);
      requestFocus();
    } 
    this.yearRangLabel.setText(this.minYear + "-" + this.maxYear);
    this.yearPanel.validate();
  }
  
  private JPanel createCenterPane() {
    createYearPanel();
    JPanel jPanel = FRGUIPaneFactory.createBorderLayout_S_Pane();
    jPanel.setOpaque(true);
    jPanel.add(this.yearPanel);
    jPanel.setVisible(true);
    return jPanel;
  }
  
  private void createYearPanel() {
    this.yearPanel = new JPanel();
    this.yearPanel.setBorder(new EmptyBorder(0, -1, -1, -1));
    this.yearPanel.setLayout(new GridLayout(5, 4, -1, -1));
  }
  
  private JPanel createNorthPane() {
    JPanel jPanel = FRGUIPaneFactory.createX_AXISBoxInnerContainer_S_Pane();
    jPanel.setBackground(new Color(16777215));
    jPanel.setPreferredSize(new Dimension(1, 22));
    jPanel.add(Box.createHorizontalStrut(5));
    jPanel.add((Component)createSkipButton(1, -1, new Icon[] { BaseUtils.readIcon("/com/fr/design/images/calender/year_reduce.png"), BaseUtils.readIcon("/com/fr/design/images/calender/year_reduce_hover.png"), BaseUtils.readIcon("/com/fr/design/images/calender/year_reduce_click.png") }));
    this.yearRangLabel = new UILabel("", 0);
    this.yearRangLabel.setBackground(new Color(16777215));
    this.yearRangLabel.setForeground(new Color(0));
    this.yearRangLabel.setFont(FONT_SONG);
    jPanel.add(Box.createHorizontalGlue());
    jPanel.add((Component)this.yearRangLabel);
    jPanel.add(Box.createHorizontalGlue());
    jPanel.add((Component)createSkipButton(1, 1, new Icon[] { BaseUtils.readIcon("/com/fr/design/images/calender/year_add.png"), BaseUtils.readIcon("/com/fr/design/images/calender/year_add_hover.png"), BaseUtils.readIcon("/com/fr/design/images/calender/year_add_click.png") }));
    jPanel.add(Box.createHorizontalStrut(5));
    return jPanel;
  }
  
  private JPanel createSouthPane() {
    JPanel jPanel = FRGUIPaneFactory.createX_AXISBoxInnerContainer_S_Pane();
    jPanel.setPreferredSize(new Dimension(216, 18));
    jPanel.setLayout(new BorderLayout());
    UIDayLabel uIDayLabel = new UIDayLabel(new Date(), false);
    uIDayLabel.setForeground(new Color(0));
    uIDayLabel.addMouseListener(createTodayListener(jPanel, uIDayLabel));
    uIDayLabel.setBackground(new Color(15790320));
    jPanel.add((Component)uIDayLabel, "Center");
    return jPanel;
  }
  
  protected MouseListener createTodayListener(final JPanel jp, final UIDayLabel label) {
    return new MouseAdapter() {
        public void mousePressed(MouseEvent param1MouseEvent) {
          label.setForeground(new Color(16777215));
        }
        
        public void mouseExited(MouseEvent param1MouseEvent) {
          jp.setBackground(new Color(15790320));
        }
        
        public void mouseEntered(MouseEvent param1MouseEvent) {
          jp.setBackground(new Color(13164014));
        }
        
        public void mouseReleased(MouseEvent param1MouseEvent) {
          jp.setBackground(new Color(15790320));
          label.setForeground(new Color(0));
          jp.setBorder((Border)null);
          UIYearPanel.this.isSupportDateChangeListener = true;
          UIYearPanel.this.resetMinMaxYear(Integer.valueOf(UIYearPanel.this.calendar.get(1)));
          UIYearPanel.this.setSelectedYear(Integer.valueOf(UIYearPanel.this.calendar.get(1)));
          UIYearPanel.this.isSupportDateChangeListener = false;
        }
      };
  }
  
  protected UILabel createSkipButton(int paramInt1, int paramInt2, Icon[] paramArrayOfIcon) {
    if (paramArrayOfIcon.length != 3)
      return new UILabel(); 
    UILabel uILabel = new UILabel();
    uILabel.setIcon(paramArrayOfIcon[0]);
    uILabel.setRequestFocusEnabled(false);
    uILabel.addMouseListener(createSkipListener(uILabel, paramInt1, paramInt2, paramArrayOfIcon));
    return uILabel;
  }
  
  protected MouseListener createSkipListener(final UILabel label, int paramInt1, final int amount, final Icon[] icons) {
    return new MouseAdapter() {
        public void mouseReleased(MouseEvent param1MouseEvent) {
          label.setIcon(icons[1]);
          if (amount == -1) {
            UIYearPanel.this.maxYear = Integer.valueOf(UIYearPanel.this.maxYear.intValue() - 20);
            UIYearPanel.this.minYear = Integer.valueOf(UIYearPanel.this.minYear.intValue() - 20);
          } else {
            UIYearPanel.this.maxYear = Integer.valueOf(UIYearPanel.this.maxYear.intValue() + 20);
            UIYearPanel.this.minYear = Integer.valueOf(UIYearPanel.this.minYear.intValue() + 20);
          } 
          UIYearPanel.this.updateYears();
        }
        
        public void mouseEntered(MouseEvent param1MouseEvent) {
          label.setIcon(icons[1]);
        }
        
        public void mouseExited(MouseEvent param1MouseEvent) {
          label.setIcon(icons[0]);
        }
        
        public void mousePressed(MouseEvent param1MouseEvent) {
          label.setIcon(icons[2]);
        }
      };
  }
  
  protected MouseListener createSkipListener(final JLabel label) {
    return new MouseAdapter() {
        public void mouseReleased(MouseEvent param1MouseEvent) {
          UIYearPanel.this.selectedLabel.setBackground(Color.WHITE);
          UIYearPanel.this.isSupportDateChangeListener = true;
          UIYearPanel.this.setSelectedYear(Integer.valueOf(label.getText()));
          UIYearPanel.this.isSupportDateChangeListener = false;
        }
        
        public void mouseEntered(MouseEvent param1MouseEvent) {
          label.setBackground(new Color(12968697));
          checkCurrentYear();
        }
        
        public void mouseExited(MouseEvent param1MouseEvent) {
          label.setBackground(Color.WHITE);
          checkCurrentYear();
        }
        
        public void mousePressed(MouseEvent param1MouseEvent) {
          label.setBackground(new Color(4301806));
        }
        
        private void checkCurrentYear() {
          if (Integer.valueOf(label.getText()).equals(UIYearPanel.this.selectedYear))
            label.setBackground(new Color(4301806)); 
        }
      };
  }
  
  public Integer getSelectedYear() {
    return this.selectedYear;
  }
  
  public void setSelectedYear(Integer paramInteger) {
    this.selectedYear = paramInteger;
    resetMinMaxYear(paramInteger);
    updateYears();
    if (this.isSupportDateChangeListener)
      fireDateChanged(new ChangeEvent(paramInteger)); 
  }
  
  public void addDateChangeListener(ChangeListener paramChangeListener) {
    this.listenerList.add(ChangeListener.class, paramChangeListener);
  }
  
  protected void fireDateChanged(ChangeEvent paramChangeEvent) {
    Object[] arrayOfObject = this.listenerList.getListenerList();
    for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
      if (arrayOfObject[i] == ChangeListener.class)
        ((ChangeListener)arrayOfObject[i + 1]).stateChanged(paramChangeEvent); 
    } 
  }
}
