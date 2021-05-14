@echo off 
if "%1" == "h" goto begin 
mshta vbscript:createobject("wscript.shell").run("%~nx0 h",0)(window.close)&&exit 
:begin
md D:\my\resource\project\swing-tips-5-11\swing-tips\src\main\resources\cmd\%1