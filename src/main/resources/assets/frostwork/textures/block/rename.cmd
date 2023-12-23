@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
SET old=name
SET new=elm
for /f "tokens=*" %%f in ('dir /b *.png') do (
  SET newname=%%f
  SET newname=!newname:%old%=%new%!
  move "%%f" "!newname!"
)