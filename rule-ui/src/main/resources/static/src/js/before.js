/**
 * add by tanrq 2018/1/20
 */
var tokenCookieName = "token", refreshTokenCookieName = "refreshToken";
/**
 * 新建cookie。
 * times为空字符串时,cookie的生存期至浏览器会话结束。hours为数字0时,建立的是一个失效的cookie,这个cookie会覆盖已经建立过的同名、同path的cookie（如果这个cookie存在）
 * @param name
 * @param value
 * @param times
 * @param path
 */
var setCookie = function (name, value, times, path) {
    var name = escape(name);
    var value = escape(value);
    var expires = new Date();
    expires.setMinutes(expires.getMinutes() + times);
    path = path == "" ? "" : ";path=" + path;
    var _expires = (typeof times) == "string" ? "" : ";expires=" + expires.toUTCString();
    document.cookie = name + "=" + value + _expires + path;
}
/**
 * 获取cookie
 * @param name
 * @returns {*}
 */
var getCookieValue = function (name) {
    var name = escape(name);
    //读cookie属性，这将返回文档的所有cookie
    var allcookies = document.cookie;
    //查找名为name的cookie的开始位置
    name += "=";
    var pos = allcookies.indexOf(name);
    //如果找到了具有该名字的cookie，那么提取并使用它的值
    //如果pos值为-1则说明搜索"version="失败
    if (pos != -1) {
        //cookie值开始的位置
        var start = pos + name.length;
        //从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
        var end = allcookies.indexOf(";", start);
        //如果end值为-1说明cookie列表里只有一个cookie
        if (end == -1) end = allcookies.length;
        //提取cookie的值
        var value = allcookies.substring(start, end);
        //对它解码
        return unescape(value);
    }
    else return "";
}
/**
 * 删除cookie
 * @param name
 * @param path
 */
var deleteCookie = function (name, path) {
    var name = escape(name);
    var expires = new Date(0);
    path = path == "" ? "" : ";path=" + path;
    document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;
}
var setToken = function (token) {
    setCookie(tokenCookieName, token, 28, "/");
}
var setRefreshToken = function (refreshToken) {
    setCookie(refreshTokenCookieName, refreshToken, 30, "/");
}
var getToken = function () {
    return getCookieValue(tokenCookieName);
}
var getRefreshToken = function () {
    return getCookieValue(refreshTokenCookieName);
}
var deleteToken = function () {
   deleteCookie(tokenCookieName, "/");
}
var deleteRefreshToken = function () {
    deleteCookie(refreshTokenCookieName, "/");
}

//////////////
/**
 * 重写ajax请求
 * by 谭荣巧 2018-02-06
 */

    var loginUrl = "/login.html", app =__beforeConfig.appName, refreshTokenUrl = __beforeConfig.baseUrl+"/uaa/auth/token", isLocal = false;
    /**
     * 验证token是否有效，如果无效则重新刷新token，如果无法拉取则重新登录
     * @returns {boolean}
     */
    var validationAndRefreshToken = function (noToken) {
        var token = getToken();

        var refreshToken = getRefreshToken();
        // 如果refreshToken失效，则直接跳转到登录页面
        if (refreshToken == null || refreshToken == "") {
            layer.confirm('登录超时，请重新登录。', function (index) {
                top.location.href = loginUrl;
                layer.close(index);
            });
            return false;
        } else {
            //延长refreshToken时间
            setRefreshToken(refreshToken);
        }
        // 验证token是否失效，失效则重新请求后台更新token，如果无法更新，则重新登录
        if ((token == null || token == "") && (noToken == false || !noToken)) {
            //console.debug("token无效，重新加载token");
            $.ajax({
                type: 'GET',
                async: false,
                url: refreshTokenUrl,
                headers: {
                    Authorization: "Bearer " + refreshToken
                },
                noToken: true,
                success: function (data) {
                    if (data == null || data["token"] == null || data["token"] == "") {
                        layer.confirm('登录超时，请重新登录。', function (index) {
                            top.location.href = loginUrl;
                            layer.close(index);
                        });
                        return false;
                    } else {
                        setToken(data["token"]);
                        return true;
                    }
                },
                error: function () {
                    layer.confirm('登录超时，请重新登录。', function (index) {
                        top.location.href = loginUrl;
                        layer.close(index);
                    });
                    return false;
                }
            });
        }
        return true;
    }

    // 备份jquery的ajax方法
    var _ajax = $.ajax;

    // 重写jquery的ajax方法
    $.ajax = function (opt) {
        if (!isLocal) {
            if(Cobject.isChecktoken!=false)
            {
                //所有ajax请求前先验证token是否存在
                var isValidation = validationAndRefreshToken(opt.noToken);
                if (!isValidation) {
                    return false;
                }
            }
        }

        // 备份opt中error和success方法
        var fn = {
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            },
            success: function (data, textStatus) {
            }
        }
        if (opt.error) {
            fn.error = opt.error;
        }
        if (opt.success) {
            fn.success = opt.success;
        }

        // 扩展增强处理
        $.extend(opt, {
                cache:false,//禁止缓存
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    //TODO 需判断是否token失效，和API权限是否验证通过
                    fn.error(XMLHttpRequest, textStatus, errorThrown);
                },
                success: function (data, textStatus) {
                    if (!isLocal && data != null) {
                        switch (data["status_code"]) {
                            case "9921"://TOKEN过期
                            case "9922"://验签失败
                            case "9924"://授权失败
                            case "9925"://TOKEN不能为空
                                layer.confirm(data['result_msg'] + '，请重新登录。', function (index) {
                                    top.location.href = loginUrl;
                                    layer.close(index);
                                });
                                return false;
                            case "9926":
                                layer.alert("对不起，您没有该功能的操作权限，如有疑问，请联系管理员。");
                                return false;
                        }
                    }
                    fn.success(data, textStatus);
                },
                complete: function (XHR, TS) {
                    // 请求完成后回调函数 (请求成功或失败之后均调用)。
                }
            }
        );
        if (!isLocal) {
            var headers = {
                app: __beforeConfig.appName /*系统编码统一通过http headers进行传输*/
                ,instanceId: __beforeConfig.instanceId
            }
            //noToken 默认为false，当为true时，则不传输token
            if (opt.noToken == false || !opt.noToken) {
                headers.Authorization = "Bearer " + getToken();
            }
            headers = $.extend({}, opt.headers, headers);
            $.extend(opt, {
                headers: $.extend({}, opt.headers, headers)
            });
        }
        return _ajax(opt);

    };



//////////////
$(document).keydown(function (event) {
    // 监听backspace键
    if (event.keyCode == 8 &&
        ['body', 'document', 'window', 'a'].indexOf(event.target.tagName.toLocaleLowerCase()) >= 0 &&
        (event.target.tagName === 'INPUT' && event.target.readOnly === true)
    ) {
        return false;
    }
});

////////////


