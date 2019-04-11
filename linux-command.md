
# 常用Linux命令

### **ll** **ls**	文件和目录列表   
-a 列出目录所有文件，包含以.开始的隐藏文件  -t 以文件修改时间排序    -r 反序排列
cd 	更改当前目录：输入"cd"或"cd ~"将切换到当前用户的主目录。
cd - 将切换到上一次用户访问目录， cd .. 将退到当前目录的上一级目录（父目录）

### pwd	当前工作目录

### mkdir rm rmdir mv cp chmod chown tar
   rm -i *.log	  删除任何以.log结尾的文件；删除前逐一询问确认 
   rm -rf test    删除test子目录及子目录中所有档案删除,并且不用一一确认
   rm -- -f*      删除以-f开头的文件
   mv    		  移动文件或修改文件名，根据第二参数类型（如目录，则移动文件；如为文件则重命令该文件） mv test.log test1.txt  将文件test.log重命名为test1.txt，mv log1.txt log2.txt log3.txt /test3    将文件log1.txt,log2.txt,log3.txt移动到根的test3目录中

### grep 		强大的文本搜索命令，grep(Global Regular Expression Print)全局正则表达式搜索
        全局搜索并显示特定字符串，一般用来过滤显示结果，避免显示太多不必要的信息。其命令格式为：grep string file ，从file文件中过滤出字符串string的内容。该命令经常和管道命令一起使用，过滤屏幕的输出
### ps    
	命令参数：
         -A 显示所有进程
         a 显示所有进程
         -a 显示同一终端下所有进程
         c 显示进程真实名称
         e 显示环境变量
         f 显示进程间的关系
         r 显示当前终端运行的进程
         -aux 显示所有包含其它使用的进程

	常用 ps -ef | grep 进程名

### top    显示当前系统正在执行的进程的相关信息，包括进程ID、内存占用率、CPU占用率等 
    top -H 表示以thread模式启动top命令 （注意在配合jstack时要记得把十进制的PID转换成十六进制）
    
### vmstat
    vmstat -1 -10 查看和收集上下文切换数量（时间间隔1秒，收集10次）

### free   查看内存使用情况 （-h 转换为以M为单位，方便人阅读）

### iostat 有助于判断磁盘健康状况

### cat   [-n]由第一行开始显示档案内容 
### more 一页一页的显示档案内容      
 空格键 (space)：代表向下翻一页  Enter：代表向下翻一行
### less    less 与 more 类似，但使用 less 可以随意浏览文件，而 more 仅能向前移动，却不能向后移动，而且 less 在查看之前不会加载整个文件
### find   查找    
      命令格式：
         find pathname -options [-print -exec -ok ...]
      命令选项：
         -name 按照文件名查找文件
         -perm 按文件权限查找文件
         -user 按文件属主查找文件
      命令参数：
         -print： find命令将匹配的文件输出到标准输出。
         -exec： find命令对匹配的文件执行该参数所给出的shell命令。相应命令的形式为'command' {  } \;，注意{   }和\；之间的空格。
         -ok： 和-exec的作用相同，只不过以一种更为安全的模式来执行该参数所给出的shell命令，在执行每一个命令之前，都会给出提示，让用户来确定是否执行。

### su userid 更换用户身份，默认更改为root用户 
### clear     清屏
### uptime    显示系统已经运行了多长时间，它依次显示下列信息：现在时间、系统已经运行了多长时间、目前有多少登陆用户、系统在过去的1分钟、5分钟和15分钟内的平均负载
cal    得到一个整齐的日历格式
### history    屏幕输出当前用户在命令行模式下执行的最后（1000个）命令

Tab按键    自动补全
Ctrl+c按键    让当前的程序停掉



### 查看服务进程
    ps -ef | grep Tomcat 
  
### 强杀服务
    kill -9 pid      kill -9 $(ps -ef | grep pro1)     先使用ps查找进程pro1，然后用kill杀掉
  
### 直接替换文件
    sz 下载    rz -y  覆盖上传

### 滚动查看日志
    tail -500f SystemOut.log    -n<行数> 显示行数（从后向前） -f 循环读取（常用于查看递增的日志文件）   
    直接用ctrl+c即可停止打印 

### 搜索日志
    cat ping.log|grep icmp_seq=190    搜索ping.log中内容为'icmp_seq=190'的地方 
    也可以直接grep icmp_seq=190 ping.log
