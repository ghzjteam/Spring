/**
 * Copyright (c) 2012 by Jamie Peabody, http://www.mergely.com
 * All rights reserved.
 * Version: 2.5 2012-07-31
 */
Mgly={},Object.size=function(e){var t=0,n;for(n in e)e.hasOwnProperty(n)&&t++;return t},Mgly.LCS=function(e,t){this.length=this._lcs(e,t);var n=[];e=e.split(""),t=t.split(""),e.unshift(""),t.unshift(""),this.C=n,this.x=e,this.y=t;var r=0,i=0;for(r=0;r<e.length+1;++r){n[r]=[];for(i=0;i<t.length+1;++i)n[r][i]=0}for(r=1;r<e.length+1;++r)for(i=1;i<t.length+1;++i)e[r-1]==t[i-1]?n[r][i]=n[r-1][i-1]+1:n[r][i]=Math.max(n[r][i-1],n[r-1][i]);this.ready=1},Mgly.LCS.prototype.clear=function(){this.ready=0},Mgly.LCS.prototype._diff=function(e,t,n,r){var i=this.x,s=this.y,o=this.C;this.ready&&e>0&&t>0&&i[e]==s[t]?this._diff(e-1,t-1,n,r):t>0&&(e==0||o[e][t-1]>=o[e-1][t])?(this._diff(e,t-1,n,r),n&&n(t-1,s[t])):e>0&&(t==0||o[e][t-1]<o[e-1][t])&&(this._diff(e-1,t,n,r),r&&r(e-1,i[e]))},Mgly.LCS.prototype.diff=function(e,t){this._diff(this.x.length-1,this.y.length-1,e,t)},Mgly.LCS.prototype._lcs=function(e,t){var n=0,r=Array(e.length);for(a=0;a<=e.length;a++){r[a]=Array(t.length);for(b=0;b<=t.length;b++)r[a][b]=0}for(var i=0;i<e.length;i++)for(var s=0;s<t.length;s++)e[i]==t[s]?(r[i][s]==0?r[i+1][s+1]=1:r[i+1][s+1]=r[i][s]+1,r[i+1][s+1]>n&&(n=r[i+1][s+1])):r[i+1][s+1]=0;return n},Mgly.diff=function(e,t,n){this.diff_codes={},this.max_code=0;var r=e.split("\n"),i=t.split("\n");e.length==0&&(r=[]),t.length==0&&(i=[]);var s=new Object;s.data=this._diff_codes(r),s.modified={},s.length=Object.size(s.data);var o=new Object;o.data=this._diff_codes(i),o.modified={},o.length=Object.size(o.data);var u=s.length+o.length+1,a=Array(2*u+2),f=Array(2*u+2);this._lcs(s,0,s.length,o,0,o.length,f,a),this._optimize(s),this._optimize(o),this.items=this._create_diffs(s,o),n&&(this.lhs_lines=r,this.rhs_lines=i)},Mgly.diff.prototype.changes=function(){return this.items},Mgly.diff.prototype.normal_form=function(){var e="";for(var t in this.items){var n=this.items[t],r="",i="",s="c";n.lhs_deleted_count==0&&n.rhs_inserted_count>0?s="a":n.lhs_deleted_count>0&&n.rhs_inserted_count==0&&(s="d"),n.lhs_deleted_count==1?r=n.lhs_start+1:n.lhs_deleted_count==0?r=n.lhs_start:r=n.lhs_start+1+","+(n.lhs_start+n.lhs_deleted_count),n.rhs_inserted_count==1?i=n.rhs_start+1:n.rhs_inserted_count==0?i=n.rhs_start:i=n.rhs_start+1+","+(n.rhs_start+n.rhs_inserted_count),e+=r+s+i+"\n";if(this.rhs_lines&&this.lhs_lines){for(var o=n.lhs_start;o<n.lhs_start+n.lhs_deleted_count;++o)e+="< "+this.lhs_lines[o]+"\n";n.rhs_inserted_count&&n.lhs_deleted_count&&(e+="---\n");for(var o=n.rhs_start;o<n.rhs_start+n.rhs_inserted_count;++o)e+="> "+this.rhs_lines[o]+"\n"}}return e},Mgly.diff.prototype._diff_codes=function(e){var t=this.max_code,n={};for(var r=0;r<e.length;++r){var i=e[r],s=this.diff_codes[i];s!=undefined?n[r]=s:(this.max_code++,this.diff_codes[i]=this.max_code,n[r]=this.max_code)}return n},Mgly.diff.prototype._lcs=function(e,t,n,r,i,s,o,u){while(t<n&&i<s&&e.data[t]==r.data[i])++t,++i;while(t<n&&i<s&&e.data[n-1]==r.data[s-1])--n,--s;if(t==n)while(i<s)r.modified[i++]=!0;else if(i==s)while(t<n)e.modified[t++]=!0;else{var a=this._sms(e,t,n,r,i,s,o,u);this._lcs(e,t,a.x,r,i,a.y,o,u),this._lcs(e,a.x,n,r,a.y,s,o,u)}},Mgly.diff.prototype._sms=function(e,t,n,r,i,s,o,u){var a=e.length+r.length+1,f=t-i,l=n-s,c=n-t-(s-i),h=(c&1)!=0,p=a-f,d=a-l,v=(n-t+s-i)/2+1;u[p+f+1]=t,o[d+l-1]=n;var m=new Object;for(var g=0;g<=v;++g){for(var y=f-g;y<=f+g;y+=2){var b,w;y==f-g?b=u[p+y+1]:(b=u[p+y-1]+1,y<f+g&&u[p+y+1]>=b&&(b=u[p+y+1])),w=b-y;while(b<n&&w<s&&e.data[b]==r.data[w])b++,w++;u[p+y]=b;if(h&&l-g<y&&y<l+g&&o[d+y]<=u[p+y])return m.x=u[p+y],m.y=u[p+y]-y,m}for(var y=l-g;y<=l+g;y+=2){var b,w;y==l+g?b=o[d+y-1]:(b=o[d+y+1]-1,y>l-g&&o[d+y-1]<b&&(b=o[d+y-1])),w=b-y;while(b>t&&w>i&&e.data[b-1]==r.data[w-1])b--,w--;o[d+y]=b;if(!h&&f-g<=y&&y<=f+g&&o[d+y]<=u[p+y])return m.x=u[p+y],m.y=u[p+y]-y,m}}throw"the algorithm should never come here."},Mgly.diff.prototype._optimize=function(e){var t=0,n=0;while(t<e.length){while(t<e.length&&(e.modified[t]==undefined||e.modified[t]==0))t++;n=t;while(n<e.length&&e.modified[n]==1)n++;n<e.length&&e.data[t]==e.data[n]?(e.modified[t]=!1,e.modified[n]=!0):t=n}},Mgly.diff.prototype._create_diffs=function(e,t){var n=[],r=0,i=0,s=0,o=0;while(s<e.length||o<t.length)if(s<e.length&&!e.modified[s]&&o<t.length&&!t.modified[o])s++,o++;else{r=s,i=o;while(s<e.length&&(o>=t.length||e.modified[s]))s++;while(o<t.length&&(s>=e.length||t.modified[o]))o++;if(r<s||i<o){var u=new Object;u.lhs_start=r,u.rhs_start=i,u.lhs_deleted_count=s-r,u.rhs_inserted_count=o-i,n.push(u)}}return n},Mgly.mergely=function(e,t){e&&this.init(e,t)},$.extend(Mgly.mergely.prototype,{name:"mergely",init:function(e,t){this.settings={autoresize:!0,fadein:"fast",editor_width:"400px",editor_height:"400px",resize_timeout:500,change_timeout:150,fgcolor:"#4ba3fa",bgcolor:"#eee",lhs:function(e){},rhs:function(e){},loaded:function(){},height:function(e){return e-20},width:function(e){return e},resize:function(){var t=$(e).parent().width(),n=$(window).height();this.width&&(t=this.width(t)),this.height&&(n=this.height(n));var r=t/2-16-8,i=n,s=$(e);s.find(".mergely-column, .CodeMirror-scroll").css({width:r+"px"}),s.find(".mergely-column, .mergely-canvas, .mergely-margin, .mergely-column textarea, .CodeMirror-scroll").css({height:i+"px"}),s.find(".mergely-canvas").css({height:i+"px"}),s.find(".mergely-column textarea").css({width:r+"px"}),s.css({width:t+"px",height:n+"px"}),s.css("display")=="none"&&(this.fadein!=0?s.fadeIn(this.fadein):s.show(),this.loaded&&this.loaded()),this.resized&&this.resized()},_debug:"calc",resized:function(){}},this.cmsettings={mode:"text/plain",readOnly:!1,lineWrapping:!1,lineNumbers:!0},this.element=$(e),t.cmsettings&&$.extend(this.cmsettings,t.cmsettings),t&&$.extend(this.settings,t),this.element.bind("destroyed",$.proxy(this.teardown,this)),$.data(e,this.name,this),this._setup(e)},bind:function(){var e=this;this.editor=[];var t=jQuery.extend({onChange:function(){e._changing(e.id+"-lhs",e.id+"-rhs")},onScroll:function(){e._scrolling(e.id+"-lhs")}},this.cmsettings);this.editor[this.id+"-lhs"]=CodeMirror.fromTextArea($("#"+this.id+"-lhs").get(0),t);var n=jQuery.extend({onChange:function(){e._changing(e.id+"-lhs",e.id+"-rhs")},onScroll:function(){e._scrolling(e.id+"-rhs")}},this.cmsettings);this.editor[this.id+"-rhs"]=CodeMirror.fromTextArea($("#"+this.id+"-rhs").get(0),n);var r=null;$(window).resize(function(){r&&clearTimeout(r),r=setTimeout(function(){e.em_height=null,e.settings.resize&&e.settings.resize(),e.editor[e.id+"-lhs"].refresh(),e.editor[e.id+"-rhs"].refresh(),e._changing(e.id+"-lhs",e.id+"-rhs")},e.settings.resize_timeout)})},unbind:function(){this.editor[this.id+"-lhs"].toTextArea(),this.editor[this.id+"-rhs"].toTextArea()},destroy:function(){this.element.unbind("destroyed",this.teardown),this.teardown()},teardown:function(){this.unbind()},lhs:function(e){this.editor[this.id+"-lhs"].setValue(e)},rhs:function(e){this.editor[this.id+"-rhs"].setValue(e)},swap:function(){var e=this.editor[this.id+"-lhs"],t=this.editor[this.id+"-rhs"],n=t.getValue();t.setValue(e.getValue()),e.setValue(n)},merge:function(e){var t=this.editor[this.id+"-lhs"],n=this.editor[this.id+"-rhs"];e=="lhs"?t.setValue(n.getValue()):n.setValue(t.getValue())},get:function(e){var t=this.editor[this.id+"-"+e],n=t.getValue();return n==undefined?"":n},clear:function(e){var t=this.editor[this.id+"-"+e];t.setValue("")},search:function(e,t){var n=this.editor[this.id+"-lhs"],r=this.editor[this.id+"-rhs"],i;e=="lhs"?i=n:i=r;if(i.getSelection().length==0||this.prev_query[e]!=t)this.cursor[this.id]=i.getSearchCursor(t,{line:0,ch:0},!1),this.prev_query[e]=t;this.cursor[this.id].findNext()?i.setSelection(this.cursor[this.id].from(),this.cursor[this.id].to()):this.cursor[this.id]=i.getSearchCursor(t,{line:0,ch:0},!1)},resize:function(){this.settings.resize(),this._changing(this.id+"-lhs",this.id+"-rhs")},diff:function(){var e=this.editor[this.id+"-lhs"].getValue(),t=this.editor[this.id+"-rhs"].getValue(),n=new Mgly.diff(e,t,retain_lines=!0);return n.normal_form()},_setup:function(e){$(this.element).hide(),this.id=$(e).attr("id");var t=this.settings.editor_height,n=this.settings.editor_width;this.changed_timeout=null,this.change_funcs=[],this.prev_query=[],this.cursor=[],this.change_exp=new RegExp(/(\d+(?:,\d+)?)([acd])(\d+(?:,\d+)?)/);var r,i;if($.button!=undefined)r='<button title="Merge left"></button>',i='<button title="Merge right"></button>';else{var s="width:1em;height:1em;background-color:#888;cursor:pointer;text-align:center;color:#eee;border:1px solid: #222;margin-right:5px;";r='<div style="'+s+'" title="Merge left">&lt;</div>',i='<div style="'+s+'" title="Merge right">&gt;</div>'}this.merge_right_button=$(i),this.merge_left_button=$(r),this.merge_right_button.corner&&this.merge_right_button.corner("3px"),this.merge_left_button.corner&&this.merge_left_button.corner("3px"),$(this.element).append($('<div class="mergely-margin" style="height: '+t+'"><canvas id="'+this.id+'-lhs-margin" width="8" height="'+t+'"></canvas></div>')),$(this.element).append($('<div style="width:'+n+"; height:"+t+'" id="'+this.id+'-editor-lhs" class="mergely-column"><textarea style="" id="'+this.id+'-lhs"></textarea></div>')),$(this.element).append($('<div class="mergely-canvas" style="height: '+t+'"><canvas id="'+this.id+"-lhs-"+this.id+'-rhs-canvas" style="width:28px" width="28px" height="'+t+'"></canvas></div>')),$(this.element).append($('<div style="width:'+n+"; height:"+t+'" id="'+this.id+'-editor-rhs" class="mergely-column"><textarea style="" id="'+this.id+'-rhs"></textarea></div>')),$(this.element).append($('<div class="mergely-margin" style="height: '+t+'"><canvas id="'+this.id+'-rhs-margin" width="8" height="'+t+'"></canvas></div>')),this.bind(),this.settings.lhs&&this.settings.lhs(this.editor[this.id+"-lhs"].setValue),this.settings.rhs&&this.settings.rhs(this.editor[this.id+"-rhs"].setValue),$(window).resize();var o="#"+this.id+" .CodeMirror-gutter-text { padding: 5px 0 0 0; }"+"#"+this.id+" .CodeMirror-lines pre, "+"#"+this.id+" .CodeMirror-gutter-text pre { line-height: 18px; }";this.settings.autoresize&&(o+=this.id+" .CodeMirror-scroll { height: 100%; overflow: auto; }"),$('<style type="text/css">'+o+"</style>").appendTo("head")},_scrolling:function(e){var t=$(this.editor[e].getScrollerElement());this.midway==undefined&&(this.midway=(t.height()/2+t.offset().top).toFixed(2));var n=this.editor[e].coordsChar({x:0,y:this.midway}),r=t.scrollTop(),i=t.scrollLeft();this.trace("scroll","midway",this.midway),this.trace("scroll","midline",n),this.trace("scroll","top_to",r),this.trace("scroll","left_to",i);for(var s in this.editor){if(e==s)continue;var o=e.replace(this.id+"-",""),u=s.replace(this.id+"-",""),a=0,f=null;for(var l in this.changes){var c=this.changes[l];n.line>=c[o+"-line-from"]&&(f=c,n.line>=f[o+"-line-to"]&&(a+=c[o+"-y-end"]-c[o+"-y-start"]-(c[u+"-y-end"]-c[u+"-y-start"])))}var h=!0;f&&(this.trace("scroll","last visible change",f),f[o+"-line-from"]<n.line&&f[o+"-line-to"]>n.line&&(h=!1));if(h){this.trace("scroll","scrolling other side",r-a);var t=$(this.editor[s].getScrollerElement());t.scrollTop(r-a).scrollLeft(i)}else this.trace("scroll","not scrolling other side");this._calculate_offsets(this.id+"-lhs",this.id+"-rhs",this.changes),this._draw_diff(this.id+"-lhs",this.id+"-rhs",this.changes),this.trace("scroll","scrolled")}},_changing:function(e,t){var n=this;this.changed_timeout!=null&&clearTimeout(this.changed_timeout),this.changed_timeout=setTimeout(function(){n._changed(e,t)},this.settings.change_timeout)},_changed:function(e,t){for(var n in this.editor){var r=this.editor[n];r.operation(function(){for(var e=0,t=r.lineCount();e<t;++e)r.clearMarker(e),r.setLineClass(e,null)})}for(var i in this.change_funcs){var s=this.change_funcs[i];s.clear!=undefined?s.clear():s()}this._diff(e,t)},_diff:function(e,t){var n=this.editor[e].getValue(),r=this.editor[t].getValue(),i=new Mgly.diff(n,r);this.changes=this._parse_diff(e,t,i.normal_form()),this._calculate_offsets(e,t,this.changes),this._markup_changes(e,t,this.changes),this._draw_diff(e,t,this.changes)},_parse_diff:function(e,t,n){this.trace("diff","diff results:\n",n);var r=[],i=0,s=n.split(/\n/);for(var o=0;o<s.length;++o){if(s[o].length==0)continue;var u={},a=this.change_exp.exec(s[o]);if(a==null)continue;var f=a[1].split(",");u["lhs-line-from"]=f[0]-1,f.length==1?u["lhs-line-to"]=f[0]-1:u["lhs-line-to"]=f[1]-1;var l=a[3].split(",");u["rhs-line-from"]=l[0]-1,l.length==1?u["rhs-line-to"]=l[0]-1:u["rhs-line-to"]=l[1]-1,u.op=a[2],r[i++]=u,this.trace("diff","change",u)}return r},_calculate_offsets:function(e,t,n){if(this.draw_top_offset==null){var r=this.element.find(".CodeMirror-gutter-text pre").first(),i=r.offset().top;if(!i)return;this.em_height=r.get(0).offsetHeight,this.draw_top_offset=6.5-i;if(!this.em_height){var s=$('<div class="CodeMirror-gutter-text"><pre>&nbsp;</pre></div>');this.em_height=parseInt(s.css("line-height"))-2,console.warn("Failed to calculate offsets, trying brute-force:",this.em_height)}this.em_height||(console.warn("Failed to calculate offsets, using 18 by default"),this.em_height=18),this.draw_lhs_min=.5;var o=$("#"+e+"-"+t+"-canvas");if(!o.width()){console.error("canvas width is 0");return}this.draw_rhs_max=$("#"+e+"-"+t+"-canvas").width()-.5,this.draw_lhs_width=5,this.draw_rhs_width=5,this.trace("calc","change offsets calculated",{top_offset:i,lhs_min:this.draw_lhs_min,rhs_max:this.draw_rhs_max,lhs_width:this.draw_lhs_width,rhs_width:this.draw_rhs_width})}for(var u in n){var a=n[u];a["lhs-y-start"]=this.draw_top_offset+this.editor[e].charCoords({line:a["lhs-line-from"],ch:0}).y,a["lhs-y-end"]=this.draw_top_offset+this.editor[e].charCoords({line:a["lhs-line-to"]+1,ch:0}).y,a["rhs-y-start"]=this.draw_top_offset+this.editor[t].charCoords({line:a["rhs-line-from"],ch:0}).y,a["rhs-y-end"]=this.draw_top_offset+this.editor[t].charCoords({line:a["rhs-line-to"]+1,ch:0}).y,a["op"]=="d"?a["rhs-y-start"]=a["rhs-y-end"]:a["op"]=="a"&&(a["lhs-y-start"]=a["lhs-y-end"])}},_markup_changes:function(e,t,n){$(".merge-button").remove();var r=this.editor[e];r.operation(function(){for(var e in n){var t=n[e],i="mergely-"+t.op+"-start",s="mergely-"+t.op+"-end",o=i+" "+s;if(t["lhs-line-from"]==t["lhs-line-to"])t["op"]=="c"?r.setLineClass(t["lhs-line-from"],o):t["op"]=="a"?r.setLineClass(t["lhs-line-from"],s+"-lhs"):t["op"]=="d"&&r.setLineClass(t["lhs-line-from"],o+" mergely-c-rem");else{r.setLineClass(t["lhs-line-from"],i+(t["op"]=="d"?" mergely-c-rem":"")),r.setLineClass(t["lhs-line-to"],s+(t["op"]=="d"?" mergely-c-rem":""));if(t["op"]=="d")for(var u=t["lhs-line-from"]+1;u<t["lhs-line-to"];++u)r.setLineClass(u,"mergely-c-rem mergely-d-mid")}}});var r=this.editor[t];r.operation(function(){for(var e in n){var t=n[e],i="mergely-"+t.op+"-start",s="mergely-"+t.op+"-end",o=i+" "+s;if(t["rhs-line-from"]==t["rhs-line-to"])t["op"]=="c"?r.setLineClass(t["rhs-line-from"],o):t["op"]=="a"?r.setLineClass(t["rhs-line-from"],o):t["op"]=="d"&&r.setLineClass(t["rhs-line-from"],s+"-rhs");else{r.setLineClass(t["rhs-line-from"],i),r.setLineClass(t["rhs-line-to"],s);if(t["op"]=="a")for(var u=t["rhs-line-from"]+1;u<t["rhs-line-to"];++u)r.setLineClass(u,"mergely-c-add mergely-a-mid")}}});for(var i in n){var s=n[i],o="mergely-"+s.op+"-start",u="mergely-"+s.op+"-end",a=o+" "+u;if(!this.cmsettings.readOnly){var f=this.merge_right_button.clone();f.button&&f.button({icons:{primary:"ui-icon-triangle-1-e"},text:!1}),f.addClass("merge-button"),f.attr("id","merge-right-"+i);var l=this;$(f).get(0).onclick=function(n){return function(){var r=l.editor[e].lineInfo(n["lhs-line-to"]),i=l.editor[t].lineInfo(n["rhs-line-to"]),s=l.editor[e].getRange({line:n["lhs-line-from"],ch:0},{line:n["lhs-line-to"],ch:r.text.length});if(n["op"]=="c")l.editor[t].replaceRange(s,{line:n["rhs-line-from"],ch:0},{line:n["rhs-line-to"],ch:i.text.length});else if(n["op"]=="a"){var o=parseInt(n["rhs-line-from"]),u=parseInt(n["rhs-line-to"]);for(var a=u;a>=o;--a)l.editor[t].removeLine(a)}else s+="\n",l.editor[t].replaceRange(s,{line:n["rhs-line-from"]+1,ch:0});return l.editor[e].setValue(l.editor[e].getValue()),l.editor[t].setValue(l.editor[t].getValue()),!1}}(s),this.trace("markup","lhs adding button",s["lhs-line-from"]),this.editor[e].addWidget({line:s["lhs-line-from"],ch:0},f.get(0),!1,"over","right"),f=this.merge_left_button.clone(),f.button&&f.button({icons:{primary:"ui-icon-triangle-1-w"},text:!1}),f.addClass("merge-button"),f.attr("id","merge-left-"+i);var l=this;$(f).get(0).onclick=function(n){return function(){var r=l.editor[e].lineInfo(n["lhs-line-to"]),i=l.editor[t].lineInfo(n["rhs-line-to"]),s=l.editor[t].getRange({line:n["rhs-line-from"],ch:0},{line:n["rhs-line-to"],ch:i.text.length});if(n["op"]=="c")l.editor[e].replaceRange(s,{line:n["lhs-line-from"],ch:0},{line:n["lhs-line-to"],ch:r.text.length});else if(n["op"]=="d"){var o=parseInt(n["lhs-line-from"]),u=parseInt(n["lhs-line-to"]);for(var a=u;a>=o;--a)l.editor[e].removeLine(a)}else s+="\n",l.editor[e].replaceRange(s,{line:n["lhs-line-from"]+1,ch:0});return l.editor[e].setValue(l.editor[e].getValue()),l.editor[t].setValue(l.editor[t].getValue()),!1}}(s),this.trace("markup","rhs adding button",s["rhs-line-from"]),this.editor[t].addWidget({line:s["rhs-line-from"],ch:0},f.get(0),!1,"over","right")}if(s["op"]=="a"){var c=s["rhs-line-from"],h=s["rhs-line-to"],p=this.editor[t].lineInfo(h);if(p){var d=this.editor[t].markText({line:c,ch:0},{line:h,ch:p.text.length},"mergely-c-add");this.change_funcs.push(d)}continue}if(s["op"]=="d"){var c=s["lhs-line-from"],h=s["lhs-line-to"],p=this.editor[e].lineInfo(h);if(p){var d=this.editor[e].markText({line:c,ch:0},{line:h,ch:p.text.length},"mergely-c-rem");this.change_funcs.push(d)}continue}var l=this;for(var v=s["lhs-line-from"],m=s["rhs-line-from"],i=0;v>=0&&v<=s["lhs-line-to"]||m>=0&&m<=s["rhs-line-to"];++v,++m){if(m+i>s["rhs-line-to"]){var g=this.editor[e].getLine(v),d=this.editor[e].markText({line:v,ch:0},{line:v,ch:g.length},"mergely-c-rem");this.change_funcs.push(d);continue}if(v+i>s["lhs-line-to"]){var y=this.editor[t].getLine(m),d=this.editor[e].markText({line:m,ch:0},{line:m,ch:g.length},"mergely-c-add");this.change_funcs.push(d);continue}var g=this.editor[e].getLine(v),y=this.editor[t].getLine(m),b={line:-1,ch:-1},w={line:-1,ch:-1},E={line:-1,ch:-1},S={line:-1,ch:-1},x=new Mgly.LCS(g,y),T=Math.max(g.length,y.length);T==0&&(T=1);var N=1*x.length/T*100;N<10&&x.clear(),x.diff(added=function(e,n){if(E.ch<0)E.line=m,E.ch=e,S.line=m,S.ch=e;else if(e==S.ch+1)S.ch=e;else{if(E.ch>=0&&S.ch>=E.ch){S.ch+=1;var r=l.editor[t].markText(E,S,"mergely-c-add");l.change_funcs.push(r)}E.ch=-1,S.ch=-1,n!="\n"&&this.added(e,n)}},removed=function(t,n){if(b.ch<0)b.line=v,b.ch=t,w.line=v,w.ch=t;else if(t==w.ch+1)w.ch=t;else{if(b.ch>=0&&w.ch>=b.ch){w.ch+=1;var r=l.editor[e].markText(b,w,"mergely-c-rem");l.change_funcs.push(r)}b.ch=-1,w.ch=-1,n!="\n"&&this.removed(t,n)}});if(E.ch>=0&&S.ch>=E.ch){S.ch+=1;var d=this.editor[t].markText(E,S,"mergely-c-add");this.change_funcs.push(d)}if(b.ch>=0&&w.ch>=b.ch){w.ch+=1;var d=this.editor[e].markText(b,w,"mergely-c-rem");this.change_funcs.push(d)}}}},_draw_diff:function(e,t,n){var r=$(this.editor[e].getScrollerElement()).height(),i=$(this.editor[e].getScrollerElement()).children(":first-child").height(),s=r/i,o=r/i,u=$(this.editor[e].getScrollerElement()),a=$(this.editor[t].getScrollerElement()),f=this.editor[e].lineCount(),l=this.editor[t].lineCount();this.trace("draw","visible_page_height",r),this.trace("draw","gutter_height",i),this.trace("draw","visible_page_ratio",s),this.trace("draw","lhs-scroller-top",u.scrollTop()),this.trace("draw","rhs-scroller-top",a.scrollTop());var c=document.getElementById(e+"-"+t+"-canvas");if(c==undefined)throw"Failed to find: "+e+"-"+t+"-canvas";$.each($("canvas"),function(){$(this).get(0).height=r});var h=$("#"+this.id+"-lhs-margin"),p=$("#"+this.id+"-rhs-margin");h.unbind("click"),p.unbind("click");var d=h.get(0),v=p.get(0),m=$(h).offset(),g=$(p).offset(),y=c.getContext("2d"),b=d.getContext("2d"),w=v.getContext("2d");b.beginPath(),b.fillStyle=this.settings.bgcolor,b.strokeStyle="#888",b.fillRect(0,0,6.5,r),b.strokeRect(0,0,6.5,r),w.beginPath(),w.fillStyle=this.settings.bgcolor,w.strokeStyle="#888",w.fillRect(0,0,6.5,r),w.strokeRect(0,0,6.5,r);for(var E in n){var S=n[E],x=S["lhs-y-start"],T=S["lhs-y-end"],N=S["rhs-y-start"],C=S["rhs-y-end"];y.beginPath(),y.strokeStyle=this.settings.fgcolor,y.lineWidth=1,y.moveTo(this.draw_lhs_min,x),y.lineTo(this.draw_lhs_min+this.draw_lhs_width,x),y.lineTo(this.draw_lhs_min+this.draw_lhs_width,T+1),y.lineTo(this.draw_lhs_min,T+1),y.stroke(),y.moveTo(this.draw_rhs_max,N),y.lineTo(this.draw_rhs_max-this.draw_rhs_width,N),y.lineTo(this.draw_rhs_max-this.draw_rhs_width,C+1),y.lineTo(this.draw_rhs_max,C+1),y.stroke(),y.moveTo(this.draw_lhs_min+this.draw_lhs_width,x+(T+1-x)/2),y.lineTo(this.draw_rhs_max-this.draw_rhs_width,N+(C+1-N)/2),y.stroke(),this.trace("draw",S),x=(S["lhs-y-start"]+u.scrollTop())*s,T=(S["lhs-y-end"]+u.scrollTop())*s+1,N=(S["rhs-y-start"]+a.scrollTop())*s,C=(S["rhs-y-end"]+a.scrollTop())*s+1,this.trace("draw","marker calculated",x,T,N,C),b.beginPath(),b.fillStyle=this.settings.fgcolor,b.strokeStyle="#000",b.lineWidth=.5,b.fillRect(1.5,x,4.5,Math.max(T-x,5)),b.strokeRect(1.5,x,4.5,Math.max(T-x,5)),w.beginPath(),w.fillStyle=this.settings.fgcolor,w.strokeStyle="#000",w.lineWidth=.5,w.fillRect(1.5,N,4.5,Math.max(C-N,5)),w.strokeRect(1.5,N,4.5,Math.max(C-N,5))}b.fillStyle="rgba(0, 0, 200, 0.5)",w.fillStyle="rgba(0, 0, 200, 0.5)";var k=h.height()*s,L=u.scrollTop()/i*h.height();this.trace("draw","cls.height",h.height()),this.trace("draw","lhs_scroller.scrollTop()",u.scrollTop()),this.trace("draw","gutter_height",i),this.trace("draw","visible_page_ratio",s),this.trace("draw","from",L,"to",k),b.fillRect(1.5,L,4.5,k),w.fillRect(1.5,L,4.5,k),h.click(function(e){var t=e.pageY-m.top-k/2,n=Math.max(0,t/d.height*u.get(0).scrollHeight);u.scrollTop(n)}),p.click(function(e){var t=e.pageY-g.top-k/2,n=Math.max(0,t/v.height*a.get(0).scrollHeight);a.scrollTop(n)})},trace:function(e){this.settings._debug.indexOf(e)>=0&&(arguments[0]=e+":",console.log(this,[].slice.apply(arguments)))}}),$.pluginMaker=function(e){$.fn[e.prototype.name]=function(t){var n=$.makeArray(arguments),r=n.slice(1),i=undefined;this.each(function(){var s=$.data(this,e.prototype.name);if(s){if(typeof t=="string")i=s[t].apply(s,r);else if(s.update)return alert("here"),s.update.apply(s,n)}else new e(this,t)});if(i!=undefined)return i}},$.pluginMaker(Mgly.mergely)