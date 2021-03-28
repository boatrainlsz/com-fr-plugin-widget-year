FR.Year = FR.extend(FR.BaseYearEditor, {
    _defaultConfig : function() {
        return $.extend(FR.Year.superclass._defaultConfig.apply(), {
            directEdit : true
        });
    },
    _init : function() {
        FR.Year.superclass._init.apply(this, arguments);
        this.switchArrow();
        if (this.options.widgetCss && this.options.widgetCss.length !== 0) {
            $.each(this.options.widgetCss, function(c, d) {
                FR.$import(d, "css", true)
            })
        }
        var b = this.options;
        this.sty= this._createStartYear(b.startYear);
        this.edy = this._createEndYear(b.endYear);
        var a = this;
        if (!this.$view) {
            this.$view = $("<div/>").appendTo(FR.$view_container).hide()
        }
        this.editComp.keydown(function(c) {
            a.editComp[0].realValue = null
        });
        $(this.editComp).keyup(function() {
            if ($(this).val() == a.oriText) {
                return
            }
            a.isValidateInput();
            a.oriText = $(this).val();
            a.fireEvent(FR.Events.AFTEREDIT)
        });
    },
    getArrowIconHeight : function() {
        return 17;
    },
    switchArrow : function() {
        this.arrow.switchClass("fr-trigger-center", "fr-date-trigger-center");
    },
    _applyInvalidCss : function(a) {
        this.errorMsg = a;
        this.invalidateCss();
    },
    onTriggerClick : function(a) {
        if (!this.isEnabled()) {
            return;
        }
        if (document.activeElement != this.editComp[0]) {
            this.editComp.focus();
        }
        if (this.isExpanded()) {
            if (FR.Browser.isIE8() && this.$view.css("visibility") == "hidden") {
                this.$view.css("visibility", "visible");
            } else {
                this.$view.show();
            }
        } else {
            this.$view.empty();
            this._createCalendar();
        }
    },
    _showView : function() {
        if (FR.Browser.isIE8() && this.$view.css("visibility") == "hidden") {
            this.$view.css("visibility", "visible");
        } else {
            this.$view.show();
        }
    },
    _createStartYear : function(d) {
        if (FR.isEmpty(d)) {
            return null
        }
        var a = d;
        return a
    },
    _createEndYear : function(c) {
        if (FR.isEmpty(c)) {
            return null
        }
        var d = c;
        return d;

    },
    modifyPosition : function() {
        this.tH = this.$view.height();
        this.tW = this.$view.width();
        FR.Year.superclass.modifyPosition.apply(this, arguments);
    },
    _createCalendar : function() {
        var e = this.options;
        var b = this;
        if (this.options.need2BuildConfig === true && this.options.data) {
            this.options.data.resetStatus(this.createDependencePara4Data());
            var c = this.options.data.getData();
            if (c[0].data) {
                if (c[0].data.startYear) {
                    this.sty = this._createStartYear(e.startYear);
                }
                if (c[0].data.endYear) {
                    this.edy = this._createEndYear(e.endYear);
                }
            }
            this.options.rebuildConfig = false;
        }
        this.yearpicker = new FR.YearPicker({
            renderEl : this.$view,
            year : b.editComp.val(),
            startYear : this.sty,
            endYear : this.edy,
            onYearUpdate : function() {
                b.editComp.val(this.getValue());
                b.isValidateInput();
                b.fireEvent(FR.Events.AFTEREDIT);
            }
        });
        if (FR.Browser.isIE8() && this.$view.css("visibility") == "hidden") {
            this.$view.css("visibility", "visible");
        } else {
            this.$view.show();
        }
        $(document).bind("mousedown", this, this.collapseIf);
        this.modifyPosition();
    },
    getValue : function() {
        var a = this.editComp.val();
        return  a;
    },
    isValidateInput : function(j) {
        var e = this.sty;
        var f = this.edy;
        var i = this;
        var a = true;
        var c = j ? j : this.editComp.val().year;
        if(c != null){
            if (e && c && c <e ){
                this._applyInvalidCss(FR.i18nText("Err-The_number_is_less_than_the_minimum_value") + e);
            } else {
                if (f && c && c > f) {
                    this._applyInvalidCss(FR.i18nText("Err-The_number_is_larger_than_the_maximum_value") + f);
                } else {
                    if (c > 2999 || c < 1900) {
                        this._applyInvalidCss("out of range");
                    } else {
                        this.validateCss();
                    }
                }
            }
        }
    },
    reset : function() {
        FR.Year.superclass.reset.apply(this, arguments);
        this.editComp[0].realValue = null;
        this.options.currentYear = null;
        this.options.need2BuildConfig = true;
        if (this.options.data) {
            delete this.options.data.records;
        }
    }
});
$.shortcut('year', FR.Year);