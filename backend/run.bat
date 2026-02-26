@echo off
SETLOCAL EnableDelayedExpansion

:MENU
cls
echo ============================================
echo      EXECUTAR - PROJETO FORUM
echo ============================================
echo 1. AMBIENTE TOTAL (Tudo no Docker)
echo 2. AMBIENTE DEV   (App Windows + Banco Docker)
echo 3. Voltar para o terminal
echo ============================================
set /p choice="Escolha uma opcao: "

if "%choice%"=="1" goto OPCAO1
if "%choice%"=="2" goto OPCAO2
if "%choice%"=="3" goto SAIR
goto MENU

:OPCAO1
echo.
echo [INFO] Subindo tudo via Docker...
docker compose -p backend up -d --build
if %ERRORLEVEL% NEQ 0 goto DOCKER_ERROR
echo [OK] Containers iniciados.
goto SAIR

:OPCAO2
echo.
echo [1/2] Carregando .env e subindo Postgres...
if not exist .env (echo [ERRO] .env nao encontrado! & pause & goto MENU)
for /f "usebackq tokens=*" %%i in (".env") do (set "%%i")
docker compose -f docker-compose.postgres.yml up -d
if %ERRORLEVEL% NEQ 0 goto DOCKER_ERROR

echo [2/2] Iniciando Spring Boot...
:: cmd /c permite que o terminal continue aberto após o encerramento do Spring
cmd /c mvnw.cmd spring-boot:run
goto SAIR

:DOCKER_ERROR
echo.
echo [ERRO] Falha no Docker. Verifique se o Docker Desktop esta aberto.
goto SAIR

:SAIR
echo [INFO] Script finalizado. Terminal pronto para uso.
echo.
goto :EOF