@echo off

echo Periodically dumping netstat and jstack data on this host ...

for /L %%c in (1,1,10000) do (date /t >> .\netstat.txt) && (time /t >> .\netstat.txt) && (netstat -a -b -v -n >> .\netstat.txt) && (jstack -l 7777 >> .\jstack.txt) && (echo Collection %%c% successful) && ((timeout /t 1 /nobreak)>nul)


