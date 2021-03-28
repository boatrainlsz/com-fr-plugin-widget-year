FR.YearPicker = FR.extend(FR.Widget, {
    CONSTS : {
        MINYEAR : 1900,
        MAXYEAR : 2999,
        NAV : {
            title : 4,
            clear : 5,
            thisyear : 6,
            yok : 7,
            prevy : 8,
            nexty : 9,
            cancel : 10,
            current : 15,
            tyear : 300
        }
    },
    _TT : {
        CLEAR : FR.i18nText("Fine-Module_Year_Clear"),
        OK : FR.i18nText("Fine-Module_Year_OK"),
        THISYEAR: FR.i18nText("Fine-Module_Year_ThisYear")
    },
    _defaultConfig : function() {
        return $.extend(FR.YearPicker.superclass._defaultConfig.apply(this, arguments), {
            widgetName : "datepicker",
            endYear : null,
            startYear : null,
            year : null,
            onYearUpdate : null,
            onClear : function() {
                this._hideView();
            },
            onOK : function() {
                this._hideView();
            },
            onClose : function() {
                this._hideView();
            },
            onThisYear : function() {
                this._hideView();
            }
        });
    },
    _hideView : function() {
        if (FR.Browser.isIE8()) {
            this.element.css("visibility", "hidden");
        } else {
            this.element.hide();
        }
    },
    _init : function() {
        FR.YearPicker.superclass._init.apply(this, arguments);
        if(this.options.year==""){
            this.options.year=new Date().getFullYear();
        }
        this._resetRangeYear(this.options.year);
        this.element.addClass("fr-datepicker");
        if(!this.options.year || this.options.year == null){
            this.options.year = new Date().getFullYear();
        }
        this.cache = {
            showYear : null
        };
        this._initTables();
        this._bindEvts();
    },
    _bindEvts : function(){
        this.element.unbind();
        var a = this, g = this.options, d = this.CONSTS.NAV;
        var c = this.$yeartable;
        var b = function(l){
            var m = l.target;
            var k = l.type;
            var j = $(m).data("nav");
            if ($(m).data("disabled") || m.tagName !== "TD" || !j) {
                return;
            }
            if (!a.options.year) {
                a.options.year = new Date().getFullYear();
            }
            if (k === "mouseover") {
                $(m).addClass("hover");
            }else {
                if (k === "mouseup") {
                    switch(j) {
                        case d.prevy:
                            a._toPrevDecade();
                            a._loadYearData(c,a.options.year);
                            FR.applyFunc(a, g.onDateUpdate, arguments);
                            break;
                        case d.nexty:
                            a._toNextDecade();
                            a._loadYearData(c, a.options.year);
                            FR.applyFunc(a, g.onDateUpdate, arguments);
                            break;
                        case d.tyear:
                            a.cache.selectedYear && a.cache.selectedYear.removeClass("selected");
                            a.cache.selectedYear = $(m).addClass("selected");
                            var i = $(m).text();
                            a.setValue(i);
                            FR.applyFunc(a, g.onYearUpdate, arguments);
                            FR.applyFunc(a, g.onClose, arguments);
                            break;
                        case d.thisyear:
                            var h = new Date().getFullYear();

                            if ((a.options.startYear && h < a.options.startYear) || (a.options.endYear && h > a.options.endYear)) {
                                return;
                            } else {
                                a.options.Year = h;
                                a.setValue(h);
                            }
                            FR.applyFunc(a, g.onYearUpdate, arguments);
                            FR.applyFunc(a, g.onThisYear, arguments);
                            break;
                        case d.yok:
                            FR.applyFunc(a, g.onYearUpdate, arguments);
                            FR.applyFunc(a, g.onOK, arguments);
                            break;
                        case d.clear:
                            a.options.year = null;
                            a.cache.selectedYear && a.cache.selectedYear.removeClass("selected");
                            FR.applyFunc(a, g.onYearUpdate, arguments);
                            FR.applyFunc(a, g.onClear, arguments);
                            break;
                        case d.current:
                            a.options.year = new Date().getFullYear();
                            FR.applyFunc(a, g.onYearUpdate, arguments);
                    }
                }else {
                    if (k === "mouseout") {
                        $(m).removeClass("hover");
                    }
                }
            }
        };
        this.element.bind("mousedown", b).bind("mouseover", b).bind("mouseup", b).bind("mouseout", b);
    },
    _toPrevDecade : function() {
        var e = this.rangetMaxYear, a = this.rangetMinYear;
        this.rangetMaxYear = e-20;
        this.rangetMinYear = a-20;
    },
    _toNextDecade : function() {
        var e = this.rangetMaxYear, a = this.rangetMinYear;
        this.rangetMaxYear = e+20;
        this.rangetMinYear = a+20;
    },
    _resetRangeYear : function(y){
        var d = parseInt(y)
        var x = d%20;
        if(x==0){
            this.rangetMinYear = d-20+1;
            this.rangetMaxYear = d;
        }else{
            this.rangetMinYear = d-x+1;
            this.rangetMaxYear = d+(20-x);
        }
    },
    _initTables : function() {
        var a = this.options;
        this.$yeartable = this._createYearPicker();
        this._loadYearData(this.$yeartable, this.options.year);
        this.$yeartable.appendTo(this.element).show();
    },
    _createYearPicker : function() {
        var j = $('<table cellspacing = "0" cellpadding = "0" class="dt"/>');
        var a = this.CONSTS.NAV;
        var f = $("<thead/>").appendTo(j);
        k = $('<tr class = "mainhead"/>');
        j.$prevy = this._createCell(k, "&lsaquo;", 1, a.prevy, "prevy");
        j.$title = $('<td class="title" colspan="2"/>').data("nav", a.title).appendTo(k);
        j.$nexty = this._createCell(k, "&rsaquo;", 1, a.nexty, "nexty");
        k.appendTo(f);
        var d = $('<tbody onselectstart="return false"/>').appendTo(j);
        for ( c = 5; c > 0; c--) {
            var k = $("<tr/>").appendTo(d);
            for (var h = 0; h < 4; h++) {
                $("<td/>").appendTo(k);
            }
        }
        var g = $("<tfoot/>").appendTo(j);
        var k = $('<tr class = "optbtns"/>');
        this._createCell(k, this._TT.CLEAR, 1, a.clear, "clear");
        this._createCell(k, this._TT.THISYEAR, 2, a.thisyear, "thisyear");
        this._createCell(k, this._TT.OK, 1, a.yok, "ok");
        k.appendTo(g);
        return j;
    },

    _loadYearData : function(s, u) {
        if (!u) {
            return;
        }
        this.cache.showYear = u;
        var x = this.options.startYear, r = this.options.endYear;
        s.$title.text(this.rangetMinYear+"--"+this.rangetMaxYear);
        s.$title.removeClass("hover").data("disabled", true);
        var b = this.rangetMaxYear;

        if ((r && b > r) || b > this.CONSTS.MAXYEAR) {
            s.$nexty.addClass("disabled").removeClass("hover").data("disabled", true);
        } else {
            s.$nexty.removeClass("disabled").data("disabled", false);
        }
        var o = this.rangetMinYear;
        if ((x && o < x) || o < this.CONSTS.MINYEAR) {
            s.$prevy.addClass("disabled").removeClass("hover").data("disabled", true);
        } else {
            s.$prevy.removeClass("disabled").data("disabled", false);
        }
        var m = s.find("tbody").children().eq(0);
        var w ;
        for (var q = 0; q < 5; q++) {
            if (!m.length) {
                break;
            }
            var d = m.children().eq(0);
            w = o+q*4;
            var e;
            for (var n = 0; n < 4; ++n) {
                d.removeClass().data("nav", this.CONSTS.NAV.tyear);
                if (!d.length) {
                    break;
                }
                e = w+n;
                d.text(e);
                var c = false;
                if ((x != null && x > e) || (r != null && r < e)) {
                    d.addClass("tyear disabled");
                    c = true;
                } else {
                    d.addClass("tyear");
                }
                d.data("disabled", c);
                if (!c) {
                    if (e == u) {
                        this.cache.selectedYear && this.cache.selectedYear.removeClass("selected");
                        d.addClass("selected");
                        this.cache.selectedYear = d;
                    }
                    if ( e == u) {
                        d.addClass("thisyear");
                    }
                }
                d = d.next();
            }
            m = m.next();
        }
    },
    _createCell : function(c, e, f, d, a) {
        var b = $("<td class/>").attr("colSpan", f).html(e).appendTo(c);
        if (d) {
            b.data("nav", d);
        }
        a = a ? "btn " + a : "btn";
        b.addClass(a);
        return b
    },
    getValue : function() {
        return this.options.year;
    },
    setValue : function(a) {
        debugger
        this.options.year = a;
        // this.setText(a);
    },
    getText : function() {
        return this.getValue();
    },
    setText : function(a) {
        this.setValue(a);
    }
});
$.shortcut("yearpicker", FR.YearPicker);