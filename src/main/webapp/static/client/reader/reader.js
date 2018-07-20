EPUBJS.reader = {};
EPUBJS.reader.plugins = {}; //-- Attach extra Controllers as plugins (like search?)
/*
epub中不需要设置目录页，若要删除这个目录页，请查找“catalog”关键字，查看相关代码。 若要恢复目录的显示，只需要注释掉相关代码即可
这个关键字可能随着实际目录页的命名而不同
*/
(function(root, $) {

	var previousReader = root.ePubReader || {};

	var ePubReader = root.ePubReader = function(path, options) {
		return new EPUBJS.Reader(path, options);
	};

	_.extend(ePubReader, {
		noConflict : function() {
			root.ePubReader = previousReader;
			return this;
		}
	});
	
	//console.log( typeof define, typeof module);

	//exports to multiple environments
	if (typeof define === 'function' && define.amd)
	//AMD
	define(function(){ return Reader; });
	else if (typeof module != "undefined" && module.exports)
	//Node
	module.exports = ePubReader;

})(window, jQuery);

EPUBJS.Reader = function(path, _options) {
	var reader = this;
	var book;
	var plugin;
	this.fontSizeMap = {};
	
	this.settings = _.defaults(_options || {}, {
		restore : true,
		reload : false,
		bookmarks : null,
		bookmarksText : null,
		contained : null,
		bookKey : null,
		styles : null,
		sidebarReflow: false
	});
	this.setBookKey(path); //-- This could be username + path or any unique string
	
	if(this.settings.restore && this.isSaved()) {
		this.applySavedSettings();
	}

	this.settings.styles = this.settings.styles || {
		fontSize : "100%"
	};
	
	this.book = book = new EPUBJS.Book({
		bookPath: path,
		restore: this.settings.restore,
		preloadNextChapter: false, // 禁止加载下一页，因为计算总页数时会加载所有的页
		reload: this.settings.reload,
		contained: this.settings.contained,
		bookKey: this.settings.bookKey,
		styles: this.settings.styles,
		calcBookPages : false // 计算总页码
	});
	
	if(this.settings.previousLocationCfi) {
		//console.log("line 69", this.settings.previousLocationCfi);
		book.gotoCfi(this.settings.previousLocationCfi);
	}
	
	this.offline = false;
	this.sidebarOpen = false;
	if(!this.settings.bookmarks) {
		this.settings.bookmarks = [];
		this.settings.bookmarksText = [];
	}

	book.renderTo("viewer");

	//加载书
	reader.ReaderController = EPUBJS.reader.ReaderController.call(reader, book);
	//设置
	// reader.SettingsController = EPUBJS.reader.SettingsController.call(reader, book);
	//遮罩层
	reader.ControlsController = EPUBJS.reader.ControlsController.call(reader, book);
	//左侧收藏
    // reader.SidebarController = EPUBJS.reader.SidebarController.call(reader, book);
	//书签
    // reader.BookmarksController = EPUBJS.reader.BookmarksController.call(reader, book);
	//评论
    // reader.CommentController = EPUBJS.reader.CommentController.call(reader, book);
	
	// Call Plugins
	for(plugin in EPUBJS.reader.plugins) {
		if(EPUBJS.reader.plugins.hasOwnProperty(plugin)) {
			reader[plugin] = EPUBJS.reader.plugins[plugin].call(reader, book);
		}
	}
	
	book.ready.all.then(function() {
		// reader.ReaderController.hideLoader();
	});

	book.getMetadata().then(function(meta) {
		reader.MetaController = EPUBJS.reader.MetaController.call(reader, meta);
	});

	book.getToc().then(function(toc) {
		reader.TocController = EPUBJS.reader.TocController.call(reader, toc);
	});
	
	book.getSpine().then(function(spine) {
		//console.log(spine);
		//book.calcTotalPages();
	});
	
	window.addEventListener("beforeunload", this.unload.bind(this), false);
	
	document.addEventListener('keydown', this.adjustFontSize.bind(this), false);
	
	book.on("renderer:keydown", this.adjustFontSize.bind(this));
	book.on("renderer:keydown", reader.ReaderController.arrowKeys.bind(this));

	return this;
};

