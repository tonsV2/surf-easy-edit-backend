webpackJsonp([1],{136:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(1),r=n(96),a=n.n(r),i=n(24);o.default.config.productionTip=!1,new o.default({el:"#app",router:i.a,template:"<App/>",components:{App:a.a}})},137:function(t,e,n){"use strict";var o=n(38),r=n.n(o),a=n(25),i=n.n(a),s=n(27);e.a={getBackendUrl:function(){return s.a.getUrl("api")+"/edit/"},loadEdit:function(t){var e=this;return new r.a(function(n,o){var r=e.getBackendUrl()+t;i.a.get(r).then(function(t){n(t.data)},function(t){e.handleError(t)})})},saveEdit:function(t,e){var n=this;return new r.a(function(o,r){var a=n.getBackendUrl()+t,s={url:a,method:"POST",data:e};i()(s).then(function(t){o(t.data.content)},function(t){n.handleError(t)})})},handleError:function(t){console.log("Handle Error"),console.log(t)}}},138:function(t,e,n){"use strict";var o=n(38),r=n.n(o),a=n(25),i=n.n(a),s=n(27);e.a={getBackendUrl:function(){return s.a.getUrl("api")+"/users"},getAll:function(){var t=this;return new r.a(function(e,n){var o=t.getBackendUrl();i.a.get(o).then(function(t){e(t.data)},function(e){t.handleError(e)})})},handleError:function(t){console.log("Handle Error"),console.log(t)}}},139:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(221),r=n.n(o);e.default={props:{value:{type:String},options:{type:Object,default:function(){return{}}},autofocus:Boolean},data:function(){return{focused:this.autofocus,editor:null}},mounted:function(){var t=this,e=this.$el;this.editor=new r.a(e,this.options),this.editor.on("text-change",function(){t.$emit("input",t.editor.root.innerHTML)})},watch:{value:function(){this.editor.hasFocus()||(this.editor.root.innerHTML=this.value)},focused:function(t){this.editor[t?"focus":"blur"]()}}}},140:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={}},141:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(26),r=n(67),a=n(230),i=n.n(a);e.default={methods:{submit:function(){r.a.save(this.title,this.content).then(function(t){console.log(t)})}},components:{Quill:i.a},mounted:function(){o.a.authRequired("/add")},data:function(){return{title:null,content:null,editorOptions:{theme:"snow",modules:{toolbar:[["bold","italic","underline","strike"],["blockquote","code-block"],[{header:1},{header:2}],[{list:"ordered"},{list:"bullet"}],[{script:"sub"},{script:"super"}],[{indent:"-1"},{indent:"+1"}],[{direction:"rtl"}],[{size:["small",!1,"large","huge"]}],[{header:[1,2,3,4,5,6,!1]}],[{color:[]},{background:[]}],[{font:[]}],[{align:[]}],["clean"]]}}}}}},142:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(1),r=n(95),a=n.n(r),i=n(92),s=(n.n(i),n(240)),u=n.n(s),l=n(137);o.default.use(a.a),o.default.use(u.a),e.default={methods:{submit:function(){var t=this;l.a.saveEdit(this.$route.query.id,this.content).then(function(e){t.content=e,t.$toasted.show("Updated!",{theme:"outline",position:"top-right",duration:2e3})})}},mounted:function(){var t=this;l.a.loadEdit(this.$route.query.id).then(function(e){t.content=e})},data:function(){return{content:null}}}},143:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(1),r=n(26),a=n(67),i=n(199),s=n.n(i),u=n(213);n.n(u);o.default.use(s.a),e.default={methods:{getAll:function(){var t=this;a.a.get().then(function(e){t.posts=e})},deletePost:function(t){this.post=t,this.showConfirmDialog=!0},deleteConfirmed:function(){var t=this;this.showConfirmDialog=!1,a.a.delete(this.post).then(function(e){t.getAll()})}},mounted:function(){r.a.authRequired("/list"),this.getAll()},data:function(){return{posts:null,post:null,authenticated:r.a.isAuthenticated(),showConfirmDialog:!1}}}},144:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(26),r=n(24);e.default={data:function(){return{credentials:{username:"",password:""},error:""}},methods:{submit:function(){o.a.login(this.credentials.username,this.credentials.password),r.a.go(-1)}}}},145:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(146),r=n.n(o),a=n(1),i=n(95),s=n.n(i),u=n(92),l=(n.n(u),n(27)),c=n(138);a.default.use(s.a),e.default={methods:{getAll:function(){var t=this;c.a.getAll().then(function(e){var n=l.a.getUrl("api"),o=l.a.getUrl("base"),a=!0,i=!1,s=void 0;try{for(var u,c=r()(e);!(a=(u=c.next()).done);a=!0){var d=u.value,f=[n+"/posts/latest?username="+d.username,n+"/posts/latest/content?username="+d.username,n+"/posts/latest/title?username="+d.username,n+"/feed?username="+d.username,n+"/feed/latest?username="+d.username,o+"/#/edit?id="+d.editId];t.users.push({username:d.username,urls:f})}}catch(t){i=!0,s=t}finally{try{!a&&c.return&&c.return()}finally{if(i)throw s}}})}},mounted:function(){this.getAll()},data:function(){return{users:[]}}}},213:function(t,e){},214:function(t,e){},215:function(t,e){},216:function(t,e){},217:function(t,e){},218:function(t,e){},224:function(t,e,n){t.exports=n.p+"static/img/logo.fab64d1.png"},225:function(t,e,n){n(217);var o=n(15)(n(141),n(236),null,null);t.exports=o.exports},226:function(t,e,n){n(215);var o=n(15)(n(142),n(233),"data-v-1966562c",null);t.exports=o.exports},227:function(t,e,n){var o=n(15)(n(143),n(232),null,null);t.exports=o.exports},228:function(t,e,n){var o=n(15)(n(144),n(234),null,null);t.exports=o.exports},229:function(t,e,n){n(218);var o=n(15)(n(145),n(237),"data-v-caf3f954",null);t.exports=o.exports},230:function(t,e,n){n(214);var o=n(15)(n(139),n(231),null,null);t.exports=o.exports},231:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement;return(t._self._c||e)("div",{staticClass:"quill-editor"})},staticRenderFns:[]}},232:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"list"},[n("h1",[t._v("List")]),t._v(" "),n("ul",t._l(t.posts,function(e){return n("li",[t._v("\n      "+t._s(e.title)+" - "+t._s(e.content)+" - "),n("button",{on:{click:function(n){return t.deletePost(e)}}},[t._v("Delete")])])}),0),t._v(" "),t.authenticated?t._e():n("router-link",{attrs:{to:"/login"}},[t._v("Login")]),t._v(" "),t.authenticated?n("router-link",{attrs:{to:"/logout"}},[t._v("Logout")]):t._e(),t._v(" "),n("el-dialog",{attrs:{title:"Delete post?",size:"tiny"},model:{value:t.showConfirmDialog,callback:function(e){t.showConfirmDialog=e},expression:"showConfirmDialog"}},[n("span",[t._v(t._s(t.post))]),t._v(" "),n("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(e){t.showConfirmDialog=!1}}},[t._v("Cancel")]),t._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.deleteConfirmed()}}},[t._v("Confirm")])],1)])],1)},staticRenderFns:[]}},233:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"editor"},[n("h1",[t._v("Digital signage content")]),t._v(" "),n("md-input-container",[n("md-textarea",{model:{value:t.content,callback:function(e){t.content=e},expression:"content"}}),t._v(" "),n("md-button",{staticClass:"md-raised md-primary",nativeOn:{click:function(e){return t.submit()}}},[t._v("Submit")])],1)],1)},staticRenderFns:[]}},234:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"col-sm-4 col-sm-offset-4"},[n("h2",[t._v("Log In")]),t._v(" "),n("p",[t._v("Log in to your account")]),t._v(" "),t.error?n("div",{staticClass:"alert alert-danger"},[n("p",[t._v(t._s(t.error))])]):t._e(),t._v(" "),n("div",{staticClass:"form-group"},[n("input",{directives:[{name:"model",rawName:"v-model",value:t.credentials.username,expression:"credentials.username"}],staticClass:"form-control",attrs:{type:"text",placeholder:"Enter your username"},domProps:{value:t.credentials.username},on:{input:function(e){e.target.composing||t.$set(t.credentials,"username",e.target.value)}}})]),t._v(" "),n("div",{staticClass:"form-group"},[n("input",{directives:[{name:"model",rawName:"v-model",value:t.credentials.password,expression:"credentials.password"}],staticClass:"form-control",attrs:{type:"password",placeholder:"Enter your password"},domProps:{value:t.credentials.password},on:{input:function(e){e.target.composing||t.$set(t.credentials,"password",e.target.value)}}})]),t._v(" "),n("button",{staticClass:"btn btn-primary",on:{click:function(e){return t.submit()}}},[t._v("Access")])])},staticRenderFns:[]}},235:function(t,e,n){t.exports={render:function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",{attrs:{id:"app"}},[o("img",{attrs:{src:n(224)}}),t._v(" "),o("router-view")],1)},staticRenderFns:[]}},236:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"editor"},[n("h1",[t._v("New post")]),t._v(" "),n("div",[n("input",{directives:[{name:"model",rawName:"v-model",value:t.title,expression:"title"}],staticClass:"title",attrs:{placeholder:"Title"},domProps:{value:t.title},on:{input:function(e){e.target.composing||(t.title=e.target.value)}}})]),t._v(" "),n("quill",{attrs:{options:t.editorOptions},model:{value:t.content,callback:function(e){t.content=e},expression:"content"}},[t._v("Content...")]),t._v(" "),n("button",{staticClass:"submit",on:{click:function(e){return t.submit()}}},[t._v("Submit")])],1)},staticRenderFns:[]}},237:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"users"},[n("h1",[t._v("Users")]),t._v(" "),n("md-list",t._l(t.users,function(e){return n("md-list-item",{key:e.username},[n("span",[t._v(t._s(e.username))]),t._v(" "),n("md-list-expand",[n("md-list",t._l(e.urls,function(e){return n("md-list-item",{key:e,staticClass:"md-inset"},[n("a",{attrs:{href:e}},[t._v(t._s(e))])])}),1)],1)],1)}),1)],1)},staticRenderFns:[]}},24:function(t,e,n){"use strict";var o=n(1),r=n(238),a=n(229),i=n.n(a),s=n(226),u=n.n(s),l=n(225),c=n.n(l),d=n(227),f=n.n(d),p=n(228),h=n.n(p);o.default.use(r.a);var v=[{path:"/users",name:"Users",component:i.a},{path:"/edit",name:"Edit",component:u.a},{path:"/add",name:"Add",component:c.a},{path:"/list",name:"List",component:f.a},{path:"/login",name:"Login",component:h.a},{path:"*",redirect:"/list"}];e.a=new r.a({routes:v})},26:function(t,e,n){"use strict";var o=n(148),r=n.n(o),a=n(25),i=n.n(a),s=n(24),u="easyedit.surfstation.dk"===location.hostname?"http://easyedit.surfstation.dk/oauth/token":"http://localhost:8080/oauth/token";e.a={storageKey:"access_token",user:{authenticated:!1},login:function(t,e,n){var o=this,a={client_id:"html5",client_secret:"password"},l={grant_type:"password",username:t,password:e},c={Authorization:"Basic "+btoa(a.client_id+":"+a.client_secret),"Content-Type":"application/x-www-form-urlencoded"},d=r()(l).map(function(t){return encodeURIComponent(t)+"="+encodeURIComponent(l[t])}).join("&"),f={url:u,method:"POST",headers:c,data:d};i()(f).then(function(t){console.log(t),localStorage.setItem(o.storageKey,t.data.access_token),o.user.authenticated=!0,n&&s.a.go(n)})},logout:function(){localStorage.removeItem(this.storageKey),this.user.authenticated=!1},isAuthenticated:function(){return!0===this.user.authenticated},authRequired:function(){this.isAuthenticated()||s.a.push("/login")},getAuthHeader:function(){return{Authorization:"Bearer "+localStorage.getItem(this.storageKey)}}}},27:function(t,e,n){"use strict";e.a={urls:{base:location.origin,api:location.hostname.endsWith("localhost")?"http://localhost:8080/api":location.origin+"/api"},getUrl:function(t){return this.urls[t]}}},67:function(t,e,n){"use strict";var o=n(38),r=n.n(o),a=n(25),i=n.n(a),s=n(26),u=n(24),l=n(27);e.a={getBackendUrl:function(){return l.a.getUrl("api")+"/posts"},get:function(){var t=this;return new r.a(function(e,n){var o=t.getBackendUrl();i.a.get(o,{headers:s.a.getAuthHeader()}).then(function(t){e(t.data)},function(e){t.handleError(e)})})},delete:function(t){var e=this;return new r.a(function(n,o){var r=e.getBackendUrl()+"/"+t.id;i.a.delete(r,{headers:s.a.getAuthHeader()}).then(function(t){n(200===t.status)},function(t){e.handleError(t)})})},save:function(t,e){var n=this;return new r.a(function(o,r){var a=n.getBackendUrl(),u={title:t,content:e},l={headers:s.a.getAuthHeader()};i.a.post(a,u,l).then(function(t){o(201===t.status)},function(t){n.handleError(t)})})},handleError:function(t){console.log("Handle Error"),console.log(t),401!==t.status&&403!==t.status||u.a.push("/login")}}},92:function(t,e){},96:function(t,e,n){n(216);var o=n(15)(n(140),n(235),null,null);t.exports=o.exports}},[136]);
//# sourceMappingURL=app.aaeca3d2f65079beb050.js.map