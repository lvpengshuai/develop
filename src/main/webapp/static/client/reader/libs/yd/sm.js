/*
SelectionMenu 1.1
http://github.com/molily/selectionmenu
by molily (zapperlott@gmail.com, http://molily.de/)

EN: SelectionMenu displays a context menu when the user selects some text on the page

EN: License: Public Domain
EN: You're allowed to copy, distribute and change the code without restrictions

*/

// EN: Create a private scope using an anonymous function,
// EN: save the return value in a global variable.
function getX(e) {
	e = e || window.event;
	return e.pageX || e.clientX + (document.body.scrollLeft || document.documentElement.scrollLeft);
}

function getY(e) {
	e = e|| window.event;
	return e.pageY || e.clientY + (document.body.scrollTop || document.documentElement.scrollTop);
}

var SelectionMenu = (function (window, document) {
	
	// EN: The menu element which is inserted when selecting text
	var span = null;
	var _pageX=0,_pageY=0,_clientX = 0,_sx=0;
	var domobj = null;
	
	// EN: Shared private helper functions	
	function addEvent (obj, type, fn) {
		// EN: Feature dection DOM Events / Microsoft
		if (obj.addEventListener) {
			obj.addEventListener(type, fn, false);
		} else if (obj.attachEvent) {
			obj.attachEvent('on' + type, function () {
				return fn.call(obj, window.event);
			});
		}
	}
	
	// EN: Publish addEvent as a static method
	// EN: (attach it to the constructor object)
	SelectionMenu.addEvent = addEvent;
	
	function getSelection () {
		// EN: Feature dection HTML5 / Microsoft
		if (window.getSelection) {
			return window.getSelection();
		} else if (document.selection && document.selection.createRange) {
			return document.selection.createRange();
		} else {
			// EN: No browser support available for the required features
			return false;
		}
	}
	
	function getSelectedText (selection) {
		// EN: Feature detection HTML5 / Microsoft
		return selection.toString ? selection.toString() : selection.text;
	}

	
	function contains (a, b) {
		// EN: Feature detection DOM Core / Microsoft
		return a.compareDocumentPosition ? !!(a.compareDocumentPosition(b) & 16) : a.contains(b);
	}
	
	function mouseOnMenu (e) {
		// Greife auf das Zielelement des Ereignisses zu
		// EN: Feature detection DOM Events / Microsoft
		var target = e.target || e.srcElement;
		// Ist das Zielelement das Menü oder darin enthalten?
		return target == span || contains(span, target);
	}
	
	// EN: Main constructor function
	function SelectionMenu (options) {
		var instance = this;
		
		// EN: Copy members from the options object to the instance
		instance.id = options.id || 'selection-menu';
		instance.menuHTML = options.menuHTML;
		instance.spanId = options.spanId;
		instance.minimalSelection = options.minimalSelection || 2;
		instance.container = options.container;
		instance.handler = options.handler;
		
		// EN: Initialisation
		instance.create();
		instance.setupEvents();
	}
	
	SelectionMenu.prototype = {
		
		create : function () {
			var instance = this;
			
			// EN: Create the menu container if necessary
			if (span) {
				return;
			}
			
			span = document.getElementById(instance.spanId);
			span.id = instance.spanId;
		},
		
		setupEvents : function () {
			
			var instance = this;
			var container = instance.container;
			
			// EN: Hide the menu on mouse down
			addEvent(container, 'mousedown', function (e) {
				//console.log('aaaa'+"sc:"+$(document).scrollLeft()+"  mx:"+getX(e));
				instance.hide(e);
			});
			
			// EN: Insert the menu on mouseup given some text is selected
			addEvent(container, 'mouseup', function (e) {
				_pageX = getX(e);
				_pageY = getY(e);
				_clientX = e.clientX;
				_sx = document.body.scrollLeft || document.documentElement.scrollLeft;
//				console.log("_pageX",_pageX,"_pageY",_pageY,"_clientX", _clientX);
				instance.insert(e);
				
				// EN: After a delay, check if the text was deselected
				window.setTimeout(function () {
					instance.hideIfNoSelection();
				}, 0);
				
			});
			
			instance.setupMenuEvents();
		},
		
		setupMenuEvents : function () {
			var instance = this;
			
			// EN: Register the handler for clicks on the menu
			addEvent(span, 'click', function (e) {
				instance.handler.call(instance, e);
				return false;
			});
			
			// EN: Prevent IE to select the text of the menu
			span.unselectable = true;
		},
		
		hide : function (e) {
			// EN: Abort if an event object was passed and the click hit the menu itself
			if (e && mouseOnMenu(e)) {
				return;
			}
			
			$(span).hide();
		},
		
		hideIfNoSelection : function () {
			var instance = this;
			var selection = getSelection();
			if (!selection) {
				return;
			}
			var selectedText = getSelectedText(selection);
			if (!selectedText.length) {
				instance.hide();
			}
		},
		
		insert : function (e) {
			var instance = this;

			// EN: Abort if the mouse event occured at the menu itself
			if (mouseOnMenu(e)) {
				return;
			}

			// EN: Get a Selection object or a TextRange (IE)
			var selection = getSelection();
			if (!selection) {
				// EN: No browser support available for the required features
				return;
			}

			// EN: Get the selected text
			var selectedText = getSelectedText(selection);
			instance.selectedText = selectedText;

			// EN: Abort if the selected text is too short
			if (selectedText.length < instance.minimalSelection) {
				instance.hide(e);
				return;
			}

			// EN: Menu positioning
			instance.position();
		},
		
		position : function () {
			var body_width = document.body.clientWidth;
			var spanWidth = 350; // 弹出层的固定宽度
			var spanHeight = 110; // 弹出层的高度+行高
			var lineHeight = 20;
			
			var xx = _pageX,
			    yy = _pageY-spanHeight-lineHeight;
			if((xx+spanWidth) > (_sx + 540)) {
				//xx = _pageX - (xx+spanWidth-_sx-540);
				xx = _sx + 150;
			}
			if(yy < 0){
				yy = _pageY+lineHeight;
			}
			$(span).show();
			$(span).css({height: "110px",width:'350px',left:xx+'px',top:yy+'px'});
			setRangyPostion();
		}
	};
	
	// EN: Return the constructor function
	return SelectionMenu;
	
})(window, document);