//[3950]lhf 20170519 add
this.JSON = {};
if (window.Prototype) {
    delete Object.prototype.toJSON;
    delete Array.prototype.toJSON;
    delete Hash.prototype.toJSON;
    delete String.prototype.toJSON;
    delete Date.prototype.toJSON;

}
(function () {
    function f(n) {
        return n < 10 ? '0' + n : n;
    }

    Date.prototype.toJSON = function (key) {
        var rtd = isFinite(this.valueOf()) ? this.getFullYear() + '-' + f(this.getMonth() + 1) + '-' + f(this.getDate()) + ' ' + f(this.getHours()) + ':' + f(this.getMinutes()) + ':' + f(this.getSeconds()) + '' : null;
        return rtd;
    };
    String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function (key) {
        return this.valueOf();
    };

    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
	escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
	gap, indent, meta = {
	    '\b': '\\b',
	    '\t': '\\t',
	    '\n': '\\n',
	    '\f': '\\f',
	    '\r': '\\r',
	    '"': '\\"',
	    '\\': '\\\\'
	},
	rep;
    function quote(string) {
        escapable.lastIndex = 0;
        return escapable.test(string) ? '"' + string.replace(escapable,
		function (a) {
		    var c = meta[a];
		    return typeof c === 'string' ? c : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
		}) + '"' : '"' + string + '"';
    }
    function str(key, holder) {
        var i, k, v, length, mind = gap,
		partial, value = holder[key];
        if (value && typeof value === 'object' && typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }
        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }
        switch (typeof value) {
            case 'string':
                return quote(value);
            case 'number':
                return isFinite(value) ? String(value) : 'null';
            case 'boolean':
            case 'null':
                return String(value);
            case 'object':
                if (!value) {
                    return 'null';
                }
                gap += indent;
                partial = [];
                if (Object.prototype.toString.apply(value) === '[object Array]') {
                    length = value.length;
                    for (i = 0; i < length; i += 1) {
                        partial[i] = str(i, value) || 'null';
                    }
                    v = partial.length === 0 ? '[]' : gap ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']' : '[' + partial.join(',') + ']';
                    gap = mind;
                    return v;
                }
                if (rep && typeof rep === 'object') {
                    length = rep.length;
                    for (i = 0; i < length; i += 1) {
                        k = rep[i];
                        if (typeof k === 'string') {
                            v = str(k, value);
                            if (v) {
                                partial.push(quote(k) + (gap ? ': ' : ':') + v);
                            }
                        }
                    }
                } else {
                    for (k in value) {
                        if (Object.hasOwnProperty.call(value, k)) {
                            v = str(k, value);
                            if (v) {
                                partial.push(quote(k) + (gap ? ': ' : ':') + v);
                            }
                        }
                    }
                }
                v = partial.length === 0 ? '{}' : gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}' : '{' + partial.join(',') + '}';
                gap = mind;
                return v;
        }
    };
    JSON.stringify = function (value, replacer, space) {
        var i;
        gap = '';
        indent = '';
        if (typeof space === 'number') {
            for (i = 0; i < space; i += 1) {
                indent += ' ';
            }
        } else if (typeof space === 'string') {
            indent = space;
        }
        rep = replacer;
        if (replacer && typeof replacer !== 'function' && (typeof replacer !== 'object' || typeof replacer.length !== 'number')) {
            throw new Error('JSON.stringify');
        }
        return str('', {
            '': value
        });
    };

    JSON.parse = function (text, reviver) {
        var j;
        function walk(holder, key) {
            var k, v, value = holder[key];
            if (value && typeof value === 'object') {
                for (k in value) {
                    if (Object.hasOwnProperty.call(value, k)) {
                        v = walk(value, k);
                        if (v !== undefined) {
                            value[k] = v;
                        } else {
                            delete value[k];
                        }
                    }
                }
            }
            return reviver.call(holder, key, value);
        }
        text = String(text);
        cx.lastIndex = 0;
        if (cx.test(text)) {
            text = text.replace(cx,
            function (a) {
                return '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            });
        }
        if (/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
            j = eval('(' + text + ')');
            return typeof reviver === 'function' ? walk({
                '': j
            },
            '') : j;
        }
        throw new SyntaxError('JSON.parse');
    };
    if (typeof JSON.ReapirJsonNetObj !== 'function') {
        JSON.ReapirJsonNetObj = function (o) {
            var self = this;
            self.identifiers = [];
            self.refs = [];
            self.rez = function (value) {
                var i, item, name, path;
                if (value && typeof value === 'object') {
                    if (Object.prototype.toString.apply(value) === '[object Array]') {
                        for (i = 0; i < value.length; i += 1) {
                            item = value[i];
                            if (item && typeof item === 'object') {
                                path = item.$ref;
                                if (typeof path === 'string' && path != null) {
                                    value[i] = self.identifiers[parseInt(path)];
                                } else {
                                    self.identifiers[parseInt(item.$id)] = item;
                                    self.rez(item);
                                }
                            }
                        }
                    } else {
                        for (name in value) {
                            if (typeof value[name] === 'object') {
                                item = value[name];
                                if (item) {
                                    path = item.$ref;
                                    if (typeof path === 'string' && path != null) {
                                        value[name] = self.identifiers[parseInt(path)];
                                    } else {
                                        self.identifiers[parseInt(item.$id)] = item;
                                        self.rez(item);
                                    }
                                }
                            }
                        }
                    }
                }
            };
            self.removeIdent = function (value) {
                var i, item, name, path;
                if (value && typeof value === 'object') {
                    if ($.isArray(value)) {
                        for (i = 0; i < value.length; i += 1) {
                            item = value[i];
                            if (item && typeof item === 'object') {
                                if (item._isremoveIdent == undefined) {
                                    self.removeIdent(item);
                                }

                            }
                        }
                    } else {
                        if (value["$id"]) {
                            value._isremoveIdent = true;
                            delete value["$id"];
                        }
                        for (name in value) {
                            item = value[name];
                            if (typeof item === 'object') {
                                if ($.isArray(item)) {
                                    self.removeIdent(item);
                                } else {
                                    if (item && item._isremoveIdent == undefined) {
                                        self.removeIdent(item);
                                    }
                                }

                            }

                        }
                    }
                }
            };
            //step1 还原循环引用对象
            self.rez(o);
            //		    //step2 清除$id属性
            // 		    self.removeIdent(o);
            self.identifiers = [];
        };
    }
    if (typeof JSON.decycle !== 'function') {
        JSON.decycle = function decycle(object) {
            'use strict';
            var objects = [],
			paths = [];
            return (function derez(value, path) {
                var i, name, nu;
                if (typeof value === 'object' && value !== null && !(value instanceof Boolean) && !(value instanceof Date) && !(value instanceof Number) && !(value instanceof RegExp) && !(value instanceof String)) {
                    for (i = 0; i < objects.length; i += 1) {
                        if (objects[i] === value) {
                            return {
                                $ref: paths[i]
                            };
                        }
                    }
                    objects.push(value);
                    paths.push(path);
                    if (Object.prototype.toString.apply(value) === '[object Array]') {
                        nu = [];
                        for (i = 0; i < value.length; i += 1) {
                            nu[i] = derez(value[i], path + '[' + i + ']');
                        }
                    } else {
                        nu = {};
                        for (name in value) {
                            if (Object.prototype.hasOwnProperty.call(value, name)) {
                                nu[name] = derez(value[name], path + '[' + JSON.stringify(name) + ']');
                            }
                        }
                    }
                    return nu;
                }
                return value;
            }(object, '$'));
        };
    }
    if (typeof JSON.retrocycle !== 'function') {
        JSON.retrocycle = function retrocycle($) {
            'use strict';
            var px = /^\$(?:\[(?:\d+|\"(?:[^\\\"\u0000-\u001f]|\\([\\\"\/bfnrt]|u[0-9a-zA-Z]{4}))*\")\])*$/;
            (function rez(value) {
                var i, item, name, path;
                if (value && typeof value === 'object') {
                    if (Object.prototype.toString.apply(value) === '[object Array]') {
                        for (i = 0; i < value.length; i += 1) {
                            item = value[i];
                            if (item && typeof item === 'object') {
                                path = item.$ref;
                                if (typeof path === 'string' && px.test(path)) {
                                    value[i] = eval(path);
                                } else {
                                    rez(item);
                                }
                            }
                        }
                    } else {
                        for (name in value) {
                            if (typeof value[name] === 'object') {
                                item = value[name];
                                if (item) {
                                    path = item.$ref;
                                    if (typeof path === 'string' && px.test(path)) {
                                        value[name] = eval(path);
                                    } else {
                                        rez(item);
                                    }
                                }
                            }
                        }
                    }
                }
            }($));
            return $;
        };
    }
    if (typeof JSON.DeepCopy !== 'function') {
        JSON.DeepCopy = function (obj) {
            var jsonString = JSON.stringify(JSON.decycle(obj));
            var newDeepCopyObj = JSON.retrocycle(JSON.parse(jsonString));
            return newDeepCopyObj;
        };
    }
}());

function Class() {
    var j = arguments[arguments.length - 1];
    if (!j) {
        return;
    }
    var g = arguments.length > 1 ? arguments[0] : object;
    function k() { }
    k.prototype = g.prototype;
    var l = new k();
    for (var i in j) {
        if (i != "Create") {
            l[i] = j[i];
        }
    }
    if (j.toString != Object.prototype.toString) {
        l.toString = j.toString;
    }
    if (j.toLocaleString != Object.prototype.toLocaleString) {
        l.toLocaleString = j.toLocaleString;
    }
    if (j.valueOf != Object.prototype.valueOf) {
        l.valueOf = j.valueOf;
    }
    if (j.Create) {
        var h = j.Create;
    } else {
        h = function () {
            this.base.apply(this, arguments);
        };
    }
    h.prototype = l;
    h.Base = g;
    h.prototype.Type = h;
    return h;
}
function object() { }
object.prototype.isA = function (d) {
    var c = this.Type;
    while (c) {
        if (c == d) {
            return true;
        }
        c = c.Base;
    }
    return false;
};
object.prototype.base = function () {
    var c = this.Type.Base;
    if (!c.Base) {
        c.apply(this, arguments);
    } else {
        this.base = d(c);
        c.apply(this, arguments);
        delete this.base;
    }
    function d(b) {
        var a = b.Base;
        if (!a.Base) {
            return a;
        }
        return function () {
            this.base = d(a);
            a.apply(this, arguments);
        };
    }
};
if (typeof this.JSON.decycle !== "function") {
    this.JSON.decycle = function decycle(a) {
        var b = [],
		d = [];
        return (function c(h, j) {
            var g, f, e;
            if (typeof h === "object" && h !== null && !(h instanceof Boolean) && !(h instanceof Date) && !(h instanceof Number) && !(h instanceof RegExp) && !(h instanceof String)) {
                for (g = 0; g < b.length; g += 1) {
                    if (b[g] === h) {
                        return {
                            $ref: d[g]
                        };
                    }
                }
                b.push(h);
                d.push(j);
                if (Object.prototype.toString.apply(h) === "[object Array]") {
                    e = [];
                    for (g = 0; g < h.length; g += 1) {
                        e[g] = c(h[g], j + "[" + g + "]");
                    }
                } else {
                    e = {};
                    for (f in h) {
                        if (Object.prototype.hasOwnProperty.call(h, f)) {
                            e[f] = c(h[f], j + "[" + this.JSON.stringify(f) + "]");
                        }
                    }
                }
                return e;
            }
            return h;
        }(a, "$"));
    };
}
if (typeof this.JSON.retrocycle !== "function") {
    this.JSON.retrocycle = function retrocycle($) {
        var px = /^\$(?:\[(?:\d+|\"(?:[^\\\"\u0000-\u001f]|\\([\\\"\/bfnrt]|u[0-9a-zA-Z]{4}))*\")\])*$/;
        (function rez(value) {
            var i, item, name, path;
            if (value && typeof value === "object") {
                if (Object.prototype.toString.apply(value) === "[object Array]") {
                    for (i = 0; i < value.length; i += 1) {
                        item = value[i];
                        if (item && typeof item === "object") {
                            path = item.$ref;
                            if (typeof path === "string" && px.test(path)) {
                                value[i] = eval(path);
                            } else {
                                rez(item);
                            }
                        }
                    }
                } else {
                    for (name in value) {
                        if (typeof value[name] === "object") {
                            item = value[name];
                            if (item) {
                                path = item.$ref;
                                if (typeof path === "string" && px.test(path)) {
                                    value[name] = eval(path);
                                } else {
                                    rez(item);
                                }
                            }
                        }
                    }
                }
            }
        }($));
        return $;
    };
};
var IObject = Class({
    url: '/WebService.asmx/ExecuteControlFunction',
    jsopUrl: 'http://localhost:6768/BillService.asmx/ExecuteJsonPFunction',
    Page: '',
    callBackEventDic: {},//回调事件字典，用于处理自定义函数的回调
    Create: function () {
        this.base();
    },
    CallPageMenthod: function (data, success, options) {
        if (this.Page == '') {
            alert("当前Page未设置Controller类");
        } else {
            var ndata = new Array();
            ndata.push(this.Page);
            for (var d in data) {
                if (typeof (data[d]) === "function") continue;
                var decycleItem = JSON.decycle(data[d]);
                ndata.push(decycleItem);
            }
            this.CallController(ndata, success, options);
        }
    },
    //请求数据
    CallController: function (data, success, options) {
        var p = options || {};
        var tempData = JSON.decycle(data);
        var json = JSON.stringify({
            paramJson: JSON.stringify(tempData)
        });
        $.ajax({
            type: "Post",
            url: this.url,
            dataType: "json",
            cache: p.cache ? p.cache : false,
            async: p.async ? p.async : true,
            contentType: "application/json; charset=utf-8",
            data: json,
            beforeSend: function () {
                this.loading = true;
                if (p.beforeSend) p.beforeSend();

            },
            complete: function () {
                this.loading = false;
                if (p.complete) p.complete();

            },
            success: function (result, status, jqXHR) {
                if (!result) return;
                if (!result.IsError) {
                    if (success) {
                        Cobject.receiveData(success, result.d, p);
                    }
                } else {
                    if (p.error) p.error(result.Message);
                }
            },
            error: function (err) {
                Cobject.ShowLoading(false);
                var errorMessage = "";
                try {
                    var errorObj = JSON.parse(err.responseText);
                    errorMessage = errorObj.Message;
                } catch (e) {
                    errorMessage = err.responseText;
                }
                if (p.error != undefined) {
                    p.error(errorMessage);
                } else {
                    if (errorMessage != "") {
                        alert(errorMessage);
                    }

                }
            }
        });
    },
    //请求数据
    CallJsonp: function (data, options) {
        var p = options || {};
        var tempData = JSON.decycle(data);
        var json = { paramJson: JSON.stringify(tempData) };
        $.ajax({
            async: false,
            url: options.url,
            type: 'GET',
            dataType: 'jsonp',
            data: json,
            timeout: 5000,
            beforeSend: function () {
                this.loading = true;
                if (p.beforeSend) p.beforeSend();
            },
            complete: function () {
                this.loading = false;
                if (p.complete) p.complete();
            },
            success: function (result, status, jqXHR) {
                if (!result) return;
                if (result.ErrorMessage && result.ErrorMessage.length > 0) {
                    Cobject.ShowLoading(false);
                    if (p.error) {
                        p.error(result.ErrorMessage);
                    }
                    else {
                        alert(result.ErrorMessage);
                    }

                } else {
                    if (p.success) {
                        result.Result = JSON.parse(result.Result);
                        JSON.ReapirJsonNetObj(result.Result);
                        Cobject.receiveRealResult(p.success, result, p);
                    }
                }
            },
            error: function (err) {
                Cobject.ShowLoading(false);
                var errorMessage = "";
                try {
                    var errorObj = JSON.parse(err.responseText);
                    errorMessage = errorObj.Message;
                } catch (e) {
                    errorMessage = err.responseText;
                }
                if (p.error != undefined) {
                    p.error(errorMessage);
                } else {
                    if (errorMessage != "") {
                        alert(errorMessage);
                    }

                }
            }
        });
    },
    receiveData: function (success, resultJson, p) {
        var resultObj = JSON.parse(resultJson);
        JSON.ReapirJsonNetObj(resultObj);
        Cobject.receiveRealResult(success, resultObj, p);
    },
    receiveRealResult: function (success, resultObj, p) {
        //真正的结果
        var realResult = resultObj.Result;
        if (resultObj.ExceptionMessage && resultObj.ExceptionMessage.length > 0) {
            if (p && p.error) {
                p.error(resultObj.ExceptionMessage);
            } else {
                Cobject.ShowLoading(false);
                alert(resultObj.ExceptionMessage);
                var e = new Error();
                e.message = resultObj.ExceptionMessage;
                throw e;
            }

        }
        if (resultObj.IsCloseSystem == true) {
            window.location.href = "/stopWeb.html";
        }

        //如果允许调用改方法，则触发成功事件
        if (resultObj.PermissionInfo) {
            if (resultObj.PermissionInfo.AllowCallMethod) {
                try {
                    success(realResult);
                } catch (e) {
                    alert(e);
                }

            } else {
                //如果还没登陆，则退回登录页
                if (resultObj.PermissionInfo.IsLogin == false) {
                    alert("超时或者未登录！");
                    var currentWindow = window;
                    if (currentWindow.top) {
                        currentWindow = currentWindow.top;
                    }
                    var url = "";
                    if (resultObj.SourceDomain == "web") {
                        if (CurrentPlatFormCode == "CaiXing") {
                            url = "/cxweb/Login.html";
                        } else {
                            url = "/Web/Login.html";
                        }
                    }
                    if (resultObj.SourceDomain == "admin") {
                        if (CurrentPlatFormCode == "CaiXing") {
                            url = "/cxadmin/Login.html";
                        } else {
                            url = "/admin/login.html";
                        }
                    }
                    if (url) {
                        currentWindow.location.href = url;
                    }
                }
                if (resultObj.PermissionInfo.IsLogin == true && resultObj.PermissionInfo.AllowCallMethod == false) {
                    alert("没有权限！");
                    Cobject.ShowLoading(false);
                }
            }
        }
    },
    getGuid: function () {
        var S4 = function () {
            return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
        };
        return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
    },
    ReapirJsonNetObj: function (o) { //修复Json循环引用实体
        var self = this;
        self.identifiers = [],
		self.refs = [],
		self.rez = function (a) {
		    var b, c, d, e;
		    if (a && "object" == typeof a) if ("[object Array]" === Object.prototype.toString.apply(a)) for (b = 0; b < a.length; b += 1) c = a[b],
			c && "object" == typeof c && (e = c.$ref, "string" == typeof e && null != e ? a[b] = self.identifiers[parseInt(e)] : (self.identifiers[parseInt(c.$id)] = c, self.rez(c)));
		    else for (d in a) "object" == typeof a[d] && (c = a[d], c && (e = c.$ref, "string" == typeof e && null != e ? a[d] = self.identifiers[parseInt(e)] : (self.identifiers[parseInt(c.$id)] = c, self.rez(c))));
		},
		self.rez(o),
		self.identifiers = [];
    }
});

//工具类
var tools = Class({});

//克隆对象的方法
tools.Clone = function (obj) {
    var b;
    if (obj.constructor == Object) {
        b = new obj.constructor();
    } else {
        b = new obj.constructor(obj.valueOf());
    }
    for (var a in obj) {
        if (b[a] != obj[a]) {
            if (typeof (obj[a]) == "object") {
                b[a] = tools.Clone(obj[a]);
            } else {
                b[a] = obj[a];
            }
        }
    }
    b.toString = obj.toString;
    b.valueOf = obj.valueOf;
    return b;
};

tools.DateFormat = function (date, fmt) { //author: meizz 
    var o = {
        "M+": date.getMonth() + 1,
        //月份 
        "d+": date.getDate(),
        //日 
        "h+": date.getHours(),
        //小时 
        "m+": date.getMinutes(),
        //分 
        "s+": date.getSeconds(),
        //秒 
        "q+": Math.floor((date.getMonth() + 3) / 3),
        //季度 
        "S": date.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};


//cookie
(function ($, document, undefined) {
    var pluses = /\+/g;
    function raw(s) {
        return s;
    }
    function decoded(s) {
        return decodeURIComponent(s.replace(pluses, ' '));
    }
    var config = tools.cookie = $.cookie = function (key, value, options) {
        if (value !== undefined) {
            options = $.extend({},
			config.defaults, options);
            if (value === null) {
                options.expires = -1;
            }
            if (typeof options.expires === 'number') {
                var days = options.expires,
				t = options.expires = new Date();
                t.setDate(t.getDate() + days);
            }
            value = config.json ? JSON.stringify(value) : String(value);
            return (document.cookie = [encodeURIComponent(key), '=', config.raw ? value : encodeURIComponent(value), options.expires ? '; expires=' + options.expires.toUTCString() : '', options.path ? '; path=' + options.path : '', options.domain ? '; domain=' + options.domain : '', options.secure ? '; secure' : ''].join(''));
        }
        var decode = config.raw ? raw : decoded;
        var cookies = document.cookie.split('; ');
        for (var i = 0, l = cookies.length; i < l; i++) {
            var parts = cookies[i].split('=');
            if (decode(parts.shift()) === key) {
                var cookie = decode(parts.join('='));
                return config.json ? JSON.parse(cookie) : cookie;
            }
        }
        return null;
    };
    config.defaults = {};
    $.removeCookie = function (key, options) {
        if ($.cookie(key) !== null) {
            $.cookie(key, null, options);
            return true;
        }
        return false;
    };
})(jQuery, document);
//cookiejson
(function ($) {
    var isObject = function (x) {
        return (typeof x === 'object') && !(x instanceof Array) && (x !== null);
    };
    $.extend({
        getJSONCookie: function (cookieName) {
            var cookieData = $.cookie(cookieName);
            return cookieData ? JSON.parse(cookieData) : {};
        },
        setJSONCookie: function (cookieName, data, options) {
            var cookieData = '';
            options = $.extend({
                expires: 90,
                path: '/'
            },
			options);
            if (!isObject(data)) {
                throw new Error('JSONCookie data must be an object');
            }
            cookieData = JSON.stringify(data);
            return $.cookie(cookieName, cookieData, options);
        },
        removeJSONCookie: function (cookieName) {
            return $.cookie(cookieName, null);
        },
        JSONCookie: function (cookieName, data, options) {
            if (data) {
                $.setJSONCookie(cookieName, data, options);
            }
            return $.getJSONCookie(cookieName);
        }
    });
})(jQuery);

//获取url参数
(function ($) {
    var qy = tools.GetQueryString = $.GetQueryString = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return "";
    };
    var qy2 = tools.GetUrlQueryString = $.GetUrlQueryString = function (url, name) {
        var result = null;
        if (url) {
            var myIndex = url.indexOf("?");
            if (myIndex != -1) {
                var search = url.substr(myIndex, url.length - myIndex);
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = search.substr(1).match(reg);
                if (r != null) { result = unescape(r[2]); }
            }
        }
        return result;
    };
})(jQuery);


//定义一个用于前端的类
var Cobject = {};
var before = Class({
    ControllerUrl: "",
    pageSize: 20,
    Cobject: null,
    Title: "",
    PageRequest: {},
    CurrentUser: {},
    callBackEventDic: {}, //回调事件字典，用于处理自定义函数的回调
    LoadingMessage: undefined, //loading层信息
    PartLoadingMessage: undefined, //partloading层信息
    ProductDetail: null,
    LoadNeedData: null,
    menuCode:"",//该页面对应的页面权限编码
    app:__beforeConfig.appName,
    WebUrl:__beforeConfig.webUrl,//Web html站点的转发前缀
    ServiceUrl:__beforeConfig.serviceUrl,//服务的转发前缀
    baseUrl:__beforeConfig.baseUrl,
    gatemodel:__beforeConfig.gatemodel, //路由模式 local 本地模式(读取本地的数据) common统一路由(读取统一网关)
    //重算路由模式
    refreshGatemodel:function () {
        var tb = this;
        var currentFullUrl = window.location.href;
        //根据url路径判断是调用本地服务还是统一网关的服务
        if(currentFullUrl)
        {
            currentFullUrl=currentFullUrl.toLowerCase();
            if(currentFullUrl.indexOf("172.16.200.110")>-1)
            {
                //统一网关调用
                tb.gatemodel="common";
            }
            if(currentFullUrl.indexOf("gatemodel=common")>-1)
            {
                //统一网关调用
                tb.gatemodel="common";
            }
            if(currentFullUrl.indexOf("gatemodel=local")>-1)
            {
                //本地调用
                tb.gatemodel="local";
            }
            if(__beforeConfig.active!='local')
            {
                this.gatemodel="common";
            }
        }
        return tb.gatemodel;
    },
    //返回完整的Web前缀绝对路径
    GetWebUrl:function (extendUrl) {
        var tb = this;
        var currentFullUrl = window.location.href;
        var hostname= window.location.hostname;
        //重算路由模式
        tb.refreshGatemodel();
        var fullUrl="";
        //统一路由模式的，调用的html路径要加入前缀
        if(tb.gatemodel=="common")
        {
            if(tb.WebUrl!="")
            {
                fullUrl="/"+tb.WebUrl;
            }
        }
        //本地模式需要判断是否走本地路由方式调用
        if(tb.gatemodel=="local")
        {
          var currentFullUrl=currentFullUrl.toLowerCase();
          //本地路由模式
          if(currentFullUrl.indexOf(":30902")>-1)
          {
              fullUrl="/ui";
          }
        }
        if(extendUrl!=null&&extendUrl!="")
        {
            fullUrl=fullUrl+"/"+extendUrl;
        }
        return fullUrl;
    },
    //返回完整的Service前缀绝对路径
    GetServiceUrl:function (extendUrl,controllerUrl) {
        var tb = this;
        var currentFullUrl = window.location.href;
        var hostname= window.location.hostname;
        //重算路由模式
        tb.refreshGatemodel();
        var fullUrl="";
        var myControllerUrl=tb.ControllerUrl;
        if(controllerUrl&&controllerUrl.length>0)
        {
            myControllerUrl=controllerUrl;
        }
        if(tb.gatemodel=="common")
        {
            fullUrl=tb.baseUrl+"/"+tb.ServiceUrl+"/"+myControllerUrl;
        }
        //本地连接，不通过统一路由
        if(tb.gatemodel=="local")
        {
            //通过本地local调用
            if(currentFullUrl.indexOf(":30902")>-1)
            {
                fullUrl="/service/"+myControllerUrl;
            }
            //通过内网IP调用
            else if(currentFullUrl.indexOf("10.110.1")>-1&&currentFullUrl.indexOf(":30901")>-1)
            {
                fullUrl="http://"+hostname+":30906/"+myControllerUrl;
            }
            //通过本地路由调用
            else
            {
                fullUrl="http://localhost:30906/"+myControllerUrl;
            }

        }
        if(extendUrl!=null&&extendUrl!="")
        {
            fullUrl=fullUrl+"/"+extendUrl;
        }

        return fullUrl;
    },
    //属性结束
    //公共方法开始
    Create: function () {
        this.base();
        var tb = this;
        tb.Base = tb.Type.prototype;
        Cobject = tb;
    },
    getGuid: function () {
        var S4 = function () {
            return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
        };
        return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
    },
    //获取控件当前的值
    GetControlSelectValue: function (control) {
        var value = null;
        if (control) {
            var isCheck = false;
            if (control.type) {
                //是ligerui控件
                switch (control.type) {
                    case '$.ligerui.controls.Grid':
                        value = control.getSelected();
                        break;
                    case 'TextBox':
                        value = control.getValue();
                        break;
                    case 'ComboBox':
                        value = control.getValue();
                        break;
                    case 'CheckBox':
                        value = control.getValue();
                        break;
                    case 'Radio':
                        value = control.getValue();
                        break;
                    case 'MyjButtonEdit':
                        value = control.getSelectItem();
                        break;
                    case 'DateEditor':
                        value = $(control.element).val();
                        break;
                    case 'Spinner':
                        value = control.getValue();
                        break;
                }
            } else {
                //普通html控件
                if (typeof (control.attr) == "function") {
                    if (control.length > 0) {
                        var type = control.attr("type");
                        var nodeName = control[0].nodeName;
                        if (type == undefined) {
                            type = control[0].type;
                        }
                        switch (type) {
                            case "radio":
                                if (control.is(":checked")) {
                                    value = true;
                                } else {
                                    value = false;
                                }
                                break;
                            case "select":
                                //清空数据源
                                value = control.val();
                                if (value == "undefined") {
                                    value = null;
                                }
                                break;
                            case "select-one":
                                //清空数据源
                                value = control.val();
                                if (value == "undefined") {
                                    value = null;
                                }
                                break;
                            case "number":
                            case "int":
                            case "float":
                            case 'text':
                                value = control.val();
                                break;
                            case "checkbox":
                                if (control.is(":checked")) {
                                    value = true;
                                } else {
                                    value = false;
                                }
                                break;

                        }
                        if (nodeName == "TEXTAREA") {
                            value = control.val();
                        }

                    }

                }
            }
        }
        return value;
    },
    //设置控件选择的值
    SetControlSelectValue: function (control, data, isPropertyChange) {
        if (control) {
            var isCheck = false;
            if (control.type) {
                //是ligerui控件
                switch (control.type) {
                    case '$.ligerui.controls.Grid':
                        control.select(data);
                        break;
                    case 'TextBox':
                        control.setValue(data, isPropertyChange);
                        break;
                    case 'ComboBox':
                        control.setValue(data, isPropertyChange);
                        break;
                    case 'CheckBox':
                        isCheck = false;
                        if (data == true) {
                            isCheck = true;
                        }
                        if (data == 1) {
                            isCheck = true;
                        }
                        control.setValue(isCheck, isPropertyChange);
                        break;
                    case 'Radio':
                        isCheck = false;
                        if (data == true) {
                            isCheck = true;
                        }
                        if (data == 1) {
                            isCheck = true;
                        }
                        control.setValue(isCheck, isPropertyChange);
                        break;
                    case 'MyjButtonEdit':
                        control.setSelectItem(data, isPropertyChange);
                        break;
                    case 'DateEditor':
                        control.setValue(data, isPropertyChange);
                        break;
                    case 'Spinner':
                        control.setValue(data, isPropertyChange);
                        break;
                }
            } else {
                //普通html控件
                if (typeof (control.attr) == "function") {
                    if (control.length > 0) {
                        var type = control.attr("type");
                        var nodeName = control[0].nodeName;
                        if (type == undefined) {
                            type = control[0].type;
                        }
                        switch (type) {
                            case "radio":
                                isCheck = false;
                                if (data == true) {
                                    isCheck = true;
                                }
                                if (data == 1) {
                                    isCheck = true;
                                }
                                if (isCheck == true) {
                                    control.attr("checked", "true");
                                } else {
                                    control.removeAttr("checked");
                                }
                                break;
                            case "select":
                                //清空数据源
                                control.val(data);
                                break;
                            case "select-one":
                                //清空数据源
                                control.val(data);
                                break;
                            case "number":
                            case "int":
                            case "float":
                            case 'text':
                                control.val(data);
                                break;
                            case "checkbox":
                                isCheck = false;
                                if (data == true) {
                                    isCheck = true;
                                }
                                if (data == 1) {
                                    isCheck = true;
                                }
                                if (isCheck == true) {
                                    control.attr("checked", "true");
                                } else {
                                    control.removeAttr("checked");
                                }
                                break;

                        }
                        if (nodeName == "TEXTAREA") {
                            control.val(data);
                        }

                    }

                }
            }
        }
    },
    //设置控件的数据源
    SetControlData: function (control, data, isPropertyChange,defaultText) {
        if (control) {
            var isCheck = false;
            if (control.type) {
                //是ligerui控件
                switch (control.type) {
                    case '$.ligerui.controls.Grid':
                        control.loadData({ Rows: data, Total: data.length });
                        break;
                    case 'TextBox':
                        control.setValue(data, isPropertyChange);
                        break;
                    case 'ComboBox':
                        control.setData(data, isPropertyChange);
                        break;
                    case 'CheckBox':
                        isCheck = false;
                        if (data == true) {
                            isCheck = true;
                        }
                        if (data == 1) {
                            isCheck = true;
                        }
                        control.setValue(isCheck, isPropertyChange);
                        break;
                    case 'Radio':
                        isCheck = false;
                        if (data == true) {
                            isCheck = true;
                        }
                        if (data == 1) {
                            isCheck = true;
                        }
                        control.setValue(isCheck, isPropertyChange);
                        break;
                    case 'MyjButtonEdit':
                        control.setSelectItem(data, isPropertyChange);
                        break;
                    case 'DateEditor':
                        control.setValue(data, isPropertyChange);
                        break;
                    case 'Spinner':
                        control.setValue(data, isPropertyChange);
                        break;
                }
            } else {
                //普通html控件
                if (typeof (control.attr) == "function") {
                    if (control.length > 0) {
                        var type = control.attr("type");
                        var nodeName = control[0].nodeName;
                        if (type == undefined) {
                            type = control[0].type;
                        }
                        switch (type) {
                            case "radio":
                                isCheck = false;
                                if (data == true) {
                                    isCheck = true;
                                }
                                if (data == 1) {
                                    isCheck = true;
                                }
                                if (isCheck == true) {
                                    control.attr("checked", "true");
                                } else {
                                    control.removeAttr("checked");
                                }

                                break;
                            case "select":
                                //清空数据源
                                control.empty();
                                //
                                var valuePropertyName = control.attr("valueProperty");
                                var namePropertyName = control.attr("namePropertyName");
                                if (valuePropertyName && namePropertyName) {
                                    //默认加载默认选项
                                    if(defaultText)
                                    {
                                        var optionItem = $("<option value=''>" + defaultText + "</option>");
                                        control.append(optionItem);
                                    }
                                    $(data).each(function (ind, item) {
                                        var optionItem = $("<option value='" + item[valuePropertyName] + "'>" + item[namePropertyName] + "</option>");
                                        optionItem.data("Item", item);
                                        control.append(optionItem);
                                    });
                                }
                                control.data("Data", data);
                                break;
                            case "select-one":
                                //清空数据源
                                control.empty();
                                var valuePropertyName = control.attr("valueProperty");
                                var namePropertyName = control.attr("namePropertyName");
                                if (valuePropertyName && namePropertyName) {
                                    //默认加载默认选项
                                    if(defaultText)
                                    {
                                        var optionItem = $("<option value=''>" + defaultText + "</option>");
                                        control.append(optionItem);
                                    }
                                    $(data).each(function (ind, item) {
                                        var optionItem = $("<option value='" + item[valuePropertyName] + "'>" + item[namePropertyName] + "</option>");
                                        optionItem.data("Item", item);
                                        control.append(optionItem);
                                    });
                                }
                                control.data("Data", data);
                                break;
                            case "number":
                            case "int":
                            case "float":
                            case 'text':
                                control.val(data);
                                break;
                            case "checkbox":
                                isCheck = false;
                                if (data == true) {
                                    isCheck = true;
                                }
                                if (data == 1) {
                                    isCheck = true;
                                }
                                if (isCheck == true) {
                                    control.attr("checked", "true");
                                } else {
                                    control.removeAttr("checked");
                                }
                                break;

                        }
                        if (nodeName == "TEXTAREA") {
                            control.val(data);
                        }

                    }

                }
            }
        }
    },
    //组别控件的子项目是否全选
    IsGroupControlAllSelect: function (controlgroupName) {
        var isAllSelect = false;
        if (controlgroupName) {
            var groupControlItemList = $("input[name='" + controlgroupName + "']");
            //总条数
            var totalCount = groupControlItemList.length;
            //选中的条数
            var selectCount = 0;
            $(groupControlItemList)
                .each(function (ind, item) {
                    if (Cobject.GetControlSelectValue($(item)) == true) {
                        selectCount++;
                    }
                });
            if (totalCount == selectCount) {
                isAllSelect = true;
            }
        }
        return isAllSelect;
    },
    getGroupControlSelectValue: function (controlgroupName) {
        var selectValueList = [];
        var groupControlItemList = $("input[name='" + controlgroupName + "']");
        if (groupControlItemList) {
            $(groupControlItemList).each(function (ind, item) {
                if (Cobject.GetControlSelectValue($(item)) == true) {
                    var controlVal = $(item).attr("value");
                    selectValueList.push(controlVal);
                }
            });
        }

        return selectValueList;
    },
    getFirstGroupControlSelectValue: function (controlgroupName) {
        var selectValue = null;
        var selectValueList = Cobject.getGroupControlSelectValue(controlgroupName);
        if (selectValueList && selectValueList.length > 0) {
            selectValue = selectValueList[0];
        }
        return selectValue;
    },
    //设置radio或者checkbox这种组别选择控件的选中值
    SetGroupControlSelectValue: function (controlgroupName, selectValueList) {
        if (controlgroupName) {
            if (selectValueList == null) {
                selectValueList = [];
            }

            //选择数据源是数组的，则循环生成字典，否则直接把值作为字典
            var selectValueDic = {};
            if ($.isArray(selectValueList) == true) {
                if (selectValueList && selectValueList.length > 0) {
                    $(selectValueList)
                        .each(function (ind, item) {
                            selectValueDic[item] = "1";
                        });
                }
            } else {
                selectValueDic[selectValueList] = "1";
            }


            var groupControlItemList = $("input[name='" + controlgroupName + "']");
            if (groupControlItemList) {
                $(groupControlItemList).each(function (ind, item) {
                    var controlVal = $(item).attr("value");
                    //匹配到，则选中
                    if (selectValueDic[controlVal] != undefined) {
                        Cobject.SetControlData($(item), true);
                    } else {
                        Cobject.SetControlData($(item), false);
                    }
                });
            }
        }
    },
    SetControlDisable: function (control, disable, autoDisable) {
        var isDisable = true;
        if (disable == false) {
            isDisable = false;
        }
        if (control) {
            if (control.type) {
                //是ligerui控件
                switch (control.type) {
                    case '$.ligerui.controls.Grid':
                        var columns = control.getColumns();
                        $(columns).each(function (ind2, columnItem) {
                            if (columnItem.editor) {
                                if (isDisable == true) {
                                    columnItem.IsForbiddenByStatus = isDisable;
                                } else {
                                    columnItem.IsForbiddenByStatus = undefined;
                                }
                            }
                        });
                        break;
                    case 'TextBox':
                        if (control.options.disabled === undefined || control.options.disabled === null) {
                            control._setDisabled(isDisable);
                        }
                        break;
                    case 'ComboBox':
                        if (control.options.disabled === undefined || control.options.disabled === null) {
                            control.setDisabled(disable);
                        }
                        break;
                    case 'CheckBox':
                        control.setDisabled(isDisable);
                        break;
                        //                    case 'Radio':                                                                                                              
                        //                        control.setDisabled(isDisable);                                                                                                              
                        //                        break;                                                                                                              
                    case 'MyjButtonEdit':
                        control.setDisable(isDisable);
                        break;
                    case 'DateEditor':
                        if (isDisable) {
                            control.setDisabled();
                        } else {
                            control.setEnabled();
                        }
                        break;
                    case 'Spinner':
                        if (control.options.defaultdisable === undefined || control.options.defaultdisable === null) {
                            control.setDisabled(isDisable);
                        }
                        break;
                    case 'uploadifyFlash':
                        if (control.id) {
                            if (isDisable == true) {
                                $('#' + control.id).hide();
                            } else {
                                $('#' + control.id).show();
                            }
                            //                            $('#' + control.id).uploadify('disable', true);
                            //                            control.setButtonDisabled();
                            //                            $('#' + control.id).uploadify('disable', isDisable);
                        }
                        break;
                }
            } else {
                //普通html控件
                if (typeof (control.attr) == "function") {
                    if (control.length > 0) {
                        var type = control.attr("type");
                        var nodeName = control[0].nodeName;
                        if (type == undefined) {
                            type = control[0].type;
                        }
                        switch (type) {
                            case "radio":
                            case "select":
                            case "select-one":
                            case "number":
                            case "int":
                            case "checkbox":
                            case "float":
                            case 'text':
                                if (control.attr("defaultdisabled") == undefined) {
                                    control.attr("disabled", isDisable);
                                } else {
                                    control.attr("disabled", true);
                                }

                                if (type == "select-one") {
                                    if (isDisable == true) {
                                        control.css("background-color", "rgb(235, 235, 228)");
                                    } else {
                                        var noneMessage = "none";
                                        control.css("background-color", noneMessage);
                                    }

                                }
                                break;
                            case 'button':
                                //按钮类型,如果是自动关闭并且按钮设置了CanDisable="false"，则不处理关闭按钮
                                var canDisable = control.attr("CanDisable");
                                if (autoDisable == true && canDisable == "false") {
                                    control.removeAttr("disabled");
                                } else {
                                    if (isDisable == true) {
                                        control.attr('disabled', "true");
                                    } else {
                                        control.removeAttr("disabled");
                                    }
                                }

                                break;
                        }
                        if (nodeName == "TEXTAREA") {
                            if (control.attr("defaultdisabled") == undefined) {
                                control.attr("disabled", isDisable);
                            } else {
                                control.attr("disabled", true);
                            }
                        }

                    }

                }
            }
        }
    },
    dataFormat: function (str) {
        var ar_date = "";
        if (str && str != "") {
            var dFormat = function (i) {
                return i < 10 ? "0" + i.toString() : i;
            }
            var d = eval('new ' + str.substr(1, str.length - 2));
            ar_date = [d.getFullYear(), d.getMonth() + 1, d.getDate()];
            for (var i = 0; i < ar_date.length; i++) ar_date[i] = dFormat(ar_date[i]);
            ar_date = ar_date.join('-');
        }

        return ar_date;
    },
    alert: function (message) {
        layer.alert(message);
    },
    postJson:function (url,requestInfo,success,err) {
        $.ajax({
            type:'POST',
            url: url,
            async: true,
            data: JSON.stringify(requestInfo),
            // data: requestInfo,
            dataType: 'json',
            contentType: "application/json",
            success:function (da) {
                var isSuccess=true;
                if(da&&da.code !=null&&da.code!=""&&da.code!="1")
                {
                    isSuccess=false;
                }
                if(isSuccess==true)
                {
                    if(success)
                    {
                        success(da);
                    }
                }
                else {
                    if($.isFunction(err))
                    {
                        err(da);
                    }
                    else {
                        layer.msg(da.msg, {icon: 2,time:1200});
                    }

                    //layer.msg(data.msg);
                }
            },
            error:function (xhr, txtStatus, err) {
                layer.alert('请求异常:' + txtStatus + '<br/>' + err, {
                    icon: 2,
                    title: '错误提示'
                });
            }
        });
    },
    ShowLoading: function (isshow, message) {
        var loading = $("#main_loading");
        var mainmask = $("#main_mask");
        if (loading.length == 0) {
            loading = $("<div class='l-loading' id='main_loading' style='background-color: #000;' />");
            Cobject.LoadingMessage = $("<div  style='margin-left: 24px;margin-top: 3px;'/>");
            loading.append(Cobject.LoadingMessage);
            mainmask = $("<div class='mainmask' id='main_mask'>");
            $("body").append(loading);
            $("body").append(mainmask);
            if (message) {
                Cobject.LoadingMessage.html(message);
            }
        }
        if (isshow == null || isshow) {
            Cobject.ShowLoadingIndex = layer.msg('正在处理中，请稍后....', {
                icon: 1,
                time: 9999000 //2秒关闭（如果不配置，默认是3秒）
            });
            //Cobject.ShowLoadingIndex = layer.open({
            //    type: 1,
            //    closeBtn: false,
            //    shift: 2,
            //    shadeClose: true,
            //    title: "正在处理中，请稍后....",
            //    content: loading
            //});
            //loading.show();
            //mainmask.show();
        } else {
            layer.close(Cobject.ShowLoadingIndex);
            loading.hide();
            mainmask.hide();
        }
    },
    //检查是否是身份证号码格式
    IsIdentityCard: function (identityCard) {
        var isPass = false;
        var regex = /(^\d{15}$)|(^\d{17}(\d|x|X)$)/;
        var regex15 = /(^\d{15}$)/;
        var regex18 = /(^\d{17}(\d|x|X)$)/;
        if (regex15.test(identityCard)) {
            //验证通过，执行处理
            isPass = true;
        } else if (regex18.test(identityCard)) {
            //验证通过，执行处理
            isPass = true;
        } else {
            isPass = false;
        }
        return isPass;
    },
    //检查输入的字符串是否是手机格式
    IsPhone: function (phone) {
        var isPass = true;
        if (phone.length != 11) {
            isPass = false;
        }
        if (!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(phone))) {
            isPass = false;
        }
        return isPass;
    },
    //检查输入的字符串是否是日期格式
    IsDate: function (RQ) {
        var date = RQ;
        var result = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);

        if (result == null)
            return false;
        var d = new Date(result[1], result[3] - 1, result[4]);
        return (d.getFullYear() == result[1] && (d.getMonth() + 1) == result[3] && d.getDate() == result[4]);
    },
    IsEmail: function(email) {
        var regEmail = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
        return regEmail.test(email);
    },
    GetShortString: function (message, count) {
        var resultMessage = "";
        if (message != null && count > 0) {
            if (message.length <= count) {
                resultMessage = message;
            } else {
                resultMessage = message.substr(0, count) + "....";
            }
        }
        return resultMessage;
    },
    SetLoadingMessage: function (message, e) {
        var type = typeof (message);
        var responseMessage = "";
        if (type == "string") {
            responseMessage = message;

        }
        if (type == "object") {
            if (e && e.MasterKey && e.MasterKey.length > 0) {
                responseMessage = message[e.MasterKey];
            }
            if (responseMessage == "") {
                responseMessage = JSON.stringify(message);
            }
        }
        Cobject.LoadingMessage.html(responseMessage);
    },
    //返回身份证的生日日期
    GetBirthdayFromIdentity: function (psidno) {
        var birthday = "";
        if (psidno) {
            var birthdayno, birthdaytemp;
            if (psidno.length == 18) {
                birthdayno = psidno.substring(6, 14);
            } else if (psidno.length == 15) {
                birthdaytemp = psidno.substring(6, 12);
                birthdayno = "19" + birthdaytemp;
            } else {
                //alert("错误的身份证号码，请核对！")
                return "";
            }
            birthday = birthdayno.substring(0, 4) + "-" + birthdayno.substring(4, 6) + "-" + birthdayno.substring(6, 8)
        }
        return birthday;
    },
    //返回身份证的性别
    GetSexFromIdentity: function (psidno) {
        var sexno, sex;
        if (psidno.length == 18) {
            sexno = psidno.substring(16, 17);
        } else if (psidno.length == 15) {
            sexno = psidno.substring(14, 15);
        } else {
            //alert("错误的身份证号码，请核对！");
            return "";
        }
        var tempid = sexno % 2;
        if (tempid == 0) {
            sex = 'F';
        } else {
            sex = 'M';
        }
        return sex;
    },
    //对MVC生成表格的输入框重新排序
    refreshMvcTable: function(table) {
        var trList = $(table).find("tr");
        if (trList && trList.length > 0) {
            $(trList).each(function (ind, trItem) {
                var inputList = $(trItem).find("input");
                $(inputList).each(function (controlInd, controlItem) {
                    var name = $(controlItem).attr("name");
                    var id = $(controlItem).attr("id");
                    var type = $(controlItem).attr("type");
                    var value = $(controlItem).attr("value");
                    if (name && id) {
                        var leftIndex = name.indexOf("[");
                        var rightIndex = name.indexOf("]");
                        if (leftIndex > -1 && rightIndex > -1) {
                         
                            var originalIndex = name.substring(leftIndex + 1, rightIndex);
                            var leftStr = name.substring(0, leftIndex);
                            var rightStr = name.substring(rightIndex + 2, name.length);
                            var myID = leftStr + "_" + originalIndex + "__" + rightStr;
                            if (type == "radio") {
                                myID = leftStr + "[" + originalIndex + "]_" + rightStr + "_" + value;
                            }
                            //name和ID是MVC自动生成的
                            if (id == myID) {
                                var newID = leftStr + "_" + ind + "__" + rightStr;
                                if (type == "radio") {
                                    newID = leftStr + "[" + ind + "]_" + rightStr + "_" + value;
                                }
                                var newName = leftStr + "[" + ind + "]." + rightStr;
                                if (type == "radio") {
                                    newName = leftStr + "[" + ind + "]_" + rightStr;
                                }
                                $(controlItem).attr("name", newName);
                                $(controlItem).attr("id", newID);
                                $(controlItem).attr("MvcRowIndex", ind);
                            }
                        }
                    }
                });
            });
        }
    },
    //返回数据检查的结果
    GetCheckTagResult: function () {
        //返回数据检查的结果
        var checkResult = [];
        var checktagList = $('*[checktag]');
        //检查非集合类
        $(checktagList).each(function (index, controlObj) {
            if (controlObj) {
                var checktag = $(controlObj).attr("checktag");
                var tagname = $(controlObj).attr("tagname");
                if (checktag && checktag.length > 0 && tagname && tagname.length > 0) {
                    var control = $(controlObj).data("DataControl");
                    var controlType = typeof (control);
                    if (control == undefined || controlType == "string") {
                        control = $(controlObj);
                    }
                    var value = Cobject.GetControlSelectValue(control);
                    var checktagList = checktag.split(" ");
                    $(checktagList).each(function (ind, checktagitem) {
                         var checkResultItem = {};
                         checkResultItem.control = control;
                         checkResultItem.checktag = checktag;
                         checkResultItem.value = value;
                        //根据检查的标签类型，返回对应的结果
                        //非空检查
                         if (checktagitem == "notnull") {
                            if (value == null || value == "") {
                                checkResultItem.message = tagname + "不能为空!";
                                checkResult.push(checkResultItem);
                            }
                         }
                        //特殊字符非空检查
                         if (checktagitem.indexOf("notnull(") > -1) {
                             var tagKey = checktagitem.replace("notnull(", "");
                             tagKey = tagKey.replace(")", "");
                             if (value == tagKey) {
                                 checkResultItem.message = tagname + "不能为空!";
                                 checkResult.push(checkResultItem);
                             }
                         }
                         //身份证类型检查
                         if (checktagitem == "IdentityCard") {
                             if (value && value!="") {
                                 var isIdentityCard = Cobject.IsIdentityCard(value);
                                 if (isIdentityCard == false) {
                                     checkResultItem.message = tagname + " 格式填写不正确!";
                                     checkResult.push(checkResultItem);
                                 }
                             }
                         }
                        //电话类型检查
                         if (checktagitem == "Phone") {
                             if (value && value != "") {
                                 var isPhone = Cobject.IsPhone(value);
                                 if (isPhone == false) {
                                     checkResultItem.message = tagname + " 格式填写不正确!";
                                     checkResult.push(checkResultItem);
                                 }
                             }
                         }
                        //检查日期格式
                         if (checktagitem == "Date") {
                             if (value && value != "") {
                                 var isDate = Cobject.IsDate(value);
                                 if (isDate == false) {
                                     checkResultItem.message = tagname + " 格式填写不正确!";
                                     checkResult.push(checkResultItem);
                                 }
                             }
                         }
                        //检查日期格式
                         if (checktagitem == "Email") {
                             if (value && value != "") {
                                 var isDate = Cobject.IsEmail(value);
                                 if (isDate == false) {
                                     checkResultItem.message = tagname + " 格式填写不正确!";
                                     checkResult.push(checkResultItem);
                                 }
                             }
                         }
                    });

                }
            }
        });

        ////检查集合类
        //if (Cobject.edPage && isPass == true) {
        //    for (var key in Cobject.edPage) {
        //        var control = Cobject.edPage[key];
        //        if (control != null) {
        //            switch (control.type) {
        //                case '$.ligerui.controls.Grid':
        //                    {
        //                        var columns = control.getColumns();
        //                        var data = null;
        //                        $(columns).each(function (ind2, columnItem) {
        //                            var checktag = columnItem.checktag;
        //                            if (checktag == "notnull") {
        //                                if (data == null) {
        //                                    data = control.getData();
        //                                }
        //                                $(data).each(function (rowIndex, rowItem) {
        //                                    var value = Cobject.GetFieldValue(rowItem, columnItem.name);
        //                                    if (value == null || value == "") {
        //                                        isPass = false;
        //                                        Cobject.alert("第" + (rowIndex + 1) + "行" + columnItem.display + "不能为空!");
        //                                        return false;
        //                                    }
        //                                });
        //                            }
        //                            if (isPass == false) {
        //                                return false;
        //                            }
        //                        });
        //                        if (isPass == false) {
        //                            return false;
        //                        }
        //                    }
        //                    break;
        //            }
        //        }
        //    }
        //}
        return checkResult;
    },
    //检查数据合法性，并提示结果
    CheckTag: function () {
        var isPass = true;
        var checkTagResult = Cobject.GetCheckTagResult();
        if (checkTagResult && checkTagResult.length > 0) {
            isPass = false;
            Cobject.alert(checkTagResult[0].message);
        }
        return isPass;
    },
    //设置成银行卡类型
    SetBankCard: function (control) {
        $(control).keyup(function () {
            this.value = this.value.replace(/\s/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
        });
        
    },

    //返回div里面的某个元素
    GetControlInDiv: function(div,controlName) {
        var control = null;
        if (div && controlName) {
            control = $($(div).find('*[name="' + controlName + '"]')[0]);
        }
        return control;
    },
    get:function (url,success,err) {
        if(url)
        {
            $.get(url, function (da) {
                var isSuccess=true;
                if(da&&da.code !=null&&da.code!=""&&da.code!="1")
                {
                    isSuccess=false;
                }
                if(da&&da.statusCode=="9926"&&da.resultMsg)
                {
                    isSuccess=false;
                    da.msg=da.resultMsg;
                }

                if(isSuccess==true)
                {
                    if(success)
                    {
                        success(da);
                    }
                }
                else {
                    if($.isFunction(err))
                    {
                        err(da);
                    }
                    else {
                        layer.msg(da.msg, {icon: 2,time:1200});
                    }

                    //layer.msg(data.msg);
                }


            });
        }
        else {
            layer.msg("url地址不存在！");
        }
    },
    post:function (url,data,success,err) {
        $.ajax({
            // cache : true,
            type : "POST",
            url : url,
            data : data,
            //async : false,
            error : function(request) {
                if($.isFunction(err))
                {
                    err(request);
                }
                else
                {
                    alert("Connection error");
                }

            },
            success : function(da) {
                var isSuccess=true;
                if(da&&da.code !=null&&da.code!=""&&da.code!="1")
                {
                    isSuccess=false;
                }
                if(isSuccess==true)
                {
                    if(success)
                    {
                        success(da);
                    }
                }
                else {
                    if($.isFunction(err))
                    {
                        err(da);
                    }
                    else {
                        layer.msg(da.msg, {icon: 2,time:3200});
                    }

                    //layer.msg(data.msg);
                }
            }
        });
    },
    getIndexPage:function () {
        var currentWindow = window;
        var indexInfo = null;
        //最多查询的嵌套层数
        var maxCount=10;

        var currentCount=0;
        while (currentWindow.parent) {
            currentCount=currentCount+1;
            currentWindow = currentWindow.parent;
            if (currentWindow.indexPage && currentWindow.indexPage.Page == "IndexPage") {
                indexInfo = currentWindow.indexPage;
                break;
            }
            //如果超过嵌套层数还没找到该页面的，直接退出
            if(currentCount>=maxCount)
            {
                break;
            }
        }



        return indexInfo;
    },
    //返回当前页面的权限按钮
    getPermitButton:function (mymenuCode) {
        var currentMenuCode=Cobject.menuCode;
        if(mymenuCode&&mymenuCode.length>0)
        {
            currentMenuCode=mymenuCode;
        }
       var buttonCodeList=null;
       var indexPage=Cobject.getIndexPage();
       if(indexPage&&currentMenuCode&&indexPage.getPagePermission)
       {
           buttonCodeList=  indexPage.getPagePermission(currentMenuCode);
       }
       return buttonCodeList;
    },
    initConditionStartTime:function (starttime) {
        var resultStartTime=  starttime;
        if(resultStartTime)
        {
            resultStartTime=resultStartTime.trim();
            if(resultStartTime.length>0)
            {
                //长类
                if(resultStartTime.length>10)
                {
                    var times=  resultStartTime.split(" ");
                    if(times.length==2)
                    {
                        resultStartTime=times[0]+ " 00:00:00";
                    }
                }

                //短类
                if(resultStartTime.length<=10)
                {
                    resultStartTime = resultStartTime + " 00:00:00"
                }
            }

        }
        return resultStartTime;
    },
    initConditionEndTime:function (endtime) {
    var resultEndTime=  endtime;
    if(resultEndTime)
    {
        resultEndTime=resultEndTime.trim();
        if(resultEndTime.length>0)
        {
            //长类
            if(resultEndTime.length>10)
            {
                var times=  resultEndTime.split(" ");
                if(times.length==2)
                {
                    resultEndTime=times[0]+ " 23:59:59";
                }
            }

            //短类
            if(resultEndTime.length<=10)
            {
                resultEndTime = resultEndTime + " 23:59:59"
            }
        }

    }
    return resultEndTime;
}
});






