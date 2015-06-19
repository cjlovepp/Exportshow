一、linux平台导出highcharts图片中文乱码现象及解决（linux未安装中文字体）
	解决方案：
		1：检查是否安装中文字体：fc-list :lang=zh
		2：安装中文字体包（可以直接拷贝windows的字体包）
		3：将中文字体包放到linux字体共享目录下：mkdir -p /usr/share/fonts/my_fonts
   											  mv /tmp/fonts/simkai.ttf /usr/share/fonts/my_fonts/
   											  mv /tmp/fonts/ simsun.ttc /usr/share/fonts/my_fonts/
   											  mv /tmp/fonts/ simhei.ttf /usr/share/fonts/my_fonts/
   		4：生成字体索引：在/usr/share/fonts/my_fonts/目录下执行： mkfontscale
   		5：完成