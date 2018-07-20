define("ydcore:widget/book_view/txt_reader/txt_reader.js",
function(e, t, a) {
    var r, o = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcommon:widget/ui/js_core/login/login.js").login,
    n = e("ydcommon:widget/ui/js_core/log/log.js"),
    s = e("ydcore:widget/ui/book_view/toolsArea/toolsArea.js").toolsArea,
    d = e("ydcore:widget/ui/book_view/modelList/modelList.js"),
    c = e("ydcore:widget/book_view/txt_reader/js/system/mediator.sys.js").mediator,
    p = e("ydcore:widget/book_view/txt_reader/js/system/config.sys.js").Config,
    l = e("ydcore:widget/book_view/txt_reader/js/system/state.sys.js").State,
    m = e("ydcore:widget/book_view/txt_reader/js/system/util.sys.js"),
    u = e("ydcore:widget/book_view/txt_reader/js/system/note.util.sys.js").Util,
    f = e("ydcore:widget/book_view/txt_reader/js/p/presenter.js").PresenterList,
    b = {};
    b.cookie = e("ydcommon:widget/ui/lib/cookie/cookie.js"),
    b.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    b.browser = e("ydcommon:widget/ui/lib/browser/browser.js"),
    b.Date = e("ydcommon:widget/ui/lib/date/date.js"),
    e("ydcore:widget/book_view/txt_reader/js/system/jquery.plugins.js"),
    r = b.lang.createClass(function(e) {
        this._init(e)
    }).extend({
        _init: function(e) {
            var t = this,
            a = t._initToolbarOptions(e);
            new s(a),
            new d(a),
            a = t._initReaderOptions(a),
            t._fixPayBlock(a),
            o.extend(t, a),
            t._bindToolsBarEvent(),
            t._bindLog(),
            t.reader = new f(a),
            c.fire("p:switch-type", {
                data: {
                    type: t.isOnlyFfkan ? "ffkan": t.initFormatType
                }
            }),
            t.ffkanData.isCan && c.fire("v:show-once-switch-tips")
        },
        _initToolbarOptions: function(e) {
            var t = this,
            a = o.extend({},
            e, {
                $doc: o(document),
                $win: o(window),
                $body: o(document.body),
                $htmlBody: o("html,body"),
                isIE6: !(!b.browser.ie || 6 !== b.browser.ie),
                isIE67: !!(b.browser.ie && b.browser.ie < 8),
                isIE678: !!(b.browser.ie && b.browser.ie < 9),
                isTxtReader: !0,
                mediator: c,
                Util: m,
                noteUtil: u,
                defaultWidth: o("#txt_reader").width()
            });
            a.ispaied || a.is_all_free ? (a.freeChapterStr = "", a.ffkanData.isCan = !1, a.payFlag = a.ispaied ? 1 : 2) : a.payFlag = 0,
            a.ffkanData.blockCount < 1 && (a.ffkanData.isCan = !1),
            a.isOnlyFfkan = a.ffkanData.isCan && 1 === +a.urlQueryData.ffkan,
            a.currentChapter = parseInt(a.toPage, 10) || 1,
            a.freeChapterNum = parseInt(a.freeChapterNum) || 0,
            a.totalChapterNum = parseInt(a.totalChapterNum) || 0,
            a.freeChapterNum < 1 && (a.freeChapterNum = Math.min(2, Math.max(1, a.totalChapterNum))),
            a.totalChapterNum < 1 && (a.isPreview || n.send("book", "jsondataerror", {
                docid: a.doc_id,
                cn: 0,
                pn: 0,
                level: 0,
                type: "totalChapterNumIs0"
            }), a.totalChapterNum = 1),
            a.totalChapterNum < a.freeChapterNum && (a.freeChapterNum = a.totalChapterNum),
            a.allPCount = 0;
            try {
                a.allParagraphs = o.parseJSON(a.allParagraphs || "[]")
            } catch(r) {
                a.allParagraphs = []
            }
            o.each(a.allParagraphs,
            function(e, t) {
                a.allParagraphs[e] = parseInt(t, 10) || 0,
                a.allPCount += a.allParagraphs[e]
            }),
            a.oriCurChapterNum > a.currentChapter && (a.paragraphNum = a.allParagraphs[a.currentChapter - 1]);
            var i = [];
            try {
                i = "array" === o.type(a.catalogs) ? a.catalogs: o.parseJSON(a.catalogs)
            } catch(r) {
                i = []
            }
            a.catalogs = i;
            var s = [];
            try {
                s = "array" === o.type(a.bookmarks) ? a.bookmarks: o.parseJSON(a.bookmarks)
            } catch(r) {
                s = []
            }
            o.each(s,
            function(e, t) {
                s[e].create_time = b.Date.format(new Date(1e3 * Number(s[e].create_time)), "yyyy-MM-dd HH:mm:ss"),
                s[e].update_time = b.Date.format(new Date(1e3 * Number(s[e].update_time)), "yyyy-MM-dd HH:mm:ss"),
                s[e].cn = Number(s[e].cn) + 1,
                s[e].pn = Number(s[e].pn) + 1,
                s[e].offset = Number(s[e].offset),
                1 == t.cn && (s[e].pn += 2)
            }),
            a.bookmarks = s;
            var d = [];
            try {
                d = "array" === o.type(a.booknotes) ? a.booknotes: o.parseJSON(a.booknotes)
            } catch(r) {
                d = []
            }
            return o.each(d,
            function(e, t) {
                d[e].create_time = b.Date.format(new Date(1e3 * Number(d[e].create_time)), "yyyy-MM-dd HH:mm:ss"),
                d[e].update_time = b.Date.format(new Date(1e3 * Number(d[e].update_time)), "yyyy-MM-dd HH:mm:ss"),
                d[e].bfi = Number(d[e].bfi) + 1,
                d[e].bpi = Number(d[e].bpi) + 1,
                d[e].bci = Number(d[e].bci),
                d[e].efi = Number(d[e].efi) + 1,
                d[e].epi = Number(d[e].epi) + 1,
                d[e].eci = Number(d[e].eci),
                1 == t.bfi && (d[e].bpi += 2),
                1 == t.efi && (d[e].epi += 2),
                d[e].style = o.parseJSON(d[e].style || "{}"),
                d[e].key = [d[e].bfi, d[e].bpi, d[e].bci, d[e].efi, d[e].epi, d[e].eci].join("-")
            }),
            d = t._quickSort(d, t._compareStartPoint),
            a.booknotes = d,
            a.realChapterNameMap = [],
            o.each(a.catalogs,
            function(e, t) {
                if (1 == t.level) {
                    var r = t.href.split("-"),
                    o = parseInt(r[0]),
                    i = parseInt(r[1]);
                    a.realChapterNameMap.push({
                        cn: o,
                        pn: i,
                        title: t.title
                    })
                }
            }),
            t._isQADoc(a),
            a
        },
        _isQADoc: function(e) {
            var t = {
                "9947cdc0dd3383c4bb4cd296": !0,
                "190c5f95770bf78a65295493": !0,
                "9994916b90c69ec3d5bb7592": !0,
                edec99add5bbfd0a7956739d: !0,
                "38c76abf33d4b14e852468b8": !0,
                d4cb3a79a216147916112865: !0
            };
            e.isQADoc = t[e.doc_id] === !0,
            e.isQADoc && (e.coverPageInfo.hasCoverPage = !1, e.hideRightToolsbar = !0, e.isForbidNote = !0)
        },
        _quickSort: function(e, t) {
            function a(t, a) {
                var r = e[t];
                e[t] = e[a],
                e[a] = r
            }
            function r(o, i) {
                if (i > o) {
                    var n = e[o + i >> 1],
                    s = o,
                    d = i;
                    do {
                        for (; t(e[s], n) < 0;) s++;
                        for (; t(n, e[d]) < 0;) d--;
                        d >= s && a(s++, d--)
                    } while ( d >= s );
                    r(o, d),
                    r(s, i)
                }
            }
            return r(0, e.length - 1),
            e
        },
        _compareStartPoint: function(e, t) {
            return e.bfi < t.bfi ? -1 : e.bfi == t.bfi ? e.bpi < t.bpi ? -1 : e.bpi == t.bpi ? e.bci < t.bci ? -1 : e.bci == t.bci ? 0 : 1 : 1 : 1
        },
        _bindToolsBarEvent: function() {
            var e = this,
            t = e.mediator;
            t.on("p:addFavo",
            function(e) {
                return i.verlogin(function() {
                    t.fire("m:addFavo").then(function() {
                        e.promise.resolve()
                    })
                }),
                e.promise
            });
            var a = o("#reader-pay-" + e.uid);
            a.length && a.click(function(e) {
                e.preventDefault(),
                o("body").trigger("pay.start", {
                    pos: "buy.book.bottom.view"
                })
            })
        },
        _bindLog: function() {
            var e = this,
            t = e.mediator,
            a = function() {
                for (var t = [], a = Math.random().toString(36).substr(2), r = 20; a.length < r;) a += Math.random().toString(36).substr(2);
                return t.push(a.substr(0, r)),
                t.push((new Date).getTime()),
                t.push(e.doc_id),
                t = t.join("").split(""),
                t = o.map(t,
                function(e) {
                    var t = Math.round(100 * Math.random()),
                    a = t > 50,
                    r = a ? "toUpperCase": "toLowerCase";
                    return e[r]()
                }),
                t.join("")
            } ();
            t.on("p:send-log",
            function(t) {
                if (!e.isPreview) {
                    var a = t.data || {};
                    a && a.logAct && (a = o.extend(a, {
                        docId: e.doc_id,
                        version: "bdjson"
                    }), n.send("book", "bdjsonview", a))
                }
            }),
            t.on("p:send-x-log",
            function(t) {
                if (!e.isPreview) {
                    var r = t.data || {};
                    if (r && r.act_id) {
                        var i = r.act_id;
                        delete r.act_id,
                        r = o.extend(r, {
                            playid: a,
                            docid: e.doc_id
                        });
                        var s = +r.cn,
                        d = +r.pn;
                        1 === s && (d -= 2, d = Math.max(d, 1), r.pn = d);
                        try {
                            n.xsend(2, i, r)
                        } catch(c) {}
                    }
                }
            }),
            t.on("p:tools-area-send-x-log",
            function(e) {
                t.fire("p:send-normal-x-log", {
                    data: e.data
                })
            }),
            t.on("p:send-normal-x-log",
            function(a) {
                if (!e.isPreview) {
                    var r = a.data || {};
                    r = o.extend(r, {
                        cn: e.State.curChapterNum,
                        pn: e.State.curParagraphIndex,
                        format: e.State.currentType
                    }),
                    t.fire("p:send-x-log", {
                        data: r
                    })
                }
            }),
            t.on("p:send-ffkan-log",
            function(a) {
                if (!e.isPreview) {
                    var r, i, n, s = e.State.curFfkanNum,
                    d = e.State.curFfkanPn,
                    c = e.State.curFfkanRange;
                    if (c.beginCn > 0 && c.endCn >= c.beginCn) {
                        for (r = c.beginCn, i = c.beginPn + d - 1, n = e.allParagraphs[r - 1] - i + 1; d > n;) i = d - n,
                        n += e.allParagraphs[r],
                        r++;
                        s = r,
                        d = i
                    }
                    1 === s && (d += 2);
                    var p = a.data || {};
                    p = o.extend(p, {
                        cn: s,
                        pn: d,
                        format: e.State.currentType
                    }),
                    t.fire("p:send-x-log", {
                        data: p
                    })
                }
            })
        },
        _fixPayBlock: function(e) {
            var t = !1;
            if (e.freeChapterNum === e.totalChapterNum && (t = !0, e.freeChapterStr)) {
                var a = e.freeChapterStr.split("-"),
                r = parseInt(a[0]) || 0,
                i = parseInt(a[1]) || 0;
                r >= e.freeChapterNum && i > 0 && i < e.allParagraphs[e.freeChapterNum - 1] && (t = !1)
            }
            t ? o("#pay-block-mod").remove() : o("#pay-block-mod").size() > 0 && o("#book-end").remove()
        },
        _initReaderOptions: function(e) {
            e.bdjsonUrl = e.bdjsonUrl.split("?").slice(0, 2).join("?"),
            e.bdjsonUrl += 1 === e.bdjsonUrl.split("?").length ? "?": "&",
            !e.isPreview && 1 === e.currentChapter && e.paragraphNum > 1 && (e.paragraphNum += 2);
            var t = e.isLocalPreview ? "format": b.cookie.get("YD_view_type"),
            a = t;
            "format" !== t && "stream" !== t && (a = e.isIE67 ? "stream": "format", t = a),
            e.isQADoc && (a = t = "stream");
            var r = new l(e);
            window.readerState = r;
            var i = o.extend({},
            e, {
                $toolsBar: o("#tools-bar"),
                initFormatType: t,
                defaultFormatType: a,
                Config: p,
                State: r
            });
            return i
        }
    }),
    a.exports = r
});;
define("ydcore:widget/book_view/txt_reader/js/chapter/config.js",
function(e, t, i) {
    var a = {
        retryCount: 3,
        maxWaitingTime: 3e3,
        containerId: "pre-render-container",
        containerWidth: 652,
        fontWrapId: "txt-reader-wrap",
        readerForMac: "reader-for-mac",
        readerForOther: "reader-for-other",
        readerForWin7: "reader-for-win7",
        loadingClass: "reader-loading-layer",
        errorClass: "reader-error-layer",
        dealPCount: 20,
        dealPTimespan: 0,
        dealOneLine: !1,
        defaultImgSize: {
            width: 500,
            height: 370
        },
        punctuation: {
            className: "s-p",
            singleByte: {
                value: ",\\.\\?\"'!;:\\)\\]\\}\\+=_#%&~\\*\\|-",
                className: "s-p-1",
                extRightDiff: 4,
                shrinkLeftClassName: "sh-l-1",
                shrinkRightClassName: "sh-r-1"
            },
            doubleByte: {
                value: "\uff0c\u3002\u3001\uff1f\u201c\u201d\u2018\u2019\uff01\uff1b\uff1a\uff09\u3011\uff5d\u3009\u300b\u2014\u2026\uffe5",
                className: "s-p-2",
                extRightDiff: 8,
                lineEndCharDiffs: [17, 21],
                lineEndCharDiffsSmall: [13, 17],
                ieDiffEncrement: 20,
                shrinkLeftClassName: "sh-l-2",
                shrinkRightClassName: "sh-r-2",
                firstQuoteClassName: "f-q-2"
            }
        },
        verticalTypeset: {
            regex: /[a-zA-Z0-9&\/_\(\)\[\]%~|-]/
        },
        pClass: {
            paragraphBlock: "p-b",
            img: "reader-image",
            extEmptyCss: "ext_empty_css",
            extDisplayNone: "ext_display_none",
            extLeft: "ext_text-align_left",
            extRight: "ext_text-align_right",
            extCenter: "ext_text-align_center",
            extExample: "ext_quote",
            extIndent: "ext_text-indent",
            extNoIndent: "ext_no_text-indent",
            extLegends: "ext_legends",
            extInscribed: "ext_inscribed",
            extPicnoteWrap: "ext_picnote_wrap",
            extPicnote: "ext_picnote",
            extPicnoteTop: "ext_picnote_top",
            extPicnoteBottom: "ext_picnote_bottom",
            extPicnoteLeft: "ext_picnote_left",
            extPicnoteRight: "ext_picnote_right",
            extVertical: "ext_vertical",
            extTop: "ext_text-align_top",
            extBottom: "ext_text-align_bottom",
            verticalChar: "v-s",
            verticalSingleChar: "v-s-s",
            verticalSingleCharIE: "v-s-s-ie8",
            extRemark: "ext_remark",
            oneLine: "one-line",
            nextIsOneLine: "n-i-ol",
            prevIsOneLine: "p-i-ol"
        }
    };
    a.tempLegendClass = {
        ext_color_ff0000: a.pClass.extPicnoteTop,
        ext_color_006600: a.pClass.extPicnoteBottom,
        ext_color_99ffcc: a.pClass.extPicnoteLeft,
        ext_color_ff6600: a.pClass.extPicnoteRight,
        ext_italic: a.pClass.extEmptyCss,
        ext_underline: a.pClass.extVertical,
        "ext_font-size_1": a.pClass.extTop,
        "ext_font-size_2": a.pClass.extBottom
    },
    i.exports.config = a
});;
define("ydcore:widget/book_view/txt_reader/js/chapter/bdjson.js",
function(e, t, a) {
    var o, r = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = {},
    n = e("ydcommon:widget/ui/js_core/log/log.js"),
    s = e("ydcore:widget/book_view/txt_reader/js/chapter/config.js").config;
    i.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    o = i.lang.createClass(function(e) {
        this._init(e)
    }).extend({
        _init: function(e) {
            var t = this;
            r.extend(t, e, {
                callbackNamePrefix: "wenku",
                reloadMethod: "getJsonData",
                retryCount: 0,
                gotData: !1,
                loadErrorStartTime: null,
                loadStartTime: null
            }),
            t.isFfkan ? (t.reloadMethod = "getFfkanData", t.getFfkanData()) : t.getJsonData()
        },
        getCallbackName: function(e) {
            var t = this;
            return e = e || "",
            e + t.callbackNamePrefix + t.chapterNum
        },
        getFfkanData: function() {
            var e = this,
            t = ["/browse/main/easyscancontent?doc_id=", e.doc_id, "&char_num=1000&scan_times=", e.chapterNum].join("");
            e.loadStartTime = new Date,
            e.loadErrorStartTime = e.loadErrorStartTime || e.loadStartTime,
            r.ajax({
                dataType: "json",
                url: t,
                timeout: s.maxWaitingTime
            }).done(r.proxy(e.jsonpSuccess, e)).fail(r.proxy(e.jsonpFail, e))
        },
        getLocalPreviewData: function() {
            var e = this,
            t = (e.chapterNum - 1) * e.perBlockCount;
            Request.editor.getParagraph({
                book_id: e.doc_id,
                start: t,
                count: e.perBlockCount,
                callback: function(t) {
                    var a = {
                        t: "div",
                        style: [],
                        blockNum: e.chapterNum,
                        c: t
                    };
                    e.jsonpSuccess(a)
                }
            })
        },
        getJsonData: function() {
            var e = this,
            t = e.bdjsonUrl + ["type=json", "cn=" + e.chapterNum, "_=" + (e.ispaied ? 1 : 0), "t=" + e._getCacheTime()].join("&");
            e.loadStartTime = new Date,
            e.loadErrorStartTime = e.loadErrorStartTime || e.loadStartTime,
            r.ajax({
                dataType: "jsonp",
                url: t,
                cache: !0,
                timeout: s.maxWaitingTime,
                jsonp: "callback",
                jsonpCallback: e.getCallbackName()
            }).done(r.proxy(e.jsonpSuccess, e)).fail(r.proxy(e.jsonpFail, e))
        },
        jsonpFail: function(e) {
            var t = this;
            if (!t.gotData) if (t.isPreview || n.send("book", "jsonloadfailure", {
                doc_id: t.doc_id,
                cn: t.chapterNum,
                version: 1
            }), t.retryCount < s.retryCount) t.retryCount++,
            t[t.reloadMethod]();
            else {
                t.gotData = !0;
                var a = new Date - t.loadErrorStartTime;
                try {
                    var o = r.trim(e.responseText) || ""; (12e3 > a || "[]" === o || "" === o) && t._errorLog()
                } catch(i) {}
                t.mediator.fire("json:fail")
            }
        },
        _errorLog: function() {
            var e = this;
            e.isPreview || n.send("book", "jsondataerror", {
                docid: e.doc_id,
                cn: e.chapterNum,
                pn: 0,
                level: 0,
                type: "bdjsonPCLoadError"
            })
        },
        jsonpSuccess: function(e) {
            var t = this;
            if (!t.gotData) {
                if (!t.isPreview) {
                    var a = new Date - t.loadStartTime;
                    n.send("book", "jsonload", {
                        time: a,
                        doc_id: t.doc_id,
                        cn: t.chapterNum,
                        version: 1
                    })
                }
                return t.gotData = !0,
                e ? void t.mediator.fire("json:success", {
                    data: {
                        json: e
                    }
                }) : (t._errorLog(), void t.mediator.fire("json:fail"))
            }
        },
        _getCacheTime: function() {
            var e = this,
            t = new Date;
            if (!e.isPreview) {
                var a = t.getMinutes(),
                o = 5 * Math.floor(a / 5);
                t.setMinutes(o),
                t.setSeconds(0),
                t.setMilliseconds(0)
            }
            return Number(t.getTime() / 1e3)
        }
    }),
    a.exports.BDJson = o
});;
define("ydcore:widget/book_view/txt_reader/js/chapter/singlepage.js",
function(e, a, r) {
    {
        var o, p = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
        n = {},
        t = (e("ydcommon:widget/ui/js_core/log/log.js"), e("ydcommon:widget/ui/js_core/mvp/template/template.js").template);
        e("ydcore:widget/book_view/txt_reader/js/chapter/config.js").config
    }
    n.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    o = n.lang.createClass(function(e) {
        this._init(e)
    }).extend({
        _init: function() {
            var e = this;
            p.extend(e, {
                wait: null,
                gotCoverImgSize: !1,
                height: 0,
                width: 0
            })
        },
        parse: function(e) {
            var a = this,
            r = e.coverPageInfo,
            o = "",
            p = "";
            if (!r.hasCoverPage) return "";
            try {
                o = a.parseCover(e)
            } catch(n) {
                o = ""
            }
            try {
                p = a.parseCopy(e)
            } catch(n) {
                p = ""
            }
            return o + p
        },
        parseCover: function(e) {
            var a = e.coverPageInfo,
            r = "reader-image-1-" + e.imgIndex,
            o = a.bigCoverSrc || a.coverSrc;
            e.mediator.fire("parse:loadImg", {
                data: {
                    imgId: r,
                    imgUrl: o
                }
            });
            var n = t(p("#cover-page-tpl").val(), {
                pClass: "single-page progress-absent cover-page p-b p-b-1 p-b-1-" + e.paragraphIndex,
                paragraphIndex: e.paragraphIndex,
                imgId: r,
                imgSrc: o,
                bookName: a.bookName
            });
            return e.imgIndex++,
            e.paragraphIndex++,
            e.naParagraphIndex++,
            n
        },
        parseCopy: function(e) {
            var a = e.coverPageInfo,
            r = t(p("#copy-page-tpl").val(), {
                pClass: "single-page progress-absent copy-page p-b p-b-1 p-b-1-" + e.paragraphIndex,
                paragraphIndex: e.paragraphIndex,
                bookName: a.bookName,
                bookAuthor: a.bookAuthor,
                bookTranslator: a.bookTranslator,
                bookPress: a.bookPress,
                bookISBN: a.bookISBN,
                bookCopy: a.bookCopy,
                publishType: a.publishType,
                ydPress: a.ydPress,
                copyText: a.copyText
            });
            return e.paragraphIndex++,
            e.naParagraphIndex++,
            r
        }
    }),
    r.exports.SinglePage = o
});;
define("ydcore:widget/book_view/txt_reader/js/chapter/newparser.js",
function(t, a, e) {
    var r, i = t("ydcommon:widget/ui/lib/jquery/jquery.js"),
    s = {},
    n = t("ydcommon:widget/ui/js_core/log/log.js"),
    o = t("ydcore:widget/book_view/txt_reader/js/chapter/config.js").config,
    p = t("ydcommon:widget/lib/fis/data/data.js"),
    l = t("ydcore:widget/book_view/txt_reader/js/chapter/singlepage.js").SinglePage;
    s.lang = t("ydcommon:widget/ui/lib/lang/lang.js"),
    s.browser = t("ydcommon:widget/ui/lib/browser/browser.js"),
    s.array = t("ydcommon:widget/ui/lib/array/array.js"),
    s.string = t("ydcommon:widget/ui/lib/string/string.js"),
    r = s.lang.createClass(function(t) {
        this._init(t)
    }).extend({
        _init: function(t) {
            var a = this;
            i.extend(a, t),
            i.extend(a, {
                singlePageParser: new l,
                parseTagList: {
                    img: "parseImgTag",
                    br: "parseBrTag",
                    obj: "parseObjTag",
                    table: "parseTableTag",
                    sdiv: "parseSdivTag"
                },
                parseObjList: {
                    note: "parseObjNote",
                    math: "parseObjMath",
                    "img-note": "parseObjImgNote",
                    video: "parseObjAV",
                    audio: "parseObjAV",
                    page: "parseObjPage",
                    textbox: "parseObjTextBox"
                },
                parseSdivList: {
                    code: "parseSdivCode",
                    list: "parseSdivLists"
                },
                legendIndex: 1,
                paragraphIndex: 1,
                naParagraphIndex: 1,
                imgIndex: 1,
                pToDiv: !1,
                extR: [],
                extAttr: {},
                dataLevel: 0
            })
        },
        parse: function() {
            var t = this,
            a = t.data,
            e = [];
            if (t.dataLevel++, i.isArray(a.c)) 1 === t.chapter && e.push(t.singlePageParser.parse(t)),
            i.each(a.c,
            function(a, r) {
                t._fixR(r),
                t._fixPicnote(r),
                r.r.push(o.pClass.paragraphBlock),
                r.r.push(o.pClass.paragraphBlock + "-" + t.paragraphIndex),
                r.r.push(o.pClass.paragraphBlock + "-" + t.chapter + "-" + t.paragraphIndex),
                r._customAttr = {
                    "data-cn": t.chapter,
                    "data-paragraph-index": t.paragraphIndex
                },
                "p" == r.t && i.isArray(r.c) && "page" == r.c[0].datatype && "br" == r.c[0].t ? r.r.push("progress-absent") : (r._customAttr["data-na-pn-index"] = t.naParagraphIndex, t.naParagraphIndex++),
                e.push(t.parseChild(r)),
                t.pToDiv = !1,
                t.paragraphIndex++
            });
            else {
                if (a.c && a.c.blockNum) return t.data = a.c,
                t.dataLevel--,
                t.parse();
                if (a.c && a.c.t) return a.c = [a.c],
                t.dataLevel--,
                t.parse();
                e.push(t.parseChild(a.c))
            }
            return t.dataLevel--,
            e.join("")
        },
        parseChild: function(t, a) {
            var e = this,
            r = [];
            a = a !== !1,
            e.dataLevel++,
            i.isArray(t.c) && "sdiv" !== t.t && (i.each(t.c,
            function(t, a) {
                r.push(e.parseChild(a))
            }), t.c = r.join(""), a = !1),
            2 === e.dataLevel && ("p" === t.t && e.pToDiv && (t.t = "div"), e.pToDiv = !1, e.extR.length && (e._fixR(t), t.r = t.r.concat(e.extR), e.extR = []), t._customAttr = t._customAttr || {},
            i.each(e.extAttr,
            function(a, e) {
                t._customAttr[a] = e
            }), e.extAttr = {});
            try {
                r = e.parseTag(t, a)
            } catch(s) {
                r = ""
            }
            return e.dataLevel--,
            r
        },
        _fixR: function(t) {
            t.r = t.r || [],
            i.isArray(t.r) || (t.r = [t.r])
        },
        _fixPicnote: function(t) {
            var a = i.inArray(o.pClass.extPicnote, t.r);
            if (t.t && a > -1) {
                var e = !0;
                if (t.c && i.isArray(t.c) && t.c.length > 0) {
                    var r;
                    if (i.each(t.c,
                    function(t, a) {
                        return a && "obj" === a.t ? (r = a, !1) : void 0
                    }), !r) {
                        var s = [],
                        n = 0;
                        i.each(t.c,
                        function(t, a) {
                            s.push({
                                t: "p",
                                c: [a]
                            });
                            try {
                                "img" === a.t.toLowerCase() && n++
                            } catch(e) {}
                        }),
                        r = {
                            t: "obj",
                            datatype: "img-note",
                            data: s
                        },
                        e = n > 0 && s.length - n > 0
                    }
                    t.c = [r]
                } else e = !1;
                t.r.splice(a, 1),
                e && t.r.push(o.pClass.extPicnoteWrap)
            }
        },
        parseTag: function(t, a) {
            var e = this;
            if (!t.hasOwnProperty("t")) return "";
            if (e._fixR(t), i.inArray(o.pClass.extDisplayNone, t.r) > -1) return "";
            t.c || (t.c = "", e.isPreview || n.send("book", "jsondataerror", {
                docid: e.docid,
                cn: e.chapter,
                pn: e.paragraphIndex,
                level: e.dataLevel,
                type: "dataIsNull"
            })),
            i.isArray(t.style) && (t._customAttr = t._customAttr || {},
            t._customAttr.style = e._parseStyle(t.style)),
            t["data-remark"] && (t = {
                t: "obj",
                datatype: "note",
                data: [{
                    t: "span",
                    c: t["data-remark"]
                }]
            });
            var r = e.parseTagList[t.t] || "parseNormalTag";
            return e[r] ? e[r](t, a === !0) : ""
        },
        parseATag: function(t) {
            t.r.push("span-to-link"),
            t.t = "a";
            var a = i.trim(t.href || "");
            a = encodeURI(a);
            for (var e, r = ["http", "/", "ftp://"], s = !0, n = 0; e = r[n++];) if (0 === a.indexOf(e)) {
                s = !1;
                break
            }
            s && (a = "http://" + a),
            t._customAttr = t._customAttr || {},
            i.extend(t._customAttr, {
                target: "_blank",
                href: a
            })
        },
        _parseCustomAttr: function(t) {
            var a = "";
            return t = t || {},
            i.each(t,
            function(t, e) {
                t && e && (a += " " + t + '="' + e + '"')
            }),
            a
        },
        parseNormalTag: function(t, a) {
            var e = this;
            t.href && e.parseATag(t);
            var r, n = s.string.encodeHTML(t.t),
            c = e._parseCustomAttr(t._customAttr),
            p = i.inArray(o.pClass.extVertical, t.r) > -1;
            return a ? p ? r = e.parseVertical(t.c) : (r = e.replacePunctuation(t.c), 2 === e.dataLevel && (r = "<span>" + r + "</span>")) : r = t.c,
            r = r.replace(/[\u0000-\u001F]/, ""),
            ["<", n, t.r.length > 0 ? ' class="' + s.string.encodeHTML(t.r.join(" ")) + '"': "", c, ">", r, "</", n, ">"].join("")
        },
        _getImgUrl: function(t) {
            var a = this;
            if (t.match(/(^http:|^https:)/)) return t;
            var e = a.imgUrl + ["type=pic", "src=" + t].join("&");
            return a.isLocalPreview && (e = t.replace(/&amp;/g, "&")),
            e
        },
        parseImgTag: function(t) {
            var a = this,
            e = "",
            r = (s.string.encodeHTML(t.r.join(" ")), a._getImgUrl(t.src)),
            i = o.pClass.img + "-" + a.chapter + "-" + a.imgIndex++,
            n = r,
            c = "",
            p = "",
            l = parseInt(t.h) || 0,
            d = parseInt(t.w) || 0;
            return d > a.maxImgWidth && (l = a.maxImgWidth / d * l, d = a.maxImgWidth),
            l > 0 && (c = ' height="' + l + '"'),
            d > 0 && (p = ' width="' + Math.min(a.maxImgWidth, d) + '"', n = !1),
            a.mediator.fire("parse:loadImg", {
                data: {
                    imgId: i,
                    imgUrl: n
                }
            }),
            e = '<img id="' + i + '" src="' + r + '" alt="' + (t.alt || "") + '"' + c + p + (t.r.length > 0 ? ' class="' + s.string.encodeHTML(t.r.join(" ")) + '"': "") + a._parseCustomAttr(t._customAttr) + "/>"
        },
        parseBrTag: function(t) {
            var a = "<br/>";
            return "page" === t.datatype && (a = '<span class="page-divide">' + a + "</span>"),
            a
        },
        parseTableTag: function(t, a) {
            var e = this,
            r = "",
            i = t["bg-img"];
            if (i) {
                t.t = "p",
                t.c = [i],
                delete t["bg-img"];
                try {
                    r = e.parseChild(t, a)
                } catch(s) {
                    r = ""
                }
            }
            return r
        },
        parseObjTag: function(t) {
            var a = this,
            e = "",
            r = a.parseObjList[t.datatype];
            return r && a[r] && (e = a[r](t)),
            e
        },
        parseObjNote: function(t) {
            var a = this,
            e = "";
            try {
                var r = t.data[0].c;
                r ? (a._fixR(t), t.r.push("ext_remark"), e = '<span data-remark="' + r + '" class="' + t.r.join(" ") + '"><b class="ic ic-remark"></b></span>') : e = ""
            } catch(i) {
                e = ""
            }
            return e
        },
        parseObjMath: function(t) {
            var a = this,
            e = "",
            r = t.data[0];
            if (r) try {
                e = a.parseChild(r),
                a.isPreview || n.xsend(2, "200222", {
                    docid: a.docid
                })
            } catch(i) {
                e = ""
            }
            return e
        },
        _getDataKey: function(t) {
            var a = [t, Math.random().toString(35).substr(2, 10), (new Date).getTime()];
            return a.join("_")
        },
        _saveAVData: function(t, a) {
            var e = this,
            r = e._getDataKey("av");
            t._customAttr = t._customAttr || {};
            var i = p.get("viewAVData");
            i || (i = {},
            p.set("viewAVData", i)),
            i[r] = a,
            t._customAttr["data-av-key"] = r
        },
        parseObjAV: function(t) {
            var a = this,
            e = "";
            try {
                if (!t.c) return "";
                var r = t.data[0];
                r.w = Math.max(t.w, r.w) || r.w,
                r.h = Math.max(t.h, r.h) || r.h,
                r.r = t.r || [],
                r.r.push(t.datatype + "-holder", "av-holder"),
                a._saveAVData(r, t.c),
                e = a.parseChild(r),
                a.pToDiv = !0,
                a.extR.push(t.datatype + "-wrap", "av-wrap", "custom-get-line"),
                a.extAttr = {
                    "data-line-type": "av"
                }
            } catch(i) {
                e = ""
            }
            return e
        },
        parseObjTextBox: function(t) {
            var a = this,
            e = "";
            a._fixR(t);
            var r = {
                t: "div",
                r: ["text-box"].concat(t.r),
                c: t.data
            };
            return e = a.parseChild(r),
            e && (a.pToDiv = !0),
            e
        },
        parseObjPage: function(t) {
            var a = this,
            e = "";
            a._fixR(t);
            var r = {
                t: "div",
                r: ["single-page-inner"].concat(t.r),
                c: [],
                _customAttr: {
                    "data-scale-value": "848|1130"
                }
            };
            i.isArray(t.data) || (t.data = t.data && t.data.t ? [t.data] : []);
            var s = t.data;
            if (s[0] && s[0].t && "p" === s[0].t.toLowerCase() && (s = s[0].c), i.each(s,
            function(t, e) {
                r.c.push(a.parseChild(e))
            }), t["bg-img"]) {
                var n = a._getImgUrl(t["bg-img"]),
                c = !1;
                if (a.isIE678 && (i.inArray("ext_bg-img_outer", t.r) > -1 || i.inArray("ext_bg-img_inner", t.r) > -1) && (c = !0), c) {
                    var p = o.pClass.img + "-" + a.chapter + "-" + a.imgIndex++;
                    a.mediator.fire("parse:loadImg", {
                        data: {
                            imgId: p,
                            imgUrl: n
                        }
                    }),
                    r.c.unshift('<img id="' + p + '" class="single-page-bg-img" src="' + n + '">')
                } else r._customAttr.style = "background-image:url(" + n + ");"
            }
            return r.c = r.c.join(""),
            e = a.parseChild(r, !1),
            e && (a.pToDiv = !0, a.extR.push("single-page", "normal-single-page")),
            e
        },
        _getCString: function(t) {
            var a = this;
            t = t || {},
            t.c = t.c || "";
            var e = [];
            try {
                "obj" === t.t && "note" === t.datatype ? e.push(t) : "string" === i.type(t.c) ? e.push(t.c) : i.isArray(t.c) ? i.each(t.c,
                function(t, r) {
                    e.push.apply(e, a._getCString(r))
                }) : t.c.t && e.push.apply(e, a._getCString(c))
            } catch(r) {}
            return e
        },
        _flatSpan: function(t) {
            var a, e = this,
            r = [],
            s = {
                t: "div",
                c: t
            },
            n = e._getCString(s);
            return i.each(n,
            function(t, s) {
                s && ("string" === i.type(s) ? a ? a.c += s: (a = {
                    t: "span",
                    r: [o.pClass.extVertical],
                    c: s
                },
                r.push(a)) : (e._fixR(s), s.r.push("v-s"), r.push({
                    t: "span",
                    r: [o.pClass.extVertical],
                    c: [s]
                }), a = null))
            }),
            r
        },
        parseObjImgNote: function(t) {
            var a = this,
            e = "",
            r = !1;
            try {
                if (t.data = t.data || [], i.isArray(t.data) || (t.data = [t.data]), t.data.length > 0) {
                    var n, c = [];
                    if (i.each(t.data,
                    function(t, e) {
                        var r = e.c[0];
                        "img" === r.t ? n = n || r: (a._fixR(r), c.push(r))
                    }), n) {
                        var p;
                        if (c.length && (c[0].c || c[0].data)) {
                            r = !0;
                            var l = s.array.unique(c[0].r),
                            d = [],
                            u = i.inArray(o.pClass.extVertical, l);
                            u > -1 && (d.push(o.pClass.extVertical), l.splice(u, 1));
                            var g = l.join(" ").match(/ext_picnote_((bottom)|(top)|(left)|(right))/);
                            g ? -1 !== u || "left" !== g[1] && "right" !== g[1] || d.push(o.pClass.extVertical) : l.push(o.pClass.extPicnoteBottom),
                            d.length && (c = a._flatSpan(c));
                            var h = {
                                t: "div",
                                c: [n],
                                r: ["picnote-img"]
                            },
                            m = {
                                t: "div",
                                c: c,
                                r: ["picnote-text"]
                            },
                            v = [h, m];
                            i.inArray(o.pClass.extPicnoteTop, l) > -1 && v.reverse(),
                            p = {
                                t: "div",
                                c: v,
                                r: [o.pClass.extPicnote].concat(l)
                            }
                        } else p = n;
                        e = a.parseChild(p)
                    } else e = ""
                } else e = ""
            } catch(f) {
                e = ""
            }
            return e && r && (a.pToDiv = !0),
            e
        },
        parseSdivTag: function(t) {
            var a = this,
            e = "",
            r = a.parseSdivList[t.datatype];
            return i.isFunction(a[r]) && (e = a[r](t)),
            e
        },
        _getNewCodeC: function(t, a, e) {
            var r = [];
            t = t || [],
            i.isArray(t) || (t = [t]);
            var s = 0,
            n = 0,
            c = 0,
            o = 0;
            return a && (c += 17, 1 > e && (e = 1), s = 11 * (e + t.length).toString(10).length, o = s + 5, n = o + 3),
            i.each(t,
            function(p, l) {
                if (l.t = "div", l.c && 0 !== l.c.length && l.c[0]) {
                    var d = l.c.length,
                    u = l.c[0],
                    g = u.r || [];
                    i.isArray(g) || (g = [g]),
                    g = g.join(" ");
                    var h = g.match(/ext_code_spaces_(\d+)/),
                    m = "",
                    v = 0;
                    if (a && (v += c, m = "margin-left:" + o + "px;", l.c.unshift({
                        t: "span",
                        r: ["code-line-num"],
                        c: e + p,
                        _customAttr: {
                            style: "width:" + s + "px;left:-" + n + "px;"
                        }
                    }), p === t.length - 1 && l.c.unshift({
                        t: "span",
                        r: ["code-line-mask", "code-line-mask-down"],
                        c: ""
                    }), 0 === p && l.c.unshift({
                        t: "span",
                        r: ["code-line-mask", "code-line-mask-up"],
                        c: ""
                    })), h) {
                        d--;
                        var f = parseInt(h[1]) || 0;
                        f > 0 && (f = Math.max(2, f), v += 10 * f)
                    }
                    v > 0 && (m += "padding-left:" + v + "px;"),
                    m && (l._customAttr = {
                        style: m
                    }),
                    d || l.c.push({
                        t: "span",
                        r: ["ext_code_spaces"],
                        c: "&nbsp;"
                    }),
                    r.push(l)
                }
            }),
            r
        },
        parseSdivLists: function(t) {
            var a = this,
            e = "";
            try {
                var r, s = {
                    decimal: "ol",
                    "lower-alpha": "ol",
                    "cjk-ideographic": "ol",
                    "upper-roman": "ol",
                    dot: "ul",
                    disc: "ul",
                    square: "ul",
                    diamond: "ul"
                },
                n = {
                    "cjk-ideographic": function(t) {
                        var a = {
                            0 : "\u96f6",
                            1 : "\u4e00",
                            2 : "\u4e8c",
                            3 : "\u4e09",
                            4 : "\u56db",
                            5 : "\u4e94",
                            6 : "\u516d",
                            7 : "\u4e03",
                            8 : "\u516b",
                            9 : "\u4e5d",
                            10 : "\u5341",
                            100 : "\u767e",
                            1000 : "\u5343",
                            10000 : "\u4e07"
                        },
                        e = [],
                        r = "";
                        t += "";
                        for (var i = t.length,
                        s = 0; i > s; s++) {
                            if (0 == t[s]) t[s + 1] && 0 != t[s + 1] && (r = a[0]);
                            else switch ((2 != i || 1 != t[0] || 0 != s) && (r = a[t[s]]), i - s - 1) {
                            case 1:
                                r += a[10];
                                break;
                            case 2:
                                r += a[100];
                                break;
                            case 3:
                                r += a[1e3];
                                break;
                            case 4:
                                r += a[1e4]
                            }
                            e.push(r),
                            r = ""
                        }
                        return e.push(". "),
                        e.join("")
                    }
                },
                c = function(t) {
                    return t.dot.match(/(^http|^https)/) || i.each(t,
                    function(a, e) {
                        t[a] = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ":" + window.location.port: "") + e
                    }),
                    t
                } ({
                    dot: "http://static.wenku.bdimg.com/static/ydcore/widget/book_view/txt_reader/img/icon-list-disc_6dcf099.png",
                    disc: "http://static.wenku.bdimg.com/static/ydcore/widget/book_view/txt_reader/img/icon-list-disc_6dcf099.png",
                    square: "http://static.wenku.bdimg.com/static/ydcore/widget/book_view/txt_reader/img/icon-list-square_7f7bab9.png",
                    diamond: "http://static.wenku.bdimg.com/static/ydcore/widget/book_view/txt_reader/img/icon-list-diamond_78ebc54.png"
                });
                r = {
                    t: s[t.listtype],
                    c: function(a) {
                        var e = [];
                        return i.each(a,
                        function(a, r) {
                            var i = function(t) {
                                var a, e = arguments.callee,
                                r = t.c;
                                if ("span" != t.t) r instanceof Array && r.length > 0 && (a = e(r[0]));
                                else if (t.r) {
                                    var i = t.r.join(" ");
                                    a = i.match(/(ext_color_[^.(?= )]+)/),
                                    a = a && a[0]
                                }
                                return a
                            } (r);
                            c[t.listtype] && (r.c = [{
                                t: "span",
                                c: r.c
                            }], r.c.unshift({
                                t: "img",
                                src: c[t.listtype],
                                r: ["list-" + t.listtype + "-icon"]
                            })),
                            n[t.listtype] && (r.c = [{
                                t: "span",
                                c: r.c
                            }], r.c.unshift({
                                t: "span",
                                r: ["list-" + t.listtype + "-icon", i || ""],
                                c: n[t.listtype](a + 1)
                            })),
                            e.push({
                                t: "li",
                                c: [r],
                                r: ["list-" + t.listtype, i || ""]
                            })
                        }),
                        e
                    } (t.c),
                    r: t.r.concat(["custom-" + t.listtype, "custom-get-line", o.pClass.paragraphBlock]),
                    _customAttr: i.extend(t._customAttr, {
                        "data-line-type": "list"
                    })
                },
                e = a.parseChild(r)
            } catch(p) {
                e = ""
            }
            return e
        },
        parseSdivCode: function(t) {
            var a = this,
            e = "";
            try {
                var r, s = t.lang,
                n = t["line-number"],
                c = parseInt(n),
                o = !isNaN(c),
                p = a._getNewCodeC(t.c, o, c);
                p.length > 0 ? (r = {
                    t: "div",
                    c: [{
                        t: "div",
                        c: p,
                        r: ["code-container"]
                    }],
                    r: t.r.concat(["ext_code_wrap", "custom-get-line", "ext_code_lang_" + s, "code-theme-default"]),
                    _customAttr: i.extend(t._customAttr, {
                        "data-line-type": "code"
                    })
                },
                o && r.r.push("code-has-line-num")) : r = {
                    t: "p",
                    c: " "
                },
                e = a.parseChild(r)
            } catch(l) {
                e = ""
            }
            return e
        },
        parseVertical: function(t) {
            for (var a, e, r, i = this,
            s = t.split(""), n = o.pClass.verticalChar, c = 0, p = s.length; p > c; c++) r = s[c],
            a = "",
            e = "",
            o.verticalTypeset.regex.test(r) ? (i.isIE67 && (a = " style=\"filter: progid:DXImageTransform.Microsoft.Matrix(M11=-1.836909530733566e-16, M12=-1, M21=1, M22=-1.836909530733566e-16, SizingMethod='auto expand');\""), e = " " + o.pClass[i.isIE678 ? "verticalSingleCharIE": "verticalSingleChar"]) : e = "",
            s[c] = '<span class="' + n + " " + n + "-" + c + e + '"' + a + ">" + r + "</span>";
            return s.join("")
        },
        replacePunctuation: function() {
            var t = o.punctuation.singleByte.value,
            a = o.punctuation.doubleByte.value,
            e = o.punctuation.className,
            r = o.punctuation.singleByte.className,
            i = o.punctuation.doubleByte.className;
            return s.browser.ie && s.browser.ie < 8 ?
            function(t) {
                return t += "",
                t.replace(/</g, "&lt;").replace(/>/g, "&gt;")
            }: function(s) {
                s += "",
                s = s.replace(/</g, "&lt;").replace(/>/g, "&gt;");
                var n = new RegExp("(&[\\w#]+;)|([" + t + "])", "g"),
                c = new RegExp("[" + a + "]", "g");
                return '<span class="text">' + s.replace(n,
                function(t, a, i) {
                    return a ? a: '</span><span class="' + e + " " + r + '">' + i + '</span><span class="text">'
                }).replace(c,
                function(t) {
                    return '</span><span class="' + e + " " + i + '">' + t + '</span><span class="text">'
                }) + "</span>"
            }
        } (),
        parseRoot: function(t) {
            var a = this,
            e = a.data;
            t.addClass(a.parseAttr(e.r)).attr({
                "data-block-num": e.blockNum || 0,
                style: a.parseAttr(e.style)
            })
        },
        _parseStyle: function(t) {
            var a = {},
            e = "";
            return i.each(t,
            function(t, e) {
                i.extend(a, e)
            }),
            i.each(a,
            function(t, a) {
                e += s.string.encodeHTML(t) + ": " + s.string.encodeHTML(a) + ";"
            }),
            e
        },
        parseAttr: function(t, a) {
            return i.isArray(t) && (t = t.join(" ")),
            t ? a && (t = " " + a + '="' + t + '"') : t = "",
            t
        }
    }),
    e.exports.Parser = r
});;
define("ydcore:widget/book_view/txt_reader/js/chapter/shrink.js",
function(e, t, a) {
    var n, i = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    s = {},
    o = e("ydcore:widget/book_view/txt_reader/js/chapter/config.js").config;
    s.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    s.browser = e("ydcommon:widget/ui/lib/browser/browser.js"),
    n = s.lang.createClass(function(e) {
        this._init(e)
    }).extend({
        _init: function(e) {
            var t = this;
            return i.extend(t, e, {
                offsetWidth: 18,
                offsetLeft: 0,
                offsetRight: 0,
                lineHeight: 18,
                dealtPCount: 0
            }),
            t.isIE67 ? void t.mediator.fire("shrink:ready") : (t.dealOneLine(), void(t.dealtPCount < t.paragraphCount && t.deal()))
        },
        _getOffset: function() {
            var e = this,
            t = {
                visibility: "hidden",
                position: "absolute",
                fontSize: "18px",
                left: 0,
                top: 0
            },
            a = i("." + o.fontWrapId, e.$container),
            n = a.css("position");
            a.css("position", "relative"),
            $placeholder = i("<span>").html("\uff0c").css(t).prependTo(a),
            e.offsetWidth = $placeholder.width(),
            $placeholder.remove();
            var s = i("<p>").html("test").css(t).prependTo(a);
            e.lineHeight = s.height(),
            s.remove(),
            a.css("position", n)
        },
        dealOneLine: function() {
            var e = this;
            if (o.dealOneLine === !0) {
                var t = o.pClass.oneLine,
                a = o.pClass.nextIsOneLine,
                n = o.pClass.prevIsOneLine,
                s = o.pClass.extInscribed,
                l = !1,
                r = i(),
                u = r;
                e.$paragraphList.map(function(o, d) {
                    var f = i(d);
                    "p" !== f.get(0).tagName.toLowerCase() || f.height() !== e.lineHeight || f.hasClass(s) ? (l = !1, u = r) : (l ? (f.addClass(t + " " + n), u.addClass(a)) : f.addClass(t), l = !0, u = f)
                })
            }
        },
        deal: function() {
            var e = this,
            t = e.$paragraphList.slice(e.dealtPCount, e.dealtPCount + o.dealPCount),
            a = t.size();
            e._deal(t),
            e.dealtPCount += a,
            e.dealtPCount < e.paragraphCount ? setTimeout(function() {
                e.deal()
            },
            o.dealPTimespan) : e.mediator.fire("shrink:ready")
        },
        _deal: function() {
            var e = o.punctuation.className,
            t = o.punctuation.singleByte.className,
            a = o.punctuation.singleByte.extRightDiff,
            n = o.punctuation.doubleByte.extRightDiff;
            return s.browser.ie && (n += o.punctuation.doubleByte.ieDiffEncrement),
            function(s) {
                var o = this;
                s.each(function(s, l) {
                    var r = i(l),
                    u = r.offset(),
                    d = r.width();
                    o.offsetLeft = u.left,
                    o.offsetRight = u.left + d - o.offsetWidth;
                    var f = r.find("span." + e);
                    f.each(function(e, s) {
                        var l = i(s),
                        r = l.offset(),
                        u = r.left - o.offsetLeft,
                        d = o.offsetRight - r.left,
                        f = l.hasClass(t);
                        f ? a > u && o.dealSingleByteChar(l, u) : n > u ? o.dealDoubleByteChar(l, u) : o.dealRightDBChar(l, d)
                    })
                })
            }
        } (),
        dealSingleByteChar: function() {
            {
                var e = (o.pClass.extRight, o.punctuation.singleByte.shrinkLeftClassName);
                o.punctuation.singleByte.shrinkRightClassName
            }
            return function(t) {
                var a = "";
                a = e,
                t.addClass(a)
            }
        } (),
        dealDoubleByteChar: function() {
            var e = (o.pClass.extRight, o.punctuation.doubleByte.shrinkLeftClassName),
            t = (o.punctuation.doubleByte.shrinkRightClassName, o.punctuation.doubleByte.firstQuoteClassName);
            return function(a) {
                var n = this,
                i = "";
                switch (a.text()) {
                case "\u2014":
                    break;
                case "\u201c":
                case "\u2018":
                    a.addClass(t);
                    break;
                default:
                    i = e,
                    a.addClass(i),
                    n.isDealDBOk(a) || a.removeClass(i).addClass("sb-xx")
                }
            }
        } (),
        isDealDBOk: function() {
            if (s.browser.ie) {
                var e = o.punctuation.doubleByte.extRightDiff;
                return e += o.punctuation.doubleByte.ieDiffEncrement,
                function(t) {
                    var a = this,
                    n = t.offset().left,
                    i = n - a.offsetLeft;
                    return Math.abs(i) <= e ? !1 : !0
                }
            }
            return function() {
                return ! 0
            }
        } (),
        dealRightDBChar: function() {
            var e = [],
            t = o.punctuation.doubleByte.lineEndCharDiffs;
            e.push(Math.min.apply(null, t)),
            e.push(Math.max.apply(null, t));
            var a = [],
            n = o.punctuation.doubleByte.lineEndCharDiffsSmall;
            return a.push(Math.min.apply(null, n)),
            a.push(Math.max.apply(null, n)),
            function(t, n) {
                var i = e;
                switch (t.text()) {
                case "\u3001":
                    i = a
                }
                n > i[0] && n < i[1] && t.addClass("sh-end-2")
            }
        } ()
    }),
    a.exports.Shrink = n
});;
define("ydcore:widget/book_view/txt_reader/js/chapter/legend.js",
function(e, t, i) {
    var a, n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    h = {},
    o = e("ydcore:widget/book_view/txt_reader/js/chapter/config.js").config;
    h.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    a = h.lang.createClass(function(e) {
        this._init(e)
    }).extend({
        _init: function(e) {
            var t = this;
            n.extend(t, e, {
                maxImgWidth: e.$container.width(),
                imgPool: [],
                contentReady: !1,
                doneCount: 0,
                totalCount: 0
            }),
            t.bindEvent()
        },
        bindEvent: function() {
            var e = this;
            e.mediator.on("content:ready",
            function() {
                e.contentReady = !0,
                e.imgPool.length > 0 ? n.each(e.imgPool,
                function(t, i) {
                    e.push(i.imgId, i.size, !0)
                }) : e.doneCount >= e.totalCount && e.mediator.fire("legend:ready")
            }),
            e.mediator.on("parse:loadImg", n.proxy(e.loadImg, e))
        },
        done: function() {
            var e = this;
            e.doneCount++,
            e.contentReady && e.doneCount >= e.totalCount && e.mediator.fire("legend:ready")
        },
        resizeImg: function(e, t) {
            var i = this;
            t = t || i.maxImgWidth;
            var a = e.width(),
            n = e.height();
            if (a > t) {
                var h = n * t / a;
                return void e.width(t).height(h)
            }
        },
        dealImg: function(e, t) {
            var i = this,
            a = e.parent(),
            n = t - 200;
            if (e.hasClass("ext_float_left") || e.hasClass("ext_float_right") ? (a.addClass("has-float-img"), i.resizeImg(e, n)) : i.resizeImg(e, t), a.hasClass("p-b")) {
                var h = a.children();
                if (1 === h.size()) a.addClass("single-img-p");
                else if (h.size() > 1) {
                    var s = h.first(),
                    d = a.hasClass("ext_float_left") ? "left": a.hasClass("ext_float_right") ? "right": "",
                    r = !1;
                    try {
                        r = "img" === s[0].tagName.toLowerCase()
                    } catch(l) {
                        r = !1
                    }
                    d && r && (s.addClass("ext_float_" + d), a.addClass("has-float-img").removeClass("ext_float_" + d), i.resizeImg(s, n))
                }
            } else a = a.parent(),
            a.hasClass(o.pClass.extPicnote) && i.dealPicnote(a, e, t);
            i.done()
        },
        dealPicnote: function(e, t) {
            var i = this;
            if (e.hasClass(o.pClass.extPicnote)) {
                var a = e.find(".picnote-text");
                if (a.size() > 0) {
                    var n;
                    if (e.hasClass(o.pClass.extPicnoteLeft) ? n = "left": e.hasClass(o.pClass.extPicnoteRight) && (n = "right"), n) {
                        var h = i.splitColumn(t, i.mergeLegend(a));
                        if (a.width(h[0] * h[1]), 1 === h[0]) if (e.hasClass(o.pClass.extBottom)) {
                            var s = a.height(),
                            d = t.height();
                            d > s && a.css("marginTop", d - s)
                        } else if (e.hasClass(o.pClass.extCenter)) {
                            var s = a.height(),
                            d = t.height();
                            d > s && a.css("marginTop", (d - s) / 2)
                        }
                        i.isIE67 && e.width(t.width() + a.width())
                    }
                }
            }
        },
        mergeLegend: function(e) {
            var t = n();
            e.children(".ext_vertical").each(function(e, i) {
                t = t.add(n(i).children())
            }),
            t.each(function(e, t) {
                var i = t.className;
                i = i.replace(/v-s-[0-9]+\s?/, " "),
                t.className = i + " v-s-" + e
            });
            var i = n('<p class="ext_vertical"></p>').append(t);
            return e.html("").append(i),
            i
        },
        _resizeImg: function(e, t, i) {
            var a, n, h = e.height(),
            o = e.width();
            "width" === t ? (n = i, a = n * h / o) : (a = i, n = a * o / h),
            e.height(a).width(n)
        },
        splitColumn: function(e, t) {
            var i = this,
            a = t.clone(),
            h = t.width() + 15,
            o = i.maxImgWidth,
            s = 0;
            e.width() + h > o && i._resizeImg(e, "width", o - h);
            for (var d, r, l, g, c, f;;) {
                for (d = 0, r = null, l = t, g = e.height(), f = n(); l;) l.height() > g ? (r = r || n('<p class="ext_vertical"></p>'), r.prepend(l.children().last())) : (d++, f = f.add(l), r && l.after(r), l = r, r = null);
                if (s++, c = h * d, !(e.width() + c > o && 5 > s)) break;
                i._resizeImg(e, "width", o - c - 2 * h),
                t.replaceWith(t = a.clone()),
                f.remove()
            }
            return [d, h]
        },
        push: function(e, t, i) {
            var a = this,
            h = n("#" + e),
            o = a.maxImgWidth;
            if (h.size() > 0) {
                if (t) {
                    if ("reader-image-1-1" === e) {
                        var s = h.closest(".p-b");
                        if (s.hasClass("cover-page")) return t.width < 840 || t.height < 1120 ? s.removeClass("cover-page").removeClass("single-page").html("") : (o = a.defaultWidth - 2, t.width > o && (t.height = t.height * o / t.width, t.width = o), h.attr({
                            width: t.width,
                            height: t.height
                        }), h.parent().height(t.height).attr("data-scale-value", t.width + "|" + t.height)),
                        void a.done()
                    }
                    t.width > o && (t.height = t.height * o / t.width, t.width = o),
                    h.attr("height", t.height).attr("width", t.width)
                }
                a.dealImg(h, o)
            } else {
                if (i === !0) return void a.done();
                a.imgPool.push({
                    imgId: e,
                    size: t
                })
            }
        },
        loadImg: function(e) {
            var t = this,
            i = e.data,
            a = i.imgUrl,
            n = i.imgId,
            h = 3e3;
            return "-1-1" === n.substr( - 4) && (h = 5e3),
            t.totalCount++,
            a ? (window.noSizeImg = window.noSizeImg || [], window.noSizeImg.push({
                imgId: n,
                imgUrl: a
            }), void t.imgReady(a,
            function(e) {
                var i;
                try {
                    i = e ? {
                        height: this.height || o.defaultImgSize.height,
                        width: this.width || o.defaultImgSize.width
                    }: {
                        height: this.height || o.defaultImgSize.height,
                        width: this.width || o.defaultImgSize.width
                    }
                } catch(a) {
                    i = o.defaultImgSize
                }
                t.push(n, i)
            },
            h)) : void t.push(n)
        },
        imgReady: function() {
            var e = [],
            t = null,
            i = function() {
                for (var t = 0; t < e.length; t++) e[t].end ? e.splice(t--, 1) : e[t](); ! e.length && a()
            },
            a = function() {
                clearInterval(t),
                t = null
            };
            return function(a, n, h) {
                var o, s, d, r, l, g, c = new Image;
                if (s = function() {
                    o.end = !0,
                    n.call(c, !0),
                    c = c.onload = c.onerror = null
                },
                o = function(e) {
                    l = c.width,
                    g = c.height,
                    (l !== d || g !== r || l * g > 0 || e) && (o.end = !0, n.call(c), c = c.onload = c.onerror = null)
                },
                c.onerror = s, c.onload = function() { ! o.end && o(!0)
                },
                c.src = a, !o.end) {
                    var f;
                    try {
                        f = c.complete
                    } catch(m) {
                        f = !1
                    }
                    if (o.end || !f) d = c.width,
                    r = c.height,
                    !o.end && o(),
                    o.end || (e.push(o), null === t && (t = setInterval(i, 40)), setTimeout(function() { ! o.end && s()
                    },
                    h || 3e3));
                    else {
                        n.call(c);
                        try {
                            c = c.onload = c.onerror = null
                        } catch(m) {}
                    }
                }
            }
        } ()
    }),
    i.exports.Legend = a
});;
define("ydcore:widget/book_view/txt_reader/js/chapter/chapter.js",
function(e, n, t) {
    var a, i = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    r = {},
    o = e("ydcommon:widget/ui/js_core/mvp/event/event.js").custEvent,
    d = e("ydcommon:widget/ui/js_core/log/log.js"),
    c = e("ydcore:widget/book_view/txt_reader/js/chapter/config.js").config,
    s = e("ydcore:widget/book_view/txt_reader/js/chapter/bdjson.js").BDJson,
    l = e("ydcore:widget/book_view/txt_reader/js/chapter/newparser.js").Parser,
    p = e("ydcore:widget/book_view/txt_reader/js/chapter/shrink.js").Shrink,
    u = e("ydcore:widget/book_view/txt_reader/js/chapter/legend.js").Legend;
    r.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    r.platform = e("ydcommon:widget/ui/lib/platform/platform.js");
    var f = ""; !
    function() {
        if (r.platform.isWindows) {
            var e = navigator.userAgent.match(/Windows NT (\d+).(\d+)/); + e[1] > 5 && (f = c.readerForWin7)
        } else r.platform.isMacintosh && (f = c.readerForMac);
        "" === f && (f = c.readerForOther)
    } (),
    a = r.lang.createClass(function(e) {
        this._init(e)
    }).extend({
        _init: function(e) {
            var n = this;
            i.extend(n, e, {
                mediator: new o,
                $currentBlock: null,
                $container: null,
                $containerWrap: null,
                _ffkanData: {},
                ready: !1,
                doingCount: 0,
                doneCount: 0,
                imgReady: !1,
                loadStartTime: new Date
            }),
            n._initContainer(),
            n._bindEvent(),
            n.getJsonData()
        },
        _initContainer: function() {
            var e = this,
            n = i(['<div id="' + c.containerId + "-" + e.chapterNum + '" class="' + c.containerId + '">', '<div class="' + c.containerId + '-inner">', '<div id="' + c.fontWrapId + "-" + e.chapterNum + '" class="' + c.fontWrapId + " " + f + '"></div>', "</div>", "</div>"].join(""));
            e.$doc.find("body").append(n),
            e.$containerWrap = n,
            e.$container = n.children().first(),
            e.$currentBlock = e.$container.children().first()
        },
        _bindEvent: function() {
            function e() {
                n.doneCount++,
                n.chapterReady()
            }
            var n = this,
            t = n.mediator;
            t.on("legend:ready", e),
            t.on("shrink:ready", e),
            t.on("json:fail", i.proxy(n.jsonFail, n)),
            t.on("json:success", i.proxy(n.jsonSuccess, n))
        },
        getJsonData: function() {
            var e = this,
            n = {
                doc_id: e.doc_id,
                bdjsonUrl: e.bdjsonUrl,
                mediator: e.mediator,
                isFfkan: e.isFfkan,
                isPreview: e.isPreview,
                isLocalPreview: e.isLocalPreview,
                perBlockCount: e.perBlockCount,
                chapterNum: e.chapterNum,
                ispaied: e.ispaied
            };
            new s(n)
        },
        jsonFail: function() {
            var e = this;
            e.outMediator.fire("chapter:json-fail", {
                data: {
                    isFfkan: e.isFfkan,
                    chapterNum: e.chapterNum
                }
            })
        },
        jsonSuccess: function(e) {
            var n = this;
            n.renderReader(e.data.json)
        },
        renderReader: function(e) {
            var n = this;
            n.doingCount++,
            n.dealLegend(),
            n.fillContent(e),
            n.mediator.fire("content:ready"),
            n.dealShrink(),
            n.doneCount++,
            n.chapterReady(e)
        },
        chapterReady: function(e) {
            var n = this;
            if (e && i.extend(n._ffkanData, {
                title: e.title,
                begin: e.c_head || "0-0",
                end: e.c_tail || "0-0"
            }), !n.ready && n.doneCount >= n.doingCount) {
                n.ready = !0,
                n.isLast && n.addPayBlock(); {
                    var t = n.$container.html();
                    n.$currentBlock.outerHeight()
                }
                n.$containerWrap.remove(),
                n.$container = null,
                n.$currentBlock = null,
                n.$containerWrap = null,
                n.outMediator.fire("chapter:chapter-ready", {
                    data: {
                        isFfkan: n.isFfkan,
                        cache: {
                            ffkanData: n._ffkanData,
                            chapterNum: n.chapterNum,
                            domId: c.fontWrapId + "-" + n.chapterNum,
                            html: t
                        },
                        chapterNum: n.chapterNum
                    }
                })
            }
        },
        addPayBlock: function() {
            var e, n = this,
            t = "",
            a = "last-p-b";
            n.isFfkan ? (a += " ffk-last-p-b", t = ['<p class="">\u8fd9\u6bb5\u5185\u5bb9\u5df2\u8bfb\u5b8c\uff0c<a href="###" class="fan-1-fan-link">\u518d\u7ffb\u4e00\u4e0b</a>\u770b\u66f4\u591a\u5185\u5bb9</p>'].join("")) : n.isQADoc ? (a += " qa-last-p-b", t = ['<div class="qa-btn-wrap">', '<button class="qa-btn yui-btn yui-btn-green">\u5f00\u59cb\u7b54\u9898</button>', "</div>"].join("")) : (e = i("#book-end"), 0 === e.size() ? (e = i("#pay-block-mod").find(".pay-page-inner"), t = e.html()) : t = '<p class="book-end">' + e.html() + "</p>", t += i("#ft .footer").children().first().addClass("footer").end().end().html());
            var r = n.$currentBlock.find(".p-b"),
            o = r.size(),
            d = r.last(),
            c = parseInt(d.attr("data-paragraph-index")) || 0;
            c ? c += 1 : c = o + 1,
            e = i(['<div class="' + a + " p-b p-b-" + c + " p-b-" + n.chapterNum + "-" + c + ' no-select-range progress-absent"', ' data-paragraph-index="' + c + '">', t, "</div>"].join("")),
            d.size() > 0 ? e.insertAfter(d) : e.appendTo(n.$currentBlock.find(".text-container"))
        },
        fillContent: function(e) {
            var n, t, a = this,
            i = new l({
                coverPageInfo: a.coverPageInfo,
                docid: a.doc_id,
                isIE67: a.isIE67,
                isIE678: a.isIE678,
                isPreview: a.isPreview,
                isLocalPreview: a.isLocalPreview,
                mediator: a.mediator,
                data: e,
                fontWrapId: c.fontWrapId,
                imgUrl: a.bdjsonUrl,
                maxImgWidth: a.$container.width(),
                chapter: a.chapterNum
            }),
            r = new Date;
            try {
                t = i.parse()
            } catch(o) {
                t = ""
            }
            i.parseRoot(a.$currentBlock),
            a.$currentBlock.html(['<div class="text-container">', t, "</div>"].join("")),
            a.isPreview || (n = new Date - r, d.send("book", "rendertime", {
                time: n,
                doc_id: a.doc_id,
                cn: a.chapterNum,
                version: 1
            }))
        },
        dealLegend: function() {
            var e = this;
            e.doingCount++,
            new u({
                isIE67: e.isIE67,
                mediator: e.mediator,
                defaultWidth: e.defaultWidth,
                $container: e.$container
            })
        },
        dealShrink: function() {
            var e = this,
            n = e.$currentBlock.find(".p-b"),
            t = n.size();
            1 > t || (e.doingCount++, new p({
                isIE67: e.isIE67,
                $container: e.$container,
                $paragraphList: n,
                paragraphCount: t,
                mediator: e.mediator
            }))
        }
    }),
    t.exports.Chapter = a
});;
define("ydcore:widget/book_view/txt_reader/js/chapter/parser.js",
function(a, r, e) {
    var t, s = a("ydcommon:widget/ui/lib/jquery/jquery.js"),
    n = {},
    p = a("ydcommon:widget/ui/js_core/log/log.js"),
    i = a("ydcore:widget/book_view/txt_reader/js/chapter/config.js").config;
    n.lang = a("ydcommon:widget/ui/lib/lang/lang.js"),
    n.browser = a("ydcommon:widget/ui/lib/browser/browser.js"),
    t = n.lang.createClass(function(a) {
        this._init(a)
    }).extend({
        _init: function(a) {
            var r = this;
            s.extend(r, a),
            s.extend(r, {
                html: [],
                selfClosing: ["br", "img"],
                legendIndex: 1,
                paragraphIndex: 1,
                imgIndex: 1,
                dataLevel: 0
            })
        },
        parseCover: function() {
            {
                var a = this;
                a.coverPageInfo
            }
        },
        parse: function() {
            var a = this,
            r = a.data;
            if (1 === a.chapter && a.parseCover(), a.dataLevel++, s.isArray(r.c)) if (r.c.length > 0) for (var e, t, n = 0,
            p = r.c.length; p > n; n++) e = r.c[n],
            t = r.c[n + 1],
            e.t && "p" === e.t.toLowerCase() && e.r && (s.isArray(e.r) || (e.r = [e.r])) && s.inArray(i.pClass.extPicnote, e.r) > -1 && e.c && s.isArray(e.c) && e.c.length > 0 ? 2 === e.c.length ? a.parseLegend(e) : a._preParseObjImgNote(e) : (e.r = e.r || [], s.isArray(e.r) || (e.r = [e.r]), e.r.push(i.pClass.paragraphBlock), e.r.push(i.pClass.paragraphBlock + "-" + a.paragraphIndex), e.r.push(i.pClass.paragraphBlock + "-" + a.chapter + "-" + a.paragraphIndex), e._ylAttr_ = {
                "data-paragraph-index": a.paragraphIndex++
            },
            a.parseContent(e));
            else r.c = null,
            a.parseContent(r.c);
            else a.parseContent(r.c);
            return a.dataLevel--,
            a.html.join("")
        },
        parseRoot: function(a) {
            var r = this,
            e = r.data;
            a.addClass(r.parseAttr(e.r)).attr({
                "data-block-num": e.blockNum || 0,
                style: r.parseAttr(e.style)
            })
        },
        _preParseObjImgNote: function(a) {
            var r, e = this,
            t = {
                t: "div",
                c: "",
                r: [i.pClass.extPicnoteWrap]
            };
            if (s.each(a.c,
            function(a, e) {
                return e && "obj" === e.t ? (r = e, !1) : void 0
            }), r) {
                try {
                    r.data[1].c[0].r = r.data[1].c[0].r || [],
                    r.data[1].c[0].r.push(i.pClass.extLegends)
                } catch(n) {}
                t.c = [r]
            }
            t.r.push(i.pClass.extPicnote + "_" + e.chapter + "_" + e.legendIndex++),
            t.r.push(i.pClass.paragraphBlock),
            t.r.push(i.pClass.paragraphBlock + "-" + e.paragraphIndex),
            t.r.push(i.pClass.paragraphBlock + "-" + e.chapter + "-" + e.paragraphIndex),
            t._ylAttr_ = {
                "data-paragraph-index": e.paragraphIndex++
            },
            e.parseContent(t)
        },
        parseLegend: function(a) {
            var r, e = this,
            t = [];
            if (s.each(a.c,
            function(a, e) {
                return e && "obj" === e.t ? (r = e, !1) : void 0
            }), r) return void e._preParseObjImgNote(a);
            "img" === a.c[1].t && a.c.reverse();
            var n = {
                t: "p",
                c: [a.c[0]],
                r: []
            },
            p = a.c[1];
            p.t = "p",
            p.r.push(i.pClass.extLegends);
            var c = {
                t: "div",
                c: [{
                    t: "div",
                    c: [n, p],
                    r: [i.pClass.extPicnote]
                }],
                r: [i.pClass.extPicnoteWrap]
            };
            a.r && (s.isArray(a.r) || (a.r = [a.r]), s.each(a.r,
            function(a, r) {
                r !== i.pClass.extPicnote && c.r.push(r)
            })),
            s.each(p.r,
            function(a, r) { / ext_picnote_ / .test(r) ? c.c[0].r.push(r) : t.push(r)
            }),
            p.r = t,
            e.beforeParseLegend(c),
            e.parseContent(c)
        },
        beforeParseLegend: function(a) {
            var r = this,
            e = a.c[0],
            t = e.c[0],
            n = e.c[1];
            s.inArray(i.pClass.extPicnoteTop, e.r) > -1 && (e.c = [n, t]),
            a.r.push(i.pClass.extPicnote + "_" + r.chapter + "_" + r.legendIndex++),
            a.r.push(i.pClass.paragraphBlock),
            a.r.push(i.pClass.paragraphBlock + "-" + r.paragraphIndex),
            a.r.push(i.pClass.paragraphBlock + "-" + r.chapter + "-" + r.paragraphIndex),
            a._ylAttr_ = {
                "data-paragraph-index": r.paragraphIndex++
            }
        },
        parseContent: function(a, r) {
            var e = this,
            t = e.html;
            if (!a) return void(e.isPreview || p.send("book", "jsondataerror", {
                docid: e.docid,
                cn: e.chapter,
                pn: e.paragraphIndex - 1,
                level: e.dataLevel,
                type: "dataIsNull"
            }));
            if ("string" === s.type(a)) if (r === !0) t.push(e.parseVertical(a));
            else {
                var n = e.replacePunctuation(a);
                2 === e.dataLevel && (n = "<span>" + n + "</span>"),
                t.push(n)
            } else if (s.isArray(a)) s.each(a,
            function(a, t) {
                e.parseContent(t, r)
            });
            else {
                var c = a.t;
                e.dataLevel++,
                "obj" === c ? t.push(e.parseObj(a)) : -1 !== s.inArray(c, e.selfClosing) ? t.push(e.parseSelfCloseTag(a)) : (a.r = a.r || [], s.isArray(a.r) || (a.r = [a.r]), -1 === s.inArray(i.pClass.extDisplayNone, a.r) && (t.push("<" + c), a["data-remark"] && (a.c = {
                    t: "b",
                    r: ["ic", "ic-remark"],
                    c: ""
                },
                -1 === s.inArray(i.pClass.extRemark, a.r) && a.r.push(i.pClass.extRemark), t.push(e.parseAttr(a["data-remark"], "data-remark"))), t.push(e.parseAttr(a.r, "class")), a._ylAttr_ && s.each(a._ylAttr_,
                function(a, r) {
                    t.push(e.parseAttr(r, a))
                }), t.push(">"), e.parseContent(a.c, !!(r || a.r && s.isArray(a.r) && s.inArray(i.pClass.extVertical, a.r) > -1)), t.push("</" + c + ">"))),
                e.dataLevel--
            }
        },
        parseObj: function(a) {
            var r = this,
            e = "";
            switch (a.datatype) {
            case "note":
                e = r.parseObjNote(a);
                break;
            case "img-note":
                e = r.parseObjImgNote(a)
            }
            return e
        },
        parseObjNote: function(a) {
            var r = "";
            try {
                var e = a.data[0].c;
                r = e ? '<span data-remark="' + e + '" class="ext_remark"><b class="ic ic-remark"></b></span>': ""
            } catch(t) {
                r = ""
            }
            return r
        },
        parseObjImgNote: function(a) {
            var r = this,
            e = "";
            try {
                if (a.data = a.data || [], s.isArray(a.data) || (a.data = [a.data]), a.data.length > 0) {
                    var t = {
                        t: "div",
                        c: a.data,
                        r: [i.pClass.extPicnote]
                    };
                    r.parseContent(t)
                } else e = ""
            } catch(n) {
                e = ""
            }
            return e
        },
        parseVertical: function(a) {
            for (var r, e, t = this,
            s = a.split(""), n = i.pClass.verticalChar, p = "", c = 0, l = s.length; l > c; c++) e = s[c],
            i.verticalTypeset.regex.test(e) ? (t.isIE678 ? t.isIE67 ? p = " style=\"filter: progid:DXImageTransform.Microsoft.Matrix(M11=-1.836909530733566e-16, M12=-1, M21=1, M22=-1.836909530733566e-16, SizingMethod='auto expand');\"": r = " " + i.pClass.verticalSingleCharIE: r = " " + i.pClass.verticalSingleChar, r = " " + i.pClass[t.isIE678 ? "verticalSingleCharIE": "verticalSingleChar"]) : r = "",
            s[c] = '<span class="' + n + " " + n + "-" + c + r + '"' + p + ">" + e + "</span>";
            return s.join("")
        },
        replacePunctuation: function() {
            var a = i.punctuation.singleByte.value,
            r = i.punctuation.doubleByte.value,
            e = i.punctuation.className,
            t = i.punctuation.singleByte.className,
            s = i.punctuation.doubleByte.className;
            return n.browser.ie && n.browser.ie < 8 ?
            function(a) {
                return a
            }: function(n) {
                var p = new RegExp("(&[\\w#]+;)|([" + a + "])", "g"),
                i = new RegExp("[" + r + "]", "g");
                return '<span class="text">' + n.replace(p,
                function(a, r, s) {
                    return r ? r: '</span><span class="' + e + " " + t + '">' + s + '</span><span class="text">'
                }).replace(i,
                function(a) {
                    return '</span><span class="' + e + " " + s + '">' + a + '</span><span class="text">'
                }) + "</span>"
            }
        } (),
        parseSelfCloseTag: function(a) {
            var r, e = this,
            t = "";
            switch (a.t) {
            case "br":
                t = "<br/>",
                "page" === a.datatype && (t = '<span class="page-divide">' + t + "</span>");
                break;
            case "img":
                r = e.imgUrl + ["type=pic", "src=" + a.src].join("&"),
                e.isLocalPreview && (r = a.src.replace(/&amp;/g, "&"));
                var s = i.pClass.img + "-" + e.chapter + "-" + e.imgIndex++,
                n = r,
                p = "",
                c = "",
                l = parseInt(a.h) || 0,
                o = parseInt(a.w) || 0;
                o > e.maxImgWidth && (l = e.maxImgWidth * l / o, o = e.maxImgWidth),
                l > 0 && (p = ' height="' + l + '"'),
                o > 0 && (c = ' width="' + Math.min(e.maxImgWidth, o) + '"', n = !1),
                e.mediator.fire("parse:loadImg", {
                    data: {
                        imgId: s,
                        imgUrl: n
                    }
                }),
                t = '<img id="' + s + '" src="' + r + '" alt="' + (a.alt || "") + '"' + p + c + "/>"
            }
            return t
        },
        parseAttr: function(a, r) {
            return s.isArray(a) && (a = a.join(" ")),
            a ? r && (a = " " + r + '="' + a + '"') : a = "",
            a
        }
    }),
    e.exports.Parser = t
});;
define("ydcore:widget/book_view/txt_reader/js/m/bookmark.model.js",
function(o, e, t) {
    var n = o("ydcommon:widget/ui/lib/jquery/jquery.js"),
    a = o("ydcommon:widget/ui/js_core/mvp/model/model.js").Model,
    r = {};
    r.lang = o("ydcommon:widget/ui/lib/lang/lang.js"),
    r.JSON = o("ydcommon:widget/ui/lib/json/json.js"),
    r.Date = o("ydcommon:widget/ui/lib/date/date.js");
    var i = r.lang.createClass(function(o) {
        this._init(o)
    },
    {
        superClass: a
    }).extend({
        _init: function(o) {
            var e = this;
            n.extend(e, o, {
                api: "/user/submit/bookmarkbdjson",
                OP_ADD: 0,
                OP_DEL: 2
            }),
            e._bindEvent()
        },
        _bindEvent: function() {
            var o = this,
            e = o.Util;
            e.on("m:add-bookmark", o),
            e.on("m:delete-bookmark", o)
        },
        addBookmark: function(o) {
            var e = this,
            t = (e.mediator, o.promise),
            a = o.data || {},
            i = n.extend({},
            a),
            s = [{
                cn: i.cn - 1 || 0,
                pn: (1 == i.cn ? i.pn - 3 : i.pn - 1) || 0,
                offset: i.offset,
                summary: i.summary,
                status: e.OP_ADD
            }],
            d = {
                status: 0,
                bookmarks: []
            };
            return n.ajax({
                url: e.api,
                type: "POST",
                data: {
                    doc_id: e.doc_id,
                    bookmarks: r.JSON.stringify(s)
                },
                cache: !1
            }).done(function(o) {
                0 == o.error_no && (d.bookmarks = e._addLocalBookmark(i), t.resolve(d))
            }),
            t
        },
        deleteBookmark: function(o) {
            var e = this,
            t = (e.mediator, o.promise),
            a = o.data || {},
            i = [],
            s = n.extend(!0, {},
            a.bookmarks),
            d = {
                status: 0,
                bookmarks: []
            };
            return n.each(s,
            function(o, t) {
                i.push({
                    cn: t.cn - 1 || 0,
                    pn: (1 == t.cn ? t.pn - 3 : t.pn - 1) || 0,
                    offset: t.offset,
                    status: e.OP_DEL
                })
            }),
            n.ajax({
                url: e.api,
                type: "POST",
                data: {
                    doc_id: e.doc_id,
                    bookmarks: r.JSON.stringify(i)
                },
                cache: !1
            }).done(function(o) {
                0 == o.error_no && (d.bookmarks = e._delLocalBookmarks(s), t.resolve(d))
            }),
            t
        },
        _delLocalBookmarks: function(o) {
            var e = this,
            t = e.State.bookmarks,
            a = [];
            return n.each(t,
            function(e, t) {
                var r = !0;
                n.each(o,
                function(o, e) {
                    return e.cn == t.cn && e.pn == t.pn && e.offset == t.offset ? r = !1 : void 0
                }),
                r && a.push(t)
            }),
            e.State.bookmarks = a,
            e.State.bookmarks
        },
        _addLocalBookmark: function(o) {
            var e = this,
            t = e.State.bookmarks,
            a = 0;
            return o.create_time = o.update_time = r.Date.format(new Date, "yyyy-MM-dd HH:mm:ss"),
            n.each(t,
            function(e, t) {
                if (t.cn > o.cn) return ! 1;
                if (t.cn == o.cn) {
                    if (t.pn > o.pn) return ! 1;
                    if (t.pn == o.pn && t.offset >= o.offset) return ! 1
                }
                a = e + 1
            }),
            t.splice(a, 0, o),
            t
        }
    });
    t.exports.BookMarkModel = i
});;
define("ydcore:widget/book_view/txt_reader/js/m/chapter.model.js",
function(e, t, r) {
    var a = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcommon:widget/ui/js_core/mvp/model/model.js").Model,
    o = e("ydcore:widget/book_view/txt_reader/js/chapter/chapter.js").Chapter,
    c = {};
    c.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var s = c.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: i
    }).extend({
        _init: function(e) {
            var t = this;
            a.extend(t, e, {
                lastPayPrice: (t.payprice / 100).toFixed(2),
                _promisePool: {},
                _chapterCache: {},
                _ffkan_prefix: "block-",
                _prefix: "chapter-"
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = e.mediator,
            r = e.Util;
            r.on("m:get-chapter-cache", e),
            r.on("m:set-chapter-cache", e),
            r.on("m:get-chapter-html", e),
            r.on("chapter:chapter-ready", e),
            r.on("chapter:json-fail", e),
            t.when(["tools:get-last-pay-price"]).then(function(t) {
                e.lastPayPrice = t[0]
            })
        },
        _getPrefix: function(e) {
            var t = this;
            return e = e || {},
            e.isFfkan === !0 ? t._ffkan_prefix: t._prefix
        },
        jsonFail: function(e) {
            var t = this,
            r = e.data || {},
            a = r.chapterNum,
            i = t._getPrefix(r),
            o = t._promisePool[i + a];
            o && (t._promisePool[i + a] = null, delete t._promisePool[i + a], o.resolve("fail"))
        },
        chapterReady: function(e) {
            var t = this,
            r = e.data || {},
            a = r.cache,
            i = a.chapterNum,
            o = t._getPrefix(r);
            t.chapterCache(o, i, a);
            var c = t._promisePool[o + i];
            c && (t._promisePool[o + i] = null, delete t._promisePool[o + i], c.resolve(a))
        },
        getChapterCache: function(e) {
            var t = this,
            r = e.data,
            a = e.promise,
            i = t._getPrefix(r),
            o = r.chapterNum,
            c = t.chapterCache(i, o);
            return a.resolve(c || null),
            a
        },
        setChapterCache: function(e) {
            var t = this,
            r = e.data,
            a = t._getPrefix(r),
            i = r.chapterNum,
            o = r.chapterCache;
            t.chapterCache(a, i, o)
        },
        getChapterHtml: function(e) {
            var t = this,
            r = e.data,
            i = e.promise,
            c = t._getPrefix(r),
            s = r.chapterNum,
            n = t.chapterCache(c, s);
            if (n) i.resolve(n);
            else {
                var h = t._promisePool[c + s];
                if (h) return t._promisePool[c + s] = i,
                h.resolve("loading"),
                i;
                t._promisePool[c + s] = i;
                var l = {
                    coverPageInfo: t.coverPageInfo,
                    defaultWidth: t.defaultWidth,
                    $doc: t.$doc,
                    isIE67: t.isIE67,
                    isIE678: t.isIE678,
                    isLast: s >= t.freeChapterNum,
                    isQADoc: t.isQADoc,
                    isPreview: t.isPreview,
                    isLocalPreview: t.isLocalPreview,
                    perBlockCount: t.perBlockCount,
                    doc_id: t.doc_id,
                    bdjsonUrl: t.bdjsonUrl,
                    outMediator: t.mediator,
                    chapterNum: s,
                    ispaied: t.ispaied
                };
                r.isFfkan === !0 && a.extend(l, {
                    coverPageInfo: {
                        hasCoverPage: !1
                    },
                    lastPayPrice: t.lastPayPrice,
                    isFfkan: !0,
                    isLast: !0
                }); {
                    new o(l)
                }
            }
            return i
        },
        chapterCache: function(e, t, r) {
            var a = this;
            return r ? void(a._chapterCache[e + t] = r) : a._chapterCache[e + t]
        }
    });
    r.exports.ChapterModel = s
});;
define("ydcore:widget/book_view/txt_reader/js/m/progress.model.js",
function(e, n, s) {
    var r = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcommon:widget/ui/js_core/mvp/model/model.js").Model,
    t = {};
    t.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var a = t.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: i
    }).extend({
        _init: function(e) {
            var n = this,
            s = 1e4;
            r.extend(n, e, {
                sendTime: new Date,
                firstTimeSpan: s,
                otherTimeSpan: 3e4,
                timeSpan: s,
                _sendTimer: null,
                progressData: {
                    cn: e.toPage - 1,
                    pn: e.paragraphNum - 1,
                    offset: e.paragraphOffset
                }
            }),
            n._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            n = (e.mediator, e.Util);
            n.on("m:save-read-progress", e),
            n.on("m:send-read-progress", e)
        },
        saveReadProgress: function(e) {
            var n = this,
            s = e.data || {};
            if (!n.isPreview) {
                n._sendTimer && (clearTimeout(n._sendTimer), n._sendTimer = null);
                var r = s.cn - 1,
                i = s.pn - 1,
                t = s.offset;
                if (r !== n.progressData.cn || i !== n.progressData.pn || t !== n.progressData.offset) {
                    n.progressData = {
                        cn: r,
                        pn: i,
                        offset: t
                    };
                    var a = new Date,
                    o = a - n.sendTime;
                    if (o < n.timeSpan) return void(n._sendTimer = setTimeout(function() {
                        n.send(!0),
                        n._sendTimer = null
                    },
                    n.timeSpan));
                    n.timeSpan = n.otherTimeSpan,
                    n.sendTime = a,
                    n.send()
                }
            }
        },
        sendReadProgress: function() {
            var e = this;
            e.isPreview || e.send()
        },
        send: function(e) {
            var n = this;
            e === !0 && (n.timeSpan = n.otherTimeSpan, n.sendTime = new Date);
            var s = {
                version: 2,
                docid: n.doc_id,
                cn: n.progressData.cn,
                pn: n.progressData.pn,
                offset: n.progressData.offset
            };
            0 === s.cn && (s.pn = Math.max(0, s.pn - 2)),
            r.get("/user/submit/bookreadposition", s)
        }
    });
    s.exports.ProgressModel = a
});;
define("ydcore:widget/book_view/txt_reader/js/m/selecttext.model.js",
function(e, t, n) {
    var i = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    o = e("ydcommon:widget/ui/js_core/mvp/model/model.js").Model,
    d = {};
    d.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var l = d.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: o
    }).extend({
        _init: function(e) {
            var t = this;
            i.extend(t, e),
            t._bindEvent()
        },
        _bindEvent: function() {}
    });
    n.exports.SelectTextModel = l
});;
define("ydcore:widget/book_view/txt_reader/js/m/note.model.js",
function(t, o, e) {
    var i = t("ydcommon:widget/ui/lib/jquery/jquery.js"),
    s = t("ydcommon:widget/ui/js_core/mvp/model/model.js").Model,
    n = t("ydcommon:widget/ui/js_core/log/log.js"),
    a = {};
    a.lang = t("ydcommon:widget/ui/lib/lang/lang.js"),
    a.JSON = t("ydcommon:widget/ui/lib/json/json.js"),
    a.Date = t("ydcommon:widget/ui/lib/date/date.js"),
    a.string = t("ydcommon:widget/ui/lib/string/string.js");
    var c = a.lang.createClass(function(t) {
        this._init(t)
    },
    {
        superClass: s
    }).extend({
        _init: function(t) {
            var o = this;
            i.extend(o, t, {
                API: "/user/submit/booknote",
                OP_ADD: 0,
                OP_MOD: 1,
                OP_DEL: 2,
                strLimit: 500,
                dataTpl: {
                    bfi: 0,
                    bpi: 0,
                    bci: 0,
                    efi: 0,
                    epi: 0,
                    eci: 0,
                    summary: "",
                    customstr: "",
                    style: "",
                    status: 0,
                    create_time: 0,
                    update_time: 0
                }
            }),
            o._bindEvent()
        },
        _bindEvent: function() {
            var t = this,
            o = t.Util;
            o.on("m:add-booknotes", t),
            o.on("m:del-booknotes", t),
            o.on("m:mod-booknotes", t)
        },
        addBooknotes: function(t) {
            var o = this,
            e = (o.mediator, t.promise),
            s = t.data || {},
            n = s.booknotes,
            c = {
                status: 0
            },
            r = [];
            return i.each(n,
            function(t, e) {
                r.push({
                    bfi: e.bfi - 1 || 0,
                    bpi: (1 == e.bfi ? e.bpi - 3 : e.bpi - 1) || 0,
                    bci: e.bci || 0,
                    efi: e.efi - 1 || 0,
                    epi: (1 == e.efi ? e.epi - 3 : e.epi - 1) || 0,
                    eci: e.eci || 0,
                    summary: encodeURIComponent(e.summary || ""),
                    customstr: encodeURIComponent(a.string.cut(e.customstr || "", o.strLimit)),
                    style: i.isEmptyObject(e.style) ? '{"noteColor": 0}': a.JSON.stringify(e.style),
                    status: o.OP_ADD
                })
            }),
            i.ajax({
                url: o.API,
                type: "POST",
                data: {
                    doc_id: o.doc_id,
                    booknotes: a.JSON.stringify(r)
                },
                cache: !1
            }).done(function(t) {
                0 == t.error_no && (o._addLocalBooknotes(n), e.resolve(c), o._log(n))
            }),
            e
        },
        delBooknotes: function(t) {
            var o = this,
            e = (o.mediator, t.promise),
            s = t.data || {},
            n = s.booknotes,
            c = {
                status: 0
            },
            r = [];
            return i.each(n,
            function(t, e) {
                r.push({
                    bfi: e.bfi - 1 || 0,
                    bpi: (1 == e.bfi ? e.bpi - 3 : e.bpi - 1) || 0,
                    bci: e.bci || 0,
                    efi: e.efi - 1 || 0,
                    epi: (1 == e.efi ? e.epi - 3 : e.epi - 1) || 0,
                    eci: e.eci || 0,
                    status: o.OP_DEL
                })
            }),
            i.ajax({
                url: o.API,
                type: "POST",
                data: {
                    doc_id: o.doc_id,
                    booknotes: a.JSON.stringify(r)
                },
                cache: !1
            }).done(function(t) {
                0 == t.error_no && (o._delLocalBooknotes(n), e.resolve(c))
            }),
            e
        },
        modBooknotes: function(t) {
            var o = this,
            e = (o.mediator, t.promise),
            s = t.data || {},
            n = s.booknotes,
            c = {
                status: 0
            },
            r = [];
            return i.each(n,
            function(t, e) {
                r.push({
                    bfi: e.bfi - 1 || 0,
                    bpi: (1 == e.bfi ? e.bpi - 3 : e.bpi - 1) || 0,
                    bci: e.bci || 0,
                    efi: e.efi - 1 || 0,
                    epi: (1 == e.efi ? e.epi - 3 : e.epi - 1) || 0,
                    eci: e.eci || 0,
                    summary: encodeURIComponent(e.summary || ""),
                    customstr: encodeURIComponent(a.string.cut(e.customstr || "", o.strLimit)),
                    style: i.isEmptyObject(e.style) ? '{"noteColor": 0}': a.JSON.stringify(e.style),
                    status: o.OP_MOD
                })
            }),
            i.ajax({
                url: o.API,
                type: "POST",
                data: {
                    doc_id: o.doc_id,
                    booknotes: a.JSON.stringify(r)
                },
                cache: !1
            }).done(function(t) {
                0 == t.error_no && (o._modLocalBooknotes(n), e.resolve(c), o._log(n))
            }),
            e
        },
        _addLocalBooknotes: function(t) {
            var o = this,
            e = o.State.booknotes;
            return i.each(t,
            function(t, s) {
                s.create_time = s.update_time = a.Date.format(new Date, "yyyy-MM-dd HH:mm:ss"),
                s.key = [s.bfi, s.bpi, s.bci, s.efi, s.epi, s.eci].join("-"),
                s.style = i.isEmptyObject(s.style) ? {
                    noteColor: 0
                }: s.style,
                s.summary = s.summary || "",
                s.customstr = a.string.cut(s.customstr || "", o.strLimit),
                e.push(s)
            }),
            o.State.booknotes = o.noteUtil.quickSort(e, o.noteUtil.compareStartPoint),
            o.State.booknotes
        },
        _delLocalBooknotes: function(t) {
            var o = this,
            e = o.State.booknotes,
            s = [],
            n = [];
            return i.each(t,
            function(t, o) {
                s.push(o.key)
            }),
            i.each(e,
            function(t, o) {
                i.inArray(o.key, s) >= 0 || n.push(o)
            }),
            o.State.booknotes = n,
            o.State.booknotes
        },
        _modLocalBooknotes: function(t) {
            var o = this,
            e = t[0],
            s = o.State.booknotes;
            return i.each(s,
            function(t, n) {
                n.key == e.key && (s[t].style = i.isEmptyObject(e.style) ? {
                    noteColor: 0
                }: e.style, s[t].customstr = a.string.cut(e.customstr || "", o.strLimit), s[t].update_time = a.Date.format(new Date, "yyyy-MM-dd HH:mm:ss"))
            }),
            o.State.booknotes = s,
            o.State.allBooknotes
        },
        _log: function(t) {
            i.each(t,
            function(t, o) {
                o.customstr ? n.xsend(2, 200267) : n.xsend(2, 200266)
            })
        }
    });
    e.exports.NoteModel = c
});;
define("ydcore:widget/book_view/txt_reader/js/m/model.js",
function(e, o, t) {
    var d = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    r = e("ydcommon:widget/ui/js_core/mvp/model/model.js").Model,
    i = e("ydcore:widget/book_view/txt_reader/js/m/chapter.model.js").ChapterModel,
    s = e("ydcore:widget/book_view/txt_reader/js/m/progress.model.js").ProgressModel,
    n = e("ydcore:widget/book_view/txt_reader/js/m/selecttext.model.js").SelectTextModel,
    l = e("ydcore:widget/book_view/txt_reader/js/m/note.model.js").NoteModel,
    m = e("ydcore:widget/book_view/txt_reader/js/m/bookmark.model.js").BookMarkModel,
    a = {};
    a.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var w = a.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: r
    }).extend({
        _init: function(e) {
            var o = this;
            d.extend(o, e, {
                _modelList: []
            }),
            o._modelList.push(new i(e), new s(e), new n(e), new l(e), new m(e))
        }
    }),
    c = function(e) {
        var o = new w(e);
        return o
    };
    t.exports.ModelList = c
});;
define("ydcore:widget/book_view/txt_reader/js/p/common/bookmark.presenter.js",
function(e, o, n) {
    var t = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    r = e("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    a = {};
    a.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var s = a.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: r
    }).extend({
        _init: function(e) {
            var o = this;
            t.extend(o, e, {
                curPageBookmarks: []
            }),
            o._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            o = (e.mediator, e.Util);
            o.on("p:add-bookmark", e),
            o.on("p:delete-bookmark", e),
            o.on("p:get-bookmarks", e),
            o.on("p:paragraph-change", e)
        },
        addBookmark: function(e) {
            var o = this,
            n = o.mediator,
            t = e.promise,
            r = e.data || {},
            a = {
                status: 0
            };
            return n.fire("m:add-bookmark", {
                data: {
                    cn: r.cn || 0,
                    pn: r.pn || 0,
                    offset: r.offset || 0,
                    summary: r.summary || ""
                }
            }).then(function(e) {
                0 == e.status && (t.resolve(a), n.fire("v:bookmark-change", {
                    data: {
                        bookmarks: o.State.bookmarks
                    }
                }))
            }),
            t
        },
        deleteBookmark: function(e) {
            var o = this,
            n = o.mediator,
            t = e.promise,
            r = e.data || {},
            a = {
                status: 0
            },
            s = [];
            return r.bookmark ? s.push(r.bookmark) : s = o.curPageBookmarks,
            n.fire("m:delete-bookmark", {
                data: {
                    bookmarks: s
                }
            }).then(function(e) {
                0 == e.status && (t.resolve(a), n.fire("v:bookmark-change", {
                    data: {
                        bookmarks: o.State.bookmarks
                    }
                }))
            }),
            t
        },
        getBookmarks: function(e) {
            var o = this,
            n = e.promise,
            t = e.data || {},
            r = [];
            return r = t.visible ? o._getCurViewBookmarks() : o._getAllBookMarks(),
            n.resolve(r),
            n
        },
        paragraphChange: function() {
            var e = this,
            o = e.mediator;
            o.fire("v:paragraph-change")
        },
        _getAllBookMarks: function() {
            var e = this;
            return e.State.bookmarks || []
        },
        _getCurViewBookmarks: function() {
            var e = this,
            o = e.State,
            n = o.bookmarks || [],
            r = {
                cn: o.curChapterNum,
                pn: o.curParagraphIndex,
                offset: o.curParagraphOffset
            },
            a = {
                cn: o.curBottomInfo.chapterNum,
                pn: o.curBottomInfo.paragraphIndex,
                offset: o.curBottomInfo.offset
            },
            s = o.curBottomInfo.isInPage,
            f = [];
            return t.each(n,
            function(e, o) {
                if (a.cn < o.cn) return ! 1;
                if (r.cn < o.cn && o.cn < a.cn) f.push(n[e]);
                else if (r.cn == o.cn && o.cn == a.cn) {
                    if (a.pn < o.pn) return ! 1;
                    if (r.pn < o.pn && o.pn < a.pn && f.push(n[e]), r.pn == o.pn && o.pn == a.pn) {
                        if (a.offset < o.offset) return ! 1;
                        r.offset <= o.offset && o.offset <= a.offset && (o.offset == a.offset ? s && f.push(n[e]) : f.push(n[e]))
                    } else if (r.pn == o.pn && o.pn < a.pn) o.offset >= r.offset && f.push(n[e]);
                    else if (a.pn == o.pn) {
                        if (! (o.offset <= a.offset)) return ! 1;
                        s && f.push(n[e])
                    }
                } else r.cn == o.cn && o.cn < a.cn ? r.pn < o.pn ? f.push(n[e]) : r.pn == o.pn && r.offset <= o.offset && f.push(n[e]) : a.cn == o.cn && (o.pn < a.pn ? f.push(n[e]) : a.pn == o.pn && o.offset < a.offset && f.push(n[e]))
            }),
            e.curPageBookmarks = f,
            f
        }
    });
    n.exports.BookmarkPresenter = s
});;
define("ydcore:widget/book_view/txt_reader/js/p/common/progress.presenter.js",
function(e, r, s) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    t = e("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    i = {};
    i.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var o = i.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: t
    }).extend({
        _init: function(e) {
            var r = this;
            n.extend(r, e),
            r._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            r = (e.mediator, e.Util);
            r.on("p:save-read-progress", e)
        },
        saveReadProgress: function(e) {
            var r = this,
            s = e.data || {};
            r.mediator.fire("m:save-read-progress", {
                data: s
            })
        }
    });
    s.exports.ProgressPresenter = o
});;
define("ydcore:widget/book_view/txt_reader/js/p/common/selecttext.presenter.js",
function(e, n, t) {
    var i = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    r = e("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    s = {};
    s.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var o = s.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: r
    }).extend({
        _init: function(e) {
            var n = this;
            i.extend(n, e),
            n._bindEvent()
        },
        _bindEvent: function() {}
    });
    t.exports.SelectTextPresenter = o
});;
define("ydcore:widget/book_view/txt_reader/js/p/common/note.presenter.js",
function(e, t, o) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    s = e("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    i = {};
    i.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var r = i.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: s
    }).extend({
        _init: function(e) {
            var t = this;
            n.extend(t, e),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            t.on("p:show-view-notes", e),
            t.on("p:clear-notes-state", e),
            t.on("p:add-note", e),
            t.on("p:del-note", e),
            t.on("p:get-notes-in-boundary", e)
        },
        showViewNotes: function(e) {
            var t = this,
            o = e.promise,
            s = t.mediator,
            i = (e.data || {},
            t.State.boundary || {}),
            r = [];
            return n.isEmptyObject(i) || t.isPreview || "ffkan" == t.State.currentType || (r = t._getBoundaryBooknotes(i, !0), s.fire("v:show-view-notes", {
                data: {
                    notes: r
                }
            })),
            o.resolve(r),
            o
        },
        clearNotesState: function(e) {
            {
                var t = this,
                o = (e.promise, t.mediator);
                e.data || {}
            }
            o.fire("v:clear-notes-state")
        },
        addNote: function(e) {
            var t = this,
            o = t.mediator,
            n = e.promise,
            s = e.data || {},
            i = s.boundary || {},
            r = s.style || {
                noteColor: 0
            },
            a = s.customstr || "",
            u = s.changeCustomstr,
            d = t._getBoundaryBooknotes(s.boundary, !1),
            c = [];
            return 0 == d.length ? (c.push(i), c[0].style = r, o.when(["v:get-range-text", {
                data: {
                    notes: c
                }
            }]).then(function(e) {
                c[0].summary = e[0].summary,
                o.when(["m:add-booknotes", {
                    data: {
                        booknotes: c
                    }
                }]).then(function(e) {
                    0 == e[0].status && (o.fire("p:show-view-notes"), o.fire("v:booknote-change", {
                        data: {
                            booknotes: t.State.booknotes
                        }
                    }), n.resolve(c))
                })
            })) : 1 == d.length && t.noteUtil.isSamePosNote(d[0], i) ? (d[0].style = r, d[0].customstr = u ? a: d[0].customstr, c.push(d[0]), o.when(["m:mod-booknotes", {
                data: {
                    booknotes: c
                }
            }]).then(function(e) {
                0 == e[0].status && (o.fire("p:show-view-notes"), o.fire("v:booknote-change", {
                    data: {
                        booknotes: t.State.booknotes
                    }
                }), n.resolve(c))
            })) : (c.push(t._mergeNotes(d, i, r, !0)), o.when(["v:get-range-text", {
                data: {
                    notes: c
                }
            }]).then(function(e) {
                c[0].summary = e[0].summary,
                o.when(["m:del-booknotes", {
                    data: {
                        booknotes: d
                    }
                }]).then(function(e) {
                    0 == e[0].status && o.when(["m:add-booknotes", {
                        data: {
                            booknotes: c
                        }
                    }]).then(function(e) {
                        0 == e[0].status && (o.fire("p:show-view-notes"), o.fire("v:booknote-change", {
                            data: {
                                booknotes: t.State.booknotes
                            }
                        }), n.resolve(c))
                    })
                })
            })),
            n
        },
        delNote: function(e) {
            var t = this,
            o = t.mediator,
            s = e.promise,
            i = e.data || {},
            r = i.noteKey,
            a = t.State.booknotes,
            u = [];
            return n.each(a,
            function(e, t) {
                return t.key === r ? (u.push(n.extend({},
                t)), !1) : void 0
            }),
            u.length > 0 ? o.when(["m:del-booknotes", {
                data: {
                    booknotes: u
                }
            }]).then(function() {
                o.fire("p:show-view-notes"),
                o.fire("v:booknote-change", {
                    data: {
                        booknotes: t.State.booknotes
                    }
                }),
                s.resolve()
            }) : s.resolve(),
            s
        },
        getNotesInBoundary: function(e) {
            var t = this,
            o = (t.mediator, e.promise),
            n = e.data || {},
            s = n.boundary,
            i = [];
            return i = t._getBoundaryBooknotes(s, !1),
            o.resolve(i),
            o
        },
        _getBoundaryBooknotes: function(e, t) {
            var o = this,
            s = o.noteUtil,
            i = o.State.booknotes,
            r = [];
            return n.each(i,
            function(i, a) {
                var u = {},
                d = {},
                c = {},
                f = !1,
                h = s.getPonitByPrefix(e, "b"),
                m = s.getPonitByPrefix(e, "e"),
                b = s.getPonitByPrefix(a, "b"),
                l = s.getPonitByPrefix(a, "e"),
                p = s.comparePoint(b, h),
                g = s.comparePoint(l, h),
                y = s.comparePoint(b, m),
                v = s.comparePoint(l, m);
                if (0 >= p && 0 == g) f = !0,
                d = n.extend({},
                a),
                c = o._getBoundary(b, h);
                else if (0 >= p && 1 == g) f = !0,
                d = n.extend({},
                a),
                c = o._getBoundary(b, l);
                else if (1 == p && 0 >= v) f = !0,
                d = n.extend({},
                a),
                c = o._getBoundary(b, l);
                else if (1 == p && 1 == v && -1 == y) f = !0,
                d = n.extend({},
                a),
                c = o._getBoundary(b, l);
                else if (1 == p && 1 == v && 0 == y) f = !0,
                d = n.extend({},
                a),
                c = o._getBoundary(b, m);
                else if (1 == y && 1 == v) return ! 1;
                f && (t ? (u.ori = d, u.page = c) : u = a, r.push(u))
            }),
            r
        },
        _mergeNotes: function(e, t, o, s) {
            var i = this,
            r = i.noteUtil,
            a = [].concat(e, [t]),
            u = {},
            d = [],
            c = "",
            f = {},
            h = 1,
            m = {};
            return a = r.quickSort(a, r.compareStartPoint),
            u.bfi = a[0].bfi,
            u.bpi = a[0].bpi,
            u.bci = a[0].bci,
            s && (n.each(a,
            function(e, t) {
                t.customstr && t.customstr.length > 0 && (d.push(t.customstr), h++)
            }), d.length > 0 && (c = d.join("\n"))),
            n.isEmptyObject(o) ? n.each(a,
            function(e, t) {
                return n.isEmptyObject(t.style) ? void 0 : (f = n.extend({},
                t.style), !1)
            }) : f = o,
            a = r.quickSort(a, r.compareEndPoint),
            u.efi = a[a.length - 1].efi,
            u.epi = a[a.length - 1].epi,
            u.eci = a[a.length - 1].eci,
            m = u,
            m.customstr = c,
            m.style = f,
            m
        },
        _getBoundary: function(e, t) {
            var o = {};
            return o.bfi = e.cn,
            o.bpi = e.pn,
            o.bci = e.offset,
            o.efi = t.cn,
            o.epi = t.pn,
            o.eci = t.offset,
            o
        }
    });
    o.exports.NotePresenter = r
});;
define("ydcore:widget/book_view/txt_reader/js/p/common/common.presenter.js",
function(e, r, t) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    o = e("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    s = e("ydcore:widget/book_view/txt_reader/js/p/common/bookmark.presenter.js").BookmarkPresenter,
    i = e("ydcore:widget/book_view/txt_reader/js/p/common/progress.presenter.js").ProgressPresenter,
    d = e("ydcore:widget/book_view/txt_reader/js/p/common/selecttext.presenter.js").SelectTextPresenter,
    m = e("ydcore:widget/book_view/txt_reader/js/p/common/note.presenter.js").NotePresenter,
    c = {};
    c.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var p = c.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: o
    }).extend({
        _init: function(e) {
            var r = this;
            n.extend(r, e, {
                _presenterList: []
            }),
            r._presenterList.push(new i(e), new d(e), new s(e), new m(e)),
            r._bindEvent()
        },
        _bindEvent: function() {}
    });
    t.exports.Common = p
});;
define("ydcore:widget/book_view/txt_reader/js/p/ffkan/chapter.presenter.js",
function(e, t, a) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    r = e("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    i = {};
    i.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var o = i.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: r
    }).extend({
        _init: function(e) {
            var t = this;
            n.extend(t, e, {}),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            t.on("p:load-chapter", e)
        },
        loadChapter: function(e) {
            var t = this,
            a = t.mediator,
            n = e.data || {},
            r = e.promise,
            i = n.chapterNum,
            o = n.progressData;
            return t.checkChapterNum(i) ? (a.when(["m:get-chapter-html", {
                data: {
                    isFfkan: !0,
                    chapterNum: i
                }
            }], ["v:clear-text-container"], ["v:loading-chapter"]).then(function(e) {
                var n = e[0];
                if ("loading" === n) return r.resolve(!1),
                void console.log("loading", new Date);
                if ("fail" === n) return a.fire("v:loading-fail", {
                    data: {
                        chapterNum: i
                    }
                }),
                void r.resolve(!1);
                try {
                    n.ffkanData.title && a.fire("v:set-ffkan-title", {
                        data: {
                            title: n.ffkanData.title
                        }
                    });
                    var s = n.ffkanData.begin.split("-"),
                    c = n.ffkanData.end.split("-");
                    t.State.curFfkanRange = {
                        beginCn: +s[0],
                        beginPn: +s[1],
                        endCn: +c[0],
                        endPn: +c[1]
                    }
                } catch(d) {}
                a.fire("v:insert-chapter", {
                    data: {
                        chapterCache: n
                    }
                }),
                t.State.curFfkanNum = i,
                o ? a.when(["v:direct-scroll-to-chapter", {
                    data: o
                }]).then(function() {
                    r.resolve()
                }) : r.resolve()
            }), r) : (r.resolve(!1), r)
        },
        checkChapterNum: function(e) {
            var t = this;
            return e > 0 && e <= t.ffkanData.blockCount
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            t.off(e),
            t.dispose(e)
        }
    });
    a.exports.ChapterPresenter = o
});;
define("ydcore:widget/book_view/txt_reader/js/p/ffkan/ffkan.presenter.js",
function(e, t, r) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    a = e("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    s = e("ydcore:widget/book_view/txt_reader/js/p/ffkan/chapter.presenter.js").ChapterPresenter,
    i = {};
    i.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var d = i.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: a
    }).extend({
        _init: function(e) {
            var t = this;
            window.__yueduSparkHide = !0,
            n.extend(t, e, {
                _presenterList: []
            }),
            t._presenterList.push(new s(e)),
            t.render()
        },
        render: function() {
            var e = this,
            t = e.mediator;
            t.fire("v:window-resize");
            var r = {
                chapterNum: Math.max(1, e.State.curFfkanNum) || 1
            }; (r.chapterNum > 1 || e.State.curFfkanPn > 1 || e.State.curFfkanOffset > 1) && (r.progressData = {
                pn: e.State.curFfkanPn,
                offset: e.State.curFfkanOffset
            }),
            t.fire("p:load-chapter", {
                data: r
            }),
            t.fire("p:send-ffkan-log", {
                data: {
                    act_id: "200205",
                    pageturn: e.isOnlyFfkan ? 1 : 2
                }
            })
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            n.each(e._presenterList,
            function(e, t) {
                t.disposed || t.dispose()
            }),
            t.dispose(e)
        }
    });
    r.exports.Ffkan = d
});;
define("ydcore:widget/book_view/txt_reader/js/p/format/chapter.presenter.js",
function(t, e, a) {
    var r = t("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = t("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    o = {};
    o.lang = t("ydcommon:widget/ui/lib/lang/lang.js");
    var n = o.lang.createClass(function(t) {
        this._init(t)
    },
    {
        superClass: i
    }).extend({
        _init: function(t) {
            var e = this;
            r.extend(e, t, {
                token: 0,
                isFirst: !0,
                upLoading: !1,
                downLoading: !1
            }),
            e._bindEvent()
        },
        _bindEvent: function() {
            var t = this,
            e = (t.mediator, t.Util);
            e.on("p:first-load", t),
            e.on("p:pre-load", t),
            e.on("p:scroll-to-chapter", t)
        },
        scrollToChapter: function(t) {
            var e, a = this,
            i = t.data || {},
            o = i.chapterNum,
            n = i.progress,
            s = i.offset || 0,
            d = i.paragraphNum,
            p = n === e && d > 0,
            h = {
                chapterNum: o,
                progress: p ? d: n,
                offset: s,
                isParagraph: p
            },
            c = a.State.showingChapterNumList;
            r.inArray(o, c) > -1 ? a.mediator.fire("v:direct-scroll-to-chapter", {
                data: h
            }) : (a.mediator.fire("v:clear-text-container"), a.mediator.fire("p:first-load", {
                data: h
            }))
        },
        preLoad: function(t) {
            var e = this,
            a = t.data || {},
            r = e.State.showingChapterNumList;
            a.prev && r[0] && e.loadChapter({
                data: {
                    chapterNum: r[0] - 1,
                    direction: "up"
                }
            }),
            a.next && r[r.length - 1] && e.loadChapter({
                data: {
                    chapterNum: r[r.length - 1] + 1,
                    direction: "down"
                }
            })
        },
        firstLoad: function(t) {
            var e = this,
            a = t.data || {};
            e.token++;
            var i = r.extend({},
            a),
            o = a.chapterNum || e.State.curChapterNum,
            n = "down";
            e.State.showingChapterNumList = [],
            e.upLoading = !1,
            e.downLoading = !1,
            e.loadChapter({
                data: {
                    chapterNum: o,
                    direction: n,
                    progressData: i,
                    isFirst: !0
                }
            })
        },
        loadChapter: function(t) {
            var e = this,
            a = e.mediator,
            i = t.data || {},
            o = i.isFirst === !0,
            n = i.chapterNum,
            s = i.direction,
            d = s + "Loading",
            p = i.progressData || {};
            if (!e[d] && e.checkChapterNum(n)) {
                o ? (e.upLoading = !0, e.downLoading = !0) : e[d] = !0;
                var h = e.token;
                a.when(["m:get-chapter-html", {
                    data: {
                        chapterNum: n
                    }
                }], ["v:loading-chapter", {
                    data: {
                        direction: s
                    }
                }]).then(function(t) {
                    var i = t[0];
                    if ("loading" !== i) {
                        if ("fail" === i) return void a.fire("v:loading-fail", {
                            data: {
                                direction: s
                            }
                        });
                        if (h === e.token) { - 1 === r.inArray(n, e.State.showingChapterNumList) && a.fire("v:insert-chapter", {
                                data: {
                                    chapterCache: i,
                                    direction: s
                                }
                            });
                            var c = "push";
                            "up" === s && (c = "unshift"),
                            -1 === r.inArray(n, e.State.showingChapterNumList) && e.State.showingChapterNumList[c](n);
                            var u = !1;
                            e.State.isFirst && (4 != e.State.savePosFrom && (u = !0), e.State.isFirst = !1, alog && alog("speed.set", "c_flc", +new Date), alog && alog.fire && alog.fire("mark"), a.fire("v:prevent-default-scroll")),
                            e.isFirst && (e.isFirst = !1),
                            o ? (e.upLoading = !1, e.downLoading = !1, a.fire("p:set-page-scroll-state")) : e[d] = !1,
                            a.fire("v:load-and-remove"),
                            p.progress && (a.fire("v:direct-scroll-to-chapter", {
                                data: p
                            }), u && a.fire("v:show-position-tip", {
                                data: p,
                                ext: {
                                    from: e.State.savePosFrom,
                                    time: e.State.savePosTime
                                }
                            }))
                        }
                    }
                })
            }
        },
        checkChapterNum: function(t) {
            var e = this;
            return t > 0 && t <= e.freeChapterNum
        },
        dispose: function() {
            var t = this,
            e = t.Util;
            e.off(t),
            e.dispose(t)
        }
    });
    a.exports.ChapterPresenter = n
});;
define("ydcore:widget/book_view/txt_reader/js/p/format/pager.presenter.js",
function(e, t, r) {
    var i = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    o = e("ydcommon:widget/ui/js_core/log/log.js"),
    s = e("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    a = {};
    a.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var n = a.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: s
    }).extend({
        _init: function(e) {
            var t = this;
            i.extend(t, e),
            i.extend(t, {
                readProgressDivisor: 1 / t.totalChapterNum,
                viewProgressDivisor: 1 / t.freeChapterNum,
                logTime: null
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            t.on("p:get-progress", e),
            t.on("p:set-page-scroll-state", e)
        },
        sendViewLog: function() {
            var e = this;
            if (!e.isPreview) {
                if (!e.logTime) return void(e.logTime = new Date);
                var t = new Date,
                r = t - e.logTime;
                if (! (2e3 > r)) {
                    e.logTime = t;
                    var i = {
                        doc_id: e.doc_id,
                        timeSpan: r,
                        cn: e.State.curChapterNum,
                        allp: e.allPCount,
                        docType: e.type,
                        version: 1
                    };
                    o.send("book", "view", i)
                }
            }
        },
        getProgress: function(e) {
            var t = this,
            r = t.mediator,
            i = e.data,
            o = i.chapterNum,
            s = i.curChapterPageCount,
            a = i.curPageIndex,
            n = Math.min(1, Math.max(5e-4, a - 1) / s);
            n = o - 1 + n,
            n *= t.viewProgressDivisor;
            var d = Math.min(1, a / s);
            d = o - 1 + d,
            d *= t.viewProgressDivisor,
            r.fire("v:set-read-progress", {
                data: {
                    progress: n
                }
            }),
            r.fire("v:set-view-progress", {
                data: {
                    top: n,
                    bottom: d
                }
            })
        },
        setPageScrollState: function(e) {
            var t = this,
            r = e.data || {},
            i = r.isTop === !0,
            o = r.isBottom === !0;
            t.mediator.fire("v:set-page-scroll-state", {
                data: {
                    isTop: i,
                    isBottom: o
                }
            })
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            t.off(e),
            t.dispose(e)
        }
    });
    r.exports.PagerPresenter = n
});;
define("ydcore:widget/book_view/txt_reader/js/p/format/scrollbar.presenter.js",
function(r, e, t) {
    var i = r("ydcommon:widget/ui/lib/jquery/jquery.js"),
    s = r("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    o = {};
    o.lang = r("ydcommon:widget/ui/lib/lang/lang.js");
    var a = o.lang.createClass(function(r) {
        this._init(r)
    },
    {
        superClass: s
    }).extend({
        _init: function(r) {
            var e = this;
            i.extend(e, r),
            e._bindEvent()
        },
        _bindEvent: function() {
            var r = this,
            e = (r.mediator, r.Util);
            e.on("p:scrollbar-scrolling", r),
            e.on("p:scrollbar-scroll-stop", r)
        },
        scrollbarScrolling: function(r) {
            var e = this,
            t = r.data || {},
            s = t.chapterNum,
            o = t.progress,
            a = e.State.showingChapterNumList; - 1 === i.inArray(s, a) && (s < a[0] ? (s = a[0], o = 0) : s > a.slice( - 1)[0] && (s = a.slice( - 1)[0], o = 1)),
            e.mediator.fire("v:direct-scroll-to-chapter", {
                data: {
                    chapterNum: s,
                    progress: o
                }
            })
        },
        scrollbarScrollStop: function(r) {
            var e = this,
            t = r.data || {},
            s = t.chapterNum,
            o = t.progress,
            a = e.State.showingChapterNumList;
            if (i.inArray(s, a) > -1) e.mediator.fire("v:direct-scroll-to-chapter", {
                data: {
                    chapterNum: s,
                    progress: o
                }
            });
            else {
                if (s > e.freeChapterNum && (s = e.freeChapterNum, o = 1, a.slice( - 1)[0] === s)) return void e.mediator.fire("v:direct-scroll-to-chapter", {
                    data: {
                        chapterNum: s,
                        progress: o
                    }
                });
                e.mediator.fire("p:scroll-to-chapter", {
                    data: {
                        chapterNum: s,
                        progress: o
                    }
                })
            }
        },
        dispose: function() {
            var r = this,
            e = r.Util;
            e.off(r),
            e.dispose(r)
        }
    });
    t.exports.ScrollbarPresenter = a
});;
define("ydcore:widget/book_view/txt_reader/js/p/format/format.presenter.js",
function(e, r, t) {
    var s = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    a = e("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    i = e("ydcore:widget/book_view/txt_reader/js/p/format/chapter.presenter.js").ChapterPresenter,
    n = e("ydcore:widget/book_view/txt_reader/js/p/format/pager.presenter.js").PagerPresenter,
    o = e("ydcore:widget/book_view/txt_reader/js/p/format/scrollbar.presenter.js").ScrollbarPresenter,
    d = {};
    d.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var p = d.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: a
    }).extend({
        _init: function(e) {
            var r = this;
            s.extend(r, e, {
                _presenterList: []
            }),
            r._presenterList.push(new i(e), new n(e), new o(e)),
            r.render()
        },
        render: function() {
            var e = this,
            r = e.mediator;
            r.fire("v:window-resize"),
            r.fire("v:progressbar-init"),
            r.fire("v:scrollbar-init"),
            r.fire("p:first-load", {
                data: {
                    chapterNum: e.State.curChapterNum,
                    progress: e.State.curParagraphIndex > 0 ? e.State.curParagraphIndex: 0,
                    offset: e.State.curParagraphOffset,
                    isParagraph: !0
                }
            })
        },
        dispose: function() {
            var e = this,
            r = e.Util;
            s.each(e._presenterList,
            function(e, r) {
                r.disposed || r.dispose()
            }),
            r.dispose(e)
        }
    });
    t.exports.Format = p
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/comment.view.js",
function(t, e, r) {
    var i = t("ydcommon:widget/ui/lib/jquery/jquery.js"),
    o = t("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    n = {};
    n.lang = t("ydcommon:widget/ui/lib/lang/lang.js");
    var a = ['<div id="comment-pop-wrap" class="comment-pop-wrap hidden comment-visual-hidden">', '<div class="arrow arrow-top"></div>', '<div class="comment-pop">', '<p class="inner-text"></p>', "</div>", "</div>"].join(""),
    s = n.lang.createClass(function(t) {
        this._init(t)
    },
    {
        superClass: o
    }).extend({
        _init: function(t) {
            var e = this;
            i.extend(e, t, {
                maxHeight: 0,
                maxWidth: 260,
                boundaryLeft: 0,
                boundaryRight: 0,
                curTarget: null,
                $el: null,
                $arrow: null,
                $wrap: null,
                $pop: null,
                $text: null
            }),
            e._bindEvent()
        },
        _createComment: function() {
            var t = this;
            t.$wrap = i(a),
            t.$arrow = t.$wrap.find(".arrow"),
            t.$pop = t.$wrap.find(".comment-pop"),
            t.$text = t.$pop.find(".inner-text"),
            t.$body.append(t.$wrap),
            t.$wrap.mousewheel(function(t) {
                t.stopPropagation()
            })
        },
        _setBoundary: function() {
            var t = this,
            e = i(t.el);
            t.boundaryLeft = e.offset().left,
            t.boundaryRight = t.boundaryLeft + e.width()
        },
        show: function(t) {
            var e = this;
            e.$wrap || e._createComment(),
            e.maxHeight || e._getMaxHeight(),
            e.boundaryRight || e._setBoundary(),
            e.curTarget = t.target;
            var r = i(t.target).closest(".ext_remark"),
            o = r.attr("data-remark");
            e.setMsg(o),
            e.$wrap.removeClass("hidden"),
            e.setPos(r.find(".ic-remark")),
            e.$wrap.removeClass("comment-visual-hidden")
        },
        hide: function() {
            var t = this;
            t.curTarget = null,
            t.$wrap && (t.$wrap.addClass("hidden comment-visual-hidden").removeAttr("style"), t.$text.html("").removeAttr("style"))
        },
        setMsg: function(t) {
            var e = this;
            t = t || "",
            t.length < 5 && (t = "&nbsp;&nbsp;&nbsp;" + t + "&nbsp;&nbsp;&nbsp;"),
            e.$text.html(t)
        },
        _getMaxHeight: function() {
            var t = this;
            t.$text.html("\u7684<br/>\u7684<br/>\u7684<br/>\u7684<br/>\u7684"),
            t.$wrap.removeClass("hidden"),
            t.maxHeight = t.$text.height() || 110,
            t.$text.html("")
        },
        setArrowDirection: function(t) {
            var e = this,
            r = "arrow-top arrow-bottom",
            i = "";
            i = "down" === t ? "arrow-top": "arrow-bottom",
            e.$arrow.removeClass(r).addClass(i)
        },
        setSize: function() {
            var t = this,
            e = t.$text,
            r = e.width();
            r > t.maxWidth && e.width(t.maxWidth);
            var i = e.height();
            i > t.maxHeight && e.height(t.maxHeight)
        },
        setPos: function(t) {
            var e, r, i, o = this,
            n = o.$pop,
            a = o.$arrow;
            o.setSize();
            var s = a.height(),
            d = n.outerHeight() + s,
            h = t.offset(),
            p = o.$doc.scrollTop();
            if (i = "up", h.top - p >= d) e = h.top - d;
            else {
                var m = o.$win.height(),
                c = h.top + t.outerHeight();
                p + m - c >= d ? (e = c + s, i = "down") : e = h.top - d
            }
            var u = n.outerWidth(),
            l = h.left + t.width() / 2,
            r = l - u / 2,
            w = 5;
            r + u > o.boundaryRight && (r = o.boundaryRight - u - w),
            r <= o.boundaryLeft && (r = o.boundaryLeft + w),
            o.setArrowDirection(i),
            a.css("left", l - a.width() / 2 - r),
            o.$wrap.css({
                top: e,
                left: r
            })
        },
        _bindEvent: function() {
            var t = this;
            t.$body.click(function(e) {
                var r = i(e.target);
                e.target !== t.curTarget && r.hasClass("ic-remark") ? t.show(e) : t.$wrap && !i.contains(t.$wrap.get(0), e.target) && t.hide()
            }).keydown(function(e) {
                27 === e.which && t.hide()
            }),
            t.mediator.on("v:window-resize",
            function() {
                t.hide(),
                t._setBoundary()
            }),
            t.mediator.on("p:hide-comment-pop",
            function() {
                t.hide()
            })
        }
    });
    r.exports.CommentView = s
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/pay.view.js",
function(e, i, t) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    a = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    o = {};
    o.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var s = o.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: a
    }).extend({
        _init: function(e) {
            var i = this;
            n.extend(i, e, {}),
            i._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            i = e.mediator;
            n(e.el).on("click", ".last-p-b .action a",
            function(t) {
                t.preventDefault();
                var a = "p:send-normal-x-log",
                o = {};
                n(this).hasClass("ffk-buy-btn") ? (a = "p:send-ffkan-log", o.clickType = "14011002") : o.pos = "buy.book.bottom.view",
                i.fire(a, {
                    data: {
                        act_id: 200204
                    }
                }),
                e.$body.trigger("pay.start", o)
            }),
            n(e.el).on("click", ".last-p-b .js-publish-to-phone",
            function(e) {
                e.preventDefault();
                var t = "p:send-normal-x-log";
                i.fire(t, {
                    data: {
                        act_id: 200251
                    }
                })
            })
        }
    });
    t.exports.PayView = s
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/av.view.js",
function(e, t, a) {
    var i = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    o = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    n = e("ydcommon:widget/lib/fis/data/data.js"),
    d = {};
    d.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var r = d.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: o
    }).extend({
        _init: function(e) {
            var t = this;
            i.extend(t, e, {
                tpl: {
                    outVideo: i("#out-video-tpl").val(),
                    audio: i("#audio-tpl").val()
                },
                playerMap: {}
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = e.mediator;
            t.on("v:before-render-to-doc",
            function(t) {
                var a = t.data,
                i = a.dom,
                o = i.find(".video-wrap"),
                n = i.find(".audio-wrap");
                e.renderVideoUI(o),
                e.renderAudioUI(n)
            }),
            i(e.el).on("click", ".audio-wrap .play-btn",
            function(t) {
                t.preventDefault(),
                e._loadSM(i(this))
            }).on("click", ".audio-wrap .progress-wrap",
            function(t) {
                e.setAudioProgress(i(this), t)
            })
        },
        _getDataByEl: function(e) {
            var t = e.hasClass("av-holder") ? e: e.closest(".av-wrap").find(".av-holder"),
            a = t.attr("data-av-key"),
            i = n.get("viewAVData")[a];
            return {
                key: a,
                data: i
            }
        },
        renderVideoUI: function(e) {
            var t = this;
            e.each(function(e, a) {
                var o = i(a),
                d = o.find(".video-holder"),
                r = o.find("iframe");
                if (!r.length) {
                    var u = d.attr("data-av-key"),
                    l = n.get("viewAVData")[u];
                    r = i(t.tpl.outVideo),
                    r.height(d.height() || d.attr("height")).width(d.width() || d.attr("width")).insertAfter(d.hide()).attr({
                        src: l.iframe || l.flash
                    })
                }
            })
        },
        renderAudioUI: function(e) {
            var t = this;
            e.each(function(e, a) {
                var o = i(a),
                d = o.find(".audio-holder"),
                r = o.find(".audio-player"),
                u = d.attr("data-av-key"),
                l = n.get("viewAVData")[u],
                s = t.playerMap[u],
                c = !1;
                r.length || (c = !0, r = i(t.tpl.audio).insertAfter(d.hide()).attr({
                    title: l.title
                })),
                s && setTimeout(function() {
                    s._$el = r,
                    t.syncAudioUI(s, r)
                },
                0)
            })
        },
        syncAudioUI: function(e) {
            function t(e) {
                return 10 > e && (e = "0" + e),
                e
            }
            var a = this,
            i = a._audioPlayerEl(e);
            if (i) {
                var o = e.paused || !e.playState;
                i.find(".play-btn")[o ? "removeClass": "addClass"]("pause-btn");
                var n = Math.floor(e.position / 1e3),
                d = t(Math.floor(n / 60)),
                r = t(n % 60);
                i.find(".time").html(d + "'" + r + "''");
                var u = e.bytesLoaded / e.bytesTotal,
                l = e.position / e.durationEstimate,
                s = (Math.ceil(100 * u) || 0) + "%",
                c = (Math.ceil(100 * l) || 0) + "%";
                i.find(".progress-load").css("width", s).end().find(".progress-play").css("width", c).end()
            }
        },
        setAudioProgress: function(e, t) {
            var a = this,
            i = a._getDataByEl(e),
            o = a.playerMap[i.key];
            if (o) {
                var n = t.pageX,
                d = e.offset().left,
                r = e.width(),
                u = (n - d) / r;
                u *= o.durationEstimate,
                o.setPosition(u),
                (o.paused || !o.playState) && a.syncAudioUI(o)
            }
        },
        _getAudioUrl: function(e) {
            var t = this,
            a = "/bookeditor/interface/getbookaudio?book_id=" + t.doc_id + "&token=" + e;
            return a
        },
        _audioPlayerEl: function(e, t) {
            return t && (e._$el = t),
            t = e._$el,
            t && i.contains(document, t[0]) || (e._$el = null),
            e._$el
        },
        _loadSM: function(t) {
            var a = this,
            i = a._getDataByEl(t),
            o = i.data.token,
            n = a._getAudioUrl(o),
            d = {
                $el: t.closest(".audio-player"),
                key: i.key,
                url: n
            };
            return a.SM ? void a.playSound(d) : void e.async("ydcore:widget/ui/book_view/soundmanager2/soundmanager2.js",
            function(e) {
                e.soundManager.setup({
                    url: e.swfPath,
                    debugMode: !1,
                    onready: function() {
                        a.SM = e,
                        a.playSound(d)
                    }
                })
            })
        },
        playSound: function(e) {
            var t = this,
            a = e.key,
            i = e.url,
            o = t.playerMap[a];
            o || (o = t.createSound(i), t._audioPlayerEl(o, e.$el), t.playerMap[a] = o),
            o.togglePause(),
            t.syncAudioUI(o)
        },
        createSound: function(e) {
            var t = this,
            a = t.SM,
            i = function() {
                t.syncAudioUI(o)
            },
            o = a.soundManager.createSound({
                url: e,
                whileloading: i,
                whileplaying: i,
                onplay: i,
                onpause: i,
                onload: i,
                onfinish: i
            });
            return o
        }
    });
    a.exports.AVView = r
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/qa.view.js",
function(e, i, o) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    t = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    d = e("ydcommon:widget/ui/read/yui/dialog/dialog.js"),
    a = e("ydcommon:widget/ui/js_core/log/log.js"),
    s = {};
    s.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var r = s.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: t
    }).extend({
        _init: function(e) {
            var i = this;
            e.isQADoc && (a.xsend(2, "200270", {
                docid: i.doc_id
            }), n.extend(i, e, {
                startTime: new Date,
                timeHolder: 0
            }), i._bindEvent())
        },
        _sendTimeLog: function() {
            var e = this,
            i = new Date,
            o = i - e.startTime;
            a.xsend(2, "200269", {
                docid: e.doc_id,
                duration: o
            }),
            e.startTime = i
        },
        _sendUnloadLog: function(e) {
            var i = this;
            e || i._sendTimeLog();
            for (var o = 1e5; o-->0;) i.timeHolder++,
            i.timeHolder > 100 && (i.timeHolder = 0)
        },
        _bindEvent: function() {
            function e() {
                var e = "http://yuedu.baidu.com/topic/yuedu/qa/" + i.doc_id + ".html";
                e += "?_" + (new Date).getTime(),
                d.pop({
                    content: '<iframe src="' + e + '" frameborder="0"></iframe>',
                    width: "850px",
                    height: "100%",
                    maskOpacity: .8,
                    className: "qa-iframe-dialog",
                    closeWithClickMask: !1,
                    closeWithEsc: !1
                })
            } {
                var i = this;
                i.mediator
            }
            n(window).on("beforeunload",
            function() {
                i._sendUnloadLog()
            }).on("unload",
            function() {
                i._sendUnloadLog(!0)
            }),
            e()
        }
    });
    o.exports.QAView = r
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/progressbar.view.js",
function(r, s, i) {
    var e = r("ydcommon:widget/ui/lib/jquery/jquery.js"),
    n = r("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    o = {};
    o.lang = r("ydcommon:widget/ui/lib/lang/lang.js");
    var t = o.lang.createClass(function(r) {
        this._init(r)
    },
    {
        superClass: n
    }).extend({
        _init: function(r) {
            var s = this;
            e.extend(s, r, {
                isInited: !1,
                isProgressing: !1,
                willProgress: 0
            }),
            s._bindEvent()
        },
        _bindEvent: function() {
            var r = this,
            s = (r.mediator, r.Util);
            s.on("v:progressbar-init", r),
            s.on("v:set-read-progress", r)
        },
        progressbarInit: function() {
            var r = this;
            r.isInited || (r.$wrap = e('<div id="read-progress"><div class="inner-progress" style="width:0%;"></div></div>'), r.$wrap.appendTo(r.$toolsBar), r.$inner = r.$wrap.find(".inner-progress"), r.isInited = !0)
        },
        setReadProgress: function(r) {
            var s = this,
            i = r.data || {},
            e = i.progress;
            e *= 100,
            s.showProgress(e)
        },
        showProgress: function(r) {
            var s = this;
            return s.isProgressing ? void(s.willProgress = r) : (s.isProgressing = !0, void s.$inner.animate({
                width: r + "%"
            },
            200).promise().done(function() {
                s.isProgressing = !1;
                var r = s.willProgress;
                s.willProgress = 0,
                r > 0 && s.showProgress(r)
            }))
        }
    });
    i.exports.ProgressbarView = t
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/selecttext/share.js",
function(e, s, t) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcommon:widget/ui/js_core/log/log.js"),
    a = {};
    a.json = e("ydcommon:widget/ui/lib/json/json.js");
    var o = e("ydcore:widget/ui/book_view/lib/rangy/rangy.js").rangy,
    r = {
        init: function() {
            var e = this;
            n(function() {
                e.shareEl = n("#reader-share-pop").appendTo("body"),
                e._initEvent()
            })
        },
        _initEvent: function() {
            var e = this;
            e.shareEl.find(".reader-share-icons").on("click",
            function() {
                e.mediator.fire("v:hide-all-reader-pop")
            }),
            e.shareEl.find(".bdshare-items").click(function() {
                try {
                    var s = n(this);
                    s.hasClass("bds_tsina") ? i.xsend(2, 200261) : s.hasClass("bds_qzone") ? i.xsend(2, 200263) : s.hasClass("bds_douban") ? i.xsend(2, 200264) : s.hasClass("bds_renren") && i.xsend(2, 200265);
                    var t = {
                        act_id: "200080"
                    },
                    a = o.getSelection(),
                    r = [a.anchorNode, a.focusNode];
                    a.isBackwards() && r.reverse();
                    var d = [],
                    c = [],
                    h = !0;
                    if (n.each(r,
                    function(e, s) {
                        if (s) {
                            var t = n(s).closest(".p-b");
                            if (t[0]) {
                                var i = t[0].className || "",
                                a = /\bp-b-(\d+)-(\d+)\b/.exec(i);
                                if (a) return d.push(parseInt(a[2]) || 1),
                                c.push(parseInt(a[1]) || 1),
                                !0
                            }
                        }
                        return h = !1,
                        !1
                    }), h) {
                        t.cn = c[0],
                        t.pn = d[0],
                        t.cn_end = c[1],
                        t.pn_end = d[1],
                        1 === t.cn_end && (t.pn_end -= 2, t.pn_end = Math.max(t.pn_end, 1));
                        var l = e._shareTxt;
                        l.length > 200 && (l = l.substr(0, 100) + " ... " + l.substr(l.length - 100)),
                        t.content = encodeURIComponent(l),
                        e.mediator.fire("p:send-x-log", {
                            data: t
                        })
                    }
                } catch(u) {}
            })
        },
        show: function(e) {
            var s = this.shareEl,
            t = this._getArrowPosition(e.position, s),
            n = t.position;
            "up" === t.direction ? (s.removeClass("select-a-b"), s.addClass("select-a-t")) : (s.removeClass("select-a-t"), s.addClass("select-a-b")),
            s.css({
                left: n.X,
                top: n.Y
            }).show(),
            this._shareTxt = e.txt,
            this._setShareData(e.txt)
        },
        _setShareData: function(e) {
            var s = {
                text: e,
                title: document.title,
                url: location.href.replace(/\?.*$/g, "")
            };
            n("#bdshare").attr("data", a.json.stringify(s))
        },
        hide: function() {
            this.shareEl && this.shareEl.css("display", "none")
        },
        _getArrowPosition: function(e, s) {
            var t = s.height(),
            i = n("body").scrollTop() + n(window).height(),
            a = i - e.Y < t + 30 ? "up": "down",
            o = {
                X: e.X - 45,
                Y: "up" == a ? e.Y - 14 - t: e.Y + 8
            };
            return {
                direction: a,
                position: o
            }
        }
    };
    t.exports = r
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/selecttext/copy.js",
function(e, t, i) {
    var o = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    n = e("ydcommon:widget/ui/js_core/log/log.js"),
    r = e("ydcommon:widget/ui/js_core/util/util.js"),
    d = e("ydcore:widget/ui/book_view/lib/ZeroClipboard/ZeroClipboard.js").ZeroClipboard;
    window.ZeroClipboard = d,
    d.setMoviePath("/static/ydcore/widget/ui/book_view/lib/ZeroClipboard/ZeroClipboard.swf");
    var s = new d.Client;
    s.setHandCursor(!0);
    var a = r.zIndexManager.bringToFront,
    c = {
        init: function(e) {
            var t = this;
            t.text = "",
            o(function() {
                t._initBtn(e),
                t._initEvent()
            })
        },
        _initBtn: function(e) {
            var t = this;
            a(e.find("#copy-text-btn").get(0)),
            s.glue("copy-text-btn"),
            t.reposition()
        },
        _initEvent: function() {
            var e = this;
            s.addEventListener("mouseover",
            function() {
                s.setText(e.text)
            }),
            s.addEventListener("complete",
            function() {
                e.mediator.fire("v:hide-all-reader-pop"),
                e.mediator.fire("v:show-general-msg", {
                    data: {
                        msg: "\u6587\u672c\u5185\u5bb9\u5df2\u590d\u5236"
                    }
                }),
                n.xsend(2, 200260)
            })
        },
        setText: function(e) {
            this.text = e || ""
        },
        reposition: function() {
            s.reposition()
        }
    };
    i.exports = c
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/selecttext.view.js",
function(e, t, o) {
    var a = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    n = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    i = e("ydcommon:widget/ui/js_core/login/login.js").login,
    s = e("ydcore:widget/book_view/txt_reader/js/v/common/selecttext/share.js"),
    r = e("ydcore:widget/book_view/txt_reader/js/v/common/selecttext/copy.js"),
    l = e("ydcore:widget/ui/book_view/lib/rangy/rangy.js").rangy,
    d = {};
    d.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    d.browser = e("ydcommon:widget/ui/lib/browser/browser.js"),
    d.string = e("ydcommon:widget/ui/lib/string/string.js"),
    d.cookie = e("ydcommon:widget/ui/lib/cookie/cookie.js");
    var c = ['<div id="select-text" class="reader-pop has-arrow-pop reader-select-pop">', '<div class="abox">', '<div class="a1"></div>', '<div class="a2"></div>', "</div>", '<ul class="select-area">', '<li><a id="copy-text-btn" class="select-item copy-text" href="###">\u590d\u5236</a></li>', '<li><a class="select-item add-underline" href="###">\u5212\u8bcd</a></li>', '<li><a class="select-item cancel-underline" href="###">\u6e05\u9664\u5212\u7ebf</a></li>', '<li><a class="select-item modify-note" href="###" target="_blank">\u7b14\u8bb0</a></li>', '<li><a class="select-item share" href="###">\u5206\u4eab</a></li>', "</ul>", '<ul class="color-area">', '<li><a class="color-item color0 add-underline" data-color="0" href="###"><span class="color-icon"></span></a></li>', '<li><a class="color-item color1 add-underline" data-color="1" href="###"><span class="color-icon"></span></a></li>', '<li><a class="color-item color2 add-underline" data-color="2" href="###"><span class="color-icon"></span></a></li>', '<li><a class="color-item color3 add-underline" data-color="3" href="###"><span class="color-icon"></span></a></li>', '<li><a class="color-item color4 add-underline" data-color="4" href="###"><span class="color-icon"></span></a></li>', "</ul>", "</div>"].join(""),
    g = d.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: n
    }).extend({
        _init: function(e) {
            var t = this;
            a.extend(t, e, {
                forbidStartSelectors: [".progress-absent", "img", ".av-wrap"],
                curNoteKey: "",
                isIconArea: !1,
                oriMousePos: {}
            }),
            t.$selectPop = a(c).appendTo("body"),
            t._initDefaultUnderlineColor(),
            t._simplePopInit(),
            t._bindEvent()
        },
        _simplePopInit: function() {
            var e = this;
            s.mediator = e.mediator,
            s.init(),
            r.mediator = e.mediator,
            r.init(e.$selectPop)
        },
        _bindEvent: function() {
            var e = this;
            e.Util.on("v:point-on-underline", e),
            e.mediator.on("v:hide-all-reader-pop",
            function() {
                e.curNoteKey = "",
                e.isIconArea = !1,
                e._hideAllPops(!0)
            }),
            e._bindUIEvent()
        },
        _bindUIEvent: function() {
            var e = this,
            t = e.$selectPop,
            o = e.mediator;
            e._forbidUIOp(),
            e._shrinkSelectionOp(),
            e.$win.on("resize",
            function() {
                e._hideAllPops(!0)
            }).on("keydown",
            function(t) {
                27 === t.keyCode && e._hideAllPops(!0)
            }),
            e.$body.on("click",
            function() {
                e._hideAllPops(!1)
            }),
            t.on("mouseenter", ".color-item",
            function() {
                a(this).addClass("hover")
            }).on("mouseleave", ".color-item",
            function() {
                a(this).removeClass("hover")
            }),
            t.on("click",
            function(t) {
                t.preventDefault(),
                t.stopPropagation(),
                e.hide()
            }),
            e.$readerContainer.on("mouseup",
            function(t) {
                e.isPreview || "ffkan" == e.State.currentType || e.isForbidNote || setTimeout(function() {
                    var o = e._getSelectedText(e.curNoteKey);
                    e.curNoteKey && e.isIconArea ? e._showNotePanel(t) : e.curNoteKey || o ? e.show(t, o) : e.hide()
                },
                30)
            }),
            t.on("click", ".share",
            function() {
                var o = t.attr("data-note-key");
                s.show({
                    txt: e._getSelectedText(o),
                    position: e.oriMousePos
                })
            }),
            t.on("click", ".add-underline",
            function() {
                if (e._isLogin()) {
                    var n = a(this).hasClass("color-item") ? a(this).data("color") : e.State.curNoteColor;
                    e._setDefaultUnderlineColor(n),
                    o.fire("v:add-note", {
                        data: {
                            noteKey: t.attr("data-note-key") || "",
                            noteColor: n,
                            changeCustomstr: !1
                        }
                    })
                }
            }),
            t.on("click", ".cancel-underline",
            function() {
                if (e._isLogin()) {
                    var a, n = t.attr("data-note-key");
                    n && (a = e.noteUtil.getNoteByKey(n), a.customstr ? o.fire("v:confirm-del-note", {
                        data: {
                            noteKey: n
                        }
                    }) : o.fire("p:del-note", {
                        data: {
                            noteKey: n
                        }
                    }))
                }
            }),
            t.on("click", ".modify-note",
            function() {
                if (e._isLogin()) if (t.attr("data-note-key")) {
                    var a = e.noteUtil.getNoteByKey(t.attr("data-note-key"));
                    a.customstr ? o.fire("v:show-view-note-pop", {
                        data: {
                            note: a,
                            position: e.oriMousePos
                        }
                    }) : o.fire("v:show-modify-note-pop", {
                        data: {
                            note: a,
                            position: e.oriMousePos
                        }
                    })
                } else o.fire("v:add-note", {
                    data: {
                        noteColor: e.State.curNoteColor
                    }
                }).then(function(t) {
                    t[0].customstr.length ? o.fire("v:show-view-note-pop", {
                        data: {
                            note: t[0],
                            position: e.oriMousePos
                        }
                    }) : o.fire("v:show-modify-note-pop", {
                        data: {
                            note: t[0],
                            position: e.oriMousePos
                        }
                    })
                })
            })
        },
        _forbidUIOp: function() {
            var e = this;
            e.$readerContainer.on("click",
            function(t) { (t.target == e.$readerContainer.get(0) || t.target == e.$textContainer.get(0)) && t.preventDefault()
            }),
            e.$textContainer.on("mousedown",
            function(t) { (a(t.target).closest(e.forbidStartSelectors.join(",")).length > 0 || t.target == e.$readerContainer.get(0) || t.target == e.$textContainer.get(0)) && (t.preventDefault(), t.stopImmediatePropagation(), e._hideAllPops(!0))
            }).on("mouseup",
            function(t) {
                if (e.isIE678) {
                    var o = e.$textContainer.find(".text-container"),
                    n = l.getSelection().getRangeAt(0),
                    i = a(n.startContainer).get(0),
                    s = a(n.endContainer).get(0),
                    r = !0,
                    c = !0,
                    g = !1;
                    if (a.each(o,
                    function(e, t) {
                        return a.contains(t, i) ? (r = !1, !1) : void 0
                    }), a.each(o,
                    function(e, t) {
                        return a.contains(t, s) ? (c = !1, !1) : void 0
                    }), (r || c) && (g = !0), a(i).closest(".progress-absent").length && (g = !0), a(s).closest(".progress-absent").length && (g = !0), g) return t.preventDefault(),
                    t.stopImmediatePropagation(),
                    void e._hideAllPops(!0)
                }
                var p = d.string.trim(l.getSelection().toString());
                p.length || e.curNoteKey ? !p.length && a(t.target).closest(e.forbidStartSelectors.join(",")).length && (t.preventDefault(), t.stopImmediatePropagation(), e._hideAllPops(!0)) : (t.preventDefault(), t.stopImmediatePropagation(), e._hideAllPops(!0))
            }).on("copy",
            function(t) {
                e.isPreview || (t.preventDefault(), t.stopImmediatePropagation())
            })
        },
        _shrinkSelectionOp: function() {
            var e = this;
            e.$textContainer.on("mouseup",
            function() {
                var t = l.getSelection();
                t.rangeCount && !t.isCollapsed && (e._shrinkParagraph(), e._shrinkInPa())
            })
        },
        _shrinkParagraph: function() {
            var e, t, o, n, i, s = this,
            r = l.getSelection(),
            c = r.getRangeAt(0),
            g = a(c.startContainer),
            p = a(c.endContainer),
            f = g.closest(".p-b"),
            h = p.closest(".p-b"),
            u = a(),
            m = !1,
            v = !1;
            for (u = s._getRangePb(f, h); u.length > 0 && u.first().get(0) !== u.last().get(0);) if (e = u.first(), e.hasClass("progress-absent") || e.hasClass("single-img-p") || e.hasClass("single-page") || 0 === e.height() || !d.string.trim(e.text()).length) u = u.not(":first"),
            m = !0;
            else {
                if (m) break;
                if (3 === g.get(0).nodeType && c.startOffset !== l.dom.getNodeLength(c.startContainer)) break;
                if (g.hasClass("p-b")) {
                    if (l.dom.getNodeLength(g.get(0)) !== c.startOffset - 1) break;
                    u = u.not(":first"),
                    m = !0
                } else {
                    for (var v = !1; ! g.hasClass("p-b");) {
                        if (l.dom.getNodeLength(g.parent().get(0)) - 1 !== l.dom.getNodeIndex(g.get(0)) || !c.startOffset && +a(c.startContainer).attr("data-part-len")) {
                            v = !0;
                            break
                        }
                        g = g.parent()
                    }
                    if (v) break;
                    u = u.not(":first"),
                    m = !0
                }
            }
            for (e = u.first(); u.length > 0 && u.first().get(0) !== u.last().get(0);) if (t = u.last(), t.hasClass("progress-absent") || t.hasClass("single-img-p") || t.hasClass("single-page") || 0 === t.height() || !d.string.trim(t.text()).length) u = u.not(":last"),
            m = !0;
            else {
                if (m) break;
                if (3 === p.get(0).nodeType && 0 !== c.endOffset) break;
                if (p.hasClass("p-b")) {
                    if (0 !== c.endOffset) break;
                    u = u.not(":last"),
                    m = !0
                } else {
                    for (var v = !1; ! p.hasClass("p-b");) {
                        if (0 !== l.dom.getNodeIndex(p.get(0)) || 0 != c.endOffset) {
                            v = !0;
                            break
                        }
                        p = p.parent()
                    }
                    if (v) break;
                    u = u.not(":last"),
                    m = !0
                }
            }
            t = u.last(),
            o = c.cloneRange(),
            m ? (f.get(0) !== e.get(0) && (e.hasClass("ext_picnote_wrap") || e.hasClass("ext_code_wrap") ? o.setStart(e.get(0), 0) : (n = e.find('.atom[data-part-len!="0"]').first().get(0), n.firstChild ? (n = n.firstChild, o.setStart(n, 0)) : o.setStartBefore(n))), h.get(0) !== t.get(0) && (t.hasClass("ext_picnote_wrap") || t.hasClass("ext_code_wrap") ? o.setEnd(t.get(0), l.dom.getNodeLength(t.get(0))) : (i = t.find('.atom[data-part-len!="0"]').last().get(0), i.firstChild ? o.setEnd(i.firstChild, l.dom.getNodeLength(i.firstChild)) : o.setStartAfter(i)))) : (e.hasClass("ext_code_wrap") && o.setStart(e.get(0), 0), t.hasClass("ext_code_wrap") && o.setEnd(t.get(0), l.dom.getNodeLength(t.get(0)))),
            r.removeAllRanges(),
            o.isValid() && r.setSingleRange(o)
        },
        _shrinkInPa: function() {
            var e = l.getSelection(),
            t = e.getRangeAt(0);
            if (1 == t.startContainer.nodeType) var o = t.startContainer.childNodes[Math.min(t.startOffset, l.dom.getNodeLength(t.endContainer) - 1)];
            if (1 == t.endContainer.nodeType) var n = t.endContainer.childNodes[Math.max(t.endOffset - 1, 0)];
            if (o && "BR" == o.tagName || n && "BR" == n.tagName) return void me._hideAllPops(!0);
            var i, s, r = a(t.startContainer),
            d = a(t.endContainer),
            c = r.find('.atom[data-part-len!="0"]:first'),
            g = d.find('.atom[data-part-len!="0"]:last'),
            p = !1,
            f = t.cloneRange(); ! r.hasClass("p-b") && c.length && (p = !0, i = c.get(0).firstChild, i ? f.setStart(i, 0) : f.setStartBefore(c.get(0))),
            !d.hasClass("p-b") && g.length && (p = !0, s = g.get(0).firstChild, s ? f.setEnd(s, l.dom.getNodeLength(s)) : f.setEndAfter(g.get(0))),
            p && (e.removeAllRanges(), f.isValid() && e.setSingleRange(f))
        },
        pointOnUnderline: function(e) {
            var t, o, a = this,
            n = e.data || {},
            i = a.$selectPop;
            a.curNoteKey = n.curNoteKey,
            a.isIconArea = !!n.isIconArea,
            i.data("isHide") && (n.curNoteKey ? (i.find(".select-area .add-underline").parent().hide(), i.find(".select-area .cancel-underline").parent().show(), i.find(".active").removeClass("active"), t = a.noteUtil.getNoteByKey(n.curNoteKey), t && t.style && t.style.noteColor && (o = t.style.noteColor, i.find(".color-item.color" + o).addClass("active"))) : (i.find(".select-area .cancel-underline").parent().hide(), i.find(".select-area .add-underline").parent().show(), i.find(".active").removeClass("active")))
        },
        show: function(e, t) {
            var o, a = this;
            a.oriMousePos = {
                X: e.pageX,
                Y: e.pageY
            },
            o = a._getArrowPosition(e),
            a.$selectPop.attr("data-note-key", a.curNoteKey),
            a.hide(),
            s.hide(),
            a._showPanel(o),
            r.setText(t ? t: a.curNoteKey ? a.noteUtil.getNoteByKey(a.curNoteKey).summary: ""),
            r.reposition()
        },
        hide: function() {
            var e = this;
            e.$selectPop.offset({
                top: -9999,
                left: -9999
            }).data("isHide", !0),
            r.reposition()
        },
        _getSelectedText: function(e) {
            var t, o = this,
            n = l.getSelection(),
            i = !1,
            s = "";
            return n.isCollapsed ? e && (i = o.noteUtil.getNoteByKey(e)) : (t = n.getRangeAt(0), a(t.commonAncestorContainer).closest(o.$textContainer).length > 0 && (i = o.noteUtil.parseRangeToPos(t))),
            i && o.mediator.when(["v:get-range-text", {
                data: {
                    notes: [i]
                }
            }]).then(function(e) {
                s = e[0].summary
            }),
            s
        },
        _getRangePb: function(e, t) {
            var o, a, n, i = this,
            s = e.attr("data-cn"),
            r = t.attr("data-cn"),
            l = Number(s) + 1;
            if (s == r) o = e.attr("data-paragraph-index") != t.attr("data-paragraph-index") ? e.nextUntil(t, ".p-b").addBack().add(t) : e;
            else {
                for (o = e.nextAll(".p-b").addBack(), a = t.closest(".txt-reader-wrap").find(".p-b"), n = t.attr("data-paragraph-index"); r > l;) o = o.add(i.$textContainer.find('.txt-reader-wrap[data-block-num="' + l + '"] .p-b')),
                l++;
                o = o.add(a.slice(0, n))
            }
            return o
        },
        _getArrowPosition: function(e) {
            var t = this,
            o = t.$selectPop.height(),
            n = t.$selectPop.width(),
            i = (a(window).height(), a(window).width()),
            s = e.pageY > o + 30 ? "up": "down";
            i - e.pageX < n ? (t.$selectPop.find(".abox").css({
                left: e.pageX - (i - n)
            }), e.pageX = i - n) : t.$selectPop.find(".abox").css({
                left: 30
            });
            var r = {
                X: e.pageX - 30,
                Y: "up" == s ? e.pageY - 14 - o: e.pageY + 8
            };
            return {
                direction: s,
                position: r
            }
        },
        _showPanel: function(e) {
            var t = this,
            o = t.$selectPop,
            a = e.position;
            "up" === e.direction ? (o.removeClass("select-a-b"), o.addClass("select-a-t")) : (o.removeClass("select-a-t"), o.addClass("select-a-b")),
            o.css({
                left: a.X,
                top: a.Y
            }).data("isHide", !1)
        },
        _showNotePanel: function(e) {
            {
                var t = this,
                o = t.mediator;
                t.$selectPop
            }
            if (t.curNoteKey) {
                var a = t.noteUtil.getNoteByKey(t.curNoteKey);
                a.customstr ? o.fire("v:show-view-note-pop", {
                    data: {
                        note: a,
                        position: {
                            X: e.pageX,
                            Y: e.pageY
                        }
                    }
                }) : o.fire("v:show-modify-note-pop", {
                    data: {
                        note: a,
                        position: {
                            X: e.pageX,
                            Y: e.pageY
                        }
                    }
                })
            }
        },
        _hideAllPops: function(e) {
            var t = this;
            t.hide(),
            s.hide(),
            t.mediator.fire("v:hide-all-note-pop"),
            e && l.getSelection().removeAllRanges()
        },
        _initDefaultUnderlineColor: function() {
            var e = this;
            e.State.curNoteColor = parseInt(d.cookie.get("note_color")) || 0
        },
        _setDefaultUnderlineColor: function(e) {
            var t = this,
            e = parseInt(e) || 0;
            d.cookie.set("note_color", e, {
                path: "/",
                expires: 2592e6
            }),
            t._initDefaultUnderlineColor()
        },
        _isLogin: function() {
            var e = this;
            return e.isLogin ? !0 : (i.verlogin(function() {
                window.location.reload(!0)
            }), !1)
        }
    });
    o.exports.SelectTextView = g
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/note/show.pop.js",
function(i, e, o) {
    var t = i("ydcommon:widget/ui/lib/jquery/jquery.js"),
    n = i("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    s = {};
    s.lang = i("ydcommon:widget/ui/lib/lang/lang.js");
    var a = ['<div id="note-show-pop" class="reader-pop has-arrow-pop note-show-pop no-select-range">', '<div class="abox">', '<div class="a1"></div>', '<div class="a2"></div>', "</div>", '<div class="content-wrap">', '<div class="note-overview-container">', '<div class="scrollbar">', '<div class="track">', '<div class="thumb">', '<div class="end"></div>', '<div class="bottom"></div>', "</div>", "</div>", "</div>", '<div class="viewport">', '<div class="overview note-overview"></div>', "</div>", "</div>", '<p class="act-btn">', '<a href="###" class="modify-link">\u4fee\u6539</a>', '<a href="###" class="del-link">\u5220\u9664</a>', "</p>", "</div>", "</div>"].join(""),
    d = s.lang.createClass(function(i) {
        this._init(i)
    },
    {
        superClass: n
    }).extend({
        _init: function(i) {
            var e = this;
            t.extend(e, i, {
                note: {},
                maxHeight: 145,
                wrapHeight: 214
            }),
            e._createPop(),
            e._bindEvent()
        },
        _createPop: function() {
            var i = this;
            i.$el = t(a).appendTo(i.$body),
            i.$container = i.$el.find(".note-overview-container"),
            i.$overview = i.$el.find(".note-overview"),
            i.$confirm = i.$el.find(".modify-link"),
            i.$cancel = i.$el.find(".del-link")
        },
        _bindEvent: function() {
            {
                var i = this,
                e = i.mediator;
                i.Util
            }
            i.Util.on("v:show-view-note-pop", i),
            e.on("v:hide-all-note-pop", t.proxy(i._hide, i)),
            i._bindUIEvent()
        },
        _bindUIEvent: function() {
            var i = this,
            e = i.mediator;
            i.$el.on("click",
            function(i) {
                i.stopPropagation()
            }),
            i.$el.on("click", ".modify-link",
            function(o) {
                o.preventDefault(),
                i._hide(),
                e.fire("v:show-modify-note-pop", {
                    data: {
                        note: i.note,
                        position: i.pos
                    }
                })
            }),
            i.$el.on("click", ".del-link",
            function(o) {
                o.preventDefault(),
                i._hide(),
                e.fire("v:confirm-del-note", {
                    data: {
                        noteKey: i.note.key
                    }
                })
            })
        },
        showViewNotePop: function(i) {
            var e = this,
            o = i.data || {};
            e.note = o.note,
            e.pos = o.position,
            e._show(o.note, o.position)
        },
        _show: function(i, e) {
            var o, e, t = this,
            n = t.$el,
            s = t.$container,
            a = t.noteUtil.parseOriStr(i.customstr, "<br>");
            t.$overview.html(a),
            t.maxHeight >= s.find(".note-overview").height() ? (n.css("height", "auto"), s.css("height", "auto").find(".viewport").css("height", t.$overview.height()), s.find(".scrollbar").addClass("hidden")) : (n.css("height", t.wrapHeight), s.css("height", t.maxHeight).find(".viewport").css("height", t.maxHeight), s.find(".scrollbar").removeClass("hidden"), s.tinyscrollbar()),
            o = this._getPopPostion(e, t.$el),
            e = o.position,
            "up" === o.direction ? (n.removeClass("select-a-b"), n.addClass("select-a-t")) : (n.removeClass("select-a-t"), n.addClass("select-a-b")),
            n.css({
                left: e.X,
                top: e.Y
            })
        },
        _getPopPostion: function(i, e) {
            var o = this,
            n = e.height(),
            s = e.width(),
            a = (t(window).height(), t(window).width()),
            d = i.Y > n + 30 ? "up": "down";
            a - i.X < s ? (o.$el.find(".abox").css({
                left: i.X - (a - s)
            }), i.X = a - s) : o.$el.find(".abox").css({
                left: 30
            });
            var l = {
                X: i.X - 30,
                Y: "up" == d ? i.Y - 14 - n: i.Y + 8
            };
            return {
                direction: d,
                position: l
            }
        },
        _hide: function() {
            var i = this;
            i.$el.offset({
                top: -9999,
                left: -9999
            })
        }
    });
    o.exports.PopView = d
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/note/modify.pop.js",
function(t, n, o) {
    var e = t("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = t("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    s = {};
    s.lang = t("ydcommon:widget/ui/lib/lang/lang.js");
    var a = ['<div id="note-modify-pop" class="reader-pop has-arrow-pop note-modify-pop">', '<div class="abox">', '<div class="a1"></div>', '<div class="a2"></div>', "</div>", '<div class="content-wrap">', '<p class="mb10"><textarea class="customstr-input" maxlength="500"></textarea></p>', '<p class="act-btn">', '<a href="###" class="pop-btn confirm-btn mr10">\u786e\u5b9a</a>', '<a href="###" class="pop-btn cancel-btn">\u53d6\u6d88</a>', "</p>", "</div>", "</div>"].join(""),
    c = s.lang.createClass(function(t) {
        this._init(t)
    },
    {
        superClass: i
    }).extend({
        _init: function(t) {
            var n = this;
            e.extend(n, t, {
                note: {}
            }),
            n._createPop(),
            n._bindEvent()
        },
        _createPop: function() {
            var t = this;
            t.$el = e(a).appendTo(t.$body),
            t.$input = t.$el.find(".customstr-input"),
            t.$confirm = t.$el.find(".confirm-btn"),
            t.$cancel = t.$el.find(".cancel-btn")
        },
        _bindEvent: function() {
            {
                var t = this,
                n = t.mediator;
                t.Util
            }
            t.Util.on("v:show-modify-note-pop", t),
            n.on("v:hide-all-note-pop", e.proxy(t._hide, t)),
            t._bindUIEvent()
        },
        _bindUIEvent: function() {
            var t = this;
            t.$el.on("click",
            function(t) {
                t.stopPropagation()
            }),
            t.$input.on("keydown",
            function(t) {
                t.stopPropagation()
            }),
            t.$el.on("click", ".confirm-btn",
            function(n) {
                n.preventDefault(),
                t._hide(),
                t.mediator.fire("p:add-note", {
                    data: {
                        boundary: t.note,
                        style: t.note.style,
                        customstr: t.$input.val(),
                        changeCustomstr: !0
                    }
                })
            }),
            t.$el.on("click", ".cancel-btn",
            function(n) {
                n.preventDefault(),
                t._hide()
            })
        },
        showModifyNotePop: function(t) {
            var n = this,
            o = t.data || {};
            n.note = o.note,
            n._show(o.note, o.position)
        },
        _show: function(t, n) {
            var o = this,
            e = this._getPopPostion(n, o.$el),
            n = e.position,
            i = o.$el;
            "up" === e.direction ? (i.removeClass("select-a-b"), i.addClass("select-a-t")) : (i.removeClass("select-a-t"), i.addClass("select-a-b")),
            i.css({
                left: n.X,
                top: n.Y
            }).show(),
            o.$input.val(t.customstr || "").focus()
        },
        _getPopPostion: function(t, n) {
            var o = this,
            i = n.height(),
            s = n.width(),
            a = (e(window).height(), e(window).width()),
            c = t.Y > i + 30 ? "up": "down";
            a - t.X < s ? (o.$el.find(".abox").css({
                left: t.X - (a - s)
            }), t.X = a - s) : o.$el.find(".abox").css({
                left: 30
            });
            var d = {
                X: t.X - 30,
                Y: "up" == c ? t.Y - 14 - i: t.Y + 8
            };
            return {
                direction: c,
                position: d
            }
        },
        _hide: function() {
            var t = this;
            t.$el.hide()
        }
    });
    o.exports.PopView = c
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/note/del.confirm.js",
function(e, n, t) {
    var i = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    o = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    a = {};
    a.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var c = ['<div id="del-note-confirm" class="reader-pop del-note-confirm no-select-range">', '<div class="content-wrap">', '<p class="tips-text mb20">\u786e\u5b9a\u8981\u5220\u9664\u8fd9\u6761\u7b14\u8bb0\u5417\uff1f</p>', '<p class="act-btn">', '<a href="###" class="pop-btn confirm-btn mr10">\u786e\u5b9a</a>', '<a href="###" class="pop-btn cancel-btn">\u53d6\u6d88</a>', "</p>", "</div>", "</div>"].join(""),
    d = a.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: o
    }).extend({
        _init: function(e) {
            var n = this;
            i.extend(n, e, {
                noteKey: ""
            }),
            n.$el = i(c).appendTo(n.$body),
            n._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            n = e.mediator,
            t = e.Util;
            t.on("v:confirm-del-note", e),
            n.on("v:hide-all-note-pop", i.proxy(e._hide, e)),
            e._bindUIEvent()
        },
        _bindUIEvent: function() {
            var e = this;
            e.$el.on("click",
            function(e) {
                e.stopPropagation()
            }),
            e.$el.on("click", ".confirm-btn",
            function(n) {
                n.preventDefault(),
                e._hide(),
                e.noteKey && e.mediator.fire("p:del-note", {
                    data: {
                        noteKey: e.noteKey
                    }
                })
            }),
            e.$el.on("click", ".cancel-btn",
            function(n) {
                n.preventDefault(),
                e._hide()
            })
        },
        confirmDelNote: function(e) {
            var n = this,
            t = (n.mediator, e.promise, e.data || {});
            n.noteKey = t.noteKey,
            n._show()
        },
        _show: function() {
            var e = this;
            e.$el.show()
        },
        _hide: function() {
            var e = this;
            e.$el.hide()
        }
    });
    t.exports.PopView = d
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/note.view.js",
function(e, t, o) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcore:widget/ui/book_view/lib/rangy/rangy.js").rangy,
    r = e("ydcore:widget/ui/book_view/lib/underscore/underscore.js")._,
    a = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    s = e("ydcore:widget/book_view/txt_reader/js/v/common/note/show.pop.js").PopView;
    ModifyPop = e("ydcore:widget/book_view/txt_reader/js/v/common/note/modify.pop.js").PopView,
    DelConfirm = e("ydcore:widget/book_view/txt_reader/js/v/common/note/del.confirm.js").PopView;
    var c = {};
    c.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    c.string = e("ydcommon:widget/ui/lib/string/string.js");
    var l = c.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: a
    }).extend({
        _init: function(e) {
            var t = this;
            n.extend(t, e, {
                classApplier: i.createClassApplier("js-mark"),
                _popViewList: [],
                marksPosArr: [],
                curViewHasNote: !1,
                selectAreaHasNote: !1,
                pointAreaHasNote: !1,
                pointIsIconArea: !1,
                codeHilightClass: [".code-block-note-color0", ".code-block-note-color1", ".code-block-note-color2", ".code-block-note-color3", ".code-block-note-color4"]
            }),
            t._popViewList.push(new s(e), new ModifyPop(e), new DelConfirm(e)),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = e.Util;
            t.on("v:add-note", e),
            t.on("v:show-view-notes", e),
            t.on("v:clear-notes-state", e),
            t.on("v:get-range-text", e),
            e._bindUIEvent()
        },
        _bindUIEvent: function() {
            var e = this,
            t = e.$textContainer;
            t.on("mousemove", r.throttle(r.bind(e._detectMousePosNote, e), e.isIE678 ? 200 : 50)),
            t.on("mouseleave",
            function() {
                e.curNoteKey = "",
                e.pointAreaHasNote = !1,
                e.mediator.fire("v:point-on-underline", {
                    data: {
                        curNoteKey: "",
                        isIconArea: !1
                    }
                })
            }),
            t.on("mouseup", r.bind(e._detectMouseUpRangeNotes, e))
        },
        addNote: function(e) {
            var t, o, n, r, a = this,
            s = (a.mediator, e.promise),
            c = e.data || {},
            l = c.noteKey;
            return l ? n = a.noteUtil.getNoteByKey(l) : (t = i.getSelection(), o = t.getRangeAt(0), n = a.noteUtil.parseRangeToPos(o), t && t.removeAllRanges()),
            r = {
                noteColor: c.noteColor || 0
            },
            n ? a.mediator.fire("p:add-note", {
                data: {
                    boundary: n,
                    style: r
                }
            }).then(function(e) {
                s.resolve(e)
            }) : s.reject(),
            s
        },
        showViewNotes: function(e) {
            var t = this,
            o = e.promise,
            i = e.data || {},
            r = i.notes || [],
            a = t._getTextContainer(),
            s = [];
            return t.clearNotesState(),
            r.length > 0 ? (t.curViewHasNote = !0, s = t.noteUtil.parsePosToRange(r, a), n.each(s,
            function(e, o) {
                o && t._addUnderLineOnRange(o, r[e])
            })) : t.curViewHasNote = !1,
            o.resolve(s),
            o
        },
        clearNotesState: function() {
            var e = this,
            t = e._getTextContainer(),
            o = e._getUnerlineContainer();
            e.curViewHasNote = !1,
            e.selectAreaHasNote = !1,
            e.pointAreaHasNote = !1,
            e.pointIsIconArea = !1,
            e.marksPosArr = [],
            o.html(""),
            e.$textContainer.removeClass("pointer-in-note-area"),
            t.find(e.codeHilightClass.join(",")).removeClass("code-block-note-color0 code-block-note-color1 code-block-note-color2 code-block-note-color3 code-block-note-color4"),
            i.getSelection().removeAllRanges()
        },
        getRangeText: function(e) {
            var t, o = this,
            n = e.promise,
            i = e.data || {},
            r = i.notes || [],
            a = o._getTextContainer(),
            s = {},
            c = [];
            return c.push({
                page: r[0]
            }),
            t = o.noteUtil.parsePosToRange(c, a)[0],
            s.summary = t ? o._getText(t) : "",
            n.resolve(s),
            n
        },
        _getText: function(e) {
            var t = this,
            o = n('<div class="extract-range-text"></div>').appendTo(t.$body),
            i = n(),
            r = "";
            return o.html(e.toHtml()),
            o.find(".single-page, .normal-single-page, .single-img-p").html("<span>[\u56fe\u7247]</span>"),
            o.find(".ext_code_wrap").html("<span>[\u4ee3\u7801]</span>"),
            o.find("img.atom").replaceWith("<span>[\u56fe\u7247]</span>"),
            o.find(".ext_remark").replaceWith("<span>[\u6ce8\u91ca]</span>"),
            i = o.find(".p-b"),
            i.length > 0 ? n.each(i,
            function(e, t) {
                var o = n(t);
                r += o.text() + "\n"
            }) : r = o.text(),
            o.remove(),
            r
        },
        _addUnderLineOnRange: function(e, t) {
            var o = this,
            n = o.classApplier,
            i = [];
            n.applyToRange(e),
            i = o._getRects(t),
            o.marksPosArr.push.apply(o.marksPosArr, i),
            o._addToContainer(i, t),
            n.undoToRange(e)
        },
        _getRects: function(e) {
            var t, o, i = this,
            a = i._getTextContainer(),
            s = i._getUnerlineContainer(),
            l = a.find(".js-mark"),
            d = s[0].getBoundingClientRect(),
            p = document.documentElement.scrollTop,
            u = document.documentElement.clientTop,
            g = document.documentElement.scrollLeft,
            h = document.documentElement.clientLeft,
            m = [],
            f = [];
            return n.each(l,
            function(t, o) {
                var r = o.getClientRects(),
                a = n(o);
                c.string.trim(a.text()).length && n.each(r,
                function(t, o) {
                    var n = {
                        top: o.top + p - u - d.top,
                        left: o.left + g - h - d.left
                    };
                    if (n.width = o.width ? o.width: o.right - o.left, n.height = o.height ? o.height: o.bottom - o.top, n.bottom = n.top + n.height, n.right = n.left + n.width, i.isIE67) {
                        {
                            var r = parseInt(a.css("font-size"));
                            parseInt(a.css("line-height")) * r
                        }
                        n.top += n.height / 6
                    }
                    n.noteKey = e.ori.key,
                    m.push(n)
                })
            }),
            m.length > 0 && (f = i._mergeRects(m), e.ori.customstr && (t = r.last(f), o = {
                top: t.top - 13,
                left: t.right - 2,
                width: 14,
                height: 14,
                isIcon: !0
            },
            o.bottom = o.top + o.height, o.right = o.left + o.width, o.noteKey = e.ori.key, f.push(o))),
            f
        },
        _mergeRects: function(e) {
            var t = this,
            o = [],
            n = [],
            i = {},
            r = {};
            for (i = e.shift(), o.push(i); r = e.shift();) t._isInSameLine(i, r) ? o.push(r) : (n.push(t._mergeLine(o)), i = r, o = [], o.push(i));
            return o.length > 0 && (n.push(t._mergeLine(o)), o = []),
            n
        },
        _isInSameLine: function(e, t) {
            var o = e.top,
            n = t.top,
            i = 1;
            return Math.abs(o - n) <= i ? !0 : !1
        },
        _mergeLine: function(e) {
            var t = {};
            return t.top = r.min(e,
            function(e) {
                return e.top
            }).top,
            t.bottom = r.max(e,
            function(e) {
                return e.bottom
            }).bottom,
            t.left = r.min(e,
            function(e) {
                return e.left
            }).left,
            t.right = r.max(e,
            function(e) {
                return e.right
            }).right,
            t.width = t.right - t.left,
            t.height = t.bottom - t.top,
            t.noteKey = e[0].noteKey,
            t
        },
        _addToContainer: function(e, t) {
            var o = this,
            i = o._getUnerlineContainer();
            n.each(e,
            function(e, r) {
                var a, s = n('<span class="note-mark"></span>'),
                c = [t.ori.bfi, t.ori.bpi, t.ori.bci].join("-"),
                l = [t.ori.efi, t.ori.epi, t.ori.eci].join("-"),
                d = "mark-color" + (t.ori.style.noteColor || 0);
                s.attr("data-note-begin", c),
                s.attr("data-note-end", l),
                s.addClass([c, l].join("-")).addClass(d),
                r.isIcon && s.addClass("note-mark-icon icon-color" + (t.ori.style.noteColor || 0)),
                s.appendTo(i),
                s.css({
                    top: r.top,
                    left: r.left,
                    width: r.width,
                    height: r.height
                }).show(),
                a = s.offset(),
                r.isIcon && "format" == o.State.currentType && (a.top < 50 || a.top + s.height() >= n(window).height() - 60) && s.hide()
            })
        },
        _detectMousePosNote: function(e) {
            var t = this,
            o = t.mediator,
            r = "",
            a = i.getSelection(),
            s = t._getUnerlineContainer();
            if (t.curNoteKey = "", t.pointAreaHasNote = !1, o.fire("v:point-on-underline", {
                data: {
                    curNoteKey: "",
                    isIconArea: !1
                }
            }), t.curViewHasNote) {
                if (s.length) {
                    var c = s[0].getBoundingClientRect(),
                    l = {
                        pX: e.clientX - c.left,
                        pY: e.clientY - c.top
                    };
                    n.each(t.marksPosArr,
                    function(e, o) {
                        return o.left <= l.pX && o.right >= l.pX && o.top <= l.pY && o.bottom >= l.pY ? (t.pointAreaHasNote = !0, t.pointIsIconArea = o.isIcon ? !0 : !1, r = o.noteKey, !1) : void 0
                    })
                }
                t.curNoteKey = a.isCollapsed ? r: t._isSelectionInSingleNote() ? r: "",
                t.pointAreaHasNote ? t.$textContainer.addClass("pointer-in-note-area") : t.$textContainer.removeClass("pointer-in-note-area"),
                o.fire("v:point-on-underline", {
                    data: {
                        curNoteKey: t.curNoteKey,
                        isIconArea: t.pointIsIconArea
                    }
                })
            }
        },
        _detectMouseUpRangeNotes: function() {
            var e = this,
            t = e.mediator,
            o = e.noteUtil,
            n = i.getSelection(),
            r = [];
            if (e.selectAreaHasNote = !1, t.fire("v:point-on-underline", {
                data: {
                    curNoteKey: e.curNoteKey,
                    isIconArea: e.pointIsIconArea
                }
            }), e.curViewHasNote) {
                if (n.rangeCount && !n.isCollapsed) {
                    var a = o.parseRangeToPos(n.getRangeAt(0));
                    a && e.mediator.fire("p:get-notes-in-boundary", {
                        data: {
                            boundary: a
                        }
                    }).then(function(e) {
                        r = e
                    })
                }
                r.length > 0 && (e.selectAreaHasNote = !0, e.curNoteKey = e._isSelectionInSingleNote() ? r[0].key: "", e.pointIsIconArea = !1),
                t.fire("v:point-on-underline", {
                    data: {
                        curNoteKey: e.curNoteKey,
                        isIconArea: e.pointIsIconArea
                    }
                })
            }
        },
        _isSelectionInSingleNote: function() {
            var e = this,
            t = e.noteUtil,
            o = i.getSelection(),
            n = [],
            r = {};
            return o.rangeCount && !o.isCollapsed && (r = t.parseRangeToPos(o.getRangeAt(0)), r && e.mediator.fire("p:get-notes-in-boundary", {
                data: {
                    boundary: r
                }
            }).then(function(e) {
                n = e
            }), 1 == n.length && t.compareStartPoint(r, n[0]) >= 0 && t.compareEndPoint(r, n[0]) <= 0) ? !0 : !1
        },
        _getUnerlineContainer: function() {
            var e = this,
            t = window.readerState.currentType;
            return "stream" == t ? e.$readerContainer.find("#stream-underline-block") : e.$textContainer.find(".underline-container")
        },
        _getTextContainer: function() {
            var e = this,
            t = window.readerState.currentType;
            return "stream" == t ? e.$readerContainer: e.$textContainer.find(".text-container")
        }
    });
    o.exports.NoteView = l
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/singlepage.view.js",
function(e, i, n) {
    var a = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    t = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    g = {};
    g.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var s = g.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: t
    }).extend({
        _init: function(e) {
            var i = this;
            a.extend(i, e, {}),
            i._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            i = e.mediator;
            i.on("v:scale-single-page",
            function(i) {
                var n = i.data || {};
                n.$pager && e.scaleSinglePage(n.$pager)
            }),
            i.on("v:scale-single-page-bg-img",
            function(i) {
                var n = i.data,
                a = n.type,
                t = n.$singlePageInner,
                g = n.innerWidth,
                s = n.innerHeight;
                e.scaleSinglePageBgImg(a, t, g, s)
            }),
            i.on("v:before-render-to-doc",
            function(i) {
                var n = i.data,
                a = n.format;
                if ("format" === a) {
                    var t = n.dom,
                    g = t.find(".single-page").size() > 0;
                    g ? e.scaleSinglePage(t) : t.height(e.State.containerHeight - e.toolsBarHeight)
                }
            }),
            i.on("v:after-render-to-doc",
            function(n) {
                var a = n.data,
                t = a.format,
                g = a.dom;
                "stream" === t ? e.fixStreamSinglePageBgImg(g) : "format" === t && i.fire("v:switch-tools-area-center", {
                    data: {
                        hideCenter: g.find(".single-page").size() > 0
                    }
                })
            })
        },
        scaleSinglePage: function(e) {
            var i = this,
            n = i.State.containerHeight,
            a = i.defaultWidth - 2;
            e.height(n);
            var t = e.find(".single-page-inner"),
            g = (t.attr("data-scale-value") || "848|1130").split("|");
            g[0] = +g[0],
            g[1] = +g[1];
            var s = i.State.maxSinglePageHeight,
            r = s * g[0] / g[1];
            if (r > a && (r = a, s > 1100 && (s = r * g[1] / g[0])), t.height(s), t.parent().hasClass("copy-page") ? i.fixCopyPageHeight(t, s) : t.width(r), i.isIE678) {
                var o;
                t.hasClass("ext_bg-img_inner") ? o = "inner": t.hasClass("ext_bg-img_outer") && (o = "outer"),
                o && i.mediator.fire("v:scale-single-page-bg-img", {
                    data: {
                        type: o,
                        $singlePageInner: t,
                        innerWidth: r,
                        innerHeight: s
                    }
                })
            }
        },
        fixCopyPageHeight: function(e, i) {
            var n = 220,
            a = 80,
            t = 60,
            g = 40,
            s = 0,
            r = 80,
            o = 30,
            d = .73 * i - n,
            l = .27 * i - a,
            c = 0,
            p = 0,
            f = 0,
            h = 0,
            m = 0;
            d > t + g ? (c = t, p = g) : d > 0 && (c = d * (t / (t + g)), p = d * (g / (t + g))),
            l > r + s + o ? m = o: l > 0 && (h = l * (r / (r + s + o)), f = l * (s / (r + s + o)), m = l * (o / (r + s + o))),
            e.find(".copy-title").css({
                marginTop: c,
                marginBottom: p
            }).end().find(".copy-info-list").css({
                marginTop: f,
                marginBottom: h
            }).find(".info-copytext").css({
                marginTop: m
            })
        },
        fixStreamSinglePageBgImg: function(e) {
            var i = this,
            n = (i.mediator, e.find(".normal-single-page"));
            n.each(function(e, i) {
                var n = a(i),
                t = n.prev();
                0 === t.find(".page-divide").size() && n.prepend('<span class="page-divide"><br></span>');
                var g = n.next();
                g.hasClass(".single-page") || 0 !== g.find(".page-divide").size() || n.append('<span class="page-divide"><br></span>')
            });
            var t = e.find(".copy-page"),
            g = t.next();
            g.size() > 0 && 0 === g.find(".page-divide").size() && t.append('<span class="page-divide"><br></span>'),
            i.isIE678 && n.each(function(e, n) {
                var t, g, s, r = a(n),
                o = r.find(".single-page-inner");
                o.hasClass("ext_bg-img_inner") ? t = "inner": o.hasClass("ext_bg-img_outer") && (t = "outer"),
                t && (g = o.width(), s = o.height(), i.scaleSinglePageBgImg(t, o, g, s))
            })
        },
        scaleSinglePageBgImg: function(e, i, n, a) {
            function t() {
                l = n,
                c = l * v / m
            }
            function g() {
                c = a,
                l = c * m / v
            }
            function s(e, i, t) {
                o = e * (m - n),
                d = e * (v - a),
                o / m >= d / v ? i() : t()
            }
            var r = i.find(".single-page-bg-img");
            if (n = n || i.width(), a = a || i.height(), r.size() > 0) {
                var o, d, l, c, p, f, h, m = r.width(),
                v = r.height(),
                u = 0,
                b = 0;
                "inner" === e ? (p = m > n || v > a, f = n > m && a > v, h = 1) : (p = n > m || a > v, f = m > n && v > a, h = -1),
                p ? s(h, t, g) : f && s( - h, g, t),
                l && c ? (r.height(c).width(l), u = (n - l) / 2, b = (a - c) / 2) : (u = (a - m) / 2, b = (a - v) / 2),
                r.css({
                    marginLeft: u,
                    marginTop: b
                })
            }
        }
    });
    n.exports.SinglePageView = s
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/window.view.js",
function(e, t, i) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    a = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    o = {};
    o.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var r = o.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: a
    }).extend({
        _init: function(e) {
            var t = this;
            n.extend(t, e, {
                winSize: {},
                unloaded: !1
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            function e() {
                var e = t.$win.height(),
                n = t.$win.width(),
                a = "";
                e !== t.winSize.height && (a = "height"),
                n !== t.winSize.width && (a = a ? "both": "width"),
                a && (t.winSize.height = e, t.winSize.width = n, i.fire("v:window-resize", {
                    data: {
                        changedName: a,
                        winSize: t.winSize
                    }
                }))
            }
            var t = this,
            i = t.mediator,
            n = t.Util;
            n.on("v:window-resize", t),
            n.on("v:no-select-range", t),
            n.on("v:can-select-range", t),
            n.on("v:back-to-editor", t),
            t.$doc.on("mousedown", ".no-select-range",
            function() {
                i.fire("v:no-select-range")
            }).on("mouseup",
            function() {
                i.fire("v:can-select-range")
            }),
            t.winSize.height = t.$win.height(),
            t.winSize.width = t.$win.width();
            var a = null;
            t.$win.resize(function() {
                a && clearTimeout(a),
                t.isIE67 ? a = setTimeout(e, 0) : e()
            }).on("unload beforeunload",
            function() {
                if (!t.unloaded) {
                    i.fire("m:send-read-progress"),
                    t.unloaded = !0;
                    for (var e = 1e4; e--;);
                }
            })
        },
        windowResize: function(e) {
            var t = this,
            i = e.data || {},
            n = i.height || t.$win.height(),
            a = Math.max(1, n - t.toolsBarHeight),
            o = {
                format: 300,
                ffkan: 400
            };
            t.State.ffkanBottomHeight = o.ffkan - o.format,
            o[t.State.currentType] > 0 && a < o[t.State.currentType] ? (a = o[t.State.currentType], t.$htmlBody.addClass("txt-format-scroll")) : t.$htmlBody.removeClass("txt-format-scroll"),
            t.$readerContainer.height(a + t.toolsBarHeight),
            t.State.containerHeight = t.$readerContainer.height();
            var r = t.State.containerHeight,
            d = 30;
            "ffkan" === t.State.currentType && (r -= t.State.ffkanBottomHeight, d = 0),
            t.State.maxSinglePageHeight = r,
            r -= t.toolsBarHeight + d,
            t.State.maxPageHeight = r,
            t.mediator.fire("v:windowResize"),
            t.mediator.fire("v:set-container-height", {
                data: {
                    changedName: i.changedName,
                    winHeight: n
                }
            })
        },
        noSelectRange: function() {
            var e = this;
            e.$doc.on("selectstart", e.selectRangeFn)
        },
        canSelectRange: function() {
            var e = this;
            e.$doc.off("selectstart", e.selectRangeFn)
        },
        selectRangeFn: function(e) {
            e.preventDefault(),
            e.stopPropagation()
        },
        backToEditor: function() {
            var e = this;
            if (e.isLocalPreview && window.localStorage && window.localStorage.setItem) {
                var t = e.State.curChapterNum,
                i = e.State.curParagraphIndex,
                n = (Math.max(1, t) - 1) * e.perBlockCount + i;
                n = Math.max(1, n - 1),
                window.localStorage.setItem(e.doc_id, n)
            }
        }
    });
    i.exports.WindowView = r
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/tips.view.js",
function(i, t, e) {
    var s = i("ydcommon:widget/ui/lib/jquery/jquery.js"),
    o = i("ydcommon:widget/ui/js_core/util/util.js"),
    n = i("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    a = {};
    a.lang = i("ydcommon:widget/ui/lib/lang/lang.js"),
    a.cookie = i("ydcommon:widget/ui/lib/cookie/cookie.js");
    var p = a.lang.createClass(function(i) {
        this._init(i)
    },
    {
        superClass: n
    }).extend({
        _init: function(i) {
            var t = this;
            s.extend(t, i, {
                lastPayPrice: (t.payprice / 100).toFixed(2),
                containerTpl: '<div class="ffk-tips-container"></div>',
                tipsConf: {
                    buy: {
                        tpl: ['<div class="ffk-buy-tips tips-item">', '<div class="close"></div>', '<span class="goods-price">\uffe5{{lastPayPrice}}</span>', '<div class="pay-btn"></div>', "</div>"].join(""),
                        offset: {
                            left: -103,
                            top: -65
                        },
                        autoClose: !1
                    },
                    last: {
                        tpl: ['<div class="ffk-last-tips tips-item"></div>'].join(""),
                        offset: {
                            left: -45,
                            top: -80
                        }
                    },
                    "switch": {
                        tpl: ['<div class="ffk-switch-tips tips-item">', '<div class="close"></div>', "</div>"].join(""),
                        offset: {
                            left: -383,
                            top: -25
                        },
                        autoClose: !1
                    }
                },
                showingTips: []
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var i = this,
            t = i.mediator,
            e = i.Util;
            e.on("v:show-buy-tips", i),
            e.on("v:show-last-page-tips", i),
            e.on("v:show-once-switch-tips", i),
            e.on("v:hide-tips", i),
            t.when(["tools:get-last-pay-price"]).then(function(t) {
                i.lastPayPrice = t[0],
                i.tipsConf.buy.tpl = i.tipsConf.buy.tpl.replace("{{lastPayPrice}}", i.lastPayPrice)
            })
        },
        hideTips: function(i) {
            function t(i) {
                i.off(),
                o.recycleDom(i[0]),
                i = null,
                n.fire("v:after-hide-" + p + "-tips")
            }
            var e, s = this,
            n = s.mediator,
            a = i.data || {},
            p = a.name || "";
            if (p) for (var f = s.showingTips.length - 1; f >= 0; f--) e = s.showingTips[f],
            e.name === p && (s.showingTips.splice(f, 1), t(e.$el), e.$el = null);
            else for (; e = s.showingTips.shift();) t(e.$el),
            e.$el = null
        },
        _createTips: function(i, t, e) {
            var o = this,
            n = o.mediator;
            o.$tipsContainer || (o.$tipsContainer = s(o.containerTpl).appendTo(o.$body)),
            n.fire("v:hide-tips", {
                data: {
                    name: i
                }
            }),
            n.fire("v:before-show-" + i + "-tips");
            var a = o.tipsConf[i];
            e = e || 3e3;
            var p = {
                top: 0,
                left: 0
            },
            f = a.offset || p;
            t = t || p,
            t.left = t.left + f.left + "px",
            t.top = t.top + f.top + "px";
            var l = s(a.tpl).css(t).appendTo(o.$tipsContainer);
            return o.showingTips.push({
                name: i,
                $el: l
            }),
            a.autoClose === !1 ? l.on("click", ".close",
            function() {
                n.fire("v:hide-tips", {
                    data: {
                        name: i
                    }
                })
            }) : setTimeout(function() {
                n.fire("v:hide-tips", {
                    data: {
                        name: i
                    }
                })
            },
            e),
            l
        },
        showBuyTips: function() {
            var i = this,
            t = i.mediator,
            e = s(".ffk-page-bar-wrap .ffk-btn");
            if (e.length > 0) {
                t.fire("v:hide-tips", {
                    data: {
                        name: "last"
                    }
                });
                var o = i._createTips("buy", e.offset());
                o.on("click", ".pay-btn",
                function() {
                    t.fire("p:send-ffkan-log", {
                        data: {
                            act_id: 200203
                        }
                    }),
                    i.$body.trigger("pay.start", {
                        pos: "buy.book.ffktips.view"
                    })
                })
            }
        },
        showLastPageTips: function() {
            var i = this,
            t = (i.mediator, s(".ffk-page-bar-wrap .ffk-btn"));
            t.length > 0 && i._createTips("last", t.offset())
        },
        showOnceSwitchTips: function() {
            var i = this;
            if (i.needShow("switch")) {
                var t = s("#tools-bar .ffk-switch-btn");
                t.length > 0 && i._createTips("switch", t.offset())
            }
        },
        needShow: function(i) {
            var t = {
                "switch": 0
            },
            e = !1,
            s = a.cookie.get("ffk-tp") || "0";
            s = s.split("|");
            var o = t[i];
            return "0" === s[o] && (e = !0, s[o] = "1", s = s.join("|"), a.cookie.set("ffk-tp", s, {
                path: "/ebook/",
                expires: 31536e6
            })),
            e
        },
        dispose: function() {
            var i = this,
            t = i.Util;
            t.off(i),
            t.dispose(i)
        }
    });
    e.exports.TipsView = p
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/common.view.js",
function(e, o, i) {
    var t = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    w = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    n = e("ydcore:widget/book_view/txt_reader/js/v/common/comment.view.js").CommentView,
    r = e("ydcore:widget/book_view/txt_reader/js/v/common/pay.view.js").PayView,
    d = e("ydcore:widget/book_view/txt_reader/js/v/common/av.view.js").AVView,
    s = e("ydcore:widget/book_view/txt_reader/js/v/common/qa.view.js").QAView,
    v = (e("ydcore:widget/book_view/txt_reader/js/v/common/progressbar.view.js").ProgressbarView, e("ydcore:widget/book_view/txt_reader/js/v/common/selecttext.view.js").SelectTextView),
    m = e("ydcore:widget/book_view/txt_reader/js/v/common/note.view.js").NoteView,
    c = e("ydcore:widget/book_view/txt_reader/js/v/common/singlepage.view.js").SinglePageView,
    _ = e("ydcore:widget/book_view/txt_reader/js/v/common/window.view.js").WindowView,
    a = e("ydcore:widget/book_view/txt_reader/js/v/common/tips.view.js").TipsView,
    j = {};
    j.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var g = j.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: w
    }).extend({
        _init: function(e) {
            var o = this;
            t.extend(o, e, {
                _viewList: []
            }),
            o._bindEvent(),
            o._viewList.push(new n(e), new r(e), new d(e), new s(e), new v(e), new m(e), new c(e), new _(e), new a(e)),
            o._bindEvent()
        },
        _bindEvent: function() {}
    });
    i.exports.Common = g
});;
define("ydcore:widget/book_view/txt_reader/js/v/ffkan/chapter.view.js",
function(e, t, a) {
    var i = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    n = e("ydcommon:widget/ui/js_core/util/util.js"),
    r = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    o = {};
    o.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var c = o.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: r
    }).extend({
        _init: function(e) {
            var t = this;
            i.extend(t, e, {
                resizeTimer: null,
                $formatContainer: null
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            t.on("v:insert-chapter", e),
            t.on("v:set-container-height", e)
        },
        setContainerHeight: function(e) {
            var t = this,
            a = e.data || {};
            a.changedName && "width" !== a.changedName && (t.resizeTimer && clearTimeout(t.resizeTimer), t.resizeTimer = setTimeout(function() {
                t.mediator && t.$formatContainer && (t.mediator.fire("v:clear-text-container"), t.mediator.fire("p:load-chapter", {
                    data: {
                        chapterNum: t.State.curFfkanNum,
                        progressData: {
                            pn: t.State.curFfkanPn,
                            offset: t.State.curFfkanOffset
                        }
                    }
                }))
            },
            300))
        },
        insertChapter: function(e) {
            var t = this,
            a = t.mediator,
            i = e.data || {},
            r = i.chapterCache,
            o = r.chapterNum;
            t.$formatContainer || a.when("v:create-format-container").then(function(e) {
                t.$formatContainer = e[0]
            }),
            t.$formatContainer.html(r.html);
            var c = t.$formatContainer.children().first();
            r.gotLines || a.when(["v:get-lines", {
                data: {
                    $chapter: c,
                    chapterCache: r
                }
            }]).then(function(e) {
                var t = e[0];
                a.fire("m:set-chapter-cache", {
                    data: {
                        isFfkan: !0,
                        chapterNum: t.chapterNum,
                        chapterCache: t
                    }
                })
            }),
            a.when(["v:cut-pages", {
                data: {
                    $chapter: c,
                    chapterNum: o
                }
            }]).then(function(e) {
                var t = e[0];
                n.recycleDom(c[0]),
                c = null,
                a.fire("v:insert-pages", {
                    data: {
                        chapterNum: o,
                        pageList: t
                    }
                })
            })
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            e.$textContainer.off("click", ".retry-loading"),
            t.off(e),
            t.dispose(e)
        }
    });
    a.exports.ChapterView = c
});;
define("ydcore:widget/book_view/txt_reader/js/v/common_part/lines.view.js",
function(t, e, i) {
    var a = t("ydcommon:widget/ui/lib/jquery/jquery.js"),
    s = t("ydcommon:widget/ui/js_core/util/util.js"),
    n = t("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    r = {};
    r.lang = t("ydcommon:widget/ui/lib/lang/lang.js"),
    r.browser = t("ydcommon:widget/ui/lib/browser/browser.js");
    var h = r.lang.createClass(function(t) {
        this._init(t)
    },
    {
        superClass: n
    }).extend({
        _init: function(t) {
            var e = this;
            a.extend(e, t, {
                dataOffset: 0
            }),
            e._initImgFixedHeight(),
            e._bindEvent()
        },
        _bindEvent: function() {
            var t = this,
            e = (t.mediator, t.Util);
            e.on("v:get-lines", t),
            e.on("v:get-one-paragraph-lines", t)
        },
        _getLineHeight: function(t) {
            var e = t.css("lineHeight");
            if ("px" !== e.substr(e.length - 2)) {
                e = parseFloat(e);
                var i = parseInt(t.css("fontSize"));
                e = parseInt(i * e)
            } else e = parseInt(e);
            return e
        },
        _isInOneLine: function(t, e) {
            return e.top < t.bottom || e.top === t.bottom && "img" !== t.tagName && "img" !== e.tagName
        },
        _initImgFixedHeight: function() {
            var t = this,
            e = {};
            e = r.browser.ie && 8 === r.browser.ie ? {
                "pLineHeight-36": {
                    "height-38": 39,
                    "height-37": 38,
                    "height-36": 38,
                    "height-35": 37,
                    "height-34": 37
                }
            }: r.browser.firefox ? {
                "pLineHeight-36": {
                    "height-40": 41,
                    "height-39": 40,
                    "height-38": 40,
                    "height-37": 39,
                    "height-36": 39,
                    "height-35": 38,
                    "height-34": 38,
                    "height-33": 37,
                    "height-32": 37
                }
            }: {
                "pLineHeight-36": {
                    "height-39": 40,
                    "height-38": 39,
                    "height-37": 39,
                    "height-36": 38,
                    "height-35": 38,
                    "height-34": 37,
                    "height-33": 37
                }
            },
            t._imgFixedHeightMap = e
        },
        _getImgFixedHeight: function(t, e) {
            var i = this,
            a = i._imgFixedHeightMap["pLineHeight-" + t] || {};
            return a["height-" + e] || e
        },
        getParagraphLines: function(t) {
            var e = this;
            if (t.hasClass("single-page") || t.hasClass("no-select-range") || t.hasClass("single-img-p") || t.hasClass("ext_picnote_wrap") || t.hasClass("ext_legends") && t.find("img").size() > 0) {
                if (t.addClass("format-no-divide"), e.dataOffset = 0, t.hasClass("ext_picnote_wrap")) {
                    var i = t.find(".picnote-img img"),
                    s = t.find(".picnote-text");
                    i.addClass("atom").attr("data-paragraph-offset", 0).attr("data-part-len", 1),
                    e.dataOffset += 1,
                    e.getParagraphOffsets(s)
                }
            } else {
                var n = 0,
                h = !1,
                o = t.children();
                e.dataOffset = 0,
                e.getParagraphOffsets(t);
                var g = t.css("fontWeight"),
                f = e._getLineHeight(t),
                l = t.offset().top,
                p = l,
                d = a(),
                m = [],
                u = o.filter("br, span, img").not(".s-p, .text, .span-to-link"),
                c = o.filter(".span-to-link").children().filter("br, span, img").not(".s-p, .text");
                u = u.add(c);
                var v = o.filter("img");
                if (0 === u.size() && 0 === v.size() && (t.wrapInner("<span></span>"), o = t.children(), u = o), 0 === u.size() && 1 === v.size()) t.html("").append(v).addClass("single-img-p format-no-divide");
                else {
                    var b = u.filter(".ext_valign_sup, .ext_valign_sub");
                    b.addClass("sup-sub-line");
                    var _ = [],
                    H = null;
                    u.each(function(t, i) {
                        var s = i.tagName.toLowerCase();
                        if ("br" === s) return void _.push({
                            tagName: "br",
                            lineHeight: f
                        });
                        var n = a(i),
                        h = n.offset().top,
                        o = n.height(),
                        l = h + o,
                        p = 0;
                        if ("img" === s && (n.hasClass("ext_float_left") || n.hasClass("ext_float_right"))) return d.push(i),
                        void _.push({
                            tagName: "floatImg",
                            top: h,
                            height: n.outerHeight(!0),
                            bottom: l
                        });
                        var m = e._getLineHeight(n); ! r.browser.firefox && n.hasClass("ext_bold") && n.css("fontWeight") !== g && (m += 1),
                        "img" === s && (p = e._getImgFixedHeight(f, o)),
                        m = Math.max(f, m, p);
                        var u = Math.ceil(o / m),
                        c = {
                            tagName: s,
                            lineHeight: m,
                            lineCount: u,
                            height: o,
                            top: h,
                            bottom: l
                        };
                        _.push(c)
                    });
                    var x = [],
                    L = function() {};
                    e.isIE6 && (L = function(t) {
                        if (t) for (var e; e = x.shift();) e.rect.height < n && u.eq(e.index).css("margin", (n - e.rect.height) / 2 + "px 0")
                    });
                    var C;
                    a.each(_,
                    function(t, i) {
                        var a = i.lineCount,
                        s = i.lineHeight,
                        r = i.height,
                        o = i.top,
                        g = i.tagName;
                        if ("floatImg" === g) return void(H = i);
                        if ("br" === g) return (0 === t || C === t - 1) && m.push(s),
                        void(C = t);
                        for (var f = 1,
                        l = _[t - f]; l && "floatImg" === l.tagName;) f++,
                        l = _[t - f];
                        if (l && e._isInOneLine(l, i) ? (a--, r -= s, s > n ? (p += s - n, n = s, h = "img" === g, m.pop(), m.push(n)) : n > s && h && (r -= (n - s) / 2, a = Math.ceil(r / s)), L(a > 0)) : (H && o > H.top && ("img" === g && H.bottom > p && m.push(H.bottom - p), H = null), L(!0)), a > 1) {
                            for (var d = s,
                            u = "img" === g,
                            c = 1,
                            v = _[t + c]; v;) if ("floatImg" !== v.tagName) {
                                if (!e._isInOneLine(i, v)) break;
                                if (v.lineHeight > d && (d = v.lineHeight, u = "img" === v.tagName), d = Math.max(d, v.lineHeight), v.lineCount > 1) break;
                                c++,
                                v = _[t + c]
                            } else c++,
                            v = _[t + c];
                            d > s && (r -= (d - s) * (u ? .5 : .7), a = Math.max(1, Math.ceil(r / s)))
                        }
                        for (a > 0 && (n = s, h = "img" === g); a-->0;) m.push(n),
                        p += n;
                        e.isIE6 && "img" === g && x.push({
                            index: t,
                            rect: i
                        })
                    }),
                    b.removeClass("sup-sub-line"),
                    u.size() > 0 && d.size() > 0 && (m = e.getFloatImgLine(t, d, m.concat([]))),
                    t.attr("data-rows-height", m.join("|"))
                }
            }
        },
        getCustomLines: function(t) {
            var e = this,
            i = t.attr("data-line-type");
            switch (i) {
            case "code":
                e.getCodeLineHeight(t);
                break;
            case "list":
                e.getListLineHeight(t),
                e.dataOffset = 0,
                e.getParagraphOffsets(t);
                break;
            case "av":
                e.getOuterLineHeight(t)
            }
        },
        getCodeLineHeight: function(t) {
            var e = this,
            i = e._getLineHeight(t),
            a = e.getBlockTBPadding(t.find(".code-container")),
            s = a.top + a.bottom,
            n = Math.ceil((t.height() - s) / i),
            r = [];
            if (n > 0) {
                for (var h = n; h--;) r.push(i);
                r[0] += a.top,
                r[n - 1] += a.bottom
            } else r.push(a.top + a.bottom);
            t.attr("data-rows-height", r.join("|"))
        },
        getListLineHeight: function(t) {
            var e = this,
            i = t.find("p"),
            a = e.getBlockTBPadding(t),
            s = e._getLineHeight(i),
            n = Math.ceil(t.height() / s),
            r = [];
            if (n > 0) {
                for (var h = n; h--;) r.push(s);
                r[0] += a.top,
                r[n - 1] += a.bottom
            } else r.push(a.top + a.bottom);
            t.attr("data-rows-height", r.join("|"))
        },
        getOuterLineHeight: function(t) {
            t.attr("data-rows-height", t.height())
        },
        getBlockTBPadding: function(t) {
            var e = {
                top: 0,
                bottom: 0
            };
            if (t.size() > 0) {
                var i = ["Top", "Bottom"];
                a.each(i,
                function(i, a) {
                    var s = parseInt(t.css("border" + a + "Width")) || 0,
                    n = parseInt(t.css("padding" + a)) || 0;
                    e[a.toLowerCase()] = s + n
                })
            }
            return e
        },
        getFloatImgLine: function(t, e, i) {
            function s(t) {
                if (t) {
                    var e = a(t),
                    i = e.offset().top,
                    s = parseInt(e.css("marginTop")) || 0;
                    i -= s,
                    n = Math.max(g, i),
                    r = e.outerHeight(!0) - Math.max(0, g - n),
                    h = n + r,
                    o = 0
                }
            }
            var n, r, h, o, g = t.offset().top,
            f = g,
            l = [],
            p = e.toArray(),
            d = p.shift();
            s(d);
            for (var m = 0,
            u = i.length; u > m; m++) {
                if (lineHeight = i[m], d) if (h > f && f >= n) o += lineHeight;
                else {
                    if (f >= h) {
                        o > 0 && l.push(o),
                        d = p.shift(),
                        s(d),
                        m--;
                        continue
                    }
                    l.push(lineHeight)
                } else l.push(lineHeight);
                f += lineHeight
            }
            return l.length < 1 && l.push(r),
            l
        },
        _getFloatImgLine: function(t, e, i) {
            var a = t.offset().top,
            s = [],
            n = 0,
            r = [],
            h = e.first(),
            o = h.offset().top,
            g = parseInt(h.css("marginTop"));
            o -= g;
            for (var f, l = h.outerHeight(!0), p = o + l, d = (end = 0, a), m = a, u = null, c = 0, v = i.length; v > c; c++) f = i[c],
            o >= d + f ? (s.push(f), m += f) : d > p && (null === u && (u = d), r.push(f)),
            d += f;
            return 0 === r.length && (u = p),
            n = u - m,
            n > 0 && s.push(n),
            s.concat(r)
        },
        getLines: function(t) {
            var e = this,
            i = (e.mediator, t.promise),
            n = t.data,
            r = n.$chapter,
            h = n.chapterCache,
            o = r.find(".p-b");
            o.each(function(t, i) {
                var s = a(i);
                s.hasClass("custom-get-line") ? e.getCustomLines(s) : e.getParagraphLines(s)
            });
            var g = a("<div></div>");
            return g.append(r.clone()),
            h.html = g.html(),
            h.gotLines = !0,
            s.recycleDom(g[0]),
            g = null,
            i.resolve(h),
            i
        },
        getOneParagraphLines: function(t) {
            var e = this,
            i = t.data,
            a = i.$p;
            e.getParagraphLines(a)
        },
        getParagraphOffsets: function(t) {
            var e = this,
            i = t.children();
            if (! (t.hasClass("list-dot-icon") || t.hasClass("progress-absent") || 0 == t.contents().length && "IMG" != t.prop("tagName"))) if (1 == t.length && t.hasClass("ext_remark")) t.attr("data-paragraph-offset", e.dataOffset),
            t.attr("data-part-len", 1),
            t.addClass("atom"),
            e.dataOffset += 1;
            else if (i.length > 0) for (var s = 0; s < i.length; s++) e.getParagraphOffsets(a(i[s]));
            else if (t.attr("data-paragraph-offset", e.dataOffset), t.addClass("atom"), "IMG" == t.prop("tagName")) t.attr("data-part-len", 1),
            e.dataOffset += 1;
            else {
                var n = t.text().length;
                t.attr("data-part-len", n),
                e.dataOffset += n
            }
        },
        dispose: function() {
            var t = this,
            e = t.Util;
            e.off(t),
            e.dispose(t)
        }
    });
    i.exports.LinesView = h
});;
define("ydcore:widget/book_view/txt_reader/js/v/common_part/cutpage.view.js",
function(e, t, a) {
    var r = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    n = {};
    n.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var s = n.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: i
    }).extend({
        _init: function(e) {
            var t = this;
            r.extend(t, e, {
                formatContainerTpl: ['<div id="reader-format-container">', '<div class="reader-format-inner"></div>', "</div>"].join(""),
                pagerTpl: ['<div class="format-pager-wrap">', '<div class="format-pager">', '<div class="pager-inner renderClass">', '<div class="underline-container"></div>', '<div class="text-container"></div>', "</div>", "</div>", "</div>"].join(""),
                currentBoundary: null
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            t.on("v:cut-pages", e),
            t.on("v:create-format-container", e),
            t.on("v:send-page-change-log", e)
        },
        sendPageChangeLog: function(e) {
            var t = this,
            a = e.data || {},
            r = a.$pager,
            i = a.promise,
            n = a.isPrev;
            try {
                if (r && i && r.length > 0) {
                    var s = r.find(".p-b"),
                    o = s.first().get(0).className,
                    d = (s.last().get(0).className, /p-b-(\d+-\d+)/),
                    h = o.match(d)[1].split("-"),
                    g = s.size(),
                    p = s.text().length,
                    f = r.data("progress"),
                    c = s;
                    Math.abs(parseInt(f.marginTop) || 0) > 0 && (g -= 1, p -= s.first().text().length, c = c.slice(1));
                    var l = c.filter("h2, h3, h4"),
                    u = {
                        range: 0,
                        cn: h[0],
                        pn: h[1],
                        pc: g,
                        wc: p,
                        reader: t.payFlag,
                        title: l.length > 0 ? 1 : 0,
                        direct: n ? "prev": "next",
                        format: t.State.currentType,
                        act_id: "200025"
                    },
                    m = "p:send-x-log";
                    "ffkan" === u.format && (m = "p:send-ffkan-log"),
                    t.mediator.fire(m, {
                        data: u
                    })
                }
            } catch(v) {}
        },
        createFormatContainer: function(e) {
            var t = this,
            a = e.promise,
            i = r("#reader-format-container");
            return 0 === i.size() && (i = r(t.formatContainerTpl).appendTo(t.$body)),
            i = i.find(".reader-format-inner"),
            a.resolve(i),
            a
        },
        createPager: function(e) {
            var t = this,
            a = r(t.pagerTpl.replace("renderClass", e));
            return a
        },
        cutPages: function(e) {
            var t, a = this,
            i = (a.mediator, e.promise),
            n = e.data,
            s = n.$chapter,
            o = n.chapterNum,
            d = [];
            a.currentBoundary = {};
            var h = 1;
            try {
                for (; t = a.getNextPage(s);) {
                    if (0 === t.$p.size()) {
                        if (a.currentBoundary.bottom) {
                            a.currentBoundary.bottom = [a.currentBoundary.bottom[0].next(), 1];
                            continue
                        }
                        break
                    }
                    t.isEnd && t.pageHeight < t.maxPageHeight && (t.pageHeight = Math.min(t.pageHeight + 5, t.maxPageHeight)),
                    t.chapterNum = o,
                    t.index = h,
                    d.push(t),
                    h++
                }
            } catch(g) {}
            return d = r.map(d,
            function(e, t) {
                var i = a.createPager(e.renderClass);
                if (i.find(".text-container").append(e.$p.clone()).end().find(".pager-inner").css("marginTop", e.marginTop), i.find(".single-page").size() > 0 && (i.addClass("format-single-page-wrap"), e.pageHeight = a.State.maxSinglePageHeight), i.find(".format-pager").height(e.pageHeight), i.data("progress", {
                    marginTop: e.marginTop,
                    index: e.index,
                    chapterNum: e.chapterNum,
                    paragraphIndex: e.paragraphIndex,
                    offset: e.offset,
                    absent: e.absent
                }).data("bottom-boundary", a._getBottomBoundary(t, d)), r.trim(i.text()).length > 0 || i.find("img").size() > 0) return i;
                var n = i.find(".single-page");
                return n.size() > 0 ? i: null
            }),
            i.resolve(d),
            i
        },
        _hasPageDivide: function(e) {
            return e.find(".page-divide").size() > 0
        },
        _isHxTitle: function(e) {
            var t = !1;
            try {
                var a = e.get(0).tagName.toLowerCase();
                /^h[1-6]$/.test(a) && (t = !0)
            } catch(r) {
                t = !1
            }
            return t
        },
        getNextPage: function(e) {
            var t = this;
            t.currentBoundary = t.currentBoundary || {},
            t.currentBoundary.top = t.currentBoundary.bottom || [e.find(".p-b").first(), 1];
            var a = t.currentBoundary.top,
            i = a[0];
            do
            if (i.size() < 1) return;
            while ((t._hasPageDivide(i) || 0 === i.height()) && (i = i.next()));
            var n, s, o, d = parseInt(i.attr("data-paragraph-index")),
            h = a[1],
            g = r(),
            p = i.closest(".txt-reader-wrap").get(0).className,
            f = t.State.maxPageHeight,
            c = 0,
            l = 0,
            u = 0,
            m = 0,
            v = !1,
            x = function(e) {
                return {
                    $p: g,
                    marginTop: c,
                    pageHeight: u,
                    isEnd: e === !0,
                    maxPageHeight: f,
                    renderClass: p,
                    paragraphIndex: d,
                    offset: t._getOffset(i, h),
                    absent: g.first().hasClass("progress-absent")
                }
            },
            w = parseInt(i.css("marginTop")),
            _ = i.offset().top,
            b = i.height(),
            C = _ + b;
            l = _,
            a[1] > 1 ? (c -= w, o = a[1] - 1, s = 0, n = i.attr("data-rows-height").split("|"), r.each(n,
            function(e, t) {
                return e >= o ? !1 : void(s += parseInt(t))
            }), c -= s, l += s) : l -= w;
            var y = !1,
            P = i;
            if (P.hasClass("single-page")) return g = g.add(P),
            u = f,
            t.currentBoundary.bottom = [P.next(), 1],
            x(!0);
            for (; (m = C - l) <= f;) {
                if (g = g.add(P), u = m, P = P.next(), P.size() < 1) {
                    v = !0;
                    break
                }
                if (t._hasPageDivide(P)) {
                    y = !0;
                    break
                }
                if (_ = P.offset().top, _ - l > f) {
                    v = !0;
                    break
                }
                b = P.height(),
                C = _ + b
            }
            var I = 1;
            if (P !== i) {
                var N;
                if (v || y || (N = P.hasClass("format-no-divide"))) {
                    if (P.hasClass("last-p-b") && P.find(".down-page").length) P.removeClass("hide-drop-down"),
                    P.height() > f && P.addClass("hide-drop-down");
                    else if (N) {
                        var T = t.scaleParagraph(P, l, u, f);
                        T && (u = f, g = g.add(P), P = P.next())
                    }
                    return t.currentBoundary.bottom = [P, 1],
                    x(!0)
                }
                t.scaleImgs(P, f),
                n = P.attr("data-rows-height").split("|")
            } else {
                if (I = a[1], P.hasClass("format-no-divide")) return P.hasClass("last-p-b") && P.find(".down-page").length && (P.removeClass("hide-drop-down"), P.height() > f && P.addClass("hide-drop-down")),
                t.scalePage(P, f, p);
                1 === I && t.scaleImgs(P, f),
                n = P.attr("data-rows-height").split("|")
            }
            return s = 0,
            o = 1,
            n = n || [],
            r.each(n,
            function(e, t) {
                return t = parseInt(t),
                s += t,
                C = _ + s,
                (m = C - l) > f ? !1 : (u = m, void o++)
            }),
            t.currentBoundary.bottom = [P, o],
            o > I && (g = g.add(P)),
            0 === g.size() ? t.getOneline(P, f, p) : x(1 >= o)
        },
        _getOffset: function(e, t) {
            var a = 0;
            if (t -= 1, 1 > t) return a;
            var i, n, s, o, d, h, g = 0,
            p = 0,
            f = e.attr("data-rows-height").split("|");
            if (r.each(f,
            function(e, a) {
                a = parseInt(a) || 0,
                g += a,
                t > e && (p += a)
            }), 0 >= g || 0 >= p) a = 0;
            else if (g > p) {
                h = e.css("position"),
                e.css("position", "relative"),
                i = e.find(".atom");
                for (var c = 0; c < i.length; c++) if (s = r(i[c]), o = s.position(), d = s.height(), o.top + d > p) {
                    a = o.top < p ? Number(s.data("paragraph-offset")) + Math.floor((p - o.top) / d * s.attr("data-part-len")) : Number(s.data("paragraph-offset"));
                    break
                }
                e.css("position", h)
            } else n = e.find('.atom[data-part-len!="0"]:last'),
            a = Math.max(Number(n.data("paragraph-offset") || 0) + Number(n.attr("data-part-len") || 0) - 1, 0);
            return a
        },
        _getBottomBoundary: function(e, t) {
            var a, r, i, n, s = t.length - 1,
            o = {},
            d = t[e],
            h = {},
            g = !0;
            return s > e ? (o = t[e + 1], h = {
                marginTop: o.marginTop,
                index: o.index,
                chapterNum: o.chapterNum,
                paragraphIndex: o.paragraphIndex,
                offset: o.offset
            },
            d.$p.last().attr("data-paragraph-index") !== o.$p.first().attr("data-paragraph-index") && (g = !1), h.isInPage = g) : (o = t[e], a = o.$p.last(), r = a.find(".atom").last(), i = a.attr("data-paragraph-index"), n = r.length ? Number(r.attr("data-paragraph-offset")) + Number(r.attr("data-part-len")) : 0, h = {
                marginTop: o.marginTop,
                index: o.index,
                chapterNum: o.chapterNum,
                paragraphIndex: i,
                offset: n,
                isInPage: !0
            }),
            h
        },
        scaleImgs: function(e, t) {
            for (var a, i, n, s, o = this,
            d = 50,
            h = t - d,
            g = e.find("img"), p = g.toArray(), f = 70, c = 0, l = 0, u = p.length; u > l; l++) a = r(p[l]),
            i = a.height(),
            i > h && (n = a.width(), s = Math.max(1, t - f), s > i && (s = Math.max(1, i - f)), newWidth = s / i * n, a.height(s).width(newWidth), c++);
            c > 0 && o.mediator.fire("v:get-one-paragraph-lines", {
                data: {
                    $p: e
                }
            })
        },
        getOneline: function(e, t) {
            var a, i, n = this,
            s = n.currentBoundary.bottom[1];
            return 1 === s && (a = e.attr("data-rows-height").split("|"), i = parseInt(a[s - 1]), t >= i) ? (e.css("margin", 0), n.getNextPage()) : {
                $p: r()
            }
        },
        scaleParagraph: function(e, t, a, r) {
            var i = this,
            n = !1,
            s = !1;
            if ((r - a > 300 || .3 > a / r && r > 300) && ((s = e.find(".ext_picnote").size() > 0) || (n = e.hasClass("single-img-p")))) {
                var o = e.offset().top,
                d = e.height(),
                h = r - (o - t),
                g = h;
                if (s && e.text().length > 0) {
                    var p = e.find(".ext_picnote");
                    if (p.hasClass("ext_picnote_left") || p.hasClass("ext_picnote_right")) return i.scalePicnote(p, h, e);
                    if (g -= e.find(".picnote-text").outerHeight(), 0 >= g) return ! 1
                }
                for (var f, c, l = e.find("img").first(), u = l.height(), m = l.width(), v = u, x = m, w = u - g; d > h;) {
                    if (c = u - w, 0 >= c) return l.height(u).width(m),
                    !1;
                    f = c / v * x,
                    l.height(c).width(f),
                    w += 10,
                    d = e.height()
                }
                return ! 0
            }
            return ! 1
        },
        mergeLegend: function(e) {
            var t = e.find(".picnote-text"),
            a = t.find(".v-s"),
            i = r('<p class="ext_vertical"></p>').append(a);
            return t.html("").append(i),
            i
        },
        _resizeImg: function(e, t, a) {
            var r, i, n = e.height(),
            s = e.width();
            "width" === t ? (i = a, r = i * n / s) : (r = a, i = r * s / n),
            e.height(r).width(i)
        },
        scalePicnote: function(e, t, a, i) {
            var n = this,
            s = a.html(),
            o = n.mergeLegend(e),
            d = e.find("img"),
            h = a.width(),
            g = o.clone(),
            p = o.width() + 15;
            d.height() > t && n._resizeImg(d, "height", t),
            d.width() + p > h && n._resizeImg(d, "width", h - p);
            var f, c, l, u, m, v = !1,
            x = 0,
            w = p;
            if (e.height() > t) for (;;) {
                for (f = 0, c = null, l = o, u = d.height(), m = r(); l;) l.height() > u ? (c = c || r('<p class="ext_vertical"></p>'), c.prepend(l.children().last())) : (f++, m = m.add(l), c && l.after(c), l = c, c = null);
                if (x++, w = p * f, !(d.width() + w > h)) {
                    v = !0;
                    break
                }
                if (! (5 > x)) break;
                n._resizeImg(d, "width", h - w - 2 * p),
                o.replaceWith(o = g.clone()),
                m.remove()
            } else v = !0;
            if (i !== !0 && v === !1) return a.html(s),
            !1;
            var _ = a.find(".picnote-text"),
            b = 0;
            return _.width(w),
            1 === f && (e.hasClass("ext_text-align_bottom") ? b = Math.max(0, d.height() - _.height()) : e.hasClass("ext_text-align_center") && (b = Math.max(0, d.height() - _.height()) / 2)),
            _.css("marginTop", b),
            n.isIE67 && e.width(w + d.width()),
            a.height(t).css("overflow", "hidden"),
            !0
        },
        _getNoScaleHeight: function(e) {
            var t = 50;
            return e.hasClass("ext_picnote_wrap") && (e.find(".ext_picnote_left").size() > 0 || e.find(".ext_picnote_right").size() > 0 ? t = 0 : (t = e.find(".picnote-text").height(), t = Math.max(50, t))),
            t
        },
        scalePage: function(e, t, a) {
            var r = this,
            i = parseInt(e.attr("data-paragraph-index"));
            e = e.clone().insertAfter(e),
            e.css("margin", 0);
            var n = e.height();
            if (n > t) {
                var s = e.find("img").first();
                if (s.size() > 0) {
                    var o = s.parent().parent();
                    if (o.hasClass("ext_picnote") && (o.hasClass("ext_picnote_left") || o.hasClass("ext_picnote_right"))) r.scalePicnote(o, t, e, !0);
                    else {
                        var d = 30;
                        e.text().length > 0 && (d += r._getNoScaleHeight(e));
                        for (var h, g, p = s.height(), f = s.width(), c = t - d; p > c && (h = t - d, !(0 >= h));) g = h / p * f,
                        s.height(h).width(g),
                        d += 10,
                        p = s.height(),
                        f = s.width()
                    }
                }
                e.height(t).css("overflow", "hidden")
            }
            var l = e.next();
            return r.currentBoundary.bottom = [l, 1],
            e.remove(),
            {
                $p: e,
                marginTop: 0,
                pageHeight: t,
                isEnd: !0,
                maxPageHeight: t,
                renderClass: a,
                paragraphIndex: i,
                offset: 0,
                absent: e.first().hasClass("progress-absent")
            }
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            t.off(e),
            t.dispose(e)
        }
    });
    a.exports.CutPageView = s
});;
define("ydcore:widget/book_view/txt_reader/js/v/ffkan/pager.view.js",
function(e, a, n) {
    var t = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    r = {};
    r.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var o = r.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: i
    }).extend({
        _init: function(e) {
            var a = this;
            t.extend(a, e, {
                loadingTpl: ['<div class="loading-block">', '<span class="loading-fail">\u52a0\u8f7d\u5931\u8d25\uff01\u70b9\u51fb<a class="retry-loading">\u91cd\u8bd5</a>\u4e00\u4e0b</span>', "</div>"].join(""),
                pagerContainerTpl: ['<div id="format-pager-container"></div>'].join(""),
                loadFailChapterNum: 0,
                pageList: [],
                pageIndex: -1,
                $pagerContainer: null
            }),
            a._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            a = e.mediator,
            n = e.Util;
            n.on("v:direct-scroll-to-chapter", e),
            n.on("v:clear-text-container", e),
            n.on("v:load-and-remove", e),
            n.on("v:insert-pages", e),
            n.on("v:next-page", e),
            n.on("v:prev-page", e),
            n.on("v:loading-chapter", e),
            n.on("v:loading-fail", e),
            e.$textContainer.on("click", ".retry-loading",
            function() {
                e.$loadingBlock.removeClass("loading-fail-block"),
                a.fire("p:load-chapter", {
                    data: {
                        chapterNum: e.loadFailChapterNum > 0 ? e.loadFailChapterNum: 1
                    }
                })
            })
        },
        directScrollToChapter: function(e) {
            var a = this,
            n = e.data || {},
            i = e.promise,
            r = n.pn,
            o = n.offset,
            d = -1;
            return t.each(a.pageList,
            function(e, a) {
                var n = a.data("progress");
                return r > n.paragraphIndex || r === n.paragraphIndex && o >= n.offset ? void d++:!1
            }),
            d = Math.max(0, d),
            d !== a.pageIndex && a.showPage(d),
            i.resolve(),
            i
        },
        clearTextContainer: function() {
            var e = this;
            e.$pagerContainer && (e.$pagerContainer.empty(), t.each(e.pageList,
            function(e, a) {
                a.remove()
            }), e.pageList = [], e.pageIndex = -1)
        },
        insertPages: function(e) {
            var a = this,
            n = e.data || {};
            a.pageList = n.pageList || [],
            a.showPage(0)
        },
        nextPage: function(e) {
            var a = this,
            n = e.promise;
            if ("next" === a.waitLoading) return void n.resolve();
            var t = a.pageIndex + 1,
            i = a.pageList.length;
            return i > t ? a.showPage(t, n) : n.resolve(),
            n
        },
        prevPage: function(e) {
            var a = this,
            n = e.promise,
            t = a.pageIndex - 1;
            return t >= 0 ? a.showPage(t, n, !0) : n.resolve(),
            n
        },
        showPage: function(e, a, n) {
            function t() {
                i.mediator.fire("v:after-render-to-doc", {
                    data: {
                        dom: d,
                        format: "format"
                    }
                }),
                a && a.resolve()
            }
            var i = this,
            r = i.mediator;
            r.fire("v:hide-all-reader-pop"),
            r.fire("p:hide-comment-pop");
            var o = i.pageList[i.pageIndex];
            r.fire("v:send-page-change-log", {
                data: {
                    $pager: o,
                    promise: a,
                    isPrev: n === !0
                }
            });
            var d = i.pageList[e];
            i.mediator.fire("v:before-render-to-doc", {
                data: {
                    dom: d,
                    format: "format"
                }
            }),
            !a || !o || o.size() < 1 ? (o && o.detach(), d.appendTo(i.$pagerContainer.empty()), t()) : n === !0 ? i.$pagerContainer.prepend(d).css("left", "-100%").animate({
                left: "0"
            },
            function() {
                o.detach(),
                t()
            }) : i.$pagerContainer.append(d).animate({
                left: "-100%"
            },
            function() {
                o.detach(),
                i.$pagerContainer.css("left", 0),
                t()
            }),
            i.pageIndex = e,
            r.fire("v:load-and-remove")
        },
        loadAndRemove: function() {
            var e = this,
            a = e.mediator,
            n = e.pageList[e.pageIndex],
            t = n.data("progress");
            e.State.curFfkanPn = t.paragraphIndex,
            e.State.curFfkanOffset = t.offset;
            var i = 0 === e.pageIndex,
            r = e.pageIndex === e.pageList.length - 1;
            a.fire("v:set-page-bar-state", {
                data: {
                    isFirstPage: i,
                    isLastPage: r
                }
            }),
            r && a.fire("v:send-page-change-log", {
                data: {
                    $pager: n,
                    promise: !0,
                    isPrev: !1
                }
            })
        },
        loadingChapter: function() {
            var e = this;
            e.$loadingBlock || (e.$loadingBlock = t(e.loadingTpl)),
            e.$pagerContainer || (e.$pagerContainer = t(e.pagerContainerTpl).appendTo(e.$textContainer)),
            -1 === e.pageIndex && e.$pagerContainer.append(e.$loadingBlock)
        },
        loadingFail: function(e) {
            var a = this,
            n = e.data || {};
            a.$loadingBlock.addClass("loading-fail-block"),
            a.loadFailChapterNum = n.chapterNum
        },
        dispose: function() {
            var e = this,
            a = e.Util;
            e.$textContainer.off("click", ".retry-loading"),
            a.off(e),
            a.dispose(e)
        }
    });
    n.exports.PagerView = o
});;
define("ydcore:widget/book_view/txt_reader/js/v/common_part/operate.view.js",
function(e, t, o) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    a = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    i = {};
    i.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var r = i.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: a
    }).extend({
        _init: function(e) {
            var t = this;
            n.extend(t, e, {
                isTop: !1,
                isBottom: !1,
                isPaging: !1,
                scrollStep: 40,
                onScrollTimer: null,
                touchX: 0,
                touchY: 0,
                touchStartY: 0,
                touchStartTime: null,
                domEventsList: [],
                catalogEl: n(".catalog-box").get(0),
                bookmarkEl: n(".bdjson-bookmark-box").get(0),
                noteEl: n(".bdjson-booknote-box").get(0)
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            e.domEventsList.push({
                dom: e.$doc,
                eventName: "touchstart",
                fn: n.proxy(e.touchstart, e)
            },
            {
                dom: e.$doc,
                eventName: "touchmove",
                fn: n.proxy(e.touchmove, e)
            },
            {
                dom: e.$doc,
                eventName: "touchend",
                fn: n.proxy(e.touchend, e)
            },
            {
                dom: e.$doc,
                eventName: "mousewheel",
                fn: n.proxy(e.mousewheel, e)
            },
            {
                dom: e.$doc,
                eventName: "keydown",
                fn: n.proxy(e.keydown, e)
            }),
            n.each(e.domEventsList,
            function(e, t) {
                t.dom.on(t.eventName, t.fn)
            }),
            t.on("v:prev-screen", e),
            t.on("v:next-screen", e),
            t.on("v:set-page-scroll-state", e)
        },
        touchstart: function(e) {
            var t = this,
            o = e.originalEvent,
            n = o.touches[0];
            t.touchX = n.clientX,
            t.touchStartX = n.clientX,
            t.touchStartTime = new Date
        },
        touchmove: function(e) {
            var t = this;
            e.preventDefault(),
            e.stopPropagation();
            var o = e.originalEvent,
            a = o.touches[0],
            i = o.target;
            if (! (t.catalogEl && (t.catalogEl === i || n.contains(t.catalogEl, i)) || t.bookmarkEl && (t.bookmarkEl === i || n.contains(t.bookmarkEl, i)) || t.noteEl && (t.noteEl === i || n.contains(t.noteEl, i)) || n(i).hasClass("customstr-input") || n(i).closest(".note-show-pop").length)) {
                var r = a.clientX;
                r !== t.touchX && (t.touchX = r)
            }
        },
        touchend: function(e) {
            var t = this,
            o = t.mediator,
            n = (e.originalEvent, t.touchX - t.touchStartX);
            Math.abs(n) > 30 && o.fire(n > 0 ? "v:prev-screen": "v:next-screen")
        },
        _isBottom: function() {
            var e = this;
            return e.$readerContainer.offset().top + e.$readerContainer.height() <= e.$win.height() + e.$doc.scrollTop() ? !0 : !1
        },
        _isTop: function() {
            var e = this;
            return 0 === e.$doc.scrollTop() ? !0 : !1
        },
        mousewheel: function(e, t) {
            var o = this,
            a = o.mediator,
            i = e.target;
            if (! (o.catalogEl && (o.catalogEl === i || n.contains(o.catalogEl, i)) || o.bookmarkEl && (o.bookmarkEl === i || n.contains(o.bookmarkEl, i)) || o.noteEl && (o.noteEl === i || n.contains(o.noteEl, i)) || n(i).hasClass("customstr-input") || n(i).closest(".note-show-pop").length)) {
                {
                    o.scrollStep
                }
                0 > t && o._isBottom() ? (a.fire("v:next-screen"), a.fire("p:send-log", {
                    data: {
                        logAct: "page.wheel",
                        direct: "next",
                        pageType: "format"
                    }
                })) : t > 0 && o._isTop() && (a.fire("v:prev-screen"), a.fire("p:send-log", {
                    data: {
                        logAct: "page.wheel",
                        direct: "prev",
                        pageType: "format"
                    }
                }))
            }
        },
        keydown: function(e) {
            {
                var t = this,
                o = t.mediator,
                n = !0;
                t.scrollStep
            }
            switch (e.which) {
            case 37:
                o.fire("v:prev-screen"),
                o.fire("p:send-log", {
                    data: {
                        logAct: "page.press",
                        direct: "prev",
                        pageType: "format"
                    }
                });
                break;
            case 39:
                o.fire("v:next-screen"),
                o.fire("p:send-log", {
                    data: {
                        logAct: "page.press",
                        direct: "next",
                        pageType: "format"
                    }
                });
                break;
            default:
                n = !1
            }
            n && e.preventDefault()
        },
        prevScreen: function() {
            var e = this;
            e.isPaging || (e.isPaging = !0, e.mediator.when(["v:prev-page"]).then(function() {
                e.isPaging = !1
            }), e.mediator.fire("p:send-log", {
                data: {
                    logAct: "pagecount"
                }
            }))
        },
        nextScreen: function() {
            var e = this;
            e.isPaging || (e.isPaging = !0, e.mediator.when(["v:next-page"]).then(function() {
                e.isPaging = !1
            }), e.mediator.fire("p:send-log", {
                data: {
                    logAct: "pagecount"
                }
            }))
        },
        setPageScrollState: function(e) {
            var t = this,
            o = e.data || {};
            t.isTop = o.isTop,
            t.isBottom = o.isBottom
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            n.each(e.domEventsList,
            function(e, t) {
                t.dom.off(t.eventName, t.fn)
            }),
            e.domEventsList = [],
            t.off(e),
            t.dispose(e)
        }
    });
    o.exports.OperateView = r
});;
define("ydcore:widget/book_view/txt_reader/js/v/ffkan/pagebar.view.js",
function(a, e, i) {
    var t = a("ydcommon:widget/ui/lib/jquery/jquery.js"),
    n = a("ydcommon:widget/ui/js_core/util/util.js"),
    s = a("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    r = {};
    r.lang = a("ydcommon:widget/ui/lib/lang/lang.js");
    var o = r.lang.createClass(function(a) {
        this._init(a)
    },
    {
        superClass: s
    }).extend({
        _init: function(a) {
            var e = this;
            t.extend(e, a, {
                pageBarTpl: ['<div class="ffk-page-bar-wrap">', '<div class="page-bar">', '<a href="#" class="prev-page page-btn">\u4e0a\u4e00\u9875</a>', '<span class="split-line ib"></span>', '<a href="#" class="next-page page-btn">\u4e0b\u4e00\u9875</a>', "</div>", '<div class="fan-yi-fan">', '<a href="#" class="ffk-btn" hidefocus="true"></a>', "</div>", "</div>"].join(""),
                isLoading: !1,
                f1fCount: 0
            }),
            e.$pageBar = t(e.pageBarTpl).appendTo(e.$body),
            e._bindEvent()
        },
        _bindEvent: function() {
            var a = this,
            e = a.mediator,
            i = a.Util;
            i.on("v:set-page-bar-state", a),
            i.on("v:loading-chapter", a),
            i.on("v:fan-yi-fan", a),
            t(a.el).on("click", ".fan-1-fan-link",
            function(a) {
                a.preventDefault(0),
                e.fire("v:fan-yi-fan")
            }),
            a.$pageBar.on("click", ".ffk-btn",
            function(a) {
                a.preventDefault(),
                e.fire("v:fan-yi-fan", {
                    data: {
                        fromBottomBtn: !0
                    }
                })
            }).on("click", ".page-btn",
            function(a) {
                a.preventDefault();
                var i = t(this);
                if (!i.hasClass("disabled")) {
                    var n = "v:next-screen";
                    i.hasClass("prev-page") && (n = "v:prev-screen"),
                    e.fire(n)
                }
            })
        },
        fanYiFan: function(a) {
            var e = this,
            i = e.mediator,
            t = a.data || {};
            if (!e.isLoading) {
                if (i.fire("v:hide-tips", {
                    data: {
                        name: "buy"
                    }
                }), t.fromBottomBtn && e.f1fCount++, 5 === e.f1fCount) return e.f1fCount++,
                void i.fire("v:show-buy-tips");
                i.fire("p:send-ffkan-log", {
                    data: {
                        act_id: 200202
                    }
                }),
                e.isLoading = !0;
                var n = e.State.curFfkanNum + 1;
                n > e.ffkanData.blockCount && (n = 1),
                console.log("isLoading: ", e.isLoading),
                i.when(["p:load-chapter", {
                    data: {
                        chapterNum: n
                    }
                }]).then(function() {
                    e.isLoading = !1,
                    n === e.ffkanData.blockCount && i.fire("v:show-last-page-tips")
                })
            }
        },
        loadingChapter: function() {
            var a = this;
            a._setPageBarState({
                isFirstPage: !0,
                isLastPage: !0
            })
        },
        setPageBarState: function(a) {
            var e = this,
            i = (e.mediator, a.data || {});
            e._setPageBarState(i)
        },
        _setPageBarState: function(a) {
            var e = this;
            e.$pageBar.find(".prev-page")[a.isFirstPage ? "addClass": "removeClass"]("disabled"),
            e.$pageBar.find(".next-page")[a.isLastPage ? "addClass": "removeClass"]("disabled")
        },
        dispose: function() {
            var a = this,
            e = a.Util;
            a.$pageBar.off("click"),
            n.recycleDom(a.$pageBar[0]),
            a.$pageBar = null,
            e.off(a),
            e.dispose(a)
        }
    });
    i.exports.PageBarView = o
});;
define("ydcore:widget/book_view/txt_reader/js/v/ffkan/ffkan.view.js",
function(e, i, t) {
    var o = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    s = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    a = e("ydcore:widget/book_view/txt_reader/js/v/ffkan/chapter.view.js").ChapterView,
    r = e("ydcore:widget/book_view/txt_reader/js/v/common_part/lines.view.js").LinesView,
    n = e("ydcore:widget/book_view/txt_reader/js/v/common_part/cutpage.view.js").CutPageView,
    w = e("ydcore:widget/book_view/txt_reader/js/v/ffkan/pager.view.js").PagerView,
    d = e("ydcore:widget/book_view/txt_reader/js/v/common_part/operate.view.js").OperateView,
    v = e("ydcore:widget/book_view/txt_reader/js/v/ffkan/pagebar.view.js").PageBarView,
    c = {};
    c.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var _ = c.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: s
    }).extend({
        _init: function(e) {
            var i = this;
            o.extend(i, e, {
                _viewList: []
            }),
            i._viewList.push(new a(e), new r(e), new n(e), new d(e), new v(e), new w(e))
        },
        dispose: function() {
            var e = this,
            i = e.Util;
            o.each(e._viewList,
            function(e, i) {
                i.disposed || i.dispose()
            }),
            e.$textContainer.addClass("min-height").html("").css("top", 0),
            i.dispose(e)
        }
    });
    t.exports.Ffkan = _
});;
define("ydcore:widget/book_view/txt_reader/js/v/stream/chapter.view.js",
function(e, a, o) {
    var t = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    n = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    i = {};
    i.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var r = i.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: n
    }).extend({
        _init: function(e) {
            var a = this;
            t.extend(a, e, {
                loadingTpl: ['<div class="loading-block">', '<span class="loading-fail">\u52a0\u8f7d\u5931\u8d25\uff01\u70b9\u51fb<a class="retry-loading">\u91cd\u8bd5</a>\u4e00\u4e0b</span>', "</div>"].join(""),
                $prevLoadingBlock: null,
                $nextLoadingBlock: null,
                $formatContainer: null
            }),
            a._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            a = e.mediator,
            o = e.Util;
            o.on("v:loading-chapter", e),
            o.on("v:insert-chapter", e),
            o.on("v:loading-fail", e),
            o.on("v:remove-loading-block", e),
            o.on("v:clear-text-container", e),
            e.$textContainer.on("click", ".retry-loading",
            function() {
                var e = t(this).closest(".loading-block");
                e.removeClass("loading-fail-block"),
                a.fire("v:load-and-remove", {
                    data: {
                        loadingFail: !0
                    }
                })
            })
        },
        clearTextContainer: function() {
            var e = this;
            e.$nextLoadingBlock = null,
            e.$prevLoadingBlock = null,
            e.$textContainer.html("");
            var a = -e.State.scrollTop;
            e.mediator.fire("v:change-top", {
                data: {
                    top: a
                }
            })
        },
        removeLoadingBlock: function(e) {
            var a = this,
            o = e.data || {};
            if (o.next === !0 && a.$nextLoadingBlock && (a.$nextLoadingBlock.remove(), a.$nextLoadingBlock = null), o.prev === !0 && a.$prevLoadingBlock) {
                var t = a.$prevLoadingBlock.outerHeight();
                a.$prevLoadingBlock.remove(),
                a.$prevLoadingBlock = null,
                a.mediator.fire("v:change-top", {
                    data: {
                        top: t,
                        loadingBlock: !0
                    }
                })
            }
        },
        loadingFail: function(e) {
            var a, o = this,
            t = e.data,
            n = t.direction;
            a = "up" === n ? "$prevLoadingBlock": "$nextLoadingBlock",
            o[a] && o[a].addClass("loading-fail-block")
        },
        insertChapter: function(e) {
            var a, o = this,
            n = e.data || {},
            i = n.chapterCache,
            r = n.direction;
            o.$formatContainer || o.mediator.when("v:create-format-container").then(function(e) {
                o.$formatContainer = e[0]
            }),
            o.$formatContainer.html(i.html);
            var l = o.$formatContainer.children().first();
            if (i.gotLines || o.mediator.when(["v:get-lines", {
                data: {
                    $chapter: l,
                    chapterCache: i
                }
            }]).then(function(e) {
                var a = e[0];
                o.mediator.fire("m:set-chapter-cache", {
                    data: {
                        chapterNum: a.chapterNum,
                        chapterCache: a
                    }
                })
            }), a = "up" === r ? "$prevLoadingBlock": "$nextLoadingBlock", o[a]) {
                l = t(i.html),
                o.mediator.fire("v:before-render-to-doc", {
                    data: {
                        dom: l,
                        format: "stream"
                    }
                });
                var d = o[a].outerHeight();
                if (o[a].replaceWith(l), o.mediator.fire("v:after-render-to-doc", {
                    data: {
                        dom: l,
                        format: "stream"
                    }
                }), "up" === r) {
                    var c = l.outerHeight() - d;
                    o.mediator.fire("v:change-top", {
                        data: {
                            top: -c
                        }
                    })
                }
                o[a] = null
            }
        },
        loadingChapter: function(e) {
            var a, o, n, i = this,
            r = e.promise,
            l = e.data || {},
            d = l.direction || "down";
            if ("up" === d ? (o = "prepend", n = "$prevLoadingBlock") : (o = "append", n = "$nextLoadingBlock"), !i[n]) {
                if (a = t(i.loadingTpl), i.$textContainer[o](a), "up" === d) {
                    var c = a.outerHeight();
                    i.mediator.fire("v:change-top", {
                        data: {
                            top: -c,
                            loadingBlock: !0
                        }
                    })
                }
                i[n] = a
            }
            return r.resolve(a),
            r
        },
        dispose: function() {
            var e = this,
            a = e.Util;
            e.$textContainer.off("click", ".retry-loading"),
            a.off(e),
            a.dispose(e)
        }
    });
    o.exports.ChapterView = r
});;
define("ydcore:widget/book_view/txt_reader/js/v/stream/operate.view.js",
function(e, t, o) {
    var a = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    n = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    r = {};
    r.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    r.platform = e("ydcommon:widget/ui/lib/platform/platform.js");
    var i = r.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: n
    }).extend({
        _init: function(e) {
            var t = this;
            a.extend(t, e, {
                isTop: !1,
                isBottom: !1,
                isScreenChanging: !1,
                scrollStep: 40,
                onScrollTimer: null,
                logTime: null,
                isMac: r.platform.isMacintosh,
                touchX: 0,
                touchY: 0,
                touchStartY: 0,
                touchStartTime: null,
                domEventsList: [],
                catalogEl: a(".catalog-box").get(0),
                bookmarkEl: a(".bdjson-bookmark-box").get(0),
                noteEl: a(".bdjson-booknote-box").get(0)
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            e.domEventsList.push({
                dom: e.$doc,
                eventName: "touchstart",
                fn: a.proxy(e.touchstart, e)
            },
            {
                dom: e.$doc,
                eventName: "touchmove",
                fn: a.proxy(e.touchmove, e)
            },
            {
                dom: e.$doc,
                eventName: "touchend",
                fn: a.proxy(e.touchend, e)
            },
            {
                dom: e.$doc,
                eventName: "mousewheel",
                fn: a.proxy(e.mousewheel, e)
            },
            {
                dom: e.$doc,
                eventName: "keydown",
                fn: a.proxy(e.keydown, e)
            }),
            a.each(e.domEventsList,
            function(e, t) {
                t.dom.on(t.eventName, t.fn)
            }),
            t.on("v:back-top", e),
            t.on("v:prev-screen", e),
            t.on("v:next-screen", e),
            t.on("v:set-page-scroll-state", e)
        },
        changeTop: function(e) {
            var t = this;
            if (!t.isScreenChanging && e) {
                var o = new Date; (!t.logTime || o - t.logTime > 1e3) && (t._sendViewLog(0, e > 0), t.logTime = o);
                var a = t.mediator;
                a.fire("v:change-top", {
                    data: {
                        top: e
                    }
                }),
                t.onScrollTimer || (t.onScrollTimer = setTimeout(function() {
                    t.onScrollTimer = null,
                    a.fire("v:load-and-remove")
                },
                100))
            }
        },
        _sendViewLog: function(e, t) {
            var o = this;
            try {
                var a = parseInt(o.State.curChapterNum) || 1,
                n = parseInt(o.State.curParagraphIndex) || 1,
                r = {
                    range: e || 0,
                    cn: a,
                    pn: n,
                    direct: t ? "prev": "next",
                    format: "stream",
                    act_id: "200025"
                };
                o.mediator.fire("p:send-x-log", {
                    data: r
                })
            } catch(i) {}
        },
        touchstart: function(e) {
            var t = this,
            o = e.originalEvent,
            a = o.touches[0];
            t.touchY = a.clientY,
            t.touchStartY = a.clientY,
            t.touchStartTime = new Date
        },
        touchmove: function(e) {
            var t = this;
            e.preventDefault(),
            e.stopPropagation();
            var o = e.originalEvent,
            n = o.touches[0],
            r = o.target;
            if (! (t.catalogEl && (t.catalogEl === r || a.contains(t.catalogEl, r)) || t.bookmarkEl && (t.bookmarkEl === r || a.contains(t.bookmarkEl, r)) || t.noteEl && (t.noteEl === r || a.contains(t.noteEl, r)) || a(r).hasClass("customstr-input") || a(r).closest(".note-show-pop").length)) {
                var i = n.clientY;
                if (i !== t.touchY) {
                    var c = i - t.touchY;
                    t.touchY = i,
                    t.changeTop(c)
                }
            }
        },
        touchend: function(e) {
            var t = this,
            o = (e.originalEvent, new Date - t.touchStartTime),
            a = t.touchY - t.touchStartY;
            300 > o && Math.abs(a / o) > .5 && t.changeScreenView(a > 0)
        },
        mousewheel: function(e, t, o, n) {
            var r = this,
            i = e.target;
            if (! (r.catalogEl && (r.catalogEl === i || a.contains(r.catalogEl, i)) || r.bookmarkEl && (r.bookmarkEl === i || a.contains(r.bookmarkEl, i)) || r.noteEl && (r.noteEl === i || a.contains(r.noteEl, i)) || a(i).hasClass("customstr-input") || a(i).closest(".note-show-pop").length)) {
                var c = r.scrollStep;
                r.isMac && (t = n || t, c = 1);
                var s = t * c;
                r.changeTop(s)
            }
        },
        keydown: function(e) {
            var t = this,
            o = t.mediator,
            a = !0,
            n = t.scrollStep;
            switch (e.which) {
            case 40:
                n = -1 * t.scrollStep;
            case 38:
                t.changeTop(n);
                break;
            case 33:
                o.fire("v:prev-screen"),
                o.fire("p:send-log", {
                    data: {
                        logAct: "page.press",
                        direct: "prev",
                        pageType: "stream"
                    }
                });
                break;
            case 32:
            case 34:
                o.fire("v:next-screen"),
                o.fire("p:send-log", {
                    data: {
                        logAct: "page.press",
                        direct: "next",
                        pageType: "stream"
                    }
                });
                break;
            case 36:
                o.fire("p:scroll-to-chapter", {
                    data: {
                        chapterNum: 1,
                        progress: 0
                    }
                });
                break;
            case 35:
                o.fire("p:scroll-to-chapter", {
                    data: {
                        chapterNum: t.freeChapterNum,
                        progress: 1
                    }
                });
                break;
            default:
                a = !1
            }
            a && e.preventDefault()
        },
        backTop: function() {
            var e = this,
            t = -e.State.scrollTop;
            e.changeTop(t)
        },
        prevScreen: function() {
            var e = this;
            e.changeScreenView(!0)
        },
        nextScreen: function() {
            var e = this;
            e.changeScreenView()
        },
        changeScreenView: function(e) {
            var t = this;
            if (!t.isScreenChanging) {
                t._sendViewLog(1, e === !0);
                var o = Math.max(0, t.State.containerHeight - t.toolsBarHeight - 30);
                if (e === !0) {
                    if (t.isTop) return
                } else {
                    if (t.isBottom) return;
                    o = -1 * o
                }
                t.isScreenChanging = !0,
                t.mediator.fire("v:change-top", {
                    data: {
                        top: o,
                        easing: !0
                    }
                }).then(function() {
                    t.isScreenChanging = !1,
                    t.mediator.fire("v:load-and-remove")
                }),
                t.mediator.fire("p:send-log", {
                    data: {
                        logAct: "pagecount"
                    }
                })
            }
        },
        setPageScrollState: function(e) {
            var t = this,
            o = e.data || {};
            t.isTop = o.isTop,
            t.isBottom = o.isBottom
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            a.each(e.domEventsList,
            function(e, t) {
                t.dom.off(t.eventName, t.fn)
            }),
            e.domEventsList = [],
            t.off(e),
            t.dispose(e)
        }
    });
    o.exports.OperateView = i
});;
define("ydcore:widget/book_view/txt_reader/js/v/stream/scroll.view.js",
function(t, e, a) {
    var o = t("ydcommon:widget/ui/lib/jquery/jquery.js"),
    r = t("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    n = {};
    n.lang = t("ydcommon:widget/ui/lib/lang/lang.js");
    var i = n.lang.createClass(function(t) {
        this._init(t)
    },
    {
        superClass: r
    }).extend({
        _init: function(t) {
            var e = this;
            o.extend(e, t, {
                top: 0,
                bottom: 0,
                loadTop: 0,
                loadBottom: 0,
                removeTop: 0,
                removeBottom: 0,
                viewLogTimer: null
            }),
            e._bindEvent()
        },
        _bindEvent: function() {
            var t = this,
            e = (t.mediator, t.Util);
            e.on("v:prevent-default-scroll", t),
            e.on("v:load-and-remove", t),
            e.on("v:set-container-height", t),
            e.on("v:change-top", t)
        },
        preventDefaultScroll: function() {
            var t = this;
            t.$win.scrollTop(0)
        },
        loadAndRemove: function(t) {
            var e = this,
            a = e.mediator,
            r = t.data || {},
            n = e.State,
            i = n.showingChapterNumList,
            s = [],
            p = !1,
            d = !1,
            l = !1,
            f = null,
            c = null,
            h = 0,
            g = null,
            m = null,
            v = 0,
            u = 0,
            b = o(),
            w = !1,
            x = !1,
            C = e.$textContainer.children().not(".loading-block");
            if (0 === C.size()) return void(r.loadingFail === !0 && a.fire("p:first-load"));
            C.each(function(t, a) {
                var r = o(a),
                n = r.offset().top,
                I = r.outerHeight(),
                T = n + I;
                if (T < e.removeTop) u += I,
                b = b.add(r),
                w = !0;
                else if (n > e.removeBottom) b = b.add(r),
                x = !0;
                else {
                    var B = i[t];
                    s.push(B),
                    p || (p = !0, n >= e.loadTop && (d = !0)),
                    l = T <= e.loadBottom,
                    !f && T > e.top && (h = B, g = r, f = [B, e.top - n, I]),
                    !m && T >= e.bottom ? (m = r, v = B) : t == C.length - 1 && T <= e.bottom && (m = r, v = B),
                    n <= e.bottom && (c = [B, e.bottom - n, I])
                }
            }),
            m || (m = g),
            n.showingChapterNumList = s;
            var I = 1,
            T = 0,
            B = 0;
            if (g) {
                var S = null;
                g.find(".p-b:not(.progress-absent,.no-select-range):not(:empty)").each(function(t, a) {
                    var r = o(a),
                    n = r.offset().top,
                    i = r.height();
                    return S = r,
                    n <= e.top ? (B = n, n + i >= e.top ? !1 : void 0) : !1
                }),
                S && (I = parseInt(S.attr("data-paragraph-index")) || 1, T = e._getOffset(S, e.top - B))
            }
            var _ = 1,
            k = 0,
            y = 0;
            if (m) {
                var $ = null;
                m.find(".p-b:not(.progress-absent,.no-select-range):not(:empty)").each(function(t, e) {
                    var a = o(e),
                    r = a.offset().top,
                    n = a.height();
                    return $ = a,
                    y = r,
                    r + n <= o(window).height() ? void 0 : !1
                }),
                $ && (_ = parseInt($.attr("data-paragraph-index")) || 1, k = e._getBottomOffset($, o(window).height() - y))
            }
            var H = !1;
            S && (H = S.hasClass("single-page")),
            a.fire("v:switch-tools-area-center", {
                data: {
                    hideCenter: H
                }
            });
            var j = n.curChapterNum,
            L = n.curParagraphIndex,
            N = n.curParagraphOffset; (j !== h || L !== I || T !== N) && (n.curChapterNum = h, n.curParagraphIndex = I, n.curParagraphOffset = T, n.curBottomInfo = {
                chapterNum: v || 1,
                paragraphIndex: _ || 1,
                offset: y || 0
            },
            S && !S.hasClass("progress-absent") && 1 != S.find(".page-divide").length && a.fire("p:save-read-progress", {
                data: {
                    cn: h,
                    pn: I,
                    offset: T,
                    docid: e.doc_id
                }
            }), a.fire("p:paragraph-change", {
                data: {
                    cn: h,
                    pn: I,
                    offset: T
                }
            })),
            a.fire("p:get-progress", {
                data: {
                    first: f,
                    last: c
                }
            }),
            a.fire("v:remove-loading-block", {
                data: {
                    prev: w,
                    next: x
                }
            }),
            b.remove(),
            u > 0 && e.changeTop({
                data: {
                    top: u
                }
            }),
            e.loadCurViewNotes(S, $),
            a.fire("p:pre-load", {
                data: {
                    prev: d,
                    next: l
                }
            }),
            e.viewLogTimer && clearTimeout(e.viewLogTimer),
            e.viewLogTimer = setTimeout(function() {
                a.fire("p:send-view-log")
            },
            300)
        },
        _getOffset: function(t, e) {
            var a, r, n, i, s, p, d, l = 0;
            if (d = p = t.height(), 0 === d || 0 >= e) l = 0;
            else if (d > e) {
                s = t.css("position"),
                t.css("position", "relative"),
                a = t.find('.atom[data-part-len!="0"]');
                for (var f = 0; f < a.length; f++) if (n = o(a[f]), i = n.position(), p = n.height(), i.top + p > e) {
                    l = i.top < e ? parseInt(n.data("paragraph-offset")) + Math.floor((e - i.top) / p * n.attr("data-part-len")) : parseInt(n.data("paragraph-offset"));
                    break
                }
                t.css("position", s)
            } else r = t.find('.atom[data-part-len!="0"]:last'),
            l = Math.max(parseInt(r.data("paragraph-offset") || 0) + parseInt(r.attr("data-part-len") || 0) - 1, 0);
            return l
        },
        _getBottomOffset: function(t, e) {
            var a, r, n, i, s, p, d, l = 0;
            if (d = p = t.height(), 0 === d || 0 >= e) l = 0;
            else if (d > e) {
                s = t.css("position"),
                t.css("position", "relative"),
                a = t.find('.atom[data-part-len!="0"]');
                for (var f = 0; f < a.length; f++) if (n = o(a[f]), i = n.position(), p = n.height(), i.top + p > e) {
                    l = i.top < e ? parseInt(n.data("paragraph-offset")) + Math.floor(e / p * n.attr("data-part-len")) : parseInt(n.data("paragraph-offset"));
                    break
                }
                t.css("position", s)
            } else r = t.find('.atom[data-part-len!="0"]:last'),
            l = Math.max(parseInt(r.data("paragraph-offset") || 0) + parseInt(r.attr("data-part-len") || 0) - 1, 0);
            return l
        },
        setContainerHeight: function(t) {
            var e = this,
            a = t.data || {},
            o = a.winHeight,
            r = e.Config;
            e.top = e.$readerContainer.offset().top + e.toolsBarHeight,
            e.bottom = e.top + e.State.containerHeight,
            e.loadTop = e.top - r.wsLoadDeviation,
            e.loadBottom = e.bottom + r.wsLoadDeviation,
            e.removeTop = e.top - r.wsRemoveDeviation,
            e.removeBottom = e.bottom + r.wsRemoveDeviation,
            e.winHeight = o,
            e.State.docHeight = e.$doc.height(),
            e.$textContainer.removeClass("min-height")
        },
        changeTop: function(t) {
            var e = this,
            a = t.data || {},
            o = a.top,
            r = a.easing,
            n = t.promise,
            i = e.State.scrollTop;
            return e.scrollReader(o, r, n, a.loadingBlock),
            i !== e.State.scrollTop && (e.mediator.fire("v:hide-all-reader-pop"), e.mediator.fire("p:hide-comment-pop")),
            e.mediator.fire("p:set-page-scroll-state"),
            n
        },
        scrollReader: function(t, e, a, o) {
            var r = this,
            n = !1,
            i = -Math.max(0, r.$textContainer.outerHeight() - r.State.containerHeight),
            s = r.State.scrollTop + t;
            i > s ? s = i: s > 0 && (s = 0),
            s !== r.State.scrollTop && (r.State.scrollTop = s, e ? (r.scrollAnimate(r.$textContainer, {
                top: s
            },
            a), !o && r.scrollAnimate(r.$readerContainer.find("#stream-underline-block"), {
                top: s
            }), n = !0) : (r.$textContainer.css("top", s), !o && r.$readerContainer.find("#stream-underline-block").css("top", s))),
            e && !n && a && a.resolve()
        },
        scrollAnimate: function(t, e, a) {
            t.animate(e, {
                easing: "easeOutCirc"
            }).promise().done(function() {
                a && a.resolve()
            })
        },
        loadCurViewNotes: function(t, e) {
            if (t && e) {
                var a = this,
                o = t.attr("data-cn"),
                r = e.attr("data-cn"),
                n = Number(o) + 1;
                if (o == r) i = t.attr("data-paragraph-index") != e.attr("data-paragraph-index") ? t.nextUntil(e, ".p-b").addBack().add(e) : t;
                else {
                    for (var i = t.nextAll(".p-b").addBack(), s = e.closest(".txt-reader-wrap").find(".p-b"), p = e.attr("data-paragraph-index"); r > n;) i = i.add(a.$textContainer.find('.txt-reader-wrap[data-block-num="' + n + '"] .p-b')),
                    n++;
                    i = i.add(s.slice(0, p))
                }
                var d, l, f, c;
                d = i.filter(":not(.progress-absent):not(:empty):first"),
                l = i.filter(":not(.progress-absent):not(:empty):last"),
                f = l.find('.atom[data-part-len!="0"]').last(),
                d.length && l.length ? (c = {
                    bfi: parseInt(d.attr("data-cn")),
                    bpi: parseInt(d.attr("data-paragraph-index")),
                    bci: 0,
                    efi: parseInt(l.attr("data-cn")),
                    epi: parseInt(l.attr("data-paragraph-index")),
                    eci: f.length ? parseInt(f.data("paragraph-offset")) + parseInt(f.attr("data-part-len")) - 1 : 0
                },
                a.State.boundary = c, a.mediator.fire("p:show-view-notes")) : a.mediator.fire("p:clear-notes-state")
            }
        },
        dispose: function() {
            var t = this,
            e = t.Util;
            e.off(t),
            e.dispose(t)
        }
    });
    a.exports.ScrollView = i
});;
define("ydcore:widget/book_view/txt_reader/js/v/stream/scrollbar.view.js",
function(e, r, s) {
    var o = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    t = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    a = {};
    a.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var i = a.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: t
    }).extend({
        _init: function(e) {
            var r = this;
            e = e || {},
            o.extend(r, e, {
                template: {
                    view: ['<div id="scroll-bar-v" class="scroll-bar-v' + (e.isIE6 ? " scroll-bar-v-ie6": "") + ' no-select-range">', '<div class="scroll-bar-inner">', '<b class="ic ic-top"></b>', '<b class="ic ic-bottom"></b>', "</div>", '<div class="scroll-bar">', '<div class="progress-bar">', '<b class="ic ic-top"></b>', '<b class="ic ic-bottom"></b>', "</div>", '<div class="ic ic-progress-point"></div>', "</div>", '<div class="bubble-wrap">', '<div class="arrow"></div>', '<div class="inner">', '<span class="title"></span>', '<span class="percent"></span>', "</div>", "</div>", "</div>"].join(""),
                    type: "template"
                },
                maxTitleLength: 54,
                isTop: !1,
                isBottom: !1,
                isMousedown: !1,
                isLeaved: !0,
                maxProgress: 1,
                progress: 0,
                progressTop: 0,
                progressHeight: 0,
                progressBottom: 0,
                changeScrollTimer: null,
                changeScrollTimespan: 300,
                domEventsList: []
            }),
            r._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            r = (e.mediator, e.Util);
            r.on("v:scrollbar-init", e),
            r.on("v:window-resize", e),
            r.on("v:set-view-progress", e),
            r.on("v:set-page-scroll-state", e)
        },
        setPageScrollState: function(e) {
            var r = this,
            s = e.data;
            r.isBottom = s.isBottom === !0,
            r.isTop = s.isTop === !0
        },
        _bindDomEvent: function() {
            var e = this;
            e.domEventsList.push({
                dom: e.$el,
                eventName: "mouseenter",
                fn: o.proxy(e.mouseenter, e)
            },
            {
                dom: e.$el,
                eventName: "mouseleave",
                fn: o.proxy(e.mouseleave, e)
            },
            {
                dom: e.$point,
                eventName: "mousedown",
                fn: o.proxy(e.mousedown, e)
            },
            {
                dom: e.$doc,
                eventName: "mouseup",
                fn: o.proxy(e.mouseup, e)
            },
            {
                dom: e.$doc,
                eventName: "mousemove",
                fn: o.proxy(e.mousemove, e)
            }),
            o.each(e.domEventsList,
            function(e, r) {
                r.dom.on(r.eventName, r.fn)
            })
        },
        scrollbarInit: function() {
            var e = this;
            e.$el = o(e.renderView(e.template, {})).appendTo(e.$body),
            e.$realBarWrap = e.$el.find(".scroll-bar"),
            e.$realBar = e.$realBarWrap.find(".progress-bar"),
            e.$point = e.$realBarWrap.find(".ic-progress-point"),
            e.$bubbleWrap = e.$el.find(".bubble-wrap"),
            e.$bubbleArrow = e.$el.find(".arrow"),
            e.$bubbleTitle = e.$el.find(".title"),
            e.$bubblePercent = e.$el.find(".percent");
            var r = e.totalChapterNum / e.totalChapterNum * 100 + "%";
            e.$realBarWrap.height(r),
            e.getRealBarPos(),
            e._bindDomEvent()
        },
        getRealBarPos: function() {
            var e = this;
            if (e.isIE6) {
                var r = e.$win.height(),
                s = parseInt(e.$el.css("top")),
                o = parseInt(e.$el.css("bottom")),
                t = r - s - o;
                e.$el.height(t);
                var a = e.$realBarWrap.height(),
                i = 18;
                e.maxProgress = Math.max(0, a - i) / a
            }
            e.progressTop = e.$realBarWrap.offset().top,
            e.progressHeight = e.$realBarWrap.height(),
            e.progressBottom = e.progressHeight + e.progressTop
        },
        preventDefault: function(e) {
            e && e.preventDefault && e.preventDefault()
        },
        mouseenter: function() {
            var e = this;
            e.$el.addClass("scroll-bar-v-hover"),
            e._setProgress(e.progress),
            e.isLeaved = !1
        },
        mouseleave: function() {
            var e = this;
            e.isMousedown || e.$el.removeClass("scroll-bar-v-hover"),
            e.isLeaved = !0
        },
        getProgress: function() {
            var e = this,
            r = e.progress * e.freeChapterNum,
            s = Math.floor(r);
            return r -= s,
            r > 0 ? s += 1 : s > 0 ? r = 1 : s = 1,
            {
                chapterNum: s,
                progress: r,
                direction: e.direction
            }
        },
        changeScroll: function(e) {
            var r = this,
            s = r.mediator,
            o = r.progress;
            r.changeScrollTimer = setTimeout(function() { (o !== r.progress || e) && s.fire("p:scrollbar-scrolling", {
                    data: r.getProgress()
                }),
                r.changeScroll(!0)
            },
            r.changeScrollTimespan)
        },
        mousedown: function(e) {
            {
                var r = this;
                e.target
            }
            e.preventDefault(),
            r.isMousedown = !0,
            r.mousedownY = e.pageY,
            r.$el.addClass("scroll-bar-v-hover"),
            r.changeScroll()
        },
        mouseup: function(e) {
            var r = this;
            if (r.isMousedown && r.mediator.fire("p:send-log", {
                data: {
                    logAct: "scrollbar.drag",
                    pageType: "stream"
                }
            }), r.isMousedown = !1, r.isLeaved && r.$el.removeClass("scroll-bar-v-hover"), r.changeScrollTimer) clearTimeout(r.changeScrollTimer),
            r.changeScrollTimer = null,
            r.mediator.fire("p:scrollbar-scroll-stop", {
                data: r.getProgress()
            });
            else {
                var s = r.$el.get(0),
                t = r.$bubbleWrap.get(0);
                if (e.target !== t && !o.contains(t, e.target) && (e.target === s || o.contains(s, e.target))) {
                    var a = e.pageY,
                    i = r.$el.offset().top,
                    l = a - i - 10;
                    l = Math.max(0, l) / r.progressHeight,
                    r._setProgress(l),
                    r.mediator.fire("p:scrollbar-scroll-stop", {
                        data: r.getProgress()
                    })
                }
            }
        },
        mousemove: function(e) {
            var r = this;
            if (r.isMousedown) {
                var s = e.pageY,
                o = (s - r.mousedownY) / r.progressHeight,
                t = r.progress + o;
                t >= 0 && 1 >= t && (r.mousedownY = s),
                r._setProgress(t)
            }
        },
        windowResize: function() {
            var e = this;
            e.$el && e.getRealBarPos()
        },
        setViewProgress: function(e) {
            var r = this;
            if (!r.isMousedown) {
                var s = e.data || {},
                o = s.top;
                r.isBottom && (o = 1, r.mediator.fire("v:set-progress-to-100")),
                r._setProgress(o)
            }
        },
        _setProgress: function(e) {
            var r = this;
            r.progress = e = Math.max(0, Math.min(1, e)),
            e = Math.min(r.maxProgress, e);
            var s = 100 * e;
            e = s + "%",
            s = Math.max(1, Math.round(s)) + "%",
            r.$realBar.height(e),
            r.$point.css("top", e),
            r.$bubbleWrap.css("top", e);
            var o = "(" + s + ")";
            r.$bubblePercent.html(o),
            r.$bubbleTitle.html(r._getTitle(r.maxTitleLength - o.length)),
            r.$el.hasClass("scroll-bar-v-hover") && r.setBubblePos()
        },
        setBubblePos: function() {
            var e = this,
            r = e.$bubbleWrap.height();
            e.$bubbleWrap.css("marginTop", -r / 2);
            var s = e.$bubbleWrap.offset().top,
            o = 10;
            if (o > s) s = o - s,
            e.$bubbleWrap.css("top", "+=" + s),
            e.$bubbleArrow.css("top", r / 2 - s);
            else {
                var s = r + s + o - e.$win.height();
                s > 0 ? (e.$bubbleWrap.css("top", "-=" + s), e.$bubbleArrow.css("top", r / 2 + s)) : e.$bubbleArrow.css("top", "50%")
            }
        },
        _getTitle: function(e) {
            var r = this,
            s = r.getProgress().chapterNum,
            t = "";
            return o.each(r.realChapterNameMap,
            function(e, r) {
                return s >= r.cn ? void(t = r.title) : !1
            }),
            t = t || r.docTitle,
            t = r.Util.subStrFromMiddle(t, e)
        },
        dispose: function() {
            var e = this,
            r = e.Util;
            o.each(e.domEventsList,
            function(e, r) {
                r.dom.off(r.eventName, r.fn)
            }),
            e.domEventsList = [],
            e.$point = null,
            e.$realBar = null,
            e.$realBarWrap = null,
            e.$el.remove(),
            e.$el = null,
            r.off(e),
            r.dispose(e)
        }
    });
    s.exports.ScrollbarView = i
});;
define("ydcore:widget/book_view/txt_reader/js/v/stream/stream.view.js",
function(e, i, t) {
    var r = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    s = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    o = e("ydcore:widget/book_view/txt_reader/js/v/stream/chapter.view.js").ChapterView,
    n = e("ydcore:widget/book_view/txt_reader/js/v/stream/operate.view.js").OperateView,
    d = e("ydcore:widget/book_view/txt_reader/js/v/common_part/lines.view.js").LinesView,
    a = e("ydcore:widget/book_view/txt_reader/js/v/common_part/cutpage.view.js").CutPageView,
    w = e("ydcore:widget/book_view/txt_reader/js/v/stream/scroll.view.js").ScrollView,
    v = e("ydcore:widget/book_view/txt_reader/js/v/stream/scrollbar.view.js").ScrollbarView,
    c = {};
    c.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var l = c.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: s
    }).extend({
        _init: function(e) {
            var i = this;
            r.extend(i, e, {
                _viewList: [],
                underlineTpl: '<div id="stream-underline-block" class="underline-container"></div>'
            }),
            i._viewList.push(new o(e), new n(e), new d(e), new a(e), new v(e), new w(e)),
            i.$readerContainer.prepend(i.underlineTpl)
        },
        dispose: function() {
            var e = this,
            i = e.Util;
            r.each(e._viewList,
            function(e, i) {
                i.disposed || i.dispose()
            }),
            r("#stream-underline-block").remove(),
            e.$textContainer.addClass("min-height").html("").css("top", 0),
            i.dispose(e)
        }
    });
    t.exports.Stream = l
});;
define("ydcore:widget/book_view/txt_reader/js/v/format/chapter.view.js",
function(e, t, a) {
    var r = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcommon:widget/ui/js_core/util/util.js"),
    n = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    o = {};
    o.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var c = o.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: n
    }).extend({
        _init: function(e) {
            var t = this;
            r.extend(t, e, {
                resizeTimer: null,
                $formatContainer: null
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            t.on("v:insert-chapter", e),
            t.on("v:set-container-height", e)
        },
        setContainerHeight: function(e) {
            var t = this,
            a = e.data || {};
            a.changedName && "width" !== a.changedName && (t.resizeTimer && clearTimeout(t.resizeTimer), t.resizeTimer = setTimeout(function() {
                t.mediator && t.$formatContainer && (t.mediator.fire("v:clear-text-container"), t.mediator.fire("p:first-load", {
                    data: {
                        chapterNum: t.State.curChapterNum,
                        progress: t.State.curParagraphIndex > 0 ? t.State.curParagraphIndex: 0,
                        offset: t.State.curParagraphOffset,
                        isParagraph: !0
                    }
                }))
            },
            300))
        },
        insertChapter: function(e) {
            var t = this,
            a = t.mediator,
            r = e.data || {},
            n = r.chapterCache,
            o = n.chapterNum,
            c = r.direction;
            t.$formatContainer || a.when("v:create-format-container").then(function(e) {
                t.$formatContainer = e[0]
            }),
            t.$formatContainer.html(n.html);
            var s = t.$formatContainer.children().first();
            n.gotLines || a.when(["v:get-lines", {
                data: {
                    $chapter: s,
                    chapterCache: n
                }
            }]).then(function(e) {
                var t = e[0];
                a.fire("m:set-chapter-cache", {
                    data: {
                        chapterNum: t.chapterNum,
                        chapterCache: t
                    }
                })
            }),
            a.when(["v:cut-pages", {
                data: {
                    $chapter: s,
                    chapterNum: o
                }
            }]).then(function(e) {
                var t = e[0];
                i.recycleDom(s[0]),
                s = null,
                a.fire("v:insert-pages", {
                    data: {
                        chapterNum: o,
                        pageList: t,
                        direction: c
                    }
                })
            })
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            e.$textContainer.off("click", ".retry-loading"),
            t.off(e),
            t.dispose(e)
        }
    });
    a.exports.ChapterView = c
});;
define("ydcore:widget/book_view/txt_reader/js/v/format/pager.view.js",
function(e, a, t) {
    var r = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    n = {};
    n.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var o = n.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: i
    }).extend({
        _init: function(e) {
            var a = this;
            r.extend(a, e, {
                loadingTpl: ['<div class="loading-block">', '<span class="loading-fail">\u52a0\u8f7d\u5931\u8d25\uff01\u70b9\u51fb<a class="retry-loading">\u91cd\u8bd5</a>\u4e00\u4e0b</span>', "</div>"].join(""),
                pagerContainerTpl: ['<div id="format-pager-container"></div>'].join(""),
                waitLoading: "",
                isTop: !1,
                isBottom: !1,
                chapterPageList: [],
                pageList: [],
                pageIndex: -1,
                $pagerContainer: null
            }),
            a._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            a = e.mediator,
            t = e.Util;
            t.on("v:direct-scroll-to-chapter", e),
            t.on("v:clear-text-container", e),
            t.on("v:load-and-remove", e),
            t.on("v:insert-pages", e),
            t.on("v:next-page", e),
            t.on("v:prev-page", e),
            t.on("v:loading-chapter", e),
            t.on("v:loading-fail", e),
            e.$textContainer.on("click", ".retry-loading",
            function() {
                e.$loadingBlock.removeClass("loading-fail-block"),
                a.fire("v:load-and-remove", {
                    data: {
                        loadingFail: !0
                    }
                })
            })
        },
        clearTextContainer: function() {
            var e = this;
            e.$pagerContainer && (e.$pagerContainer.empty(), e.chapterPageList = [], r.each(e.pageList,
            function(e, a) {
                a.remove()
            }), e.pageList = [], e.pageIndex = -1)
        },
        insertPages: function(e) {
            var a = this,
            t = e.data || {},
            i = t.pageList || [],
            n = t.direction,
            o = t.chapterNum,
            p = "push";
            "up" === n && (p = "unshift", (a.pageIndex >= 0 || a.waitLoading) && (a.pageIndex += i.length)),
            a.chapterPageList[p]({
                chapterNum: o,
                pageList: i
            });
            var g = [];
            r.each(a.chapterPageList,
            function(e, a) {
                g = g.concat(a.pageList)
            }),
            a.pageList = g,
            -1 === a.pageIndex && a.pageList.length > 0 && a.showPage(0),
            a.waitLoading && a.showPage(a.pageIndex),
            a.mediator.fire("v:load-and-remove")
        },
        loadAndRemove: function(e) {
            var a = this,
            t = a.mediator,
            i = e.data || {};
            if (0 === a.pageList.length) return i.loadingFail === !0 && t.fire("p:first-load"),
            void(a.chapterPageList.length > 0 && a.fixEmptyChapter());
            var n = a.pageList[a.pageIndex],
            o = n.data("progress"),
            p = n.data("bottom-boundary"),
            g = 0,
            s = 7,
            d = 0,
            c = [],
            h = [],
            l = [],
            f = [],
            u = 0;
            r.each(a.chapterPageList,
            function(e, t) {
                var r = t.pageList.length;
                d + r - 1 < a.pageIndex - s ? (c.push.apply(c, t.pageList), u += r) : d <= a.pageIndex + s ? (t.chapterNum === o.chapterNum && (g = t.pageList.length), h.push(t.chapterNum), l.push(t), f = f.concat(t.pageList)) : c.push.apply(c, t.pageList),
                d += r
            }),
            r.each(c,
            function(e, a) {
                a.remove()
            }),
            c = null,
            a.State.showingChapterNumList = h,
            a.chapterPageList = l,
            a.pageList = f,
            a.pageIndex -= u;
            var m = a.State.curChapterNum,
            v = a.State.curParagraphIndex,
            x = a.State.curParagraphOffset; (m !== o.chapterNum || v !== o.paragraphIndex || o.offset !== x) && (a.State.curChapterNum = o.chapterNum, a.State.curParagraphIndex = o.paragraphIndex, a.State.curParagraphOffset = o.offset, a.State.curBottomInfo = p, o.absent || t.fire("p:save-read-progress", {
                data: {
                    cn: o.chapterNum,
                    pn: o.paragraphIndex,
                    offset: o.offset,
                    docid: a.doc_id
                }
            }), t.fire("p:paragraph-change", {
                data: {
                    cn: o.chapterNum,
                    pn: o.paragraphIndex,
                    offset: o.offset
                }
            })),
            a.isTop = 0 === a.pageIndex && (1 === a.State.curChapterNum || a.chapterPageList.length > 0 && 1 === a.chapterPageList[0].chapterNum),
            a.isBottom = a.pageIndex === a.pageList.length - 1 && (a.State.curChapterNum === a.freeChapterNum || a.chapterPageList.length > 0 && a.chapterPageList[a.chapterPageList.length - 1].chapterNum === a.freeChapterNum),
            t.fire("p:set-page-scroll-state", {
                data: {
                    isTop: a.isTop,
                    isBottom: a.isBottom
                }
            }),
            t.fire("p:get-progress", {
                data: {
                    chapterNum: o.chapterNum,
                    curChapterPageCount: g,
                    curPageIndex: o.index
                }
            });
            var L = 5,
            I = a.pageIndex < L,
            C = a.pageList.length - 1 - a.pageIndex < L;
            t.fire("p:pre-load", {
                data: {
                    prev: I,
                    next: C
                }
            }),
            t.fire("p:send-view-log")
        },
        fixEmptyChapter: function() {
            var e = this,
            a = e.mediator;
            e.isTop = e.pageIndex < 1 && (1 === e.State.curChapterNum || e.chapterPageList.length > 0 && 1 === e.chapterPageList[0].chapterNum),
            e.isBottom = e.pageIndex === e.pageList.length - 1 && (e.State.curChapterNum === e.freeChapterNum || e.chapterPageList.length > 0 && e.chapterPageList[e.chapterPageList.length - 1].chapterNum === e.freeChapterNum),
            a.fire("p:set-page-scroll-state", {
                data: {
                    isTop: e.isTop,
                    isBottom: e.isBottom
                }
            });
            var t = 5,
            r = e.pageIndex < t,
            i = e.pageList.length - 1 - e.pageIndex < t;
            a.fire("p:pre-load", {
                data: {
                    prev: r,
                    next: i
                }
            })
        },
        directScrollToChapter: function(e) {
            var a, t = this,
            i = e.data || {},
            n = i.chapterNum,
            o = i.progress,
            p = i.isParagraph,
            g = i.offset || 0,
            s = -1;
            if (r.each(t.chapterPageList,
            function(e, t) {
                if (t.chapterNum === n) a = t.pageList;
                else {
                    if (! (t.chapterNum < n)) return ! 1;
                    s += t.pageList.length
                }
            }), a) {
                var d = 0;
                p ? (r.each(a,
                function(e, a) {
                    var t = a.data("progress");
                    return o > t.paragraphIndex || o === t.paragraphIndex && g >= t.offset ? void d++:!1
                }), 0 === d && (d = 1)) : (d = Math.ceil(a.length * o), d > a.length && (d = a.length)),
                s += d,
                s = Math.max(0, s),
                s !== t.pageIndex && t.showPage(s),
                t.mediator.fire("v:load-and-remove")
            }
        },
        nextPage: function(e) {
            var a = this,
            t = e.promise;
            if ("next" === a.waitLoading) return void t.resolve();
            var r = a.pageIndex + 1,
            i = a.pageList.length;
            if (i > r) a.showPage(r, t),
            a.mediator.fire("v:load-and-remove");
            else {
                if (!a.isBottom) {
                    a.waitLoading = "next";
                    var n = a.pageList[a.pageIndex];
                    n && n.detach(),
                    a.$pagerContainer.append(a.$loadingBlock),
                    a.pageIndex = r
                }
                t.resolve()
            }
            return t
        },
        prevPage: function(e) {
            var a = this,
            t = e.promise;
            if ("prev" === a.waitLoading) return void t.resolve();
            var r = a.pageIndex - 1;
            if (r >= 0) a.showPage(r, t, !0),
            a.mediator.fire("v:load-and-remove");
            else {
                if (!a.isTop) {
                    a.waitLoading = "prev";
                    var i = a.pageList[a.pageIndex];
                    i && i.detach(),
                    a.$pagerContainer.append(a.$loadingBlock),
                    a.pageIndex = r
                }
                t.resolve()
            }
            return t
        },
        showPage: function(e, a, t) {
            function r() {
                i.loadCurPageNotes(p),
                i.mediator.fire("v:after-render-to-doc", {
                    data: {
                        dom: p,
                        format: "format"
                    }
                }),
                a && a.resolve()
            }
            var i = this;
            if (e === i.pageIndex && !i.waitLoading) return void a.resolve();
            i.waitLoading = "";
            var n = i.mediator;
            n.fire("v:hide-all-reader-pop"),
            n.fire("p:hide-comment-pop");
            var o = i.pageList[i.pageIndex];
            n.fire("v:send-page-change-log", {
                data: {
                    $pager: o,
                    promise: a,
                    isPrev: t === !0
                }
            });
            var p = i.pageList[e];
            i.mediator.fire("v:before-render-to-doc", {
                data: {
                    dom: p,
                    format: "format"
                }
            }),
            !a || !o || o.size() < 1 ? (o && o.detach(), p.appendTo(i.$pagerContainer.empty()), r()) : t === !0 ? i.$pagerContainer.prepend(p).css("left", "-100%").animate({
                left: "0"
            },
            function() {
                o.detach(),
                r()
            }) : i.$pagerContainer.append(p).animate({
                left: "-100%"
            },
            function() {
                o.detach(),
                i.$pagerContainer.css("left", 0),
                r()
            }),
            i.pageIndex = e
        },
        loadCurPageNotes: function(e) {
            var a = this,
            t = e.data("progress"),
            r = e.find(".p-b:not(.progress-absent,.no-select-range):not(:empty):first"),
            i = e.find(".p-b:not(.progress-absent,.no-select-range):not(:empty):last"),
            n = i.find('.atom[data-part-len!="0"]:last');
            if (r.length && i.length) {
                var o = {
                    bfi: t.chapterNum,
                    bpi: parseInt(r.attr("data-paragraph-index")),
                    bci: 0,
                    efi: t.chapterNum,
                    epi: parseInt(i.attr("data-paragraph-index")),
                    eci: n.length ? parseInt(n.data("paragraph-offset")) + parseInt(n.attr("data-part-len")) - 1 : 0
                };
                a.State.boundary = o,
                a.mediator.fire("p:show-view-notes")
            } else a.mediator.fire("p:clear-notes-state")
        },
        loadingChapter: function() {
            var e = this;
            e.$loadingBlock || (e.$loadingBlock = r(e.loadingTpl)),
            e.$pagerContainer || (e.$pagerContainer = r(e.pagerContainerTpl).appendTo(e.$textContainer)),
            -1 === e.pageIndex && e.$pagerContainer.append(e.$loadingBlock)
        },
        loadingFail: function() {
            var e = this;
            e.$loadingBlock.addClass("loading-fail-block")
        },
        dispose: function() {
            var e = this,
            a = e.Util;
            a.off(e),
            a.dispose(e)
        }
    });
    t.exports.PagerView = o
});;
define("ydcore:widget/book_view/txt_reader/js/v/format/scrollbar.view.js",
function(e, r, s) {
    var t = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    o = e("ydcommon:widget/ui/js_core/util/util.js"),
    a = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    i = {};
    i.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    i.string = e("ydcommon:widget/ui/lib/string/string.js");
    var l = i.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: a
    }).extend({
        _init: function(e) {
            var r = this;
            e = e || {},
            t.extend(r, e, {
                template: {
                    view: ['<div id="scroll-bar-h" class="scroll-bar-h' + (e.isIE6 ? " scroll-bar-h-ie6": "") + ' no-select-range">', '<div class="scroll-bar-inner">', '<b class="ic ic-left"></b>', '<b class="ic ic-right"></b>', "</div>", '<div class="scroll-bar">', '<div class="progress-bar">', '<b class="ic ic-left"></b>', '<b class="ic ic-right"></b>', "</div>", '<div class="ic ic-progress-point"></div>', "</div>", '<div class="bubble-wrap">', '<div class="arrow"></div>', '<div class="inner">', '<span class="title"></span>', '<span class="percent"></span>', "</div>", "</div>", "</div>"].join(""),
                    type: "template"
                },
                bubbleArrowLeft: 67,
                maxTitleLength: 54,
                isTop: !1,
                isBottom: !1,
                isMousedown: !1,
                isLeaved: !0,
                maxProgress: 1,
                progress: 0,
                progressLeft: 0,
                progressWidth: 0,
                progressRight: 0,
                changeScrollTimer: null,
                changeScrollTimespan: 300,
                domEventsList: []
            }),
            r._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            r = (e.mediator, e.Util);
            r.on("v:scrollbar-init", e),
            r.on("v:window-resize", e),
            r.on("v:set-view-progress", e),
            r.on("v:set-page-scroll-state", e)
        },
        setPageScrollState: function(e) {
            var r = this,
            s = e.data;
            r.isBottom = s.isBottom === !0,
            r.isTop = s.isTop === !0
        },
        _bindDomEvent: function() {
            var e = this;
            e.domEventsList.push({
                dom: e.$el,
                eventName: "mouseenter",
                fn: t.proxy(e.mouseenter, e)
            },
            {
                dom: e.$el,
                eventName: "mouseleave",
                fn: t.proxy(e.mouseleave, e)
            },
            {
                dom: e.$point,
                eventName: "mousedown",
                fn: t.proxy(e.mousedown, e)
            },
            {
                dom: e.$doc,
                eventName: "mouseup",
                fn: t.proxy(e.mouseup, e)
            },
            {
                dom: e.$doc,
                eventName: "mousemove",
                fn: t.proxy(e.mousemove, e)
            }),
            t.each(e.domEventsList,
            function(e, r) {
                r.dom.on(r.eventName, r.fn)
            })
        },
        scrollbarInit: function() {
            var e = this;
            e.$el = t(e.renderView(e.template, {})).appendTo(e.$body),
            e.$realBarWrap = e.$el.find(".scroll-bar"),
            e.$realBar = e.$realBarWrap.find(".progress-bar"),
            e.$point = e.$realBarWrap.find(".ic-progress-point"),
            e.$bubbleWrap = e.$el.find(".bubble-wrap"),
            e.$bubbleArrow = e.$el.find(".arrow"),
            e.$bubbleTitle = e.$el.find(".title"),
            e.$bubblePercent = e.$el.find(".percent");
            var r = e.totalChapterNum / e.totalChapterNum * 100 + "%";
            e.$realBarWrap.width(r),
            e.getRealBarPos(),
            e._bindDomEvent()
        },
        getRealBarPos: function() {
            var e = this;
            if (e.isIE6) {
                var r = e.$win.width(),
                s = parseInt(e.$el.css("left")),
                t = parseInt(e.$el.css("right")),
                o = r - s - t;
                e.$el.width(o);
                var a = e.$realBarWrap.width(),
                i = 18;
                e.maxProgress = Math.max(0, a - i) / a
            }
            e.progressLeft = e.$realBarWrap.offset().left,
            e.progressWidth = e.$realBarWrap.width(),
            e.progressRight = e.progressWidth + e.progressLeft
        },
        preventDefault: function(e) {
            e && e.preventDefault && e.preventDefault()
        },
        mouseenter: function() {
            var e = this;
            e.$el.addClass("scroll-bar-h-hover"),
            e._setProgress(e.progress),
            e.isLeaved = !1
        },
        mouseleave: function() {
            var e = this;
            e.isMousedown || e.$el.removeClass("scroll-bar-h-hover"),
            e.isLeaved = !0
        },
        getProgress: function() {
            var e = this,
            r = e.progress * e.freeChapterNum,
            s = Math.floor(r);
            return r -= s,
            r > 0 ? s += 1 : s > 0 ? r = 1 : s = 1,
            {
                chapterNum: s,
                progress: r,
                direction: e.direction
            }
        },
        changeScroll: function(e) {
            var r = this,
            s = r.mediator,
            t = r.progress;
            r.changeScrollTimer = setTimeout(function() { (t !== r.progress || e) && s.fire("p:scrollbar-scrolling", {
                    data: r.getProgress()
                }),
                r.changeScroll(!0)
            },
            r.changeScrollTimespan)
        },
        mousedown: function(e) {
            {
                var r = this;
                e.target
            }
            e.preventDefault(),
            r.isMousedown = !0,
            r.mousedownX = e.pageX,
            r.$el.addClass("scroll-bar-h-hover"),
            r.changeScroll()
        },
        mouseup: function(e) {
            var r = this;
            if (r.isMousedown && r.mediator.fire("p:send-log", {
                data: {
                    logAct: "scrollbar.drag",
                    pageType: "format"
                }
            }), r.isMousedown = !1, r.isLeaved && r.$el.removeClass("scroll-bar-h-hover"), r.changeScrollTimer) clearTimeout(r.changeScrollTimer),
            r.changeScrollTimer = null,
            r.mediator.fire("p:scrollbar-scroll-stop", {
                data: r.getProgress()
            });
            else {
                var s = r.$el.get(0),
                o = r.$bubbleWrap.get(0);
                if (e.target !== o && !t.contains(o, e.target) && (e.target === s || t.contains(s, e.target))) {
                    var a = e.pageX,
                    i = r.$el.offset().left,
                    l = a - i - 10;
                    l = Math.max(0, l) / r.progressWidth,
                    r._setProgress(l),
                    r.mediator.fire("p:scrollbar-scroll-stop", {
                        data: r.getProgress()
                    })
                }
            }
        },
        mousemove: function(e) {
            var r = this;
            if (r.isMousedown) {
                var s = e.pageX,
                t = (s - r.mousedownX) / r.progressWidth,
                o = r.progress + t;
                o >= 0 && 1 >= o && (r.mousedownX = s),
                r._setProgress(o)
            }
        },
        windowResize: function() {
            var e = this;
            e.$el && e.getRealBarPos()
        },
        setViewProgress: function(e) {
            var r = this;
            if (!r.isMousedown) {
                var s = e.data || {},
                t = s.top;
                r.isBottom && (t = 1, r.mediator.fire("v:set-progress-to-100")),
                r._setProgress(t)
            }
        },
        _setProgress: function(e) {
            var r = this;
            r.progress = e = Math.max(0, Math.min(1, e)),
            e = Math.min(r.maxProgress, e);
            var s = 100 * e;
            e = s + "%",
            s = Math.max(1, Math.round(s)) + "%",
            r.$realBar.width(e),
            r.$point.css("left", e),
            r.$bubbleWrap.css("left", e);
            var t = "(" + s + ")";
            r.$bubblePercent.html(t),
            r.$bubbleTitle.html(r._getTitle(r.maxTitleLength - t.length)),
            r.$el.hasClass("scroll-bar-h-hover") && r.setBubblePos()
        },
        setBubblePos: function() {
            var e = this,
            r = e.$bubbleWrap.offset().left,
            s = 10;
            if (s > r) r = s - r,
            e.$bubbleWrap.css("left", "+=" + r),
            e.$bubbleArrow.css("left", e.bubbleArrowLeft - r);
            else {
                var t = e.$bubbleWrap.width();
                r = t + r + s - e.$win.width(),
                r > 0 ? (e.$bubbleWrap.css("left", "-=" + r), e.$bubbleArrow.css("left", e.bubbleArrowLeft + r)) : e.$bubbleArrow.removeAttr("style")
            }
        },
        _getTitle: function(e) {
            var r = this,
            s = r.getProgress().chapterNum,
            o = "";
            return t.each(r.realChapterNameMap,
            function(e, r) {
                return s >= r.cn ? void(o = r.title) : !1
            }),
            o = o || r.docTitle,
            o = r.Util.subStrFromMiddle(o, e)
        },
        dispose: function() {
            var e = this,
            r = e.Util;
            t.each(e.domEventsList,
            function(e, r) {
                r.dom.off(r.eventName, r.fn)
            }),
            e.domEventsList = [],
            e.$point = null,
            e.$realBar = null,
            e.$realBarWrap = null,
            e.$el.remove(),
            o.recycleDom(e.$el[0]),
            e.$el = null,
            r.off(e),
            r.dispose(e)
        }
    });
    s.exports.ScrollbarView = l
});;
define("ydcore:widget/book_view/txt_reader/js/v/format/format.view.js",
function(e, i, t) {
    var o = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    r = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    s = e("ydcore:widget/book_view/txt_reader/js/v/format/chapter.view.js").ChapterView,
    w = e("ydcore:widget/book_view/txt_reader/js/v/common_part/lines.view.js").LinesView,
    a = e("ydcore:widget/book_view/txt_reader/js/v/common_part/cutpage.view.js").CutPageView,
    d = e("ydcore:widget/book_view/txt_reader/js/v/format/pager.view.js").PagerView,
    n = e("ydcore:widget/book_view/txt_reader/js/v/common_part/operate.view.js").OperateView,
    v = e("ydcore:widget/book_view/txt_reader/js/v/format/scrollbar.view.js").ScrollbarView,
    c = {};
    c.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var _ = c.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: r
    }).extend({
        _init: function(e) {
            var i = this;
            o.extend(i, e, {
                _viewList: []
            }),
            i._viewList.push(new s(e), new w(e), new a(e), new n(e), new v(e), new d(e))
        },
        dispose: function() {
            var e = this,
            i = e.Util;
            o.each(e._viewList,
            function(e, i) {
                i.disposed || i.dispose()
            }),
            e.$textContainer.addClass("min-height").html("").css("top", 0),
            i.dispose(e)
        }
    });
    t.exports.Format = _
});;
define("ydcore:widget/book_view/txt_reader/js/v/view.js",
function(e, t, r) {
    var o = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    a = e("ydcore:widget/book_view/txt_reader/js/v/common/common.view.js").Common,
    i = e("ydcore:widget/book_view/txt_reader/js/v/ffkan/ffkan.view.js").Ffkan,
    n = e("ydcore:widget/book_view/txt_reader/js/v/stream/stream.view.js").Stream,
    d = e("ydcore:widget/book_view/txt_reader/js/v/format/format.view.js").Format,
    s = {
        ffkan: i,
        format: d,
        stream: n
    },
    m = function(e) {
        var t = null,
        r = null;
        e = o.extend(e || {},
        {
            $readerContainer: o("#reader-page-wrap"),
            $textContainer: o("#txt-reader-block"),
            toolsBarHeight: e.$toolsBar.height()
        }),
        t = new a(e);
        var i = e.State,
        n = e.mediator;
        n.on("v:switch-type",
        function(t) {
            var o = t.data || {},
            a = o.type;
            r && (r.dispose(), r = null),
            s[a] && (i.scrollTop = 0, "format" === a || "ffkan" === a ? e.$textContainer.addClass("format-reader-block") : e.$textContainer.removeClass("format-reader-block"), r = new s[a](e))
        })
    };
    r.exports.ViewList = m
});;
define("ydcore:widget/book_view/txt_reader/js/p/stream/chapter.presenter.js",
function(t, e, a) {
    var r = t("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = t("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    o = {};
    o.lang = t("ydcommon:widget/ui/lib/lang/lang.js");
    var n = o.lang.createClass(function(t) {
        this._init(t)
    },
    {
        superClass: i
    }).extend({
        _init: function(t) {
            var e = this;
            r.extend(e, t, {
                token: 0,
                isFirst: !0,
                upLoading: !1,
                downLoading: !1
            }),
            e._bindEvent()
        },
        _bindEvent: function() {
            var t = this,
            e = (t.mediator, t.Util);
            e.on("p:first-load", t),
            e.on("p:pre-load", t),
            e.on("p:scroll-to-chapter", t)
        },
        scrollToChapter: function(t) {
            var e, a = this,
            i = t.data || {},
            o = i.chapterNum,
            n = i.progress,
            s = i.offset || 0,
            d = i.paragraphNum,
            p = n === e && d > 0,
            c = {
                chapterNum: o,
                progress: p ? d: n,
                offset: s,
                isParagraph: p
            },
            h = a.State.showingChapterNumList;
            r.inArray(o, h) > -1 ? a.mediator.fire("p:direct-scroll-to-chapter", {
                data: c
            }) : (a.mediator.fire("v:clear-text-container"), a.mediator.fire("p:first-load", {
                data: c
            }))
        },
        preLoad: function(t) {
            var e = this,
            a = t.data || {},
            r = e.State.showingChapterNumList;
            a.prev && r[0] && e.loadChapter({
                data: {
                    chapterNum: r[0] - 1,
                    direction: "up"
                }
            }),
            a.next && r[r.length - 1] && e.loadChapter({
                data: {
                    chapterNum: r[r.length - 1] + 1,
                    direction: "down"
                }
            })
        },
        firstLoad: function(t) {
            var e = this,
            a = t.data || {};
            e.token++;
            var i = r.extend({},
            a),
            o = a.chapterNum || e.State.curChapterNum,
            n = "down";
            e.State.showingChapterNumList = [],
            e.upLoading = !1,
            e.downLoading = !1,
            e.loadChapter({
                data: {
                    chapterNum: o,
                    direction: n,
                    progressData: i,
                    isFirst: !0
                }
            })
        },
        loadChapter: function(t) {
            var e = this,
            a = e.mediator,
            r = t.data || {},
            i = r.isFirst === !0,
            o = r.chapterNum,
            n = r.direction,
            s = n + "Loading",
            d = r.progressData || {};
            if (!e[s] && e.checkChapterNum(o)) {
                i ? (e.upLoading = !0, e.downLoading = !0) : e[s] = !0;
                var p = e.token;
                a.when(["m:get-chapter-html", {
                    data: {
                        chapterNum: o
                    }
                }], ["v:loading-chapter", {
                    data: {
                        direction: n
                    }
                }]).then(function(t) {
                    var r = t[0];
                    if ("loading" !== r) {
                        if ("fail" === r) return void a.fire("v:loading-fail", {
                            data: {
                                direction: n
                            }
                        });
                        if (p === e.token) {
                            a.fire("v:insert-chapter", {
                                data: {
                                    chapterCache: r,
                                    direction: n
                                }
                            });
                            var c = "push";
                            "up" === n && (c = "unshift"),
                            e.State.showingChapterNumList[c](o);
                            var h = !1;
                            e.State.isFirst && (4 != e.State.savePosFrom && (h = !0), e.State.isFirst = !1, alog && alog("speed.set", "c_flc", +new Date), alog && alog.fire && alog.fire("mark"), a.fire("v:prevent-default-scroll")),
                            e.isFirst && (e.isFirst = !1),
                            i ? (e.upLoading = !1, e.downLoading = !1, a.fire("p:set-page-scroll-state")) : e[s] = !1,
                            a.fire("v:load-and-remove"),
                            d.progress && (a.fire("p:direct-scroll-to-chapter", {
                                data: d
                            }), h && a.fire("v:show-position-tip", {
                                data: d,
                                ext: {
                                    from: e.State.savePosFrom,
                                    time: e.State.savePosTime
                                }
                            }))
                        }
                    }
                })
            }
        },
        checkChapterNum: function(t) {
            var e = this;
            return t > 0 && t <= e.freeChapterNum
        },
        dispose: function() {
            var t = this,
            e = t.Util;
            e.off(t),
            e.dispose(t)
        }
    });
    a.exports.ChapterPresenter = n
});;
define("ydcore:widget/book_view/txt_reader/js/p/stream/scroll.presenter.js",
function(e, t, i) {
    var r = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    o = e("ydcommon:widget/ui/js_core/log/log.js"),
    s = e("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    a = {};
    a.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var n = a.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: s
    }).extend({
        _init: function(e) {
            var t = this;
            r.extend(t, e),
            r.extend(t, {
                readProgressDivisor: 1 / t.totalChapterNum,
                viewProgressDivisor: 1 / t.freeChapterNum,
                logTime: null
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            t.on("p:get-progress", e),
            t.on("p:set-page-scroll-state", e),
            t.on("p:send-view-log", e)
        },
        sendViewLog: function() {
            var e = this;
            if (!e.isPreview) {
                if (!e.logTime) return void(e.logTime = new Date);
                var t = new Date,
                i = t - e.logTime;
                if (! (2e3 > i)) {
                    e.logTime = t;
                    var r = {
                        doc_id: e.doc_id,
                        timeSpan: i,
                        cn: e.State.curChapterNum,
                        allp: e.allPCount,
                        docType: e.type,
                        version: 1
                    };
                    o.send("book", "view", r)
                }
            }
        },
        getProgress: function(e) {
            var t = this,
            i = t.mediator,
            r = e.data,
            o = r.first,
            s = r.last;
            if (o && s) {
                var a, n;
                a = o[0] - 1 + Math.min(1, o[1] / o[2]),
                n = s[0] - 1 + Math.min(1, s[1] / s[2]),
                a *= t.viewProgressDivisor,
                n *= t.viewProgressDivisor,
                i.fire("v:set-read-progress", {
                    data: {
                        progress: a
                    }
                }),
                i.fire("v:set-view-progress", {
                    data: {
                        top: a,
                        bottom: n
                    }
                })
            }
        },
        setPageScrollState: function() {
            var e = this,
            t = !1,
            i = !1;
            0 === e.State.scrollTop && 1 === e.State.showingChapterNumList[0] && (t = !0);
            var r = -Math.max(0, e.$textContainer.outerHeight() - e.State.containerHeight),
            o = e.State.showingChapterNumList.length;
            e.State.scrollTop <= r && e.State.showingChapterNumList[o - 1] === e.freeChapterNum && (i = !0),
            e.mediator.fire("v:set-page-scroll-state", {
                data: {
                    isTop: t,
                    isBottom: i
                }
            })
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            t.off(e),
            t.dispose(e)
        }
    });
    i.exports.ScrollPresenter = n
});;
define("ydcore:widget/book_view/txt_reader/js/p/stream/scrollbar.presenter.js",
function(t, e, r) {
    var a = t("ydcommon:widget/ui/lib/jquery/jquery.js"),
    o = t("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    i = {};
    i.lang = t("ydcommon:widget/ui/lib/lang/lang.js");
    var s = i.lang.createClass(function(t) {
        this._init(t)
    },
    {
        superClass: o
    }).extend({
        _init: function(t) {
            var e = this;
            a.extend(e, t),
            e._bindEvent()
        },
        _bindEvent: function() {
            var t = this,
            e = (t.mediator, t.Util);
            e.on("p:scrollbar-scrolling", t),
            e.on("p:scrollbar-scroll-stop", t),
            e.on("p:direct-scroll-to-chapter", t)
        },
        scrollbarScrolling: function(t) {
            var e = this,
            r = t.data || {},
            o = r.chapterNum,
            i = r.progress,
            s = e.State.showingChapterNumList;
            a.inArray(o, s) > -1 ? e.directScroll(o, i) : e.scrollBothEnd(o, i)
        },
        scrollbarScrollStop: function(t) {
            var e = this,
            r = t.data || {},
            o = r.chapterNum,
            i = r.progress,
            s = e.State.showingChapterNumList;
            if (a.inArray(o, s) > -1) e.directScroll(o, i);
            else {
                if (o > e.freeChapterNum) {
                    if (s.slice( - 1)[0] === e.freeChapterNum) return void e.scrollBothEnd(o, i);
                    o = e.freeChapterNum,
                    i = 1
                }
                e.mediator.fire("p:scroll-to-chapter", {
                    data: {
                        chapterNum: o,
                        progress: i
                    }
                })
            }
        },
        directScrollToChapter: function(t) {
            var e = this,
            r = t.data || {};
            e.directScroll(r.chapterNum, r.progress, r.isParagraph, r.offset)
        },
        scrollBothEnd: function(t) {
            var e, r = this,
            a = r.mediator,
            o = r.State.showingChapterNumList;
            if (t < o[0]) e = -r.State.scrollTop;
            else {
                var i = r.$textContainer;
                e = -Math.max(0, i.height() - r.State.containerHeight) - (parseFloat(i.css("top")) || 0)
            }
            a.fire("v:change-top", {
                data: {
                    top: e
                }
            }),
            a.fire("v:load-and-remove")
        },
        _getOffsetHeight: function(t, e) {
            var r = 0;
            if (e && e > 0) {
                var a = t.height() || 0,
                o = t.find("img").size() > 0,
                i = parseInt(t.css("lineHeight")) || 0,
                s = t.text().length;
                if (a > 0 && s > 0 && !o && i > 0) {
                    var n = Math.floor(a / i);
                    if (n > 5) {
                        var l = e / s;
                        l > 1 && (l = 1);
                        var c = Math.floor(l * n) - 2;
                        c > 0 && (r = c * i)
                    }
                }
            }
            return r
        },
        directScroll: function(t, e, r, o) {
            var i = this,
            s = i.mediator;
            s.when(["m:get-chapter-cache", {
                data: {
                    chapterNum: t
                }
            }]).then(function(t) {
                var n = t[0];
                if (n) {
                    var l = a("#" + n.domId),
                    c = i.$textContainer,
                    p = l.offset().top,
                    f = 0;
                    if (r) {
                        if (e > 1) {
                            var h = l.find(".p-b").eq(e - 1);
                            if (h.size() > 0) {
                                var d = i._getOffsetHeight(h, o);
                                f = h.offset(),
                                f = f ? f.top - p: 0,
                                f += d
                            }
                        }
                    } else f = l.outerHeight() * e;
                    var g = -(p - (c.offset().top + parseInt(c.css("paddingTop"))) + f) - (parseFloat(c.css("top")) || 0);
                    s.fire("v:change-top", {
                        data: {
                            top: g
                        }
                    }),
                    s.fire("v:load-and-remove")
                }
            })
        },
        dispose: function() {
            var t = this,
            e = t.Util;
            e.off(t),
            e.dispose(t)
        }
    });
    r.exports.ScrollbarPresenter = s
});;
define("ydcore:widget/book_view/txt_reader/js/p/stream/stream.presenter.js",
function(e, r, t) {
    var s = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcommon:widget/ui/js_core/mvp/presenter/presenter.js").Presenter,
    a = e("ydcore:widget/book_view/txt_reader/js/p/stream/chapter.presenter.js").ChapterPresenter,
    n = e("ydcore:widget/book_view/txt_reader/js/p/stream/scroll.presenter.js").ScrollPresenter,
    o = e("ydcore:widget/book_view/txt_reader/js/p/stream/scrollbar.presenter.js").ScrollbarPresenter,
    d = {};
    d.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var p = d.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: i
    }).extend({
        _init: function(e) {
            var r = this;
            s.extend(r, e, {
                _presenterList: []
            }),
            r._presenterList.push(new a(e), new n(e), new o(e)),
            r.render()
        },
        render: function() {
            var e = this,
            r = e.mediator;
            r.fire("v:window-resize"),
            r.fire("v:progressbar-init"),
            r.fire("v:scrollbar-init"),
            r.fire("p:first-load", {
                data: {
                    chapterNum: e.State.curChapterNum,
                    progress: e.State.curParagraphIndex > 0 ? e.State.curParagraphIndex: 0,
                    offset: e.State.curParagraphOffset,
                    isParagraph: !0
                }
            })
        },
        dispose: function() {
            var e = this,
            r = e.Util;
            s.each(e._presenterList,
            function(e, r) {
                r.disposed || r.dispose()
            }),
            r.dispose(e)
        }
    });
    t.exports.Stream = p
});;
define("ydcore:widget/book_view/txt_reader/js/p/presenter.js",
function(e, t, r) {
    var o = (e("ydcommon:widget/ui/lib/jquery/jquery.js"), e("ydcore:widget/book_view/txt_reader/js/m/model.js").ModelList),
    a = e("ydcore:widget/book_view/txt_reader/js/v/view.js").ViewList,
    i = e("ydcore:widget/book_view/txt_reader/js/p/common/common.presenter.js").Common,
    s = e("ydcore:widget/book_view/txt_reader/js/p/ffkan/ffkan.presenter.js").Ffkan,
    n = e("ydcore:widget/book_view/txt_reader/js/p/stream/stream.presenter.js").Stream,
    d = e("ydcore:widget/book_view/txt_reader/js/p/format/format.presenter.js").Format,
    f = {
        cookie: e("ydcommon:widget/ui/lib/cookie/cookie.js")
    },
    p = {
        ffkan: s,
        format: d,
        stream: n
    },
    c = {
        format: !0,
        stream: !0
    },
    m = function(e) {
        var t = null,
        r = null;
        t = new i(e);
        var s = e.State;
        o(e),
        a(e);
        var n = e.mediator,
        d = "";
        n.on("p:before-switch-type",
        function(t) {
            var r = t.data || {},
            o = s.currentType,
            a = "";
            if (o) {
                if ("ffkan" === r.switchType) {
                    var i, f;
                    "ffkan" !== o || d || (d = e.defaultFormatType),
                    d ? (a = d, d = "", i = "200201", f = "p:send-ffkan-log") : (a = "ffkan", d = o, i = "200200", f = "p:send-normal-x-log"),
                    n.fire(f, {
                        data: {
                            act_id: i
                        }
                    })
                } else switch (o) {
                case "stream":
                    a = "format";
                    break;
                case "format":
                    a = "stream"
                }
                a && n.fire("p:switch-type", {
                    data: {
                        type: a
                    }
                })
            }
        }),
        n.on("p:switch-type",
        function(t) {
            var o = t.data || {},
            a = o.type,
            i = s.currentType;
            i !== a && (r && (r.dispose(), r = null), p[a] ? (s.currentType = a, n.fire("v:switch-type", {
                data: o
            }), r = new p[a](e), "ffkan" !== a && f.cookie.set("YD_view_type", a, {
                path: "/",
                expires: 31536e6
            }), n.fire("v:set-switch-style", {
                data: {
                    type: a
                }
            }), c[a] && (c[a] = !1, n.fire("p:send-log", {
                data: {
                    logAct: "view.type",
                    type: a
                }
            }))) : s.currentType = "")
        })
    };
    r.exports.PresenterList = m
});;
define("ydcore:widget/book_view/txt_reader/js/system/config.sys.js",
function(e, o, i) {
    var s = {
        wsLoadDeviation: 1e3,
        wsRemoveDeviation: 2e3,
        canCopy: !1
    };
    i.exports.Config = s
});;
define("ydcore:widget/book_view/txt_reader/js/system/jquery.plugins.js",
function(e) {
    var t = e("ydcommon:widget/ui/lib/jquery/jquery.js"); !
    function(e) {
        e.easing.oldwing = e.easing.swing,
        e.extend(e.easing, {
            def: "oldwing",
            swing: function(t, n, o, i, l) {
                return e.easing[e.easing.def](t, n, o, i, l)
            },
            easeOutCirc: function(e, t, n, o, i) {
                return o * Math.sqrt(1 - (t = t / i - 1) * t) + n
            },
            easeOutBack: function(e, t, n, o, i, l) {
                return void 0 == l && (l = 1.70158),
                o * ((t = t / i - 1) * t * ((l + 1) * t + l) + 1) + n
            }
        })
    } (t),
    function(e) {
        function t(t) {
            var i, l = t || window.event,
            s = [].slice.call(arguments, 1),
            u = 0,
            a = 0,
            h = 0,
            r = 0,
            d = 0;
            return t = e.event.fix(l),
            t.type = "mousewheel",
            l.wheelDelta && (u = l.wheelDelta),
            l.detail && (u = -1 * l.detail),
            l.deltaY && (h = -1 * l.deltaY, u = h),
            l.deltaX && (a = l.deltaX, u = -1 * a),
            void 0 !== l.wheelDeltaY && (h = l.wheelDeltaY),
            void 0 !== l.wheelDeltaX && (a = -1 * l.wheelDeltaX),
            r = Math.abs(u),
            (!n || n > r) && (n = r),
            d = Math.max(Math.abs(h), Math.abs(a)),
            (!o || o > d) && (o = d),
            i = u > 0 ? "floor": "ceil",
            u = Math[i](u / n),
            a = Math[i](a / o),
            h = Math[i](h / o),
            s.unshift(t, u, a, h),
            (e.event.dispatch || e.event.handle).apply(this, s)
        }
        var n, o, i = ["wheel", "mousewheel", "DOMMouseScroll", "MozMousePixelScroll"],
        l = "onwheel" in document || document.documentMode >= 9 ? ["wheel"] : ["mousewheel", "DomMouseScroll", "MozMousePixelScroll"];
        if (e.event.fixHooks) for (var s = i.length; s;) e.event.fixHooks[i[--s]] = e.event.mouseHooks;
        e.event.special.mousewheel = {
            setup: function() {
                if (this.addEventListener) for (var e = l.length; e;) this.addEventListener(l[--e], t, !1);
                else this.onmousewheel = t
            },
            teardown: function() {
                if (this.removeEventListener) for (var e = l.length; e;) this.removeEventListener(l[--e], t, !1);
                else this.onmousewheel = null
            }
        },
        e.fn.extend({
            mousewheel: function(e) {
                return e ? this.bind("mousewheel", e) : this.trigger("mousewheel")
            },
            unmousewheel: function(e) {
                return this.unbind("mousewheel", e)
            }
        })
    } (t)
});;
define("ydcore:widget/book_view/txt_reader/js/system/mediator.sys.js",
function(e, t, o) {
    var s = e("ydcommon:widget/ui/js_core/mvp/event/event.js").custEvent;
    o.exports.mediator = new s
});;
define("ydcore:widget/book_view/txt_reader/js/system/note.util.sys.js",
function(t, e, a) {
    var r = t("ydcommon:widget/ui/lib/jquery/jquery.js"),
    n = t("ydcore:widget/ui/book_view/lib/rangy/rangy.js").rangy;
    T = {},
    T.string = t("ydcommon:widget/ui/lib/string/string.js");
    var i = {
        parseRangeToPos: function(t) {
            var e, a, n = this,
            i = {
                bfi: 0,
                bpi: 0,
                bci: 0,
                efi: 0,
                epi: 0,
                eci: 0,
                key: ""
            };
            return ! t.isValid() || t.collapsed ? !1 : t.startContainer && t.endContainer && r(t.startContainer).parents("#txt-reader-block").length && r(t.endContainer).parents("#txt-reader-block").length ? (e = n._getStartEndPos(t, !0), e && r.extend(i, e), a = n._getStartEndPos(t, !1), a && r.extend(i, a), e && a && n.comparePoint(n.getPonitByPrefix(e, "b"), n.getPonitByPrefix(a, "e")) <= 0 ? (i.key = [i.bfi, i.bpi, i.bci, i.efi, i.epi, i.eci].join("-"), i) : !1) : !1
        },
        _getStartEndPos: function(t, e) {
            var a, n, i, s = this,
            o = {};
            return e ? (a = t.startContainer, n = t.startOffset) : (a = t.endContainer, n = t.endOffset),
            i = r(a).closest(".p-b"),
            i.length ? (e ? (o.bfi = parseInt(i.attr("data-cn")), o.bpi = parseInt(i.attr("data-paragraph-index")), o.bci = s._getOffset(i, a, n, e)) : (o.efi = parseInt(i.attr("data-cn")), o.epi = parseInt(i.attr("data-paragraph-index")), o.eci = s._getOffset(i, a, n, e)), o) : !1
        },
        _getOffset: function(t, e, a, n) {
            var i, s, o = 0,
            p = e.nodeType,
            f = (e.nodeName, r(e));
            return i = t.find('.atom[data-part-len!="0"]'),
            s = r(),
            t.hasClass("ext_code_wrap") || t.hasClass("single-page") || t.hasClass("single-img-p") ? o = 0 : 3 == p || f.hasClass("ic-remark") || f.hasClass("ext_remark") ? (s = f.closest(".atom"), o = Math.max(parseInt(s.attr("data-paragraph-offset")) + a - (n ? 0 : 1), 0)) : 1 == p && (s = r(e.childNodes[Math.max(a - (n ? 0 : 1), 0)]), s.hasClass("atom") ? o = +s.attr("data-paragraph-offset") : s.hasClass("ext_picnote") ? n ? o = 0 : (s = s.find('.atom[data-part-len!="0"]:last'), o = s.length ? parseInt(s.attr("data-paragraph-offset")) + parseInt(s.attr("data-part-len")) - 1 : 0) : o = 0),
            o
        },
        parsePosToRange: function(t, e) {
            var a = this,
            n = [];
            return r.each(t,
            function(t, r) {
                n.push(a._restorePosRange(r, e))
            }),
            n
        },
        _restorePosRange: function(t, e) {
            var a = this,
            i = n.createRange(),
            s = e.find(".p-b-" + t.page.bfi + "-" + t.page.bpi),
            o = e.find(".p-b-" + t.page.efi + "-" + t.page.epi),
            p = ["single-img-p", "ext_code_wrap", "single-page"],
            f = !1,
            d = !1;
            0 == s.length && (s = e.find(".p-b:not(.progress-absent):not(:empty):first"), i.setStartBefore(s.get(0)), f = !0),
            0 == o.length && (o = e.find(".p-b:not(.progress-absent):not(:empty):last"), i.setEndAfter(o.get(0)), d = !0),
            f || r.each(p,
            function(t, e) {
                return s.hasClass(e) ? (i.setStartBefore(s.get(0)), f = !0, !1) : void 0
            }),
            d || r.each(p,
            function(t, e) {
                return o.hasClass(e) ? (i.setEndAfter(o.get(0)), d = !0, !1) : void 0
            }),
            a._dealCodeBlock(s, o, t);
            var c = function() {
                var e = r(this),
                a = parseInt(e.attr("data-paragraph-offset")),
                n = parseInt(e.attr("data-part-len")),
                i = e.text();
                return (i.length || "SPAN" != this.tagName) && t.page.bci <= a + n - 1 ? !0 : !1
            },
            g = function() {
                var e = r(this),
                a = parseInt(e.attr("data-paragraph-offset")),
                n = parseInt(e.attr("data-part-len")),
                i = e.text();
                return (i.length || "SPAN" != this.tagName) && t.page.eci <= a + n - 1 ? !0 : !1
            };
            if (!f) {
                {
                    var l = s.find(".atom").filter(c).first(),
                    h = parseInt(l.attr("data-paragraph-offset"));
                    parseInt(l.attr("data-part-len"))
                }
                if (l.length) if (l.get(0).firstChild && n.dom.isCharacterDataNode(l.get(0).firstChild)) try {
                    i.setStart(l.get(0).firstChild, t.page.bci - h),
                    f = !0
                } catch(u) {
                    f = !1
                } else try {
                    i.setStartBefore(l.get(0)),
                    f = !0
                } catch(u) {
                    f = !1
                } else f = !1
            }
            if (!d) {
                {
                    var b = o.find(".atom").filter(g).first(),
                    m = parseInt(b.attr("data-paragraph-offset"));
                    parseInt(b.attr("data-part-len"))
                }
                if (b.length) if (b.get(0).firstChild && n.dom.isCharacterDataNode(b.get(0).firstChild)) try {
                    i.setEnd(b.get(0).firstChild, t.page.eci - m + 1),
                    d = !0
                } catch(u) {
                    d = !1
                } else try {
                    n.dom.getNodeLength(b.get(0)) ? i.setEnd(b.get(0), 1) : i.setEndAfter(b.get(0)),
                    d = !0
                } catch(u) {
                    d = !1
                } else d = !1
            }
            return f && d ? i: !1
        },
        _dealCodeBlock: function(t, e, a) {
            var n = this,
            i = t.attr("data-cn"),
            s = e.attr("data-cn"),
            o = Number(i) + 1,
            p = r(),
            f = 0;
            if (a.page && a.page.style && a.page.style.noteColor >= 0 ? f = a.page.style.noteColor: a.ori && a.ori.style && a.ori.style.noteColor >= 0 && (f = a.ori.style.noteColor), i == s) p = t.attr("data-paragraph-index") != e.attr("data-paragraph-index") ? t.nextUntil(e, ".p-b").addBack().add(e) : t;
            else {
                for (var p = t.nextAll(".p-b").addBack(), d = e.closest(".txt-reader-wrap").find(".p-b"), c = e.attr("data-paragraph-index"); s > o;) p = p.add(n.$textContainer.find('.txt-reader-wrap[data-block-num="' + o + '"] .p-b')),
                o++;
                p = p.add(d.slice(0, c))
            }
            f >= 0 && p.length && p.filter(".ext_code_wrap").addClass("code-block-note-color" + f)
        },
        getNoteByKey: function(t) {
            var e = window.readerState.booknotes,
            a = {};
            return r.each(e,
            function(e, n) {
                return n.key === t ? (r.extend(a, n), !1) : void 0
            }),
            r.isEmptyObject(a) ? !1 : a
        },
        isSamePosNote: function(t, e) {
            return t.key === e.key ? !0 : !1
        },
        isSameNote: function(t, e) {
            return t.bfi === e.bfi && t.bpi === e.bpi && t.bci === e.bci && t.efi === e.efi && t.epi === e.epi && t.eci === e.eci && t.summary === e.summary && t.customstr === e.customstr && t.style.noteColor === t.style.noteColor ? !0 : !1
        },
        quickSort: function(t, e) {
            function a(e, a) {
                var r = t[e];
                t[e] = t[a],
                t[a] = r
            }
            function r(n, i) {
                if (i > n) {
                    var s = t[n + i >> 1],
                    o = n,
                    p = i;
                    do {
                        for (; e(t[o], s) < 0;) o++;
                        for (; e(s, t[p]) < 0;) p--;
                        p >= o && a(o++, p--)
                    } while ( p >= o );
                    r(n, p),
                    r(o, i)
                }
            }
            return r(0, t.length - 1),
            t
        },
        getPonitByPrefix: function(t, e) {
            var a = {};
            return a.cn = t[e + "fi"],
            a.pn = t[e + "pi"],
            a.offset = t[e + "ci"],
            a
        },
        comparePoint: function(t, e) {
            return t.cn < e.cn ? -1 : t.cn == e.cn ? t.pn < e.pn ? -1 : t.pn == e.pn ? t.offset < e.offset ? -1 : t.offset == e.offset ? 0 : 1 : 1 : 1
        },
        compareStartPoint: function(t, e) {
            return t.bfi < e.bfi ? -1 : t.bfi == e.bfi ? t.bpi < e.bpi ? -1 : t.bpi == e.bpi ? t.bci < e.bci ? -1 : t.bci == e.bci ? 0 : 1 : 1 : 1
        },
        compareEndPoint: function(t, e) {
            return t.efi < e.efi ? -1 : t.efi == e.efi ? t.epi < e.epi ? -1 : t.epi == e.epi ? t.eci < e.eci ? -1 : t.eci == e.eci ? 0 : 1 : 1 : 1
        },
        parseOriStr: function(t, e) {
            var a = /\r\n|\n\r|\n|\r/g,
            n = t.replace(a, "\n").split("\n");
            return r.each(n,
            function(t, e) {
                e = e ? e: "",
                n[t] = T.string.encodeHTML(e).replace(/\s/g, "&nbsp;")
            }),
            e = e ? e: "",
            n.join(e)
        }
    };
    a.exports.Util = i
});;
define("ydcore:widget/book_view/txt_reader/js/system/state.sys.js",
function(r, t, e) {
    var a = r("ydcommon:widget/ui/lib/jquery/jquery.js"),
    o = {};
    o.lang = r("ydcommon:widget/ui/lib/lang/lang.js");
    var n = o.lang.createClass(function(r) {
        this._init(r)
    }).extend({
        _init: function(r) {
            var t = this;
            a.extend(t, r, {
                isFirst: !0,
                containerHeight: 0,
                maxPageHeight: 0,
                maxSinglePageHeight: 0,
                ffkanBottomHeight: 0,
                scrollTop: 0,
                bodyScrollTop: 0,
                docHeight: 0,
                maxScrollTop: 0,
                currentType: "",
                curChapterNum: r.currentChapter,
                curParagraphIndex: r.paragraphNum,
                curParagraphOffset: r.paragraphOffset,
                curBottomInfo: {},
                boundary: {},
                curNoteColor: 0,
                curFfkanNum: 1,
                curFfkanPn: 1,
                curFkkanOffset: 1,
                curFfkanRange: {},
                showingChapterNumList: [],
                topProgress: 0,
                bottomProgress: 0
            })
        }
    });
    e.exports.State = n
});;
define("ydcore:widget/book_view/txt_reader/js/system/util.sys.js",
function(e, t, n) {
    function s(e, t) {
        var n = e.split(":"),
        s = g.camelCase(n[1] || n[0]),
        i = {
            eventName: e,
            fn: g.proxy(t[s], t)
        };
        l.on(i.eventName, i.fn),
        t.eventNameList = t.eventNameList || [],
        t.eventNameList.push(i)
    }
    function i(e, t, n) {
        var s = l.listeners[e],
        i = 0,
        r = s.length;
        if (!t) return void(s.length = 0);
        for (; r > i; i++) s[i][0] === t && s[i][1] === n && (s.splice(i, 1), i--, r--);
        return this
    }
    function r(e) {
        var t = e.eventNameList || [];
        g.each(t,
        function(e, t) {
            i(t.eventName, t.fn)
        }),
        t.length = 0
    }
    function o(e) {
        e.fire("dispose"),
        d.lang.Class.prototype.dispose.call(e)
    }
    function a(e, t) {
        var n = String(e),
        s = d.string.getByteLength(n);
        if (s > t) {
            var i = n.substr(0, n.length - 5),
            r = n.slice( - 3);
            for (n = i + "..." + r, s = d.string.getByteLength(n); s > t;) i = i.substr(0, i.length - Math.max(1, Math.floor((s - t) / 2))),
            n = i + "..." + r,
            s = d.string.getByteLength(n)
        }
        return n
    }
    var g = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    l = e("ydcore:widget/book_view/txt_reader/js/system/mediator.sys.js").mediator,
    d = {};
    d.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    d.string = e("ydcommon:widget/ui/lib/string/string.js"),
    n.exports.on = s,
    n.exports.off = r,
    n.exports.dispose = o,
    n.exports.subStrFromMiddle = a
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/selecttext/error.js",
function(r, e, t) {
    var o = r("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = r("ydcore:widget/ui/book_view/lib/rangy/rangy.js").rangy,
    s = ['<div id="reader-error-pop" class="reader-error-pop">', '<div class="arrow"></div>', '<div class="error-wrap">', '<p class="selected-txt-hd">\u60a8\u9009\u4e2d\u7684\u5185\u5bb9\uff1a</p>', '<p class="selected-txt"></p>', '<p class="txtarea"><textarea class="error-ta" placeholder="\u8bf7\u8f93\u5165\u60a8\u7684\u7ea0\u9519\u610f\u89c1\uff0c\u6211\u4eec\u4f1a\u5c3d\u5feb\u6539\u6b63~" maxlength="500"></textarea></p>', '<p class="act-btn">', '<a href="javascript:;" class="cancel">\u53d6\u6d88</a>', '<a href="javascript:;" class="bt ui-btn ui-btn-26-light submit-btn"><b class="btc"><b class="btText">\u786e\u5b9a</b></b></a>', "</p>", "</div>", "</div>"].join(""),
    a = ['<div id="send-error-tip">', '<div class="bg-opacity"></div>', "<p></p>", "</div>"].join(""),
    n = {
        _init: function() {
            this._createErrorPop(),
            this._initEvent()
        },
        _createErrorPop: function() {
            o("body").append(s),
            o("body").append(a),
            this.errorPopEl = o("#reader-error-pop"),
            this.selectedTxt = this.errorPopEl.find(".selected-txt"),
            this.errorTa = this.errorPopEl.find(".error-ta"),
            this.sendErrorTip = o("#send-error-tip")
        },
        _initEvent: function() {
            var r = this;
            o(document.body).on("click",
            function(e) {
                var t = e.target,
                i = r.errorPopEl[0],
                s = o("#select-text")[0];
                o.contains(i, t) || o.contains(s, t) || i === t || r.hide()
            }),
            this.errorPopEl.find(".cancel").on("click",
            function() {
                r.hide()
            }),
            o(this.errorPopEl).on("keydown",
            function(r) {
                var e = r.keyCode; (32 === e || 75 === e) && r.stopPropagation()
            }),
            this.errorPopEl.find(".submit-btn").on("click",
            function(e) {
                e.preventDefault();
                var t = o.trim(r.errorTa.val()),
                i = "\u7ea0\u9519\u5185\u5bb9\uff1a" + r.selectTxt + "\n\u53cd\u9988\u5185\u5bb9\uff1a" + t;
                o.ajax({
                    dataType: "jsonp",
                    url: "http://ufo.baidu.com/listen/api/addfeedback",
                    timeout: 5e3,
                    jsonp: "callback",
                    jsonpCallback: "success_jsonpCallback",
                    data: {
                        product_line: 4,
                        description: i,
                        isajax: 1,
                        ajax: "get",
                        extend_ebook_doc_id: r.docId,
                        cn: r.cn,
                        pn: r.pn,
                        referer: location.href
                    }
                }).done(function(e) {
                    r._sendErrorTip(0 === e.error_no ? "success": "failure")
                }).fail(function() {
                    r._sendErrorTip("failure")
                })
            })
        },
        show: function(r) {
            var e = this.errorPopEl,
            t = this._getArrowPosition(r.position, e),
            o = t.position,
            i = this.errorTa;
            "up" === t.direction ? (e.removeClass("reader-error-ab"), e.addClass("reader-error-at")) : (e.removeClass("reader-error-at"), e.addClass("reader-error-ab")),
            e.css({
                display: "block",
                left: o.X,
                top: o.Y
            }),
            this.selectTxt = r.txt,
            this.docId = r.docId,
            this._setErrorPos(),
            this.selectedTxt.html(this.selectTxt),
            i.val(""),
            i.focus()
        },
        _setErrorPos: function() {
            function r(r) {
                if (!r) return null;
                switch (r.nodeType) {
                case 1:
                    return r;
                case 3:
                    return r.parentNode;
                default:
                    return null
                }
            }
            var e = this;
            i.init();
            var t = i.getSelection(),
            s = r(t.anchorNode);
            if (t.isBackwards() && (s = r(t.focusNode)), s) {
                var a = o(s).closest(".p-b"),
                n = a.closest(".txt-reader-wrap"),
                c = parseInt(n.attr("data-block-num")) || 1,
                d = parseInt(a.attr("data-paragraph-index")) || 1;
                e.cn = c,
                e.pn = d
            }
        },
        hide: function() {
            this.errorPopEl.css("display", "none")
        },
        _getArrowPosition: function(r, e) {
            var t = e.height(),
            i = o("body").scrollTop() + o(window).height(),
            s = i - r.Y < t + 30 ? "up": "down",
            a = {
                X: r.X - 45,
                Y: "up" == s ? r.Y - 14 - t: r.Y + 8
            };
            return {
                direction: s,
                position: a
            }
        },
        _sendErrorTip: function(r) {
            var e = "success" === r ? "\u975e\u5e38\u611f\u8c22\uff0c\u7ea0\u6b63\u610f\u89c1\u53d1\u9001\u6210\u529f": "\u5bf9\u4e0d\u8d77\uff0c\u53d1\u9001\u5931\u8d25\u8bf7\u91cd\u8bd5",
            t = o(window).height(),
            i = o(window).width(),
            s = this.sendErrorTip,
            a = o(s).height(),
            n = o(s).width();
            this.hide(),
            s.find("p").text(e),
            s.css({
                left: (i - n) / 2,
                top: (t - a) / 2,
                display: "block"
            }),
            setTimeout(function() {
                s.animate({
                    opacity: 0
                },
                600,
                function() {
                    s.css({
                        display: "none",
                        opacity: 1
                    })
                })
            },
            1200)
        }
    };
    o(function() {
        n._init()
    }),
    t.exports = n
});;
define("ydcore:widget/book_view/txt_reader/js/v/common/selecttext/search.js",
function() {});;
define("ydcore:widget/book_view/txt_reader/js/v/format/cutpage.view.js",
function(e, t, a) {
    var r = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    n = {};
    n.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var s = n.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: i
    }).extend({
        _init: function(e) {
            var t = this;
            r.extend(t, e, {
                formatContainerTpl: ['<div id="reader-format-container">', '<div class="reader-format-inner"></div>', "</div>"].join(""),
                pagerTpl: ['<div class="format-pager-wrap">', '<div class="format-pager">', '<div class="pager-inner renderClass">', '<div class="text-container"></div>', "</div>", "</div>", "</div>"].join(""),
                currentBoundary: null
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            t.on("v:cut-pages", e),
            t.on("v:create-format-container", e)
        },
        createFormatContainer: function(e) {
            var t = this,
            a = e.promise,
            i = r("#reader-format-container");
            return 0 === i.size() && (i = r(t.formatContainerTpl).appendTo(t.$body)),
            i = i.find(".reader-format-inner"),
            a.resolve(i),
            a
        },
        createPager: function(e) {
            var t = this,
            a = r(t.pagerTpl.replace("renderClass", e));
            return a
        },
        cutPages: function(e) {
            var t, a = this,
            i = (a.mediator, e.promise),
            n = e.data,
            s = n.$chapter,
            o = n.chapterNum,
            h = [];
            a.currentBoundary = {};
            var g = 1;
            try {
                for (; t = a.getNextPage(s);) {
                    if (0 === t.$p.size()) {
                        if (a.currentBoundary.bottom) {
                            a.currentBoundary.bottom = [a.currentBoundary.bottom[0].next(), 1];
                            continue
                        }
                        break
                    }
                    t.isEnd && t.pageHeight < t.maxPageHeight && (t.pageHeight = Math.min(t.pageHeight + 5, t.maxPageHeight)),
                    t.chapterNum = o,
                    t.index = g,
                    h.push(t),
                    g++
                }
            } catch(d) {}
            return h = r.map(h,
            function(e) {
                var t = a.createPager(e.renderClass);
                if (t.find(".text-container").append(e.$p.clone()).end().find(".pager-inner").css("marginTop", e.marginTop), t.find(".single-page").size() > 0 && (t.addClass("format-single-page-wrap"), e.pageHeight = a.State.maxSinglePageHeight), t.find(".format-pager").height(e.pageHeight), t.data("progress", {
                    index: e.index,
                    chapterNum: e.chapterNum,
                    paragraphIndex: e.paragraphIndex,
                    offset: e.offset
                }), r.trim(t.text()).length > 0 || t.find("img").size() > 0) return t;
                var i = t.find(".single-page");
                return i.size() > 0 ? t: null
            }),
            i.resolve(h),
            i
        },
        _hasPageDivide: function(e) {
            return e.find(".page-divide").size() > 0
        },
        _isHxTitle: function(e) {
            var t = !1;
            try {
                var a = e.get(0).tagName.toLowerCase();
                /^h[1-6]$/.test(a) && (t = !0)
            } catch(r) {
                t = !1
            }
            return t
        },
        getNextPage: function(e) {
            var t = this;
            t.currentBoundary = t.currentBoundary || {},
            t.currentBoundary.top = t.currentBoundary.bottom || [e.find(".p-b").first(), 1];
            var a = t.currentBoundary.top,
            i = a[0];
            do
            if (i.size() < 1) return;
            while ((t._hasPageDivide(i) || 0 === i.height()) && (i = i.next()));
            var n, s, o, h = parseInt(i.attr("data-paragraph-index")),
            g = a[1],
            d = r(),
            c = i.closest(".txt-reader-wrap").get(0).className,
            f = t.State.maxPageHeight,
            p = 0,
            l = 0,
            u = 0,
            m = 0,
            v = !1,
            x = function(e) {
                return {
                    $p: d,
                    marginTop: p,
                    pageHeight: u,
                    isEnd: e === !0,
                    maxPageHeight: f,
                    renderClass: c,
                    paragraphIndex: h,
                    offset: t._getOffset(i, g)
                }
            },
            w = parseInt(i.css("marginTop")),
            _ = i.offset().top,
            y = i.height(),
            C = _ + y;
            l = _,
            a[1] > 1 ? (p -= w, o = a[1] - 1, s = 0, n = i.attr("data-rows-height").split("|"), r.each(n,
            function(e, t) {
                return e >= o ? !1 : void(s += parseInt(t))
            }), p -= s, l += s) : l -= w;
            var b = !1,
            P = i;
            if (P.hasClass("single-page")) return d = d.add(P),
            u = f,
            t.currentBoundary.bottom = [P.next(), 1],
            x(!0);
            for (; (m = C - l) <= f;) {
                if (d = d.add(P), u = m, P = P.next(), P.size() < 1) {
                    v = !0;
                    break
                }
                if (t._hasPageDivide(P)) {
                    b = !0;
                    break
                }
                if (_ = P.offset().top, _ - l > f) {
                    v = !0;
                    break
                }
                y = P.height(),
                C = _ + y
            }
            var I = 1;
            if (P !== i) {
                var z;
                if (v || b || (z = P.hasClass("format-no-divide"))) {
                    if (z) {
                        var H = t.scaleParagraph(P, l, u, f);
                        H && (u = f, d = d.add(P), P = P.next())
                    }
                    return t.currentBoundary.bottom = [P, 1],
                    x(!0)
                }
                t.scaleImgs(P, f),
                n = P.attr("data-rows-height").split("|")
            } else {
                if (I = a[1], P.hasClass("format-no-divide")) return t.scalePage(P, f, c);
                1 === I && t.scaleImgs(P, f),
                n = P.attr("data-rows-height").split("|")
            }
            return s = 0,
            o = 1,
            n = n || [],
            r.each(n,
            function(e, t) {
                return t = parseInt(t),
                s += t,
                C = _ + s,
                (m = C - l) > f ? !1 : (u = m, void o++)
            }),
            t.currentBoundary.bottom = [P, o],
            o > I && (d = d.add(P)),
            0 === d.size() ? t.getOneline(P, f, c) : x(1 >= o)
        },
        _getOffset: function(e, t) {
            var a = 0;
            if (t -= 1, 1 > t) return a;
            var i = 0,
            n = 0,
            s = e.attr("data-rows-height").split("|");
            return r.each(s,
            function(e, a) {
                a = parseInt(s) || 0,
                i += a,
                t > e && (n += a)
            }),
            a = 0 >= i || 0 >= n ? 0 : i > n ? Math.floor(n / i * e.text().length) : e.text().length
        },
        scaleImgs: function(e, t) {
            for (var a, i, n, s, o = this,
            h = 50,
            g = t - h,
            d = e.find("img"), c = d.toArray(), f = 70, p = 0, l = 0, u = c.length; u > l; l++) a = r(c[l]),
            i = a.height(),
            i > g && (n = a.width(), s = Math.max(1, t - f), s > i && (s = Math.max(1, i - f)), newWidth = s / i * n, a.height(s).width(newWidth), p++);
            p > 0 && o.mediator.fire("v:get-one-paragraph-lines", {
                data: {
                    $p: e
                }
            })
        },
        getOneline: function(e, t) {
            var a, i, n = this,
            s = n.currentBoundary.bottom[1];
            return 1 === s && (a = e.attr("data-rows-height").split("|"), i = parseInt(a[s - 1]), t >= i) ? (e.css("margin", 0), n.getNextPage()) : {
                $p: r()
            }
        },
        scaleParagraph: function(e, t, a, r) {
            var i = this,
            n = !1,
            s = !1;
            if ((r - a > 300 || .3 > a / r && r > 300) && ((s = e.find(".ext_picnote").size() > 0) || (n = e.hasClass("single-img-p")))) {
                var o = e.offset().top,
                h = e.height(),
                g = r - (o - t),
                d = g;
                if (s && e.text().length > 0) {
                    var c = e.find(".ext_picnote");
                    if (c.hasClass("ext_picnote_left") || c.hasClass("ext_picnote_right")) return i.scalePicnote(c, g, e);
                    if (d -= e.find(".picnote-text").outerHeight(), 0 >= d) return ! 1
                }
                for (var f, p, l = e.find("img").first(), u = l.height(), m = l.width(), v = u, x = m, w = u - d; h > g;) {
                    if (p = u - w, 0 >= p) return l.height(u).width(m),
                    !1;
                    f = p / v * x,
                    l.height(p).width(f),
                    w += 10,
                    h = e.height()
                }
                return ! 0
            }
            return ! 1
        },
        mergeLegend: function(e) {
            var t = e.find(".picnote-text"),
            a = t.find(".v-s"),
            i = r('<p class="ext_vertical"></p>').append(a);
            return t.html("").append(i),
            i
        },
        _resizeImg: function(e, t, a) {
            var r, i, n = e.height(),
            s = e.width();
            "width" === t ? (i = a, r = i * n / s) : (r = a, i = r * s / n),
            e.height(r).width(i)
        },
        scalePicnote: function(e, t, a, i) {
            var n = this,
            s = a.html(),
            o = n.mergeLegend(e),
            h = e.find("img"),
            g = a.width(),
            d = o.clone(),
            c = o.width() + 15;
            h.height() > t && n._resizeImg(h, "height", t),
            h.width() + c > g && n._resizeImg(h, "width", g - c);
            var f, p, l, u, m, v = !1,
            x = 0,
            w = c;
            if (e.height() > t) for (;;) {
                for (f = 0, p = null, l = o, u = h.height(), m = r(); l;) l.height() > u ? (p = p || r('<p class="ext_vertical"></p>'), p.prepend(l.children().last())) : (f++, m = m.add(l), p && l.after(p), l = p, p = null);
                if (x++, w = c * f, !(h.width() + w > g)) {
                    v = !0;
                    break
                }
                if (! (5 > x)) break;
                n._resizeImg(h, "width", g - w - 2 * c),
                o.replaceWith(o = d.clone()),
                m.remove()
            } else v = !0;
            if (i !== !0 && v === !1) return a.html(s),
            !1;
            var _ = a.find(".picnote-text"),
            y = 0;
            return _.width(w),
            1 === f && (e.hasClass("ext_text-align_bottom") ? y = Math.max(0, h.height() - _.height()) : e.hasClass("ext_text-align_center") && (y = Math.max(0, h.height() - _.height()) / 2)),
            _.css("marginTop", y),
            n.isIE67 && e.width(w + h.width()),
            a.height(t).css("overflow", "hidden"),
            !0
        },
        _getNoScaleHeight: function(e) {
            var t = 50;
            return e.hasClass("ext_picnote_wrap") && (e.find(".ext_picnote_left").size() > 0 || e.find(".ext_picnote_right").size() > 0 ? t = 0 : (t = e.find(".picnote-text").height(), t = Math.max(50, t))),
            t
        },
        scalePage: function(e, t, a) {
            var r = this,
            i = parseInt(e.attr("data-paragraph-index"));
            e = e.clone().insertAfter(e),
            e.css("margin", 0);
            var n = e.height();
            if (n > t) {
                var s = e.find("img").first();
                if (s.size() > 0) {
                    var o = s.parent().parent();
                    if (o.hasClass("ext_picnote") && (o.hasClass("ext_picnote_left") || o.hasClass("ext_picnote_right"))) r.scalePicnote(o, t, e, !0);
                    else {
                        var h = 30;
                        e.text().length > 0 && (h += r._getNoScaleHeight(e));
                        for (var g, d, c = s.height(), f = s.width(), p = t - h; c > p && (g = t - h, !(0 >= g));) d = g / c * f,
                        s.height(g).width(d),
                        h += 10,
                        c = s.height(),
                        f = s.width()
                    }
                }
                e.height(t).css("overflow", "hidden")
            }
            var l = e.next();
            return r.currentBoundary.bottom = [l, 1],
            e.remove(),
            {
                $p: e,
                marginTop: 0,
                pageHeight: t,
                isEnd: !0,
                maxPageHeight: t,
                renderClass: a,
                paragraphIndex: i,
                offset: 0
            }
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            t.off(e),
            t.dispose(e)
        }
    });
    a.exports.CutPageView = s
});;
define("ydcore:widget/book_view/txt_reader/js/v/format/lines.view.js",
function(e, t, i) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    a = e("ydcommon:widget/ui/js_core/util/util.js"),
    s = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    o = {};
    o.lang = e("ydcommon:widget/ui/lib/lang/lang.js"),
    o.browser = e("ydcommon:widget/ui/lib/browser/browser.js");
    var h = o.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: s
    }).extend({
        _init: function(e) {
            var t = this;
            n.extend(t, e, {}),
            t._initImgFixedHeight(),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            t.on("v:get-lines", e),
            t.on("v:get-one-paragraph-lines", e)
        },
        _getLineHeight: function(e) {
            var t = e.css("lineHeight");
            if ("px" !== t.substr(t.length - 2)) {
                t = parseFloat(t);
                var i = parseInt(e.css("fontSize"));
                t = parseInt(i * t)
            } else t = parseInt(t);
            return t
        },
        _isInOneLine: function(e, t) {
            return t.top < e.bottom || t.top === e.bottom && "img" !== e.tagName && "img" !== t.tagName
        },
        _initImgFixedHeight: function() {
            var e = this,
            t = {};
            t = o.browser.ie && 8 === o.browser.ie ? {
                "pLineHeight-36": {
                    "height-38": 39,
                    "height-37": 38,
                    "height-36": 38,
                    "height-35": 37,
                    "height-34": 37
                }
            }: o.browser.firefox ? {
                "pLineHeight-36": {
                    "height-40": 41,
                    "height-39": 40,
                    "height-38": 40,
                    "height-37": 39,
                    "height-36": 39,
                    "height-35": 38,
                    "height-34": 38,
                    "height-33": 37,
                    "height-32": 37
                }
            }: {
                "pLineHeight-36": {
                    "height-39": 40,
                    "height-38": 39,
                    "height-37": 39,
                    "height-36": 38,
                    "height-35": 38,
                    "height-34": 37,
                    "height-33": 37
                }
            },
            e._imgFixedHeightMap = t
        },
        _getImgFixedHeight: function(e, t) {
            var i = this,
            n = i._imgFixedHeightMap["pLineHeight-" + e] || {};
            return n["height-" + t] || t
        },
        getParagraphLines: function(e) {
            var t = this;
            if (e.hasClass("single-page") || e.hasClass("no-select-range") || e.hasClass("single-img-p") || e.hasClass("ext_picnote_wrap") || e.hasClass("ext_legends") && e.find("img").size() > 0) e.addClass("format-no-divide");
            else {
                var i = 0,
                a = !1,
                s = "P" == e[0].tagName ? e: e.find("p"),
                h = s.children(),
                g = e.css("fontWeight"),
                r = t._getLineHeight(e),
                l = e.offset().top,
                p = l,
                f = n(),
                u = [],
                m = h.filter("br, span, img").not(".s-p, .text, .span-to-link"),
                c = h.filter(".span-to-link").children().filter("br, span, img").not(".s-p, .text");
                m = m.add(c);
                var d = h.filter("img");
                if (0 === m.size() && 0 === d.size() && (s.wrapInner("<span></span>"), h = s.children(), m = h), 0 === m.size() && 1 === d.size()) e.html("").append(d).addClass("single-img-p format-no-divide");
                else {
                    var v = m.filter(".ext_valign_sup, .ext_valign_sub");
                    v.addClass("sup-sub-line");
                    var b = [],
                    _ = null;
                    m.each(function(e, i) {
                        var a = i.tagName.toLowerCase();
                        if ("br" === a) return void b.push({
                            tagName: "br",
                            lineHeight: r
                        });
                        var s = n(i),
                        h = s.offset().top,
                        l = s.height(),
                        p = h + l,
                        u = 0;
                        if ("img" === a && (s.hasClass("ext_float_left") || s.hasClass("ext_float_right"))) return f.push(i),
                        void b.push({
                            tagName: "floatImg",
                            top: h,
                            height: s.outerHeight(!0),
                            bottom: p
                        });
                        var m = t._getLineHeight(s); ! o.browser.firefox && s.hasClass("ext_bold") && s.css("fontWeight") !== g && (m += 1),
                        "img" === a && (u = t._getImgFixedHeight(r, l)),
                        m = Math.max(r, m, u);
                        var c = Math.ceil(l / m),
                        d = {
                            tagName: a,
                            lineHeight: m,
                            lineCount: c,
                            height: l,
                            top: h,
                            bottom: p
                        };
                        b.push(d)
                    });
                    var H = [],
                    L = function() {};
                    t.isIE6 && (L = function(e) {
                        if (e) for (var t; t = H.shift();) t.rect.height < i && m.eq(t.index).css("margin", (i - t.rect.height) / 2 + "px 0")
                    });
                    var x;
                    n.each(b,
                    function(e, n) {
                        var s = n.lineCount,
                        o = n.lineHeight,
                        h = n.height,
                        g = n.top,
                        r = n.tagName;
                        if ("floatImg" === r) return void(_ = n);
                        if ("br" === r) return (0 === e || x === e - 1) && u.push(o),
                        void(x = e);
                        for (var l = 1,
                        f = b[e - l]; f && "floatImg" === f.tagName;) l++,
                        f = b[e - l];
                        if (f && t._isInOneLine(f, n) ? (s--, h -= o, o > i ? (p += o - i, i = o, a = "img" === r, u.pop(), u.push(i)) : i > o && a && (h -= (i - o) / 2, s = Math.ceil(h / o)), L(s > 0)) : (_ && g > _.top && ("img" === r && _.bottom > p && u.push(_.bottom - p), _ = null), L(!0)), s > 1) {
                            for (var m = o,
                            c = "img" === r,
                            d = 1,
                            v = b[e + d]; v;) if ("floatImg" !== v.tagName) {
                                if (!t._isInOneLine(n, v)) break;
                                if (v.lineHeight > m && (m = v.lineHeight, c = "img" === v.tagName), m = Math.max(m, v.lineHeight), v.lineCount > 1) break;
                                d++,
                                v = b[e + d]
                            } else d++,
                            v = b[e + d];
                            m > o && (h -= (m - o) * (c ? .5 : .7), s = Math.max(1, Math.ceil(h / o)))
                        }
                        for (s > 0 && (i = o, a = "img" === r); s-->0;) u.push(i),
                        p += i;
                        t.isIE6 && "img" === r && H.push({
                            index: e,
                            rect: n
                        })
                    }),
                    v.removeClass("sup-sub-line"),
                    m.size() > 0 && f.size() > 0 && (u = t.getFloatImgLine(e, f, u.concat([]))),
                    e.attr("data-rows-height", u.join("|"))
                }
            }
        },
        getCustomLines: function(e) {
            var t = this,
            i = e.attr("data-line-type");
            switch (i) {
            case "code":
                t.getCodeLineHeight(e);
                break;
            case "list":
                t.getListLineHeight(e)
            }
        },
        getListLineHeight: function(e) {
            var t = this,
            i = e.find("p"),
            n = t.getBlockTBPadding(e),
            a = t._getLineHeight(i),
            s = Math.ceil(e.height() / a),
            o = [];
            if (s > 0) {
                for (var h = s; h--;) o.push(a);
                o[0] += n.top,
                o[s - 1] += n.bottom
            } else o.push(n.top + n.bottom);
            e.attr("data-rows-height", o.join("|"))
        },
        getCodeLineHeight: function(e) {
            var t = this,
            i = t._getLineHeight(e),
            n = t.getBlockTBPadding(e.find(".code-container")),
            a = n.top + n.bottom,
            s = Math.ceil((e.height() - a) / i),
            o = [];
            if (s > 0) {
                for (var h = s; h--;) o.push(i);
                o[0] += n.top,
                o[s - 1] += n.bottom
            } else o.push(n.top + n.bottom);
            e.attr("data-rows-height", o.join("|"))
        },
        getBlockTBPadding: function(e) {
            var t = {
                top: 0,
                bottom: 0
            };
            if (e.size() > 0) {
                var i = ["Top", "Bottom"];
                n.each(i,
                function(i, n) {
                    var a = parseInt(e.css("border" + n + "Width")) || 0,
                    s = parseInt(e.css("padding" + n)) || 0;
                    t[n.toLowerCase()] = a + s
                })
            }
            return t
        },
        getFloatImgLine: function(e, t, i) {
            function a(e) {
                if (e) {
                    var t = n(e),
                    i = t.offset().top,
                    a = parseInt(t.css("marginTop")) || 0;
                    i -= a,
                    s = Math.max(r, i),
                    o = t.outerHeight(!0) - Math.max(0, r - s),
                    h = s + o,
                    g = 0
                }
            }
            var s, o, h, g, r = e.offset().top,
            l = r,
            p = [],
            f = t.toArray(),
            u = f.shift();
            a(u);
            for (var m = 0,
            c = i.length; c > m; m++) {
                if (lineHeight = i[m], u) if (h > l && l >= s) g += lineHeight;
                else {
                    if (l >= h) {
                        g > 0 && p.push(g),
                        u = f.shift(),
                        a(u),
                        m--;
                        continue
                    }
                    p.push(lineHeight)
                } else p.push(lineHeight);
                l += lineHeight
            }
            return p.length < 1 && p.push(o),
            p
        },
        _getFloatImgLine: function(e, t, i) {
            var n = e.offset().top,
            a = [],
            s = 0,
            o = [],
            h = t.first(),
            g = h.offset().top,
            r = parseInt(h.css("marginTop"));
            g -= r;
            for (var l, p = h.outerHeight(!0), f = g + p, u = (end = 0, n), m = n, c = null, d = 0, v = i.length; v > d; d++) l = i[d],
            g >= u + l ? (a.push(l), m += l) : u > f && (null === c && (c = u), o.push(l)),
            u += l;
            return 0 === o.length && (c = f),
            s = c - m,
            s > 0 && a.push(s),
            a.concat(o)
        },
        getLines: function(e) {
            var t = this,
            i = (t.mediator, e.promise),
            s = e.data,
            o = s.$chapter,
            h = s.chapterCache,
            g = o.find(".p-b");
            g.each(function(e, i) {
                var a = n(i);
                a.hasClass("custom-get-line") ? t.getCustomLines(a) : t.getParagraphLines(a)
            });
            var r = n("<div></div>");
            return r.append(o.clone()),
            h.html = r.html(),
            h.gotLines = !0,
            a.recycleDom(r[0]),
            r = null,
            i.resolve(h),
            i
        },
        getOneParagraphLines: function(e) {
            var t = this,
            i = e.data,
            n = i.$p;
            t.getParagraphLines(n)
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            t.off(e),
            t.dispose(e)
        }
    });
    i.exports.LinesView = h
});;
define("ydcore:widget/book_view/txt_reader/js/v/format/operate.view.js",
function(e, t, o) {
    var n = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    i = e("ydcommon:widget/ui/js_core/mvp/view/view.js").View,
    a = {};
    a.lang = e("ydcommon:widget/ui/lib/lang/lang.js");
    var r = a.lang.createClass(function(e) {
        this._init(e)
    },
    {
        superClass: i
    }).extend({
        _init: function(e) {
            var t = this;
            n.extend(t, e, {
                isTop: !1,
                isBottom: !1,
                isPaging: !1,
                scrollStep: 40,
                onScrollTimer: null,
                touchX: 0,
                touchY: 0,
                touchStartY: 0,
                touchStartTime: null,
                domEventsList: [],
                catalogEl: n(".catalog-box").get(0)
            }),
            t._bindEvent()
        },
        _bindEvent: function() {
            var e = this,
            t = (e.mediator, e.Util);
            e.domEventsList.push({
                dom: e.$doc,
                eventName: "touchstart",
                fn: n.proxy(e.touchstart, e)
            },
            {
                dom: e.$doc,
                eventName: "touchmove",
                fn: n.proxy(e.touchmove, e)
            },
            {
                dom: e.$doc,
                eventName: "touchend",
                fn: n.proxy(e.touchend, e)
            },
            {
                dom: e.$doc,
                eventName: "mousewheel",
                fn: n.proxy(e.mousewheel, e)
            },
            {
                dom: e.$doc,
                eventName: "keydown",
                fn: n.proxy(e.keydown, e)
            }),
            n.each(e.domEventsList,
            function(e, t) {
                t.dom.on(t.eventName, t.fn)
            }),
            t.on("v:prev-screen", e),
            t.on("v:next-screen", e),
            t.on("v:set-page-scroll-state", e)
        },
        touchstart: function(e) {
            var t = this,
            o = e.originalEvent,
            n = o.touches[0];
            t.touchX = n.clientX,
            t.touchStartX = n.clientX,
            t.touchStartTime = new Date
        },
        touchmove: function(e) {
            var t = this;
            e.preventDefault(),
            e.stopPropagation();
            var o = e.originalEvent,
            i = o.touches[0],
            a = o.target;
            if (!t.catalogEl || t.catalogEl !== a && !n.contains(t.catalogEl, a)) {
                var r = i.clientX;
                r !== t.touchX && (t.touchX = r)
            }
        },
        touchend: function(e) {
            var t = this,
            o = t.mediator,
            n = (e.originalEvent, t.touchX - t.touchStartX);
            Math.abs(n) > 30 && o.fire(n > 0 ? "v:prev-screen": "v:next-screen")
        },
        _isBottom: function() {
            var e = this;
            return e.$readerContainer.offset().top + e.$readerContainer.height() <= e.$win.height() + e.$doc.scrollTop() ? !0 : !1
        },
        _isTop: function() {
            var e = this;
            return 0 === e.$doc.scrollTop() ? !0 : !1
        },
        mousewheel: function(e, t) {
            var o = this,
            i = o.mediator,
            a = e.target;
            if (!o.catalogEl || o.catalogEl !== a && !n.contains(o.catalogEl, a)) {
                {
                    o.scrollStep
                }
                0 > t && o._isBottom() ? (i.fire("v:next-screen"), i.fire("p:send-log", {
                    data: {
                        logAct: "page.wheel",
                        direct: "next",
                        pageType: "format"
                    }
                })) : t > 0 && o._isTop() && (i.fire("v:prev-screen"), i.fire("p:send-log", {
                    data: {
                        logAct: "page.wheel",
                        direct: "prev",
                        pageType: "format"
                    }
                }))
            }
        },
        keydown: function(e) {
            {
                var t = this,
                o = t.mediator,
                n = !0;
                t.scrollStep
            }
            switch (e.which) {
            case 37:
                o.fire("v:prev-screen"),
                o.fire("p:send-log", {
                    data: {
                        logAct: "page.press",
                        direct: "prev",
                        pageType: "format"
                    }
                });
                break;
            case 39:
                o.fire("v:next-screen"),
                o.fire("p:send-log", {
                    data: {
                        logAct: "page.press",
                        direct: "next",
                        pageType: "format"
                    }
                });
                break;
            default:
                n = !1
            }
            n && e.preventDefault()
        },
        prevScreen: function() {
            var e = this;
            e.isPaging || (e.isPaging = !0, e.mediator.when(["v:prev-page"]).then(function() {
                e.isPaging = !1
            }), e.mediator.fire("p:send-log", {
                data: {
                    logAct: "pagecount"
                }
            }))
        },
        nextScreen: function() {
            var e = this;
            e.isPaging || (e.isPaging = !0, e.mediator.when(["v:next-page"]).then(function() {
                e.isPaging = !1
            }), e.mediator.fire("p:send-log", {
                data: {
                    logAct: "pagecount"
                }
            }))
        },
        setPageScrollState: function(e) {
            var t = this,
            o = e.data || {};
            t.isTop = o.isTop,
            t.isBottom = o.isBottom
        },
        dispose: function() {
            var e = this,
            t = e.Util;
            n.each(e.domEventsList,
            function(e, t) {
                t.dom.off(t.eventName, t.fn)
            }),
            e.domEventsList = [],
            t.off(e),
            t.dispose(e)
        }
    });
    o.exports.OperateView = r
});;
define("ydcore:widget/book_view/book_spark_na/book_spark_na.js",
function(o, e, r) {
    var t = o("ydcommon:widget/ui/lib/jquery/jquery.js"),
    s = o("ydcommon:widget/ui/js_core/qrcode/qrcode.js"),
    d = o("ydcommon:widget/ui/js_core/log/log.js"),
    a = o("ydcommon:widget/ui/lib/cookie/cookie.js"),
    n = o("ydcommon:widget/ui/lib/json/json.js");
    o("ydcommon:widget/ui/js_core/countdown/countdown.js"),
    o("ydcore:widget/ui/placeholder/placeholder.js");
    var i = {
        init: function(o) {
            var e = this;
            e.$ele = t(".book-spark-wrap"),
            e.$title = e.$ele.find(".spark-title"),
            e.$closeButton = e.$ele.find(".close-button"),
            e.$sparkCountdown = e.$ele.find(".spark-countdown-wrap"),
            e.$bookCover = e.$ele.find(".spark-book-image"),
            e.$messageInput = e.$ele.find(".spark-message-input"),
            e.$vcodeInput = e.$ele.find(".spark-vcode-input"),
            e.$vcodeButton = e.$ele.find(".spark-vcode-button"),
            e.$vcodeImage = e.$ele.find(".spark-vcode-image"),
            e.$sendButton = e.$ele.find(".spark-message-button"),
            e.$errorTips = e.$ele.find(".spark-error-tips"),
            e.docTitle = e.$ele.attr("data-title"),
            e.docId = o.docId,
            e.bookCoverUrl = o.bookCover,
            e.freeDownloadCount = o.freeDownloadCount,
            e.appDownloadCount = o.appDownloadCount,
            e.sendSMSUrl = "/customer/interface/smsnadiversion",
            e.qrCodeUrl = "http://yd.baidu.com/ebook/" + e.docId + "?fr=read1_code&guide=pc&qrt=0&did=" + e.docId,
            e.statCode = "200254",
            e.statCodeButton = "200256",
            e.statCodeFr = "read1_msg",
            e.$messageInput.placeholder(),
            e.currentSpark = 0,
            e.delay = 2e4,
            e.duration = 3e5,
            e.popTime = 400,
            e.$closeButton.click(t.proxy(e._closeSpark, e)),
            e.$messageInput.on("focus",
            function() {
                t(".spark-vcode-wrap").show()
            }),
            e._loadVCode(),
            e._loadQRCode(),
            e._loadBookCover(e.bookCoverUrl, "74", "98"),
            e.$vcodeButton.click(t.proxy(e._loadVCode, e)),
            e.$sendButton.click(t.proxy(e._handleSendButton, e)),
            setTimeout(function() {
                e._show()
            },
            e.delay)
        },
        _loadVCode: function() {
            var o = this;
            t.ajax({
                url: "/customer/interface/commentvcode",
                method: "GET",
                data: {
                    type: 1
                },
                success: function(e) {
                    void 0 !== e.status.code && 0 === e.status.code && void 0 !== e.data && void 0 !== e.data.imgurl && o.$vcodeImage.attr({
                        src: e.data.imgurl
                    })
                },
                error: function() {}
            })
        },
        _loadQRCode: function() {
            {
                var o = this;
                new s(t(".spark-qrcode-image").get(0), {
                    text: o.qrCodeUrl,
                    width: 93,
                    height: 93,
                    colorDark: "#000000",
                    colorLight: "#FFFFFF",
                    correctLevel: s.CorrectLevel.L
                })
            }
        },
        _loadBookCover: function(o, e, r) {
            var t = this;
            t.$bookCover.attr({
                src: o,
                width: e,
                height: r
            })
        },
        _handleSendButton: function() {
            var o = this;
            if (!t(o).hasClass("spark-sended")) {
                var e = t.trim(o.$messageInput.val()),
                r = o.$vcodeInput.val(),
                s = /^(134|135|136|137|138|139|147|150|151|152|157|158|159|182|183|187|188|130|131|132|155|156|185|186|145|133|153|180|189|181|170)\d{8}$/;
                return s.test(e) ? 4 !== r.length ? void o._showErrorTips(o.$errorTips, "\u8bf7\u8f93\u5165\u6b63\u786e\u76844\u4f4d\u9a8c\u8bc1\u7801") : void t.ajax({
                    url: o.sendSMSUrl,
                    method: "GET",
                    data: {
                        phone: e,
                        vcode: r,
                        title: o.docTitle,
                        doc_id: o.docId,
                        fr: o.statCodeFr
                    },
                    cache: !1,
                    success: function(e) {
                        return e && e.status && void 0 !== e.status.code && 0 === e.status.code ? (o._hideErrorTips(o.$errorTips), t(".spark-vcode-wrap").hide(), o.$sendButton.addClass("spark-sended").text("\u5df2\u53d1\u9001"), o._closeSpark(), void d.xsend(2, o.statCodeButton)) : (1 === e.status.code ? o._showErrorTips(o.$errorTips, "\u7f3a\u5c11\u56fe\u4e66\u53c2\u6570") : 2 === e.status.code ? o._showErrorTips(o.$errorTips, "\u9a8c\u8bc1\u7801\u4e0d\u5b58\u5728") : 3 === e.status.code ? o._showErrorTips(o.$errorTips, "\u624b\u673a\u53f7\u4e0d\u6b63\u786e") : 4 === e.status.code ? o._showErrorTips(o.$errorTips, "\u9a8c\u8bc1\u7801\u9519\u8bef") : 5 === e.status.code ? o._showErrorTips(o.$errorTips, "\u5df2\u7ecf\u53d1\u9001\u8fc7\u4e86") : 6 === e.status.code ? o._showErrorTips(o.$errorTips, "\u53d1\u9001\u5931\u8d25\uff0c\u8bf7\u7a0d\u5019\u518d\u8bd5") : o._showErrorTips(o.$errorTips, "\u7cfb\u7edf\u9519\u8bef\uff0c\u8bf7\u60a8\u7a0d\u540e\u91cd\u8bd5"), o._loadVCode(), void o.$vcodeInput.val(""))
                    },
                    error: function() {
                        o._showErrorTips(o.$errorTips, "\u7cfb\u7edf\u9519\u8bef\uff0c\u8bf7\u60a8\u7a0d\u540e\u91cd\u8bd5"),
                        o._loadVCode(),
                        o.$vcodeInput.val("")
                    }
                }) : void o._showErrorTips(o.$errorTips, "\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u624b\u673a\u53f7\u7801")
            }
        },
        _showErrorTips: function(o, e) {
            t(o).text(e)
        },
        _hideErrorTips: function(o) {
            t(o).text("")
        },
        _show: function() {
            var o = this,
            e = (new Date).getTime() + o.duration;
            o.$ele.slideDown(o.popTime,
            function() {
                d.xsend(2, o.statCode),
                o.$sparkCountdown.countdown(e,
                function(o) {
                    var e = o.offset.minutes + "",
                    r = o.offset.seconds < 10 ? "0" + o.offset.seconds: "" + o.offset.seconds;
                    t(this).html(['<span class="figure minite-1">0</span>', '<span class="figure minite-2">' + e + "</span>", "<span>\u5206</span>", '<span class="figure second-1">' + r.charAt(0) + "</span>", '<span class="figure second-2">' + r.charAt(1) + "</span>", "<span>\u79d2\u5185\u6709\u6548</span>"].join(""))
                }).on("finish.countdown",
                function() {
                    o._closeSpark()
                })
            })
        },
        _closeSpark: function(o) {
            var e = this;
            o && o.preventDefault(),
            0 == e.currentSpark ? e.$ele.slideUp(e.popTime,
            function() {
                var o = a.get("naSparkFlag");
                return o = o ? n.parse(o) : {},
                o.shown ? void e.$ele.remove() : (o.shown = 1, a.set("naSparkFlag", n.stringify(o), {
                    path: "/",
                    expires: 31536e6
                }), e.currentSpark = 1, e._loadBookCover("http://img.baidu.com/img/iknow/wenku/r/image/2014-11-09/6aaea893c247946e8750d5a1f65c7a9d.jpg", "140", "100"), e.$bookCover.css({
                    width: "140px",
                    height: "100px",
                    "-moz-box-shadow": "none",
                    "-webkit-box-shadow": "none",
                    "box-shadow": "none"
                }), t(".spark-book-price").html("\u514d\u8d39&nbsp;&nbsp;<span>&yen;40,000</span>"), e.$title.html("2\u4e07\u672c\u7cbe\u54c1\u7535\u5b50\u4e66\uff0c\u5168\u90e8<span>\u514d\u8d39\u4e0b\u8f7d</span>\uff0c\u60a8\u4e00\u5b9a\u4e0d\u80fd\u9519\u8fc7\uff01"), t(".spark-qrcode-wrap").find("p").eq(2).html("\u6253\u5f00\u624b\u673a\u5fae\u4fe1\u6216<br>QQ\u7b49\u5de5\u5177\u626b\u63cf"), t(".spark-qrcode-wrap").find("p").eq(3).html(""), t(".spark-footer-wrap").find("span").eq(0).html("*\u60a8\u4e5f\u53ef\u4ee5\u5728\u5e94\u7528\u5546\u5e97\u641c\u201c\u767e\u5ea6\u9605\u8bfb\u201d\u5b89\u88c5\u5ba2\u6237\u7aef\uff0c\u5b89\u88c5\u5b8c\u6210\u5373\u53ef\u4e0b\u8f7d\u3002"), t(".spark-vcode-wrap").show(), e.$sendButton.removeClass("spark-sended").text("\u514d\u8d39\u53d1\u9001"), e._hideErrorTips(e.$errorTips), e.$vcodeInput.val(""), e.$messageInput.val(""), e._loadVCode(), t(".spark-vcode-wrap").hide(), t(".spark-qrcode-image").html(""), e.qrCodeUrl = "http://yd.baidu.com/ebook/" + e.docId + "?fr=read2_code&guide=pc&qrt=0&did=" + e.docId, e._loadQRCode(), e.statCode = "200255", e.statCodeButton = "200257", e.statCodeFr = "read2_msg", e.delay = 12e4, void setTimeout(function() {
                    e._show()
                },
                e.delay))
            }) : e.$ele.slideUp(e.popTime,
            function() {
                e.$ele.remove()
            }),
            e.$sparkCountdown.countdown("stop")
        },
        __placeHolderFunc: function() {}
    };
    r.exports = i
});;
define("ydcore:widget/ui/get_by_free/get_by_free.js",
function(e, t, i) {
    var o = e("ydcommon:widget/ui/lib/jquery/jquery.js"),
    c = e("ydcommon:widget/ui/js_core/_dialog/_dialog.js"),
    n = e("ydcommon:widget/ui/js_core/login/login.js"),
    d = !1,
    r = function() {
        return d ? d: (d = this, void this._init())
    };
    o.extend(r.prototype, {
        _init: function() {
            var e = this;
            e.clickBtn = o.proxy(e.click, e),
            o(document.body).on("click", ".get-by-free", e.clickBtn)
        },
        click: function(e) {
            var t = this,
            i = o(e.currentTarget);
            if (!i.hasClass("clicked")) {
                var c = i.data("doc-id");
                i.addClass("clicked"),
                n.login.verlogin(function() {
                    o.ajax({
                        url: "/goods/interface/sendfreebook?doc_id=" + c,
                        success: function() {
                            o(document.body).trigger("GetByFree.complete", {
                                id: c
                            }),
                            t.showPop()
                        },
                        dataType: "json"
                    })
                })
            }
        },
        showPop: function() {
            c.alert({
                content: '<p class="getByFree-complete">\u9886\u53d6\u6210\u529f~</p>',
                title: "\u63d0\u793a",
                height: 200,
                width: 300
            })
        },
        off: function() {
            o(document.body).off("click", ".get-by-free", me.clickBtn)
        }
    }),
    d = new r,
    i.exports.GetByFree = r,
    i.exports._instance = d
});