EPUBJS.Reader.prototype.adjustFontSize = function(e) {
	var fontSize;
	var interval = 2;
	var PLUS = 187;
	var MINUS = 189;
	var ZERO = 48;
	var MOD = (e.ctrlKey || e.metaKey );
	
	if(!this.settings.styles) return;
	
	if(!this.settings.styles.fontSize) {
		this.settings.styles.fontSize = "100%";
	}
	
	fontSize = parseInt(this.settings.styles.fontSize.slice(0, -1));

	if(MOD && e.keyCode == PLUS) {
		e.preventDefault();
		this.book.setStyle("fontSize", (fontSize + interval) + "%");
		
	}

	if(MOD && e.keyCode == MINUS){

		e.preventDefault();
		this.book.setStyle("fontSize", (fontSize - interval) + "%");
	}
	
	if(MOD && e.keyCode == ZERO){
		e.preventDefault();
		this.book.setStyle("fontSize", "100%");
	}
};

EPUBJS.Reader.prototype.addBookmark = function(cfi) {
	var present = this.isBookmarked(cfi);
	if(present > -1 ) return;
	
	var prevEl = this.book.render.visibileEl;
	var visibileEl = this.book.render.findFirstVisible(prevEl);
	var text = visibileEl.innerHTML.replace(/<[^>]+>/g, "");
	if (text && text.length > 200) {
		text = text.substring(0, 200) + "...";
	}
	this.settings.bookmarks.push(cfi);
	this.settings.bookmarksText.push(text);
	
	this.trigger("reader:bookmarked", cfi);
};

EPUBJS.Reader.prototype.removeBookmark = function(cfi) {
	var bookmark = this.isBookmarked(cfi);
	if( bookmark === -1 ) return;
	
	//delete this.settings.bookmarks[bookmark];
	//delete this.settings.bookmarksText[bookmark];
	this.settings.bookmarks.splice(bookmark, 1);
	this.settings.bookmarksText.splice(bookmark, 1);
	
	this.trigger("reader:unbookmarked", bookmark);
};

EPUBJS.Reader.prototype.isBookmarked = function(cfi) {
	var bookmarks = this.settings.bookmarks;
	//console.log("isBookmarked", bookmarks, bookmarks.indexOf(cfi));
	return bookmarks.indexOf(cfi);
};


EPUBJS.Reader.prototype.clearBookmarks = function() {
	this.settings.bookmarks = [];
	this.settings.bookmarksText = [];
};

//-- Settings
EPUBJS.Reader.prototype.setBookKey = function(identifier){
	if(!this.settings.bookKey) {
		this.settings.bookKey = "epubjsreader:" + EPUBJS.VERSION + ":" + window.location.host + ":" + identifier;
	}
	return this.settings.bookKey;
};

//-- Checks if the book setting can be retrieved from localStorage
EPUBJS.Reader.prototype.isSaved = function(bookPath) {
	var storedSettings = localStorage.getItem(this.settings.bookKey);

	//console.log(storedSettings);
	if( !localStorage ||
		storedSettings === null) {
		return false;
	} else {
		return true;
	}
};

EPUBJS.Reader.prototype.removeSavedSettings = function() {
	localStorage.removeItem(this.settings.bookKey);
};

EPUBJS.Reader.prototype.applySavedSettings = function() {
		try {
			var stored = JSON.parse(localStorage.getItem(this.settings.bookKey));
			//console.log(localStorage.getItem(this.settings.bookKey), this.settings.bookKey);
			//console.log(stored);
		} catch(e) {

		}
		if(stored) {
			this.settings = _.defaults(this.settings, stored);
			return true;
		} else {
			return false;
		}
};

EPUBJS.Reader.prototype.saveSettings = function(){
	if(this.book) {
		this.settings.previousLocationCfi = this.book.getCurrentLocationCfi();
	}

	localStorage.setItem(this.settings.bookKey, JSON.stringify(this.settings));
};

EPUBJS.Reader.prototype.unload = function(event){
	if(this.settings.restore) {
		this.saveSettings();
	}
	if (typeof updateReadRate != "undefined") {
		// 上报阅读进度
		var rate = 0;
		var currentPage = this.book.currentPage;
		rate = (currentPage / this.book.totalPages * 100).toFixed(2);
		updateReadRate(rate);
	}
};

