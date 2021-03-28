FR.BaseYearEditor = FR.extend(FR.TriggerEditor, {
	_defaultConfig : function() {
		return $.extend(FR.BaseYearEditor.superclass._defaultConfig.apply(), {});
	},
	_init : function() {
		FR.BaseYearEditor.superclass._init.apply(this, arguments);
		if (this.options.value2) {
			this._dealValueWithEvents(this.options.value2);
		}
		/*if (this.options.year) {
			this._dealValueWithEvents(this.options.year);
		}*/
	},
	_dealValueWithEvents : function(f){
		if (FR.isEmpty(f)) {
			this.reset();
			return;
		}
		var h = f || "";
		if (h.year) {
			h = h.year;
		}

		if (h == "Invalid Date" || h == "NaN" || h === false || h.year=== "") {
			h="";
		}
		this.setText(h);
		this.options.currentYear = h;
		var d = this.options.value2;
		/*var d = this.options.year;*/
		this.options.value2 = f;
		if (arguments[1] !== false) {
			this.fireEvent(FR.Events.CHANGE, f, d);
		}
	},
	setText : function(b) {
		this.editComp.val(b);
	},
	setStartOrEndYear : function(c, b) {
		if (!b) {
			return;
		}
		if (c == "startYear") {
			this.options.startYear = b;
			this.sty = b;
		} else {
			if (c == "endYear") {
				this.options.startYear = b;
				this.edy = b;
			}
		}
	},
	setStartYear : function(b) {
		this.setStartOrEndYear("startYear", b);
	},
	setEndYear : function(b) {
		this.setStartOrEndYear("endYear", b);
	},
	/*setText : function(b) {
		this.editComp.val(b);
	},*/
	recoveryValue : function() {
		this.editComp.val(this.options.oldValue);
	},
	isValidate : function(d) {
		if(d){
			 return (/^[1-9][0-9]*$/.test(d)) && d>0;
		}
		var c = this.options.allowBlank !== false;
		var b = this.sty;
		var h = this.edy;
		var g = this.editComp.val();
		if ((!g) || g.length == 0) {
			if (c) {
				return true;
			} else {
				this.errorMsg = this.options.errorMsg || FR.i18nText("FR-Engine-Not_NULL");
				return false;
			}
		}
		if((/^[1-9][0-9]*$/.test(g)) && g>0 ){

		}else{
            this.errorMsg = FR.i18nText("Fine-Module_Year_Value_Not_Match");
			return false;
		}
		if(g != null){
			if (b && g < b) {
				this.errorMsg = this.options.errorMsg || FR.i18nText("FR-Engine-Err-The_Number_Is_Less_Than_The_Minimum_Value") + b;
				return false;
			}
			if (h && g > h) {
				this.errorMsg = this.options.errorMsg || FR.i18nText("FR-Engine-Err-The_Number_Is_Larger_Than_The_Maximum_Value") + h;
				return false;
			}
			if (g > 2999 || g < 1900) {
				this.errorMsg = "out of range";
				return false;
			}
		}
		return true;
	},
	reset : function() {
		this.setText("");
	}
});
$.shortcut('baseyeareditor', FR.BaseYearEditor);