"use strict";
(function($) {
    var template =
        '<div class="jcalculator">' +
        //自定义的
        '<span  class="operation">(</span>' +
        '<span  class="operation" >)</span>' +
        '<span data-value="" class="add_item"> 变量</span>' +
        '<span class="clear ">清空</span>' +
        '<span>7</span>' +
        '<span>8</span>' +
        '<span>9</span>' +
        '<span class="plus operation">+</span>' +
        '<span>4</span>' +
        '<span>5</span>' +
        '<span>6</span>' +
        '<span class="minus operation">-</span>' +
        '<span>1</span>' +
        '<span>2</span>' +
        '<span>3</span>' +
        '<span class=" operation">*</span>' +
        '<span class="operation">.</span>' +
        '<span>0</span>' +
        '<span class="ok ">确定</span>' +
        '<span class="operation">/</span>' +
        '</div>';
    var getTempHtml = function (that) {
        // 根据数据源渲染ul

        // 将完整的ul li 代码插入到页面中，保存$ul的引用
        var $ul = $(template).appendTo("body");

        //点击事件
        $ul.find('span').on('click', function(e) {
            var val = $(that).attr("data-value");

            var code = $(this).text().trim();
            if (!isNaN(code) || $(this).hasClass("operation")) {

                //判断是否有值
                if(val == '' || val == undefined || val == null ){
                    $(that).text("");
                }
                $(that).attr("data-value",$(that).attr("data-value") + code);
                $(that).text( $(that).text() + code);
            } else {
                //删除
                if($(this).hasClass("clear")){
                    $(that).attr("data-value","");
                    $(that).text("请输入四则表达式");
                }else if($(this).hasClass("add_item")){
                    $(this).submenu({
                        data: sceneUtil.data.entitysBank,
                        callback: function (obj, value) {
                            val = $(that).attr("data-value");
                            if(val == '' || val == undefined || val == null ){
                                $(that).text("");
                                $(that).attr("data-value","");
                            }
                            var itemId = $(that).attr("data-itemId");
                            if( itemId == '' || itemId == undefined  ){
                                $(that).attr("data-itemId",value);
                            }else{
                                $(that).attr("data-itemId",itemId+","+value);
                            }
                            $(that).attr("data-value",$(that).attr("data-value") + "{{"+$(obj).attr("key")+"}}");
                            $(that).text( $(that).text() + $(obj).attr("ptext")+"."+ $(obj).text());
                        }
                    });
                    $(this).click();

                }
            }
        });

        // 点击屏幕任何角落，隐藏$ul
        $(document).on("click", function() { $ul.hide(); });
        $ul.on("click", function(e) {
            e.stopPropagation();
        });
        return $ul;
    }
    // 防止重复的id
    var id = 1;
    // 生成html
    var genHTML = function (jsondata,type,cb) {
        // 根据数据源渲染ul
        var items = '';
        for (var i = 0; i < jsondata.length; i++) {
            var subItems = '';
            // 如果存在子集
            if (jsondata[i].sons != undefined) {
                subItems += '<ul class="m-list">';
                for (var m = 0; m < jsondata[i].sons.length; m++) {
                    var _value = jsondata[i].sons[m].value;
                    var _text = jsondata[i].sons[m].text;
                    var _key = jsondata[i].key + '_'+jsondata[i].sons[m].key;
                    subItems += '<li value=' + _value + '  key= '+_key+ ' ptext="'+jsondata[i].text+'">' + _text + '</li>';
                }
                subItems += '</ul>';
            }
            items += '<li value="'+jsondata[i].value+'" class="one-li" >' + jsondata[i].text + subItems + '</li>';
        }

        // 将完整的ul li 代码插入到页面中，保存$ul的引用
        var $ul = $("<ul class='xy-dropdown-menu' id='xy-dropdown-menu-" + id + "'>" + items + "</ul>").appendTo("body");
        // console.log($ul);
        // 给子列表的li绑定点击事件
        $ul.find('.m-list li').on('click', function(e) {
            var _value = $(this).attr("value");
            cb && cb($(this), _value);
            $ul.hide();
        });
        if(type == 1){
            $ul.find('li.one-li').on('click', function(e) {
                var _value = $(this).val();
                cb && cb($(this), _value);
                $ul.hide();
            });
        }

        // 展示或隐藏子列表
        $ul.find('>li').hover(function() {
            $(this).find(".m-list").show();
        }, function() {
            $(this).find(".m-list").hide();
        });

        // 点击屏幕任何角落，隐藏$ul
        $(document).on("click", function() { $ul.hide(); });
        $ul.on("click", function(e) {
            e.stopPropagation();
        });

        return $ul;
    }

    $.fn.extend({
        // 弹出nav
        submenu: function(options) {
            // 验证参数合法性
            if (!typeof(options) === 'object') return console.error("submenu方法参数必须是object对象");
            if (!options.data || !options.hasOwnProperty('data')) return console.error("submenu方法必须包含data参数");
            if (!options.hasOwnProperty('callback') || !typeof(options.callback) === 'function') return console.error("submenu方法必须包含callback参数并且必须是一个函数");

            // xy-dropdown-menu
            var menuId = $(this).attr('xy-dropdown-menu');
            if (menuId) $(menuId).remove()

            // 根据数据，生成html内容，并且插入到页面中去
            var $ul = genHTML(options.data,options.type, options.callback);
            var $obj = $(this);
            // 根据触发元素的位置，来设置ul的绝对路径
            var tx = this.offset().top;
            var ty = this.offset().left;
            var th = this.height();
           // console.log(tx+":left"+ty);
            //$ul.offset({ top: tx + th + 11 + 'px', left: ty + 5 + 'px' });
            $ul.css("top",tx + th + 11 + 'px');
            $ul.css("left",ty - 20 + 'px');
            // 将触发对象设置特殊属性
            $(this).attr('xy-dropdown-menu', '#xy-dropdown-menu-btn-' + id);

            // 增加id的索引
            id++;
            // 给触发对象绑定事件
            $(this).unbind('click').bind('click', function(e) {
                // 根据触发元素的位置，来设置ul的绝对路径
                var tx = $obj.offset().top;
                var ty = $obj.offset().left;
                var th = $obj.height();
              //  console.log(tx+":left"+ty);
                //$ul.offset({ top: tx + th + 11 + 'px', left: ty + 5 + 'px' });
                $ul.css("top",tx + th + 11 + 'px');
                $ul.css("left",ty - 1 + 'px');

                $ul.toggle();
                e.stopPropagation();
            });
        },

        calculator: function(options) {
        // 验证参数合法性
        if (!typeof(options) === 'object') return console.error("submenu方法参数必须是object对象");

        // xy-dropdown-menu
        var menuId = $(this).attr('xy-dropdown-menu');
        if (menuId) $(menuId).remove()

        // 根据数据，生成html内容，并且插入到页面中去
        var $html = getTempHtml($(this));
        var $obj = $(this);
        // 根据触发元素的位置，来设置ul的绝对路径
        var tx = this.offset().top;
        var ty = this.offset().left;
        var th = this.height();
      //  console.log(tx+":left"+ty);
        //$ul.offset({ top: tx + th + 11 + 'px', left: ty + 5 + 'px' });
            $html.css("top",tx + th + 11 + 'px');
            $html.css("left",ty - 20 + 'px');
        // 将触发对象设置特殊属性
        $(this).attr('xy-dropdown-menu', '#xy-dropdown-menu-btn-' + id);

        // 增加id的索引
        id++;
        // 给触发对象绑定事件
        $(this).unbind('click').bind('click', function(e) {
            // 根据触发元素的位置，来设置ul的绝对路径
            var tx = $obj.offset().top;
            var ty = $obj.offset().left;
            var th = $obj.height();
           // console.log(tx+":left"+ty);
            //$ul.offset({ top: tx + th + 11 + 'px', left: ty + 5 + 'px' });
            $html.css("top",tx + th + 11 + 'px');
            $html.css("left",ty - 1 + 'px');

            $html.toggle();
            e.stopPropagation();
        });
    },
    });
})(window.jQuery);