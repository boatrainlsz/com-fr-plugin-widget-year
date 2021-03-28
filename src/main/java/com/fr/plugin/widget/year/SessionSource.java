package com.fr.plugin.widget.year;

public enum SessionSource {
  INTEGRATION("integration"),
  SINGLE("single");
  
  private String type;
  
  SessionSource(String paramString1) {
    this.type = paramString1;
  }
  
  public String getType() {
    return this.type;
  }
}
