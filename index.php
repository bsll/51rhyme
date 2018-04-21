<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>歌词搜索</title>
<style type="text/css">
input {font-size:14px;font-family:arial;border: none;background:none;}
    /*--这里设置输入框文字字体，并设置输入框背景和边框为无--*/
span.search {float:left;
    /*--将输入框放在span中并设置浮动--*/
padding:0; margin:10,90px;
width:250px;height:36px;
    /*--设置输入框的外层元素宽度和高度--*/
background:#fafafa;color: #9e9c9c;
    /*--设置背景颜色和输入框文字颜色--*/
border: 2px solid #628DFF;
border-top-left-radius:5px;
border-bottom-left-radius:5px;

    /*--设置边框，并将左上角和左下角设置圆角--*/
}
span.search input {
width: 240px;height:36px;
    /*--设置输入框本身的宽度和高度--*/
line-height:36px;
    /*--设置输入框行距和高度相等以便垂直居中--*/
padding:0 5px;
    /*--设置输入框本身离外层的内边距，输入文字更美观--*/
outline:none;
    /*--为了美观，将获取焦点时的轮廓线去掉--*/
}
span.button {float:left;
    /*--设置提交按钮放在span中并设置浮动--*/
padding:0;margin:0;
border: 2px solid #628DFF;
border-top-right-radius:5px;
border-bottom-right-radius:5px;

    /*--设置边框，并将右上角和右下角设置圆角--*/
}
span.button input {
height:36px;
line-height:36px;
    /*--设置按钮本身的高度，行距和高度相等以便垂直居中--*/
padding:0 12px;
    /*--为了美观，适当设置按钮文字的左右边距--*/
background:#628DFF;color:#fff;
    /*--将提交按钮的背景颜色设置与边框颜色相同--*/
}
::-webkit-input-placeholder {color:#ccc;}
    /*--设置输入框内默认文字的颜色--*/
</style>
</head>
<body>
<form action="search.php" target="_blank" />
<input name="flag" type="hidden" value="word" />
<span class="search"><input type="text" name="word" placeholder="请输入押韵字"/></span>
<!--注意以上为文本框设置的placeholder属性，为文本框默认显示的文字-->
<span class="button"><input type="submit"  value="搜索" /></span>
</form>
</body>
</html>
