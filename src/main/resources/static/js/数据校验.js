$.extend($.fn.textbox.defaults.rules, {
    eqPwd : {
        validator : function(value, param) {
            return value == $(param[0]).val();
        },
        message : '密码不一致！'
    },
    idcard : {// 验证身份证
        validator : function(value) {
            var vcity = {
                11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古",
                21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏",
                33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南",
                42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆",
                51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃",
                63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外"
            };

            // 判断是否为空
            isEmpty = function (card) {
                if (/^\s*$/.test(card) === true) {
                    return true;
                }
            }
            //检查号码是否符合规范，包括长度，类型
            isCardNo = function (card) {
                if (isEmpty(card)) {
                    return true;
                }
                //这个代码表示身份证可以为空
                //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
                var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
                if (reg.test(card) === false) {
                    return false;
                }


                return true;
            };

            //取身份证前两位,校验省份
            checkProvince = function (card) {

                var province = card.substr(0, 2);
                if (vcity[province] == undefined) {
                    return false;
                }
                return true;
            };

            //检查生日是否正确
            checkBirthday = function (card) {

                var len = card.length;
                //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
                if (len == '15') {
                    var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
                    var arr_data = card.match(re_fifteen);
                    var year = arr_data[2];
                    var month = arr_data[3];
                    var day = arr_data[4];
                    var birthday = new Date('19' + year + '/' + month + '/' + day);
                    return verifyBirthday('19' + year, month, day, birthday);
                }
                //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
                if (len == '18') {
                    var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
                    var arr_data = card.match(re_eighteen);
                    var year = arr_data[2];
                    var month = arr_data[3];
                    var day = arr_data[4];
                    var birthday = new Date(year + '/' + month + '/' + day);
                    return verifyBirthday(year, month, day, birthday);
                }
                return false;
            };

            //校验日期
            verifyBirthday = function (year, month, day, birthday) {
                var now = new Date();
                var now_year = now.getFullYear();
                //年月日是否合理
                if (birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day) {
                    //判断年份的范围（3岁到100岁之间)
                    var time = now_year - year;
                    if (time >= 3 && time <= 100) {
                        return true;
                    }
                    return false;
                }
                return false;
            };

            //校验位的检测
            checkParity = function (card) {
                if (isEmpty(card)) {
                    return true;
                }
                //15位转18位
                card = changeFivteenToEighteen(card);
                var len = card.length;
                if (len == '18') {
                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                    var cardTemp = 0, i, valnum;
                    for (i = 0; i < 17; i++) {
                        cardTemp += card.substr(i, 1) * arrInt[i];
                    }
                    valnum = arrCh[cardTemp % 11];
                    if (valnum == card.substr(17, 1)) {
                        return true;
                    }
                    return false;
                }
                return false;
            };

            //15位转18位身份证号
            changeFivteenToEighteen = function (card) {
                if (isEmpty(card)) {
                    return true;
                }
                if (card.length == '15') {
                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                    var cardTemp = 0, i;
                    card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
                    for (i = 0; i < 17; i++) {
                        cardTemp += card.substr(i, 1) * arrInt[i];
                    }
                    card += arrCh[cardTemp % 11];
                    return card;
                }
                return card;
            };

            //checkCard = function () {
            var card = value;
            //校验长度，类型
            if (isCardNo(card) === false) {
                //alert('您输入的身份证号码不正确，请重新输入');
                //document.getElementById('card_no').focus;
                return false;
            }
            //检查省份
            if (checkProvince(card) === false) {
                return false;
            }
            //校验生日
            if (checkBirthday(card) === false) {
                return false;
            }
            //检验位的检测
            if (checkParity(card) === false) {
                return false;
            }

            return true;
        },
        message : '身份证号码格式不正确'
    },
    minLength: {
        validator: function(value, param){
            return value.length >= param[0];
        },
        message: '请输入至少（2）个字符.'
    },
    length:{validator:function(value,param){
            var len=$.trim(value).length;
            return len>=param[0]&&len<=param[1];
        },
        message:"输入内容长度必须介于{0}和{1}之间."
    },
    phone : {// 验证电话号码
        validator : function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message : '格式不正确,请使用下面格式:010-88888888'
    },
    mobile : {// 验证手机号码
        validator : function(value) {
            return /^(130|131|132|133|153|155|134|135|136|137|138|139|150|151|152|156|157|158|159|180|181|182|183|187|188|189)\d{8}$/i.test(value);
        },
        message : '手机号码格式不正确'
    },
    intOrFloat : {// 验证整数或小数
        validator : function(value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message : '请输入数字，并确保格式正确'
    },
    currency : {// 验证货币
        validator : function(value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message : '货币格式不正确'
    },
    qq : {// 验证QQ,从10000开始
        validator : function(value) {
            return /^[1-9]\d{4,9}$/i.test(value);
        },
        message : 'QQ号码格式不正确'
    },
    integer : {// 验证整数
        validator : function(value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message : '请输入整数'
    },
    age : {// 验证年龄
        validator : function(value) {
            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
        },
        message : '年龄必须是0到120之间的整数'
    },
    chinese : {// 验证中文
        validator : function(value) {
            return /^[\Α-\￥]+$/i.test(value);
        },
        message : '请输入中文'
    },
    english : {// 验证英语
        validator : function(value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message : '请输入英文'
    },
    unnormal : {// 验证是否包含空格和非法字符
        validator : function(value) {
            return /.+/i.test(value);
        },
        message : '输入值不能为空和包含其他非法字符'
    },
    username : {// 验证用户名
        validator : function(value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        },
        message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
    },
    faxno : {// 验证传真
        validator : function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message : '传真号码不正确'
    },
    zip : {// 验证邮政编码
        validator : function(value) {
            return /^[0-9]\d{5}$/i.test(value);
        },
        message : '邮政编码格式不正确'
    },
    ip : {// 验证IP地址
        validator : function(value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message : 'IP地址格式不正确'
    },
    name : {// 验证姓名，可以是中文或英文
        validator : function(value) {
            return /^[\Α-\￥]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
        },
        message : '请输入姓名'
    },
    msn:{
        validator : function(value){
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
        },
        message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
    },
    emaill:{
        validator : function(value){
                return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(value)
        },
        message : '请输入有效的邮箱地址(例：abc@hotmail.com)'
    },
    userNameValidity:{
        validator : function(value){
            if (!value) {
                message : '请输入名称'
                return false;

            } else {
                // 判断开头是否是空字符
                const nullReg = /^\S+/
                if (!nullReg.test(value)) {
                    message : '名字首位不能有空格，请重新输入'
                    return false;
                } else {
                    // 开头不是空字符
                    //1 判断首字符是否是字母，是则走英文校验
                    const resp = /^(\$|[a-zA-Z_])/
                    if (resp.test(value)) {
                        // 英文校验
                        const cc = /^[a-zA-Z]+(\s+[a-zA-Z]+)*$/
                        if (value.length < 100 && cc.test(value)) {
                            return false;
                        } else {
                            return false;
                        }
                    } else {
                        const regs = /^[\u4e00-\u9fa5]+$/
                        if (value.length < 100 && regs.test(value)) {
                            return false;
                        } else {

                            return false;
                        }
                    }
                    return false;
                }
            }
        },


        // message : '名称格式不正确，请输入正确的名称'
    },
    isIdCard: {
        validator: function(value, param){
            var partten =/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
            return partten.test(value);
        },
        message: '身份证号错误！'
    }




});

