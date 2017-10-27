@echo off

SET java_pid=1234

echo Periodically dumping netstat and jstack %java_pid% data on this host ...

rem netstat args: -a -b -v -n

for /L %%c in (1,1,10000) do (date /t >> .\netstat.txt) && (time /t >> .\netstat.txt) && (netstat -a -b -v -n >> .\netstat.txt) && (jstack -l 1111 >> .\jstack.txt) && (echo Collection %%c% successful) && ((timeout /t 1 /nobreak)>nul)