//-- Enable binding events to reader
RSVP.EventTarget.mixin(EPUBJS.Reader.prototype);
EPUBJS.reader.BookmarksController = function() {
	var reader = this;
	var book = this.book;

	var $bookmarks = $("#bookmarkDiv"),
			$list = $bookmarks.find("#bookmarkList");
	
	var docfrag = document.createDocumentFragment();
	
	var show = function() {
		if(reader.TocController) {
			reader.TocController.hide();
		};
		if(reader.CommentController) {
			reader.CommentController.hide();
		};
		$bookmarks.show();
		$("#contentDiv").hide();
    	if ($("#divScrollBar2").length == 0) {
	    	jsScroll(document.getElementById('div2'), 5, 'divScrollBar', 'divScrollBar2');
    	} else {
    		//$("#divScrollBar2").show();
    		jScrollBarShow("div2");
    	}
	};

	var hide = function() {
		$bookmarks.hide();
		$("#divScrollBar2").hide();
		$("#contentDiv").show();
	};
	
	var counter = 0;
	
	var createBookmarkItem = function(cfi, index) {
		var listitem = document.createElement("dd"),
		        content = document.createElement("p"),
		        h5 = document.createElement("h5"),
		        deleteBtn = document.createElement("a")
				link = document.createElement("a");
		
		h5.appendChild(deleteBtn);
		deleteBtn.innerHTML = "删除";
		deleteBtn.setAttribute("href","javascript:void(0)");
		
		listitem.id = "bookmark-"+counter;
		listitem.classList.add('list_item');
		//-- Parse Cfi
		var currentCfi = new EPUBJS.EpubCFI(cfi);
		//element = book.render.epubcfi.getElement(currentCfi, book.render.doc);
		//console.log(element);
		var text = reader.settings.bookmarksText[index];
		console.log("bookmark:",text);
		//content.textContent = /(^\s*)|(\s*$)/g.test(text) ? text : "[图片]";
		if(text.indexOf("dajianet.com")>=0){
			text="图片";
			content.textContent=text;
		}else{
			content.textContent=text;
		}


		content.textContent = text;
		content.style.cursor = "pointer";

		content.setAttribute("href", cfi);
		
		content.addEventListener("click", function(event){
				var cfi = this.getAttribute('href');
				book.gotoCfi(cfi);
				event.preventDefault();
				reader.BookmarksController.hide();
		}, false);
		
		listitem.appendChild(content);
		listitem.appendChild(h5);
		
		counter++;
		
		$(deleteBtn).click(function() {
			reader.removeBookmark($(this).parent().parent().find("p").attr("href"));
			//$bookmark.removeClass("icon-bookmark").addClass("icon-bookmark-empty");
			//$(".icon-bookmark").removeClass("icon-bookmark").addClass("icon-bookmark-empty");
			jScrollBarShow("div2");
		});
		
		return listitem;
	};

	var index = 0;
	this.settings.bookmarks.forEach(function(cfi) {
		if (!cfi) return;
		var bookmark = createBookmarkItem(cfi, index++);
		docfrag.appendChild(bookmark);
	});
	
	$list.append(docfrag);
	
	this.on("reader:bookmarked", function(cfi) {
		if (!cfi) return;
		var item = createBookmarkItem(cfi, reader.settings.bookmarksText.length - 1);
		$list.append(item);
	});
	
	this.on("reader:unbookmarked", function(index) {
		//console.log("delete bookmark", index);
		index++;
		//var $item = $("#bookmark-"+index);
		var $item = $list.find("dd:nth-child(" + index + ")");
		$item.remove();
	});

	return {
		"show" : show,
		"hide" : hide
	};
};
EPUBJS.reader.ControlsController = function(book) {
	var reader = this;

	var $store = $("#store"),
			$fullscreen = $("#fullscreen"),
			$fullscreenicon = $("#fullscreenicon"),
			$cancelfullscreenicon = $("#cancelfullscreenicon"),
			$slider = $("#slider"),
			$main = $("#main"),
			$sidebar = $("#sidebar"),
			$settings = $("#setting"),
			$bookmark = $("#bookmark,.icon-bookmark,.icon-bookmark-empty");

	var goOnline = function() {
		reader.offline = false;
		// $store.attr("src", $icon.data("save"));
	};

	var goOffline = function() {
		reader.offline = true;
		// $store.attr("src", $icon.data("saved"));
	};
	
	var fullscreen = false;

	book.on("book:online", goOnline);
	book.on("book:offline", goOffline);

	$slider.on("click", function () {
		if(reader.sidebarOpen) {
			reader.SidebarController.hide();
			$slider.addClass("icon-menu");
			$slider.removeClass("icon-right");
		} else {
			reader.SidebarController.show();
			$slider.addClass("icon-right");
			$slider.removeClass("icon-menu");
		}
	});

	$settings.on("click", function() {
		reader.SettingsController.show();
	});

	$bookmark.on("click", function() {
		var cfi = reader.book.getCurrentLocationCfi();
		var bookmarked = reader.isBookmarked(cfi);
		
		if(bookmarked === -1) { //-- Add bookmark
			reader.addBookmark(cfi);
			//$bookmark.addClass("icon-bookmark").removeClass("icon-bookmark-empty"); 
			$(".icon-bookmark-empty").removeClass("icon-bookmark-empty").addClass("icon-bookmark");
		} else { //-- Remove Bookmark
			reader.removeBookmark(cfi);
			//$bookmark.removeClass("icon-bookmark").addClass("icon-bookmark-empty"); 
			$(".icon-bookmark").removeClass("icon-bookmark").addClass("icon-bookmark-empty");
		}

	});

	book.on('renderer:pageChanged', function(cfi){
		//-- Check if bookmarked
		var bookmarked = reader.isBookmarked(cfi);
		
		if(bookmarked === -1) { //-- Not bookmarked
			//$bookmark.removeClass("icon-bookmark").addClass("icon-bookmark-empty"); 
			$(".icon-bookmark").removeClass("icon-bookmark").addClass("icon-bookmark-empty");
		} else { //-- Bookmarked
			//$bookmark.addClass("icon-bookmark").removeClass("icon-bookmark-empty");
			$(".icon-bookmark-empty").addClass("icon-bookmark").removeClass("icon-bookmark-empty");
		}
		//console.log("pageChanged", cfi, book.spingPos, book.chapters[book.spingPos]);
		if (!book.calcTotalPaging) {
			showPage();
		}
		
	});
	
	function showPage() {
		$("#gotoBar").hide();
		var currentPage = 0;
		//console.log("readjs 459", book.spinePos);
		for (var i = 0; i < book.spinePos; i++) {
			// 切换字体大小时会出现未定义问题
			if (book.chapters[i].pages) {
				currentPage += book.chapters[i].pages;
			}
		}
		currentPage += book.render.chapterPos;
		if (!currentPage) {
			console.log("当前页计算出错", book.render.chapterPos);
			for (var i = 0; i < book.spinePos; i++) {
				console.log(book.chapters[i].pages);
			}
			return;
		}
		console.log("pos====", book.spinePos);
		if (book.totalPages && !book.calcTotalPaging) {
			book.currentPage = currentPage;
			if (book.tocArr && book.spinePos > 0) {
				//显示章节目录
				try 
				{ 
					
					if (book.tocChapterArr.length>book.spinePos &&  typeof(book.tocChapterArr[book.spinePos].label) != 'undefined') {
						$("#chapterTitle").html(book.tocChapterArr[book.spinePos].label);
						$(".chapterTitle").html(book.tocChapterArr[book.spinePos].label);
					}
				} 
				catch (e) 
				{ 
					if (book.tocChapterArr.length>book.spinePos && typeof(book.tocChapterArr[book.spinePos].label) != 'undefined') {
						$("#chapterTitle").html(book.tocChapterArr[book.spinePos].label);
						$(".chapterTitle").html(book.tocChapterArr[book.spinePos].label);
					}
					
				}
				
			}
			var percent = (currentPage/book.totalPages*100).toFixed() + "%";
			// 单页
			$("#currentPage").html(currentPage + " / " + book.totalPages);
			// 双页
			$("#currentPage1").html((currentPage * 2 - 1) + " / " + (book.totalPages * 2));
			$("#currentPage2").html((currentPage * 2) + " / " + (book.totalPages * 2));
			// 单页
			$("#progressTitle").html(currentPage + " / " + book.totalPages + " " + percent);
			// 双页
			$("#progressTitle2").html((currentPage * 2 - 1) + " / " + (book.totalPages * 2) + " " + percent);
			$("#progressBar").css("width", percent);
		} else {
			$("#currentPage").html("loading...");
			$("#progressTitle").html("loading...");
		}
		//$("#pageInfo2").html( "(" + (currentPage * 2) + "/" + (book.totalPages * 2) + ")");
		//console.log(book.getCurrentLocationCfi(), book.totalPages, book.chapters);
		//console.log("show page info:", book.render.currentChapter,  "(" + currentPage + "/" + book.totalPages + ")");
	}
	
	window.addEventListener("resize", function() {
		//console.log("窗口大小变化，IE中会不正确的触发该事件", $("#viewer").width(), $("#viewer").height());
		if (book.calcTotalPaging) return;
		//console.log("line 413  页面大小改变:", book.totalPages);
		if (EPUBJS.reader.timeout) clearTimeout(EPUBJS.reader.timeout);
		EPUBJS.reader.timeout = setTimeout( function() {
			//if(!book._prepareCalcTotalPage)
			//console.log("计算总页码！");
			book.calcBookPages();
		},1000); // 考虑到人工改变窗口大小时可能动作比较慢，所以设置较长的等待时间
	});
	
	book.on("book:readyComplete", function() {
		showPage();
		//console.log("line 413 总页数为:", book.totalPages);
		reader.ReaderController.hideLoader();
		
		setTimeout(function(){
			var chaptersPage = [];
			for (var i = 0; i < book.spinePos; i++) {
				chaptersPage.push(book.chapters[i].pages);
			}
			var fontSize = reader.settings.styles.fontSize || "100%";
			//console.log("++++++++++++++", fontSize, book.totalPages);
			reader.fontSizeMap[fontSize] = {
				totalPages : book.totalPages,
				chaptersPage : chaptersPage
			}
		});
		
		if (typeof getReadRate != "undefined") {
			getReadRate();
		}
	});
	
	return {
		"showPage" : showPage
	};
};
EPUBJS.reader.MetaController = function(meta) {
	//console.log(meta); // alert 2
	var title = meta.bookTitle,
			author = meta.creator;

	var $title = $("#book-title"),
			$author = $("#chapter-title"),
			$dash = $("#title-seperator");

		//document.title = title+" – "+author;

		$title.html(title);
		$author.html(author);
		$dash.show();
};
EPUBJS.reader.ReaderController = function(book) {
	var $main = $("#main"),
			$divider = $("#divider"),
			$loader = $("#loader"),
			$next = $("#nextPage"),
			$prev = $("#prevPage");
	var reader = this;

	/*目录显示与隐藏，样式对IE不兼容*/
	var slideIn = function() {
        if (reader.settings.sidebarReflow){
                $('#main').removeClass('single');
        } else {
                $main.removeClass("closed");
        }
    };

    var slideOut = function() {
        if (reader.settings.sidebarReflow){
                $('#main').addClass('single');
        } else {
                $main.addClass("closed");
        }
    };

	var showLoader = function() {
		$loader.show();
		hideDivider();
	};

	var hideLoader = function() {
		$loader.hide();
		
		//-- If the book is using spreads, show the divider
		if(book.settings.spreads) {
			showDivider();
		}
	};

	var showDivider = function() {
		$divider.addClass("show");
	};

	var hideDivider = function() {
		$divider.removeClass("show");
	};

	var keylock = false;

	var arrowKeys = function(e) {		
		if(e.keyCode == 37) { 
			book.prevPage();
			$prev.addClass("cur");

			keylock = true;
			setTimeout(function(){
				keylock = false;
				$prev.removeClass("cur");
			}, 100);

			 e.preventDefault();
		}
		if(e.keyCode == 39) { 
			book.nextPage();
			$next.addClass("cur");

			keylock = true;
			setTimeout(function(){
				keylock = false;
				$next.removeClass("cur");
			}, 100);

			 e.preventDefault();
		}
	}

	document.addEventListener('keydown', arrowKeys, false);
	
	//*
	if(document.attachEvent){
	     document.attachEvent('onmousewheel',scrollFunc);
	} else if(document.addEventListener){
	    document.addEventListener('DOMMouseScroll',scrollFunc,false); // firefox
	    document.addEventListener('mousewheel',scrollFunc,false); // chrome...
	}
	//window.onmousewheel=document.onmousewheel=scrollFunc;
	
	function scrollFunc(e) {
		//console.log("mouse scroll page");
		if (keylock == true || $("#contentDiv").is(":hidden")) return true;
		e = e || window.event;
		var delta = 0;
		if (e.wheelDelta) { // IE/Opera.
           delta = e.wheelDelta / 120;
           console.log("wheelDelta", e.wheelDelta);
        } else if (e.detail) {   
           delta = -e.detail / 3;  
           console.log("detail", e.detail);
        }
		console.log("mouse wheel", delta);
		if (delta < 0) {
			//console.log("down");
			keylock = true;
			book.nextPage();
			$next.addClass("cur");
			setTimeout(function(){
				keylock = false;
				$next.removeClass("cur");
			}, 200);
		} else {
			//console.log("up");
			keylock = true;
			book.prevPage();
			$prev.addClass("cur");
			setTimeout(function(){
				keylock = false;
				$prev.removeClass("cur");
			}, 200);
		}
	}//*/

	$next.on("click", function(e){
		book.nextPage();
		e.preventDefault();
	});

	$prev.on("click", function(e){
		book.prevPage();
		e.preventDefault();
	});
	
	book.on("book:spreads", function(){
		if(book.settings.spreads) {
			showDivider();
		} else {
			hideDivider();
		}
	});

	return {
		"slideOut" : slideOut,
		"slideIn"  : slideIn,
		"showLoader" : showLoader,
		"hideLoader" : hideLoader,
		"showDivider" : showDivider,
		"hideDivider" : hideDivider,
		"arrowKeys" : arrowKeys
	};
};
EPUBJS.reader.SettingsController = function() {
	var book = this.book;
	var reader = this;
	var $settings = $("#settings-modal"),
			$overlay = $(".overlay");

	var show = function() {
		$settings.addClass("md-show");
	};

	var hide = function() {
		$settings.removeClass("md-show");
	};

	var $sidebarReflowSetting = $('#sidebarReflow');

	$sidebarReflowSetting.on('click', function() {
		reader.settings.sidebarReflow = !reader.settings.sidebarReflow;
	});

	$settings.find(".closer").on("click", function() {
		hide();
	});

	$overlay.on("click", function() {
		hide();
	});

	return {
		"show" : show,
		"hide" : hide
	};
};
EPUBJS.reader.SidebarController = function(book) {
	var reader = this;

	var $sidebar = $("#sidebar"),
			$panels = $("#panels");

	var activePanel = "Toc";

	var changePanelTo = function(viewName) {
		var controllerName = viewName + "Controller";
		
		if(activePanel == viewName || typeof reader[controllerName] === 'undefined' ) return;
		reader[activePanel+ "Controller"].hide();
		reader[controllerName].show();
		activePanel = viewName;

		$panels.find('.active').removeClass("active");
		$panels.find("#show-" + viewName ).addClass("active");
	};
	
	var getActivePanel = function() {
		return activePanel;
	};
	
	var show = function() {
		reader.sidebarOpen = true;
		reader.ReaderController.slideOut();
		$sidebar.addClass("open");
	}

	var hide = function() {
		reader.sidebarOpen = false;
		reader.ReaderController.slideIn();
		$sidebar.removeClass("open");
	}

	$panels.find(".show_view").on("click", function(event) {
		var view = $(this).data("view");

		changePanelTo(view);
		event.preventDefault();
	});

	return {
		'show' : show,
		'hide' : hide,
		'getActivePanel' : getActivePanel,
		'changePanelTo' : changePanelTo
	};
};
EPUBJS.reader.TocController = function(toc) {
	var book = this.book;
	//book.toc = toc;
	var tocArr = [];
	var tocChapterArr = [];
	var $list = $("#tocViewList"),
			docfrag = document.createDocumentFragment();

	var currentChapter = false;

	var generateTocItems = function(toc, level) {

		if(!level) level = 1;

		toc.forEach(function(chapter) {
			tocArr.push(chapter);
			if(!chapter.parent){
				tocChapterArr.push(chapter);
			}
			//console.log(chapter); // alert 1
			if(chapter.href === "catalog.html") return; // 去除目录
					
			if(chapter.subitems.length > 0) {
				level++;
				generateTocItems(chapter.subitems, level);
			}
		});
	};

	var onShow = function() {
		if (reader.BookmarksController) {
			reader.BookmarksController.hide();
		}
		if (reader.CommentController) {
			reader.CommentController.hide();
		}
		$("#catalogDiv").show();
    	$("#contentDiv").hide();
    	if ($("#divScrollBar1").length == 0) {
	    	jsScroll(document.getElementById('div1'), 5, 'divScrollBar','divScrollBar1');
    	} else {
    		//$("#divScrollBar1").show();
    		jScrollBarShow("div1");
    	}
	};

	var onHide = function() {
		$("#catalogDiv").hide();
		$("#divScrollBar1").hide();
		$("#contentDiv").show();
	};

	//book.on('renderer:chapterDisplayed', chapterChange);

	generateTocItems(toc);
	console.log("===========", tocArr.length, book.spinePos);

	book.tocArr = tocArr;
	book.tocChapterArr = tocChapterArr;
	if (book.tocChapterArr[book.spinePos-1]) {
		$("#chapterTitle").html(book.tocChapterArr[book.spinePos-1].label);
	}
	


	 
	
//	book.toc.forEach(function(tocItem){  
//		chapter.myshow =false;
//		if(tocItem.href.indexOf(chapter.href) >-1 ){
//			chapter.myshow =true;
//	    	return;
//	    }else{
//		    tocItem.subitems.forEach(function(subitem){
//		    	 if(subitem.href.indexOf(chapter.href) >-1){
//		    		 chapter.myshow =true;
//				    	return;
//				  };
//		    });
//	    }

	
	tocArr.forEach(function(chapter) {
		chapter.myshow = false;
		//add byzhaoq  judge need show
		for(var i=0;i<book.chapters.length;i++){
			var tocItem = book.chapters[i];
			
			if(tocItem.href== chapter.href ){
				chapter.myshow =true;
			}
		}
		if(chapter.myshow){

			var dd = document.createElement("dd"),
			    link = document.createElement("a");
			dd.id = "toc-"+/*chapter.id*/chapter.href.replace("\.","I").replace("\/","");
			link.href = "javascript:void(0)";
			if (chapter.href) {
				link.setAttribute("src", chapter.href);
			} else {
				link.style.color = "#aeaeae";
			}
			link.innerHTML = $.trim(chapter.label);
			link.cssName = "toc_link";
			dd.appendChild(link);
			docfrag.appendChild(dd);
		}
		
	});
	
	$list.append(docfrag);
	
	$list.find("a").on("click", function(event){
		var url = this.getAttribute('src');
		
		if (url) {
			//console.log(url); // alert 8
			//-- Provide the Book with the url to show
			//   The Url must be found in the books manifest
			book.goto(url);
			onHide();
		} else {
			//alert("请购买");
		}
		
		event.preventDefault();
	});

	return {
		"show" : onShow,
		"hide" : onHide
	};
};
EPUBJS.reader.CommentController = function() {
	var book = this.book;

	var $list = $("#commentsList"),
			docfrag = document.createDocumentFragment();

	var onShow = function() {
		if (reader.BookmarksController) {
			reader.BookmarksController.hide();
		}
		if (reader.TocController) {
			reader.TocController.hide();
		}
		$("#commentDiv").show();
    	$("#contentDiv").hide();
    	if ($("#divScrollBar3").length == 0) {
	    	jsScroll(document.getElementById('div3'), 5, 'divScrollBar','divScrollBar3');
    	} else {
    		jScrollBarShow("div3");
    	}
	};

	var onHide = function() {
		$("#commentDiv").hide();
		$("#divScrollBar3").hide();
		$("#contentDiv").show();
	};

	return {
		"show" : onShow,
		"hide" : onHide
	};
};
